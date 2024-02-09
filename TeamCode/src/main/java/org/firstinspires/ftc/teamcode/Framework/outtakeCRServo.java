package org.firstinspires.ftc.teamcode.Framework;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.util.ElapsedTime;

public class outtakeCRServo
{
	private final CRServo wheelServo;
	private ElapsedTime timer;
	private ServoState currentState;

	private static final double FORWARD_TIME = 0.5;
	private static final double BACKWARD_TIME = 2.0;

	private enum ServoState
	{
		FORWARD, BACKWARD, STOPPED
	}

	public outtakeCRServo(CRServo wheelServo)
	{
		this.wheelServo = wheelServo;
		this.timer = new ElapsedTime();
		this.currentState = ServoState.STOPPED;
	}

	public void startMovingForward()
	{
		timer.reset();
		currentState = ServoState.FORWARD;
	}

	public void startMovingBackward()
	{
		timer.reset();
		currentState = ServoState.BACKWARD;
	}

	public void update()
	{
		switch (currentState)
		{
			case FORWARD:
				if (timer.seconds() < FORWARD_TIME)
				{
					wheelServo.setPower(1);
				}
				else
				{
					stopServo();
				}
				break;
			case BACKWARD:
				if (timer.seconds() < BACKWARD_TIME)
				{
					wheelServo.setPower(-1);
				}
				else
				{
					stopServo();
				}
				break;
			case STOPPED:
				break;
		}
	}

	private void stopServo()
	{
		wheelServo.setPower(0);
		currentState = ServoState.STOPPED;
	}

	public class CRMoveForwardFor1Second implements Action
	{
		private boolean initialized = false;

		@Override
		public boolean run(@NonNull TelemetryPacket telemetryPacket)
		{
			if (!initialized)
			{
				wheelServo.setPower(1);
				timer.reset();
				initialized = true;
			}

			if (timer.seconds() > 1)
			{
				stopServo();
				return false;
			}
			else
			{
				return true;
			}
		}
	}
}