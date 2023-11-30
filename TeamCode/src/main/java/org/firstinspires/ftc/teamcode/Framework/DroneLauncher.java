package org.firstinspires.ftc.teamcode.Framework;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

public class DroneLauncher {
    private static final double LAUNCH_POSITION = 1.0; // Servo position for launching the drone.
    private static final double RESET_POSITION = 0.0;  // Servo position for resetting the launcher.

    private final CRServo droneLauncher;

    ElapsedTime DroneTimer = new ElapsedTime();

    public DroneLauncher(CRServo servo) {
        this.droneLauncher = servo;
    }



    public void launchDrone() {
        droneLauncher.setPower(1);
        DroneTimer.reset();

        if (DroneTimer.seconds()>=0.5){
            droneLauncher.setPower(0);
        }
    }
}
