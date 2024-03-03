package org.firstinspires.ftc.teamcode.Framework;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;

public class Intake
{
	private final DcMotorEx intakeMotor;
	private final Servo intakeServo;
	private final CRServo wheelServo;
	private int targetPosition;

	public Intake(DcMotorEx intakeMotor, Servo intakeServo, CRServo wheelServo) {
		this.wheelServo = wheelServo;
		this.intakeMotor = intakeMotor;
		this.intakeServo = intakeServo;
		this.targetPosition = intakeMotor.getCurrentPosition();
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
				wheelServo.setPower(-1);
			} else {
				intakeMotor.setPower(-1);
			}
		} else {
			intakeMotor.setPower(0);
			wheelServo.setPower(0);
		}
	}


	public void setTargetPosition(int newPosition)
	{
		this.targetPosition = newPosition;
	}

	public void moveForward()
	{
		setTargetPosition(intakeMotor.getCurrentPosition() + 1750);
	}
	public void moveForwardMore()
	{
		setTargetPosition(intakeMotor.getCurrentPosition() + 5750);
	}
	public void moveBackward()
	{
		setTargetPosition(intakeMotor.getCurrentPosition() - 2500);
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
		intakeServo.setPosition(0.7);
	}

	public void servoIntakeDrone()
	{
		intakeServo.setPosition(0.825);
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
		private boolean initialized = false;
		@Override
		public boolean run(@NonNull TelemetryPacket telemetryPacket) {
			if (!initialized) {
				moveForward();
				initialized = true;
			}
			update();
			return getTargetPosition() > intakeMotor.getCurrentPosition();
		}
	}
	public class IntakeMotorForwardMore implements Action
	{
		private boolean initialized = false;
		@Override
		public boolean run(@NonNull TelemetryPacket telemetryPacket) {
			if (!initialized) {
				moveForwardMore();
				initialized = true;
			}
			update();
			return getTargetPosition() > intakeMotor.getCurrentPosition();
		}
	}
	public class IntakeMotorBackward implements Action
	{
		private boolean initialized = false;
		@Override
		public boolean run(@NonNull TelemetryPacket telemetryPacket) {
			if (!initialized) {
				moveBackward();
				initialized = true;
			}
			update();
			return getTargetPosition() < intakeMotor.getCurrentPosition();
		}
	}
}
