package org.firstinspires.ftc.teamcode.Framework;

import com.qualcomm.robotcore.hardware.Servo;

public class Outake {
    private static final double OPEN_POSITION_LEFT_SERVO = -0.8754;
    private static final double CLOSE_POSITION_LEFT_SERVO = -0.5129;
    private static final double OPEN_POSITION_RIGHT_SERVO = 0.8754;
    private static final double CLOSE_POSITION_RIGHT_SERVO = 0.5129;

    private final Servo outakeLeftServo;
    private final Servo outakeRightServo;

    public Outake(Servo servo1, Servo servo2) {
        this.outakeLeftServo = servo1;
        this.outakeRightServo = servo2;
    }

    public void openOutake() {
        outakeLeftServo.setPosition(OPEN_POSITION_LEFT_SERVO); 
        outakeRightServo.setPosition(OPEN_POSITION_RIGHT_SERVO); 
    }

    public void closeOutake() {
        outakeLeftServo.setPosition(CLOSE_POSITION_LEFT_SERVO);
        outakeRightServo.setPosition(CLOSE_POSITION_RIGHT_SERVO);
    }

    public double getCurrentPositionLeftServo() {
        return outakeLeftServo.getPosition();
    }

    public double getCurrentPositionRightServo() {
        return outakeRightServo.getPosition(); 
    }
}
