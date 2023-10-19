package org.firstinspires.ftc.teamcode.Framework;

import com.qualcomm.robotcore.hardware.Servo;

public class DroneLauncher
{
    private Servo droneLauncher;

    public DroneLauncher(Servo servo)
    {
        this.droneLauncher = servo;
    }

    public void launchDrone()
    {
        droneLauncher.setPosition(1);
    }

    public void resetLauncher()
    {
        droneLauncher.setPosition(0);
    }

    public double getCurrentPosition()
    {
        return droneLauncher.getPosition();
    }
}