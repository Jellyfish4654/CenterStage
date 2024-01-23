package org.firstinspires.ftc.teamcode.Framework;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.util.ElapsedTime;

public class outtakeCRServoAction {
    private ElapsedTime timer;
    private boolean started;
    private final CRServo outtakeCRServo;

    public outtakeCRServoAction(CRServo CRServoAction) {
        this.outtakeCRServo = CRServoAction;
        this.timer = new ElapsedTime();
        this.started = false;
    }

    public boolean run() {
        if (!started) {
            outtakeCRServo.setPower(1);
            timer.reset();
            started = true;
        } else if (timer.seconds() >= 2) {
            outtakeCRServo.setPower(0);
            return false; // Action complete
        }
        return true; // Action not yet complete
    }
}
