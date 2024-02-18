package org.firstinspires.ftc.teamcode.testTeleOps;

import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.teamcode.Framework.BaseOpMode;

@TeleOp(name = "Intake Test", group = "Test")
public class IntakeTuner extends BaseOpMode
{
	protected DcMotorEx intakeMotor;
	private GamepadEx gamepadEx1;
	double power = 1.0;

	public void runOpMode() throws InterruptedException
	{
		intakeMotor = hardwareMap.get(DcMotorEx.class, "Tubing");
		gamepadEx1 = new GamepadEx(gamepad1);
		waitForStart();
		while (opModeIsActive())
		{
			telemetry.addData("Power", power);
			telemetry.update();
			if (gamepadEx1.wasJustReleased(GamepadKeys.Button.DPAD_DOWN))
			{
				power -= 0.1;
			}
			if (gamepadEx1.wasJustReleased(GamepadKeys.Button.DPAD_UP))
			{
				power += 0.1;
			}
			intakeMotor.setPower(power);
		}
	}
}
