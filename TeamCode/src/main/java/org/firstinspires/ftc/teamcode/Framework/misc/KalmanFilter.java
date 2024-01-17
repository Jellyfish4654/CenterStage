package org.firstinspires.ftc.teamcode.Framework.misc;

public class KalmanFilter {
    private double R; // Measurement noise covariance
    private double Q; // Process noise covariance
    private double x; // State estimate
    private double P; // Estimate covariance

    public KalmanFilter(double R, double Q) {
        this.R = R;
        this.Q = Q;
        this.x = 0; // initial state estimate
        this.P = 1; // initial covariance estimate
    }

    public double update(double measurement) {
        // Prediction update
        P = P + Q;

        // Measurement update
        double K = P / (P + R);
        x = x + K * (measurement - x);
        P = (1 - K) * P;
        return x;
    }
}