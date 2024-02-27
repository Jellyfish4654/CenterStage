package org.firstinspires.ftc.teamcode.Framework;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;

public class Intake
{
	private final DcMotorEx intakeMotor;
	private final Servo intakeServo;

	private int targetPosition;

	public Intake(DcMotorEx intakeMotor, Servo intakeServo) {
		this.intakeMotor = intakeMotor;
		this.intakeServo = intakeServo;
	}

	public void update()
	{
		control(targetPosition);
	}

	private void control(int targetPosition) {
		int position = intakeMotor.getCurrentPosition();
		final int THRESHOLD = 30;
		int positionDifference = position - targetPosition;

		if (Math.abs(positionDifference) > THRESHOLD) {
			if (positionDifference < 0) {
				intakeMotor.setPower(1);
			} else {
				intakeMotor.setPower(-1);
			}
		} else {
			intakeMotor.setPower(0);
		}
	}


	public void setTargetPosition(int newPosition)
	{
		this.targetPosition = newPosition;
	}

	public void moveForward()
	{
		setTargetPosition(intakeMotor.getCurrentPosition() + 1000);
	}

	public void moveBackward()
	{
		setTargetPosition(intakeMotor.getCurrentPosition() - 1000);
	}
	public int getTargetPosition()
	{
		return targetPosition;
	}

	public void servoIntakeInit()
	{
		intakeServo.setPosition(0.255);
	}

	public void servoIntakeRelease()
	{
		intakeServo.setPosition(0.75);
	}

	public void servoIntakeDrone()
	{
		intakeServo.setPosition(0.825);
	}

	public boolean checkIntake() {
		return Math.abs(targetPosition - intakeMotor.getCurrentPosition()) > 50;
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

	public class IntakeMotorForward implements Action
	{
		@Override
		public boolean run(@NonNull TelemetryPacket telemetryPacket)
		{
			moveForward();
			return checkIntake();
		}
	}
	public class IntakeMotorBackward implements Action
	{
		@Override
		public boolean run(@NonNull TelemetryPacket telemetryPacket)
		{
			moveBackward();
			return checkIntake();
		}
	}
}
