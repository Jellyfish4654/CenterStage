package org.firstinspires.ftc.teamcode.Framework.misc;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvPipeline;

public class MaskedRedPipeline extends OpenCvPipeline
{
    Telemetry telemetry;
    // HSV thresholds for red color detection
    Scalar redLow1 = new Scalar(0, 100, 20);
    Scalar redHigh1 = new Scalar(10, 255, 255);
    Scalar redLow2 = new Scalar(160, 100, 20);
    Scalar redHigh2 = new Scalar(180, 255, 255);

    // Mats for image processing
    Mat mat = new Mat();
    Mat maskRed = new Mat();
    Mat maskedInput = new Mat();

    // Rectangles for dividing the camera view
    Rect leftRect, centerRect, rightRect;

    // Camera resolution
    private static final int CAMERA_WIDTH = 1920;
    private static final int CAMERA_HEIGHT = 1080;

    public MaskedRedPipeline(Telemetry telemetry)
    {
        this.telemetry = telemetry;

        // Divide camera view into rectangles
        int rectWidth = CAMERA_WIDTH / 3;
        int rectHeight = CAMERA_HEIGHT;

        leftRect = new Rect(0, 0, rectWidth, rectHeight);
        centerRect = new Rect(rectWidth, 0, rectWidth, rectHeight);
        rightRect = new Rect(2 * rectWidth, 0, rectWidth, rectHeight);
    }

    @Override
    public Mat processFrame(Mat input)
    {
        // Convert image from RGB to HSV color space
        Imgproc.cvtColor(input, mat, Imgproc.COLOR_RGB2HSV);

        // Apply Gaussian Blur to smooth out the image
        Imgproc.GaussianBlur(mat, mat, new Size(5, 5), 0.0);

        // Apply thresholds to detect red color
        Core.inRange(mat, redLow1, redHigh1, maskRed);
        Mat maskRed2 = new Mat();
        Core.inRange(mat, redLow2, redHigh2, maskRed2);
        Core.bitwise_or(maskRed, maskRed2, maskRed);

        // Apply the mask to the input image to display only the red parts
        Core.bitwise_and(input, input, maskedInput, maskRed);

        // Release the temporary Mat
        maskRed2.release();

        // Return the masked image that shows only the detected red color
        return maskedInput;
    }

    public void cleanup()
    {
        mat.release();
        maskRed.release();
        maskedInput.release();
    }
}