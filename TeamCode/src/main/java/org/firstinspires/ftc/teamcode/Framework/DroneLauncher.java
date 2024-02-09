package org.firstinspires.ftc.teamcode.Framework;

import com.qualcomm.robotcore.hardware.Servo;

public class DroneLauncher
{

	private final Servo droneLauncher;

	public DroneLauncher(Servo servo)
	{
		this.droneLauncher = servo;
	}


	public void launchDrone()
	{
		droneLauncher.setPosition(1);
	}
}
