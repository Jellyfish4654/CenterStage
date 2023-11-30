package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.Framework.BaseOpMode;
import org.firstinspires.ftc.teamcode.Framework.misc.AntiTipping;
import org.firstinspires.ftc.teamcode.Framework.misc.AprilTagPipeline;
import org.firstinspires.ftc.teamcode.Framework.misc.AutoAlignment;
import org.firstinspires.ftc.teamcode.Framework.misc.ButtonEX;
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


    protected enum DriveMode {
        TANK,
        DRIVE,
        MECANUM,
        FIELDCENTRIC
    }
    public enum OutakeState {
        OUTAKE_OPEN,
        OUTAKE_CLOSE
    };
    ElapsedTime outakeTimer = new ElapsedTime();
    OutakeState outakeState = OutakeState.OUTAKE_CLOSE;

    protected DriveMode driveMode = DriveMode.FIELDCENTRIC;
    private int slidePosition = 0;
    private final int[] autoSlidePositions = {0, 1000, 2000, 3000};
    private final SlewRateLimiter[] slewRateLimiters = new SlewRateLimiter[4];
    private AutoAlignment autoAlignment;
    private AntiTipping antiTipping;
    private boolean Hanging = false;
    private boolean intaking = true;
    private boolean sliding = true;

    public void runOpMode() throws InterruptedException {
        initHardware();
        initializeSlewRateLimiters();
        autoAlignment = new AutoAlignment(driveMotors, imuSensor);
        antiTipping = new AntiTipping(driveMotors, imuSensor);
        waitForStart();
        ElapsedTime timer = new ElapsedTime();

        while (opModeIsActive()) {
            updateDriveModeFromGamepad();
            alertEndGame(timer);
            double precisionMultiplier = calculatePrecisionMultiplier();
            resetIMU();
            displayTelemetry(precisionMultiplier);
            DriveMode(precisionMultiplier);
            IntakeControl();
            OutakeControl();
            SlideControl();
            HangerControl();
            DroneControl();
            autoAligment();
            antiTipping();
            slides.update();
            ButtonEX.Gamepad1EX.updateAll();
            ButtonEX.Gamepad2EX.updateAll();
            if (gamepad1.a){
                Intake.runPosition();
            }

        }
    }

    private void autoAligment(){
        if(gamepad1.a){
            autoAlignment.update();
        }
    }
    private void antiTipping(){
        if(Hanging==false){
            antiTipping.correctTilt();
        }
    }
    private void DroneControl() {
        if (ButtonEX.Gamepad2EX.GUIDE.fallingEdge()) {
            droneLauncher.launchDrone();
        }
    }

    private void HangerControl() {
        if(ButtonEX.Gamepad2EX.Y.fallingEdge()){
            hanger.hangUp();
            Hanging=true;
        }
        if(ButtonEX.Gamepad2EX.A.fallingEdge()){
            hanger.hangDown();
        }

    }
    private void OutakeControl(){
        switch(outakeState){
            case OUTAKE_CLOSE:
                if(ButtonEX.Gamepad2EX.B.fallingEdge()){
                    outakeServos.openOutake();
                    outakeTimer.reset();
                    outakeState = OutakeState.OUTAKE_OPEN;
                }
                break;
            case OUTAKE_OPEN:
                if(outakeTimer.seconds() >= 0.25){
                    outakeServos.closeOutake();
                    outakeState = OutakeState.OUTAKE_CLOSE;
                    slideRetract();
                }
                break;
        }
    }
    private void SlideControl() {
        if (gamepad2.left_stick_y > 0.24 || gamepad2.left_stick_y <= 0.25) {
            sliding = true;
        } else {
            sliding = false;
        }

        if (sliding == true) {
            slides.setTargetPosition(slides.getTargetPosition() + (int) (applyDeadband(gamepad2.left_stick_y) * 0.5));
        } else  {
            slides.setManualTargetPosition(slides.getTargetPosition() + (int) (applyDeadband(gamepad2.left_stick_y) * 0.5));
        }

        if (ButtonEX.Gamepad2EX.DPAD_UP.fallingEdge()) {
            slidePosition = (slidePosition + 1) % autoSlidePositions.length;
            slides.setTargetPosition(autoSlidePositions[slidePosition]);
        } else if (ButtonEX.Gamepad2EX.DPAD_DOWN.fallingEdge()) {
            slidePosition = (slidePosition - 1 + autoSlidePositions.length) % autoSlidePositions.length;
            slides.setTargetPosition(autoSlidePositions[slidePosition]);
        }
    }

    public void IntakeControl() {
        if (gamepad2.right_stick_y > 0.24 || gamepad2.right_stick_y <= 0.25) {
            intaking = true;
        } else {
            intaking = false;
        }
        if (intaking) {
            Intake.setTargetPosition(Intake.getTargetPosition() + (int) (applyDeadband(gamepad2.right_stick_y) * 0.5));
        } else {
            Intake.setManualTargetPosition(Intake.getTargetPosition() + (int) (applyDeadband(gamepad2.right_stick_y) * 0.5));
        }
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
        if (ButtonEX.Gamepad1EX.DPAD_LEFT.fallingEdge()) {
            driveMode = DriveMode.TANK;
        } else if (ButtonEX.Gamepad1EX.DPAD_UP.fallingEdge()) {
            driveMode = DriveMode.MECANUM;
        } else if (ButtonEX.Gamepad1EX.DPAD_RIGHT.fallingEdge()) {
            driveMode = DriveMode.DRIVE;
        } else if (ButtonEX.Gamepad1EX.DPAD_DOWN.fallingEdge()) {
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
        if (ButtonEX.Gamepad1EX.BACK.fallingEdge()) {
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

        double Y_BACKDROP_DISTANCE = aprilTagPipeline.getTagY();
        double X_BACKDROP_DISTANCE = aprilTagPipeline.getTagX();
        double xBackdropLeft = 1.0;
        double xBackdropRight = 10.0;
        double backdropDistance = 10.0;
        if(X_BACKDROP_DISTANCE>=xBackdropLeft && X_BACKDROP_DISTANCE<=xBackdropRight){
            if (Y_BACKDROP_DISTANCE <= backdropDistance && forward > 0) {
                // If the robot is within x inches of the wall and trying to move forward, stop forward movement
                forward = 0;
            }
        }

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
