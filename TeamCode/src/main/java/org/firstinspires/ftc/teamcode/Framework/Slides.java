package org.firstinspires.ftc.teamcode.Framework;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.CancelableProfile;
import com.acmerobotics.roadrunner.DisplacementProfile;
import com.acmerobotics.roadrunner.DualNum;
import com.acmerobotics.roadrunner.Profiles;
import com.acmerobotics.roadrunner.Time;
import com.arcrobotics.ftclib.controller.PIDController;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.util.ElapsedTime;

public class Slides
{
	private DcMotorEx slideMotorLeft;
	private DcMotorEx slideMotorRight;
	private PIDController leftController;
	private PIDController rightController;
	private CancelableProfile leftProfile;
	private CancelableProfile rightProfile;
	private ElapsedTime timer;
	private int targetPositionLeft;
	private int targetPositionRight;
	private int lowerThreshold = -10;
	private int upperThreshold = 3000;
	public static double lP = 2.04;
	public static double lI = 0;
	public static double lD = 0.0204;
	public static double rP = 2.1;
	public static double rI = 0;
	public static double rD = 0.021;
	public static double maxVelocity = 1800;
	public static double maxAcceleration = 23886;
	private double rightPIDOutput;
	private double leftPIDOutput;


	public Slides(DcMotorEx slideMotorLeft, DcMotorEx slideMotorRight) {
		this.slideMotorLeft = slideMotorLeft;
		this.slideMotorRight = slideMotorRight;
		this.leftController = new PIDController(lP, lI, lD);
		this.rightController = new PIDController(rP, rI, rD);
		this.timer = new ElapsedTime();
		this.leftProfile = Profiles.constantProfile(0.0,   0.0, maxVelocity, -maxAcceleration, maxAcceleration);
		this.rightProfile = Profiles.constantProfile(0.0,   0.0, maxVelocity, -maxAcceleration, maxAcceleration);
	}

	public void update() {
		double currentDisplacementLeft = slideMotorLeft.getCurrentPosition();
		double currentDisplacementRight = slideMotorRight.getCurrentPosition();

		// Cancel the profile at the current displacement
		DisplacementProfile leftDisplacementProfile = leftProfile.cancel(currentDisplacementLeft);
		DisplacementProfile rightDisplacementProfile = rightProfile.cancel(currentDisplacementRight);

		// Retrieve the target position and velocity from the displacement profile
		DualNum<Time> leftProfileData = leftDisplacementProfile.get(currentDisplacementLeft);
		DualNum<Time> rightProfileData = rightDisplacementProfile.get(currentDisplacementRight);

		double leftTargetPosition = leftProfileData.get(0);
		double leftTargetVelocity = leftProfileData.get(1);
		double rightTargetPosition = rightProfileData.get(0);
		double rightTargetVelocity = rightProfileData.get(1);

		leftControl(leftTargetPosition, leftTargetVelocity);
		rightControl(rightTargetPosition, rightTargetVelocity);
	}
	public void setTargetPosition(int targetPosition) {
		this.targetPositionLeft = targetPosition;
		this.targetPositionRight = targetPosition;
		// Update the profiles with the new target positions
		this.leftProfile = Profiles.constantProfile(
				(double) targetPositionLeft,
				slideMotorLeft.getVelocity(),
				maxVelocity,
				-maxAcceleration,
				maxAcceleration
		);
		this.rightProfile = Profiles.constantProfile(
				(double) targetPositionRight,
				slideMotorRight.getVelocity(),
				maxVelocity,
				-maxAcceleration,
				maxAcceleration
		);
		timer.reset();
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
		if (Math.abs(this.leftPIDOutput) < 18)
		{
			this.leftPIDOutput = 0;
		}
		slideMotorLeft.setPower(this.leftPIDOutput);
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
		if (Math.abs(this.rightPIDOutput) < 18)
		{
			this.rightPIDOutput = 0;
		}
		slideMotorRight.setPower(this.rightPIDOutput);
	}
	public void setlPGain(double p)
	{
		this.lP = p;
	}

	public void setrPGain(double p)
	{
		this.rP = p;
	}

	public void setlDGain(double d)
	{
		this.lD = d;
	}
	public void setrDGain(double d)
	{
		this.rD = d;
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
		private boolean initialized = false;
		@Override
		public boolean run(@NonNull TelemetryPacket telemetryPacket)

		{
			if (!initialized) {
				setTargetPosition(275);
				initialized = true;
			}
			update();
			int currentPositionLeft = slideMotorLeft.getCurrentPosition();
			int currentPositionRight = slideMotorRight.getCurrentPosition();
			int errorMargin = 25;

			boolean leftInPosition = Math.abs(currentPositionLeft - targetPositionLeft) >= errorMargin;
			boolean rightInPosition = Math.abs(currentPositionRight - targetPositionRight) >= errorMargin;
			return leftInPosition && rightInPosition;
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
//
//public class Slides
//{
//	private DcMotorEx slideMotorLeft;
//	private DcMotorEx slideMotorRight;
//	private PIDController leftController;
//	private PIDController rightController;
//	private TrapezoidProfile leftProfile;
//	private TrapezoidProfile rightProfile;
//	private TrapezoidProfile.Constraints leftConstraints;
//	private TrapezoidProfile.Constraints rightConstraints;
//	private ElapsedTime timer;
//	private int targetPositionLeft;
//	private int targetPositionRight;
//	private int lowerThreshold = -10;
//	private int upperThreshold = 3000;
//	private double lP = 2.04;
//	private double lI = 0;
//	private double lD = 0.0204;
//	private double rP = 2.1;
//	private double rI = 0;
//	private double rD = 0.021;
//	private double maxVelocity = 1800;
//	private double maxAcceleration = 23886;
//	private double rightPIDOutput;
//	private double leftPIDOutput;
//
//
//	public Slides(DcMotorEx slideMotorLeft, DcMotorEx slideMotorRight)
//	{
//		this.slideMotorLeft = slideMotorLeft;
//		this.slideMotorRight = slideMotorRight;
//		this.leftController = new PIDController(lP, lI, lD);
//		this.rightController = new PIDController(rP, rI, rD);
//		this.leftConstraints = new TrapezoidProfile.Constraints(maxVelocity, maxAcceleration);
//		this.rightConstraints = new TrapezoidProfile.Constraints(maxVelocity, maxAcceleration);
//		this.timer = new ElapsedTime();
//		this.leftProfile = new TrapezoidProfile(leftConstraints, new TrapezoidProfile.State(0, 0));
//		this.rightProfile = new TrapezoidProfile(rightConstraints, new TrapezoidProfile.State(0, 0));
//
//	}
//
//	public void update()
//	{
//		double time = timer.seconds();
//		TrapezoidProfile.State leftGoal = leftProfile.calculate(time);
//		TrapezoidProfile.State rightGoal = rightProfile.calculate(time);
//		leftControl(leftGoal.position, leftGoal.velocity);
//		rightControl(rightGoal.position, rightGoal.velocity);
//	}
//
//	private void leftControl(double targetPosition, double targetVelocity)
//	{
//		this.leftController.setPID(lP, lI, lD);
//		int position = slideMotorLeft.getCurrentPosition();
//		this.leftPIDOutput = leftController.calculate(position, targetPosition);
//
//		if (position < lowerThreshold && this.leftPIDOutput < 0)
//		{
//			this.leftPIDOutput = 0;
//		}
//		if (position > upperThreshold && this.leftPIDOutput > 0)
//		{
//			this.leftPIDOutput = 0;
//		}
//		if (Math.abs(this.leftPIDOutput) < 18)
//		{
//			this.leftPIDOutput = 0;
//		}
//		slideMotorLeft.setVelocity(this.leftPIDOutput);
//	}
//
//	private void rightControl(double targetPosition, double targetVelocity)
//	{
//		this.rightController.setPID(rP, rI, rD);
//		int position = slideMotorRight.getCurrentPosition();
//		this.rightPIDOutput = rightController.calculate(position, targetPosition);
//
//		if (position < lowerThreshold && this.rightPIDOutput < 0)
//		{
//			this.rightPIDOutput = 0;
//		}
//		if (position > upperThreshold && this.rightPIDOutput > 0)
//		{
//			this.rightPIDOutput = 0;
//		}
//		if (Math.abs(this.rightPIDOutput) < 18)
//		{
//			this.rightPIDOutput = 0;
//		}
//		slideMotorRight.setVelocity(this.rightPIDOutput);
//	}
//
//	public void setTargetPosition(int targetPosition)
//	{
//		this.targetPositionLeft = targetPosition;
//		this.targetPositionRight = targetPosition;
//		int currentPositionLeft = slideMotorLeft.getCurrentPosition();
//		int currentPositionRight = slideMotorRight.getCurrentPosition();
//		this.leftProfile = new TrapezoidProfile(leftConstraints,
//				new TrapezoidProfile.State(targetPositionLeft, 0),
//				new TrapezoidProfile.State(currentPositionLeft, 0));
//		this.rightProfile = new TrapezoidProfile(rightConstraints,
//				new TrapezoidProfile.State(targetPositionRight, 0),
//				new TrapezoidProfile.State(currentPositionRight, 0));
//		timer.reset();
//	}
//
//	public void setGain(double gain)
//	{
//		this.rP = gain;
//	}
//
//	public double getLeftPIDOutput()
//	{
//		return leftPIDOutput;
//	}
//
//	public double getRightPIDOutput()
//	{
//		return rightPIDOutput;
//	}
//
//	public boolean slideCheck()
//	{
//		return leftPIDOutput > 25 && rightPIDOutput > 25;
//	}
//
//	public int getTargetPositionLeft()
//	{
//		return targetPositionLeft;
//	}
//
//	public int getTargetPositionRight()
//	{
//		return targetPositionRight;
//	}
//	public class SlidesUp1 implements Action
//	{
//		private boolean initialized = false;
//		@Override
//		public boolean run(@NonNull TelemetryPacket telemetryPacket)
//
//		{
//			if (!initialized) {
//				setTargetPosition(275);
//				initialized = true;
//			}
//			update();
//			int currentPositionLeft = slideMotorLeft.getCurrentPosition();
//			int currentPositionRight = slideMotorRight.getCurrentPosition();
//			int errorMargin = 25;
//
//			boolean leftInPosition = Math.abs(currentPositionLeft - targetPositionLeft) >= errorMargin;
//			boolean rightInPosition = Math.abs(currentPositionRight - targetPositionRight) >= errorMargin;
//			return leftInPosition && rightInPosition;
//		}
//	}
//
//	public class SlidesUp2 implements Action
//	{
//		@Override
//		public boolean run(@NonNull TelemetryPacket telemetryPacket)
//		{
//			setTargetPosition(2000);
//			if (slideCheck())
//			{
//				return true;
//			}
//			else
//			{
//				return false;
//			}
//		}
//	}
//
//	public class SlidesUp3 implements Action
//	{
//		@Override
//		public boolean run(@NonNull TelemetryPacket telemetryPacket)
//		{
//			setTargetPosition(3000);
//			if (slideCheck())
//			{
//				return true;
//			}
//			else
//			{
//				return false;
//			}
//		}
//	}
//
//	public class SlidesDown0 implements Action
//	{
//		@Override
//		public boolean run(@NonNull TelemetryPacket telemetryPacket)
//		{
//			setTargetPosition(0);
//			if (slideCheck())
//			{
//				return true;
//			}
//			else
//			{
//				return false;
//			}
//		}
//	}
//}