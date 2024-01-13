package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.Framework.BaseOpMode;
import org.firstinspires.ftc.teamcode.Framework.misc.BluePipeline;
import org.firstinspires.ftc.teamcode.Framework.misc.Sides;
import org.firstinspires.ftc.teamcode.RoadRunner.MecanumDrive;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;

@Autonomous(name = "BlueAutoCloseLeft", group = "Auto")
public class BlueAutoCloseLeft extends BaseOpMode {
    OpenCvCamera webcam;
    BluePipeline detectionPipeline;
    Sides.Position detectedPosition;

    @Override
    public void runOpMode() {
        MecanumDrive drive = new MecanumDrive(hardwareMap, new Pose2d(15, -60, Math.toRadians(90)));
        Sides.setColor(Sides.Color.BLUE);
        // Initialize hardware and pipeline
        initHardware(hardwareMap);
        initHardware();
        initCamera();

        // Wait for the start button to be pressed, updating telemetry
        while (!isStarted() && !isStopRequested()) {
            telemetry.addData("Position", Sides.getPosition().toString());
            telemetry.addData("Left Pixels", BluePipeline.getLeft());
            telemetry.addData("Center Pixels", BluePipeline.getCenter());
            telemetry.addData("Right Pixels", BluePipeline.getRight());
            telemetry.update();
            detectedPosition = Sides.getPosition();
            intakeSystem.servoIntakeInit();
        }

        // After starting, stop the camera stream
        webcam.stopStreaming();

        // Run the autonomous path based on the detected position
        switch (detectedPosition) {
            case LEFT:

                Actions.runBlocking(new SequentialAction(
                                // Blue Left Purple Left
                                drive.actionBuilder(new Pose2d(13, -60, Math.toRadians(90)))

                                        .splineToConstantHeading(new Vector2d(13, -48), Math.toRadians(90))
                                        .splineTo(new Vector2d(7.5, -36.5), Math.toRadians(135))
                                        .build(),
                                        //park
                                        .strafeTo(new Vector2d(9.7, -39.3))
                                        .turn(Math.toRadians(-45))
                                        .strafeTo(new Vector2d(9.7, -58.9))
                                        .strafeTo(new Vector2d(-35, -58))
                                        .build());
                );
                break;
            case CENTER:
            case UNKNOWN:

                Actions.runBlocking(new SequentialAction(
                        // Red Right Purple Middle
                        drive.actionBuilder(new Pose2d(13, -60, Math.toRadians(90)))
                                .splineTo(new Vector2d(13, -32), Math.toRadians(90))
                                .build())
                );
                break;
            case RIGHT:
                Actions.runBlocking(new SequentialAction(
                                // Red Right Purple Right
                                drive.actionBuilder(new Pose2d(13, -60, Math.toRadians(90)))
                                        .splineTo(new Vector2d(13, -46), Math.toRadians(90))
                                        .splineTo(new Vector2d(18, -38), Math.toRadians(60))
                                        .build()
                        )
                );
                break;
        }
    }
    

    private void initHardware(HardwareMap hwMap) {
        // Initialize your robot's hardware here
    }

    private void initCamera() {
        // Initialize camera
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        webcam = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, "Webcam 1"), cameraMonitorViewId);

        detectionPipeline = new BluePipeline(telemetry);
        webcam.setPipeline(detectionPipeline);

        webcam.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() {
            @Override
            public void onOpened() {
                webcam.startStreaming(1920, 1080, OpenCvCameraRotation.UPRIGHT);
            }

            @Override
            public void onError(int errorCode) {
                // Error handling
            }
        });
    }

    private void runAutonomousPathA() {
        // Define the autonomous path A
    }

    private void runAutonomousPathB() {
        // Define the autonomous path B
        // Define the autonomous path B
    }

    private void runAutonomousPathC() {
        // Define the autonomous path C
    }
}