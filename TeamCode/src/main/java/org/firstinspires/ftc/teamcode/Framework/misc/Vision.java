package org.firstinspires.ftc.teamcode.Framework.misc;

import com.acmerobotics.roadrunner.Pose2d;
import com.arcrobotics.ftclib.command.Subsystem;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Quaternion;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagGameDatabase;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;
import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvPipeline;

import java.util.ArrayList;
import java.util.List;

public class Vision implements Subsystem {
    public TeamPropPipeline teamPropDetectionPipeline;

    OpenCvCamera camera;

    public VisionPortal visionPortal;

    public AprilTagProcessor tagProcessor;

    private static final String cameraname = "Webcam 1";
    private static final int width = 640;
    private static final int height = 480;

    public boolean cameraOpened = false;

    public Vision(HardwareMap hardwareMap) {

        tagProcessor = new AprilTagProcessor.Builder()
                .setTagLibrary(AprilTagGameDatabase.getCenterStageTagLibrary())
                .setOutputUnits(DistanceUnit.INCH, AngleUnit.RADIANS)
                .build();

        visionPortal = VisionPortal.easyCreateWithDefaults(
                hardwareMap.get(WebcamName.class, cameraname), tagProcessor);

        int cameraMonitorViewId = hardwareMap.appContext.getResources()
                .getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());

        camera = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap
                .get(WebcamName.class, cameraname), cameraMonitorViewId);

    }


    public void startPropDetection() {
        camera.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() {
            @Override
            public void onOpened() {
                cameraOpened = true;
                camera.startStreaming(width, height, OpenCvCameraRotation.UPRIGHT);
                teamPropDetectionPipeline = new TeamPropPipeline();
                camera.setPipeline(teamPropDetectionPipeline);
            }

            @Override
            public void onError(int errorCode) {
            }
        });

    }

    public void stopPropDetection() {
        camera.closeCameraDeviceAsync(() -> cameraOpened = false);
    }

    public void startAprilTagDetection() {
        visionPortal.setProcessorEnabled(tagProcessor,true);
        if (visionPortal.getCameraState() == VisionPortal.CameraState.CAMERA_DEVICE_READY) visionPortal.resumeStreaming();
    }

    public void stopAprilTagDetection() {
        visionPortal.stopStreaming();
    }

    public ArrayList<Pose2d> getAprilTagPoses() {
        ArrayList<AprilTagDetection> tags = new ArrayList<>();

        if (visionPortal.getProcessorEnabled(tagProcessor)) {
            tags = tagProcessor.getDetections();
        }

        ArrayList<Pose2d> poses = new ArrayList<>();

        for (AprilTagDetection tag: tags) {
            if (tag.metadata != null) {

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
                                0,
                                0,
                                0
                        ),
                        new Quaternion(0,0,1f,0, System.nanoTime())
                );
                Transform3d cameraToRobotTransform = robotToCameraTransform.unaryMinusInverse();

                Transform3d robotPose = cameraPose.plus(cameraToRobotTransform);

                poses.add(cameraPose.toPose2d());
            }
        }

        return poses;
    }

    public void closeAll() {
        visionPortal.close();
        stopPropDetection();
    }

    @Override
    public void update() {

    }

    public static class TeamPropPipeline extends OpenCvPipeline {

        Mat gray = new Mat();
        Mat shadowMask = new Mat();
        Mat labImage = new Mat();
        List<Mat> labChannels = new ArrayList<>();
        Mat noShadow = new Mat();
        Mat firstGray = new Mat();
        Mat binaryImg = new Mat();
        Mat blackCountImg = new Mat();

        int frameCount = 1;


        private String elapsedTime = "";

        private String endElapsedTime = "";

        @Override
        public Mat processFrame(Mat frame) {

            long startTime = System.currentTimeMillis();

            if (frameCount == 4) {


                Imgproc.cvtColor(frame, gray, Imgproc.COLOR_BGR2GRAY);

                Imgproc.medianBlur(gray, gray, 15);

                // Apply adaptive thresholding to identify shadows
                Imgproc.adaptiveThreshold(gray, shadowMask, 0, Imgproc.ADAPTIVE_THRESH_MEAN_C, Imgproc.THRESH_BINARY, 11, 10);

                // Invert the shadow mask
                Core.bitwise_not(shadowMask, shadowMask);

                // Convert the original image to LAB color space
                Imgproc.cvtColor(frame, labImage, Imgproc.COLOR_BGR2Lab);

                // Split LAB image into L, A, and B channels
                Core.split(labImage, labChannels);

                // Replace L channel with the shadow-masked L channel
                labChannels.set(0, shadowMask);

                // Merge LAB channels back into one image
                Core.merge(labChannels, labImage);

                // Convert LAB image back to BGR
                Imgproc.cvtColor(labImage, noShadow, Imgproc.COLOR_Lab2BGR);


                //firstGray = noShadow.clone();

                Imgproc.cvtColor(noShadow, firstGray, Imgproc.COLOR_BGR2GRAY);
                Imgproc.threshold(firstGray, blackCountImg, 0, 255, Imgproc.THRESH_BINARY + Imgproc.THRESH_OTSU);

                long endTime = System.currentTimeMillis();

                elapsedTime = ""+(endTime - startTime);


                //blackCountImg = binaryImg.clone();

                int leftImageWidth = blackCountImg.cols() / 3;
                int centerImageWidth = 2 * (leftImageWidth);
                int rightImageWidth = blackCountImg.cols();

                int leftBlackPixelCount = 0;
                int centerBlackPixelCount = 0;
                int rightBlackPixelCount = 0;

                /*
                for (int row = 0; row < blackCountImg.rows(); row++) {
                    for (int col = 0; col < leftImageWidth; col++) {
                        double pixelValue = blackCountImg.get(row, col)[0];
                        if (pixelValue == 0) {
                            leftBlackPixelCount++;
                        }
                    }
                }

                for (int row = 0; row < blackCountImg.rows(); row++) {
                    for (int col = leftImageWidth; col < centerImageWidth; col++) {
                        double pixelValue = blackCountImg.get(row, col)[0];
                        if (pixelValue == 0) {
                            centerBlackPixelCount++;
                        }
                    }
                }


                for (int row = 0; row < blackCountImg.rows(); row++) {
                    for (int col = centerImageWidth; col < rightImageWidth; col++) {
                        double pixelValue = blackCountImg.get(row, col)[0];
                        if (pixelValue == 0) {
                            rightBlackPixelCount++;
                        }
                    }
                }*/

                for (int row = 0; row < blackCountImg.rows(); row++) {
                    for (int col = 0; col < blackCountImg.cols(); col++) {
                        double pixelValue = blackCountImg.get(row, col)[0];

                        if(pixelValue == 0) {
                            // left Image
                            if (col < leftImageWidth) {
                                leftBlackPixelCount++;
                            } else if (col >= leftImageWidth && col < centerImageWidth) {
                                centerBlackPixelCount++;
                            } else if (col >= centerImageWidth) {
                                rightBlackPixelCount++;
                            }
                        }
                    }
                }

                frameCount = 1;

                long time = System.currentTimeMillis();

                endElapsedTime = ""+(time - endTime);
                return blackCountImg;
            } else if (frameCount != 4) {
                frameCount++;
            }
            return frame;
        }

        public String getEndTime(){
            return endElapsedTime;
        }
        public String getElapsedTime(){
            return elapsedTime;
        }


    }
}