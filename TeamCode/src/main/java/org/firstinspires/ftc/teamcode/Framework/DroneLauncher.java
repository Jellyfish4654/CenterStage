package org.firstinspires.ftc.teamcode.Framework;

import com.qualcomm.robotcore.hardware.Servo;

public class DroneLauncher {
    private static final double LAUNCH_POSITION = 1.0; // Servo position for launching the drone.
    private static final double RESET_POSITION = 0.0;  // Servo position for resetting the launcher.

    private Servo droneLauncher;

    public DroneLauncher(Servo servo) {
        this.droneLauncher = servo;
    }

    public void launchDrone() {
        droneLauncher.setPosition(LAUNCH_POSITION); // Sets the position to launch the drone.
    }

    public void resetLauncher() {
        droneLauncher.setPosition(RESET_POSITION); // Resets the launcher to its initial position.
    }

    public double getCurrentPosition() {
        return droneLauncher.getPosition(); // Returns the current position of the launcher.
    }
}
