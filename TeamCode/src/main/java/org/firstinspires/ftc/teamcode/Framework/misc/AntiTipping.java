package org.firstinspires.ftc.teamcode.Framework.misc;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.IMU;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import com.ThermalEquilibrium.homeostasis.Controllers.Feedback.BasicPID;
import com.ThermalEquilibrium.homeostasis.Parameters.PIDCoefficients;


public class AntiTipping {

    private final DcMotor[] motors;
    private final IMU imuSensor;
    PIDCoefficients pitchCoefficients = new PIDCoefficients(0,0,0);
    PIDCoefficients rollCoefficients = new PIDCoefficients(0,0,0);
    private final BasicPID pitchController = new BasicPID(pitchCoefficients);
    private final BasicPID rollController = new BasicPID(rollCoefficients);

    public AntiTipping(DcMotor[] motors, IMU imuSensor) {
        this.motors = motors;
        this.imuSensor = imuSensor;
    }

    public void correctTilt() {
        double pitch = imuSensor.getRobotYawPitchRollAngles().getPitch(AngleUnit.RADIANS);
        double roll = imuSensor.getRobotYawPitchRollAngles().getRoll(AngleUnit.RADIANS);

        double pitchCorrection = pitchController.calculate(0, pitch);
        double rollCorrection = rollController.calculate(0, roll);

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