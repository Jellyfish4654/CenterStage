package org.firstinspires.ftc.teamcode.Framework;

import com.qualcomm.robotcore.hardware.CRServo;
import com.arcrobotics.ftclib.controller.PIDController;
import com.qualcomm.robotcore.util.ElapsedTime;

public class Gecko {
    private final CRServo geckoServo;
    private PIDController geckoController;
    private ElapsedTime timer;
    private double P = 2.0;
    private double I = 0;
    private double D = 0.02;
    private int targetPosition;


    public Gecko(CRServo servo3) {
        this.geckoServo = servo3;
        this.geckoController = new PIDController(P, I, D);
        this.timer = new ElapsedTime();
    }

    public void servoForward(){
        double time = timer.seconds();
        if (time<2){
            geckoServo.setPower(1);
        }
    }
    public void servoBackward(){
        double time = timer.seconds();
        if (time>2){
            geckoServo.setPower(-1);
        }
    }
    public void setPower(float trigger){
        geckoServo.setPower(1);
    }
}
