package org.firstinspires.ftc.teamcode.Framework;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.ThermalEquilibrium.homeostasis.Controllers.Feedback.BasicPID;
import com.ThermalEquilibrium.homeostasis.Parameters.PIDCoefficients;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Framework.misc.MotionProfile;

public class Slides {

    private static final int SLIDE_LOWER_BOUND = 0;
    private static final int SLIDE_UPPER_BOUND = 3000;
    private static final double TICKS_PER_DEGREE = 145.1 / 360.0;
    private static final int EXTENDED_THRESHOLD = 500;
    private static final double KP = 0;
    private static final double KI = 0;
    private static final double KD = 0;
    private static final double FEED_FORWARD_CONSTANT = 0;
    private static final double MAX_ACCELERATION = 1.0; // Adjust as needed
    private static final double MAX_VELOCITY = 1.0; // Adjust as needed

    private final DcMotor leftMotor;
    private final DcMotor rightMotor;
    private int targetPosition;
    private final PIDCoefficients coefficients = new PIDCoefficients(KP, KI, KD);
    private final BasicPID controller = new BasicPID(coefficients);

    private ElapsedTime timer = new ElapsedTime();

    public Slides(DcMotor leftMotor, DcMotor rightMotor) {
        this.leftMotor = leftMotor;
        this.rightMotor = rightMotor;
    }

    public void setTargetPosition(int target) {
        targetPosition = Math.max(SLIDE_LOWER_BOUND, Math.min(target, SLIDE_UPPER_BOUND));
        timer.reset();
    }
    public int getTargetPosition() {
        return this.targetPosition;
    }
    public void moveSlides(double leftPower, double rightPower) {
        leftMotor.setPower(leftPower);
        rightMotor.setPower(rightPower);
    }

    public boolean slidesExtendedState() {
        return targetPosition > EXTENDED_THRESHOLD;
    }

    public void update() {
        double elapsedTime = timer.seconds();
        int leftPosition = leftMotor.getCurrentPosition();
        int rightPosition = rightMotor.getCurrentPosition();

        // Regular PIDF
        // double leftPidOutput = calculatePidOutput(targetPosition, leftPosition);
        // double rightPidOutput = calculatePidOutput(targetPosition, rightPosition);
        // double feedForward = calculateFeedForward(targetPosition);
        // moveSlides(leftPidOutput + feedForward, rightPidOutput + feedForward);

        double leftDistance = targetPosition - leftMotor.getCurrentPosition();
        double rightDistance = targetPosition - rightMotor.getCurrentPosition();

        double instantLeftTargetPosition = MotionProfile.motion_profile(MAX_ACCELERATION, MAX_VELOCITY, leftDistance, elapsedTime);
        double instantRightTargetPosition = MotionProfile.motion_profile(MAX_ACCELERATION, MAX_VELOCITY, rightDistance, elapsedTime);

        double leftPidOutput = calculatePidOutput((int) instantLeftTargetPosition, leftMotor.getCurrentPosition());
        double rightPidOutput = calculatePidOutput((int) instantRightTargetPosition, rightMotor.getCurrentPosition());
        double leftFeedForward = calculateFeedForward((int) instantLeftTargetPosition);
        double rightFeedForward = calculateFeedForward((int) instantRightTargetPosition);

        // Apply the calculated power to each motor
        moveSlides(leftPidOutput + leftFeedForward, rightPidOutput + rightFeedForward);
    }

    private double calculatePidOutput(int targetPosition, int currentPosition) {
        return controller.calculate(targetPosition, currentPosition);
    }

    private double calculateFeedForward(int targetPosition) {
        return Math.cos(Math.toRadians(targetPosition / TICKS_PER_DEGREE)) * FEED_FORWARD_CONSTANT;
    }

    public boolean isAtTargetPosition() {
        int leftPosition = leftMotor.getCurrentPosition();
        int rightPosition = rightMotor.getCurrentPosition();
        return leftPosition == targetPosition && rightPosition == targetPosition;
    }
}