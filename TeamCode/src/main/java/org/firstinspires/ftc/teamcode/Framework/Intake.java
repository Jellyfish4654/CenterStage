package org.firstinspires.ftc.teamcode.Framework;

import com.arcrobotics.ftclib.controller.PIDController;
import com.arcrobotics.ftclib.trajectory.TrapezoidProfile;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

public class Intake {
    private final DcMotorEx intakeMotor;
    private final Servo intakeServo;
    private final PIDController intakeController;
    private final TrapezoidProfile.Constraints constraints;

    private ElapsedTime servoTimer;
    private ElapsedTime motorTimer;

    private TrapezoidProfile intakeProfile;
    private int targetPosition;

    private double P = 2.05;
    private double I = 0;
    private double D = 0.0205;
    private double maxVelocity = 2000 * 1.0;
    private double maxAcceleration = 15000 * 1.0;
    private double PIDOutput;

    public Intake(DcMotorEx intakeMotor, Servo intakeServo) {
        this.intakeMotor = intakeMotor;
        this.intakeServo = intakeServo;
        this.servoTimer = new ElapsedTime();
        this.motorTimer = new ElapsedTime();

        this.intakeController = new PIDController(P, I, D);
        this.constraints = new TrapezoidProfile.Constraints(maxVelocity, maxAcceleration);
        this.intakeProfile = new TrapezoidProfile(constraints, new TrapezoidProfile.State(0, 0));
    }

    public void update() {
        double time = motorTimer.seconds();
        TrapezoidProfile.State goal = intakeProfile.calculate(time);
        control(goal.position, goal.velocity);
    }

    private void control(double targetPosition, double targetVelocity){
        this.intakeController.setPID(P, I, D);
        int position = intakeMotor.getCurrentPosition();
        this.PIDOutput = intakeController.calculate(position, targetPosition);
        intakeMotor.setVelocity(this.PIDOutput);
    }

    public void setTargetPosition(int newPosition) {
        this.targetPosition = newPosition;
        int currentPosition = intakeMotor.getCurrentPosition();
        this.intakeProfile = new TrapezoidProfile(constraints,
                new TrapezoidProfile.State(targetPosition, 0),
                new TrapezoidProfile.State(currentPosition, 0));
        motorTimer.reset();
    }

    public void moveForward(){
        int target = intakeMotor.getCurrentPosition() + 5000;
        setTargetPosition(target);
    }

    public void moveBackward(){
        int target = intakeMotor.getCurrentPosition() - 5000;
        setTargetPosition(target);
    }

    public void setGain(double p){
        this.P = p;
    }

    public int getTargetPosition() {
        return targetPosition;
    }

    public void servoIntakeInit() {
        intakeServo.setPosition(0.385);
    }

    public void servoIntakeOut() {
        intakeServo.setPosition(0.1);
        servoTimer.reset();
    }

    public void servoIntakeDrone(){
        if(servoTimer.seconds() >= 0.75) {
            intakeServo.setPosition(0.935);
        }
    }

    public double getPIDOutput() {
        return PIDOutput;
    }

    public boolean checkIntake() {
        return PIDOutput > 0;
    }
}
