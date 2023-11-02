package org.firstinspires.ftc.teamcode.Framework;

import com.qualcomm.robotcore.hardware.DcMotor;

public class Hanger {

    private DcMotor hangerMotor;
    int position = hangerMotor.getCurrentPosition();
    int targetUp = 1000;
    int targetDown = 0;

    public Hanger(DcMotor hangerMotor) {
        this.hangerMotor = hangerMotor;
        //constructor,,, dont do big stuff here
    }

    public void hangUp() {


        if (position < targetUp) {
            //target = amount to slide to,,, slides move until position is high enough
            hangerMotor.setPower(1.0);
        }
    }

    public void hangDown() {
        if (position > targetDown) {
            //target = 0 b/c slides extend until reaches bottom
            hangerMotor.setPower(-1.0);
        }
    }

}
