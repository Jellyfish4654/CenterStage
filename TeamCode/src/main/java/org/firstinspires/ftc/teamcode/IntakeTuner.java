package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.teamcode.Framework.BaseOpMode;

@TeleOp(name = "Intake Test")
public class IntakeTuner extends BaseOpMode
{
    protected DcMotorEx intakeMotor;

    public void runOpMode() throws InterruptedException
    {
        intakeMotor = hardwareMap.get(DcMotorEx.class, "Tubing");
        waitForStart();
        while (opModeIsActive())
        {
            intakeMotor.setPower(1);
        }
    }
}
