package org.firstinspires.ftc.teamcode.Actions.Red.Close.RightYellow.Park.LeftYellow.Park;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;

import org.firstinspires.ftc.teamcode.Framework.PoseStorage;
import org.firstinspires.ftc.teamcode.RoadRunner.MecanumDrive;

public class Storage {
    protected MecanumDrive drive = PoseStorage.drive;


    public Action name() {
        Pose2d startPose = PoseStorage.currentPose; // New Change
        return drive.actionBuilder(startPose)

                .build();
    }

}
