package org.firstinspires.ftc.teamcode;

import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.Framework.BaseOpMode;
import org.firstinspires.ftc.teamcode.Framework.misc.GamepadEX;
import org.firstinspires.ftc.teamcode.Framework.misc.SlewRateLimiter;

@TeleOp(name = "CenterStage JellyTele")
public class JellyTele extends BaseOpMode {
    private static final double PRECISION_MULTIPLIER_LOW = 0.35;
    private static final double PRECISION_MULTIPLIER_HIGH = 0.7;
    private static final double RATE_LIMIT = 0.5;
    private static final double DEADBAND_VALUE = 0.02;
    private static final double STRAFE_ADJUSTMENT_FACTOR = 1.1;
    private static final double MAX_SCALE = 1.0;
    private static final int ENDGAME_ALERT_TIME = 115; // Seconds
    protected enum DriveMode {
        MECANUM,
        FIELDCENTRIC
    }
    public enum OutakeState {
        SLIDES_RETRACT,
        OUTAKE_OPEN,
        OUTAKE_CLOSE
    }

    protected DriveMode driveMode = DriveMode.FIELDCENTRIC;
    private int slidePosition = 0;
    private final int[] autoSlidePositions = {0, 1000, 2000, 3000};
    private final SlewRateLimiter[] slewRateLimiters = new SlewRateLimiter[4];
    private boolean Hanging = false;
    private boolean sliding = true;
    private GamepadEX gamepadEX1;
    private GamepadEX gamepadEX2;

    @Override
    public void runOpMode() throws InterruptedException {
        initHardware();
        initializeSlewRateLimiters();
        intakeSystem.servoIntakeInit();
        GamepadEX gamepadEX1 = new GamepadEX(gamepad1);
        GamepadEX gamepadEX2 = new GamepadEX(gamepad2);
        DcMotorEx slideMotorLeft = hardwareMap.get(DcMotorEx.class, "slideMotorLeft");
        slideMotorLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        DcMotorEx slideMotorRight = hardwareMap.get(DcMotorEx.class, "slideMotorRight");
        waitForStart();
        ElapsedTime timer = new ElapsedTime();
        intakeSystem.servoIntakeOut();
        while (opModeIsActive()) {
            gamepadEX1.readButtons();
            gamepadEX2.readButtons();
            updateDriveModeFromGamepad();
            if (timer.seconds() == ENDGAME_ALERT_TIME) {
                gamepad1.runRumbleEffect(effect);
                gamepad2.runRumbleEffect(effect);
            }
            intakeSystem.servoIntakeDrone();
            double precisionMultiplier = calculatePrecisionMultiplier();
            resetIMU();
            displayTelemetry(precisionMultiplier);
            DriveMode(precisionMultiplier);
            OutakeControl();
            outakeServos.setOutput();
            slideMotorLeft.setPower(-gamepad2.right_stick_y);
            slideMotorRight.setPower(gamepad2.right_stick_y);
            intakeSystem.intakeMotor.setPower((gamepad2.left_stick_y)*0.75);
            DroneControl();
        }
    }

    public void DroneControl() {
        if(gamepadEX2.justReleased(GamepadEX.Button.BACK)){
            droneServo.launchDrone();
        }


    }

    private void OutakeControl(){

        if (gamepadEX2.justReleased(GamepadEX.Button.LEFT_BUMPER)){
            outakeServos.closeOutake();
        }
        if (gamepadEX2.justReleased(GamepadEX.Button.RIGHT_BUMPER)){
            outakeServos.openOutake();
        }
    }

    private void initializeSlewRateLimiters() {
        for (int i = 0; i < slewRateLimiters.length; i++) {
            slewRateLimiters[i] = new SlewRateLimiter(RATE_LIMIT);
        }
    }

    private void updateDriveModeFromGamepad() {
        if (gamepadEX1.justReleased(GamepadEX.Button.DPAD_UP)) {
            driveMode = DriveMode.MECANUM;
        } else if (gamepadEX1.justReleased(GamepadEX.Button.DPAD_DOWN)) {
            driveMode = DriveMode.FIELDCENTRIC;
        }
    }
    Gamepad.RumbleEffect effect = new Gamepad.RumbleEffect.Builder()
            .addStep(0.0, 1.0, 1000)
            .addStep(0.0, 0.0, 250)
            .addStep(1.0, 0.0, 1000)
            .addStep(0.0, 0.0, 250)
            .addStep(0.0, 1.0, 1000)
            .addStep(0.0, 0.0, 250)
            .addStep(1.0, 0.0, 1000)
            .build();

    private void alertEndGame(ElapsedTime timer) {

    }

    private double calculatePrecisionMultiplier() {
        if (gamepad1.left_bumper) {
            return PRECISION_MULTIPLIER_LOW;
        } else if (gamepad1.right_bumper) {
            return PRECISION_MULTIPLIER_HIGH;
        }
        return MAX_SCALE;
    }

    private void resetIMU() {
        if (gamepadEX1.justReleased(GamepadEX.Button.BACK)) {
            imuSensor.resetYaw();
            gamepad1.rumbleBlips(3);
        }
    }

    private void displayTelemetry(double precisionMultiplier) {
        telemetry.addData("drive mode", driveMode);
        telemetry.addData("mX", gamepad2.left_stick_x);
        telemetry.addData("mY", gamepad2.left_stick_y);
        telemetry.addData("precision mode", precisionMultiplier);
        telemetry.update();
    }

    private void DriveMode(double precisionMultiplier) {
        double[] motorSpeeds = new double[4];
        switch (driveMode) {
            case MECANUM:
                motorSpeeds = MecanumDrive();
                break;
            case FIELDCENTRIC:
                motorSpeeds = FieldCentricDrive();
                break;
        }
        setMotorSpeeds(precisionMultiplier, motorSpeeds);
    }

    private double[] MecanumDrive() {
        double pivot = applyDeadband(gamepad1.right_stick_x);
        double strafe = applyDeadband(gamepad1.left_stick_x) * STRAFE_ADJUSTMENT_FACTOR;
        double forward = -applyDeadband(gamepad1.left_stick_y);
        return new double[]{
                forward - strafe - pivot,
                forward + strafe - pivot,
                forward + strafe + pivot,
                forward - strafe + pivot
        };
    }

    private double[] FieldCentricDrive() {
        double forward = -applyDeadband(gamepad1.left_stick_y);
        double strafe = applyDeadband(gamepad1.left_stick_x) * STRAFE_ADJUSTMENT_FACTOR;
        double rotation = applyDeadband(gamepad1.right_stick_x);
        double botHeading = imuSensor.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS);

        double rotX = strafe * Math.cos(-botHeading) - forward * Math.sin(-botHeading);
        double rotY = strafe * Math.sin(-botHeading) + forward * Math.cos(-botHeading);
        return new double[]{
                rotY - rotX - rotation,
                rotY + rotX - rotation,
                rotY + rotX + rotation,
                rotY - rotX + rotation
        };
    }

    protected void setMotorSpeeds(double multiplier, double[] powers) {

        applyPrecisionAndScale(multiplier, powers);

        for (int i = 0; i < driveMotors.length; i++) {
            driveMotors[i].setPower(powers[i]);
        }
    }

    private void applySlewRateLimit(double[] powers) {
        for (int i = 0; i < slewRateLimiters.length; i++) {
            powers[i] = slewRateLimiters[i].calculate(powers[i]);
        }
    }

    private void applyPrecisionAndScale(double multiplier, double[] powers) {
        for (int i = 0; i < powers.length; i++) {
            powers[i] *= multiplier;
        }

        double maxPower = findMaxPower(powers);
        double scale = maxPower > MAX_SCALE ? MAX_SCALE / maxPower : 1.0;

        for (int i = 0; i < powers.length; i++) {
            powers[i] *= scale;
        }
    }

    private double findMaxPower(double[] powers) {
        double max = 0;
        for (double power : powers) {
            max = Math.max(max, Math.abs(power));
        }
        return max;
    }

    public double applyDeadband(double joystickValue) {
        double sign = Math.signum(joystickValue);
        return joystickValue + (-sign * DEADBAND_VALUE);
    }
}
