package org.firstinspires.ftc.teamcode.Framework.misc;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.IMU;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import com.ThermalEquilibrium.homeostasis.Controllers.Feedback.BasicPID;
import com.ThermalEquilibrium.homeostasis.Parameters.PIDCoefficients;

public class AutoAlignment {
    private final DcMotor[] motors;
    private final IMU imuSensor;
    PIDCoefficients yawCoefficients = new PIDCoefficients(0,0,0);
    private final BasicPID yawController = new BasicPID(yawCoefficients);
    
    public AutoAlignment(DcMotor[] motors, IMU imuSensor) {
        this.motors = motors;
        this.imuSensor = imuSensor;
    }

    public void correctTilt() {
        double yaw = imuSensor.getRobotYawPitchRollAngles().getPitch(AngleUnit.RADIANS);
        double yawCorrection = yawController.calculate(0, yaw);
        MotorPowers(yawCorrection);
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
