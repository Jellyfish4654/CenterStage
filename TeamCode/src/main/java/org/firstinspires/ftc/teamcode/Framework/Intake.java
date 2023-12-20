package org.firstinspires.ftc.teamcode.Framework;

import com.arcrobotics.ftclib.controller.PIDController;
import com.arcrobotics.ftclib.trajectory.TrapezoidProfile;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

public class Intake {
    public final DcMotorEx intakeMotor;
    private final Servo intakeServo;
    private ElapsedTime servoTimer;
    private PIDController controller;
    private TrapezoidProfile profile;
    private TrapezoidProfile.Constraints constraints;
    private ElapsedTime motorTimer;
    private double P = 0.0125;
    private double I = 0.00125;
    private double D = 0.0001;
    double maxVelocity = 1800 * 1.0;
    double maxAcceleration = 23886 * 1.0;

    public Intake(DcMotorEx intakeMotor, Servo intakeServo) {
        this.intakeMotor = intakeMotor;
        this.intakeServo = intakeServo;
        this.servoTimer = new ElapsedTime();
        this.controller = new PIDController(P, I, D);
        this.constraints = new TrapezoidProfile.Constraints(maxVelocity, maxAcceleration);
        this.motorTimer = new ElapsedTime();
        this.profile = new TrapezoidProfile(constraints, new TrapezoidProfile.State(0, 0));
    }

    public void update() {
        double time = motorTimer.seconds();
        TrapezoidProfile.State goal = profile.calculate(time);
        control(goal.position, goal.velocity);
    }

    private void control(double targetPosition, double targetVelocity){
        this.controller.setPID(P, I, D);
        int position = intakeMotor.getCurrentPosition();
        double PIDOutput = controller.calculate(position, targetPosition);
        intakeMotor.setPower(PIDOutput);
    }

    public void setTargetPosition(int targetPosition) {
        int currentPosition = intakeMotor.getCurrentPosition();
        this.profile = new TrapezoidProfile(constraints,
                new TrapezoidProfile.State(targetPosition, 0),
                new TrapezoidProfile.State(currentPosition, 0));
        motorTimer.reset();
    }

    public void setGain(double p){
        this.P = p;
    }


    public void servoIntakeInit() {
        intakeServo.setPosition(0.235);
    }
    public void servoIntakeOut() {
        intakeServo.setPosition(0.115);
        servoTimer.reset();
    }
    public void servoIntakeDrone(){
        if(servoTimer.seconds() >= 0.75) {
            intakeServo.setPosition(0.97);
        }
    }
}
