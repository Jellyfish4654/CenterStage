package org.firstinspires.ftc.teamcode.Framework;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;

import org.firstinspires.ftc.teamcode.RoadRunner.MecanumDrive;

public class ActionStorage {
    private MecanumDrive drive;

    public ActionStorage(MecanumDrive drive) {
        this.drive = drive;
    }

    public Action getLeftPurpleAction() {
        return drive.actionBuilder(drive.pose)
                .splineTo(new Vector2d(15, -48), Math.toRadians(90))
                .splineTo(new Vector2d(5, -34), Math.toRadians(135))
                .setReversed(true)
                .splineTo(new Vector2d(5-(4*Math.sin(Math.toRadians(315))), -34+(4*Math.sin(Math.toRadians(315))) ), Math.toRadians(315))
                .setReversed(false)
                .setTangent(Math.toRadians(-15))
                .splineToLinearHeading(new Pose2d(40, -36, Math.toRadians(0)), Math.toRadians(0))
                .build();
    }
}
