package org.firstinspires.ftc.teamcode.Framework;

import com.qualcomm.robotcore.hardware.Servo;

public class DroneLauncher {
    private static final double LAUNCH_POSITION = 1.0; // Servo position for launching the drone.
    private static final double RESET_POSITION = 0.0;  // Servo position for resetting the launcher.

    private final Servo droneLauncher;

    public DroneLauncher(Servo servo) {
        this.droneLauncher = servo;
    }


    public void launchDrone() {
        droneLauncher.setPosition(1);
    }
}
