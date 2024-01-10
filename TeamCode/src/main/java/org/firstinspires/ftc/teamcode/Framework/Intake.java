package org.firstinspires.ftc.teamcode.Framework;

import com.arcrobotics.ftclib.controller.PIDController;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

public class Intake {
    private final DcMotorEx intakeMotor;
    private final Servo intakeServo;
    private final PIDController intakeController;

    private int targetPosition;

    private double P = 0.0085;
    private double I = 0;
    private double D = 0.00009;
    private double PIDOutput;

    public Intake(DcMotorEx intakeMotor, Servo intakeServo) {
        this.intakeMotor = intakeMotor;
        this.intakeServo = intakeServo;

        this.intakeController = new PIDController(P, I, D);
    }

    public void update() {
        control(targetPosition);
    }

    private void control(double targetPosition) {
        this.intakeController.setPID(P, I, D);
        int position = intakeMotor.getCurrentPosition();
        this.PIDOutput = intakeController.calculate(position, targetPosition);
        double power = this.PIDOutput;
        if(Math.abs(power)>0.065) {
            intakeMotor.setPower(power);
        }
    }

    public void setTargetPosition(int newPosition) {
        this.targetPosition = newPosition;
    }

    public void moveForward(){
        setTargetPosition(intakeMotor.getCurrentPosition() + 5000);
    }

    public void moveBackward(){
        setTargetPosition(intakeMotor.getCurrentPosition() - 5000);
    }
    public void eject(){
        setTargetPosition(intakeMotor.getCurrentPosition() - 70);
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
    }

    public void servoIntakeDrone(){
        intakeServo.setPosition(0.935);
    }

    public double getPIDOutput() {
        return PIDOutput;
    }

    public boolean checkIntake() {
        return PIDOutput > 0;
    }
}
