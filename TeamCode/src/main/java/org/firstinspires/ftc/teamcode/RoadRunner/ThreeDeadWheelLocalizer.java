package org.firstinspires.ftc.teamcode.RoadRunner;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.DualNum;
import com.acmerobotics.roadrunner.Time;
import com.acmerobotics.roadrunner.Twist2dDual;
import com.acmerobotics.roadrunner.Vector2dDual;
import com.acmerobotics.roadrunner.ftc.Encoder;
import com.acmerobotics.roadrunner.ftc.FlightRecorder;
import com.acmerobotics.roadrunner.ftc.OverflowEncoder;
import com.acmerobotics.roadrunner.ftc.PositionVelocityPair;
import com.acmerobotics.roadrunner.ftc.RawEncoder;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

@Config
public final class ThreeDeadWheelLocalizer implements Localizer
{
	public static class Params
	{
		public double par0YTicks = -1307.1156497501247; // y position of the first parallel encoder (in tick units)
		public double par1YTicks = 1344.0467238904039; // y position of the second parallel encoder (in tick units)
		public double perpXTicks = 979.3007250312698; // x position of the perpendicular encoder (in tick units)
	}

	public static Params PARAMS = new Params();

	public final Encoder par0, par1, perp;

	public final double inPerTick;

	private int lastPar0Pos, lastPar1Pos, lastPerpPos;

	public ThreeDeadWheelLocalizer(HardwareMap hardwareMap, double inPerTick)
	{
		// TODO: make sure your config has **motors** with these names (or change them)
		//   the encoders should be plugged into the slot matching the named motor
		//   see https://ftc-docs.firstinspires.org/en/latest/hardware_and_software_configuration/configuring/index.html
		par0 = new OverflowEncoder(new RawEncoder(hardwareMap.get(DcMotorEx.class, "motorBR"))); //2
		par1 = new OverflowEncoder(new RawEncoder(hardwareMap.get(DcMotorEx.class, "motorFR"))); //0
		perp = new OverflowEncoder(new RawEncoder(hardwareMap.get(DcMotorEx.class, "motorFL"))); //1

		// TODO: reverse encoder directions if needed
//		par0.setDirection(DcMotorSimple.Direction.REVERSE);
		par1.setDirection(DcMotorSimple.Direction.REVERSE);
		perp.setDirection(DcMotorSimple.Direction.REVERSE);

		lastPar0Pos = par0.getPositionAndVelocity().position;
		lastPar1Pos = par1.getPositionAndVelocity().position;
		lastPerpPos = perp.getPositionAndVelocity().position;

		this.inPerTick = inPerTick;

		FlightRecorder.write("THREE_DEAD_WHEEL_PARAMS", PARAMS);
	}

	public Twist2dDual<Time> update()
	{
		PositionVelocityPair par0PosVel = par0.getPositionAndVelocity();
		PositionVelocityPair par1PosVel = par1.getPositionAndVelocity();
		PositionVelocityPair perpPosVel = perp.getPositionAndVelocity();

		int par0PosDelta = par0PosVel.position - lastPar0Pos;
		int par1PosDelta = par1PosVel.position - lastPar1Pos;
		int perpPosDelta = perpPosVel.position - lastPerpPos;

		Twist2dDual<Time> twist = new Twist2dDual<>(
				new Vector2dDual<>(
						new DualNum<Time>(new double[]{
								(PARAMS.par0YTicks * par1PosDelta - PARAMS.par1YTicks * par0PosDelta) / (PARAMS.par0YTicks - PARAMS.par1YTicks),
								(PARAMS.par0YTicks * par1PosVel.velocity - PARAMS.par1YTicks * par0PosVel.velocity) / (PARAMS.par0YTicks - PARAMS.par1YTicks),
								}).times(inPerTick),
						new DualNum<Time>(new double[]{
								(PARAMS.perpXTicks / (PARAMS.par0YTicks - PARAMS.par1YTicks) * (par1PosDelta - par0PosDelta) + perpPosDelta),
								(PARAMS.perpXTicks / (PARAMS.par0YTicks - PARAMS.par1YTicks) * (par1PosVel.velocity - par0PosVel.velocity) + perpPosVel.velocity),
								}).times(inPerTick)
				),
				new DualNum<>(new double[]{
						(par0PosDelta - par1PosDelta) / (PARAMS.par0YTicks - PARAMS.par1YTicks),
						(par0PosVel.velocity - par1PosVel.velocity) / (PARAMS.par0YTicks - PARAMS.par1YTicks),
						})
		);

		lastPar0Pos = par0PosVel.position;
		lastPar1Pos = par1PosVel.position;
		lastPerpPos = perpPosVel.position;

		return twist;
	}
}

//package org.firstinspires.ftc.teamcode.RoadRunner;
//
//        import com.acmerobotics.dashboard.config.Config;
//        import com.acmerobotics.roadrunner.DualNum;
//        import com.acmerobotics.roadrunner.Time;
//        import com.acmerobotics.roadrunner.Twist2dDual;
//        import com.acmerobotics.roadrunner.Vector2dDual;
//        import com.acmerobotics.roadrunner.ftc.Encoder;
//        import com.acmerobotics.roadrunner.ftc.FlightRecorder;
//        import com.acmerobotics.roadrunner.ftc.OverflowEncoder;
//        import com.acmerobotics.roadrunner.ftc.PositionVelocityPair;
//        import com.acmerobotics.roadrunner.ftc.RawEncoder;
//        import com.qualcomm.robotcore.hardware.DcMotorEx;
//        import com.qualcomm.robotcore.hardware.DcMotorSimple;
//        import com.qualcomm.robotcore.hardware.HardwareMap;
//
//        import org.firstinspires.ftc.teamcode.Framework.misc.KalmanFilter;
//
//@Config
//public final class ThreeDeadWheelLocalizer implements Localizer {
//    public static class Params {
//        public double par0YTicks = -1307.1156497501247; // y position of the first parallel encoder (in tick units)
//        public double par1YTicks = 1344.0467238904039; // y position of the second parallel encoder (in tick units)
//        public double perpXTicks = 979.3007250312698; // x position of the perpendicular encoder (in tick units)
//    }
//
//    public static Params PARAMS = new Params();
//
//    public final Encoder par0, par1, perp;
//
//    public final double inPerTick;
//
//    private int lastPar0Pos, lastPar1Pos, lastPerpPos;
//
//    // Kalman Filters for position and velocity
//    private KalmanFilter kfPar0Pos, kfPar1Pos, kfPerpPos;
//    private KalmanFilter kfPar0Vel, kfPar1Vel, kfPerpVel;
//
//    public ThreeDeadWheelLocalizer(HardwareMap hardwareMap, double inPerTick) {
//        // TODO: make sure your config has **motors** with these names (or change them)
//        //   the encoders should be plugged into the slot matching the named motor
//        //   see https://ftc-docs.firstinspires.org/en/latest/hardware_and_software_configuration/configuring/index.html
//        par0 = new OverflowEncoder(new RawEncoder(hardwareMap.get(DcMotorEx.class, "motorFR")));
//        par1 = new OverflowEncoder(new RawEncoder(hardwareMap.get(DcMotorEx.class, "motorFL")));
//        perp = new OverflowEncoder(new RawEncoder(hardwareMap.get(DcMotorEx.class, "motorBR")));
//
//        // TODO: reverse encoder directions if needed
//        par1.setDirection(DcMotorSimple.Direction.REVERSE);
//        perp.setDirection(DcMotorSimple.Direction.REVERSE);
//
//        lastPar0Pos = par0.getPositionAndVelocity().position;
//        lastPar1Pos = par1.getPositionAndVelocity().position;
//        lastPerpPos = perp.getPositionAndVelocity().position;
//
//        this.inPerTick = inPerTick;
//
//        // Initialize Kalman filters with recommended ranges
//        // Position filters
//        kfPar0Pos = new KalmanFilter(20, 20); // Example values, adjust as needed
//        kfPar1Pos = new KalmanFilter(20, 20); // Example values, adjust as needed
//        kfPerpPos = new KalmanFilter(20, 20); // Example values, adjust as needed
//
//        // Velocity filters
//        kfPar0Vel = new KalmanFilter(0.5, 0.5); // Example values, adjust as needed
//        kfPar1Vel = new KalmanFilter(0.5, 0.5); // Example values, adjust as needed
//        kfPerpVel = new KalmanFilter(0.5, 0.5); // Example values, adjust as needed
//        FlightRecorder.write("THREE_DEAD_WHEEL_PARAMS", PARAMS);
//    }
//
//    public Twist2dDual<Time> update() {
//        PositionVelocityPair par0PosVel = par0.getPositionAndVelocity();
//        PositionVelocityPair par1PosVel = par1.getPositionAndVelocity();
//        PositionVelocityPair perpPosVel = perp.getPositionAndVelocity();
//
//        int par0PosDelta = par0PosVel.position - lastPar0Pos;
//        int par1PosDelta = par1PosVel.position - lastPar1Pos;
//        int perpPosDelta = perpPosVel.position - lastPerpPos;
//
//        // Update Kalman filters with new measurements
//        double filteredPar0Pos = kfPar0Pos.update(par0PosVel.position);
//        double filteredPar1Pos = kfPar1Pos.update(par1PosVel.position);
//        double filteredPerpPos = kfPerpPos.update(perpPosVel.position);
//
//        double filteredPar0Vel = kfPar0Vel.update(par0PosVel.velocity);
//        double filteredPar1Vel = kfPar1Vel.update(par1PosVel.velocity);
//        double filteredPerpVel = kfPerpVel.update(perpPosVel.velocity);
//
//        Twist2dDual<Time> twist = new Twist2dDual<>(
//                new Vector2dDual<>(
//                        new DualNum<Time>(new double[] {
//                                (PARAMS.par0YTicks * filteredPar1Pos - PARAMS.par1YTicks * filteredPar0Pos) / (PARAMS.par0YTicks - PARAMS.par1YTicks),
//                                (PARAMS.par0YTicks * filteredPar1Vel - PARAMS.par1YTicks * filteredPar0Vel) / (PARAMS.par0YTicks - PARAMS.par1YTicks),
//                        }).times(inPerTick),
//                        new DualNum<Time>(new double[] {
//                                (PARAMS.perpXTicks / (PARAMS.par0YTicks - PARAMS.par1YTicks) * (filteredPar1Pos - filteredPar0Pos) + filteredPerpPos),
//                                (PARAMS.perpXTicks / (PARAMS.par0YTicks - PARAMS.par1YTicks) * (filteredPar1Vel - filteredPar0Vel) + filteredPerpVel),
//                        }).times(inPerTick)
//                ),
//                new DualNum<>(new double[] {
//                        (filteredPar0Pos - filteredPar1Pos) / (PARAMS.par0YTicks - PARAMS.par1YTicks),
//                        (filteredPar0Vel - filteredPar1Vel) / (PARAMS.par0YTicks - PARAMS.par1YTicks),
//                })
//        );
//
//        lastPar0Pos = par0PosVel.position;
//        lastPar1Pos = par1PosVel.position;
//        lastPerpPos = perpPosVel.position;
//
//        return twist;
//    }
//}
