package org.firstinspires.ftc.teamcode.Framework;

import com.ThermalEquilibrium.homeostasis.Controllers.Feedback.BasicPID;
import com.ThermalEquilibrium.homeostasis.Parameters.PIDCoefficients;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Framework.misc.MotionProfile;

public class Intake {

    private final DcMotor intakeMotor;
    private final Servo intakeServo;

    public Intake(DcMotor intakeMotor, Servo intakeServo) {
        this.intakeMotor = intakeMotor;
        this.intakeServo = intakeServo;
    }

    public static double KP = 0;
    public static int targetPosition = 0;
    public static double MAX_ACCELERATION = 1.0;
    public static double MAX_VELOCITY = 1.0;

    PIDCoefficients coefficients = new PIDCoefficients(KP, 0, 0);
    BasicPID controller = new BasicPID(coefficients);
    public static ElapsedTime timer = new ElapsedTime();

    public void update(){
        double elapsedTime = timer.seconds();
        int currentPosition = intakeMotor.getCurrentPosition();
        double KP_POWER = controller.calculate(targetPosition, currentPosition);
        double distance = targetPosition - intakeMotor.getCurrentPosition();
        double instantTargetPosition = MotionProfile.motion_profile(MAX_ACCELERATION,
                MAX_VELOCITY,
                distance,
                elapsedTime);
        double motorPower = (instantTargetPosition - intakeMotor.getCurrentPosition()) * KP_POWER;

        intakeMotor.setPower(motorPower);
    }

    public static void setTargetPosition(int target){
        targetPosition = target;
        timer.reset();
    }
    public void servoDown(){
        intakeServo.setPosition(1);
    }

    public static void setManualTargetPosition(int target){
        targetPosition = target;
    }

    public static int getTargetPosition() {
        return targetPosition;
    }
}
