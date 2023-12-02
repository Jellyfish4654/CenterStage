package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;


@TeleOp(name = "Servo Test Out")
public class ServoTuner extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        final Servo outtakeLeftServo;
        final Servo outtakeRightServo;
        outtakeLeftServo = hardwareMap.get(Servo.class, "outtakeLeftServo");
        outtakeRightServo = hardwareMap.get(Servo.class, "outtakeRightServo");

        outtakeLeftServo.setDirection(Servo.Direction.REVERSE);
        double position = 0.5; // Initialize to midpoint

        waitForStart();

        while(opModeIsActive()) {
            telemetry.addData("ServoR", position);
            telemetry.update();

            outtakeLeftServo.setPosition(position);
            outtakeRightServo.setPosition(position);

            if(gamepad1.dpad_left){
                position-=0.0001;
            }
            if(gamepad1.dpad_right){
                position+=0.0001;
            }

            if (gamepad1.a) {
                position=0;
            } else if (gamepad1.b) {
                position=1;
            }
        }
    }
}

//package org.firstinspires.ftc.teamcode;
//
//        import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
//        import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
//        import com.qualcomm.robotcore.hardware.Servo;
//
//
//@TeleOp(name = "Servo Test")
//public class ServoTuner extends LinearOpMode {
//    @Override
//    public void runOpMode() throws InterruptedException {
//        final Servo outtakeLeftServo;
//        outtakeLeftServo = hardwareMap.get(Servo.class, "droneServo");
//
//        outtakeLeftServo.setDirection(Servo.Direction.REVERSE);
//        double position = 0.5; // Initialize to midpoint
//
//        waitForStart();
//
//        while(opModeIsActive()) {
//            telemetry.addData("ServoR", position);
//            telemetry.update();
//
//            outtakeLeftServo.setPosition(position);
//
//            if(gamepad1.dpad_left){
//                position-=0.0001;
//            }
//            if(gamepad1.dpad_right){
//                position+=0.0001;
//            }
//
//            if (gamepad1.a) {
//                position=0;
//            } else if (gamepad1.b) {
//                position=1;
//            }
//        }
//    }
//}
