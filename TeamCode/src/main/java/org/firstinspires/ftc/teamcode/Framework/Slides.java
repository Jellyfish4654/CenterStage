package org.firstinspires.ftc.teamcode.Framework;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.ThermalEquilibrium.homeostasis.Controllers.Feedback.BasicPID;
import com.ThermalEquilibrium.homeostasis.Parameters.PIDCoefficients;

public class Slides {
    private DcMotorEx leftMotor;
    private DcMotorEx rightMotor;
    public static int EXTENDED_STATE = 500;

    private static final double KP = 0.005;
    private static final double KI = 0;
    private static final double KD = 0;
    private static int leftTargetPosition = 0;
    private static int rightTargetPosition = 0;

    private PIDCoefficients leftCoefficients = new PIDCoefficients(KP, KI, KD);
    private PIDCoefficients rightCoefficients = new PIDCoefficients(KP, KI, KD);

    private BasicPID leftController = new BasicPID(leftCoefficients);
    private BasicPID rightController = new BasicPID(rightCoefficients);

    public Slides(DcMotorEx leftMotor, DcMotorEx rightMotor) {
        this.leftMotor = leftMotor;
        this.rightMotor = rightMotor;

        leftTargetPosition = leftMotor.getCurrentPosition();
        rightTargetPosition = rightMotor.getCurrentPosition();
    }

    public void update() {
        int leftCurrentPosition = leftMotor.getCurrentPosition();
        int rightCurrentPosition = rightMotor.getCurrentPosition();

        double LEFT_PIDF_POWER = leftController.calculate(leftTargetPosition, leftCurrentPosition);
        double RIGHT_PIDF_POWER = rightController.calculate(rightTargetPosition, rightCurrentPosition);

        leftMotor.setPower(LEFT_PIDF_POWER);
        rightMotor.setPower(RIGHT_PIDF_POWER);
    }

    public void setLeftTargetPosition(int target) {
        leftTargetPosition = target;
    }

    public void setRightTargetPosition(int target) {
        rightTargetPosition = target;
    }

    public int getLeftTargetPosition() {
        return this.leftTargetPosition;
    }

    public int getRightTargetPosition() {
        return this.rightTargetPosition;
    }
    public boolean slidesExtendedState() {
        return rightTargetPosition > EXTENDED_STATE;
    }
}
