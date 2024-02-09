package org.firstinspires.ftc.teamcode.Framework.misc;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvPipeline;

public class RedPipeline extends OpenCvPipeline
{
	Telemetry telemetry;
	// HSV thresholds for red color detection
	Scalar redLow1 = new Scalar(0, 100, 20);
	Scalar redHigh1 = new Scalar(10, 255, 255);
	Scalar redLow2 = new Scalar(160, 100, 20);
	Scalar redHigh2 = new Scalar(180, 255, 255);

	// Mats for image processing
	Mat mat = new Mat();
	Mat maskRed1 = new Mat();
	Mat maskRed2 = new Mat();
	Mat maskRed = new Mat();

	// Rectangles for dividing the camera view
	Rect leftRect, centerRect, rightRect;

	// Camera resolution
	private static final int CAMERA_WIDTH = 1920;
	private static final int CAMERA_HEIGHT = 1080;

	public RedPipeline(Telemetry telemetry)
	{
		this.telemetry = telemetry;

		// Divide camera view into rectangles
		int rectWidth = CAMERA_WIDTH / 3;
		int rectHeight = CAMERA_HEIGHT;

		leftRect = new Rect(0, 330, 500, 750);
		centerRect = new Rect(660, 100, 600, 750);
		rightRect = new Rect(1070, 330, 500, 750);
	}

	static int leftCount;
	static int centerCount;
	static int rightCount;

	public static int getLeft()
	{
		return leftCount;
	}

	public static int getCenter()
	{
		return centerCount;
	}

	public static int getRight()
	{
		return rightCount;
	}

	@Override
	public Mat processFrame(Mat input)
	{
		// Convert image from RGB to HSV color space
		Imgproc.cvtColor(input, mat, Imgproc.COLOR_RGB2HSV);
		Imgproc.GaussianBlur(mat, mat, new Size(5, 5), 0.0);
		// Apply thresholds to detect red color
		Core.inRange(mat, redLow1, redHigh1, maskRed1);
		Core.inRange(mat, redLow2, redHigh2, maskRed2);
		Core.bitwise_or(maskRed1, maskRed2, maskRed);

		// Count red pixels in each rectangle
		leftCount = countRed(maskRed, leftRect);
		centerCount = countRed(maskRed, centerRect);
		rightCount = countRed(maskRed, rightRect);

		// Determine the position with the most red
		Sides.Position detectedPosition = determinePosition(leftCount, centerCount, rightCount);

		// Update the position in the Sides class
		Sides.setPosition(detectedPosition);

		// Draw rectangles on the input frame for visualizing the zones
		Scalar leftColor = new Scalar(255, 0, 0);   // Red color for left zone
		Scalar centerColor = new Scalar(0, 255, 0); // Green color for center zone
		Scalar rightColor = new Scalar(0, 0, 255);  // Blue color for right zone
		Imgproc.rectangle(input, leftRect, leftColor, 2);
		Imgproc.rectangle(input, centerRect, centerColor, 2);
		Imgproc.rectangle(input, rightRect, rightColor, 2);

		// Telemetry for debugging
		telemetry.addData("Red Detected on", detectedPosition);
		return input;


	}

	private int countRed(Mat mask, Rect rect)
	{
		Mat subMat = mask.submat(rect);
		int count = Core.countNonZero(subMat);
		subMat.release();
		return count;
	}

	private Sides.Position determinePosition(int leftCount, int centerCount, int rightCount)
	{
		int leftThreshold = 25000; // Minimum difference to consider a change in position
		int rightThreshold = 25000;
		int centerThreshold = 25000;
		boolean leftIsValid = leftCount > leftThreshold;
		boolean rightIsValid = rightCount > rightThreshold;
		boolean centerIsValid = centerCount > centerThreshold;
		if (leftIsValid && (!rightIsValid || leftCount > rightCount)
			&& (!centerIsValid || leftCount > centerCount))
		{
			return Sides.Position.LEFT;
		}
		else if (centerIsValid && (!rightIsValid || centerCount > rightCount)
				 && (!leftIsValid || centerCount > leftCount))
		{
			return Sides.Position.CENTER;
		}
		else if (rightIsValid && (!leftIsValid || rightCount > leftCount)
				 && (!centerIsValid || rightCount > centerCount))
		{
			return Sides.Position.RIGHT;
		}
		else
		{
			return Sides.Position.UNKNOWN;
		}
	}

}