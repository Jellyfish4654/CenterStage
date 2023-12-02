package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Framework.BaseOpMode;

@TeleOp(name = "Servo Test")
public class ServoTuner extends BaseOpMode {
    @Override
    public void runOpMode() throws InterruptedException{
        Servo clawServo  = null;
        clawServo = hardwareMap.get(Servo.class, "outtakeLeftServo");
        waitForStart();

        double position = 0.5;


        while(opModeIsActive()){
            telemetry.addData("Servo", position);
            telemetry.update();

            clawServo.setPosition(position);

            if(gamepad1.dpad_left){
                position-=0.0001;
            }
            if(gamepad1.dpad_right){
                position+=0.0001;
            }

            if (gamepad1.a){
                clawServo.setPosition(1.0);
            } else if (gamepad1.b){
                clawServo.setPosition(0);
            }

        }
    }
}