package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.Framework.BaseOpMode;
import org.firstinspires.ftc.teamcode.Framework.misc.PropPipeline;
import org.firstinspires.ftc.teamcode.Framework.misc.AutoSides;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;

@Autonomous(name="BlueAuto", group="LinearOpmode")
public class BlueAuto extends BaseOpMode {
    OpenCvCamera webcam;
    PropPipeline pipeline;

    private static final int CAMERA_WIDTH  = 1920;
    private static final int CAMERA_HEIGHT = 1080;

    @Override
    public void runOpMode() {
        AutoSides.setColor(AutoSides.Color.BLUE);
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        webcam = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, "Webcam 1"), cameraMonitorViewId);
        pipeline = new PropPipeline();
        webcam.setPipeline(pipeline);
        webcam.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() {
            @Override
            public void onOpened() {
                webcam.startStreaming(CAMERA_WIDTH, CAMERA_HEIGHT, OpenCvCameraRotation.UPRIGHT);
            }

            @Override
            public void onError(int errorCode) {
            }
        });

        waitForStart();

        while (opModeIsActive()) {
            AutoSides.Position detectedPosition = pipeline.getLocation();
            switch (detectedPosition) {
                case LEFT:
                    runAutonomousPathA();
                    break;
                case CENTER:
                    runAutonomousPathB();
                    break;
                case RIGHT:
                    runAutonomousPathC();
                    break;
                default:
                    break;
            }

            telemetry.addData("Detected Position", detectedPosition);
            telemetry.update();
        }
    }

    public void runAutonomousPathA() {
        telemetry.addLine("Running Autonomous Path A");
    }

    public void runAutonomousPathB() {
        telemetry.addLine("Running Autonomous Path B");
    }

    public void runAutonomousPathC() {
        telemetry.addLine("Running Autonomous Path C");
    }
}