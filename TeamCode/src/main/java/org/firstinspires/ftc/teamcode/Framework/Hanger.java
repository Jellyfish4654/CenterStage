package org.firstinspires.ftc.teamcode.Framework;

import com.qualcomm.robotcore.hardware.DcMotor;

public class Hanger {

    private final DcMotor hangerMotor;
    private final int TARGET_POSITION_UP = 1000;
    private final int TARGET_POSITION_DOWN = 0;

    public Hanger(DcMotor motor) {
        this.hangerMotor = motor;
    }

    public void hangUp() {
        int currentPosition = hangerMotor.getCurrentPosition();
        if (currentPosition < TARGET_POSITION_UP) {
            hangerMotor.setPower(1.0); // Move up at full power
        } else {
            stopMotor(); // Stop if it's already at or above the target
        }
    }

    public void hangDown() {
        int currentPosition = hangerMotor.getCurrentPosition();
        if (currentPosition > TARGET_POSITION_DOWN) {
            hangerMotor.setPower(-1.0); // Move down at full power
        } else {
            stopMotor(); // Stop if it's already at or below the target
        }
    }

    private void stopMotor() {
        hangerMotor.setPower(0); // Stop the motor
    }
}
