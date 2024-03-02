package org.firstinspires.ftc.teamcode.Framework.misc;

import com.acmerobotics.roadrunner.Pose2d;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Quaternion;
import org.firstinspires.ftc.teamcode.RoadRunner.MecanumDrive;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagGameDatabase;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

import java.util.ArrayList;

public class Vision {

	private VisionPortal visionPortal;
	private static AprilTagProcessor tagProcessor;
	private static final String cameraname = "Webcam 1";

	public Vision(HardwareMap hardwareMap) {
		tagProcessor = new AprilTagProcessor.Builder()
				.setTagLibrary(AprilTagGameDatabase.getCenterStageTagLibrary())
				.setOutputUnits(DistanceUnit.INCH, AngleUnit.RADIANS)
				.build();

		visionPortal = VisionPortal.easyCreateWithDefaults(
				hardwareMap.get(WebcamName.class, cameraname), tagProcessor);
	}

	public void startAprilTagDetection() {
		visionPortal.setProcessorEnabled(tagProcessor, true);
		if (visionPortal.getCameraState() == VisionPortal.CameraState.CAMERA_DEVICE_READY) {
			visionPortal.resumeStreaming();
		}
	}

	public void stopAprilTagDetection() {
		visionPortal.stopStreaming();
	}

	public static ArrayList<Pose2d> getAprilTagPoses() {
		ArrayList<AprilTagDetection> tags;
		if (tagProcessor == null) {
			return new ArrayList<>();
		} else {
			tags = tagProcessor.getDetections();
		}
		ArrayList<Pose2d> poses = new ArrayList<>();
		if (tags != null) {
			for (AprilTagDetection tag : tags) {
				if (tag != null && tag.metadata != null) {
					Transform3d tagPose = new Transform3d(
							tag.metadata.fieldPosition,
							tag.metadata.fieldOrientation
					);

					Transform3d cameraToTagTransform = new Transform3d(
							new VectorF(
									(float) tag.rawPose.x,
									(float) tag.rawPose.y,
									(float) tag.rawPose.z
							),
							Transform3d.MatrixToQuaternion(tag.rawPose.R)
					);

					Transform3d tagToCameraTransform = cameraToTagTransform.unaryMinusInverse();
					Transform3d cameraPose = tagPose.plus(tagToCameraTransform);

					Transform3d robotToCameraTransform = new Transform3d(
							new VectorF(
									-1f,
									7.5f,
									4.11024f
							),
							new Quaternion(0, 0, 1f, 0, System.nanoTime())
					);

					Transform3d cameraToRobotTransform = robotToCameraTransform.unaryMinusInverse();
					Transform3d robotPose = cameraPose.plus(cameraToRobotTransform);
					poses.add(robotPose.toPose2d());
				}
			}
		}
		return poses;
	}

	public static Pose2d processTagPoses(ArrayList<Pose2d> tagPoses, MecanumDrive drive) {
		if (!tagPoses.isEmpty()) {
			drive.pose = tagPoses.get(0);
		}
		return drive.pose;
	}

	public void closeAll() {
		if (visionPortal != null) {
			visionPortal.close();
		}
		stopAprilTagDetection();
	}
}
