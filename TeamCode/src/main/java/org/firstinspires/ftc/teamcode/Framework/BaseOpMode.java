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
    protected DroneLauncher droneServo;
    protected Intake intakeSystem;
    protected Outake outakeServos;
    protected DcMotorEx slideMotorLeft;
    protected DcMotorEx slideMotorRight;
    protected Slides slides;
    
    protected IMU imuSensor;
    protected AntiTipping antiTipping;
    protected AutoAlignment autoAlignment;
    
    protected void initHardware() {
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
        
        droneServo = new DroneLauncher(hardwareMap.get(Servo.class, "droneServo"));
        
        intakeSystem = new Intake(
                hardwareMap.get(DcMotorEx.class, "Tubing"),
                hardwareMap.get(Servo.class, "intakeServo")
        );
        
        Servo outakeServosLeftServo = hardwareMap.get(Servo.class, "outtakeLeftServo");
        outakeServosLeftServo.setDirection(Servo.Direction.REVERSE);
        Servo outakeServosRightServo = hardwareMap.get(Servo.class, "outtakeRightServo");
        CRServo geckoServo = hardwareMap.get(CRServo.class, "geckoServo");
        outakeServos = new Outake(outakeServosLeftServo, outakeServosRightServo, geckoServo);

        slideMotorLeft = hardwareMap.get(DcMotorEx.class, "slideMotorLeft");
        slideMotorRight = hardwareMap.get(DcMotorEx.class, "slideMotorRight");
        slideMotorRight.setDirection(DcMotorSimple.Direction.REVERSE);
        slides = new Slides(slideMotorLeft, slideMotorRight);
        slideMotorRight.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        slideMotorRight.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        slideMotorRight.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
        slideMotorLeft.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        slideMotorLeft.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        slideMotorLeft.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);

        imuSensor = initializeIMUSensor();
        antiTipping = new AntiTipping(driveMotors, imuSensor);
        autoAlignment = new AutoAlignment(driveMotors, imuSensor);
    }

    private IMU initializeIMUSensor() {
        IMU imu = hardwareMap.get(IMU.class, "imu");

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