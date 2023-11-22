package org.firstinspires.ftc.teamcode;

import com.ThermalEquilibrium.homeostasis.Controllers.Feedback.BasicPID;
import com.ThermalEquilibrium.homeostasis.Parameters.PIDCoefficients;
import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Framework.BaseOpMode;
import org.firstinspires.ftc.teamcode.Framework.misc.MotionProfile;

@Config
@TeleOp(name = "Slides Tuner")
public class SlidesTuner extends BaseOpMode {

    private static int SLIDE_LOWER_BOUND = 0;
    private static int SLIDE_UPPER_BOUND = 3000;
    private static double KP = 0;
    private static double KI = 0;
    private static double KD = 0;
    private static double FEED_FORWARD_CONSTANT = 0;
    private static double MAX_ACCELERATION = 1.0; // Adjust as needed
    private static double MAX_VELOCITY = 1.0; // Adjust as needed
    private static int target = 0;


    private final DcMotor leftMotor;
    private final DcMotor rightMotor;
    private int targetPosition;
    private final PIDCoefficients coefficients = new PIDCoefficients(KP, KI, KD);
    private final BasicPID leftController = new BasicPID(coefficients);
    private final BasicPID rightController = new BasicPID(coefficients);
    private FtcDashboard dashboard = FtcDashboard.getInstance();
    private ElapsedTime timer = new ElapsedTime();

    public SlidesTuner(DcMotor leftMotor, DcMotor rightMotor) {
        this.leftMotor = leftMotor;
        this.rightMotor = rightMotor;
    }

    public void setTargetPosition(int target) {
        targetPosition = Math.max(SLIDE_LOWER_BOUND, Math.min(target, SLIDE_UPPER_BOUND));
        timer.reset();
    }

    public int getTargetPosition() {
        return this.targetPosition;
    }

    public void update() {
        double elapsedTime = timer.seconds();
        int leftPosition = leftMotor.getCurrentPosition();
        int rightPosition = rightMotor.getCurrentPosition();

        double leftDistance = targetPosition - leftPosition;
        double rightDistance = targetPosition - rightPosition;

        // Without MP
         double leftPidOutput = leftController.calculate(targetPosition, leftPosition);
         double rightPidOutput = rightController.calculate(targetPosition, rightPosition);
         double feedForward = calculateFeedForward(targetPosition);

         moveSlides(leftPidOutput + feedForward, rightPidOutput + feedForward);

        // With MP
//        double instantLeftTargetPosition = MotionProfile.motion_profile(MAX_ACCELERATION, MAX_VELOCITY, leftDistance, elapsedTime);
//        double instantRightTargetPosition = MotionProfile.motion_profile(MAX_ACCELERATION, MAX_VELOCITY, rightDistance, elapsedTime);
//
//        double leftPidOutput = leftController.calculate((int) instantLeftTargetPosition, leftPosition);
//        double rightPidOutput = rightController.calculate((int) instantRightTargetPosition, rightPosition);
//        double leftFeedForward = calculateFeedForward((int) instantLeftTargetPosition);
//        double rightFeedForward = calculateFeedForward((int) instantRightTargetPosition);
//
//        moveSlides(leftPidOutput + leftFeedForward, rightPidOutput + rightFeedForward);

        TelemetryPacket packet = new TelemetryPacket();
        packet.put("Target Position", targetPosition);
        packet.put("Left Motor Position", leftPosition);
        packet.put("Right Motor Position", rightPosition);
        dashboard.sendTelemetryPacket(packet);
    }

    private void moveSlides(double leftPower, double rightPower) {
        leftMotor.setPower(leftPower);
        rightMotor.setPower(rightPower);
    }

    private double calculateFeedForward(int targetPosition) {
        return FEED_FORWARD_CONSTANT;
    }

    public boolean isAtTargetPosition() {
        int leftPosition = leftMotor.getCurrentPosition();
        int rightPosition = rightMotor.getCurrentPosition();
        return leftPosition == targetPosition && rightPosition == targetPosition;
    }

    @Override
    public void runOpMode() throws InterruptedException {
        initHardware();
        waitForStart();
        ElapsedTime timer = new ElapsedTime();

        while (opModeIsActive()) {
            update();
            setTargetPosition(target);
        }
    }
}
