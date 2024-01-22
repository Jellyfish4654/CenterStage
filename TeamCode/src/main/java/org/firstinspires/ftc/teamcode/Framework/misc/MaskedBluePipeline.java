package org.firstinspires.ftc.teamcode.Framework.misc;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvPipeline;

public class MaskedBluePipeline extends OpenCvPipeline
{
    Telemetry telemetry;
    // HSV thresholds for blue color detection
    Scalar blueLow = new Scalar(80, 25, 25);
    Scalar blueHigh = new Scalar(145, 255, 255);

    // Mats for image processing
    Mat mat = new Mat();
    Mat maskBlue = new Mat();
    Mat maskedInput = new Mat(); // Mat to store masked input

    // Rectangles for dividing the camera view
    Rect leftRect, centerRect, rightRect;

    // Camera resolution
    private static final int CAMERA_WIDTH = 1920;
    private static final int CAMERA_HEIGHT = 1080;

    public MaskedBluePipeline(Telemetry telemetry)
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
        Imgproc.GaussianBlur(mat, mat, new Size(5, 5), 0.0);
        // Apply thresholds to detect blue color
        Core.inRange(mat, blueLow, blueHigh, maskBlue);

        // Apply the mask to the input image to show only the detected blue areas
        Core.bitwise_and(input, input, maskedInput, maskBlue);

        // No need to draw rectangles on the input frame for visualizing the zones
        // Since we are only displaying the masked areas

        // Update the telemetry data with the detected position
        telemetry.addData("Masked Blue Detected on", getDetectedPosition(maskBlue));
        return maskedInput; // Return the masked input
    }

    private String getDetectedPosition(Mat mask)
    {
        int leftCount = Core.countNonZero(mask.submat(leftRect));
        int centerCount = Core.countNonZero(mask.submat(centerRect));
        int rightCount = Core.countNonZero(mask.submat(rightRect));

        // Determine the position with the most blue
        if (leftCount > centerCount && leftCount > rightCount)
        {
            return "LEFT";
        }
        else if (centerCount > leftCount && centerCount > rightCount)
        {
            return "CENTER";
        }
        else if (rightCount > centerCount && rightCount > leftCount)
        {
            return "RIGHT";
        }
        else
        {
            return "UNKNOWN";
        }
    }

    public void cleanup()
    {
        mat.release();
        maskBlue.release();
        maskedInput.release();
    }
}