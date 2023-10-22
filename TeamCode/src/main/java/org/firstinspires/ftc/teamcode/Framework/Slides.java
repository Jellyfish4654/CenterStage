package org.firstinspires.ftc.teamcode.Framework;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.ThermalEquilibrium.homeostasis.Controllers.Feedback.BasicPID;
import com.ThermalEquilibrium.homeostasis.Parameters.PIDCoefficients;

public class Slides {

    private DcMotor leftMotor;
    private DcMotor rightMotor;
    int leftPosition = leftMotor.getCurrentPosition();
    int rightPosition = rightMotor.getCurrentPosition();
    private final double Kp = 0;
    private final double Ki = 0;
    private final double Kd = 0;
    private final double f = 0;
    private final double ticksInDegree = 145.1 / 360.0;
    public int targetPosition = 0;
    public final int upperBound = 3000;
    //stop break if goes over upperbound
    private double leftPower;
    private double rightPower;
    PIDCoefficients coefficients = new PIDCoefficients(Kp,Ki,Kd);
    BasicPID controller = new BasicPID(coefficients);
    public Slides(DcMotor leftMotor, DcMotor rightMotor) {
        this.leftMotor = leftMotor;
        this.rightMotor = rightMotor;
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

    public void moveSlides(double leftPower, double rightPower) {
        leftMotor.setPower(leftPower);
        rightMotor.setPower(rightPower);
    }

    public void update() {
        // Calculate the PID control output based on the current and target positions
        double leftPid = controller.calculate(targetPosition, this.leftPosition);
        double rightPid = controller.calculate(targetPosition, this.rightPosition);
        // Calculate the feed-forward term to anticipate gravity's effect on the slide
        double ff = Math.cos(Math.toRadians(targetPosition / ticksInDegree)) * f;
        // Determine the total power as the sum of PID output and feed-forward term
        this.leftPower = leftPid + ff;
        this.rightPower = rightPid + ff;
        moveSlides(leftPower, rightPower);
    }
}
