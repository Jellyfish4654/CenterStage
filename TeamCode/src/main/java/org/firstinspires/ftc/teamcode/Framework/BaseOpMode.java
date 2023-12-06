package org.firstinspires.ftc.teamcode.Framework;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
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
    protected Slides slides;
    protected Hanger hanger;
    
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
        outakeServos = new Outake(outakeServosLeftServo, outakeServosRightServo);
        
        DcMotorEx slideMotorLeft = hardwareMap.get(DcMotorEx.class, "slideMotorLeft");
        slideMotorLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        // Initialize the right slide motor (no direction change)
        DcMotorEx slideMotorRight = hardwareMap.get(DcMotorEx.class, "slideMotorRight");
        // Initialize the Slides object with the modified slide motors
        slides = new Slides(slideMotorLeft, slideMotorRight);
        
        hanger = new Hanger(hardwareMap.get(DcMotorEx.class, "hangerMotor"));
        
        antiTipping = new AntiTipping(driveMotors, imuSensor);
        autoAlignment = new AutoAlignment(driveMotors, imuSensor);
//      aprilTagPipeline = new AprilTagPipeline(hardwareMap);
        // Initialize the IMU sensor with appropriate parameters
        imuSensor = initializeIMUSensor();
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