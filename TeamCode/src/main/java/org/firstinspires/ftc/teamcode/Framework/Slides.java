package org.firstinspires.ftc.teamcode.Framework;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.ThermalEquilibrium.homeostasis.Controllers.Feedback.BasicPID;
import com.ThermalEquilibrium.homeostasis.Parameters.PIDCoefficients;

public class Slides {

    private static final int SLIDE_LOWER_BOUND = 0;
    private static final int SLIDE_UPPER_BOUND = 3000;
    private static final double TICKS_PER_DEGREE = 145.1 / 360.0;
    private static final int EXTENDED_THRESHOLD = 500;
    private static final double KP = 0;
    private static final double KI = 0;
    private static final double KD = 0;
    private static final double FEED_FORWARD_CONSTANT = 0;

    private DcMotor leftMotor;
    private DcMotor rightMotor;
    private int targetPosition;
    private PIDCoefficients coefficients = new PIDCoefficients(KP, KI, KD);
    private BasicPID controller = new BasicPID(coefficients);

    public Slides(DcMotor leftMotor, DcMotor rightMotor) {
        this.leftMotor = leftMotor;
        this.rightMotor = rightMotor;
    }

    public void setTargetPosition(int target) {
        targetPosition = Math.max(SLIDE_LOWER_BOUND, Math.min(target, SLIDE_UPPER_BOUND));
    }

    public void moveSlides(double leftPower, double rightPower) {
        leftMotor.setPower(leftPower);
        rightMotor.setPower(rightPower);
    }

    public boolean slidesExtendedState() {
        return targetPosition > EXTENDED_THRESHOLD;
    }

    public void update() {
        int leftPosition = leftMotor.getCurrentPosition();
        int rightPosition = rightMotor.getCurrentPosition();

        double leftPidOutput = calculatePidOutput(targetPosition, leftPosition);
        double rightPidOutput = calculatePidOutput(targetPosition, rightPosition);
        double feedForward = calculateFeedForward(targetPosition);

        moveSlides(leftPidOutput + feedForward, rightPidOutput + feedForward);
    }

    private double calculatePidOutput(int targetPosition, int currentPosition) {
        return controller.calculate(targetPosition, currentPosition);
    }

    private double calculateFeedForward(int targetPosition) {
        return Math.cos(Math.toRadians(targetPosition / TICKS_PER_DEGREE)) * FEED_FORWARD_CONSTANT;
    }
}