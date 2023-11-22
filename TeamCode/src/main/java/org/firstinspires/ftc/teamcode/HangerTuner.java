package org.firstinspires.ftc.teamcode;

import com.ThermalEquilibrium.homeostasis.Controllers.Feedback.BasicPID;
import com.ThermalEquilibrium.homeostasis.Parameters.PIDCoefficients;
import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Framework.BaseOpMode;
import org.firstinspires.ftc.teamcode.Framework.misc.MotionProfile;

@Config
@TeleOp(name = "Hanger Tuner")
public class HangerTuner extends BaseOpMode {
    private DcMotor hangerMotor;
    private static double KP = 0; //Todo Tuning
    private static int HANG_UP_POSITION = 1000;
    private static int HANG_DOWN_POSITION = 0;

    private static double MAX_ACCELERATION = 1.0; //Todo Tuning
    private static double MAX_VELOCITY = 1.0;
    private static int target = 0;
    private int targetPosition;
    private final PIDCoefficients coefficients = new PIDCoefficients(KP, 0, 0);
    private final BasicPID controller = new BasicPID(coefficients);
    private ElapsedTime timer = new ElapsedTime();

    // FTC Dashboard instance
    private FtcDashboard dashboard = FtcDashboard.getInstance();

    public HangerTuner(DcMotor motor) {
        this.hangerMotor = motor;
    }

    public void setTargetPosition(int target) {
        targetPosition = target;
        timer.reset();
    }

    public int getTargetPosition() {
        return this.targetPosition;
    }

    public void moveHanger(double power) {
        hangerMotor.setPower(power);
    }

    public void update() {
        double elapsedTime = timer.seconds();
        double distance = targetPosition - hangerMotor.getCurrentPosition();
        double instantTargetPosition = MotionProfile.motion_profile(MAX_ACCELERATION, MAX_VELOCITY, distance, elapsedTime);

        // W/out MP
        double motorPower = controller.calculate(distance, 0);
        // W/ MP
        // double motorPower = controller.calculate(instantTargetPosition, hangerMotor.getCurrentPosition());
        moveHanger(motorPower);

        // Send data to FTC Dashboard
        telemetry.addData("Target Position", targetPosition);
        telemetry.addData("Current Position", hangerMotor.getCurrentPosition());
        telemetry.update();
    }

    public void hangUp() {
        setTargetPosition(HANG_UP_POSITION);
    }

    public void hangDown() {
        setTargetPosition(HANG_DOWN_POSITION);
    }

    @Override
    public void runOpMode() throws InterruptedException {
        initHardware();
        waitForStart();
        ElapsedTime timer = new ElapsedTime();

        while (opModeIsActive()) {
            update();
            setTargetPosition(target);
        }
    }
}
