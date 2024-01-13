package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.core.colorscheme.scheme.*;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class MeepMeepTesting {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(750);

        RoadRunnerBotEntity RED_RIGHT_PURPLE = new DefaultBotBuilder(meepMeep)
                .setColorScheme(new ColorSchemeBlueDark())
                .setConstraints(60, 60, Math.toRadians(90), Math.toRadians(90), 18)
                .build();
        RoadRunnerBotEntity RED_RIGHT_YELLOW = new DefaultBotBuilder(meepMeep)
                .setColorScheme(new ColorSchemeBlueDark())
                .setConstraints(60, 60, Math.toRadians(90), Math.toRadians(0), 18)
                .build();
        RoadRunnerBotEntity BLUE_RIGHT_PURPLE = new DefaultBotBuilder(meepMeep)
                .setColorScheme(new ColorSchemeBlueDark())
                .setConstraints(60, 60, Math.toRadians(90), Math.toRadians(90), 18)
                .build();
        RoadRunnerBotEntity BLUE_RIGHT_YELLOW = new DefaultBotBuilder(meepMeep)
                .setColorScheme(new ColorSchemeBlueDark())
                .setConstraints(60, 60, Math.toRadians(90), Math.toRadians(0), 18)
                .setConstraints(60, 60, Math.toRadians(270), Math.toRadians(90), 18)
                .build();
        RoadRunnerBotEntity BLUE_RIGHT_YELLOW = new DefaultBotBuilder(meepMeep)
                .setColorScheme(new ColorSchemeBlueDark())
                .setConstraints(60, 60, Math.toRadians(270), Math.toRadians(0), 18)
                .build();
// Red Right Purple Left
//        RED_RIGHT_PURPLE.runAction(RED_RIGHT_PURPLE.getDrive().actionBuilder(new Pose2d(15, -68, Math.toRadians(90)))
//                .splineTo(new Vector2d(8, -34), Math.toRadians(135))
//                .splineToConstantHeading(new Vector2d(10, -36), Math.toRadians(315))
//                .splineToSplineHeading(new Pose2d(40, -36, Math.toRadians(0)), Math.toRadians(315))
//                .build());
// Red Right Purple Middle
//        RED_RIGHT_PURPLE.runAction(RED_RIGHT_PURPLE.getDrive().actionBuilder(new Pose2d(15, -68, Math.toRadians(90)))
//                .splineTo(new Vector2d(15, -32), Math.toRadians(90))
//                .splineToConstantHeading(new Vector2d(15, -38), Math.toRadians(270))
//                .splineToSplineHeading(new Pose2d(40, -36, Math.toRadians(0)), Math.toRadians(0))
//                .build());
        // Red Right Purple Right
//        RED_RIGHT_PURPLE.runAction(RED_RIGHT_PURPLE.getDrive().actionBuilder(new Pose2d(15, -68, Math.toRadians(90)))
//                .splineTo(new Vector2d(15, -46), Math.toRadians(90))
//                .splineTo(new Vector2d(18, -38), Math.toRadians(60))
//                .splineToConstantHeading(new Vector2d(15, -46), Math.toRadians(240))
//                .splineToSplineHeading(new Pose2d(31, -46, Math.toRadians(0)), Math.toRadians(0))
//                .splineToConstantHeading(new Vector2d(40, -36), Math.toRadians(0))
//                .build());
//        Red Right Yellow Left
//        RED_RIGHT_PURPLE.runAction(RED_RIGHT_PURPLE.getDrive().actionBuilder(new Pose2d(40, -36, Math.toRadians(0)))
//                .splineToConstantHeading(new Vector2d(44.4, -28), Math.toRadians(0))
//                .splineToLinearHeading(new Pose2d(49, -28, 0), 0)
//        .splineToLinearHeading(new Pose2d(46.3, -28, 0), Math.toRadians(90))
//                .splineToLinearHeading(new Pose2d(46.3, -12, 0), Math.toRadians(90))
//
//                .build());
//         Red Right Yellow Center
//        RED_RIGHT_PURPLE.runAction(RED_RIGHT_PURPLE.getDrive().actionBuilder(new Pose2d(40, -36, Math.toRadians(0)))
//                .splineToConstantHeading(new Vector2d(49, -36), Math.toRadians(0))
//        .splineToLinearHeading(new Pose2d(46.3, -36, 0), Math.toRadians(90))
//                .splineToLinearHeading(new Pose2d(46.3, -12, 0), Math.toRadians(90))
//                .build());
        // Red Right Yellow Right
//        RED_RIGHT_PURPLE.runAction(RED_RIGHT_PURPLE.getDrive().actionBuilder(new Pose2d(40, -36, Math.toRadians(0)))
//                .splineToConstantHeading(new Vector2d(47.4, -41.2), Math.toRadians(0))
//                .splineToLinearHeading(new Pose2d(49.3, -41.2, 0), Math.toRadians(0))
//                .splineToLinearHeading(new Pose2d(46.3, -41.2, 0), Math.toRadians(90))
//
//                .splineToLinearHeading(new Pose2d(46.3, -12, 0), Math.toRadians(90))
//                              //  .splineToSplineHeading()
//                .build());
        // Red Right Park
//        RED_RIGHT_PURPLE.runAction(RED_RIGHT_PURPLE.getDrive().actionBuilder(new Pose2d(46.3, -12, Math.toRadians(0)))
//                .splineToConstantHeading(new Vector2d(59, -12), Math.toRadians(0))
//
//                .build());

//        Blue Starts Here

// Blue Left Purple Left
        RED_RIGHT_PURPLE.runAction(RED_RIGHT_PURPLE.getDrive().actionBuilder(new Pose2d(15, -68, Math.toRadians(90)))
//        BLUE_RIGHT_PURPLE.runAction(RED_RIGHT_PURPLE.getDrive().actionBuilder(new Pose2d(15, -68, Math.toRadians(90)))
//                .splineTo(new Vector2d(8, -34), Math.toRadians(135))
//                .splineToConstantHeading(new Vector2d(10, -36), Math.toRadians(315))
//                .splineToSplineHeading(new Pose2d(40, -36, Math.toRadians(0)), Math.toRadians(315))
//                .build());
// Blue Right Purple Middle
        BLUE_RIGHT_PURPLE.runAction(RED_RIGHT_PURPLE.getDrive().actionBuilder(new Pose2d(15, -68, Math.toRadians(90)))
                .splineTo(new Vector2d(15, -32), Math.toRadians(90))
                .splineToConstantHeading(new Vector2d(15, -38), Math.toRadians(270))
                .splineToSplineHeading(new Pose2d(40, -36, Math.toRadians(0)), Math.toRadians(270))
                .build());
        // Blue Right Purple Right
//        BLUE_RIGHT_PURPLE.runAction(RED_RIGHT_PURPLE.getDrive().actionBuilder(new Pose2d(15, -68, Math.toRadians(90)))
//                .splineTo(new Vector2d(15, -46), Math.toRadians(90))
//                .splineTo(new Vector2d(18, -38), Math.toRadians(60))
//                .splineToConstantHeading(new Vector2d(15, -46), Math.toRadians(240))
//                .splineToSplineHeading(new Pose2d(31, -46, Math.toRadians(0)), Math.toRadians(0))
//                .splineToConstantHeading(new Vector2d(40, -36), Math.toRadians(0))
//        BLUE_RIGHT_PURPLE.runAction(BLUE_RIGHT_PURPLE.getDrive().actionBuilder(new Pose2d(10, 60, Math.toRadians(270)))
//                .splineToConstantHeading(new Vector2d(15, 55), Math.toRadians(270))
//                .splineTo(new Vector2d(8, 34), Math.toRadians(225))
//                .splineToConstantHeading(new Vector2d(10, 36), Math.toRadians(45))
//                .splineToSplineHeading(new Pose2d(40, 36, Math.toRadians(0)), Math.toRadians(45))
//                .build());
// Blue Right Purple Middle
//        BLUE_RIGHT_PURPLE.runAction(BLUE_RIGHT_PURPLE.getDrive().actionBuilder(new Pose2d(10, 60, Math.toRadians(270)))
//                .splineToConstantHeading(new Vector2d(15, 55), Math.toRadians(270))
//                .splineTo(new Vector2d(15, 32), Math.toRadians(270))
//                .splineToConstantHeading(new Vector2d(15, 38), Math.toRadians(90))
//                .splineToSplineHeading(new Pose2d(40, 36, Math.toRadians(0)), Math.toRadians(0))
//                .build());
        // Blue Right Purple Right
//        BLUE_RIGHT_PURPLE.runAction(BLUE_RIGHT_PURPLE.getDrive().actionBuilder(new Pose2d(10, 60, Math.toRadians(270)))
//        .splineTo(new Vector2d(15, 46), Math.toRadians(270))
//                .splineTo(new Vector2d(18, 38), Math.toRadians(300))
//                .splineToConstantHeading(new Vector2d(15, 46), Math.toRadians(120))
//                .splineToSplineHeading(new Pose2d(31, 46, Math.toRadians(0)), Math.toRadians(0))
//                .splineToConstantHeading(new Vector2d(40, 36), Math.toRadians(0))
//                .build());

//        Blue Right Yellow Left
//        BLUE_RIGHT_PURPLE.runAction(BLUE_RIGHT_PURPLE.getDrive().actionBuilder(new Pose2d(40, 36, Math.toRadians(0)))
//                .splineToConstantHeading(new Vector2d(47.4, 41.2), Math.toRadians(0))
//                .splineToLinearHeading(new Pose2d(49.3, 41.2, 0), Math.toRadians(0))
//                .splineToLinearHeading(new Pose2d(46.3, 41.2, 0), Math.toRadians(270))
//
//                .splineToLinearHeading(new Pose2d(46.3, 12, 0), Math.toRadians(270))
//                .build());
//
//         Blue Right Yellow Center
//        BLUE_RIGHT_PURPLE.runAction(BLUE_RIGHT_PURPLE.getDrive().actionBuilder(new Pose2d(40, 36, Math.toRadians(0)))
//                .splineToConstantHeading(new Vector2d(49, 36), Math.toRadians(0))
//        .splineToLinearHeading(new Pose2d(46.3, 36, 0), Math.toRadians(270))
//                .splineToLinearHeading(new Pose2d(46.3, 12, 0), Math.toRadians(270))
//                .build());
        // Blue Right Yellow Right
//        BLUE_RIGHT_PURPLE.runAction(BLUE_RIGHT_PURPLE.getDrive().actionBuilder(new Pose2d(40, 36, Math.toRadians(0)))
//                .splineToConstantHeading(new Vector2d(44.4, 28), Math.toRadians(0))
//                .splineToLinearHeading(new Pose2d(49, 28, 0), 0)
//        .splineToLinearHeading(new Pose2d(46.3, 28, 0), Math.toRadians(270))
//                .splineToLinearHeading(new Pose2d(46.3, 12, 0), Math.toRadians(270))
//
//                .build());
        // Blue Right Park
        BLUE_RIGHT_PURPLE.runAction(BLUE_RIGHT_PURPLE.getDrive().actionBuilder(new Pose2d(46.3, 12, Math.toRadians(0)))
                .splineToConstantHeading(new Vector2d(59, 12), Math.toRadians(0))

                .build());
        meepMeep.setBackground(MeepMeep.Background.FIELD_CENTERSTAGE_OFFICIAL)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(BLUE_RIGHT_PURPLE)

                .start();
    }
}