package org.firstinspires.ftc.teamcode.Framework;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.Servo;
import org.firstinspires.ftc.teamcode.Framework.misc.AprilTagPipeline;

public abstract class BaseOpMode extends LinearOpMode {
    protected DcMotor[] driveMotors;
    protected Hanger hanger;
    protected Slides slides;
    protected Intake intakeMotors;
    protected IMU imuSensor;
    protected DroneLauncher droneLauncher;
    protected Outake outakeServos;
    protected Servo intakeServo;
    protected AprilTagPipeline aprilTagPipeline;
    protected void initHardware() {
        aprilTagPipeline = new AprilTagPipeline(hardwareMap);

        driveMotors = new DcMotor[] {
                hardwareMap.dcMotor.get("motorFR"),
                hardwareMap.dcMotor.get("motorBR"),
                hardwareMap.dcMotor.get("motorFL"),
                hardwareMap.dcMotor.get("motorBL")
        };
        DcMotor intakeMotor = hardwareMap.get(DcMotorEx.class, "Tubing");
        Servo intakeServo = hardwareMap.get(Servo.class, "Servo");
        intakeMotors = new Intake(intakeMotor, intakeServo);
        // Set motor directions to match physical configuration
        setMotorDirections(new DcMotorSimple.Direction[] {
                DcMotorSimple.Direction.REVERSE, // motorFR
                DcMotorSimple.Direction.REVERSE, // motorBR
                DcMotorSimple.Direction.FORWARD, // motorFL
                DcMotorSimple.Direction.FORWARD  // motorBL
        });
        hanger = new Hanger(hardwareMap.get(DcMotorEx.class, "hangerMotor"));
        slides = new Slides(
                hardwareMap.get(DcMotorEx.class, "slideMotorLeft"),
                hardwareMap.get(DcMotorEx.class, "slideMotorRight")
        );

        // Initialize the IMU sensor with appropriate parameters
        imuSensor = initializeIMUSensor("imu");

        // Initialize the drone launcher with the corresponding servo
        droneLauncher = new DroneLauncher(hardwareMap.get(Servo.class, "droneServo"));

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