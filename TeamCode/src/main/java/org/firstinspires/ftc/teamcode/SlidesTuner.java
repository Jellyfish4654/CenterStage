package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.controller.PIDController;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;

@Config
@TeleOp
public class SlidesTuner extends LinearOpMode
{
	private PIDController controller;

	public static double p = 0.06, i = 0.0061, d = 0.0002;
	public static int target = 0;
	private DcMotorEx slideMotorLeft;

	@Override
	public void runOpMode()
	{
		controller = new PIDController(p, i, d);
		telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
		slideMotorLeft = hardwareMap.get(DcMotorEx.class, "slideMotorLeft");
		slideMotorLeft.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
		slideMotorLeft.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
		slideMotorLeft.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
		waitForStart();

		while (opModeIsActive())
		{
			controller.setPID(p, i, d);
			int position = slideMotorLeft.getCurrentPosition();
			if (position != target)
			{
				double power = controller.calculate(position, target);
				slideMotorLeft.setPower(power);
			}
			else
			{
				slideMotorLeft.setPower(0);
			}
			telemetry.addData("pos ", position);
			telemetry.addData("target ", target);
			telemetry.update();
		}
	}
}
