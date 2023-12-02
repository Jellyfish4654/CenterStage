package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Framework.BaseOpMode;
import org.firstinspires.ftc.teamcode.Framework.Outake;

@TeleOp(name = "Servo Test")
public class ServoTuner extends BaseOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        final Servo outakeLeftServo;
        final Servo outakeRightServo;
        outakeLeftServo = hardwareMap.get(Servo.class, "outtakeLeftServo");
        outakeRightServo = hardwareMap.get(Servo.class, "outtakeRightServo");
        waitForStart();

        Outake outake = new Outake(outakeLeftServo, outakeRightServo);

        final double OPEN_POSITION_LEFT_SERVO = -1.0;
        final double CLOSE_POSITION_LEFT_SERVO = 1.0;
        final double OPEN_POSITION_RIGHT_SERVO = 1.0;
        final double CLOSE_POSITION_RIGHT_SERVO = -1.0;


        while (opModeIsActive()) {
            telemetry.addData("leftServo", outakeLeftServo.getPosition());
            telemetry.addData("rightServo", outakeRightServo.getPosition());
            telemetry.update();



            if (gamepad1.a) {
                outake.openOutake();
            }
            if (gamepad1.b) {
                outake.closeOutake();
            }



        }

    }
}