package org.firstinspires.ftc.teamcode.Framework;

import com.qualcomm.robotcore.hardware.DistanceSensor;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class intakeDistance {

	private DistanceSensor distanceSensor;
	private double previousDistance;
	private int elementsCount = 0;
	private boolean elementDetected = false;
	private int lastReportedCount = -1;
	private final double THRESHOLD = 5.0;

	public intakeDistance(DistanceSensor distanceSensor) {
		this.distanceSensor = distanceSensor;
		this.previousDistance = distanceSensor.getDistance(DistanceUnit.CM);
	}

	public void update() {
		double currentDistance = distanceSensor.getDistance(DistanceUnit.CM);

		if (currentDistance < previousDistance - THRESHOLD && !elementDetected) {
			elementsCount++;
			elementDetected = true;
		} else if (currentDistance > previousDistance + THRESHOLD && elementDetected) {
			elementDetected = false;
		}

		previousDistance = currentDistance;
	}

	public int getElementsCount() {
		return elementsCount;
	}

	public void resetElementsCount() {
		elementsCount = 0;
	}

	public boolean hasCountChanged() {
		if (lastReportedCount != elementsCount) {
			lastReportedCount = elementsCount;
			return true;
		}
		return false;
	}
}
