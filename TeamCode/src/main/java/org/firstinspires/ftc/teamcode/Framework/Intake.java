package org.firstinspires.ftc.teamcode.Framework;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.arcrobotics.ftclib.controller.PIDController;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;

public class Intake
{
	private final DcMotorEx intakeMotor;
	private final Servo intakeServo;
	private final PIDController intakeController;

	private int targetPosition;

	private double P = 0.0015;
	private double I = 0;
	private double D = 0.00002;
	private double PIDOutput;


	public Intake(DcMotorEx intakeMotor, Servo intakeServo)
	{
		this.intakeMotor = intakeMotor;
		this.intakeServo = intakeServo;

		this.intakeController = new PIDController(P, I, D);
	}

	public void update()
	{
		control(targetPosition);
	}

	private void control(double targetPosition)
	{
		this.intakeController.setPID(P, I, D);
		int position = intakeMotor.getCurrentPosition();
		this.PIDOutput = intakeController.calculate(position, targetPosition);
		double power = this.PIDOutput;
		if (Math.abs(power) > 0.065)
		{
			intakeMotor.setPower(power);
		}
	}

	public void setTargetPosition(int newPosition)
	{
		this.targetPosition = newPosition;
	}

	public void moveForward()
	{
		setTargetPosition(intakeMotor.getCurrentPosition() + 5000);
	}

	public void moveBackward()
	{
		setTargetPosition(intakeMotor.getCurrentPosition() - 5000);
	}

	public void eject()
	{
		setTargetPosition(intakeMotor.getCurrentPosition() - 70);
	}

	public void setGain(double p)
	{
		this.P = p;
	}

	public int getTargetPosition()
	{
		return targetPosition;
	}

	public void servoIntakeInit()
	{
		intakeServo.setPosition(0.165);
	}

	public void servoIntakeRelease()
	{
		intakeServo.setPosition(0);
	}

	public void servoIntakeDrone()
	{
		intakeServo.setPosition(0.825);
	}

	public double getPIDOutput()
	{
		return PIDOutput;
	}

	public boolean checkIntake()
	{
		return PIDOutput > 0;
	}

	public class IntakeServoRelease implements Action
	{
		@Override
		public boolean run(@NonNull TelemetryPacket telemetryPacket)
		{
			servoIntakeRelease();
			return false;
		}
	}

	public class IntakeServoDrone implements Action
	{
		@Override
		public boolean run(@NonNull TelemetryPacket telemetryPacket)
		{
			servoIntakeDrone();
			return false;
		}
	}

	public class IntakeMotor implements Action
	{
		@Override
		public boolean run(@NonNull TelemetryPacket telemetryPacket)
		{
			moveForward();
			return checkIntake();
		}
	}
}
