package org.firstinspires.ftc.teamcode.Framework;

import com.arcrobotics.ftclib.controller.PIDController;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;
import org.firstinspires.ftc.teamcode.JellyTele;

public class AutoAlignment
{
	private final DcMotor[] motors;
	private final IMU imuSensor;
	private final PIDController pidController;
	private double targetAngle = 0;
	public static double kP = 0.017;
	public static double kI = 0.0;
	public static double kD = 0.00017;

	// Constructor
	public AutoAlignment(DcMotor[] motors, IMU imuSensor)
	{
		this.motors = motors;
		this.imuSensor = imuSensor;
		this.pidController = new PIDController(kP, kI, kD);
	}

	public void setTargetAngle(double targetAngle)
	{
		this.targetAngle = targetAngle;
	}

	public void update()
	{
		YawPitchRollAngles orientation = imuSensor.getRobotYawPitchRollAngles();
//        double currentYaw = orientation.getYaw(AngleUnit.DEGREES);
		double currentYaw = -Math.toDegrees(PoseStorage.currentPose.heading.toDouble());
		currentYaw = normalizeAngle(currentYaw);
		double angleDifference = normalizeAngle(targetAngle - currentYaw);
		double correction = pidController.calculate(0, angleDifference);
		MotorPowers(correction);
	}

	private void MotorPowers(double correction)
	{
		double rightPower = correction;
		double leftPower = -correction;

		motors[0].setPower(rightPower);
		motors[1].setPower(rightPower);
		motors[2].setPower(leftPower);
		motors[3].setPower(leftPower);
	}

	private double normalizeAngle(double angle)
	{
		angle = angle % 360;
		if (angle <= -180)
		{
			angle += 360;
		}
		else if (angle > 180)
		{
			angle -= 360;
		}
		return angle;
	}
	public void setPGain(double p)
	{
		this.kP = p;
	}

	public void setDGain(double d)
	{
		this.kD = d;
	}
}