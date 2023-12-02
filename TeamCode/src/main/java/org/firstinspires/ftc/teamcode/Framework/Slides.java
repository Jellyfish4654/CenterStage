package org.firstinspires.ftc.teamcode.Framework;

import static com.qualcomm.robotcore.hardware.DcMotor.ZeroPowerBehavior.BRAKE;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.gamepad2;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

import com.qualcomm.robotcore.hardware.DcMotor;import com.qualcomm.robotcore.hardware.DcMotorEx;

public class Slides{

    static DcMotorEx leftSlide;
    static DcMotorEx rightSlide;
    static double leftTarget;
    static double rightTarget;
    static double lkp = 0.005;
    static double rkp = 0.005;
    static boolean limitoverride = false;
    public static boolean multPrecision = false;
    public static boolean slideExtracted = false;
    static int left_position = 0;
    static int right_position = 0;

    public Slides(DcMotorEx leftSlide, DcMotorEx rightSlide){
        Slides.leftSlide = leftSlide;
        Slides.rightSlide = rightSlide;
        leftSlide.setZeroPowerBehavior(BRAKE);
        rightSlide.setZeroPowerBehavior(BRAKE);

        leftSlide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightSlide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        leftSlide.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightSlide.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        left_position = leftSlide.getCurrentPosition();
        right_position = rightSlide.getCurrentPosition();

        leftTarget=0;
        rightTarget=0;
/*
        telemetry.addData("EncoderLeft", left_position);
        telemetry.addData("EncoderRight", right_position);
        telemetry.addData("leftTarget", leftTarget);
        telemetry.update();
        */
    }

    public static void high(){
        leftTarget = 4053;
        rightTarget = 4053;
    }
    public static void highDown(){
        leftTarget = 3950;
        rightTarget = 3950;
    }
    public static void mid(){
        leftTarget = 2865;
        rightTarget = 2865;
    }
    public static void low(){
        leftTarget = 1696;
        rightTarget = 1697;
    }
    public static void reset(){
        leftTarget = 0;
        rightTarget = 0;
    }

    public static void override(){
        if(limitoverride){
            limitoverride=false;
        }
        else if(!limitoverride){
            limitoverride=true;
        }
    }
    //For cups on stack
    public static void fiveCups(){
        leftTarget = 595;
        rightTarget = 595;
    }
    public static void fourCups(){
        leftTarget = 451;
        rightTarget = 451;
    }
    public static void threeCups(){
        leftTarget = 330;
        rightTarget = 330;
    }
    public static void twoCups(){
        leftTarget = 184;
        rightTarget = 184;
    }

    //Reset at 0 should be the same for one cup
    //
    //For slide positions in auto like cups

    public void manual(float num){
        leftTarget += (double)num*8;
        rightTarget += (double)num*8;
    }
    public boolean wLoop(){

        while(leftTarget!=leftSlide.getCurrentPosition() && rightTarget!=rightSlide.getCurrentPosition()){
            pLoop();
        }
        return true;
    }

    public void pLoop(){
//one of them is reversed... idk which one so use MeasureSlides to determine,
// whichever one has encoders going backwards is left, whichever has positive is right
        double leftPosition = (double)(leftSlide.getCurrentPosition());

        double left_current_error = leftTarget-leftPosition;

        double lp = lkp * left_current_error;

        double rightPosition = (double)(rightSlide.getCurrentPosition());

        double right_current_error = rightTarget-rightPosition;

        double rp = rkp * right_current_error;

        leftSlide.setPower(lp);
        rightSlide.setPower(rp);

        if(leftTarget<4401){
            leftTarget = 4401;
        }
        if(rightTarget>4405){
            rightTarget = 4405;
        }
        if(rightTarget>4000){
            multPrecision = true;
        }
        if(rightTarget<4000){
            multPrecision = false;
        }
        if(rightTarget>450){
            slideExtracted = true;
        }
        if(rightTarget<450){
            slideExtracted = false;
        }
        if(rightSlide.getCurrentPosition()==0){
            rightSlide.setPower(0);
            leftSlide.setPower(0);
        }
    }

}