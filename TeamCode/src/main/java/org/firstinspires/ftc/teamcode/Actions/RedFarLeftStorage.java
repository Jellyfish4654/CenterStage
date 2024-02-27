package org.firstinspires.ftc.teamcode.Actions;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;

import org.firstinspires.ftc.teamcode.Framework.DriveStorage;
import org.firstinspires.ftc.teamcode.RoadRunner.MecanumDrive;

public class RedFarLeftStorage {
    public MecanumDrive drive;

    public RedFarLeftStorage(MecanumDrive drive) {
        this.drive = drive;
    }
    public Action LeftPurpleAction() {
        Pose2d startPose = DriveStorage.drive.pose;
        return drive.actionBuilder(startPose)
                .splineToConstantHeading(new Vector2d(-41, -48), Math.toRadians(90))
                .splineToConstantHeading(new Vector2d(-47, -40), Math.toRadians(90))
                .splineToConstantHeading(new Vector2d(-47, -43), Math.toRadians(270))
                .splineToConstantHeading(new Vector2d(-36, -43), Math.toRadians(0))
                .splineToConstantHeading(new Vector2d(-36, -24), Math.toRadians(120))
                .splineToSplineHeading(new Pose2d(-48, -12, Math.toRadians(0)), Math.toRadians(180))
                .build();
    }
    public Action CenterPurpleAction() {
        Pose2d startPose = DriveStorage.drive.pose;
        return drive.actionBuilder(startPose)
                .splineToConstantHeading(new Vector2d(-41, -34.5), Math.toRadians(90))
                .splineToConstantHeading(new Vector2d(-41, -36), Math.toRadians(270))
                .splineToConstantHeading(new Vector2d(-54, -26), Math.toRadians(70))
                .splineToSplineHeading(new Pose2d(-48, -12, Math.toRadians(0)), Math.toRadians(180))
                .build();
    }
    public Action RightPurpleAction() {
        Pose2d startPose = DriveStorage.drive.pose;
        return drive.actionBuilder(startPose)
                .splineTo(new Vector2d(-41, -50), Math.toRadians(90))
                .splineTo(new Vector2d(-31, -37), Math.toRadians(45))
                .splineToConstantHeading(new Vector2d(-31-0.0001, -37-0.0001), Math.toRadians(120))
                .splineToConstantHeading(new Vector2d(-30 + (4 * Math.cos(Math.toRadians(225))), -37 + (4 * Math.sin(Math.toRadians(225)))),Math.toRadians(120))
                .splineToConstantHeading(new Vector2d(-41, -17.5), Math.toRadians(120))
                .splineToSplineHeading(new Pose2d(-48, -12, Math.toRadians(0)), Math.toRadians(180))
                .splineToSplineHeading(new Pose2d(-56, -8.5, Math.toRadians(0)), Math.toRadians(180))
                .build();
    }

    public Action Traj1() {
        Pose2d startPose = DriveStorage.drive.pose;
        return drive.actionBuilder(startPose)
                .splineToConstantHeading(new Vector2d(-12, -10), Math.toRadians(0))
                .splineToConstantHeading(new Vector2d(15, -10), Math.toRadians(0))
                .build();
    }

    public Action YellowLeft() {
        Pose2d startPose = DriveStorage.drive.pose;
        return drive.actionBuilder(startPose)
                .splineToConstantHeading(new Vector2d(32, -10), Math.toRadians(0))
                .splineToConstantHeading(new Vector2d(36, -24), Math.toRadians(270))
                .splineToConstantHeading(new Vector2d(36, -38), Math.toRadians(0))
                .splineToConstantHeading(new Vector2d(40.75, -43), Math.toRadians(0))
                .build();
    }

    public Action YellowCenter() {
        Pose2d startPose = DriveStorage.drive.pose;
        return drive.actionBuilder(startPose)
                .splineToConstantHeading(new Vector2d(32, -10), Math.toRadians(0))
                .splineToConstantHeading(new Vector2d(36, -24), Math.toRadians(270))
                .splineToConstantHeading(new Vector2d(36, -38), Math.toRadians(0))
                .splineToConstantHeading(new Vector2d(40.75, -49), Math.toRadians(0))
                .build();
    }

    public Action YellowRight() {
        Pose2d startPose = DriveStorage.drive.pose;
        return drive.actionBuilder(startPose)
                .splineToConstantHeading(new Vector2d(32, -10), Math.toRadians(0))
                .splineToConstantHeading(new Vector2d(36, -24), Math.toRadians(270))
                .splineToConstantHeading(new Vector2d(36, -36), Math.toRadians(0))
                .splineToConstantHeading(new Vector2d(40.75, -55), Math.toRadians(0))
                .build();
    }
}
