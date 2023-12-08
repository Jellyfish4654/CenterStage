package org.firstinspires.ftc.teamcode.Framework;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

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
    private double MAX_ACCELERATION = 999;
    private double MAX_VELOCITY = 999;
    private double MAX_DECELERATION = 999;
    private int targetPosition = 0;
    public double lKP = 0.00002;
        public double lKI = 0;
        public double lKD = 0;
//    public double lKP = 0.00002;
//    public double lKI = 0.0000000000000000000005;
//    public double lKD = 0.007;
    PIDCoefficients leftCoefficients;
    PIDCoefficients rightCoefficients;

    public Slides(DcMotorEx slideMotorLeft, DcMotorEx slideMotorRight) {
        this.slideMotorLeft = slideMotorLeft;
        this.slideMotorRight = slideMotorRight;
        this.leftCoefficients = new PIDCoefficients(lKP, lKI, lKD);
        // Create PID controllers for each motor
        this.leftPIDController = new BasicPID(leftCoefficients);
        this.rightPIDController = new BasicPID(leftCoefficients);
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
        slideMotorLeft.setPower(leftPIDOutput);
        slideMotorRight.setPower(rightPIDOutput);
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
