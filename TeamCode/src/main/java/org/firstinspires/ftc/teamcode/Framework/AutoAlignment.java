package org.firstinspires.ftc.teamcode.Framework;

import com.arcrobotics.ftclib.controller.PIDController;
import com.arcrobotics.ftclib.trajectory.TrapezoidProfile;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;

public class AutoAlignment {
	private final DcMotor[] motors;
	private final IMU imuSensor;
	private final PIDController pidController;
	private double targetAngle = 0;
	public static double kP = 0.04;
	public static double kI = 0.0;
	public static double kD = 0.0004;
	private TrapezoidProfile trapezoidProfile;
	private TrapezoidProfile.Constraints profileConstraints;
	private ElapsedTime timer;
	double maxAngularVelocity = 180;
	double maxAngularAcceleration = 180;


	// Constructor
	public AutoAlignment(DcMotor[] motors, IMU imuSensor) {
		this.motors = motors;
		this.imuSensor = imuSensor;
		this.pidController = new PIDController(kP, kI, kD);
		this.profileConstraints = new TrapezoidProfile.Constraints(maxAngularVelocity, maxAngularAcceleration);
		this.trapezoidProfile = new TrapezoidProfile(profileConstraints, new TrapezoidProfile.State(0, 0));
		this.timer = new ElapsedTime();
		this.timer.reset();
	}

	public void setTargetAngle(double targetAngle) {
		this.targetAngle = targetAngle;
		this.trapezoidProfile = new TrapezoidProfile(profileConstraints, new TrapezoidProfile.State(targetAngle, 0), trapezoidProfile.calculate(timer.seconds()));
		timer.reset();
	}

	public void update() {
		YawPitchRollAngles orientation = imuSensor.getRobotYawPitchRollAngles();
		double currentYaw = normalizeAngle(orientation.getYaw(AngleUnit.DEGREES));
		double angleDifference = normalizeAngle(targetAngle - currentYaw);
		double elapsedTime = timer.seconds();
		TrapezoidProfile.State currentGoal = new TrapezoidProfile.State(angleDifference, 0);
		this.trapezoidProfile = new TrapezoidProfile(this.profileConstraints, currentGoal, new TrapezoidProfile.State(0, trapezoidProfile.calculate(elapsedTime).velocity));
		TrapezoidProfile.State desiredState = trapezoidProfile.calculate(elapsedTime);
		double correction = pidController.calculate(currentYaw, desiredState.position);
		MotorPowers(correction);
	}


	private void MotorPowers(double correction) {
		double rightPower = correction;
		double leftPower = -correction;

		motors[0].setPower(rightPower);
		motors[1].setPower(rightPower);
		motors[2].setPower(leftPower);
		motors[3].setPower(leftPower);
	}

	private double normalizeAngle(double angle) {
		angle = angle % 360;
		if (angle <= -180) {
			angle += 360;
		} else if (angle > 180) {
			angle -= 360;
		}
		return angle;
	}
}
