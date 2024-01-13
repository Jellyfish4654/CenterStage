package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.ftc.Actions;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.SleepAction;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.Framework.BaseOpMode;
import org.firstinspires.ftc.teamcode.Framework.misc.BluePipeline;
import org.firstinspires.ftc.teamcode.Framework.misc.Sides;
import org.firstinspires.ftc.teamcode.RoadRunner.MecanumDrive;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;

@Autonomous(name="BlueAuto", group="Auto")
public class BlueAuto extends BaseOpMode {
    OpenCvCamera webcam;
    BluePipeline detectionPipeline;

    @Override
    public void runOpMode() {
        MecanumDrive drive = new MecanumDrive(hardwareMap, new Pose2d(15, -60, Math.toRadians(90)));
        Sides.setColor(Sides.Color.BLUE);
        // Initialize hardware and pipeline
        initHardware(hardwareMap);
        initCamera();

        // Wait for the start button to be pressed, updating telemetry
        while (!isStarted() && !isStopRequested()) {
            telemetry.addData("Position", Sides.getPosition().toString());
            telemetry.update();
        }

        // After starting, stop the camera stream
        webcam.stopStreaming();
        while (opModeIsActive() && !isStopRequested()) {
            // Get the detected position
            Sides.Position detectedPosition = Sides.getPosition();

            // Run the autonomous path based on the detected position
            switch (detectedPosition) {
                case LEFT:

                    Actions.runBlocking(new SequentialAction(
                                    // Blue Left Purple Left
                                    drive.actionBuilder(new Pose2d(15, -60, Math.toRadians(90)))
                                            .splineTo(new Vector2d(8, -34), Math.toRadians(135))
                                            .splineToConstantHeading(new Vector2d(10, -36), Math.toRadians(315))
                                            .splineToSplineHeading(new Pose2d(40, -36, Math.toRadians(0)), Math.toRadians(315))
                                            .build(),
                                    // Blue Right Yellow Left
                                    drive.actionBuilder(new Pose2d(40, 36, Math.toRadians(0)))
                                            .splineToConstantHeading(new Vector2d(47.4, 41.2), Math.toRadians(0))
                                            .splineToLinearHeading(new Pose2d(49.3, 41.2, 0), Math.toRadians(0))
                                            .splineToLinearHeading(new Pose2d(46.3, 41.2, 0), Math.toRadians(270))
                                            .splineToLinearHeading(new Pose2d(46.3, 12, 0), Math.toRadians(270))
                                            .build(),
                                    // Blue Right Park
                                    drive.actionBuilder(new Pose2d(46.3, 12, Math.toRadians(0)))
                                            .splineToConstantHeading(new Vector2d(59, 12), Math.toRadians(0))
                                            .build()
                            )
                    );
                    break;
                case CENTER:
                case UNKNOWN:

                    Actions.runBlocking(new SequentialAction(
                                    // Red Right Purple Middle
                                    drive.actionBuilder(new Pose2d(15, -60, Math.toRadians(90)))
                                            .splineTo(new Vector2d(15, -32), Math.toRadians(90))
                                            .splineToConstantHeading(new Vector2d(15, -38), Math.toRadians(270))
                                            .splineToSplineHeading(new Pose2d(40, -36, Math.toRadians(0)), Math.toRadians(0))
                                            .build(),
                                    // Red Right Yellow Center
                                    drive.actionBuilder(new Pose2d(40, -36, Math.toRadians(0)))
                                            .splineToConstantHeading(new Vector2d(49, -36), Math.toRadians(0))
                                            .splineToLinearHeading(new Pose2d(46.3, -36, 0), Math.toRadians(90))
                                            .splineToLinearHeading(new Pose2d(46.3, -12, 0), Math.toRadians(90))
                                            .build(),
                                    // Red Right Park
                                    drive.actionBuilder(new Pose2d(46.3, -12, Math.toRadians(0)))
                                            .splineToConstantHeading(new Vector2d(59, -12), Math.toRadians(0))
                                            .build()
                            )
                    );
                    break;
                case RIGHT:
                    runAutonomousPathC();
                    break;
            }
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