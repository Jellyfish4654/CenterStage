package org.firstinspires.ftc.teamcode.Framework;

import static com.qualcomm.robotcore.hardware.DcMotor.ZeroPowerBehavior.BRAKE;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.gamepad2;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

import com.ThermalEquilibrium.homeostasis.Controllers.Feedback.BasicPID;
import com.ThermalEquilibrium.homeostasis.Parameters.PIDCoefficients;
import com.qualcomm.robotcore.hardware.DcMotor;import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Framework.misc.MotionProfile;

public class Slides{
    private final DcMotorEx slideMotorLeft;
    private final DcMotorEx slideMotorRight;
    private PIDCoefficients coefficients;
    private BasicPID controller;
    private ElapsedTime timer;
    private MotionProfile profile;

    // Constants and target positions
    public  double KP = 0;
    public  double KI = 0;
    public double KD = 0;
    public double MAX_ACCELERATION = 1.0;
    public double MAX_VELOCITY = 1.0;
    public double MAX_DECELERATION = 1.0;
    public  int targetPosition = 0;
    public int HANG_UP_POSITION = 13000;
    public int HANG_DOWN_POSITION = 0;

    public Slides(DcMotorEx leftMotor, DcMotorEx rightMotor){
        this.slideMotorLeft = leftMotor;
        this.slideMotorRight = rightMotor;
        this.coefficients = new PIDCoefficients(KP, KI, KD);
        this.controller = new BasicPID(coefficients);
        this.timer = new ElapsedTime();
    }

    public void update() {
        double elapsedTime = timer.seconds();
        double leftCurrentPosition = slideMotorLeft.getCurrentPosition();
        double rightCurrentPosition = slideMotorRight.getCurrentPosition();
        // Calculate target position using the trapezoidal profile
        MotionProfile.State motionState = profile.calculate(elapsedTime);
        double instantTargetPosition = motionState.x;

        // Calculate PID output
        double leftMotorPower = controller.calculate(instantTargetPosition, leftCurrentPosition);
        double rightMotorPower = controller.calculate(instantTargetPosition, rightCurrentPosition);

        // Set motor power
        slideMotorLeft.setPower(leftMotorPower);
        slideMotorRight.setPower(rightMotorPower);
    }

    public void setTargetPosition(int target) {
        targetPosition = target;
        profile = new MotionProfile(slideMotorLeft.getCurrentPosition(), target, new MotionProfile.Constraints(MAX_ACCELERATION, MAX_VELOCITY, MAX_DECELERATION));
        timer.reset();
    }
}