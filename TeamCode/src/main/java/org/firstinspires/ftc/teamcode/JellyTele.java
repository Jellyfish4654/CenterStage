package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.Pose2d;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.Framework.BaseOpMode;
import org.firstinspires.ftc.teamcode.Framework.misc.PoseStorage;
import org.firstinspires.ftc.teamcode.Framework.misc.SlewRateLimiter;
import org.firstinspires.ftc.teamcode.RoadRunner.MecanumDrive;

@TeleOp(name = "CenterStage JellyTele")
public class JellyTele extends BaseOpMode
{
    private final double PRECISION_MULTIPLIER_LOW = 0.35;
    private final double PRECISION_MULTIPLIER_HIGH = 0.7;
    private double PRECISION_MULTIPLIER_INTAKE = 1;
    private final double DEADBAND_VALUE = 0.02;
    private final double STRAFE_ADJUSTMENT_FACTOR = 1.1;
    private final double MAX_SCALE = 1.0;
    private final double ENDGAME_ALERT_TIME = 110.0;
    private double lastSmoothedValue = 0;
    private double resetHeading = 0;
    private ElapsedTime joystickReleaseTimer = new ElapsedTime();
    private GamepadEx gamepadEx1;
    private GamepadEx gamepadEx2;

    protected enum DriveMode
    {
        MECANUM,
        FIELDCENTRIC
    }

    private enum Outtake
    {
        IDLE,
        DEPOSIT,
        INTAKE
    }

    protected DriveMode driveMode = DriveMode.FIELDCENTRIC;
    private static MecanumDrive drive;
    private Outtake currentState = Outtake.IDLE;
    private final SlewRateLimiter[] slewRateLimiters = new SlewRateLimiter[4];
    private Gamepad.RumbleEffect effect = new Gamepad.RumbleEffect.Builder()
            .addStep(1.0, 1.0, 900)
            .addStep(0.0, 0.0, 100)
            .addStep(1.0, 1.0, 900)
            .addStep(0.0, 0.0, 100)
            .addStep(1.0, 1.0, 900)
            .addStep(0.0, 0.0, 100)
            .addStep(1.0, 1.0, 900)
            .addStep(0.0, 0.0, 100)
            .addStep(1.0, 1.0, 900)
            .addStep(0.0, 0.0, 100)
            .addStep(1.0, 1.0, 900)
            .addStep(0.0, 0.0, 100)
            .addStep(1.0, 1.0, 900)
            .addStep(0.0, 0.0, 100)
            .build();

    @Override
    public void runOpMode() throws InterruptedException
    {
        initHardware();
        initializeSlewRateLimiters();
//        intakeSystem.servoIntakeInit();
        gamepadEx1 = new GamepadEx(gamepad1);
        gamepadEx2 = new GamepadEx(gamepad2);
        antiTipping.initImuError();
        intakeSystem.servoIntakeOut();
        drive = new MecanumDrive(hardwareMap, PoseStorage.currentPose);
        waitForStart();
        ElapsedTime timer = new ElapsedTime();
        intakeSystem.servoIntakeDrone();
        while (opModeIsActive())
        {
            readGamepadInputs();
            if (timer.milliseconds() % 500 < 100)
            {
                displayTelemetry(calculatePrecisionMultiplier());
            }
            if (timer.seconds() >= ENDGAME_ALERT_TIME)
            {
                alertEndGame(timer);
            }
            controlDroneAndOutake();
            controlSlideMotors();
            controlIntakeMotor();
            antiTipping.update();
            updateDriveMode(calculatePrecisionMultiplier());
            if (gamepad1.left_trigger > 0.5f)
            {
                autoAlignment.setTargetAngle(-90);
                autoAlignment.update();
            }
            else if (gamepad1.right_trigger > 0.5f)
            {
                autoAlignment.setTargetAngle(90);
                autoAlignment.update();
            }
            if (gamepadEx2.wasJustReleased(GamepadKeys.Button.START)){
                intakeSystem.setTargetPosition(intakeMotor.getCurrentPosition()+5000);
            }
        }
    }

    private void controlIntakeMotor()
    {
        double alpha = 0.5;
        double joystickValue = applyDeadband(-gamepad2.left_stick_y);
        double smoothedValue = alpha * joystickValue + (1 - alpha) * lastSmoothedValue;
        lastSmoothedValue = smoothedValue;

        if (Math.abs(smoothedValue) > DEADBAND_VALUE)
        {
            intakeMotor.setPower(smoothedValue * PRECISION_MULTIPLIER_INTAKE);
            intakeSystem.setTargetPosition(intakeMotor.getCurrentPosition());
            joystickReleaseTimer.reset();
        }
        else
        {
            if (joystickReleaseTimer.seconds() <= 0.5)
            {
                intakeSystem.setTargetPosition(intakeMotor.getCurrentPosition());
            }

            intakeSystem.update();
        }
        if (gamepadEx2.wasJustReleased(GamepadKeys.Button.LEFT_STICK_BUTTON))
        {
            intakeSystem.eject();
        }
        if (gamepadEx2.wasJustReleased(GamepadKeys.Button.RIGHT_STICK_BUTTON))
        {
            intakeSystem.moveForward();
        }
    }

    static final int SLIDES_HANGING_HEIGHT = 2000;
    static final int SLIDES_FIRST_PIXEL_HEIGHT = 1775;
    static final int RESET_SLIDES_HEIGHT = 0;
    private void controlSlideMotors()
    {
        if (applyDeadband(gamepad2.right_stick_y) != 0)
        {
            slideMotorLeft.setPower(-gamepad2.right_stick_y);
            slideMotorRight.setPower(-gamepad2.right_stick_y);
            int averageTarget = (slideMotorLeft.getCurrentPosition() + slideMotorRight.getCurrentPosition()) / 2;
            slides.setTargetPosition(averageTarget);
        }
        else
        {
            slides.update();
            if (gamepadEx2.wasJustReleased(GamepadKeys.Button.Y))
            {
                slides.setTargetPosition(SLIDES_HANGING_HEIGHT);
            }
            if (gamepadEx2.wasJustReleased(GamepadKeys.Button.B))
            {
                slides.setTargetPosition(SLIDES_FIRST_PIXEL_HEIGHT);
            }
            if (gamepadEx2.wasJustReleased(GamepadKeys.Button.A))
            {
                slides.setTargetPosition(RESET_SLIDES_HEIGHT);
            }
            if (gamepadEx2.wasJustReleased(GamepadKeys.Button.DPAD_UP))
            {
                int averageTarget = (slideMotorLeft.getCurrentPosition() + slideMotorRight.getCurrentPosition()) / 2;
                slides.setTargetPosition(averageTarget + 300);
            }

            if (gamepadEx2.wasJustReleased(GamepadKeys.Button.DPAD_DOWN))
            {
                int averageTarget = (slideMotorLeft.getCurrentPosition() + slideMotorRight.getCurrentPosition()) / 2;
                slides.setTargetPosition(averageTarget - 300);
            }
        }
    }

    private void OutakeControl()
    {
        outakeServos.setOutput();
        if (gamepadEx2.wasJustReleased(GamepadKeys.Button.LEFT_BUMPER))
        {
            outakeServos.closeOuttake();
        }
        if (gamepadEx2.wasJustReleased(GamepadKeys.Button.RIGHT_BUMPER))
        {
            outakeServos.openOuttake();
        }
        switch (currentState)
        {
            case IDLE:
                if (gamepad2.left_stick_y < 0)
                {
                    currentState = Outtake.INTAKE;
                }
                else if (gamepad2.x)
                {
                    currentState = Outtake.DEPOSIT;
                }
                outtakeCRServo.setPower(0);
                break;
            case INTAKE:
                if (gamepad2.x)
                {
                    currentState = Outtake.DEPOSIT;
                }
                else if (!(gamepad2.left_stick_y < 0))
                {
                    currentState = Outtake.IDLE;
                }
                outtakeCRServo.setPower(gamepad2.left_stick_y);
                break;
            case DEPOSIT:
                if (gamepad2.left_stick_y < 0)
                {
                    currentState = Outtake.INTAKE;
                }
                else if (!gamepad2.x)
                {
                    currentState = Outtake.IDLE;
                }
                outtakeCRServo.setPower(0.75);
                break;
        }
    }

    private void displayTelemetry(double precisionMultiplier)
    {
        telemetry.addData("drive mode", driveMode);
        telemetry.addData("mX", gamepad2.left_stick_x);
        telemetry.addData("mY", gamepad2.left_stick_y);
        telemetry.addData("precision mode", precisionMultiplier);
        telemetry.addData("LeftSlide", slideMotorLeft.getCurrentPosition());
        telemetry.addData("RightSlide", slideMotorRight.getCurrentPosition());
        telemetry.addData("LeftSlideOutput", slides.getLeftPIDOutput());
        telemetry.addData("RightSlideOutput", slides.getRightPIDOutput());
        telemetry.addData("LeftSlideTarget", slides.getTargetPositionLeft());
        telemetry.addData("RightSlideTarget", slides.getTargetPositionRight());
        telemetry.addData("intakeCurrentPosition", intakeMotor.getCurrentPosition());
        telemetry.addData("intakeTargetPosition", intakeSystem.getTargetPosition());
        telemetry.addData("intake precision", PRECISION_MULTIPLIER_INTAKE);
        telemetry.addData("imuyaw", imuSensor.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES));
        telemetry.addData("DWyaw", Math.toDegrees(drive.pose.heading.toDouble()));
        telemetry.addData("imupitch", imuSensor.getRobotYawPitchRollAngles().getPitch(AngleUnit.DEGREES));
        telemetry.addData("imuroll", imuSensor.getRobotYawPitchRollAngles().getRoll(AngleUnit.DEGREES));
        telemetry.addData("intakeOutput", intakeSystem.getPIDOutput());
        telemetry.update();
    }

    private void readGamepadInputs()
    {
        gamepadEx1.readButtons();
        gamepadEx2.readButtons();
        updateDriveModeFromGamepad();
    }

    private void controlDroneAndOutake()
    {
        DroneControl();
        OutakeControl();
    }

    private void updateDriveMode(double precisionMultiplier)
    {
        double[] motorSpeeds;
        switch (driveMode)
        {
            case MECANUM:
                motorSpeeds = MecanumDrive();
                break;
            case FIELDCENTRIC:
                motorSpeeds = FieldCentricDrive();
                break;
            default:
                motorSpeeds = new double[4];
        }
        setMotorSpeeds(precisionMultiplier, motorSpeeds);
    }

    private double[] MecanumDrive()
    {
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

    private double[] FieldCentricDrive()
    {
        double forward = -applyDeadband(gamepad1.left_stick_y);
        double strafe = applyDeadband(gamepad1.left_stick_x) * STRAFE_ADJUSTMENT_FACTOR;
        double rotation = applyDeadband(gamepad1.right_stick_x);
        double botHeading = imuSensor.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS) - resetHeading;

        double rotX = strafe * Math.cos(-botHeading) - forward * Math.sin(-botHeading);
        double rotY = strafe * Math.sin(-botHeading) + forward * Math.cos(-botHeading);
        return new double[]{
                rotY - rotX - rotation,
                rotY + rotX - rotation,
                rotY + rotX + rotation,
                rotY - rotX + rotation
        };
    }

    protected void setMotorSpeeds(double multiplier, double[] powers)
    {
        applyPrecisionAndScale(multiplier, powers);
        int averageTargetPosition = (slides.getTargetPositionLeft() + slides.getTargetPositionRight()) / 2;
        double rate = 1.0;
        if (averageTargetPosition >= 2000)
        {
            rate = 0.99 - ((double) (averageTargetPosition - 2000) / 10) * 0.0005;
            rate = Math.max(rate, 0);
            applySlewRateLimit(powers, rate);
        }
        for (int i = 0; i < driveMotors.length; i++)
        {
            driveMotors[i].setPower(powers[i]);
        }
    }

    private void applySlewRateLimit(double[] powers, double rate)
    {
        for (int i = 0; i < slewRateLimiters.length; i++)
        {
            slewRateLimiters[i].setRate(rate);
            powers[i] = slewRateLimiters[i].calculate(powers[i]);
        }
    }

    private void applyPrecisionAndScale(double multiplier, double[] powers)
    {
        for (int i = 0; i < powers.length; i++)
        {
            powers[i] *= multiplier;
        }

        double maxPower = findMaxPower(powers);
        double scale = maxPower > MAX_SCALE ? MAX_SCALE / maxPower : 1.0;

        for (int i = 0; i < powers.length; i++)
        {
            powers[i] *= scale;
        }
    }

    private double findMaxPower(double[] powers)
    {
        double max = 0;
        for (double power : powers)
        {
            max = Math.max(max, Math.abs(power));
        }
        return max;
    }

    private double applyDeadband(double joystickValue)
    {
        double sign = Math.signum(joystickValue);
        return joystickValue + (-sign * DEADBAND_VALUE);
    }

    private void updateDriveModeFromGamepad()
    {
        if (gamepadEx1.wasJustReleased(GamepadKeys.Button.Y))
        {
            driveMode = DriveMode.FIELDCENTRIC;
            resetIMU();
        }
        else if (gamepadEx1.wasJustReleased(GamepadKeys.Button.A))
        {
            driveMode = DriveMode.MECANUM;
        }
        resetIMU();
    }

    public void DroneControl()
    {
        if (gamepadEx2.wasJustReleased(GamepadKeys.Button.BACK))
        {
            droneServo.launchDrone();
        }
    }

    //tarir was here
    private void alertEndGame(ElapsedTime timer)
    {
        if (timer.seconds() >= ENDGAME_ALERT_TIME && timer.seconds() <= ENDGAME_ALERT_TIME + 0.2)
        {
            gamepad1.runRumbleEffect(effect);
            gamepad2.runRumbleEffect(effect);
        }
    }

    private void initializeSlewRateLimiters()
    {
        for (int i = 0; i < slewRateLimiters.length; i++)
        {
            slewRateLimiters[i] = new SlewRateLimiter(1.0);
        }
    }

    private double calculatePrecisionMultiplier()
    {
        if (gamepad1.left_bumper)
        {
            return PRECISION_MULTIPLIER_LOW;
        }
        else if (gamepad1.right_bumper)
        {
            return PRECISION_MULTIPLIER_HIGH;
        }
        if (gamepad2.right_trigger > 0.5)
        {
            PRECISION_MULTIPLIER_INTAKE = 0.75;
        }
        else
        {
            PRECISION_MULTIPLIER_INTAKE = 1;
        }
        return MAX_SCALE;
    }

    private void resetIMU()
    {
        if (gamepadEx1.wasJustReleased(GamepadKeys.Button.DPAD_UP))
        {
            imuSensor.resetYaw();
            resetHeading = 0;
            drive = new MecanumDrive(hardwareMap, new Pose2d(0, 0, resetHeading));
            gamepad1.rumbleBlips(3);
        }
        else if (gamepadEx1.wasJustReleased(GamepadKeys.Button.DPAD_LEFT))
        {
            imuSensor.resetYaw();
            resetHeading = -90;
            drive = new MecanumDrive(hardwareMap, new Pose2d(0, 0, resetHeading));
            gamepad1.rumbleBlips(3);
        }
        else if (gamepadEx1.wasJustReleased(GamepadKeys.Button.DPAD_RIGHT))
        {
            imuSensor.resetYaw();
            resetHeading = 90;

            drive = new MecanumDrive(hardwareMap, new Pose2d(0, 0, resetHeading));
            gamepad1.rumbleBlips(3);
        }
        else if (gamepadEx1.wasJustReleased(GamepadKeys.Button.DPAD_DOWN))
        {
            imuSensor.resetYaw();
            resetHeading = -180;

            drive = new MecanumDrive(hardwareMap, new Pose2d(0, 0, resetHeading));
            gamepad1.rumbleBlips(3);
        }
    }

    public static double getHeading()
    {
        return Math.toDegrees(drive.pose.heading.toDouble());
    }
}