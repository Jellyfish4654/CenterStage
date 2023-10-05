package org.firstinspires.ftc.teamcode.Framework;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

public abstract class BaseOpMode extends LinearOpMode{
    protected DcMotor[] motors;
    protected Hanger hangers;

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

        Servo claw = hardwareMap.get(Servo.class, "servo");
        DcMotor otherMotor = hardwareMap.get(DcMotor.class, "linear_actuator");
        hangers = new Hanger(otherMotor);
    }
}
