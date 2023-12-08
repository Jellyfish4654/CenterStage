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
    public static double MAX_ACCELERATION = 1.0;
    public static double MAX_VELOCITY = 1.0;
    public static int targetPosition = 0;
    private int previousTargetPosition = -1;
    private MotionProfile leftProfile;
    private MotionProfile rightProfile;
    private BasicPID leftPIDController;
    private BasicPID rightPIDController;
    private ElapsedTime timer = new ElapsedTime();

    @Override
    public void runOpMode() throws InterruptedException {
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
        rightMotor = hardwareMap.get(DcMotorEx.class, "slideMotorRight");
        leftMotor = hardwareMap.get(DcMotorEx.class, "slideMotorLeft");
        leftMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        PIDCoefficients coefficients = new PIDCoefficients(KP, KI, KD);
        leftPIDController = new BasicPID(coefficients);
        rightPIDController = new BasicPID(coefficients);

        waitForStart();

        while (opModeIsActive()) {
            if (targetPosition != previousTargetPosition) {
                leftProfile = new MotionProfile(leftMotor.getCurrentPosition(), targetPosition, new MotionProfile.Constraints(MAX_ACCELERATION, MAX_VELOCITY, MAX_ACCELERATION));
                rightProfile = new MotionProfile(rightMotor.getCurrentPosition(), targetPosition, new MotionProfile.Constraints(MAX_ACCELERATION, MAX_VELOCITY, MAX_ACCELERATION));
                previousTargetPosition = targetPosition;
                timer.reset();
            }

            double elapsedTime = timer.seconds();

            MotionProfile.State leftState = leftProfile.calculate(elapsedTime);
            MotionProfile.State rightState = rightProfile.calculate(elapsedTime);

            double leftPIDOutput = leftPIDController.calculate(leftState.x, leftMotor.getCurrentPosition());
            double rightPIDOutput = rightPIDController.calculate(rightState.x, rightMotor.getCurrentPosition());

            leftMotor.setPower(leftPIDOutput);
            rightMotor.setPower(rightPIDOutput);

            telemetry.addData("Current Left Position", leftMotor.getCurrentPosition());
            telemetry.addData("Current Right Position", rightMotor.getCurrentPosition());
            telemetry.addData("Target Position", targetPosition);
            telemetry.update();
        }
    }
}
