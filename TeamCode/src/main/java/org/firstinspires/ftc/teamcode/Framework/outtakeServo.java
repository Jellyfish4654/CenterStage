package org.firstinspires.ftc.teamcode.Framework;

import com.qualcomm.robotcore.hardware.Servo;

public class outtakeServo
{
    private static final double OUTTAKE_INTAKE = 0.248;
    private static final double OUTTAKE_DEPOSIT = 0.615;

    private final Servo outakeLeftServo;
    private final Servo outakeRightServo;
    private final Servo armLeftServo;
    private final Servo armRightServo;

    double positionL = 0.615;
    double positionR = 0.615;

    public outtakeServo(Servo servo1, Servo servo2, Servo leftArm, Servo rightArm)
    {
        this.outakeLeftServo = servo1;
        this.outakeRightServo = servo2;
        this.armLeftServo = leftArm;
        this.armRightServo = rightArm;
        outakeLeftServo.setPosition(positionL);
        outakeRightServo.setPosition(positionR);
    }

    public void openOuttake()
    {
        positionL = OUTTAKE_INTAKE;
        positionR = OUTTAKE_INTAKE;
        outakeLeftServo.setPosition(positionL);
        outakeRightServo.setPosition(positionR);
    }

    public void closeOuttake()
    {
        positionL = OUTTAKE_DEPOSIT;
        positionR = OUTTAKE_DEPOSIT;
        outakeLeftServo.setPosition(positionL);
        outakeRightServo.setPosition(positionR);
    }

    public void setOutput()
    {
        outakeLeftServo.setPosition(positionL);
        outakeRightServo.setPosition(positionR);
    }
    public double getCurrentPositionLeftServo()
    {
        return outakeLeftServo.getPosition();
    }
}
