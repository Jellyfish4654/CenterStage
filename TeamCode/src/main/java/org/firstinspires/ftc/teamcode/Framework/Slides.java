package org.firstinspires.ftc.teamcode.Framework;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.arcrobotics.ftclib.controller.PIDController;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.VoltageSensor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Framework.Profiles.MotionProfile;
import org.firstinspires.ftc.teamcode.Framework.Profiles.MotionProfileGenerator;
import org.firstinspires.ftc.teamcode.Framework.Profiles.MotionState;

public class Slides {
	private DcMotorEx slideMotorLeft, slideMotorRight;
	private VoltageSensor voltageSensor;
	private PIDController leftController, rightController;
	private MotionProfile leftProfile, rightProfile;
	private ElapsedTime timer;
	private double kPLeft = 0.01, kILeft = 0, kDLeft = 0.0002, kFLeft = 0.008, kFLeft1 = 0.015, kFLeft2 = 0.04;
	private double kPRight = 0.01, kIRight = 0, kDRight = 0.0002, kFRight = 0.008, kFRight1 = 0.015, kFRight2 = 0.04;
	//	private double kPLeft = 0.01, kILeft = 0, kDLeft = 0.0002, kFLeft = 0.01;
//	private double kPRight = 0.01, kIRight = 0, kDRight = 0.0002, kFRight = 0.01;
	private final double ticks_in_degrees = 537.7 / 360.0;
	private double maxVelocity = 2000; // True max is ~2200
	private double maxAcceleration = 29000; // True max is ~ 30000
	private double rightPIDOutput;
	private double leftPIDOutput;
	private int targetPosition;
	private double voltageCompensation;

	public Slides(DcMotorEx slideMotorLeft, DcMotorEx slideMotorRight, VoltageSensor sensor) {
		this.slideMotorLeft = slideMotorLeft;
		this.slideMotorRight = slideMotorRight;

		this.leftController = new PIDController(kPLeft, kILeft, kDLeft);
		this.rightController = new PIDController(kPRight, kIRight, kDRight);

		this.voltageSensor = sensor;
		this.timer = new ElapsedTime();
		setTargetPosition(0);
	}

	public void setTargetPosition(int TargetPosition) {
		double startVelocity = 0;

		MotionState startStateLeft = new MotionState(slideMotorLeft.getCurrentPosition(), startVelocity);
		MotionState endStateLeft = new MotionState(TargetPosition, 0);
		this.leftProfile = MotionProfileGenerator.generateSimpleMotionProfile(startStateLeft, endStateLeft, maxVelocity, maxAcceleration);

		MotionState startStateRight = new MotionState(slideMotorRight.getCurrentPosition(), startVelocity);
		MotionState endStateRight = new MotionState(TargetPosition, 0);
		this.rightProfile = MotionProfileGenerator.generateSimpleMotionProfile(startStateRight, endStateRight, maxVelocity, maxAcceleration);
		targetPosition=TargetPosition;
		timer.reset();
	}

	public void update() {
		double elapsedTime = timer.seconds();

		MotionState leftState = leftProfile.get(elapsedTime);
		leftControl(leftState);
		MotionState rightState = rightProfile.get(elapsedTime);
		rightControl(rightState);
	}

	private void leftControl(MotionState targetState) {
		double leftPower = calculateMotorPowerLeft(slideMotorLeft, targetState, leftController);
		leftPIDOutput=leftPower;
		slideMotorLeft.setPower(leftPower);
	}

	private void rightControl(MotionState targetState) {
		double rightPower = calculateMotorPowerRight(slideMotorRight, targetState, rightController);
		rightPIDOutput=rightPower;
		slideMotorRight.setPower(rightPower);
	}

	private double calculateMotorPowerLeft(DcMotorEx motor, MotionState targetState, PIDController controller) {
		int currentPosition = motor.getCurrentPosition();
		double power = controller.calculate(currentPosition, targetState.getX());

		if (currentPosition > 900) {
			double angle = (targetState.getX() - currentPosition) / ticks_in_degrees;
			double ff = Math.cos(Math.toRadians(angle)) * kFLeft2;
			power += ff;
		} else if (currentPosition > 300) {
			double angle = (targetState.getX() - currentPosition) / ticks_in_degrees;
			double ff = Math.cos(Math.toRadians(angle)) * kFLeft1;
			power += ff;
		} else {
			double angle = (targetState.getX() - currentPosition) / ticks_in_degrees;
			double ff = Math.cos(Math.toRadians(angle)) * kFLeft;
			power += ff;
		}
		voltageCompensation = 13.2 / voltageSensor.getVoltage();
		power *= voltageCompensation;

		return power;
	}
	private double calculateMotorPowerRight(DcMotorEx motor, MotionState targetState, PIDController controller) {
		int currentPosition = motor.getCurrentPosition();
		double power = controller.calculate(currentPosition, targetState.getX());

		if (currentPosition > 900) {
			double angle = (targetState.getX() - currentPosition) / ticks_in_degrees;
			double ff = Math.cos(Math.toRadians(angle)) * kFRight2;
			power += ff;
		} else if (currentPosition > 300) {
			double angle = (targetState.getX() - currentPosition) / ticks_in_degrees;
			double ff = Math.cos(Math.toRadians(angle)) * kFRight1;
			power += ff;
		} else {
			double angle = (targetState.getX() - currentPosition) / ticks_in_degrees;
			double ff = Math.cos(Math.toRadians(angle)) * kFRight;
			power += ff;
		}
		voltageCompensation = 13.2 / voltageSensor.getVoltage();
		power *= voltageCompensation;

		return power;
	}
	public double getLeftPIDOutput()
	{
		return leftPIDOutput;
	}

	public double getRightPIDOutput()
	{
		return rightPIDOutput;
	}
	public int getTargetPosition()
	{
		return targetPosition;
	}
	public class SlidesUp1 implements Action
	{
		private boolean initialized = false;
		@Override
		public boolean run(@NonNull TelemetryPacket telemetryPacket)

		{
			if (!initialized) {
				setTargetPosition(350);
				initialized = true;
			}
			update();
			int currentPositionLeft = slideMotorLeft.getCurrentPosition();
			int currentPositionRight = slideMotorRight.getCurrentPosition();
			int errorMargin = 25;

			boolean leftInPosition = Math.abs(currentPositionLeft - targetPosition) >= errorMargin;
			boolean rightInPosition = Math.abs(currentPositionRight - targetPosition) >= errorMargin;
			return leftInPosition && rightInPosition;
		}
	}

	public class SlidesUp0 implements Action
	{
		private boolean initialized = false;
		@Override
		public boolean run(@NonNull TelemetryPacket telemetryPacket)

		{
			if (!initialized) {
				setTargetPosition(0);
				initialized = true;
			}
			update();
			int currentPositionLeft = slideMotorLeft.getCurrentPosition();
			int currentPositionRight = slideMotorRight.getCurrentPosition();
			int errorMargin = 25;

			boolean leftInPosition = Math.abs(currentPositionLeft - targetPosition) >= errorMargin;
			boolean rightInPosition = Math.abs(currentPositionRight - targetPosition) >= errorMargin;
			return leftInPosition && rightInPosition;
		}
	}
}