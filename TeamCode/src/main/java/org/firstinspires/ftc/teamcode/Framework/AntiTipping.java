package org.firstinspires.ftc.teamcode.Framework;

import com.arcrobotics.ftclib.controller.PIDController;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.IMU;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;

public class AntiTipping {
    private final DcMotor[] motors;
    private final IMU imuSensor;
    private final PIDController pitchController;
    private static final double kP = 0.05;
    private static final double kI = 0.0;
    private static final double kD = 0.0005;
    private static final double THRESHOLD = 2.5;
    private static double IMU_ERROR = -7.5; // Error in IMU pitch reading

    // Constructor
    public AntiTipping(DcMotor[] motors, IMU imuSensor) {
        this.motors = motors;
        this.imuSensor = imuSensor;
        this.pitchController = new PIDController(kP, kI, kD);
    }

    public void update() {
        YawPitchRollAngles orientation = imuSensor.getRobotYawPitchRollAngles();
        double currentPitch = orientation.getPitch(AngleUnit.DEGREES);
        currentPitch -= IMU_ERROR;
        double pitchDifference = -normalizeAngle(currentPitch);
        if (Math.abs(pitchDifference) > THRESHOLD) {
            double correction = pitchController.calculate(0, pitchDifference);
            MotorPowers(correction);
        }
    }

    private void MotorPowers(double correction) {
        double power = -correction;
        power = Math.max(-1, Math.min(1, power));

        motors[0].setPower(power);
        motors[1].setPower(power);
        motors[2].setPower(power);
        motors[3].setPower(power);
    }

    private double normalizeAngle(double angle) {
        angle = angle % 360;
        if (angle <= -180) {
            angle += 360;
        } else if (angle > 180) {
            angle -= 360;
        }
        return angle;
    }

    public void initImuError() {
        YawPitchRollAngles orientation = imuSensor.getRobotYawPitchRollAngles();
        IMU_ERROR = orientation.getPitch(AngleUnit.DEGREES);
    }
}