package org.firstinspires.ftc.teamcode.Framework.misc;

import androidx.core.math.MathUtils;

import com.qualcomm.robotcore.util.ElapsedTime;

public class SlewRateLimiter
{
    private double maxIncreasePerSecond;
    private double maxDecreasePerSecond;
    private final ElapsedTime timer;
    private double lastValue;
    private double lastUpdateTime;

    public SlewRateLimiter(double positiveRateLimit, double negativeRateLimit, double initialValue)
    {
        this.maxIncreasePerSecond = positiveRateLimit;
        this.maxDecreasePerSecond = negativeRateLimit;
        this.lastValue = initialValue;
        this.lastUpdateTime = 0;
        this.timer = new ElapsedTime();
    }

    public SlewRateLimiter(double rateLimit, double initialValue)
    {
        this(rateLimit, -rateLimit, initialValue);
    }

    public SlewRateLimiter(double rateLimit)
    {
        this(rateLimit, 0);
    }

    public void setRate(double rateLimit)
    {
        this.maxIncreasePerSecond = rateLimit;
        this.maxDecreasePerSecond = -rateLimit;
    }

    public double calculate(double input)
    {
        double currentTime = timer.seconds();
        double elapsedTime = currentTime - lastUpdateTime;

        // Limit the change in value based on the rate limits and elapsed time
        lastValue +=
                MathUtils.clamp(
                        input - lastValue,
                        maxDecreasePerSecond * elapsedTime,
                        maxIncreasePerSecond * elapsedTime);
        lastUpdateTime = currentTime;
        return lastValue;
    }
}