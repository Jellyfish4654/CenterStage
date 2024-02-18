package org.firstinspires.ftc.teamcode.Actions.Blue.Far;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;

import org.firstinspires.ftc.teamcode.Framework.PoseStorage;
import org.firstinspires.ftc.teamcode.RoadRunner.MecanumDrive;

public class Storage {
    protected static MecanumDrive drive = PoseStorage.getDrive();


    public Action name() {
        Pose2d startPose = PoseStorage.currentPose; // New Change
        return drive.actionBuilder(startPose)

                .build();
    }

}
