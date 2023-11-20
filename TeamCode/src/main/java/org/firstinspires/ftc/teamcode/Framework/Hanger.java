package org.firstinspires.ftc.teamcode.Framework;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.ThermalEquilibrium.homeostasis.Controllers.Feedback.BasicPID;
import com.ThermalEquilibrium.homeostasis.Parameters.PIDCoefficients;
import org.firstinspires.ftc.teamcode.Framework.misc.MotionProfile;

public class Hanger {

    private static final double KP = 0; //Todo Tuning

    private static final int HANG_UP_POSITION = 1000;
    private static final int HANG_DOWN_POSITION = 0;

    private static final double MAX_ACCELERATION = 1.0; //Todo Tuning
    private static final double MAX_VELOCITY = 1.0;

    private final DcMotor hangerMotor;
    private int targetPosition;
    private final PIDCoefficients coefficients = new PIDCoefficients(KP, 0, 0);
    private final BasicPID controller = new BasicPID(coefficients);
    private ElapsedTime timer = new ElapsedTime();

    public Hanger(DcMotor motor) {
        this.hangerMotor = motor;
    }

    public void setTargetPosition(int target) {
        targetPosition = target;
        timer.reset();
    }

    public int getTargetPosition() {
        return this.targetPosition;
    }

    public void moveHanger(double power) {
        hangerMotor.setPower(power);
    }

    public void update() {
        double elapsedTime = timer.seconds();
        double distance = targetPosition - hangerMotor.getCurrentPosition();
        double instantTargetPosition = MotionProfile.motion_profile(MAX_ACCELERATION, MAX_VELOCITY, distance, elapsedTime);

        double motorPower = controller.calculate(instantTargetPosition, hangerMotor.getCurrentPosition());
        moveHanger(motorPower);
        
//        double motorPower = calculatePidOutput(targetPosition, hangerMotor.getCurrentPosition());
//        moveHanger(motorPower);
    }

    private double calculatePidOutput(int targetPosition, int currentPosition) {
        return controller.calculate(targetPosition, currentPosition);
    }

    public void hangUp() {
        setTargetPosition(HANG_UP_POSITION);
    }

    public void hangDown() {
        setTargetPosition(HANG_DOWN_POSITION);
    }
}
