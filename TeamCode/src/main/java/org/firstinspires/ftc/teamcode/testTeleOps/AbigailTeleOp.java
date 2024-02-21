package org.firstinspires.ftc.teamcode.testTeleOps;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;


@TeleOp(name = "AbigailTeleOp", group = "OpMode")

    public class AbigailTeleOp extends LinearOpMode {
        DcMotor frontLeftMotor;
        DcMotor frontRightMotor;
        DcMotor backLeftMotor;
        DcMotor backRightMotor;

IMU imu;
        @Override
        public void runOpMode() throws InterruptedException {
            frontLeftMotor = hardwareMap.get(DcMotor.class, "motorFL");
            frontRightMotor = hardwareMap.get(DcMotor.class, "motorFR");
            frontRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);
            backLeftMotor = hardwareMap.get(DcMotor.class, "motorBL");
            backRightMotor = hardwareMap.get(DcMotor.class, "motorBR");
            backRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);
            imu = hardwareMap.get(IMU.class, "imu");

            IMU.Parameters parameters = new IMU.Parameters (new RevHubOrientationOnRobot(
                    RevHubOrientationOnRobot.LogoFacingDirection.UP,
                    RevHubOrientationOnRobot.UsbFacingDirection.FORWARD));
                    imu.initialize(parameters);
            waitForStart();
            while (opModeIsActive()) {
                double y = -gamepad1.left_stick_y;
                double x = gamepad1.left_stick_x;
                double r = gamepad1.right_stick_x;

                double yaw = imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS);
                double pitch = imu.getRobotYawPitchRollAngles().getPitch(AngleUnit.RADIANS);
                double roll = imu.getRobotYawPitchRollAngles().getRoll(AngleUnit.RADIANS);

                double x2 = (Math.cos(-yaw) * x - Math.sin(-yaw) * y);
                double y2 = (Math.sin(-yaw) * x + Math.cos(-yaw) * y);
                double denominator = Math.max(Math.abs(y2)+Math.abs(x2)+Math.abs(r),1);

                frontLeftMotor.setPower((y + x + r)/denominator);
                frontRightMotor.setPower((y - x - r)/denominator);
                backLeftMotor.setPower((y - x + r)/denominator);
                backRightMotor.setPower((y + x - r)/denominator);

                /*if (gamepad1.a) {
                    servo.setPosition(servo.getPosition()+0.1);
                }
                else if (gamepad1.b) {
                    servo.setPosition(servo.getPosition()-0.1);
                }
                */

            }
        }
    }
