package org.firstinspires.ftc.teamcode.testTeleOps;

import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

@TeleOp(name = "ericteleop", group = "OpMode")
public class ericteleop extends LinearOpMode {
    GamepadEx thegamepad = new Gamepad(gamepad1);
    DcMotor frontLeftMotor;
    DcMotor backLeftMotor;
    DcMotor frontRightMotor;
    DcMotor backRightMotor;
    IMU imu;
    @Override
    public void runOpMode() throws InterruptedException {
        frontLeftMotor = hardwareMap.get(DcMotor.class, "motorFL");
        backLeftMotor = hardwareMap.get(DcMotor.class, "motorBL");
        frontRightMotor = hardwareMap.get(DcMotor.class, "motorFR");
        backRightMotor = hardwareMap.get(DcMotor.class, "motorBR");
        imu = hardwareMap.get(IMU.class, "imu");

        frontRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        backRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        IMU.Parameters parameters = new IMU.Parameters(new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.UP,
                RevHubOrientationOnRobot.UsbFacingDirection.FORWARD));
        imu.initialize(parameters);
        waitForStart();
        while (opModeIsActive()) {
            double Yaw = imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS);
            double y = -gamepad1.left_stick_y;
            double x = gamepad1.left_stick_x;
            double r = gamepad1.right_stick_x;
            double x2 = x*Math.cos(-Yaw)-y*Math.sin(-Yaw);
            double y2 = x*Math.sin(-Yaw)+y*Math.cos(-Yaw);
            double denominator = Math.max((Math.abs(y2)+Math.abs(x2)+Math.abs(r)),1);
            frontLeftMotor.setPower((y2+x2+r)/denominator);
            backLeftMotor.setPower((y2-x2+r)/denominator);
            frontRightMotor.setPower((y2-x2-r)/denominator);
            backRightMotor.setPower((y2+x2-r)/denominator);
            if (thegamepad.wasJustPressed(GamepadKeys.Button.BACK)) {
                imu.resetYaw();
            }
        }
    }
}
