package org.firstinspires.ftc.teamcode.Framework.misc;

public class MotionProfile {
    public double initialPosition;
    public double finalPosition;
    public double distance;
    public double t1, t2, t3; // Times for acceleration, constant velocity, and deceleration phases
    public double totalTime;
    public double maxVelocity;
    public Constraints constraints;

    public MotionProfile(double initialPosition, double finalPosition, Constraints constraints) {
        this.initialPosition = initialPosition;
        this.finalPosition = finalPosition;
        this.distance = finalPosition - initialPosition;
        this.constraints = constraints;

        // Calculate the time to reach max velocity
        t1 = constraints.maxVelocity / constraints.maxAcceleration;
        // Time to decelerate
        t3 = constraints.maxVelocity / constraints.maxDeceleration;
        // Time at max velocity
        t2 = Math.abs(distance) / constraints.maxVelocity - (t1 + t3) / 2;

        // If t2 is negative, the profile does not reach max velocity
        if (t2 < 0) {
            t2 = 0;
            double a = constraints.maxAcceleration;
            double d = constraints.maxDeceleration;
            t1 = Math.sqrt((d * distance) / (a * d + a * a));
            t3 = (a * t1) / d;
        }

        maxVelocity = constraints.maxVelocity;
        totalTime = t1 + t2 + t3;
    }

    public State calculate(double time) {
        double position, velocity, acceleration;
        if (time <= t1) {
            // Acceleration phase
            acceleration = constraints.maxAcceleration;
            velocity = acceleration * time;
            position = 0.5 * acceleration * time * time;
        } else if (time <= t1 + t2) {
            // Constant velocity phase
            acceleration = 0;
            velocity = maxVelocity;
            position = (0.5 * maxVelocity * t1) + maxVelocity * (time - t1);
        } else if (time <= totalTime) {
            // Deceleration phase
            double timeInDeceleration = time - t1 - t2;
            acceleration = -constraints.maxDeceleration;
            velocity = maxVelocity - acceleration * timeInDeceleration;
            position = (0.5 * maxVelocity * t1) + (maxVelocity * t2) + (maxVelocity * timeInDeceleration) + (0.5 * acceleration * timeInDeceleration * timeInDeceleration);
        } else {
            // End of profile
            acceleration = 0;
            velocity = 0;
            position = distance;
        }

        State state = new State();
        state.x = initialPosition + position;
        state.v = velocity;
        state.a = acceleration;

        return state;
    }

    // Inner class for state representation
    public static class State {
        public double x; // position
        public double v; // velocity
        public double a; // acceleration
    }

    // Inner class for constraints
    public static class Constraints {
        public double maxAcceleration;
        public double maxDeceleration;
        public double maxVelocity;

        public Constraints(double maxVelo, double maxAccel, double maxDecel) {
            this.maxVelocity = maxVelo;
            this.maxAcceleration = maxAccel;
            this.maxDeceleration = maxDecel;
        }
    }
}
