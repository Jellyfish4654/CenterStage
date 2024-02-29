package org.firstinspires.ftc.teamcode.Framework;

import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.VoltageSensor;

public class DroneLauncher
{

	private Servo droneServo;
	private VoltageSensor voltageSensor;
	double nominalVoltage = 13.2;

	public DroneLauncher(Servo servo, Servo.Direction direction, VoltageSensor sensor) {
		this.droneServo = servo;
		this.droneServo.setDirection(direction);
		this.voltageSensor = sensor;

	}

	void setCompensatedServoPosition(Servo servo, double position) {
		double currentVoltage = voltageSensor.getVoltage();
		double factor = nominalVoltage / currentVoltage;
		double compensatedPosition = position * factor;
		servo.setPosition(compensatedPosition);
	}

	public void launchDrone()
	{
		setCompensatedServoPosition(droneServo, 0.85);
	}
}
