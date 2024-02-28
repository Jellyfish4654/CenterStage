package org.firstinspires.ftc.teamcode.Framework;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.VoltageSensor;

public abstract class BaseOpMode extends LinearOpMode
{
	protected DcMotor[] driveMotors;
	protected DroneLauncher droneServo;
	protected intakeDistance intakeDistanceSensor;
	protected Intake intakeSystem;
	protected outtakeServo outakeServos;
	protected DcMotorEx slideMotorLeft;
	protected DcMotorEx slideMotorRight;
	protected DcMotorEx intakeMotor;
	protected Slides slides;
	protected CRServo outtakeCRServo;
	protected outtakeCRServo wheelServo;
	protected IMU imuSensor;
	protected AntiTipping antiTipping;
	protected AutoAlignment autoAlignment;

	protected DistanceSensor distanceLeft;

	protected DistanceSensor distanceRight;

	protected void initHardware()
	{
		driveMotors = new DcMotor[]{
				//control hub
				hardwareMap.dcMotor.get("motorFL"),
				hardwareMap.dcMotor.get("motorBL"),
				hardwareMap.dcMotor.get("motorFR"),
				hardwareMap.dcMotor.get("motorBR")

		};
		setMotorDirections(new DcMotorSimple.Direction[]{
				DcMotorSimple.Direction.FORWARD, // motorFL
				DcMotorSimple.Direction.FORWARD, // motorBL
				DcMotorSimple.Direction.REVERSE, // motorFR
				DcMotorSimple.Direction.REVERSE  // motorBR
		});
		// expansion hub
		VoltageSensor voltageSensor = hardwareMap.get(VoltageSensor.class, "Control Hub");
		droneServo = new DroneLauncher(hardwareMap.get(Servo.class, "droneServo"), Servo.Direction.REVERSE, voltageSensor);
		outtakeCRServo = hardwareMap.get(CRServo.class, "wheelServo");
		outtakeCRServo.setDirection(CRServo.Direction.REVERSE);
		wheelServo = new outtakeCRServo(outtakeCRServo);
		intakeMotor = hardwareMap.get(DcMotorEx.class, "Tubing");
		intakeMotor.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
		intakeMotor.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
		intakeMotor.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
		Servo intakeServo = hardwareMap.get(Servo.class, "intakeServo");
		intakeServo.setDirection(Servo.Direction.REVERSE);
		intakeSystem = new Intake(
				hardwareMap.get(DcMotorEx.class, "Tubing"),
				intakeServo,
				outtakeCRServo
		);

		Servo outakeServosLeftServo = hardwareMap.get(Servo.class, "outtakeLeftServo");
		Servo outakeServosRightServo = hardwareMap.get(Servo.class, "outtakeRightServo");
		Servo armLeftServo = hardwareMap.get(Servo.class, "armLeftServo");
		Servo armRightServo = hardwareMap.get(Servo.class, "armRightServo");
		outakeServosLeftServo.setDirection(Servo.Direction.REVERSE);
		armLeftServo.setDirection(Servo.Direction.REVERSE);
		outakeServos = new outtakeServo(outakeServosLeftServo, outakeServosRightServo, armLeftServo, armRightServo);

		slideMotorLeft = hardwareMap.get(DcMotorEx.class, "slideMotorLeft");
		slideMotorRight = hardwareMap.get(DcMotorEx.class, "slideMotorRight");
		slideMotorRight.setDirection(DcMotorSimple.Direction.REVERSE);
//		slideMotorRight.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
		slideMotorRight.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
		slideMotorRight.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
//		slideMotorLeft.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
		slideMotorLeft.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
		slideMotorLeft.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
		slides = new Slides(slideMotorLeft, slideMotorRight, voltageSensor);
		imuSensor = initializeIMUSensor();
		antiTipping = new AntiTipping(driveMotors, imuSensor);
		autoAlignment = new AutoAlignment(driveMotors, imuSensor);
		distanceLeft = hardwareMap.get(DistanceSensor.class, "distanceLeft");
		distanceRight = hardwareMap.get(DistanceSensor.class, "distanceRight");
		intakeDistanceSensor = new intakeDistance(distanceRight);
	}

	private IMU initializeIMUSensor()
	{
		IMU imu = hardwareMap.get(IMU.class, "imu");

		IMU.Parameters parameters = new IMU.Parameters(new RevHubOrientationOnRobot(
				RevHubOrientationOnRobot.LogoFacingDirection.FORWARD,
				RevHubOrientationOnRobot.UsbFacingDirection.UP
		));
		imu.initialize(parameters);
		return imu;
	}

	private void setMotorDirections(DcMotorSimple.Direction[] directions)
	{
		for (int i = 0; i < driveMotors.length; i++)
		{
			driveMotors[i].setDirection(directions[i]);
		}
	}
}