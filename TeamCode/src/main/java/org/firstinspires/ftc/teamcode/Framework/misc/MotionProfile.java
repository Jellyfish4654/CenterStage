package org.firstinspires.ftc.teamcode.Framework.misc;

public class MotionProfile {

    public static double motion_profile(double maxAcceleration, double maxVelocity, double distance, double elapsedTime) {
        double accelerationDt = maxVelocity / maxAcceleration;
        double halfwayDistance = distance / 2;
        double accelerationDistance = 0.5 * maxAcceleration * Math.pow(accelerationDt, 2);

        if (accelerationDistance > halfwayDistance) {
            accelerationDt = Math.sqrt(halfwayDistance / (0.5 * maxAcceleration));
        }

        accelerationDistance = 0.5 * maxAcceleration * Math.pow(accelerationDt, 2);
        maxVelocity = maxAcceleration * accelerationDt;

        double decelerationDt = accelerationDt;
        double cruiseDistance = distance - 2 * accelerationDistance;
        double cruiseDt = cruiseDistance / maxVelocity;
        double decelerationTime = accelerationDt + cruiseDt;

        double entireDt = accelerationDt + cruiseDt + decelerationDt;
        if (elapsedTime > entireDt) {
            return distance;
        }
        if (elapsedTime < accelerationDt) {
            return 0.5 * maxAcceleration * Math.pow(elapsedTime, 2);
        } else if (elapsedTime < decelerationTime) {
            accelerationDistance = 0.5 * maxAcceleration * Math.pow(accelerationDt, 2);
            double cruiseCurrentDt = elapsedTime - accelerationDt;

            return accelerationDistance + maxVelocity * cruiseCurrentDt;
        }
        else {
            accelerationDistance = 0.5 * maxAcceleration * Math.pow(accelerationDt, 2);
            cruiseDistance = maxVelocity * cruiseDt;
            double decelerationCurrentDt = elapsedTime - decelerationTime;

            return accelerationDistance + cruiseDistance + maxVelocity * decelerationCurrentDt - 0.5 * maxAcceleration * Math.pow(decelerationCurrentDt, 2);
        }
    }
}
