//package org.firstinspires.ftc.teamcode;
//
//import com.acmerobotics.dashboard.FtcDashboard;
//import com.acmerobotics.dashboard.config.Config;
//import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
//import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
//import com.acmerobotics.roadrunner.*;
//import com.qualcomm.robotcore.util.ElapsedTime;
//
//import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
//import org.firstinspires.ftc.teamcode.Framework.BaseOpMode;
//import org.firstinspires.ftc.teamcode.RoadRunner.MecanumDrive;
//import org.opencv.core.Scalar;
//import org.openftc.easyopencv.OpenCvCamera;
//import org.openftc.easyopencv.OpenCvCameraFactory;
//import org.openftc.easyopencv.OpenCvCameraRotation;
//import org.firstinspires.ftc.teamcode.Framework.misc.RedContourPipeline;
//
//import java.lang.Math;
//
//@Config
//@Autonomous(name="RedAuto")
//public class RedAuto extends BaseOpMode {
//    private OpenCvCamera camera;
//
//    private static final int CAMERA_WIDTH  = 1920;
//    private static final int CAMERA_HEIGHT = 1080;
//
//    private double chromaRedLowerBound = 210 - 30; // Lower bound for Cr channel to capture red shades
//    private double chromaBlueLowerBound = 95 - 30; // Lower bound for Cb channel to exclude non-red colors
//    private double chromaRedUpperBound = 210 + 30; // Upper bound for Cr channel to capture red shades
//    private double chromaBlueUpperBound = 95 + 30;
//
//    private double lowerThresholdLastUpdate = 0;
//    private double upperThresholdLastUpdate = 0;
//
//    public static double regionLeftX    = 0.0;
//    public static double regionRightX   = 0.0;
//    public static double regionTopY     = 0.5;
//    public static double regionBottomY  = 0.0;
//
//    public static Scalar defaultLowerBoundYCrCb = new Scalar(0, 210 - 20, 95 - 20);// Increase the lower Cr bound for red
//    public static Scalar defaultUpperBoundYCrCb = new Scalar(255, 210 + 20, 95 + 20); // Decrease the upper Cb bound to exclude blues
//
//    public enum OutakeState {
//        OUTAKE_OPEN,
//        OUTAKE_CLOSE,
//        SLIDES_DOWN,
//        SLIDES_UP
//    };
//    ElapsedTime outakeTimer = new ElapsedTime();
//    OutakeState outakeState = OutakeState.OUTAKE_CLOSE;
//    private MecanumDrive drive;
//    private Action TrajectoryAction1;
//
//    @Override
//    public void runOpMode() {
//        MecanumDrive drive = new MecanumDrive(hardwareMap, new Pose2d(0,0,0));
//
//        Action TrajectoryAction1 = drive.actionBuilder(drive.pose)
//                .lineToX(0)
//                .build();
//
//        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
//        camera = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, "Webcam 1"), cameraMonitorViewId);
//        RedContourPipeline myPipeline = new RedContourPipeline(regionLeftX, regionRightX, regionTopY, regionBottomY);
//        camera.setPipeline(myPipeline);
//
//        myPipeline.configureScalarLower(defaultLowerBoundYCrCb.val[0], defaultLowerBoundYCrCb.val[1], defaultLowerBoundYCrCb.val[2]);
//        myPipeline.configureScalarUpper(defaultUpperBoundYCrCb.val[0], defaultUpperBoundYCrCb.val[1], defaultUpperBoundYCrCb.val[2]);
//
//        camera.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() {
//            @Override
//            public void onOpened() {
//                camera.startStreaming(CAMERA_WIDTH, CAMERA_HEIGHT, OpenCvCameraRotation.UPRIGHT);
//            }
//
//            @Override
//            public void onError(int errorCode) {
//                // Handle camera opening error
//            }
//        });
//
//        FtcDashboard dashboard = FtcDashboard.getInstance();
//        telemetry = new MultipleTelemetry(telemetry, dashboard.getTelemetry());
//        FtcDashboard.getInstance().startCameraStream(camera, 10);
//        telemetry.update();
//        waitForStart();
//
//        while (opModeIsActive()) {
//            myPipeline.configureBorders(regionLeftX, regionRightX, regionTopY, regionBottomY);
//            if (myPipeline.hasError) {
//                telemetry.addData("Exception: ", myPipeline.debugException);
//            }
//
//            updateColorThresholds(myPipeline);
//            telemetry.addData("RectArea: ", myPipeline.getRectArea());
//            telemetry.update();
//
//            double rectMidpointX = myPipeline.getRectMidpointX();
//            if (myPipeline.getRectArea() > 2000) {
//                if (rectMidpointX > 400) {
//                    runAutonomousPathC();
//                } else if (rectMidpointX > 200) {
//                    runAutonomousPathB();
//                } else {
//                    runAutonomousPathA();
//                }
//            }
//        }
//    }
//
//    public void updateColorThresholds(RedContourPipeline myPipeline) {
//        if (lowerThresholdLastUpdate + 0.05 < getRuntime()) {
//            chromaRedLowerBound += -gamepad1.left_stick_y;
//            chromaBlueLowerBound += gamepad1.left_stick_x;
//            lowerThresholdLastUpdate = getRuntime();
//        }
//        if (upperThresholdLastUpdate + 0.05 < getRuntime()) {
//            chromaRedUpperBound += -gamepad1.right_stick_y;
//            chromaBlueUpperBound += gamepad1.right_stick_x;
//            upperThresholdLastUpdate = getRuntime();
//        }
//
//        chromaRedLowerBound = clampValue(chromaRedLowerBound, 0, 255);
//        chromaRedUpperBound = clampValue(chromaRedUpperBound, 0, 255);
//        chromaBlueLowerBound = clampValue(chromaBlueLowerBound, 0, 255);
//        chromaBlueUpperBound = clampValue(chromaBlueUpperBound, 0, 255);
//
//        myPipeline.configureScalarLower(0.0, chromaRedLowerBound, chromaBlueLowerBound);
//        myPipeline.configureScalarUpper(255.0, chromaRedUpperBound, chromaBlueUpperBound);
//
//        telemetry.addData("lowerCr ", (int) chromaRedLowerBound);
//        telemetry.addData("lowerCb ", (int) chromaBlueLowerBound);
//        telemetry.addData("UpperCr ", (int) chromaRedUpperBound);
//        telemetry.addData("UpperCb ", (int) chromaBlueUpperBound);
//    }
//
//    public Double clampValue(double value, double min, double max) {
//        return Math.max(min, Math.min(value, max));
//    }
//
////    Action controlSlidesAction = telemetryPacket -> {
////        switch(outakeState){
////            case OUTAKE_CLOSE:
////                    outakeServos.openOutake();
////                    outakeTimer.reset();
////                break;
////            case OUTAKE_OPEN:
////                while(outakeTimer.seconds() <= 0.25){
////                }
////                outakeServos.closeOutake();
////                break;
////            case SLIDES_DOWN:
////                slides.setTargetPosition(0);
////                break;
////            case SLIDES_UP:
////                slides.setTargetPosition(666);
////                break;
////            default:
////                outakeState = OutakeState.SLIDES_DOWN;
////                break;
////        }
////        return false;
//    };
//
//    public void runAutonomousPathA() {
//        telemetry.addLine("Autonomous A");
//    }
//
//    public void runAutonomousPathB() {
//        telemetry.addLine("Autonomous B");
//    }
//
//    public void runAutonomousPathC() {
//        telemetry.addLine("Autonomous C");
//    }
//}
