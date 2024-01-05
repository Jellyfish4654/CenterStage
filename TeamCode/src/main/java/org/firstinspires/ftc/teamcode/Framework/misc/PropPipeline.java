package org.firstinspires.ftc.teamcode.Framework.misc;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvPipeline;

public class PropPipeline extends OpenCvPipeline {
    Telemetry telemetry;
    Scalar blueLow = new Scalar(80, 100, 100);
    Scalar blueHigh = new Scalar(125, 255, 255);
    Scalar redLow1 = new Scalar(0, 100, 100);
    Scalar redHigh1 = new Scalar(25, 255, 255);
    Scalar redLow2 = new Scalar(130, 100, 100);
    Scalar redHigh2 = new Scalar(180, 255, 255);
    Mat mat = new Mat();
    Rect leftRect, centerRect, rightRect;

    // Define thresholds
    public static double redThreshold = 2.5;
    public static double blueThreshold = 0.2;

    public PropPipeline(Telemetry telemetry) {
        this.telemetry = telemetry;

        // Initialize rectangles based on the camera's resolution
        int cameraWidth = 1920;
        int cameraHeight = 1080;
        int rectWidth = cameraWidth / 3;
        int rectHeight = cameraHeight;

        leftRect = new Rect(0, 0, rectWidth, rectHeight);
        centerRect = new Rect(rectWidth, 0, rectWidth, rectHeight);
        rightRect = new Rect(2 * rectWidth, 0, rectWidth, rectHeight);
    }

    @Override
    public Mat processFrame(Mat input) {
        Imgproc.cvtColor(input, mat, Imgproc.COLOR_RGB2HSV);

        // Applying Gaussian Blur to the HSV converted image
        Imgproc.GaussianBlur(mat, mat, new Size(5, 5), 0.0);

        // Apply thresholds for red and blue colors
        Mat maskBlue = new Mat();
        Core.inRange(mat, blueLow, blueHigh, maskBlue);

        Mat maskRed1 = new Mat();
        Mat maskRed2 = new Mat();
        Core.inRange(mat, redLow1, redHigh1, maskRed1);
        Core.inRange(mat, redLow2, redHigh2, maskRed2);
        Mat maskRed = new Mat();
        Core.addWeighted(maskRed1, 1.0, maskRed2, 1.0, 0.0, maskRed);

        // Draw rectangles on the stream
        Scalar rectColor = new Scalar(255, 0, 0); // Blue color for rectangle
        Imgproc.rectangle(input, leftRect, rectColor, 2);
        Imgproc.rectangle(input, centerRect, rectColor, 2);
        Imgproc.rectangle(input, rightRect, rectColor, 2);

        // Check each rectangle area for the presence of the prop
        checkArea(maskBlue, leftRect, Sides.Color.BLUE, "Left", blueThreshold);
        checkArea(maskBlue, centerRect, Sides.Color.BLUE, "Center", blueThreshold);
        checkArea(maskBlue, rightRect, Sides.Color.BLUE, "Right", blueThreshold);
        checkArea(maskRed, leftRect, Sides.Color.RED, "Left", redThreshold);
        checkArea(maskRed, centerRect, Sides.Color.RED, "Center", redThreshold);
        checkArea(maskRed, rightRect, Sides.Color.RED, "Right", redThreshold);

        return input;
    }

    private void checkArea(Mat mask, Rect rect, Sides.Color color, String position, double threshold) {
        Mat subMat = mask.submat(rect);
        if (Core.countNonZero(subMat) > threshold) {
            // Prop detected in this area
            telemetry.addData(position + " Area", "Detected " + color.toString());
            Sides.setColor(color);
            Sides.setPosition(Sides.Position.valueOf(position.toUpperCase()));
        }
        subMat.release();
    }
}