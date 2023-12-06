package org.firstinspires.ftc.teamcode.Framework;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.ThermalEquilibrium.homeostasis.Controllers.Feedback.BasicPID;
import com.ThermalEquilibrium.homeostasis.Parameters.PIDCoefficients;

import org.firstinspires.ftc.teamcode.Framework.misc.MotionProfile;

public class Hanger {
    private final DcMotor hangerMotor;
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

    public Hanger(DcMotor motor) {
        this.hangerMotor = motor;
        this.coefficients = new PIDCoefficients(KP, KI, KD);
        this.controller = new BasicPID(coefficients);
        this.timer = new ElapsedTime();
    }

    public void update() {
        double elapsedTime = timer.seconds();
        double currentPosition = hangerMotor.getCurrentPosition();

        // Calculate target position using the trapezoidal profile
        MotionProfile.State motionState = profile.calculate(elapsedTime);
        double instantTargetPosition = motionState.x;

        // Calculate PID output
        double motorPower = controller.calculate(instantTargetPosition, currentPosition);

        // Set motor power
        hangerMotor.setPower(motorPower);
    }

    public void setTargetPosition(int target) {
        targetPosition = target;
        profile = new MotionProfile(hangerMotor.getCurrentPosition(), target, new MotionProfile.Constraints(MAX_ACCELERATION, MAX_VELOCITY, MAX_DECELERATION));
        timer.reset();
    }

    public void hangUp() {
        setTargetPosition(HANG_UP_POSITION);
    }

    public void hangDown() {
        setTargetPosition(HANG_DOWN_POSITION);
    }
}
