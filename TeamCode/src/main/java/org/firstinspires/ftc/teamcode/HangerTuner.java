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
@TeleOp(name = "Hanger Test")
public class HangerTuner extends LinearOpMode {
    private DcMotorEx hangerMotor;
    public static double KP = 0.005;
    public static double MAX_ACCELERATION = 1.0;
    public static double MAX_VELOCITY = 1.0;
    public static int targetPosition = 0;
    public int previousTargetPosition = 0;
    PIDCoefficients coefficients = new PIDCoefficients(KP, 0, 0);
    BasicPID controller = new BasicPID(coefficients);
    public ElapsedTime timer = new ElapsedTime();

    @Override
    public void runOpMode() throws InterruptedException {
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
//        hangerMotor = hardwareMap.get(DcMotorEx.class, "hangerMotor");
        hangerMotor = hardwareMap.get(DcMotorEx.class, "Tubing");
        hangerMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        waitForStart();
        while (opModeIsActive()) {
            if (targetPosition != previousTargetPosition) {
                timer.reset();
                previousTargetPosition = targetPosition;
            }

            double elapsedTime = timer.seconds();
            int currentPosition = hangerMotor.getCurrentPosition();
            double KP_POWER = controller.calculate(targetPosition, currentPosition);
            double distance = targetPosition - hangerMotor.getCurrentPosition();
//            double instantTargetPosition = MotionProfile.motion_profile(MAX_ACCELERATION,
//                    MAX_VELOCITY,
//                    distance,
//                    elapsedTime);
//            double motorPower = (instantTargetPosition - hangerMotor.getCurrentPosition()) * KP_POWER;

            hangerMotor.setPower(KP_POWER);

            telemetry.addData("Current Position", hangerMotor.getCurrentPosition());
            telemetry.addData("Target Position", targetPosition);
            telemetry.update();
        }
    }
}