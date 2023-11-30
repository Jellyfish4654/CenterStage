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
    protected oneSlide slides;
    protected Intake intakeMotors;
//    protected Slides slides;
    protected IMU imuSensor;
    protected AntiTipping antiTipping;
    protected DroneLauncher droneLauncher;
    protected Outake outakeServos;
    protected Intake intakeSystem;
    protected AprilTagPipeline aprilTagPipeline;
    protected AutoAlignment autoAlignment;
    protected void initHardware() {
        imuSensor = initializeIMUSensor("imu");
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

        antiTipping = new AntiTipping(driveMotors, imuSensor);
        autoAlignment = new AutoAlignment(driveMotors, imuSensor);
        aprilTagPipeline = new AprilTagPipeline(hardwareMap);
        hanger = new Hanger(hardwareMap.get(DcMotorEx.class, "hangerMotor"));
        droneLauncher = new DroneLauncher(hardwareMap.get(CRServo.class, "droneServo"));
        outakeServos = new Outake(
                hardwareMap.get(Servo.class, "outakeLeftServo"),
                hardwareMap.get(Servo.class, "outakeRightServo")
        );
        slides = new oneSlide(
                hardwareMap.get(DcMotorEx.class, "slideMotorLeft")
//                hardwareMap.get(DcMotorEx.class, "slideMotorRight")
        );
        intakeSystem = new Intake(
                hardwareMap.get(DcMotorEx.class, "Tubing"),
                hardwareMap.get(Servo.class, "intakeServo")
        );

        hanger = new Hanger(hardwareMap.get(DcMotorEx.class, "hangerMotor"));
        slides = new oneSlide(hardwareMap.get(DcMotorEx.class, "slideMotorLeft"));
//                hardwareMap.get(DcMotorEx.class, "slideMotorRight")
        // Initialize the IMU sensor with appropriate parameters
        imuSensor = initializeIMUSensor("imu");

        // Initialize the drone launcher with the corresponding servo
        droneLauncher = new DroneLauncher(hardwareMap.get(CRServo.class, "droneServo"));

        Servo outakeServosLeftServo = hardwareMap.get(Servo.class, "outakeLeftServo");
        Servo outakeServosRightServo = hardwareMap.get(Servo.class, "outakeRightServo");
        outakeServos = new Outake(outakeServosLeftServo, outakeServosRightServo);


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