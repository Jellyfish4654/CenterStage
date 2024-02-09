package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name = "MaxVelocityTest", group = "Test")
public class MaxVelocityTest extends LinearOpMode
{

	private DcMotor testMotor;
	private ElapsedTime runtime = new ElapsedTime();

	@Override
	public void runOpMode()
	{
		testMotor = hardwareMap.get(DcMotor.class, "Tubing");
		testMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
		testMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

		waitForStart();
		runtime.reset();

		double maxVelocity = 0;
		double acceleration = 0;
		double previousVelocity = 0;
		double lastTime = 0;

		while (opModeIsActive())
		{
			testMotor.setPower(1.0); // Set motor to full power

			double currentTime = runtime.seconds();
			double currentVelocity = testMotor.getCurrentPosition() / currentTime;
			maxVelocity = Math.max(maxVelocity, currentVelocity);

			if (currentTime - lastTime >= 0.1)
			{ // Calculate acceleration every 0.1 seconds
				acceleration = (currentVelocity - previousVelocity) / (currentTime - lastTime);
				previousVelocity = currentVelocity;
				lastTime = currentTime;
			}

			telemetry.addData("Current Velocity", currentVelocity);
			telemetry.addData("Max Velocity", maxVelocity);
			telemetry.addData("Acceleration", acceleration);
			telemetry.update();
		}

		testMotor.setPower(0);
	}
}
