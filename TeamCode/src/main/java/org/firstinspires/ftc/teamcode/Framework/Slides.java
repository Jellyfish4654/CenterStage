package org.firstinspires.ftc.teamcode.Framework;

import com.arcrobotics.ftclib.controller.PIDController;
import com.arcrobotics.ftclib.trajectory.TrapezoidProfile;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.util.ElapsedTime;

public class Slides {
    private DcMotorEx slideMotorLeft;
    private DcMotorEx slideMotorRight;
    private PIDController leftController;
    private PIDController rightController;

    // Separate motion profiles for each slide
    private TrapezoidProfile leftProfile;
    private TrapezoidProfile rightProfile;
    private TrapezoidProfile.Constraints leftConstraints;
    private TrapezoidProfile.Constraints rightConstraints;

    private ElapsedTime timer;
    private int targetPositionLeft;
    private int targetPositionRight;
    private int lowerThreshold = -10;
    private int upperThreshold = 3000;

    // PID Constants
    private double lP = 2.0;
    private double lI = 0;
    private double lD = 0.02;
    private double rP = 2.1;
    private double rI = 0;
    private double rD = 0.021;
    double maxVelocity = 1800;
    double maxAcceleration = 23886;

    public Slides(DcMotorEx slideMotorLeft, DcMotorEx slideMotorRight) {
        this.slideMotorLeft = slideMotorLeft;
        this.slideMotorRight = slideMotorRight;
        this.leftController = new PIDController(lP, lI, lD);
        this.rightController = new PIDController(rP, rI, rD);

        // Initialize separate constraints for each slide
        this.leftConstraints = new TrapezoidProfile.Constraints(maxVelocity, maxAcceleration);
        this.rightConstraints = new TrapezoidProfile.Constraints(maxVelocity, maxAcceleration);

        this.timer = new ElapsedTime();
        this.leftProfile = new TrapezoidProfile(leftConstraints, new TrapezoidProfile.State(0, 0));
        this.rightProfile = new TrapezoidProfile(rightConstraints, new TrapezoidProfile.State(0, 0));
    }

    public void update() {
        double time = timer.seconds();
        TrapezoidProfile.State leftGoal = leftProfile.calculate(time);
        TrapezoidProfile.State rightGoal = rightProfile.calculate(time);

        leftControl(leftGoal.position, leftGoal.velocity);
        rightControl(rightGoal.position, rightGoal.velocity);
    }   

    private void leftControl(double targetPosition, double targetVelocity){
        this.leftController.setPID(lP, lI, lD);
        int position = slideMotorLeft.getCurrentPosition();
        double leftPIDOutput = leftController.calculate(position, targetPosition);

        if (position < lowerThreshold && leftPIDOutput < 0) {
            leftPIDOutput = 0;
        }
        if (position > upperThreshold && leftPIDOutput > 0) {
            leftPIDOutput = 0;
        }

        slideMotorLeft.setVelocity(leftPIDOutput);
    }

    private void rightControl(double targetPosition, double targetVelocity){
        this.rightController.setPID(rP, rI, rD);
        int position = slideMotorRight.getCurrentPosition();
        double rightPIDOutput = rightController.calculate(position, targetPosition);

        if (position < lowerThreshold && rightPIDOutput < 0) {
            rightPIDOutput = 0;
        }
        if (position > upperThreshold && rightPIDOutput > 0) {
            rightPIDOutput = 0;
        }
        slideMotorRight.setVelocity(rightPIDOutput);
    }

    public void setTargetPosition(int targetPosition) {
        this.targetPositionLeft = targetPosition;
        this.targetPositionRight = targetPosition;
        int currentPositionLeft = slideMotorLeft.getCurrentPosition();
        int currentPositionRight = slideMotorRight.getCurrentPosition();

        this.leftProfile = new TrapezoidProfile(leftConstraints,
                new TrapezoidProfile.State(targetPositionLeft, 0),
                new TrapezoidProfile.State(currentPositionLeft, 0));

        this.rightProfile = new TrapezoidProfile(rightConstraints,
                new TrapezoidProfile.State(targetPositionRight, 0),
                new TrapezoidProfile.State(currentPositionRight, 0));
        timer.reset();
    }

    public void setGain(double gain){
        this.rP = gain;
    }

    public int getTargetPositionLeft() {
        return targetPositionLeft;
    }

    public int getTargetPositionRight() {
        return targetPositionRight;
    }
}
