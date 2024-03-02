package org.firstinspires.ftc.teamcode.Autons.Red;


import static org.firstinspires.ftc.teamcode.Framework.misc.Vision.getAprilTagPoses;
import static org.firstinspires.ftc.teamcode.Framework.misc.Vision.processTagPoses;

import androidx.annotation.NonNull;

import com.ThermalEquilibrium.homeostasis.Filters.FilterAlgorithms.LowPassFilter;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.ProfileAccelConstraint;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.SleepAction;
import com.acmerobotics.roadrunner.TranslationalVelConstraint;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.Actions.RedFarLeftStorage;
import org.firstinspires.ftc.teamcode.Framework.BaseOpMode;
import org.firstinspires.ftc.teamcode.Framework.DriveStorage;
import org.firstinspires.ftc.teamcode.Framework.misc.ActionStorage;
import org.firstinspires.ftc.teamcode.Framework.misc.LeftRedPipeline;
import org.firstinspires.ftc.teamcode.Framework.misc.RedPipeline;
import org.firstinspires.ftc.teamcode.Framework.misc.Sides;
import org.firstinspires.ftc.teamcode.RoadRunner.MecanumDrive;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;

import java.util.ArrayList;
import java.util.List;

@Autonomous(name = "RedAutoFarLeft", group = "Auto")
public class RedAutoFarLeft extends BaseOpMode {
    OpenCvCamera webcam;
    LeftRedPipeline detectionPipeline;
    Sides.Position detectedPosition;

    double distanceFilter = 0.9;
    LowPassFilter filter = new LowPassFilter(distanceFilter);
    double distance;
    public boolean actionRunning = true;

    @Override
    public void runOpMode() {
        MecanumDrive drive = new MecanumDrive(hardwareMap, new Pose2d(-70.5 + (5.5 + 24), -70.5 + 10.375, Math.toRadians(90)));
        Sides.setColor(Sides.Color.RED);
        initHardware(hardwareMap);
        initHardware();
        initCamera();
        List<LynxModule> allHubs = hardwareMap.getAll(LynxModule.class);
        for (LynxModule module : allHubs) {
            module.setBulkCachingMode(LynxModule.BulkCachingMode.MANUAL);
        }
        ElapsedTime timer = new ElapsedTime();

        while (!isStarted() && !isStopRequested()) {
            telemetry.addData("Position", Sides.getPosition().toString());
            telemetry.addData("Left Pixels", LeftRedPipeline.getLeft());
            telemetry.addData("Center Pixels", LeftRedPipeline.getCenter());
            telemetry.addData("Right Pixels", LeftRedPipeline.getRight());
            telemetry.addData("Left Distance", distanceLeft.getDistance(DistanceUnit.INCH));
            telemetry.update();

            detectedPosition = Sides.getPosition();
            intakeSystem.servoIntakeInit();
            for (LynxModule module : allHubs) {
                module.clearBulkCache();
            }
        }
        // After starting, stop the camera stream
        webcam.stopStreaming();

        switch (detectedPosition) {
            case LEFT:
                Actions.runBlocking(new ParallelAction(
                        new SequentialAction(
                                drive.actionBuilder(drive.pose)
                                        .splineToConstantHeading(new Vector2d(-41, -48), Math.toRadians(90))
                                        .splineToConstantHeading(new Vector2d(-49.5, -40), Math.toRadians(90))
                                        .splineToConstantHeading(new Vector2d(-49.5, -40-0.0001), Math.toRadians(270))
                                        .splineToConstantHeading(new Vector2d(-49.5, -43), Math.toRadians(270))
                                        .splineToConstantHeading(new Vector2d(-49.5+0.0001, -43), Math.toRadians(0))
                                        .splineToConstantHeading(new Vector2d(-33, -43), Math.toRadians(0))
                                        .splineToConstantHeading(new Vector2d(-33, -43+0.0001), Math.toRadians(90))
                                        .splineToSplineHeading(new Pose2d(-33, -12, Math.toRadians(-1.5)), Math.toRadians(90))
                                        .build(),
                                intakeSystem.new IntakeServoRelease(),
                                (telemetryPacket) -> {
                                    actionRunning = false;
                                    return false;
                                }
                        ),
                        (telemetryPacket) -> {
                            for (LynxModule module : allHubs) {
                                module.clearBulkCache();
                            }
                            drive.pose=processTagPoses(getAprilTagPoses(), drive);
                            slides.update();
                            intakeSystem.update();
                            return actionRunning;
                        }
                ));
                drive.pose = new Pose2d(-33, -12, Math.toRadians(0));
                drive.updatePoseEstimate();
                actionRunning = true;
                Actions.runBlocking(new ParallelAction(
                        new SequentialAction(
                                new ParallelAction(
                                        drive.actionBuilder(drive.pose)
                                                .splineToConstantHeading(new Vector2d(15, -14), Math.toRadians(0))
                                                .splineToConstantHeading(new Vector2d(40, -14), Math.toRadians(0))
                                                .splineToConstantHeading(new Vector2d(40, -14 - 0.001), Math.toRadians(270))
                                                .splineToConstantHeading(new Vector2d(40, -34), Math.toRadians(270), new TranslationalVelConstraint(25), new ProfileAccelConstraint(-30, 15))
                                                .splineToConstantHeading(new Vector2d(40, -34+0.0001), Math.toRadians(90))
                                                .build(),
                                        new SequentialAction(
                                                new SleepAction(1.25),
                                                slides.new SlidesUp1()

                                        )
                                ),

                                (telemetryPacket) -> {
                                    actionRunning = false;
                                    return false;
                                }
                        ),
                        (telemetryPacket) -> {
                            for (LynxModule module : allHubs) {
                                module.clearBulkCache();
                            }
                            drive.pose=processTagPoses(getAprilTagPoses(), drive);
                            slides.update();
                            intakeSystem.update();
                            return actionRunning;
                        }
                ));
                drive.pose=processTagPoses(getAprilTagPoses(), drive);
                drive.updatePoseEstimate();
                actionRunning = true;
                Actions.runBlocking(new ParallelAction(
                        new SequentialAction(
                                new ParallelAction(new SequentialAction(
                                        new SleepAction(1),
                                        drive.actionBuilder(drive.pose)
                                                .splineToConstantHeading(new Vector2d(40, -28.25), Math.toRadians(90))
                                                .splineToConstantHeading(new Vector2d(40+0.0001, -28.25), Math.toRadians(0))
                                                .splineToConstantHeading(new Vector2d(54.5, -28.25), Math.toRadians(0), new TranslationalVelConstraint(25), new ProfileAccelConstraint(-30, 15))
                                                .build()
                                ),
                                        new SequentialAction(
                                                outakeServos.new armOuttakeDeposit(),
                                                new SleepAction(0.25),
                                                outakeServos.new boxOuttakeDeposit(),
                                                new SleepAction(1.75),
                                                new Action() {
                                                    private boolean initialized = false;

                                                    @Override
                                                    public boolean run(@NonNull TelemetryPacket telemetryPacket) {

                                                        if (!initialized) {
                                                            timer.reset();
                                                            initialized = true;
                                                        }
                                                        outtakeCRServo.setPower(0.45);
                                                        if (timer.seconds() > 2) {
                                                            outtakeCRServo.setPower(0);
                                                            return false;
                                                        } else {
                                                            return true;
                                                        }
                                                    }
                                                }
                                        )
                                ),

                                (telemetryPacket) -> {
                                    actionRunning = false;
                                    return false;
                                }
                        ),
                        (telemetryPacket) -> {
                            for (LynxModule module : allHubs) {
                                module.clearBulkCache();
                            }
                            drive.pose=processTagPoses(getAprilTagPoses(), drive);
                            slides.update();
                            intakeSystem.update();
                            return actionRunning;
                        }
                ));
                drive.pose = processTagPoses(getAprilTagPoses(), drive);
                drive.updatePoseEstimate();
                actionRunning = true;

                break;
            case CENTER:
                Actions.runBlocking(new ParallelAction(
                        new SequentialAction(
                                drive.actionBuilder(drive.pose)
                                        .splineToConstantHeading(new Vector2d(-41, -37), Math.toRadians(90))
                                        .splineToConstantHeading(new Vector2d(-41, -37-0.001), Math.toRadians(270))
                                        .splineToConstantHeading(new Vector2d(-41, -40), Math.toRadians(270))
                                        .splineToConstantHeading(new Vector2d(-41-0.0001, -40), Math.toRadians(180))
                                        .splineToConstantHeading(new Vector2d(-55, -40), Math.toRadians(180))
                                        .splineToConstantHeading(new Vector2d(-55, -40+0.001), Math.toRadians(90))
                                        .splineToSplineHeading(new Pose2d(-55, -12, Math.toRadians(-5)), Math.toRadians(90))
                                        .splineToConstantHeading(new Vector2d(-55+0.0001, -12), Math.toRadians(0))
                                        .splineToConstantHeading(new Vector2d(-45, -12), Math.toRadians(0))
                                        .build(),
                                intakeSystem.new IntakeServoRelease(),
                                (telemetryPacket) -> {
                                    actionRunning = false;
                                    return false;
                                }
                        ),
                        (telemetryPacket) -> {
                            for (LynxModule module : allHubs) {
                                module.clearBulkCache();
                            }
                            drive.pose=processTagPoses(getAprilTagPoses(), drive);
                            slides.update();
                            intakeSystem.update();
                            return actionRunning;
                        }
                ));
                drive.pose = new Pose2d(-45, -12, Math.toRadians(0));
                drive.updatePoseEstimate();
                actionRunning = true;
                Actions.runBlocking(new ParallelAction(
                        new SequentialAction(
                                new ParallelAction(
                                        drive.actionBuilder(drive.pose)
                                                .splineToConstantHeading(new Vector2d(-40+0.0001, -12), Math.toRadians(0))
                                                .splineToConstantHeading(new Vector2d(-34, -12), Math.toRadians(0))
                                                .splineToConstantHeading(new Vector2d(15, -14), Math.toRadians(0))
                                                .splineToConstantHeading(new Vector2d(40, -14), Math.toRadians(0))
                                                .splineToConstantHeading(new Vector2d(40, -14 - 0.001), Math.toRadians(270))
                                                .splineToConstantHeading(new Vector2d(40, -31-0.0001), Math.toRadians(270), new TranslationalVelConstraint(25), new ProfileAccelConstraint(-30, 15))
                                                .build(),
                                        new SequentialAction(
                                                new SleepAction(3),
                                                slides.new SlidesUp1()
                                        )
                                ),

                                (telemetryPacket) -> {
                                    actionRunning = false;
                                    return false;
                                }
                        ),
                        (telemetryPacket) -> {
                            for (LynxModule module : allHubs) {
                                module.clearBulkCache();
                            }
                            drive.pose=processTagPoses(getAprilTagPoses(), drive);
                            slides.update();
                            intakeSystem.update();
                            return actionRunning;
                        }
                ));
                drive.pose=processTagPoses(getAprilTagPoses(), drive);
                drive.updatePoseEstimate();
                actionRunning = true;
                Actions.runBlocking(new ParallelAction(
                        new SequentialAction(
                                new ParallelAction(new SequentialAction(
                                        new SleepAction(0.5),
                                        drive.actionBuilder(drive.pose)
                                                .splineToConstantHeading(new Vector2d(40, -37.75-0.0001), Math.toRadians(270), new TranslationalVelConstraint(25), new ProfileAccelConstraint(-30, 15))
                                                .splineToConstantHeading(new Vector2d(40+0.0001, -37.75-0.0001), Math.toRadians(0))
                                                .splineToConstantHeading(new Vector2d(54.5, -37.75-0.0001), Math.toRadians(0), new TranslationalVelConstraint(25), new ProfileAccelConstraint(-30, 15))
                                                .build()
                                ),
                                        new SequentialAction(
                                                outakeServos.new armOuttakeDeposit(),
                                                new SleepAction(0.25),
                                                outakeServos.new boxOuttakeDeposit(),
                                                new SleepAction(1.75),
                                                new Action() {
                                                    private boolean initialized = false;

                                                    @Override
                                                    public boolean run(@NonNull TelemetryPacket telemetryPacket) {

                                                        if (!initialized) {
                                                            timer.reset();
                                                            initialized = true;
                                                        }
                                                        outtakeCRServo.setPower(0.8);
                                                        if (timer.seconds() > 2) {
                                                            outtakeCRServo.setPower(0);
                                                            return false;
                                                        } else {
                                                            return true;
                                                        }
                                                    }
                                                }
                                        )
                                ),

                                (telemetryPacket) -> {
                                    actionRunning = false;
                                    return false;
                                }
                        ),
                        (telemetryPacket) -> {
                            for (LynxModule module : allHubs) {
                                module.clearBulkCache();
                            }
                            drive.pose=processTagPoses(getAprilTagPoses(), drive);
                            slides.update();
                            intakeSystem.update();
                            return actionRunning;
                        }
                ));
                drive.updatePoseEstimate();
                actionRunning = true;
                Actions.runBlocking(new ParallelAction(
                        new SequentialAction(
                                new ParallelAction(
                                        new SequentialAction(
                                                drive.actionBuilder(drive.pose)
                                                        .splineToConstantHeading(new Vector2d(54.5-0.0001 - 0.0001, -37.75-0.0001), Math.toRadians(180))
                                                        .splineToConstantHeading(new Vector2d(40, -37.75-0.0001), Math.toRadians(180))
                                                        .build()
                                        ),
                                        new SequentialAction(
                                                new SleepAction(0.05),
                                                outakeServos.new boxOuttakeIntake(),
                                                new SleepAction(0.05),
                                                outakeServos.new armOuttakeIntake()
                                        )
                                ),

                                (telemetryPacket) -> {
                                    actionRunning = false;
                                    return false;
                                }
                        ),
                        (telemetryPacket) -> {
                            for (LynxModule module : allHubs) {
                                module.clearBulkCache();
                            }
                            drive.pose=processTagPoses(getAprilTagPoses(), drive);
                            slides.update();
                            intakeSystem.update();
                            return actionRunning;
                        }
                ));
                break;
            case RIGHT:
            case UNKNOWN:
                Actions.runBlocking(new ParallelAction(
                        new SequentialAction(
                                drive.actionBuilder(drive.pose)
                                        .splineTo(new Vector2d(-41, -50), Math.toRadians(90))
                                        .splineTo(new Vector2d(-31, -40), Math.toRadians(45))
                                        .splineToConstantHeading(new Vector2d(-31 - 0.0001, -40 - 0.0001), Math.toRadians(225))
                                        .splineToConstantHeading(new Vector2d(-30 + (6 * Math.cos(Math.toRadians(225))), -40 + (6 * Math.sin(Math.toRadians(225)))), Math.toRadians(225))
                                        .splineToConstantHeading(new Vector2d(-30 + (6 * Math.cos(Math.toRadians(225))) - 0.0001, -40 + (6 * Math.sin(Math.toRadians(225))) - 0.001), Math.toRadians(120))
                                        .splineToConstantHeading(new Vector2d(-40, -24), Math.toRadians(120))
                                        .splineToConstantHeading(new Vector2d(-40, -24+0.001), Math.toRadians(90))
                                        .splineToSplineHeading(new Pose2d(-40, -12, Math.toRadians(-3.1)), Math.toRadians(90))
                                        .build(),
                                intakeSystem.new IntakeServoRelease(),
                                (telemetryPacket) -> {
                                    actionRunning = false;
                                    return false;
                                }
                        ),
                        (telemetryPacket) -> {
                            for (LynxModule module : allHubs) {
                                module.clearBulkCache();
                            }
                            drive.pose=processTagPoses(getAprilTagPoses(), drive);
                            slides.update();
                            intakeSystem.update();
                            return actionRunning;
                        }
                ));
                drive.pose = new Pose2d(-40, -12, Math.toRadians(0));
                drive.updatePoseEstimate();
                actionRunning = true;
                Actions.runBlocking(new ParallelAction(
                        new SequentialAction(
                                new ParallelAction(
                                        drive.actionBuilder(drive.pose)
                                                .splineToConstantHeading(new Vector2d(-40+0.0001, -12), Math.toRadians(0))
                                                .splineToConstantHeading(new Vector2d(-34, -12), Math.toRadians(0))
                                                .splineToConstantHeading(new Vector2d(15, -14), Math.toRadians(0))
                                                .splineToConstantHeading(new Vector2d(40, -14), Math.toRadians(0))
                                                .splineToConstantHeading(new Vector2d(40, -14 - 0.001), Math.toRadians(270))
                                                .splineToConstantHeading(new Vector2d(40, -31-0.0001), Math.toRadians(270), new TranslationalVelConstraint(40.0), new ProfileAccelConstraint(-30, 25))
                                                .build(),
                                        new SequentialAction(
                                                new SleepAction(3),
                                                slides.new SlidesUp1()
                                        )
                                ),

                                (telemetryPacket) -> {
                                    actionRunning = false;
                                    return false;
                                }
                        ),
                        (telemetryPacket) -> {
                            for (LynxModule module : allHubs) {
                                module.clearBulkCache();
                            }
                            drive.pose=processTagPoses(getAprilTagPoses(), drive);
                            slides.update();
                            intakeSystem.update();
                            return actionRunning;
                        }
                ));
                drive.pose=processTagPoses(getAprilTagPoses(), drive);
                drive.updatePoseEstimate();
                actionRunning = true;
                Actions.runBlocking(new ParallelAction(
                        new SequentialAction(
                                new ParallelAction(new SequentialAction(
                                        new SleepAction(0.5),
                                        drive.actionBuilder(drive.pose)
                                                .splineToConstantHeading(new Vector2d(40, -43.75-0.0001), Math.toRadians(270), new TranslationalVelConstraint(40.0), new ProfileAccelConstraint(-30, 25))
                                                .splineToConstantHeading(new Vector2d(40+0.0001, -43.75-0.0001), Math.toRadians(0))
                                                .splineToConstantHeading(new Vector2d(54.5, -43.75-0.0001), Math.toRadians(0), new TranslationalVelConstraint(40.0), new ProfileAccelConstraint(-30, 25))
                                                .build()
                                ),
                                        new SequentialAction(
                                                outakeServos.new armOuttakeDeposit(),
                                                new SleepAction(0.25),
                                                outakeServos.new boxOuttakeDeposit(),
                                                new SleepAction(1.75),
                                                new Action() {
                                                    private boolean initialized = false;

                                                    @Override
                                                    public boolean run(@NonNull TelemetryPacket telemetryPacket) {

                                                        if (!initialized) {
                                                            timer.reset();
                                                            initialized = true;
                                                        }
                                                        outtakeCRServo.setPower(0.8);
                                                        if (timer.seconds() > 2) {
                                                            outtakeCRServo.setPower(0);
                                                            return false;
                                                        } else {
                                                            return true;
                                                        }
                                                    }
                                                }
                                        )
                                ),

                                (telemetryPacket) -> {
                                    actionRunning = false;
                                    return false;
                                }
                        ),
                        (telemetryPacket) -> {
                            for (LynxModule module : allHubs) {
                                module.clearBulkCache();
                            }
                            drive.pose=processTagPoses(getAprilTagPoses(), drive);
                            slides.update();
                            intakeSystem.update();
                            return actionRunning;
                        }
                ));
                drive.updatePoseEstimate();
                actionRunning = true;
                Actions.runBlocking(new ParallelAction(
                        new SequentialAction(
                                new ParallelAction(
                                        new SequentialAction(
                                                drive.actionBuilder(drive.pose)
                                                        .splineToConstantHeading(new Vector2d(54.5 - 0.0001, -43.75-0.0001), Math.toRadians(180))
                                                        .splineToConstantHeading(new Vector2d(40, -43.75-0.0001), Math.toRadians(180))
                                                        .build()
                                        ),
                                        new SequentialAction(
                                                new SleepAction(0.05),
                                                outakeServos.new boxOuttakeIntake(),
                                                new SleepAction(0.05),
                                                outakeServos.new armOuttakeIntake()
                                        )
                                ),

                                (telemetryPacket) -> {
                                    actionRunning = false;
                                    return false;
                                }
                        ),
                        (telemetryPacket) -> {
                            for (LynxModule module : allHubs) {
                                module.clearBulkCache();
                            }
                            drive.pose=processTagPoses(getAprilTagPoses(), drive);
                            slides.update();
                            intakeSystem.update();
                            return actionRunning;
                        }
                ));

//                Actions.runBlocking(new ParallelAction(
//                        new SequentialAction(
//                                new ParallelAction(
//                                        drive.actionBuilder(drive.pose)
//                                                .splineToConstantHeading(new Vector2d(-54, -9), Math.toRadians(180))
//                                                .build(),
//                                        intakeSystem.new IntakeMotorForward()
//                                ),
//                                intakeSystem.new IntakeMotorBackward(),
//                                new SleepAction(1),
//                                (telemetryPacket) -> {
//                                    actionRunning = false;
//                                    return false;
//                                }
//                        ),
//                        (telemetryPacket) -> {
//                            for (LynxModule module : allHubs) {
//                                module.clearBulkCache();
//                            }
//                            slides.update();
//                            intakeSystem.update();
//                            return actionRunning;
//                        }
//                ));
//                drive.updatePoseEstimate();
//                actionRunning = true;
//                break;
        }
    }

    private void initHardware(HardwareMap hwMap) {
        // Initialize your robot's hardware here
    }

    private void initCamera() {
        // Initialize camera
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        webcam = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, "Webcam 1"), cameraMonitorViewId);

        detectionPipeline = new LeftRedPipeline(telemetry);
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