package org.firstinspires.ftc.teamcode.testTeleOps;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name = "DoubleMaxVelocityTest", group = "Test")
public class DoubleMaxVelocityTest extends LinearOpMode {
    private DcMotorEx leftMotor, rightMotor;
    private ElapsedTime runtime = new ElapsedTime();

    @Override
    public void runOpMode() {
        leftMotor = hardwareMap.get(DcMotorEx.class, "slideMotorLeft");
        rightMotor = hardwareMap.get(DcMotorEx.class, "slideMotorRight");

        rightMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        leftMotor.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        leftMotor.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        rightMotor.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        rightMotor.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);

        waitForStart();
        runtime.reset();

        double maxVelocityLeft = 0, maxVelocityRight = 0;
        double accelerationLeft = 0, accelerationRight = 0;
        double previousVelocityLeft = 0, previousVelocityRight = 0;
        double lastTime = 0;

        while (opModeIsActive()) {
            leftMotor.setPower(1.0);
            rightMotor.setPower(1.0);

            double currentTime = runtime.seconds();
            double currentVelocityLeft = leftMotor.getVelocity();
            double currentVelocityRight = rightMotor.getVelocity();

            maxVelocityLeft = Math.max(maxVelocityLeft, currentVelocityLeft);
            maxVelocityRight = Math.max(maxVelocityRight, currentVelocityRight);

            if (currentTime - lastTime >= 0.1) {
                accelerationLeft = (currentVelocityLeft - previousVelocityLeft) / (currentTime - lastTime);
                accelerationRight = (currentVelocityRight - previousVelocityRight) / (currentTime - lastTime);
                previousVelocityLeft = currentVelocityLeft;
                previousVelocityRight = currentVelocityRight;
                lastTime = currentTime;
            }

            telemetry.addData("Left Motor Current Velocity", currentVelocityLeft);
            telemetry.addData("Left Motor Max Velocity", maxVelocityLeft);
            telemetry.addData("Left Motor Acceleration", accelerationLeft);
            telemetry.addData("Right Motor Current Velocity", currentVelocityRight);
            telemetry.addData("Right Motor Max Velocity", maxVelocityRight);
            telemetry.addData("Right Motor Acceleration", accelerationRight);
            telemetry.update();
        }

        leftMotor.setPower(0);
        rightMotor.setPower(0);
    }
}