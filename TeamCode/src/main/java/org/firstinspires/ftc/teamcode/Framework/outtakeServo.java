package org.firstinspires.ftc.teamcode.Framework;

import com.qualcomm.robotcore.hardware.Servo;

public class outtakeServo {
    private static final double OPEN_POSITION_LEFT_SERVO = 0.65;
    private static final double CLOSE_POSITION_LEFT_SERVO = 0.0;
    private static final double OPEN_POSITION_RIGHT_SERVO = 0.65;
    private static final double CLOSE_POSITION_RIGHT_SERVO = 0.0;

    private final Servo outakeLeftServo;
    private final Servo outakeRightServo;



    double positionL = 0.0;
    double positionR = 0.0;

    public outtakeServo(Servo servo1, Servo servo2) {
        this.outakeLeftServo = servo1;
        this.outakeRightServo = servo2;
    }

    public void openOuttake() {
        positionL=OPEN_POSITION_LEFT_SERVO;
        positionR=OPEN_POSITION_RIGHT_SERVO;
    }

    public void closeOuttake() {
        positionL=CLOSE_POSITION_LEFT_SERVO;
        positionR=CLOSE_POSITION_RIGHT_SERVO;
    }
    public void setOutput() {
        outakeLeftServo.setPosition(positionL);
        outakeRightServo.setPosition(positionR);
    }
    public boolean check(){
        if(getCurrentPositionLeftServo()==0.5){
            return true;
        }
        else{
            return false;
        }
    }

    public double getCurrentPositionLeftServo() {
        return outakeLeftServo.getPosition();
    }

    public double getCurrentPositionRightServo() {
        return outakeRightServo.getPosition(); 
    }
}