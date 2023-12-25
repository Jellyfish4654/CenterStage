package org.firstinspires.ftc.teamcode.Framework;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.util.ElapsedTime;

public class outtakeCRServo {
    private final CRServo wheelServo;
    private ElapsedTime timer;
    private boolean isMovingForward;
    private boolean isMovingBackward;

    public outtakeCRServo(CRServo wheelServo) {
        this.wheelServo = wheelServo;
        this.timer = new ElapsedTime();
        this.isMovingForward = false;
        this.isMovingBackward = false;
    }

    public void startMovingForward() {
        timer.reset();
        isMovingForward = true;
        isMovingBackward = false;
    }

    public void startMovingBackward() {
        timer.reset();
        isMovingBackward = true;
        isMovingForward = false;
    }

    public void update() {
        if (isMovingForward && timer.seconds() < 0.5) {
            wheelServo.setPower(1);
        } else if (isMovingBackward && timer.seconds() < 2) {
            wheelServo.setPower(-1);
        } else {
            wheelServo.setPower(0);
            isMovingForward = false;
            isMovingBackward = false;
        }
    }
}
