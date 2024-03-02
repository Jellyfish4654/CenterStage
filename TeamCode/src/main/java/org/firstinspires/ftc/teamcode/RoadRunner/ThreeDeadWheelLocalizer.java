package org.firstinspires.ftc.teamcode.RoadRunner;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.*;
import com.acmerobotics.roadrunner.ftc.*;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.teamcode.Framework.misc.KalmanFilter;

@Config
public final class ThreeDeadWheelLocalizer implements Localizer {
	public static class Params {
		public double par0YTicks = -1257.305666187808; // y position of the first parallel encoder (in tick units)
		public double par1YTicks = 1373.2202690396632; // y position of the second parallel encoder (in tick units)
		public double perpXTicks = 981.1172993779844; // x position of the perpendicular encoder (in tick units)
	}

	public static Params PARAMS = new Params();

	public final Encoder par0, par1, perp;

	public final double inPerTick;

	private KalmanFilter kfPar0Pos, kfPar1Pos, kfPerpPos;
	private KalmanFilter kfPar0Vel, kfPar1Vel, kfPerpVel;

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
		par1.setDirection(DcMotorSimple.Direction.REVERSE);
		perp.setDirection(DcMotorSimple.Direction.REVERSE);

		this.inPerTick = inPerTick;

		kfPar0Pos = new KalmanFilter(0.0015 , 0.0125);
		kfPar1Pos = new KalmanFilter(0.0015 , 0.0125);
		kfPerpPos = new KalmanFilter(0.0015 , 0.0125);
		kfPar0Vel = new KalmanFilter(0.0015 , 0.0125);
		kfPar1Vel = new KalmanFilter(0.0015 , 0.0125);
		kfPerpVel = new KalmanFilter(0.0015 , 0.0125);

		lastPar0Pos = par0.getPositionAndVelocity().position;
		lastPar1Pos = par1.getPositionAndVelocity().position;
		lastPerpPos = perp.getPositionAndVelocity().position;
	}

	public Twist2dDual<Time> update()
	{
		PositionVelocityPair par0PosVel = par0.getPositionAndVelocity();
		PositionVelocityPair par1PosVel = par1.getPositionAndVelocity();
		PositionVelocityPair perpPosVel = perp.getPositionAndVelocity();

		double filteredPar0Pos = kfPar0Pos.update(par0PosVel.position);
		double filteredPar1Pos = kfPar1Pos.update(par1PosVel.position);
		double filteredPerpPos = kfPerpPos.update(perpPosVel.position);
		double filteredPar0Vel = kfPar0Vel.update(par0PosVel.velocity);
		double filteredPar1Vel = kfPar1Vel.update(par1PosVel.velocity);
		double filteredPerpVel = kfPerpVel.update(perpPosVel.velocity);

		int par0PosDelta = (int)(filteredPar0Pos - lastPar0Pos);
		int par1PosDelta = (int)(filteredPar1Pos - lastPar1Pos);
		int perpPosDelta = (int)(filteredPerpPos - lastPerpPos);

		Twist2dDual<Time> twist = new Twist2dDual<>(
				new Vector2dDual<>(
						new DualNum<Time>(new double[]{
								(PARAMS.par0YTicks * par1PosDelta - PARAMS.par1YTicks * par0PosDelta) / (PARAMS.par0YTicks - PARAMS.par1YTicks),
								(PARAMS.par0YTicks * filteredPar1Vel - PARAMS.par1YTicks * filteredPar0Vel) / (PARAMS.par0YTicks - PARAMS.par1YTicks),
						}).times(inPerTick),
						new DualNum<Time>(new double[]{
								(PARAMS.perpXTicks / (PARAMS.par0YTicks - PARAMS.par1YTicks) * (par1PosDelta - par0PosDelta) + perpPosDelta),
								(PARAMS.perpXTicks / (PARAMS.par0YTicks - PARAMS.par1YTicks) * (filteredPar1Vel - filteredPar0Vel) + filteredPerpVel),
						}).times(inPerTick)
				),
				new DualNum<>(new double[]{
						(par0PosDelta - par1PosDelta) / (PARAMS.par0YTicks - PARAMS.par1YTicks),
						(filteredPar0Vel - filteredPar1Vel) / (PARAMS.par0YTicks - PARAMS.par1YTicks),
				})
		);

		lastPar0Pos = (int)filteredPar0Pos;
		lastPar1Pos = (int)filteredPar1Pos;
		lastPerpPos = (int)filteredPerpPos;

		return twist;
	}
}
