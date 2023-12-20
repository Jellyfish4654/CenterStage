package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name="MaxAccelerationTest", group="Test")
public class MaxAccelerationTest extends LinearOpMode {

    private DcMotor testMotor;
    private ElapsedTime runtime = new ElapsedTime();

    @Override
    public void runOpMode() {
        testMotor = hardwareMap.get(DcMotor.class, "slideMotorLeft");
        testMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        testMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        waitForStart();
        runtime.reset();

        double previousVelocity = 0;
        double previousTime = 0;
        double maxAcceleration = 0;

        while (opModeIsActive()) {
            testMotor.setPower(1.0); // Set motor to full power

            double currentTime = runtime.seconds();
            double currentPosition = testMotor.getCurrentPosition();
            double currentVelocity = currentPosition / currentTime;

            if (currentTime > 0) { // Avoid division by zero
                double currentAcceleration = (currentVelocity - previousVelocity) / (currentTime - previousTime);
                maxAcceleration = Math.max(maxAcceleration, currentAcceleration);

                previousVelocity = currentVelocity;
                previousTime = currentTime;

                telemetry.addData("Current Acceleration", currentAcceleration);
                telemetry.addData("Max Acceleration", maxAcceleration);
                telemetry.update();
            }
        }

        testMotor.setPower(0);
    }
}
