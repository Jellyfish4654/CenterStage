package org.firstinspires.ftc.teamcode;

//<<<<<<< HEAD
//import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.gamepad1;
//
//=======
//import com.ThermalEquilibrium.homeostasis.Filters.FilterAlgorithms.LowPassFilter;
//>>>>>>> 43446bc3aacd0af85d123e4bd7bf8feaf8b3557a
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.Framework.AntiTipping;
import org.firstinspires.ftc.teamcode.Framework.BaseOpMode;


@TeleOp(name = "CenterStage JellyTele")
public class JellyTele extends BaseOpMode {
    protected enum DriveMode {
        TANK,
        DRIVE,
        MECANUM,
        FIELDCENTRIC
    }
    protected DriveMode driveMode = DriveMode.FIELDCENTRIC;

    public void runOpMode() throws InterruptedException {
        AntiTipping antiTipping = new AntiTipping(motors, imu);
        // Init hardware from BaseOpMode
        initHardware();
        waitForStart();
        ElapsedTime timer = new ElapsedTime();
        while (opModeIsActive()) {
            // DRIVETRAIN
            if (gamepad1.dpad_left) {
                driveMode = DriveMode.TANK;
            } else if (gamepad1.dpad_up) {
                driveMode = DriveMode.MECANUM;
            } else if (gamepad1.dpad_right) {
                driveMode = DriveMode.DRIVE;
            } else if (gamepad1.dpad_down) {
                driveMode = DriveMode.FIELDCENTRIC;
            }
            //PRE-ENDGAME ALERT
            if(timer.seconds()==120){
                gamepad1.rumbleBlips(3);
                gamepad2.rumbleBlips(3);
            }
            // PRECISION
            double mult = gamepad1.left_bumper ? 0.35 : gamepad1.right_bumper ? 0.7 : 1.0;
            // IMU RESET
            if (gamepad1.y && gamepad1.back) {
                imu.resetYaw();
                gamepad1.rumbleBlips(5);
            }
            // TELEMETRY
            telemetry.addData("drive mode", driveMode);
            telemetry.addData("mX", gamepad2.left_stick_x);
            telemetry.addData("mY", gamepad2.left_stick_y);
            telemetry.addData("precision mode", mult);
            telemetry.update();

            // DRIVE MODE
            switch (driveMode) {
                case TANK: {
                    double l = -gamepad1.left_stick_y,
                            r = -gamepad1.right_stick_y;
                    setMotorSpeeds(mult, new double[]{r, r, l, l});
                    DEADBAND(0.02, l);
                    DEADBAND(0.02, r);
                    break;
                }
                case DRIVE: {
                    double pivot = gamepad1.left_stick_x, y = -gamepad1.left_stick_y;
                    setMotorSpeeds(mult, new double[]{
                            y - pivot,
                            y - pivot,
                            y + pivot,
                            y + pivot
                    });
                    DEADBAND(0.02, l);
                    DEADBAND(0.02, r);
                    break;
                }
                case MECANUM: {
                    double pivot = DEADBAND(0.02,gamepad1.right_stick_x);
                    double mX, mY;
                    mX = DEADBAND(0.02,gamepad1.left_stick_x) * 1.1; // Counteract imperfect strafing
                    mY = -DEADBAND(0.02,gamepad1.left_stick_y); // Remember, this is reversed!
                    setMotorSpeeds(mult, new double[]{
                            mY - mX - pivot,
                            mY + mX - pivot,
                            mY + mX + pivot,
                            mY - mX + pivot});

                    break;
                }
                case FIELDCENTRIC: {
                    double y = -gamepad1.left_stick_y; // Remember, this is reversed!
                    double x = gamepad1.left_stick_x * 1.1; // Counteract imperfect strafing
                    double rx = gamepad1.right_stick_x;
                    DEADBAND(0.02, y);
                    DEADBAND(0.02, x);
                    double botHeading = imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS);

                    // Rotate the movement direction counter to the bot's rotation
                    double rotX = x * Math.cos(-botHeading) - y * Math.sin(-botHeading);
                    double rotY = x * Math.sin(-botHeading) + y * Math.cos(-botHeading);
                    setMotorSpeeds(mult, new double[]{
                            rotY - rotX - rx,
                            rotY + rotX - rx,
                            rotY + rotX + rx,
                            rotY - rotX + rx});

                    break;
                }
            }
            // Update Anti-Tipping
            antiTipping.correctTilt();
            
        }
    }

    // PRECISION METHOD
    protected void setMotorSpeeds(double mult, double[] powers) {
        for (int i = 0; i < 4; i++) {
            powers[i] = powers[i] * mult;
        }
        double max = Math.max(Math.max(Math.abs(powers[0]), Math.abs(powers[1])),
                Math.max(Math.abs(powers[2]), Math.abs(powers[3])));
        double scale = Math.abs(1 / max);
        // SCALES POWER TO KEEP IN RANGE LIMIT
        if (scale > 1) {
            scale = 1;
        }
        for (int i = 0; i < 4; i++) {
            powers[i] *= scale;
        }
        for (int i = 0; i < 4; i++) {
            motors[i].setPower(powers[i]);
        }
    }
    protected double DEADBAND(double DEADBAND, double stickVal) {
        double DB = stickVal >= -DEADBAND && stickVal <= DEADBAND ? 0 : (stickVal - DEADBAND) * Math.signum(stickVal);
        return DB;
    }
//<<<<<<< HEAD
//=======
    //Rising Edge Detector
    public boolean risingEdgeDetect(boolean current, boolean previous) {
        return current && !previous;
    }

//>>>>>>> 43446bc3aacd0af85d123e4bd7bf8feaf8b3557a
}
