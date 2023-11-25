package org.firstinspires.ftc.teamcode.Framework;

import com.ThermalEquilibrium.homeostasis.Controllers.Feedback.BasicPID;
import com.ThermalEquilibrium.homeostasis.Parameters.PIDCoefficients;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Framework.misc.MotionProfile;

public class tubingIntake {

    private final DcMotor intakeMotor;

    public tubingIntake(DcMotor intakeMotor) {
        this.intakeMotor = intakeMotor;
    }

    public static double KP = 0;
    public static int targetPosition = 0;
    public static double MAX_ACCELERATION = 1.0;
    public static double MAX_VELOCITY = 1.0;

    PIDCoefficients coefficients = new PIDCoefficients(KP, 0, 0);
    BasicPID controller = new BasicPID(coefficients);
    public ElapsedTime timer = new ElapsedTime();

    public void update(){
        double elapsedTime = timer.seconds();
        int currentPosition = intakeMotor.getCurrentPosition();
        double KP_POWER = controller.calculate(targetPosition, currentPosition);
        double distance = targetPosition - intakeMotor.getCurrentPosition();
        double instantTargetPosition = MotionProfile.motion_profile(MAX_ACCELERATION,
                MAX_VELOCITY,
                distance,
                elapsedTime);
        double motorPower = (instantTargetPosition - intakeMotor.getCurrentPosition()) * KP_POWER;

        intakeMotor.setPower(motorPower);
    }

    public void setTargetPosition(int target){
        targetPosition = target;
        timer.reset();
    }

}
