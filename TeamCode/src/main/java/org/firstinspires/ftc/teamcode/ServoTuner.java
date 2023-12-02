package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Framework.BaseOpMode;

@TeleOp(name = "Servo Test")
public class ServoTuner extends BaseOpMode {
    @Override
    public void runOpMode() throws InterruptedException{
        final Servo outakeLeftServo;
        final Servo outakeRightServo;
        outakeLeftServo = hardwareMap.get(Servo.class, "outtakeLeftServo");
        outakeRightServo = hardwareMap.get(Servo.class, "outtakeRightServo");
        waitForStart();

        double positionL = -0.5;
        double positionR = 0.5;

        while(opModeIsActive()){
            telemetry.addData("ServoR", positionR);
            telemetry.addData("ServoL", positionL);
            telemetry.update();

            outakeLeftServo.setPosition(positionL);
            outakeRightServo.setPosition(positionR);

            if(gamepad1.dpad_left){
                positionR-=0.0001;
                positionL+=0.0001;
            }
            if(gamepad1.dpad_right){
                positionL-=0.0001;
                positionR+=0.0001;
            }


            if (gamepad1.a){
                positionR= -1.0;
                positionL= 1.0;
            } else if (gamepad1.b){
                positionR= 1.0;
                positionL= -1.0;
            }

        }
    }
}