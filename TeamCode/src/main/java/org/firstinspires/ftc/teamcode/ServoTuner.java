package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Framework.BaseOpMode;

@TeleOp(name = "Servo Tuner")
public class ServoTuner extends BaseOpMode {
    protected Servo servo;
    public void runOpMode() throws InterruptedException{
        servo = hardwareMap.get(Servo.class, "droneServo");
        waitForStart();
        double position = 0.5;

        while(opModeIsActive()){
            telemetry.addData("Servo", position);
            telemetry.update();

            servo.setPosition(position);

            if(gamepad1.dpad_left){
                position-=0.0001;
            }
            if(gamepad1.dpad_right){
                position+=0.0001;
            }

            if (gamepad1.a){
                position = 0.5;
            } else if (gamepad1.b){
                position = 0.8;
            }
        }
    }
}