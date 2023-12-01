package org.firstinspires.ftc.teamcode.Framework;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

import com.ThermalEquilibrium.homeostasis.Controllers.Feedback.BasicPID;
import com.ThermalEquilibrium.homeostasis.Parameters.PIDCoefficients;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Framework.misc.MotionProfile;

public class Intake {

    private final DcMotorEx intakeMotor;
    private final Servo intakeServo;

    public Intake(DcMotorEx intakeMotor, Servo intakeServo) {
        this.intakeMotor = intakeMotor;
        this.intakeServo = intakeServo;
    }

    public static double KP = 0.005;
    public static int targetPosition = 0;
    public static double MAX_ACCELERATION = 0.8;
    public static double MAX_VELOCITY = 0.8;
    public static double servoPosition = 0.5;

    PIDCoefficients coefficients = new PIDCoefficients(KP, 0, 0);
    BasicPID controller = new BasicPID(coefficients);
    public static ElapsedTime timer = new ElapsedTime();

    public void intakeDown() {
        intakeServo.setPosition(0.8);
    }

    public static void runPosition() {
        setTargetPosition(getTargetPosition()+1000);
    }

    public void update() {
        double elapsedTime = timer.seconds();
        int currentPosition = intakeMotor.getCurrentPosition();
        double KP_POWER = controller.calculate(targetPosition, currentPosition);
        double distance = targetPosition - intakeMotor.getCurrentPosition();
        double instantTargetPosition = MotionProfile.motion_profile(MAX_ACCELERATION,
                MAX_VELOCITY,
                distance,
                elapsedTime);
        double motorPower = (instantTargetPosition - intakeMotor.getCurrentPosition()) * KP_POWER;

        intakeMotor.setPower(KP_POWER);
    }

    public static void setTargetPosition(int target) {
        targetPosition = target;
        timer.reset();
    }

    public void servoDown() {
        intakeServo.setPosition(1);
    }

    public static void setManualTargetPosition(int target) {
        targetPosition = target;
    }

    public static int getTargetPosition() {
        return targetPosition;
    }
}
