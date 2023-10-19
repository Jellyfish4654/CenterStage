package org.firstinspires.ftc.teamcode.Framework;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.Servo;

public abstract class BaseOpMode extends LinearOpMode{
    protected DcMotor[] motors;
    protected Hanger hangers;
    protected Slides slides;
    protected IMU imu;

    protected void initHardware() {
        motors = new DcMotor[]{
                hardwareMap.dcMotor.get("motor fr"),
                hardwareMap.dcMotor.get("motor br"),
                hardwareMap.dcMotor.get("motor fl"),
                hardwareMap.dcMotor.get("motor bl")
        };
        motors[0].setDirection(DcMotorSimple.Direction.REVERSE);
        motors[1].setDirection(DcMotorSimple.Direction.REVERSE);
        motors[2].setDirection(DcMotorSimple.Direction.FORWARD);
        motors[3].setDirection(DcMotorSimple.Direction.FORWARD);

        DcMotor otherMotor = hardwareMap.get(DcMotor.class, "linear_actuator");
        DcMotor motor1 = hardwareMap.get(DcMotor.class, "motor-1");
        DcMotor motor2 = hardwareMap.get(DcMotor.class, "motor-2");

        hangers = new Hanger(otherMotor);
        slides = new Slides(motor1, motor2);

        IMU imu = hardwareMap.get(IMU.class, "imu");
        // ADJUST ORIENTATION PARAMETERS TO MATCH THE ROBOT
        IMU.Parameters parameters = new IMU.Parameters(new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.FORWARD,
                RevHubOrientationOnRobot.UsbFacingDirection.UP));
        imu.initialize(parameters);
    }
}