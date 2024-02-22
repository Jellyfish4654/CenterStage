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
	private PIDController pidController;
	private MotionProfile profile;
	private ElapsedTime timer;
	private double targetAngle = 0;
	public static double kP = 0.02;
	public static double kI = 0.0;
	public static double kD = 0;
	private double maxAngularVelocity = 90;
	private double maxAngularAcceleration = 180;

	public AutoAlignment(DcMotor[] motors, IMU imuSensor) {
		this.motors = motors;
		this.imuSensor = imuSensor;
		this.pidController = new PIDController(kP, kI, kD);
		this.timer = new ElapsedTime();
	}

	public void setTargetAngle(double TargetAngle) {
		targetAngle = TargetAngle;
		double currentAngle = getCurrentAngle();
		MotionState startState = new MotionState(currentAngle, 0);
		MotionState endState = new MotionState(targetAngle, 0);
		this.profile = MotionProfileGenerator.generateSimpleMotionProfile(startState, endState, maxAngularVelocity, maxAngularAcceleration);
		timer.reset();
	}

	public void update() {
		double elapsedTime = timer.seconds();
		MotionState targetState = profile.get(elapsedTime);
		double currentAngle = getCurrentAngle();
		double correction = calculateMotorPower(currentAngle, targetState);
		applyMotorPowers(correction);
	}

	private double getCurrentAngle() {
		YawPitchRollAngles orientation = imuSensor.getRobotYawPitchRollAngles();
		return orientation.getYaw(AngleUnit.DEGREES);
	}

	private double calculateMotorPower(double currentAngle, MotionState targetState) {
		return pidController.calculate(currentAngle, targetState.getX());
	}

	private void applyMotorPowers(double correction) {
		double rightPower = correction;
		double leftPower = -correction;

		motors[0].setPower(leftPower);
		motors[1].setPower(leftPower);
		motors[2].setPower(rightPower);
		motors[3].setPower(rightPower);
	}
}
