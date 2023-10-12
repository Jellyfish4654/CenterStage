package org.firstinspires.ftc.teamcode.Framework;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.ThermalEquilibrium.homeostasis.Controllers.Feedback.BasicPID;
import com.ThermalEquilibrium.homeostasis.Parameters.PIDCoefficients;

public class Slides {

    private DcMotor motorLeft;
    private DcMotor motorRight;
    int position1 = motorLeft.getCurrentPosition();
    int position2 = motorRight.getCurrentPosition();
    private final double Kp = 0;
    private final double Ki = 0;
    private final double Kd = 0;
    private final double f = 0;
    private final double ticksInDegree = 145.1 / 360.0;
    public int targetPosition = 0;
    public final int upperBound = 3000;
    //stop break if goes over upperbound
    private double power1;
    private double power2;
    PIDCoefficients coefficients = new PIDCoefficients(Kp,Ki,Kd);
    BasicPID controller = new BasicPID(coefficients);
    public Slides(DcMotor mot1, DcMotor mot2) {
        this.motorLeft = mot1;
        this.motorRight = mot2;
        //mot1 = left motor, mot2 = right motor
    }

    public void setTargetPosition(int target) {
        if (target < 0) {
            this.targetPosition = 0;
        } else if (target > upperBound) {
            this.targetPosition = upperBound;
        } else {
            this.targetPosition = target;
        }

        //safety limit
    }

    public void moveSlides(double power1, double power2) {
        motorLeft.setPower(power1);
        motorRight.setPower(power2);
    }

    public void update() {
        // Calculate the PID control output based on the current and target positions
        double leftPid = controller.calculate(targetPosition, this.position1);
        double rightPid = controller.calculate(targetPosition, this.position2);
        // Calculate the feed-forward term to anticipate gravity's effect on the slide
        double ff = Math.cos(Math.toRadians(targetPosition / ticksInDegree)) * f;
        // Determine the total power as the sum of PID output and feed-forward term
        this.power1 = leftPid + ff;
        this.power2 = rightPid + ff;
        moveSlides(power1, power2);
    }
}
