package org.firstinspires.ftc.teamcode.Framework.misc;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvPipeline;

import java.util.ArrayList;
import java.util.List;

public class RedContourPipeline extends OpenCvPipeline {
    Scalar HIGHLIGHT_COLOR = new Scalar(196, 23, 112);

    public static Scalar lowerBoundYCrCb = new Scalar(0.0, 120.0, 80.0); // Increase the lower Cr bound for red
    public static Scalar upperBoundYCrCb = new Scalar(255.0, 180.0, 135.0); // Decrease the upper Cb bound to exclude blues

    public volatile boolean hasError = false;
    public volatile Exception debugException;

    private double regionLeftX;
    private double regionRightX;
    private double regionTopY;
    private double regionBottomY;

    private int cameraWidth;
    private int cameraHeight;

    private int currentLoopCount = 0;
    private int previousLoopCount = 0;

    private final Mat originalImageMat = new Mat();
    private final Mat processedImageMat = new Mat();

    private Rect largestDetectedRectangle = new Rect(600, 1, 1, 1);

    private double largestArea = 0;
    private boolean isFirstDetection = false;

    private final Object synchronizationLock = new Object();

    public RedContourPipeline(double regionLeftX, double regionRightX, double regionTopY, double regionBottomY) {
        this.regionLeftX = regionLeftX;
        this.regionRightX = regionRightX;
        this.regionTopY = regionTopY;
        this.regionBottomY = regionBottomY;
    }

    public void configureScalarLower(double y, double cr, double cb) {
        lowerBoundYCrCb = new Scalar(y, cr, cb);
    }

    public void configureScalarUpper(double y, double cr, double cb) {
        upperBoundYCrCb = new Scalar(y, cr, cb);
    }

    public void configureScalarLower(int y, int cr, int cb) {
        lowerBoundYCrCb = new Scalar(y, cr, cb);
    }

    public void configureScalarUpper(int y, int cr, int cb) {
        upperBoundYCrCb = new Scalar(y, cr, cb);
    }

    public void configureBorders(double regionLeftX, double regionRightX, double regionTopY, double regionBottomY) {
        this.regionLeftX = regionLeftX;
        this.regionRightX = regionRightX;
        this.regionTopY = regionTopY;
        this.regionBottomY = regionBottomY;
    }

    @Override
    public Mat processFrame(Mat input) {
        cameraWidth = input.width();
        cameraHeight = input.height();
        try {
            Imgproc.cvtColor(input, originalImageMat, Imgproc.COLOR_RGB2YCrCb);
            Core.inRange(originalImageMat, lowerBoundYCrCb, upperBoundYCrCb, processedImageMat);
            Mat kernel = Imgproc.getStructuringElement(Imgproc.MORPH_ELLIPSE, new Size(5, 5));
            Imgproc.morphologyEx(processedImageMat, processedImageMat, Imgproc.MORPH_OPEN, kernel);
            Imgproc.morphologyEx(processedImageMat, processedImageMat, Imgproc.MORPH_CLOSE, kernel);
            Imgproc.GaussianBlur(processedImageMat, processedImageMat, new Size(5, 15), 0.00);
            Imgproc.adaptiveThreshold(processedImageMat, processedImageMat, 255, Imgproc.ADAPTIVE_THRESH_MEAN_C, Imgproc.THRESH_BINARY, 11, 2);

            List<MatOfPoint> contours = new ArrayList<>();
            Imgproc.findContours(processedImageMat, contours, new Mat(), Imgproc.RETR_LIST, Imgproc.CHAIN_APPROX_SIMPLE);
            Imgproc.drawContours(input, contours, -1, new Scalar(255, 0, 0));

            synchronized (synchronizationLock) {
                for (MatOfPoint contour : contours) {
                    Point[] contourArray = contour.toArray();

                    if (contourArray.length >= 15) {
                        MatOfPoint2f areaPoints = new MatOfPoint2f(contourArray);
                        Rect rect = Imgproc.boundingRect(areaPoints);
                        if (rect.area() > largestArea
                                && rect.x + (rect.width / 2.0) > (regionLeftX * cameraWidth)
                                && rect.x + (rect.width / 2.0) < cameraWidth - (regionRightX * cameraWidth)
                                && rect.y + (rect.height / 2.0) > (regionTopY * cameraHeight)
                                && rect.y + (rect.height / 2.0) < cameraHeight - (regionBottomY * cameraHeight)
                                || currentLoopCount - previousLoopCount > 6
                                && rect.x + (rect.width / 2.0) > (regionLeftX * cameraWidth)
                                && rect.x + (rect.width / 2.0) < cameraWidth - (regionRightX * cameraWidth)
                                && rect.y + (rect.height / 2.0) > (regionTopY * cameraHeight)
                                && rect.y + (rect.height / 2.0) < cameraHeight - (regionBottomY * cameraHeight)
                        ) {
                            largestArea = rect.area();
                            largestDetectedRectangle = rect;
                            previousLoopCount++;
                            currentLoopCount = previousLoopCount;
                            isFirstDetection = true;
                        } else if (currentLoopCount - previousLoopCount > 10) {
                            largestArea = new Rect().area();
                            largestDetectedRectangle = new Rect();
                        }
                        areaPoints.release();
                    }
                    contour.release();
                }
                if (contours.isEmpty()) {
                    largestDetectedRectangle = new Rect(600, 1, 1, 1);
                }
            }
            if (isFirstDetection && largestDetectedRectangle.area() > 500) {
                Imgproc.rectangle(input, largestDetectedRectangle, new Scalar(0, 255, 0), 2);
            }
            Imgproc.rectangle(input, new Rect(
                    (int) (regionLeftX * cameraWidth),
                    (int) (regionTopY * cameraHeight),
                    (int) (cameraWidth - (regionRightX * cameraWidth) - (regionLeftX * cameraWidth)),
                    (int) (cameraHeight - (regionBottomY * cameraHeight) - (regionTopY * cameraHeight))
            ), HIGHLIGHT_COLOR, 2);

            Imgproc.putText(input, "Area: " + getRectArea() + " Midpoint: " + getRectMidpointXY().x + " , " + getRectMidpointXY().y, new Point(5, cameraHeight - 5), 0, 0.6, new Scalar(255, 255, 255), 2);

            currentLoopCount++;
        } catch (Exception e) {
            debugException = e;
            hasError = true;
        }
        return input;
    }

    public int getRectHeight() {
        synchronized (synchronizationLock) {
            return largestDetectedRectangle.height;
        }
    }
    public int getRectWidth() {
        synchronized (synchronizationLock) {
            return largestDetectedRectangle.width;
        }
    }
    public int getRectX() {
        synchronized (synchronizationLock) {
            return largestDetectedRectangle.x;
        }
    }
    public int getRectY() {
        synchronized (synchronizationLock) {
            return largestDetectedRectangle.y;
        }
    }

    public double getRectMidpointX() {
        synchronized (synchronizationLock) {
            return getRectX() + (getRectWidth() / 2.0);
        }
    }
    public double getRectMidpointY() {
        synchronized (synchronizationLock) {
            return getRectY() + (getRectHeight() / 2.0);
        }
    }
    public Point getRectMidpointXY() {
        synchronized (synchronizationLock) {
            return new Point(getRectMidpointX(), getRectMidpointY());
        }
    }
    public double getRectArea() {
        synchronized (synchronizationLock) {
            return largestDetectedRectangle.area();
        }
    }
}
