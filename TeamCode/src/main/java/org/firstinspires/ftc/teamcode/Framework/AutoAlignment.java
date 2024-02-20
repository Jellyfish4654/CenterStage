package org.firstinspires.ftc.teamcode.Framework;

import com.arcrobotics.ftclib.controller.PIDController;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;
import org.firstinspires.ftc.teamcode.Framework.Profiles.MotionProfile;
import org.firstinspires.ftc.teamcode.Framework.Profiles.MotionProfileGenerator;
import org.firstinspires.ftc.teamcode.Framework.Profiles.MotionState;

public class AutoAlignment {
	private DcMotor[] motors;
	private IMU imuSensor;
	private PIDController rotationController;
	private MotionProfile rotationProfile;
	private ElapsedTime timer;
	private double targetAngle = 0;
	public static double kP = 0.03;
	public static double kI = 0.0;
	public static double kD = 0;
	private double maxAngularVelocity = Math.toRadians(90);
	private double maxAngularAcceleration = Math.toRadians(180);
	public AutoAlignment(DcMotor[] motors, IMU imuSensor)
	{
		this.motors = motors;
		this.imuSensor = imuSensor;
		this.rotationController = new PIDController(kP, kI, kD);
		this.timer = new ElapsedTime();
	}

	public void setTargetAngle(double targetAngleRadians) {
		targetAngle=targetAngleRadians;
		double currentAngleRadians = getCurrentAngleRadians();
		MotionState startState = new MotionState(currentAngleRadians, 0);
		MotionState endState = new MotionState(targetAngle, 0);
		this.rotationProfile = MotionProfileGenerator.generateSimpleMotionProfile(startState, endState, maxAngularVelocity, maxAngularAcceleration);
		timer.reset();
	}

	public void update() {
		double elapsedTime = timer.seconds();
		MotionState targetState = rotationProfile.get(elapsedTime);
		double currentAngleRadians = getCurrentAngleRadians();
		double correction = rotationController.calculate(currentAngleRadians, targetState.getX());

		MotorPowers(correction);
	}

	private double getCurrentAngleRadians() {
		YawPitchRollAngles orientation = imuSensor.getRobotYawPitchRollAngles();
		return orientation.getYaw(AngleUnit.RADIANS);
	}

	private void MotorPowers(double correction) {
		double rightPower = correction;
		double leftPower = -correction;

		for (int i = 0; i < motors.length / 2; i++) {
			motors[i].setPower(rightPower);
		}
		for (int i = motors.length / 2; i < motors.length; i++) {
			motors[i].setPower(leftPower);
		}
	}
}