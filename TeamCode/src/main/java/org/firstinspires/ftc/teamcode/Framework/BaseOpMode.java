package org.firstinspires.ftc.teamcode.Framework;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.Servo;

public abstract class BaseOpMode extends LinearOpMode {
    protected DcMotor[] driveMotors;
    protected Hanger hanger;
    protected Slides slides;
    protected IMU imuSensor;
    protected DroneLauncher droneLauncher;

    protected void initHardware() {
        driveMotors = new DcMotor[] {
                hardwareMap.dcMotor.get("motorFR"),
                hardwareMap.dcMotor.get("motorBR"),
                hardwareMap.dcMotor.get("motorFL"),
                hardwareMap.dcMotor.get("motorBL")
        };

        // Set motor directions to match physical configuration
        setMotorDirections(new DcMotorSimple.Direction[] {
                DcMotorSimple.Direction.REVERSE, // motorFR
                DcMotorSimple.Direction.REVERSE, // motorBR
                DcMotorSimple.Direction.FORWARD, // motorFL
                DcMotorSimple.Direction.FORWARD  // motorBL
        });

        hanger = new Hanger(hardwareMap.get(DcMotor.class, "hangerMotor"));
        slides = new Slides(
                hardwareMap.get(DcMotor.class, "slideMotorLeft"),
                hardwareMap.get(DcMotor.class, "slideMotorRight")
        );

        // Initialize the IMU sensor with appropriate parameters
        imuSensor = initializeIMUSensor("imu");

        // Initialize the drone launcher with the corresponding servo
        droneLauncher = new DroneLauncher(hardwareMap.get(Servo.class, "droneServo"));
    }

    private IMU initializeIMUSensor(String imuName) {
        IMU imu = hardwareMap.get(IMU.class, imuName);
        IMU.Parameters parameters = new IMU.Parameters(new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.FORWARD,
                RevHubOrientationOnRobot.UsbFacingDirection.UP
        ));
        imu.initialize(parameters);
        return imu;
    }

    private void setMotorDirections(DcMotorSimple.Direction[] directions) {
        for (int i = 0; i < driveMotors.length; i++) {
            driveMotors[i].setDirection(directions[i]);
        }
    }
}