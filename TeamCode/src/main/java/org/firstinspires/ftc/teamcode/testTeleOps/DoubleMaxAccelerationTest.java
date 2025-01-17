package org.firstinspires.ftc.teamcode.testTeleOps;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name = "DoubleMaxAccelerationTest", group = "Test")
public class DoubleMaxAccelerationTest extends LinearOpMode {
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

        double previousVelocityLeft = 0, previousVelocityRight = 0;
        double previousTime = 0;
        double maxAccelerationLeft = 0, maxAccelerationRight = 0;

        while (opModeIsActive()) {
            leftMotor.setPower(1.0);
            rightMotor.setPower(1.0);

            double currentTime = runtime.seconds();
            double currentVelocityLeft = leftMotor.getVelocity();
            double currentVelocityRight = rightMotor.getVelocity();

            if (currentTime > previousTime) {
                double currentAccelerationLeft = (currentVelocityLeft - previousVelocityLeft) / (currentTime - previousTime);
                double currentAccelerationRight = (currentVelocityRight - previousVelocityRight) / (currentTime - previousTime);

                maxAccelerationLeft = Math.max(maxAccelerationLeft, currentAccelerationLeft);
                maxAccelerationRight = Math.max(maxAccelerationRight, currentAccelerationRight);

                previousVelocityLeft = currentVelocityLeft;
                previousVelocityRight = currentVelocityRight;
                previousTime = currentTime;

                telemetry.addData("Left Motor Current Acceleration", currentAccelerationLeft);
                telemetry.addData("Left Motor Max Acceleration", maxAccelerationLeft);
                telemetry.addData("Right Motor Current Acceleration", currentAccelerationRight);
                telemetry.addData("Right Motor Max Acceleration", maxAccelerationRight);
                telemetry.update();
            }
        }
        leftMotor.setPower(0);
        rightMotor.setPower(0);
    }
}