package org.firstinspires.ftc.teamcode.Framework.misc;

import android.util.Size;

import com.qualcomm.robotcore.hardware.HardwareMap;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;
import org.openftc.easyopencv.OpenCvCamera;

public class AprilTagPipeline {
    protected OpenCvCamera webcam1;
    private AprilTagProcessor tagProcessor;
    private VisionPortal visionPortal;
    double x;
    double y;

    public AprilTagPipeline(HardwareMap hardwareMap) {
        tagProcessor = new AprilTagProcessor.Builder()
                .setDrawAxes(true)
                .setDrawCubeProjection(true)
                .setDrawTagID(true)
                .setDrawTagOutline(true)
                .build();

        VisionPortal.Builder builder = new VisionPortal.Builder();
        builder.setCamera(hardwareMap.get(WebcamName.class, "Webcam 1"));
        builder.setCameraResolution(new Size(1920 , 1080));
        builder.enableLiveView(true);
        builder.addProcessor(tagProcessor);
        visionPortal = builder.build();
    }

    public double getTagX(){
        return x;
    }

    public double getTagY(){
        return y;
    }

    public void update() {
        for (AprilTagDetection tag : tagProcessor.getDetections()) {
            if (tag.id == 2 || tag.id == 5) {
                x = tag.ftcPose.x;
                y = tag.ftcPose.y;

                telemetry.addData("x", tag.ftcPose.x);
                telemetry.addData("y", tag.ftcPose.y);
                telemetry.addData("z", tag.ftcPose.z);
                telemetry.addData("roll", tag.ftcPose.roll);
                telemetry.addData("pitch", tag.ftcPose.pitch);
                telemetry.addData("yaw", tag.ftcPose.yaw);
                telemetry.update();
            }
        }
    }
}
