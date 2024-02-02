package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name = "Double Servo Test")
public class DoubleServoTuner extends LinearOpMode
{

    private enum State
    {
        IDLE,
        DEPOSIT,
        INTAKE
    }

    private ElapsedTime runtime = new ElapsedTime();
    private State currentState = State.IDLE;

    @Override
    public void runOpMode() throws InterruptedException
    {
        final Servo outtakeLeftServo;
        final Servo outtakeRightServo;
        final CRServo outtakeCRServo;

        outtakeLeftServo = hardwareMap.get(Servo.class, "armLeftServo");
        outtakeRightServo = hardwareMap.get(Servo.class, "armRightServo");
        outtakeLeftServo.setDirection(Servo.Direction.REVERSE);

        outtakeCRServo = hardwareMap.get(CRServo.class, "wheelServo");
        outtakeCRServo.setDirection(CRServo.Direction.REVERSE);
        double position = 0.2;

        waitForStart();
        runtime.reset();

        while (opModeIsActive())
        {
            telemetry.addData("ServoR", position);
            telemetry.update();

            outtakeLeftServo.setPosition(position);
            outtakeRightServo.setPosition(position);

            if (gamepad1.dpad_left)
            {
                position -= 0.0001;
            }
            if (gamepad1.dpad_right)
            {
                position += 0.0001;
            }

            if (gamepad1.a)
            {
                position = 0.59;
            }
            else if (gamepad1.b)
            {
                position = 0.2;
            }
            // State Machine b/c CRServo need to change power to turn off
            switch (currentState)
            {
                case IDLE:
                    if (gamepad2.left_stick_y < 0)
                    {
                        currentState = State.INTAKE;
                    }
                    else if (gamepad2.x)
                    {
                        currentState = State.DEPOSIT;
                    }
                    outtakeCRServo.setPower(0);
                    break;
                case INTAKE:
                    if (gamepad2.x)
                    {
                        currentState = State.DEPOSIT;
                    }
                    else if (!(gamepad2.left_stick_y < 0))
                    {
                        currentState = State.IDLE;
                    }
                    outtakeCRServo.setPower(gamepad2.left_stick_y);
                    break;
                case DEPOSIT:
                    if (gamepad2.left_stick_y < 0)
                    {
                        currentState = State.INTAKE;
                    }
                    else if (!gamepad2.x)
                    {
                        currentState = State.IDLE;
                    }
                    outtakeCRServo.setPower(0.1);
                    break;
            }
//            switch (currentState) {
//                case IDLE:
//                    if (gamepad1.x) {
//                        currentState = State.BURST_ACTIVE;
//                        outtakeCRServo.setPower(0.8); // Start the servo burst
//                        runtime.reset(); // Reset the timer
//                    } else if (gamepad1.left_stick_y != 0) {
//                        currentState = State.JOYSTICK_ACTIVE;
//                        outtakeCRServo.setPower(gamepad1.left_stick_y);
//                    }
//                    break;
//
//                case BURST_ACTIVE:
//                    if (runtime.milliseconds() > 100) { // Burst duration
//                        outtakeCRServo.setPower(0); // Stop the servo
//                        currentState = State.BURST_COOLDOWN;
//                        runtime.reset(); // Reset the timer for cooldown
//                    }
//                    break;
//
//                case BURST_COOLDOWN:
//                    if (runtime.milliseconds() > 600) { // Cooldown duration
//                        currentState = State.IDLE;
//                    }
//                    break;
//
//                case JOYSTICK_ACTIVE:
//                    if (gamepad1.left_stick_y == 0) {
//                        outtakeCRServo.setPower(0);
//                        currentState = State.IDLE;
//                    } else {
//                        outtakeCRServo.setPower(gamepad1.left_stick_y);
//                    }
//                    break;
//            }
        }
    }
}