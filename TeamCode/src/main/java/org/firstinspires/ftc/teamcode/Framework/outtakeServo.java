package org.firstinspires.ftc.teamcode.Framework;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.Servo;

public class outtakeServo
{
	private static final double OUTTAKE_INTAKE = 0.4021;
	private static final double OUTTAKE_DEPOSIT = 0.8145 ;

	private static final double ARM_INTAKE_POSITION = 0.1166;
	private static final double ARM_DEPOSIT_POSITION = 0.7591;

	private final Servo outakeLeftServo;
	private final Servo outakeRightServo;
	private final Servo armLeftServo;
	private final Servo armRightServo;

	double positionL = OUTTAKE_INTAKE;
	double positionR = OUTTAKE_INTAKE;
	double armPositionL = ARM_INTAKE_POSITION;
	double armPositionR = ARM_INTAKE_POSITION;

	public outtakeServo(Servo servo1, Servo servo2, Servo leftArm, Servo rightArm)
	{
		this.outakeLeftServo = servo1;
		this.outakeRightServo = servo2;
		this.armLeftServo = leftArm;
		this.armRightServo = rightArm;
		outakeLeftServo.setPosition(positionL);
		outakeRightServo.setPosition(positionR);
		armLeftServo.setPosition(armPositionL);
		armRightServo.setPosition(armPositionR);
	}

	public void boxOuttakeIntake()
	{
		positionL = OUTTAKE_INTAKE;
		positionR = OUTTAKE_INTAKE;
		outakeLeftServo.setPosition(positionL);
		outakeRightServo.setPosition(positionR);
	}

	public void boxOuttakeDeposit()
	{
		positionL = OUTTAKE_DEPOSIT;
		positionR = OUTTAKE_DEPOSIT;
		outakeLeftServo.setPosition(positionL);
		outakeRightServo.setPosition(positionR);
	}

	public void armOuttakeIntake()
	{
		armPositionL = ARM_INTAKE_POSITION;
		armPositionR = ARM_INTAKE_POSITION;
		armLeftServo.setPosition(armPositionL);
		armRightServo.setPosition(armPositionR);
	}

	public void armOuttakeDeposit()
	{
		armPositionL = ARM_DEPOSIT_POSITION;
		armPositionR = ARM_DEPOSIT_POSITION;
		armLeftServo.setPosition(armPositionL);
		armRightServo.setPosition(armPositionR);
	}

	public void setOutput()
	{
		outakeLeftServo.setPosition(positionL);
		outakeRightServo.setPosition(positionR);
		armLeftServo.setPosition(armPositionL);
		armRightServo.setPosition(armPositionR);
	}

	public class boxOuttakeDeposit implements Action
	{
		@Override
		public boolean run(@NonNull TelemetryPacket telemetryPacket)
		{
			boxOuttakeDeposit();
			return false;
		}
	}

	public class boxOuttakeIntake implements Action
	{
		@Override
		public boolean run(@NonNull TelemetryPacket telemetryPacket)
		{
			boxOuttakeIntake();
			return false;
		}
	}

	public class armOuttakeDeposit implements Action
	{
		@Override
		public boolean run(@NonNull TelemetryPacket telemetryPacket)
		{
			armOuttakeDeposit();
			return false;
		}
	}

	public class armOuttakeIntake implements Action
	{
		@Override
		public boolean run(@NonNull TelemetryPacket telemetryPacket)
		{
			armOuttakeIntake();
			return false;
		}
	}
}
