package org.firstinspires.ftc.teamcode;


import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.Framework.BaseOpMode;
import org.firstinspires.ftc.teamcode.Framework.misc.ActionStorage;
import org.firstinspires.ftc.teamcode.Framework.misc.RedPipeline;
import org.firstinspires.ftc.teamcode.Framework.misc.Sides;
import org.firstinspires.ftc.teamcode.RoadRunner.MecanumDrive;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

@Autonomous(name = "RedAutoFarLeft", group = "Auto")
public class RedAutoFarLeft extends BaseOpMode
{
    OpenCvCamera webcam;
    RedPipeline detectionPipeline;
    Sides.Position detectedPosition;

    double distanceFilter = 0.9;
    LowPassFilter filter = new LowPassFilter(distanceFilter);
    double distance;

    @Override
    public void runOpMode()
    {
        MecanumDrive drive = new MecanumDrive(hardwareMap, new Pose2d(15, -60, Math.toRadians(90)));

        Sides.setColor(Sides.Color.RED);
        // Initialize hardware and pipeline
        initHardware(hardwareMap);
        initHardware();
        initCamera();
        // Wait for the start button to be pressed, updating telemetry
        while (!isStarted() && !isStopRequested())
        {
            telemetry.addData("Position", Sides.getPosition().toString());
            telemetry.addData("Left Pixels", RedPipeline.getLeft());
            telemetry.addData("Center Pixels", RedPipeline.getCenter());
            telemetry.addData("Right Pixels", RedPipeline.getRight());
            telemetry.addData("Left Distance", distanceLeft.getDistance(DistanceUnit.INCH));
            telemetry.update();

            detectedPosition = Sides.getPosition();
            intakeSystem.servoIntakeInit();
            distance = distanceLeft.getDistance(DistanceUnit.INCH);
            distance = filter.estimate(distance);
        }
        drive.pose = new Pose2d(distance-5.5, -60, Math.toRadians(90));
        // After starting, stop the camera stream
        webcam.stopStreaming();
        ActionStorage actionStorage = new ActionStorage(drive);
        // Run the autonomous path based on the detected position
        Action leftPurple = actionStorage.getRedFarRight_LeftPurpleAction();
        Action centerPurple = actionStorage.getRedFarRight_CenterPurpleAction();
        Action rightPurple = actionStorage.getRedFarRight_RightPurpleAction();
        switch (detectedPosition)
        {
            case LEFT:
                Actions.runBlocking(new SequentialAction(
                               leftPurple
                        )
                );
                break;
            case CENTER:
                Actions.runBlocking(new SequentialAction(
                                centerPurple
                        )
                );
                break;
            case RIGHT:
            case UNKNOWN:
                Actions.runBlocking(new SequentialAction(
                                rightPurple
                        )
                );
                break;
        }
    }

    private void initHardware(HardwareMap hwMap)
    {
        // Initialize your robot's hardware here
    }

    private void initCamera()
    {
        // Initialize camera
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        webcam = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, "Webcam 1"), cameraMonitorViewId);

        detectionPipeline = new RedPipeline(telemetry);
        webcam.setPipeline(detectionPipeline);

        webcam.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener()
        {
            @Override
            public void onOpened()
            {
                webcam.startStreaming(1920, 1080, OpenCvCameraRotation.UPRIGHT);
            }

            @Override
            public void onError(int errorCode)
            {
                // Error handling
            }
        });
    }

}