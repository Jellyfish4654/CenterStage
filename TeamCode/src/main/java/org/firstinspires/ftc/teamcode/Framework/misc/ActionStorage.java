package org.firstinspires.ftc.teamcode.Framework.misc;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;

import org.firstinspires.ftc.teamcode.RoadRunner.MecanumDrive;

public class ActionStorage
{
    private MecanumDrive drive;

    public ActionStorage(MecanumDrive drive)
    {
        this.drive = drive;
    }

    public Action getRedCloseRight_LeftPurpleAction()
    {
        return drive.actionBuilder(drive.pose)
                .splineTo(new Vector2d(15, -50), Math.toRadians(90))
                .splineTo(new Vector2d(5, -36), Math.toRadians(135))
                .splineToConstantHeading(new Vector2d(5 + (4 * Math.cos(Math.toRadians(315))), -36 + (4 * Math.sin(Math.toRadians(315)))), Math.toRadians(315))
                .splineToSplineHeading(new Pose2d(40, -36, Math.toRadians(0)), Math.toRadians(0))
                .build();
    }

    public Action getRedCloseRight_CenterPurpleAction()
    {
        return drive.actionBuilder(drive.pose)
                .splineTo(new Vector2d(15, -33), Math.toRadians(90))
                .splineToConstantHeading(new Vector2d(15, -36), Math.toRadians(270))
                .splineToSplineHeading(new Pose2d(40, -36, Math.toRadians(0)), Math.toRadians(0))
                .build();
    }

    public Action getRedCloseRight_RightPurpleAction()
    {
        return drive.actionBuilder(drive.pose)
                .splineTo(new Vector2d(15, -48), Math.toRadians(90))
                .splineTo(new Vector2d(18, -38), Math.toRadians(45))
                .splineToConstantHeading(new Vector2d(18 + (4 * Math.cos(Math.toRadians(225))), -38 + (4 * Math.sin(Math.toRadians(225)))), Math.toRadians(225))
                .splineToSplineHeading(new Pose2d(28, -44.17 + (4 * Math.sin(Math.toRadians(225))), Math.toRadians(0)), Math.toRadians(0))
                .splineToConstantHeading(new Vector2d(40, -36), Math.toRadians(0))
                .build();
    }

    public Action getRedFarRight_LeftPurpleAction()
    {
        return drive.actionBuilder(drive.pose)
                .splineTo(new Vector2d(-39, -48), Math.toRadians(90))
                .splineTo(new Vector2d(-43, -38), Math.toRadians(135))
                .splineToConstantHeading(new Vector2d(-43 + (4 * Math.cos(Math.toRadians(315))), -38 + (4 * Math.sin(Math.toRadians(315)))), Math.toRadians(315))
                .splineToConstantHeading(new Vector2d(-32, -32), Math.toRadians(135))
                .splineToSplineHeading(new Pose2d(-48, -12, Math.toRadians(0)), Math.toRadians(180))
                .build();
    }

    public Action getRedFarRight_CentertPurpleAction()
    {
        return drive.actionBuilder(drive.pose)
                .splineTo(new Vector2d(-34.5, -31), Math.toRadians(60))
                .splineToConstantHeading(new Vector2d(-34.5 + (4 * Math.cos(Math.toRadians(240))), -31 + (4 * Math.sin(Math.toRadians(240)))), Math.toRadians(240))
                .splineToConstantHeading(new Vector2d(-34.5 + (4 * Math.cos(Math.toRadians(240))) + (6 * Math.cos(Math.toRadians(150))), -31 + (4 * Math.sin(Math.toRadians(240))) + (6 * Math.sin(Math.toRadians(150)))), Math.toRadians(125))
                .splineToSplineHeading(new Pose2d(-48, -12, Math.toRadians(0)), Math.toRadians(180))
                .build();
    }
}
