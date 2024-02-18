package org.firstinspires.ftc.teamcode.Framework;

import com.acmerobotics.roadrunner.Pose2d;

import org.firstinspires.ftc.teamcode.RoadRunner.MecanumDrive;

public class PoseStorage {
    public static Pose2d currentPose = new Pose2d(0,0,0);

    public static MecanumDrive drive;

    public static void initialize(MecanumDrive initialDrive) {
        drive = initialDrive;
    }

    public static MecanumDrive getDrive() {
        return drive;
    }
}