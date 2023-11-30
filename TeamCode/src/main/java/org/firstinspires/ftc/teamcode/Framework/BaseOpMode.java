package org.firstinspires.ftc.teamcode.Framework;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.Servo;

public abstract class BaseOpMode extends LinearOpMode {
    protected DcMotor[] driveMotors;
    protected Hanger hanger;
    protected Slides slides;
    protected IMU imuSensor;
    protected DroneLauncher droneLauncher;
    protected Outake outakeServos;
    protected Intake intakeSystem;
    protected AprilTagPipeline aprilTagPipeline;
    protected AutoAlignment autoAlignment;
    protected AntiTipping antiTipping;
    protected void initHardware() {
        antiTipping = new AntiTipping(driveMotors, imuSensor);
        autoAlignment = new AutoAlignment(driveMotors, imuSensor);
        aprilTagPipeline = new AprilTagPipeline(hardwareMap);
        imuSensor = initializeIMUSensor("imu");
        hanger = new Hanger(hardwareMap.get(DcMotorEx.class, "hangerMotor"));
        droneLauncher = new DroneLauncher(hardwareMap.get(CRServo.class, "droneServo"));
        outakeServos = new Outake(
                hardwareMap.get(Servo.class, "outakeLeftServo"),
                hardwareMap.get(Servo.class, "outakeRightServo")
        );
        slides = new Slides(
                hardwareMap.get(DcMotorEx.class, "slideMotorLeft"),
                hardwareMap.get(DcMotorEx.class, "slideMotorRight")
        );
        intakeSystem = new Intake(
                hardwareMap.get(DcMotorEx.class, "Tubing"),
                hardwareMap.get(Servo.class, "intakeServo")
        );
        driveMotors = new DcMotor[] {
                hardwareMap.dcMotor.get("motorFR"),
                hardwareMap.dcMotor.get("motorBR"),
                hardwareMap.dcMotor.get("motorFL"),
                hardwareMap.dcMotor.get("motorBL")
        };
        setMotorDirections(new DcMotorSimple.Direction[] {
                DcMotorSimple.Direction.REVERSE, // motorFR
                DcMotorSimple.Direction.REVERSE, // motorBR
                DcMotorSimple.Direction.FORWARD, // motorFL
                DcMotorSimple.Direction.FORWARD  // motorBL
        });
    }

    private IMU initializeIMUSensor(String imuName) {
        IMU imu = hardwareMap.get(IMU.class, imuName);
        IMU.Parameters parameters = new IMU.Parameters(new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.BACKWARD,
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