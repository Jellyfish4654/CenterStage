package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.Framework.BaseOpMode;
import org.firstinspires.ftc.teamcode.Framework.misc.AntiTipping;
import org.firstinspires.ftc.teamcode.Framework.misc.AutoAlignment;
import org.firstinspires.ftc.teamcode.Framework.misc.ButtonEX;
import org.firstinspires.ftc.teamcode.Framework.misc.SlewRateLimiter;
import org.firstinspires.ftc.teamcode.Framework.tubingIntake;

@TeleOp(name = "Servo Test")
public class Intake extends BaseOpMode{
    protected DcMotorEx intakeMotor;
    public void runOpMode() throws InterruptedException {
        intakeMotor = hardwareMap.get(DcMotorEx.class, "intake");
        waitForStart();
        while(opModeIsActive()){
            intakeMotor.setPower(1);
        }
    }
}
