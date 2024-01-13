package org.firstinspires.ftc.teamcode;

import com.arcrobotics.ftclib.geometry.Pose2d;
import com.arcrobotics.ftclib.geometry.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.Framework.misc.BluePipeline;
import org.firstinspires.ftc.teamcode.Framework.misc.Sides;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;

@Autonomous(name="BlueAuto", group="Auto")
public class BlueAuto extends LinearOpMode {
    OpenCvCamera webcam;
    BluePipeline detectionPipeline;

    @Override
    public void runOpMode() {
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
                    runAutonomousPathA();
                    break;
                case CENTER:
                case UNKNOWN:
                    runAutonomousPathB();
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
        Actions.runBlocking(new SequentialAction(
                        // Blue Left Purple Left
                        drive.actionBuilder(new Pose2d(15, -68, Math.toRadians(90)))
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
    }

    private void runAutonomousPathB() {
        // Define the autonomous path B
        // Define the autonomous path B
        Actions.runBlocking(new SequentialAction(
                        // Red Right Purple Middle
                        drive.actionBuilder(new Pose2d(15, -68, Math.toRadians(90)))
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
    }

    private void runAutonomousPathC() {
        // Define the autonomous path C
    }
}