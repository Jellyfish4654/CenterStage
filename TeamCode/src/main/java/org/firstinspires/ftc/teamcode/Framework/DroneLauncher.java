package org.firstinspires.ftc.teamcode.Framework;

import com.qualcomm.robotcore.hardware.Servo;

public class DroneLauncher
{

	private Servo droneServo;

	// Constructor with direction setting as a parameter
	public DroneLauncher(Servo servo, Servo.Direction direction) {
		this.droneServo = servo;
		this.droneServo.setDirection(direction); // Set the servo direction during initialization
	}


	public void launchDrone()
	{
		droneServo.setPosition(1);
	}
}
