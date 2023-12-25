package org.firstinspires.ftc.teamcode.Framework;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.Servo;

public class Outake {
    private static final double OPEN_POSITION_LEFT_SERVO = 0.65;
    private static final double CLOSE_POSITION_LEFT_SERVO = 0.0;
    private static final double OPEN_POSITION_RIGHT_SERVO = 0.65;
    private static final double CLOSE_POSITION_RIGHT_SERVO = 0.0;

    private final Servo outakeLeftServo;
    private final Servo outakeRightServo;



    double positionL = 0.0;
    double positionR = 0.0;

    public Outake(Servo servo1, Servo servo2) {
        this.outakeLeftServo = servo1;
        this.outakeRightServo = servo2;
    }

    public void openOutake() {
        positionL=OPEN_POSITION_LEFT_SERVO;
        positionR=OPEN_POSITION_RIGHT_SERVO;
    }

    public void closeOutake() {
        positionL=CLOSE_POSITION_LEFT_SERVO;
        positionR=CLOSE_POSITION_RIGHT_SERVO;
    }

    public void hang() {
//        positionL=CLOSE_POSITION_LEFT_SERVO;
//        positionR=CLOSE_POSITION_RIGHT_SERVO;
    }
    public void setOutput() {
        outakeLeftServo.setPosition(positionL);
        outakeRightServo.setPosition(positionR);
    }

    public double getCurrentPositionLeftServo() {
        return outakeLeftServo.getPosition();
    }

    public double getCurrentPositionRightServo() {
        return outakeRightServo.getPosition(); 
    }
}
