package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.Framework.misc.Sides;
import org.firstinspires.ftc.teamcode.Framework.misc.PropPipeline;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;

@Autonomous(name="BlueAuto", group="Auto")
public class BlueAuto extends LinearOpMode {
    OpenCvCamera webcam;
    PropPipeline detectionPipeline;

    @Override
    public void runOpMode() {
        Sides.setColor(Sides.Color.RED);
        // Initialize hardware and pipeline
        initHardware(hardwareMap);
        initCamera();

        // Wait for the start button to be pressed, updating telemetry
        while (!isStarted()) {
            telemetry.addData("Position", Sides.getPosition().toString());
            telemetry.update();
        }

        // After starting, stop the camera stream
        webcam.stopStreaming();

        if (opModeIsActive()) {
            // Get the detected position
            Sides.Position detectedPosition = Sides.getPosition();

            // Run the autonomous path based on the detected position
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
                    // Handle the default case if needed
                    break;
            }
        }
    }

    private void initHardware(HardwareMap hwMap) {
        // Initialize your robot's hardware here
    }

    private void initCamera() {
        // Initialize camera
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        webcam = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, "Webcam 1"), cameraMonitorViewId);

        detectionPipeline = new PropPipeline(telemetry);
        webcam.setPipeline(detectionPipeline);

        webcam.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() {
            @Override
            public void onOpened() {
                webcam.startStreaming(1920, 1080, OpenCvCameraRotation.UPRIGHT);
            }

            @Override
            public void onError(int errorCode) {
                // Error handling
            }
        });
    }

    private void runAutonomousPathA() {
        // Define the autonomous path A
    }

    private void runAutonomousPathB() {
        // Define the autonomous path B
    }

    private void runAutonomousPathC() {
        // Define the autonomous path C
    }
}