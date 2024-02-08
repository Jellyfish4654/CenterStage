package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.Action;
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

@Autonomous(name = "RedAutoCloseRight", group = "Auto")
public class RedAutoCloseRight extends BaseOpMode
{
    OpenCvCamera webcam;
    RedPipeline detectionPipeline;
    Sides.Position detectedPosition;

    double distance;

    @Override
    public void runOpMode()
    {
        MecanumDrive drive = new MecanumDrive(hardwareMap, new Pose2d(15, -60, Math.toRadians(90)));
        ActionStorage actionStorage = new ActionStorage(drive);
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
            telemetry.addData("Right Distance", distanceRight.getDistance(DistanceUnit.INCH));
            telemetry.update();
            detectedPosition = Sides.getPosition();
            intakeSystem.servoIntakeInit();
            distance = distanceRight.getDistance(DistanceUnit.INCH);
        }
        drive.pose = new Pose2d(distance, -60, Math.toRadians(90));
        // After starting, stop the camera stream
        webcam.stopStreaming();
        // Run the autonomous path based on the detected position
        Action leftPurple = actionStorage.getRedCloseRight_LeftPurpleAction();
        switch (detectedPosition)
        {
            case LEFT:
                Actions.runBlocking(new SequentialAction(
                        leftPurple,
                        // Red Right Park
                        drive.actionBuilder(new Pose2d(5, -34, Math.toRadians(135)))
                                .splineToConstantHeading(new Vector2d(15, -48), Math.toRadians(270))
                                .splineToSplineHeading(new Pose2d(15, -58, Math.toRadians(90)), Math.toRadians(0))
                                .splineToConstantHeading(new Vector2d(60, -58), Math.toRadians(0))
                                .build(),
                        slides.new SlidesUp1()
                        )
                );
                break;
            case CENTER:
            case UNKNOWN:
                Actions.runBlocking(new SequentialAction(
                                // Red Right Purple Middle
                                drive.actionBuilder(new Pose2d(15, -60, Math.toRadians(90)))
                                        .splineTo(new Vector2d(15, -32), Math.toRadians(90))
                                        .build(),
//                                    // Red Right Park
                                drive.actionBuilder(new Pose2d(15, -32, Math.toRadians(90)))
                                        .lineToY(-40)
                                        .splineToConstantHeading(new Vector2d(15, -58), Math.toRadians(90))
                                        .splineToSplineHeading(new Pose2d(22, -58, Math.toRadians(90)), Math.toRadians(0))
                                        .splineToConstantHeading(new Vector2d(60, -58), Math.toRadians(0))
                                        .build()
                        )
                );
                break;
            case RIGHT:
                Actions.runBlocking(new SequentialAction(
                                // Red Right Purple Right
                                drive.actionBuilder(new Pose2d(15, -60, Math.toRadians(90)))
                                        .splineTo(new Vector2d(15, -46), Math.toRadians(90))
                                        .splineTo(new Vector2d(18, -38), Math.toRadians(60))
                                        .build(),
//                                    // Red Right Park
                                drive.actionBuilder(new Pose2d(18, -38, Math.toRadians(60)))
                                        .strafeTo(new Vector2d(12, -54))
                                        .splineToSplineHeading(new Pose2d(12, -55, Math.toRadians(90)), Math.toRadians(0))
                                        .splineToSplineHeading(new Pose2d(12, -58, Math.toRadians(90)), Math.toRadians(0))
                                        .splineToConstantHeading(new Vector2d(60, -58), Math.toRadians(0))
                                        .build()
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