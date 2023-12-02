package org.firstinspires.ftc.teamcode;

import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.Framework.BaseOpMode;
import org.firstinspires.ftc.teamcode.Framework.misc.SlewRateLimiter;
import org.firstinspires.ftc.teamcode.Framework.Intake;

@TeleOp(name = "CenterStage JellyTele")
public class JellyTele extends BaseOpMode {
    private static final double PRECISION_MULTIPLIER_LOW = 0.35;
    private static final double PRECISION_MULTIPLIER_HIGH = 0.7;
    private static final double RATE_LIMIT = 0.5;
    private static final double DEADBAND_VALUE = 0.02;
    private static final double STRAFE_ADJUSTMENT_FACTOR = 1.1;
    private static final double MAX_SCALE = 1.0;
    private static final int ENDGAME_ALERT_TIME = 120; // Seconds
    private GamepadEx gamepadEx1;
    private GamepadEx gamepadEx2;
    protected enum DriveMode {
        TANK,
        DRIVE,
        MECANUM,
        FIELDCENTRIC
    }
    public enum OutakeState {
        SLIDES_RETRACT,
        OUTAKE_OPEN,
        OUTAKE_CLOSE
    };
    ElapsedTime outakeTimer = new ElapsedTime();
    OutakeState outakeState = OutakeState.OUTAKE_OPEN;

    protected DriveMode driveMode = DriveMode.FIELDCENTRIC;
    private int slidePosition = 0;
    private final int[] autoSlidePositions = {0, 1000, 2000, 3000};
    private final SlewRateLimiter[] slewRateLimiters = new SlewRateLimiter[4];
    private boolean Hanging = false;
    private boolean sliding = true;
    @Override
    public void runOpMode() throws InterruptedException {
        initHardware();
        initializeSlewRateLimiters();
        gamepadEx1 = new GamepadEx(gamepad1);
        gamepadEx2 = new GamepadEx(gamepad2);
        waitForStart();
        ElapsedTime timer = new ElapsedTime();
        while (opModeIsActive()) {
            gamepadEx1.readButtons();
            gamepadEx2.readButtons();
            updateDriveModeFromGamepad();
            alertEndGame(timer);
            double precisionMultiplier = calculatePrecisionMultiplier();
            resetIMU();
            displayTelemetry(precisionMultiplier);
            DriveMode(precisionMultiplier);
            IntakeControl();
//            OutakeControl();
//            SlideControl();
//            HangerControl();
            DroneControl();
//            autoAlignment();
//            antiTipping();
            outakeServos.setOutput();
            slides.update();
            intakeSystem.update();

        }
    }

//    private void autoAlignment(){
//        if(gamepad1.a){
//            autoAlignment.update();
//        }
//    }
//    private void antiTipping(){
//        if(!Hanging){
//            antiTipping.correctTilt();
//        }
//    }
    private void DroneControl() {
        if (gamepadEx1.wasJustReleased(GamepadKeys.Button.BACK)){
            droneLauncher.launchDrone();
        }
    }

//    private void HangerControl() {
//        if(gamepadEx2.wasJustReleased(GamepadKeys.Button.Y)){
//            hanger.hangUp();
//            Hanging=true;
//        }
//        if(gamepadEx2.wasJustReleased(GamepadKeys.Button.A)){
//            hanger.hangDown();
//        }
//
//    }
    private void OutakeControl(){
        switch(outakeState){
            case OUTAKE_OPEN:
                if(gamepadEx2.wasJustReleased(GamepadKeys.Button.B)&&gamepadEx2.wasJustReleased(GamepadKeys.Button.BACK)){
                    outakeServos.openOutake();
                    outakeTimer.reset();
                    outakeState = OutakeState.OUTAKE_CLOSE;
                }
                break;
            case OUTAKE_CLOSE:
                if(outakeTimer.seconds() >= 0.25){
                    outakeServos.closeOutake();
                    outakeState = OutakeState.OUTAKE_OPEN;
                }
                outakeTimer.reset();
                outakeState = OutakeState.SLIDES_RETRACT;
                break;
            case SLIDES_RETRACT:
                if(outakeTimer.seconds() >= 0.25){
                    slideRetract();
                }
        }
        if (gamepadEx2.wasJustReleased(GamepadKeys.Button.LEFT_BUMPER)){
            outakeServos.closeOutake();
        }
        if (gamepadEx2.wasJustReleased(GamepadKeys.Button.RIGHT_BUMPER)){
            outakeServos.openOutake();
        }
    }
    private void SlideControl() {
        if (Math.abs(gamepad2.left_stick_y) > DEADBAND_VALUE) {
//            slides.setManualControl(true);
            int manualTarget = intakeSystem.getTargetPosition() + (int) (applyDeadband(gamepad2.left_stick_y * 100));
            intakeSystem.setTargetPosition(manualTarget);
        }
//         else {
//            slides.setManualControl(false);
//        }
//
        if (gamepadEx2.wasJustReleased(GamepadKeys.Button.DPAD_UP)) {
            slidePosition = (slidePosition + 1) % autoSlidePositions.length;
            slides.setTargetPosition(autoSlidePositions[slidePosition]);
        } else if (gamepadEx2.wasJustReleased(GamepadKeys.Button.DPAD_DOWN)) {
            slidePosition = (slidePosition - 1 + autoSlidePositions.length) % autoSlidePositions.length;
            slides.setTargetPosition(autoSlidePositions[slidePosition]);
        }
    }

    private void IntakeControl() {
        if (Math.abs(gamepad2.right_stick_y) > DEADBAND_VALUE) {
            int manualTarget = intakeSystem.getTargetPosition() + (int) (applyDeadband(gamepad2.right_stick_y * 100));
            intakeSystem.setTargetPosition(manualTarget);
        }
//        if (Math.abs(gamepad2.right_stick_y) > DEADBAND_VALUE) {
//            intakeSystem.setManualControl(true);
//            int manualTarget = Intake.getTargetPosition() + (int) (applyDeadband(gamepad2.right_stick_y * 100));
//            Intake.setTargetPosition(manualTarget);
//        } else {
//            intakeSystem.setManualControl(false);
//        }
//
//        if (gamepad2.a) {
//            Intake.runPosition();
//        }
    }

    private void slideRetract(){
        slidePosition=0;
        slides.setTargetPosition(autoSlidePositions[slidePosition]);
    }
    
    private void initializeSlewRateLimiters() {
        for (int i = 0; i < slewRateLimiters.length; i++) {
            slewRateLimiters[i] = new SlewRateLimiter(RATE_LIMIT);
        }
    }

    private void updateDriveModeFromGamepad() {
        if (gamepadEx1.wasJustReleased(GamepadKeys.Button.DPAD_LEFT)) {
            driveMode = DriveMode.TANK;
        } else if (gamepadEx1.wasJustReleased(GamepadKeys.Button.DPAD_UP)) {
            driveMode = DriveMode.MECANUM;
        } else if (gamepadEx1.wasJustReleased(GamepadKeys.Button.DPAD_RIGHT)) {
            driveMode = DriveMode.DRIVE;
        } else if (gamepadEx1.wasJustReleased(GamepadKeys.Button.DPAD_DOWN)) {
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
        if (timer.seconds() == ENDGAME_ALERT_TIME) {
            gamepad1.runRumbleEffect(effect);
            gamepad2.runRumbleEffect(effect);
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
        if (gamepadEx1.wasJustReleased(GamepadKeys.Button.BACK)) {
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

//        double Y_BACKDROP_DISTANCE = aprilTagPipeline.getTagY();
//        double X_BACKDROP_DISTANCE = aprilTagPipeline.getTagX();
//        double xBackdropLeft = 1.0;
//        double xBackdropRight = 10.0;
//        double backdropDistance = 10.0;
//        if(X_BACKDROP_DISTANCE>=xBackdropLeft && X_BACKDROP_DISTANCE<=xBackdropRight){
//            if (Y_BACKDROP_DISTANCE <= backdropDistance && forward > 0) {
//                // If the robot is within x inches of the wall and trying to move forward, stop forward movement
//                forward = 0;
//            }
//        }

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

    public double applyDeadband(double joystickValue) {
        double sign = Math.signum(joystickValue);
        return joystickValue + (-sign * DEADBAND_VALUE);
    }
}
