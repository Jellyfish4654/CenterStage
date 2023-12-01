package org.firstinspires.ftc.teamcode.Framework;

import com.ThermalEquilibrium.homeostasis.Controllers.Feedback.BasicPID;
import com.ThermalEquilibrium.homeostasis.Parameters.PIDCoefficients;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Framework.misc.MotionProfile;

public class oneSlide {
    private DcMotor leftMotor;

    private static double KP = 0.005;
    private static double KI = 0;
    private static double KD = 0;
    private static double FEED_FORWARD_CONSTANT = 0;
    private static double MAX_ACCELERATION = 1.0; // Adjust as needed
    private static double MAX_VELOCITY = 1.0; // Adjust as needed
    private static int targetPosition = 0;
    private final double TICKS_PER_DEGREE = 145.1 / 360.0;
    private static int SLIDE_LOWER_BOUND = 0;
    private static int SLIDE_UPPER_BOUND = 3000;
    private static int EXTENDED_STATE = 500;
    private boolean isManualControl = false;
    private double profileStartTime; // Track the start time of each profile

    PIDCoefficients coefficients = new PIDCoefficients(KP, KI, KD);
    BasicPID controller = new BasicPID(coefficients);
    public ElapsedTime timer = new ElapsedTime();

    public oneSlide(DcMotor leftMotor) {
        this.leftMotor = leftMotor;
    }

    public void update() {
        double elapsedTime = timer.seconds() - profileStartTime;
        int currentPosition = leftMotor.getCurrentPosition();
        double FF = Math.cos(Math.toRadians(targetPosition / TICKS_PER_DEGREE)) * FEED_FORWARD_CONSTANT;
        double PIDF_POWER = controller.calculate(targetPosition, currentPosition) + FF;

        double distance = targetPosition - currentPosition;
        double instantTargetPosition = MotionProfile.motion_profile(MAX_ACCELERATION,
                MAX_VELOCITY,
                distance,
                elapsedTime);

        double motorPower = (instantTargetPosition - currentPosition) * PIDF_POWER;
        leftMotor.setPower(motorPower);
    }

    public void setTargetPosition(int target) {
        targetPosition = Math.max(SLIDE_LOWER_BOUND, Math.min(target, SLIDE_UPPER_BOUND));
        if (!isManualControl) {
            profileStartTime = timer.seconds();
        }
    }

    public void setManualControl(boolean manualControl) {
        isManualControl = manualControl;
        if (!manualControl) {
            profileStartTime = timer.seconds();
        }
    }

    public int getTargetPosition() {
        return targetPosition;
    }

    public boolean slidesExtendedState() {
        return targetPosition > EXTENDED_STATE;
    }

    public boolean isAtTargetPosition() {
        int currentPosition = leftMotor.getCurrentPosition();
        return currentPosition == targetPosition;
    }
}
