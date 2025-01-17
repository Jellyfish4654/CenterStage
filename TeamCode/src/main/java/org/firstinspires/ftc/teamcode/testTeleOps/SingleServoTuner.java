package org.firstinspires.ftc.teamcode.testTeleOps;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;


@TeleOp(name = "Single Servo Test", group = "Test")
public class SingleServoTuner extends LinearOpMode
{
	@Override
	public void runOpMode() throws InterruptedException
	{
		final Servo outtakeLeftServo;
		outtakeLeftServo = hardwareMap.get(Servo.class, "droneServo");

//        outtakeLeftServo.setDirection(Servo.Direction.REVERSE);
		double position = 0.85; // Initialize to midpoint

		waitForStart();

		while (opModeIsActive())
		{
			telemetry.addData("ServoR", position);
			telemetry.update();

			outtakeLeftServo.setPosition(position);

			if (gamepad1.dpad_left)
			{
				position -= 0.0001;
			}
			if (gamepad1.dpad_right)
			{
				position += 0.0001;
			}

			if (gamepad1.a)
			{
				position = 0;
			}
			else if (gamepad1.b)
			{
				position = 0.85;
			}
		}
	}
}
