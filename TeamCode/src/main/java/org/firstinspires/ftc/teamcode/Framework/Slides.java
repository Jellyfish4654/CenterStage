package org.firstinspires.ftc.teamcode.Framework;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.ThermalEquilibrium.homeostasis.Controllers.Feedback.BasicPID;
import com.ThermalEquilibrium.homeostasis.Parameters.PIDCoefficients;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Framework.misc.MotionProfile;

public class Slides {
    public  DcMotor leftMotor;
    public  DcMotor rightMotor;
    public Slides(DcMotor leftMotor, DcMotor rightMotor) {
        this.leftMotor = leftMotor;
        this.rightMotor = rightMotor;
    }
    public static double KP = 0;
    public static double KI = 0;
    public static double KD = 0;
    public static double FEED_FORWARD_CONSTANT = 0;
    public static double MAX_ACCELERATION = 1.0; // Adjust as needed
    public static double MAX_VELOCITY = 1.0; // Adjust as needed
    public static int targetPosition = 0;
    private final double TICKS_PER_DEGREE = 145.1 / 360.0;
    public static int SLIDE_LOWER_BOUND = 0;
    public static int SLIDE_UPPER_BOUND = 3000;
    public static int EXTENDED_STATE = 500;
    PIDCoefficients coefficients = new PIDCoefficients(KP, KI, KD);
    BasicPID controller = new BasicPID(coefficients);

    public ElapsedTime timer = new ElapsedTime();

    public void update(){
        double elapsedTime = timer.seconds();
        int leftCurrentPosition = leftMotor.getCurrentPosition();
        int rightCurrentPosition = rightMotor.getCurrentPosition();
        double FF = Math.cos(Math.toRadians(targetPosition / TICKS_PER_DEGREE)) * FEED_FORWARD_CONSTANT;
        double LEFT_PIDF_POWER = controller.calculate(targetPosition, leftCurrentPosition) + FF;
        double RIGHT_PIDF_POWER = controller.calculate(targetPosition, rightCurrentPosition) + FF;
        double leftDistance = targetPosition - leftMotor.getCurrentPosition();
        double rightDistance = targetPosition - rightMotor.getCurrentPosition();
        double leftInstantTargetPosition = MotionProfile.motion_profile(MAX_ACCELERATION,
                MAX_VELOCITY,
                leftDistance,
                elapsedTime);
        double rightInstantTargetPosition = MotionProfile.motion_profile(MAX_ACCELERATION,
                MAX_VELOCITY,
                rightDistance,
                elapsedTime);
        double leftMotorPower = (leftInstantTargetPosition - leftMotor.getCurrentPosition()) * LEFT_PIDF_POWER;
        double rightMotorPower = (rightInstantTargetPosition - rightMotor.getCurrentPosition()) * RIGHT_PIDF_POWER;

        leftMotor.setPower(LEFT_PIDF_POWER);
        rightMotor.setPower(RIGHT_PIDF_POWER);
    }

    public void setTargetPosition(int target) {
        targetPosition = Math.max(SLIDE_LOWER_BOUND, Math.min(target, SLIDE_UPPER_BOUND));
        timer.reset();
    }
    public int getTargetPosition() {
        return this.targetPosition;
    }
    public boolean slidesExtendedState() {
        return targetPosition > EXTENDED_STATE;
    }
    public boolean isAtTargetPosition() {
        int leftPosition = leftMotor.getCurrentPosition();
        int rightPosition = rightMotor.getCurrentPosition();
        return leftPosition == targetPosition && rightPosition == targetPosition;
    }
}