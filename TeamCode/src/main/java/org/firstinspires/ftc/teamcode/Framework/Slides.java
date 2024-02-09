package org.firstinspires.ftc.teamcode.Framework;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.arcrobotics.ftclib.controller.PIDController;
import com.arcrobotics.ftclib.trajectory.TrapezoidProfile;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.util.ElapsedTime;

public class Slides
{
	private DcMotorEx slideMotorLeft;
	private DcMotorEx slideMotorRight;
	private PIDController leftController;
	private PIDController rightController;
	private TrapezoidProfile leftProfile;
	private TrapezoidProfile rightProfile;
	private TrapezoidProfile.Constraints leftConstraints;
	private TrapezoidProfile.Constraints rightConstraints;
	private ElapsedTime timer;
	private int targetPositionLeft;
	private int targetPositionRight;
	private int lowerThreshold = -10;
	private int upperThreshold = 3000;
	private double lP = 2.04;
	private double lI = 0;
	private double lD = 0.0204;
	private double rP = 2.1;
	private double rI = 0;
	private double rD = 0.021;
	private double maxVelocity = 1800;
	private double maxAcceleration = 23886;
	private double rightPIDOutput;
	private double leftPIDOutput;


	public Slides(DcMotorEx slideMotorLeft, DcMotorEx slideMotorRight)
	{
		this.slideMotorLeft = slideMotorLeft;
		this.slideMotorRight = slideMotorRight;
		this.leftController = new PIDController(lP, lI, lD);
		this.rightController = new PIDController(rP, rI, rD);
		this.leftConstraints = new TrapezoidProfile.Constraints(maxVelocity, maxAcceleration);
		this.rightConstraints = new TrapezoidProfile.Constraints(maxVelocity, maxAcceleration);
		this.timer = new ElapsedTime();
		this.leftProfile = new TrapezoidProfile(leftConstraints, new TrapezoidProfile.State(0, 0));
		this.rightProfile = new TrapezoidProfile(rightConstraints, new TrapezoidProfile.State(0, 0));

	}

	public void update()
	{
		double time = timer.seconds();
		TrapezoidProfile.State leftGoal = leftProfile.calculate(time);
		TrapezoidProfile.State rightGoal = rightProfile.calculate(time);
		leftControl(leftGoal.position, leftGoal.velocity);
		rightControl(rightGoal.position, rightGoal.velocity);
	}

	private void leftControl(double targetPosition, double targetVelocity)
	{
		this.leftController.setPID(lP, lI, lD);
		int position = slideMotorLeft.getCurrentPosition();
		this.leftPIDOutput = leftController.calculate(position, targetPosition);

		if (position < lowerThreshold && this.leftPIDOutput < 0)
		{
			this.leftPIDOutput = 0;
		}
		if (position > upperThreshold && this.leftPIDOutput > 0)
		{
			this.leftPIDOutput = 0;
		}
		if (Math.abs(this.leftPIDOutput) < 10)
		{
			this.leftPIDOutput = 0;
		}
		slideMotorLeft.setVelocity(this.leftPIDOutput);
	}

	private void rightControl(double targetPosition, double targetVelocity)
	{
		this.rightController.setPID(rP, rI, rD);
		int position = slideMotorRight.getCurrentPosition();
		this.rightPIDOutput = rightController.calculate(position, targetPosition);

		if (position < lowerThreshold && this.rightPIDOutput < 0)
		{
			this.rightPIDOutput = 0;
		}
		if (position > upperThreshold && this.rightPIDOutput > 0)
		{
			this.rightPIDOutput = 0;
		}
		if (Math.abs(this.rightPIDOutput) < 10)
		{
			this.rightPIDOutput = 0;
		}
		slideMotorRight.setVelocity(this.rightPIDOutput);
	}

	public void setTargetPosition(int targetPosition)
	{
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

	public void setGain(double gain)
	{
		this.rP = gain;
	}

	public double getLeftPIDOutput()
	{
		return leftPIDOutput;
	}

	public double getRightPIDOutput()
	{
		return rightPIDOutput;
	}

	public boolean slideCheck()
	{
		return leftPIDOutput > 25 && rightPIDOutput > 25;
	}

	public int getTargetPositionLeft()
	{
		return targetPositionLeft;
	}

	public int getTargetPositionRight()
	{
		return targetPositionRight;
	}

	public class SlidesUp1 implements Action
	{
		@Override
		public boolean run(@NonNull TelemetryPacket telemetryPacket)
		{
			setTargetPosition(1000);
			if (slideCheck())
			{
				return true;
			}
			else
			{
				return false;
			}
		}
	}

	public class SlidesUp2 implements Action
	{
		@Override
		public boolean run(@NonNull TelemetryPacket telemetryPacket)
		{
			setTargetPosition(2000);
			if (slideCheck())
			{
				return true;
			}
			else
			{
				return false;
			}
		}
	}

	public class SlidesUp3 implements Action
	{
		@Override
		public boolean run(@NonNull TelemetryPacket telemetryPacket)
		{
			setTargetPosition(3000);
			if (slideCheck())
			{
				return true;
			}
			else
			{
				return false;
			}
		}
	}

	public class SlidesDown0 implements Action
	{
		@Override
		public boolean run(@NonNull TelemetryPacket telemetryPacket)
		{
			setTargetPosition(0);
			if (slideCheck())
			{
				return true;
			}
			else
			{
				return false;
			}
		}
	}
}