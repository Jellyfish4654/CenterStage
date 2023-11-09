package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.Framework.BaseOpMode;
import org.firstinspires.ftc.teamcode.Framework.misc.AntiTipping;
import org.firstinspires.ftc.teamcode.Framework.misc.ButtonEX;
import org.firstinspires.ftc.teamcode.Framework.misc.SlewRateLimiter;

@TeleOp(name = "CenterStage JellyTele")
public class JellyTele extends BaseOpMode {

    private static final double PRECISION_MULTIPLIER_LOW = 0.35;
    private static final double PRECISION_MULTIPLIER_HIGH = 0.7;
    private static final double RATE_LIMIT = 0.5;
    private static final double DEADBAND_VALUE = 0.02;
    private static final double STRAFE_ADJUSTMENT_FACTOR = 1.1;
    private static final double MAX_SCALE = 1.0;
    private static final int ENDGAME_ALERT_TIME = 120; // Seconds

    protected enum DriveMode {
        TANK,
        DRIVE,
        MECANUM,
        FIELDCENTRIC
    }
    private enum SlideMode {
        MANUAL,
        AUTOMATIC
    }

    protected DriveMode driveMode = DriveMode.FIELDCENTRIC;
    private SlideMode currentSlideMode = SlideMode.MANUAL;
    private int slidePosition = 0;
    private final int[] autoSlidePositions = {0, 1000, 2000, 3000};
    private final SlewRateLimiter[] slewRateLimiters = new SlewRateLimiter[4];

    public void runOpMode() throws InterruptedException {
        AntiTipping antiTipping = new AntiTipping(driveMotors, imuSensor);
        initHardware();
        ElapsedTime timer = new ElapsedTime();
        waitForStart();

        initializeSlewRateLimiters();

        while (opModeIsActive()) {
            updateDriveModeFromGamepad();
            alertEndGame(timer);
            double precisionMultiplier = calculatePrecisionMultiplier();
            resetIMU();
            displayTelemetry(precisionMultiplier);

            DriveMode(precisionMultiplier);
            antiTipping.correctTilt();
            if (ButtonEX.Gamepad2EX.A.risingEdge()) {
                droneLauncher.launchDrone();
            }
            SlideControl();
            ButtonEX.Gamepad1EX.updateAll();
            ButtonEX.Gamepad2EX.updateAll();
        }
    }

    private void SlideControl() {
        // Slide control FSM logic
        switch (currentSlideMode) {
            case MANUAL:
                if (ButtonEX.Gamepad2EX.B.risingEdge()) {
                    currentSlideMode = SlideMode.AUTOMATIC;
                } else {
                    slides.setTargetPosition(slides.getTargetPosition() + (int) (gamepad2.left_stick_y * 2));
                }
                break;

            case AUTOMATIC:
                if (ButtonEX.Gamepad2EX.B.risingEdge()) {
                    currentSlideMode = SlideMode.MANUAL;
                } else {
                    if (ButtonEX.Gamepad2EX.DPAD_UP.fallingEdge()) {
                        slidePosition = (slidePosition + 1) % autoSlidePositions.length;
                        slides.setTargetPosition(autoSlidePositions[slidePosition]);
                    } else if (ButtonEX.Gamepad2EX.DPAD_DOWN.fallingEdge()) {
                        slidePosition = (slidePosition - 1 + autoSlidePositions.length) % autoSlidePositions.length;
                        slides.setTargetPosition(autoSlidePositions[slidePosition]);
                    }
                }
                break;
        }
    }
    
    private void initializeSlewRateLimiters() {
        for (int i = 0; i < slewRateLimiters.length; i++) {
            slewRateLimiters[i] = new SlewRateLimiter(RATE_LIMIT);
        }
    }

    private void updateDriveModeFromGamepad() {
        if (gamepad1.dpad_left) {
            driveMode = DriveMode.TANK;
        } else if (gamepad1.dpad_up) {
            driveMode = DriveMode.MECANUM;
        } else if (gamepad1.dpad_right) {
            driveMode = DriveMode.DRIVE;
        } else if (gamepad1.dpad_down) {
            driveMode = DriveMode.FIELDCENTRIC;
        }
    }

    private void alertEndGame(ElapsedTime timer) {
        if (timer.seconds() == ENDGAME_ALERT_TIME) {
            gamepad1.rumbleBlips(3);
            gamepad2.rumbleBlips(3);
        }
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
        if (gamepad1.y && gamepad1.back) {
            imuSensor.resetYaw();
            gamepad1.rumbleBlips(5);
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
            case TANK:
                motorSpeeds = TankDrive();
                break;
            case DRIVE:
                motorSpeeds = Drive();
                break;
            case MECANUM:
                motorSpeeds = MecanumDrive();
                break;
            case FIELDCENTRIC:
                motorSpeeds = FieldCentricDrive();
                break;
        }
        setMotorSpeeds(precisionMultiplier, motorSpeeds);
    }

    // Methods for each drive mode execution
    private double[] TankDrive() {
        double left = -applyDeadband(gamepad1.left_stick_y);
        double right = -applyDeadband(gamepad1.right_stick_y);
        return new double[]{right, right, left, left};
    }

    private double[] Drive() {
        double pivot = applyDeadband(gamepad1.left_stick_x);
        double forward = -applyDeadband(gamepad1.left_stick_y);
        return new double[]{
                forward - pivot,
                forward - pivot,
                forward + pivot,
                forward + pivot
        };
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
        if (slides.slidesExtendedState()) {
            applySlewRateLimit(powers);
        }

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


    protected double applyDeadband(double stickValue) {
        return Math.abs(stickValue) > DEADBAND_VALUE ? (stickValue - DEADBAND_VALUE) * Math.signum(stickValue) : 0;
    }
}
