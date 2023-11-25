package org.firstinspires.ftc.teamcode.Framework;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.ThermalEquilibrium.homeostasis.Controllers.Feedback.BasicPID;
import com.ThermalEquilibrium.homeostasis.Parameters.PIDCoefficients;
import org.firstinspires.ftc.teamcode.Framework.misc.MotionProfile;

public class Hanger {
    private final DcMotor hangerMotor;
    public Hanger(DcMotor motor) {
        this.hangerMotor = motor;
    }

    public static double KP = 0;
    public static int targetPosition = 0;
    public static double MAX_ACCELERATION = 1.0;
    public static double MAX_VELOCITY = 1.0;
    public int HANG_UP_POSITION = 13000;
    public int HANG_DOWN_POSITION = 0;
    PIDCoefficients coefficients = new PIDCoefficients(KP, 0, 0);
    BasicPID controller = new BasicPID(coefficients);
    public ElapsedTime timer = new ElapsedTime();

    public void update(){
        double elapsedTime = timer.seconds();
        int currentPosition = hangerMotor.getCurrentPosition();
        double KP_POWER = controller.calculate(targetPosition, currentPosition);
        double distance = targetPosition - hangerMotor.getCurrentPosition();
        double instantTargetPosition = MotionProfile.motion_profile(MAX_ACCELERATION,
                MAX_VELOCITY,
                distance,
                elapsedTime);
        double motorPower = (instantTargetPosition - hangerMotor.getCurrentPosition()) * KP_POWER;

        hangerMotor.setPower(motorPower);
    }
    public void setTargetPosition(int target){
        targetPosition = target;
        timer.reset();
    }
    public void hangUp(){
        setTargetPosition(HANG_UP_POSITION);
    }
    public void hangDown(){
        setTargetPosition(HANG_DOWN_POSITION);
    }
}