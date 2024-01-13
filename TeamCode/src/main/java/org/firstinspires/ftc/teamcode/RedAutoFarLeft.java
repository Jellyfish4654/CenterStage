package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.Framework.BaseOpMode;
import org.firstinspires.ftc.teamcode.Framework.misc.RedPipeline;
import org.firstinspires.ftc.teamcode.Framework.misc.Sides;
import org.firstinspires.ftc.teamcode.RoadRunner.MecanumDrive;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;

@Autonomous(name = "RedAutoFarLeft", group = "Auto")
public class RedAutoFarLeft extends BaseOpMode {
    OpenCvCamera webcam;
    RedPipeline detectionPipeline;
    Sides.Position detectedPosition;

    @Override
    public void runOpMode() {
        MecanumDrive drive = new MecanumDrive(hardwareMap, new Pose2d(15, -60, Math.toRadians(90)));
        Sides.setColor(Sides.Color.RED);
        // Initialize hardware and pipeline
        initHardware(hardwareMap);
        initHardware();
        initCamera();
        // Wait for the start button to be pressed, updating telemetry
        while (!isStarted() && !isStopRequested()) {
            telemetry.addData("Position", Sides.getPosition().toString());
            telemetry.addData("Left Pixels", RedPipeline.getLeft());
            telemetry.addData("Center Pixels", RedPipeline.getCenter());
            telemetry.addData("Right Pixels", RedPipeline.getRight());
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
                ),
                                new SleepAction(
                                        sleep(9000)
                                ),

                                //park
                        new SequentialAction(
                                drive.actionBuilder(new Pose2d(7.5, -36.5, Math.toRadians(135)))
                                        .strafeTo(new Vector2d(9.7, -39.3))
                                        .turn(Math.toRadians(-45))
                                        .strafeTo(new Vector2d(9.7, -58.9))
                                        .strafeTo(new Vector2d(107, -58))
                                        .build())

                        );
//                                ,
//                                new ParallelAction(
//                                        telemetryPacket -> {
//                                            slides.setTargetPosition(1775);
//                                            return slides.slideCheck();
//                                        },
//                                        telemetryPacket -> {
//                                            slides.update();
//                                            return slides.slideCheck();
//                                        }
//                                )
                        // Red Right Yellow Left
//                                    drive.actionBuilder(new Pose2d(40, -36, Math.toRadians(0)))
//                                            .splineToConstantHeading(new Vector2d(44.4, -28), Math.toRadians(0))
//                                            .splineToLinearHeading(new Pose2d(49, -28, 0), 0)
//                                            .splineToLinearHeading(new Pose2d(46.3, -28, 0), Math.toRadians(90))
//                                            .splineToLinearHeading(new Pose2d(46.3, -12, 0), Math.toRadians(90))
//                                            .build(),
//                                    // Red Right Park
//                                    drive.actionBuilder(new Pose2d(46.3, -12, Math.toRadians(0)))
//                                            .splineToConstantHeading(new Vector2d(59, -12), Math.toRadians(0))
//                                            .build()
                break;
            case CENTER:
            case UNKNOWN:
                Actions.runBlocking(new SequentialAction(
                        // Red Right Purple Middle
                        drive.actionBuilder(new Pose2d(13, -60, Math.toRadians(90)))
                                .splineTo(new Vector2d(13, -32), Math.toRadians(90))
                                .build()),
                        new SleepAction(
                                sleep(9000)
                        ),
                        new SequentialAction(
                        //park
                        drive.actionBuilder(new Pose2d(13, -60, Math.toRadians(90)))
                        .strafeTo(new Vector2d(13, -60))
                        .strafeTo(new Vector2d(105, -58))
                        .build())
                );
                break;
            case RIGHT:
                Actions.runBlocking(new SequentialAction(
                                // Red Right Purple Right
                                drive.actionBuilder(new Pose2d(13, -60, Math.toRadians(90)))
                                        .splineTo(new Vector2d(13, -46), Math.toRadians(90))
                                        .splineTo(new Vector2d(18, -38), Math.toRadians(60))
                                        .build()),
                        new SleepAction(
                                sleep(9000)
                        ),
                        new SequentialAction(
                        //park
                                        drive.splineToConstantHeading(new Vector2d(13, -46), Math.toRadians(60))
                                        .turn(Math.toRadians(30))
                                        .strafeTo(new Vector2d(9.7, -58.9))
                                        .strafeTo(new Vector2d(107, -58))
                                        .build())
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

        detectionPipeline = new RedPipeline(telemetry);
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

}