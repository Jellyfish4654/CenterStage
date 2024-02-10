package org.firstinspires.ftc.teamcode.Framework.misc;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;

import org.checkerframework.checker.units.qual.A;
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

	public Action getRedFarRight_CenterPurpleAction()
	{
		return drive.actionBuilder(drive.pose)
				.splineTo(new Vector2d(-36.5, -34.5), Math.toRadians(60))
				.splineToConstantHeading(new Vector2d(-36.5 + (6 * Math.cos(Math.toRadians(240))), -34.5 + (6 * Math.sin(Math.toRadians(240)))), Math.toRadians(240))
				.splineToConstantHeading(new Vector2d(-36.5 + (4 * Math.cos(Math.toRadians(240))) + (20 * Math.cos(Math.toRadians(150))), -34.5 + (4 * Math.sin(Math.toRadians(240))) + (20 * Math.sin(Math.toRadians(150)))), Math.toRadians(125))
				.splineToSplineHeading(new Pose2d(-48, -12, Math.toRadians(0)), Math.toRadians(180))
					.build();
	}

	public Action getRedFarRight_RightPurpleAction()
	{
		return drive.actionBuilder(drive.pose)
					.splineTo(new Vector2d(-39, -50), Math.toRadians(90))
					.splineTo(new Vector2d(-30, -36), Math.toRadians(45))
					.splineToConstantHeading(new Vector2d(-30 + (2 * Math.cos(Math.toRadians(225))), -36 + (2 * Math.sin(Math.toRadians(225)))), Math.toRadians(225))
					.splineToConstantHeading(new Vector2d(-30 + (2 * Math.cos(Math.toRadians(225))) + (6 * Math.cos(Math.toRadians(135))), -36 + (2 * Math.sin(Math.toRadians(225))) + (6 * Math.sin(Math.toRadians(135)))), Math.toRadians(115.82))
					.splineToConstantHeading(new Vector2d(-41, -17.5), Math.toRadians(115.82))
					.splineToSplineHeading(new Pose2d(-48, -12, Math.toRadians(0)), Math.toRadians(180))
					.build();
	}

	public Action getBlueCloseRight_LeftPurpleAction()
	{
		return drive.actionBuilder(drive.pose)
					.splineTo(new Vector2d(15, 48), Math.toRadians(270))
					.splineTo(new Vector2d(18, 38), Math.toRadians(315))
					.splineToConstantHeading(new Vector2d(18 + (4 * Math.cos(Math.toRadians(135))), 38 + (4 * Math.sin(Math.toRadians(135)))), Math.toRadians(135))
					.splineToSplineHeading(new Pose2d(28, 44.17 + (4 * Math.sin(Math.toRadians(135))), Math.toRadians(0)), Math.toRadians(0))
					.splineToConstantHeading(new Vector2d(40, 36), Math.toRadians(0))
					.build();
	}

	public Action getBlueCloseRight_CenterPurpleAction()
	{
		return drive.actionBuilder(drive.pose)
					.splineTo(new Vector2d(15, 33), Math.toRadians(270))
					.splineToConstantHeading(new Vector2d(15, 36), Math.toRadians(90))
					.splineToSplineHeading(new Pose2d(40, 36, Math.toRadians(0)), Math.toRadians(0))
					.build();
	}

	public Action getBlueCloseRight_RightPurpleAction()
	{
		return drive.actionBuilder(drive.pose)
					.splineTo(new Vector2d(15, 50), Math.toRadians(270))
					.splineTo(new Vector2d(5, 36), Math.toRadians(225))
					.splineToConstantHeading(new Vector2d(5 + (4 * Math.cos(Math.toRadians(45))), 36 + (4 * Math.sin(Math.toRadians(45)))), Math.toRadians(45))
					.splineToSplineHeading(new Pose2d(40, 36, Math.toRadians(0)), Math.toRadians(0))
					.build();
	}

	public Action getBlueFarRight_LeftPurpleAction()
	{
		return drive.actionBuilder(drive.pose)
					.splineTo(new Vector2d(-39, 50), Math.toRadians(270))
					.splineTo(new Vector2d(-30, 36), Math.toRadians(315))
					.splineToConstantHeading(new Vector2d(-30 + (2 * Math.cos(Math.toRadians(135))), 36 + (2 * Math.sin(Math.toRadians(135)))), Math.toRadians(135))
					.splineToConstantHeading(new Vector2d(-30 + (2 * Math.cos(Math.toRadians(135))) + (6 * Math.cos(Math.toRadians(225))), 36 + (2 * Math.sin(Math.toRadians(135))) + (6 * Math.sin(Math.toRadians(225)))), Math.toRadians(244.18)) // Adjusting angle to 244.18 degrees
					.splineToConstantHeading(new Vector2d(-41, 17.5), Math.toRadians(244.18))
					.splineToSplineHeading(new Pose2d(-48, 12, Math.toRadians(0)), Math.toRadians(180))
					.build();
	}

	public Action getBlueFarRight_CenterPurpleAction()
	{
		return drive.actionBuilder(drive.pose)
					.splineTo(new Vector2d(-36.5, 34.5), Math.toRadians(300))
					.splineToConstantHeading(new Vector2d(-36.5 + (6 * Math.cos(Math.toRadians(120))), 34.5 + (6 * Math.sin(Math.toRadians(120)))), Math.toRadians(120))
					.splineToConstantHeading(new Vector2d(-36.5 + (4 * Math.cos(Math.toRadians(120))) + (20 * Math.cos(Math.toRadians(210))), 34.5 + (4 * Math.sin(Math.toRadians(120))) + (20 * Math.sin(Math.toRadians(210)))), Math.toRadians(235))
					.splineToSplineHeading(new Pose2d(-48, 12, Math.toRadians(0)), Math.toRadians(180))
					.build();
	}

	public Action getBlueFarRight_RightPurpleAction()
	{
		return drive.actionBuilder(drive.pose)
					.splineTo(new Vector2d(-39, 50), Math.toRadians(270))
					.splineTo(new Vector2d(-30, 36), Math.toRadians(315))
					.splineToConstantHeading(new Vector2d(-30 + (2 * Math.cos(Math.toRadians(135))), 36 + (2 * Math.sin(Math.toRadians(135)))), Math.toRadians(135))
					.splineToConstantHeading(new Vector2d(-30 + (2 * Math.cos(Math.toRadians(135))) + (6 * Math.cos(Math.toRadians(225))), 36 + (2 * Math.sin(Math.toRadians(135))) + (6 * Math.sin(Math.toRadians(225)))), Math.toRadians(244.18)) // Adjusting angle to 244.18 degrees
					.splineToConstantHeading(new Vector2d(-41, 17.5), Math.toRadians(244.18))
					.splineToSplineHeading(new Pose2d(-48, 12, Math.toRadians(0)), Math.toRadians(180))
					.build();
	}

	public Action getRedTraj()
	{
		return drive.actionBuilder(new Pose2d(-48, -12, Math.toRadians(0)))
					.splineToConstantHeading(new Vector2d(-12, -10), Math.toRadians(0))
					.splineToConstantHeading(new Vector2d(15, -10), Math.toRadians(0))
					.build();
	}

	public Action getBlueTraj()
	{
		return drive.actionBuilder(new Pose2d(-48, 12, Math.toRadians(180)))
					.splineToConstantHeading(new Vector2d(-12, 10), Math.toRadians(0))
					.splineToConstantHeading(new Vector2d(15, 10), Math.toRadians(0))
					.build();
	}

	public Action getRedFarYellowLeft()
	{
		return drive.actionBuilder(new Pose2d(15, -10, Math.toRadians(0)))
					.splineToConstantHeading(new Vector2d(32, -10), Math.toRadians(0))
					.splineToConstantHeading(new Vector2d(36, -24), Math.toRadians(270))
					.splineToConstantHeading(new Vector2d(38, -36), Math.toRadians(0))
					.splineToConstantHeading(new Vector2d(48, -29), Math.toRadians(0))
					.build();
	}

	public Action getRedFarYellowCenter()
	{
		return drive.actionBuilder(new Pose2d(15, -10, Math.toRadians(0)))
					.splineToConstantHeading(new Vector2d(32, -10), Math.toRadians(0))
					.splineToConstantHeading(new Vector2d(36, -24), Math.toRadians(270))
					.splineToConstantHeading(new Vector2d(38, -36), Math.toRadians(0))
					.splineToConstantHeading(new Vector2d(48, -35), Math.toRadians(0))
					.build();
	}

	public Action getRedFarYellowRight()
	{
		return drive.actionBuilder(new Pose2d(15, -10, Math.toRadians(0)))
					.splineToConstantHeading(new Vector2d(32, -10), Math.toRadians(0))
					.splineToConstantHeading(new Vector2d(36, -24), Math.toRadians(270))
					.splineToConstantHeading(new Vector2d(38, -36), Math.toRadians(0))
					.splineToConstantHeading(new Vector2d(48, -41), Math.toRadians(0))
					.build();
	}

	public Action getBlueFarYellowLeft()
	{
		return drive.actionBuilder(new Pose2d(15, 10, Math.toRadians(0)))
					.splineToConstantHeading(new Vector2d(32, 10), Math.toRadians(0))
					.splineToConstantHeading(new Vector2d(36, 24), Math.toRadians(90))
					.splineToConstantHeading(new Vector2d(38, 36), Math.toRadians(0))
					.splineToConstantHeading(new Vector2d(48, 41), Math.toRadians(0))
					.build();
	}

	public Action getBlueFarYellowCenter()
	{
		return drive.actionBuilder(new Pose2d(15, 10, Math.toRadians(0)))
					.splineToConstantHeading(new Vector2d(32, 10), Math.toRadians(0))
					.splineToConstantHeading(new Vector2d(36, 24), Math.toRadians(90))
					.splineToConstantHeading(new Vector2d(38, 36), Math.toRadians(0))
					.splineToConstantHeading(new Vector2d(48, 35), Math.toRadians(0))
					.build();
	}

	public Action getBlueFarYellowRight()
	{
		return drive.actionBuilder(new Pose2d(15, 10, Math.toRadians(0)))
					.splineToConstantHeading(new Vector2d(32, 10), Math.toRadians(0))
					.splineToConstantHeading(new Vector2d(36, 24), Math.toRadians(90))
					.splineToConstantHeading(new Vector2d(38, 36), Math.toRadians(0))
					.splineToConstantHeading(new Vector2d(48, 29), Math.toRadians(0))
					.build();
	}


	public Action getRedYellowParkLeft()
	{
		return drive.actionBuilder(new Pose2d(48, -29, Math.toRadians(0)))
					.splineToConstantHeading(new Vector2d(46, -29), Math.toRadians(180))
					.splineToConstantHeading(new Vector2d(46, -10), Math.toRadians(0))
					.splineToConstantHeading(new Vector2d(58, -10), Math.toRadians(0))
					.build();
	}

	public Action getRedYellowParkCenter()
	{
		return drive.actionBuilder(new Pose2d(48, -35, Math.toRadians(0)))
					.splineToConstantHeading(new Vector2d(46, -35), Math.toRadians(180))
					.splineToConstantHeading(new Vector2d(46, -10), Math.toRadians(0))
					.splineToConstantHeading(new Vector2d(58, -10), Math.toRadians(0))
					.build();
	}

	public Action getRedYellowParkRight()
	{
		return drive.actionBuilder(new Pose2d(48, -41, Math.toRadians(0)))
					.splineToConstantHeading(new Vector2d(46, -41), Math.toRadians(180))
					.splineToConstantHeading(new Vector2d(46, -10), Math.toRadians(0))
					.splineToConstantHeading(new Vector2d(58, -10), Math.toRadians(0))
					.build();
	}

	public Action getBlueYellowParkLeft()
	{
		return drive.actionBuilder(new Pose2d(48, 41, Math.toRadians(0)))
					.splineToConstantHeading(new Vector2d(46, 41), Math.toRadians(180))
					.splineToConstantHeading(new Vector2d(46, 10), Math.toRadians(0))
					.splineToConstantHeading(new Vector2d(58, 10), Math.toRadians(0))
					.build();
	}

	public Action getBlueYellowParkCenter()
	{
		return drive.actionBuilder(new Pose2d(48, 35, Math.toRadians(0)))
					.splineToConstantHeading(new Vector2d(46, 35), Math.toRadians(180))
					.splineToConstantHeading(new Vector2d(46, 10), Math.toRadians(0))
					.splineToConstantHeading(new Vector2d(58, 10), Math.toRadians(0))
					.build();
	}

	public Action getBlueYellowParkRight()
	{
		return drive.actionBuilder(new Pose2d(48, 29, Math.toRadians(0)))
					.splineToConstantHeading(new Vector2d(46, 29), Math.toRadians(180))
					.splineToConstantHeading(new Vector2d(46, 10), Math.toRadians(0))
					.splineToConstantHeading(new Vector2d(58, 10), Math.toRadians(0))
					.build();
	}

	public Action getRedCloseYellowLeft()
	{
		return drive.actionBuilder(new Pose2d(58, -10, Math.toRadians(0)))
				.splineToConstantHeading(new Vector2d(48, -29), Math.toRadians(0))
				.build();
	}
	public Action getRedCloseYellowCenter()
	{
		return  drive.actionBuilder(new Pose2d(58, -10, Math.toRadians(0)))
				.splineToConstantHeading(new Vector2d(48, -35), Math.toRadians(0))
				.build();
	}
	public Action getRedCloseYellowRight()
	{
		return drive.actionBuilder(new Pose2d(58, -10, Math.toRadians(0)))
				.splineToConstantHeading(new Vector2d(48, -41), Math.toRadians(0))
				.build();
	}
	public Action getBlueCloseYellowLeft()
	{
		return drive.actionBuilder(new Pose2d(58, 10, Math.toRadians(0)))
				.splineToConstantHeading(new Vector2d(48, 41), Math.toRadians(0))
				.build();
	}
	public Action getBlueCloseYellowCenter()
	{
		return  drive.actionBuilder(new Pose2d(58, 10, Math.toRadians(0)))
				.splineToConstantHeading(new Vector2d(48, 35), Math.toRadians(0))
				.build();
	}
	public Action getBlueCloseYellowRight()
	{
		return  drive.actionBuilder(new Pose2d(58, 10, Math.toRadians(0)))
				.splineToConstantHeading(new Vector2d(48, 29), Math.toRadians(0))
				.build();
	}
}
