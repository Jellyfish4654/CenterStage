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
    public static int targetPosition = 0;
    private static final double MAX_ACCELERATION = 1.0;
    private static final double MAX_VELOCITY = 1.0;
    private static final double MAX_DECELERATION = 1.0;
    private static boolean isManualControl = false;
    private BasicPID controller;
    private static double profileStartTime;
    PIDCoefficients coefficients;
    public static ElapsedTime timer = new ElapsedTime();

    private ElapsedTime servoTimer;
    public double KP = 0;
    public double KI = 0;
    public double KD = 0;
    private MotionProfile motionProfile;

    public Intake(DcMotorEx intakeMotor, Servo intakeServo) {
        this.intakeMotor = intakeMotor;
        this.intakeServo = intakeServo;
        this.servoTimer = new ElapsedTime();
        this.coefficients = new PIDCoefficients(KP, KI, KD);
        this.controller = new BasicPID(coefficients);
        initializeMotionProfiles();
    }

    public void update() {
        double elapsedTime = timer.seconds();

        // Update motion profiles
        MotionProfile.State leftState = motionProfile.calculate(elapsedTime);
        
        // Calculate PID output for each motor
        double PIDOutput = controller.calculate(targetPosition, intakeMotor.getCurrentPosition());

        // Apply simple proportional control using the motion profile's target position
        double motorPower = (leftState.x - intakeMotor.getCurrentPosition()) * PIDOutput;
        
        // Set motor power
        intakeMotor.setPower(motorPower);
    }
    private void initializeMotionProfiles() {
        // Set initial target as current position
        int initialPosition = intakeMotor.getCurrentPosition();
        this.motionProfile = new MotionProfile(initialPosition, initialPosition, new MotionProfile.Constraints(MAX_ACCELERATION, MAX_VELOCITY, MAX_DECELERATION));
    }
    public void setTargetPosition(int target) {
        this.targetPosition = target;
        // Reset the motion profiles with the current positions and the new target
        this.motionProfile = new MotionProfile(intakeMotor.getCurrentPosition(), target, new MotionProfile.Constraints(MAX_ACCELERATION, MAX_VELOCITY, MAX_DECELERATION));
        timer.reset();
    }

    public void setManualControl(boolean manualControl) {
        isManualControl = manualControl;
        if (!manualControl) {
            profileStartTime = timer.seconds(); // Reset profile start time when switching back to automated control
        }
    }

    public void servoIntakeInit() {
        intakeServo.setPosition(0.235);
    }
    public void servoIntakeOut() {
        intakeServo.setPosition(0.115);
        servoTimer.reset();
    }
    public void servoIntakeDrone(){
        if(servoTimer.seconds() >= 0.75) {
            intakeServo.setPosition(0.97);
        }
    }
}
