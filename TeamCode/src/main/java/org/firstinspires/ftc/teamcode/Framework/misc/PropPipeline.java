package org.firstinspires.ftc.teamcode.Framework.misc;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import com.acmerobotics.dashboard.config.Config;
import org.firstinspires.ftc.robotcore.external.function.Consumer;
import org.firstinspires.ftc.robotcore.external.function.Continuation;
import org.firstinspires.ftc.robotcore.external.stream.CameraStreamSource;
import org.firstinspires.ftc.robotcore.internal.camera.calibration.CameraCalibration;
import org.firstinspires.ftc.vision.VisionProcessor;
import org.opencv.android.Utils;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfKeyPoint;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvPipeline;

import java.util.concurrent.atomic.AtomicReference;

@Config
public class PropPipeline extends OpenCvPipeline implements VisionProcessor, CameraStreamSource {
    private final AtomicReference<Bitmap> lastFrame = new AtomicReference<>(Bitmap.createBitmap(1, 1, Bitmap.Config.RGB_565));
    private AutoSides.Position location = AutoSides.Position.RIGHT;
    public MatOfKeyPoint keyPoints = new MatOfKeyPoint();
    private Rect leftZoneArea;
    private Rect centerZoneArea;
    private Mat finalMat = new Mat();

    public static int blueLeftX = 800;
    public static int blueLeftY = 550;
    public static int blueCenterX = 1175;
    public static int blueCenterY = 175;
    public static int redLeftX = 900;
    public static int redLeftY = 525;
    public static int redCenterX = 1325;
    public static int redCenterY = 100;
    public static int width = 125;
    public static int height = 125;
    public static double redThreshold = 2.5;
    public static double blueThreshold = 0.2;
    public static double threshold = 0;
    public double leftColor = 0.0;
    public double centerColor = 0.0;
    public Scalar left = new Scalar(0,0,0);
    public Scalar center = new Scalar(0,0,0);

    @Override
    public void init(int width, int height, CameraCalibration calibration) {
        lastFrame.set(Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565));
        updateThresholdBasedOnColor(AutoSides.getColor());
    }

    @Override
    public Object processFrame(Mat frame, long captureTimeNanos) {
        updateThresholdBasedOnColor(AutoSides.getColor());
        // HSV Color Filtering
        Mat hsvMat = new Mat();
        Imgproc.cvtColor(frame, hsvMat, Imgproc.COLOR_RGB2HSV);
        Scalar blueLow = new Scalar(80, 100, 100);
        Scalar blueHigh = new Scalar(125, 255, 255);
        // Red exists at both the start and the end of the hue range
        Scalar redLow1 = new Scalar(0, 100, 100);
        Scalar redHigh1 = new Scalar(25, 255, 255);
        Scalar redLow2 = new Scalar(130, 100, 100);
        Scalar redHigh2 = new Scalar(180, 255, 255);

        Mat blueMask = new Mat();
        Mat redMask1 = new Mat();
        Mat redMask2 = new Mat();
        Mat redMask = new Mat();

        Core.inRange(hsvMat, blueLow, blueHigh, blueMask);
        Core.inRange(hsvMat, redLow1, redHigh1, redMask1);
        Core.inRange(hsvMat, redLow2, redHigh2, redMask2);
        Core.bitwise_or(redMask1, redMask2, redMask);

        Mat colorMask = (AutoSides.getColor() == AutoSides.Color.BLUE) ? blueMask : redMask;

        frame.copyTo(finalMat);
        Imgproc.GaussianBlur(finalMat, finalMat, new Size(5, 5), 0.0);

        leftZoneArea = new Rect(AutoSides.getColor() == AutoSides.Color.RED ? redLeftX : blueLeftX, AutoSides.getColor() == AutoSides.Color.RED ? redLeftY : blueLeftY, width, height);
        centerZoneArea = new Rect(AutoSides.getColor() == AutoSides.Color.RED ? redCenterX : blueCenterX, AutoSides.getColor() == AutoSides.Color.RED ? redCenterY : blueCenterY, width, height);

        Mat leftZone = finalMat.submat(leftZoneArea);
        Mat centerZone = finalMat.submat(centerZoneArea);

        left = Core.sumElems(leftZone);
        center = Core.sumElems(centerZone);

        leftColor = left.val[0] / 1000000.0;
        centerColor = center.val[0] / 1000000.0;
        // Zone detection
        if(AutoSides.getColor() == AutoSides.Color.BLUE) {
            if (leftColor < threshold) {
                location = AutoSides.Position.LEFT;
            } else if (centerColor < threshold) {
                location = AutoSides.Position.CENTER;
            } else {
                location = AutoSides.Position.RIGHT;
            }
        } else {
            if (leftColor > threshold) {
                location = AutoSides.Position.LEFT;
            } else if (centerColor > threshold) {
                location = AutoSides.Position.CENTER;
            } else {
                location = AutoSides.Position.RIGHT;
            }
        }

        Imgproc.rectangle(finalMat, leftZoneArea, new Scalar(255, 255, 255));
        Imgproc.rectangle(finalMat, centerZoneArea, new Scalar(255, 255, 255));

        Bitmap b = Bitmap.createBitmap(finalMat.width(), finalMat.height(), Bitmap.Config.RGB_565);
        Utils.matToBitmap(finalMat, b);
        lastFrame.set(b);

        // Release Matt when done
        leftZone.release();
        centerZone.release();
        hsvMat.release();
        blueMask.release();
        redMask1.release();
        redMask2.release();
        redMask.release();

        return null;
    }

    private void updateThresholdBasedOnColor(AutoSides.Color color) {
        threshold = (color == AutoSides.Color.RED) ? redThreshold : blueThreshold;
    }

    public AutoSides.Position getLocation() {
        return this.location;
    }

    @Override
    public Mat processFrame(Mat input) {
        return null;
    }

    @Override
    public void onDrawFrame(Canvas canvas, int onscreenWidth, int onscreenHeight, float scaleBmpPxToCanvasPx, float scaleCanvasDensity, Object userContext) {
    }

    @Override
    public void getFrameBitmap(Continuation<? extends Consumer<Bitmap>> continuation) {
        continuation.dispatch(bitmapConsumer -> bitmapConsumer.accept(lastFrame.get()));
    }
}
