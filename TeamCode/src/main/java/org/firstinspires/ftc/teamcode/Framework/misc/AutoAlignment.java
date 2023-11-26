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

    public void update() {
        double yaw = imuSensor.getRobotYawPitchRollAngles().getPitch(AngleUnit.RADIANS);
        double targetPosition = 0;
        double yawCorrection = yawController.calculate(targetPosition, yaw);
        MotorPowers(yawCorrection);
    }

    private void MotorPowers(double correction) {
        double rightPower = correction;
        double leftPower = -correction;

        motors[0].setPower(rightPower);
        motors[1].setPower(rightPower);
        motors[2].setPower(leftPower);
        motors[3].setPower(leftPower);
    }
}
