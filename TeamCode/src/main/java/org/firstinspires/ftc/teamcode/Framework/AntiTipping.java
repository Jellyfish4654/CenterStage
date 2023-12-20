package org.firstinspires.ftc.teamcode.Framework;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.IMU;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import com.arcrobotics.ftclib.controller.PIDController;

public class AntiTipping {
    private final DcMotor[] motors;
    private final IMU imuSensor;
    private PIDController pitchController;
    private PIDController rollController;
    private static double P = 0.005;
    private static double I = 0;
    private static double D = 0;

    public AntiTipping(DcMotor[] motors, IMU imuSensor) {
        this.motors = motors;
        this.imuSensor = imuSensor;

        this.pitchController = new PIDController(P, I, D);
        this.rollController = new PIDController(P, I, D);
    }
    
    public void correctTilt() {
        this.pitchController.setPID(P, I, D);
        this.rollController.setPID(P, I, D);
        double pitch = imuSensor.getRobotYawPitchRollAngles().getPitch(AngleUnit.RADIANS);
        double roll = imuSensor.getRobotYawPitchRollAngles().getRoll(AngleUnit.RADIANS);
        double targetPosition = 0;

        double pitchCorrection = pitchController.calculate(pitch, targetPosition);
        double rollCorrection = rollController.calculate(roll, targetPosition);

        double correction = Math.abs(pitch) > Math.abs(roll) ? pitchCorrection : rollCorrection;

        MotorPowers(correction);
    }

    private void MotorPowers(double correction) {
        int[] powerMap = {1, -1, 1, -1};
        for (int i = 0; i < motors.length; i++) {
            double power = correction * powerMap[i];
            power = Math.max(-1, Math.min(1, power));
            motors[i].setPower(power);
        }
    }
}