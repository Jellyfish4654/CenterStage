package org.firstinspires.ftc.teamcode.Framework;

import com.ThermalEquilibrium.homeostasis.Controllers.Feedback.BasicPID;
import com.ThermalEquilibrium.homeostasis.Parameters.PIDCoefficients;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Framework.misc.MotionProfile;

public class Slides {
    private final DcMotorEx slideMotorLeft;
    private final DcMotorEx slideMotorRight;
    private BasicPID leftPIDController;
    private BasicPID rightPIDController;
    private ElapsedTime timer;
    private MotionProfile leftProfile;
    private MotionProfile rightProfile;

    // Constants for motion profiles and PID
    private static final double MAX_ACCELERATION = 1.0;
    private static final double MAX_VELOCITY = 1.0;
    private static final double MAX_DECELERATION = 1.0;
    private int targetPosition = 0;
    public  double KP = 0;
    public  double KI = 0;
    public double KD = 0;
    PIDCoefficients coefficients;

    public Slides(DcMotorEx slideMotorLeft, DcMotorEx slideMotorRight) {
        this.slideMotorLeft = slideMotorLeft;
        this.slideMotorRight = slideMotorRight;
        this.coefficients = new PIDCoefficients(KP, KI, KD);
        // Create PID controllers for each motor
        this.leftPIDController = new BasicPID(coefficients);
        this.rightPIDController = new BasicPID(coefficients);
        this.timer = new ElapsedTime();
        initializeMotionProfiles();
    }

    public void update() {
        double elapsedTime = timer.seconds();

        // Update motion profiles
        MotionProfile.State leftState = leftProfile.calculate(elapsedTime);
        MotionProfile.State rightState = rightProfile.calculate(elapsedTime);

        // Calculate PID output for each motor
        double leftPIDOutput = leftPIDController.calculate(targetPosition, slideMotorLeft.getCurrentPosition());
        double rightPIDOutput = rightPIDController.calculate(targetPosition, slideMotorRight.getCurrentPosition());

        // Apply simple proportional control using the motion profile's target position
        double leftMotorPower = (leftState.x - slideMotorLeft.getCurrentPosition()) * leftPIDOutput;
        double rightMotorPower = (rightState.x - slideMotorRight.getCurrentPosition()) * rightPIDOutput;

        // Set motor power
        slideMotorLeft.setPower(leftMotorPower);
        slideMotorRight.setPower(rightMotorPower);

        // Todo - Limit switches and synchronization
    }

    private void initializeMotionProfiles() {
        // Set initial target as current position
        int initialPosition = slideMotorLeft.getCurrentPosition();
        this.leftProfile = new MotionProfile(initialPosition, initialPosition, new MotionProfile.Constraints(MAX_ACCELERATION, MAX_VELOCITY, MAX_DECELERATION));
        initialPosition = slideMotorRight.getCurrentPosition();
        this.rightProfile = new MotionProfile(initialPosition, initialPosition, new MotionProfile.Constraints(MAX_ACCELERATION, MAX_VELOCITY, MAX_DECELERATION));
    }
    public void setTargetPosition(int target) {
        this.targetPosition = target;
        // Reset the motion profiles with the current positions and the new target
        this.leftProfile = new MotionProfile(slideMotorLeft.getCurrentPosition(), target, new MotionProfile.Constraints(MAX_ACCELERATION, MAX_VELOCITY, MAX_DECELERATION));
        this.rightProfile = new MotionProfile(slideMotorRight.getCurrentPosition(), target, new MotionProfile.Constraints(MAX_ACCELERATION, MAX_VELOCITY, MAX_DECELERATION));
        timer.reset();
    }
}
