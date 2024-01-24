package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import com.qualcomm.robotcore.hardware.DistanceSensor;


public class Distance extends LinearOpMode{
    DistanceSensor distance;
    @Override
    public void runOpMode() throws InterruptedException {
        distance = hardwareMap.get(DistanceSensor.class, "distanceLeft");
        waitForStart();
        while (opModeIsActive()) {
            telemetry.addData("drive mode", distance.getDistance(DistanceUnit.INCH));
            telemetry.update();
        }
    }
}
