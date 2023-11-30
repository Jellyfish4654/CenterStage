package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Framework.BaseOpMode;

@TeleOp(name = "Servo Tuner")
public class ServoTuner extends BaseOpMode {
    protected CRServo servo;
    public void runOpMode() throws InterruptedException{
        servo = hardwareMap.get(CRServo.class, "droneServo");
        waitForStart();
        double position = 0.5;
        ElapsedTime DroneTimer = new ElapsedTime();
        while(opModeIsActive()){
            telemetry.addData("Servo", position);
            telemetry.update();

                servo.setPower(1);
                DroneTimer.reset();

                if (DroneTimer.seconds()>=0.5){
                    servo.setPower(0);
                }

//            servo.setPosition(position);
//
//            if(gamepad1.dpad_left){
//                position-=0.0001;
//            }
//            if(gamepad1.dpad_right){
//                position+=0.0001;
//            }
//
//            if (gamepad1.a){
//                position = 0.5;
//            } else if (gamepad1.b){
//                position = 0.8;
//            }
        }
    }
}