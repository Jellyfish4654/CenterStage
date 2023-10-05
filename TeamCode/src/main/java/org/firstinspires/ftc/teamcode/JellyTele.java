package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.Framework.AntiTipping;
import org.firstinspires.ftc.teamcode.Framework.BaseOpMode;

@TeleOp(name = "CenterStage JellyTele")
public class JellyTele extends BaseOpMode {
    protected enum DriveMode {
        TANK,
        DRIVE,
        MECANUM,
        FIELDCENTRIC
    }
    protected DriveMode driveMode = DriveMode.FIELDCENTRIC;

    public void runOpMode() throws InterruptedException {
        AntiTipping antiTipping = new AntiTipping(motors, imu);
        // Init hardware from BaseOpMode
        initHardware();
        waitForStart();
    }

}
