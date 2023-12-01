package org.firstinspires.ftc.teamcode;

import com.ThermalEquilibrium.homeostasis.Controllers.Feedback.BasicPID;
import com.ThermalEquilibrium.homeostasis.Parameters.PIDCoefficients;
import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Framework.misc.MotionProfile;

@Config
@TeleOp(name = "Slides Tuner")
public class SlidesTuner extends LinearOpMode {
    public DcMotorEx leftMotor;
    public DcMotorEx rightMotor;
    public static double KP = 0.005;
    public static double KI = 0;
    public static double KD = 0;
    public static double FEED_FORWARD_CONSTANT = 0;
    public static double MAX_ACCELERATION = 1.0; // Adjust as needed
    public static double MAX_VELOCITY = 1.0; // Adjust as needed
    public static int targetPosition = 0;
    public int previousTargetPosition = 0;
    private final double TICKS_PER_DEGREE = 145.1 / 360.0;
    PIDCoefficients coefficients = new PIDCoefficients(KP, KI, KD);
    BasicPID controller = new BasicPID(coefficients);
    public ElapsedTime timer = new ElapsedTime();

    @Override
    public void runOpMode() throws InterruptedException {
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
        rightMotor = hardwareMap.get(DcMotorEx.class, "slideMotorRight");
        leftMotor = hardwareMap.get(DcMotorEx.class, "slideMotorLeft");
        leftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        waitForStart();
        while (opModeIsActive()) {
            if (targetPosition != previousTargetPosition) {
                timer.reset();
                previousTargetPosition = targetPosition;
            }

            double elapsedTime = timer.seconds();
            int leftCurrentPosition = leftMotor.getCurrentPosition();
            int rightCurrentPosition = rightMotor.getCurrentPosition();
            double FF = Math.cos(Math.toRadians(targetPosition / TICKS_PER_DEGREE)) * FEED_FORWARD_CONSTANT;
            double LEFT_PIDF_POWER = controller.calculate(targetPosition, leftCurrentPosition) + FF;
            double RIGHT_PIDF_POWER = controller.calculate(targetPosition, rightCurrentPosition) + FF;
            double leftDistance = targetPosition - leftMotor.getCurrentPosition();
            double rightDistance = targetPosition - rightMotor.getCurrentPosition();
            double leftInstantTargetPosition = MotionProfile.motion_profile(MAX_ACCELERATION,
                    MAX_VELOCITY,
                    leftDistance,
                    elapsedTime);
            double rightInstantTargetPosition = MotionProfile.motion_profile(MAX_ACCELERATION,
                    MAX_VELOCITY,
                    rightDistance,
                    elapsedTime);
            double leftMotorPower = (leftInstantTargetPosition - leftMotor.getCurrentPosition()) * LEFT_PIDF_POWER;
            double rightMotorPower = (rightInstantTargetPosition - rightMotor.getCurrentPosition()) * RIGHT_PIDF_POWER;

            leftMotor.setPower(leftMotorPower);
            rightMotor.setPower(rightMotorPower);

            telemetry.addData("Current Left Position", leftMotor.getCurrentPosition());
            telemetry.addData("Current Right Position", rightMotor.getCurrentPosition());
            telemetry.addData("Target Position", targetPosition);
            telemetry.update();
        }
    }
}
