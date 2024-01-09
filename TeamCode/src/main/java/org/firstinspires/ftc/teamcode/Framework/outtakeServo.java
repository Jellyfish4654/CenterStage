package org.firstinspires.ftc.teamcode.Framework;

import com.qualcomm.robotcore.hardware.Servo;

public class outtakeServo {
    private static final double OUTTAKE_INTAKE = 0.2;
    private static final double OUTTAKE_DEPOSIT = 0.59;

    private final Servo outakeLeftServo;
    private final Servo outakeRightServo;



    double positionL = 0.2;
    double positionR = 0.2;

    public outtakeServo(Servo servo1, Servo servo2) {
        this.outakeLeftServo = servo1;
        this.outakeRightServo = servo2;
    }

    public void openOuttake() {
        positionL=OUTTAKE_INTAKE;
        positionR=OUTTAKE_INTAKE;
    }

    public void closeOuttake() {
        positionL=OUTTAKE_DEPOSIT;
        positionR=OUTTAKE_DEPOSIT;
    }
    public void setOutput() {
        outakeLeftServo.setPosition(positionL);
        outakeRightServo.setPosition(positionR);
    }
    public boolean check(){
        if(getCurrentPositionLeftServo()==OUTTAKE_INTAKE){
            return true;
        }
        else{
            return false;
        }
    }

    public double getCurrentPositionLeftServo() {
        return outakeLeftServo.getPosition();
    }
}
