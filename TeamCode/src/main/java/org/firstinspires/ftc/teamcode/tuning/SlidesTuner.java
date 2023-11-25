package org.firstinspires.ftc.teamcode.tuning;

import com.ThermalEquilibrium.homeostasis.Controllers.Feedback.BasicPID;
import com.ThermalEquilibrium.homeostasis.Parameters.PIDCoefficients;
import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Framework.BaseOpMode;
import org.firstinspires.ftc.teamcode.Framework.misc.MotionProfile;

@Config
@TeleOp(name = "P MP Test")
public class SlidesTuner extends BaseOpMode {
	private static double KP = 0; //Todo Tuning
	
	private static int HANG_UP_POSITION = 1000;
	private static int HANG_DOWN_POSITION = 0;
	
	private static double MAX_ACCELERATION = 1.0; //Todo Tuning
	private static double MAX_VELOCITY = 1.0;
	private static int target = 0;
	
	private DcMotor hangerMotor;
	private int targetPosition;
	private final PIDCoefficients coefficients = new PIDCoefficients(KP, 0, 0);
	private final BasicPID controller = new BasicPID(coefficients);
	private ElapsedTime timer = new ElapsedTime();
	
	public SlidesTuner(DcMotor motor) {
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
		
		double motorPower = controller.calculate(instantTargetPosition, hangerMotor.getCurrentPosition());
		moveHanger(motorPower);

//        double motorPower = calculatePidOutput(targetPosition, hangerMotor.getCurrentPosition());
//        moveHanger(motorPower);
	}
	
	private double calculatePidOutput(int targetPosition, int currentPosition) {
		return controller.calculate(targetPosition, currentPosition);
	}
	
	public void hangUp() {
		setTargetPosition(HANG_UP_POSITION);
	}
	
	public void hangDown() {
		setTargetPosition(HANG_DOWN_POSITION);
	}
	
	@Override
	public void runOpMode() throws InterruptedException {
		telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
		waitForStart();
		while (opModeIsActive()) {
			update();
			setTargetPosition(target);
		}
	}



}