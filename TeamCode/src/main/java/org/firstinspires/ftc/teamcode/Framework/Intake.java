package org.firstinspires.ftc.teamcode.Framework;

import com.ThermalEquilibrium.homeostasis.Controllers.Feedback.BasicPID;
import com.ThermalEquilibrium.homeostasis.Parameters.PIDCoefficients;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Framework.misc.MotionProfile;

public class Intake {

    public final DcMotorEx intakeMotor;
    private final Servo intakeServo;

    public static double KP = 0.005;
    public static int targetPosition = 0;
    public static double MAX_ACCELERATION = 0.8;
    public static double MAX_VELOCITY = 0.8;
    private static boolean isManualControl = false;
    private static double profileStartTime;  // Track the start time of each profile

    PIDCoefficients coefficients = new PIDCoefficients(KP, 0, 0);
    BasicPID controller = new BasicPID(coefficients);
    public static ElapsedTime timer = new ElapsedTime();

    public Intake(DcMotorEx intakeMotor, Servo intakeServo) {
        this.intakeMotor = intakeMotor;
        this.intakeServo = intakeServo;
    }

    public void intakeDown() {
        intakeServo.setPosition(0.8);
    }

    public static void runPosition() {
        setTargetPosition(getTargetPosition() + 1000);
    }

    public void update() {
        double elapsedTime = timer.seconds() - profileStartTime; // Use elapsed time since profile start
        int currentPosition = intakeMotor.getCurrentPosition();
        double KP_POWER = controller.calculate(targetPosition, currentPosition);
        double distance = targetPosition - currentPosition;
        double instantTargetPosition = MotionProfile.motion_profile(MAX_ACCELERATION,
                MAX_VELOCITY,
                distance,
                elapsedTime);
        double motorPower = (instantTargetPosition - currentPosition) * KP_POWER;

        intakeMotor.setPower(KP_POWER); // Set to motorPower
    }

    public static void setTargetPosition(int target) {
        targetPosition = target;
//        if (!isManualControl) {
//            profileStartTime = timer.seconds(); // Update only if not in manual control
//        }
    }

    public void setManualControl(boolean manualControl) {
        isManualControl = manualControl;
        if (!manualControl) {
            profileStartTime = timer.seconds(); // Reset profile start time when switching back to automated control
        }
    }

    public void servoDown() {
        intakeServo.setPosition(1);
    }

    public static int getTargetPosition() {
        return targetPosition;
    }
}
