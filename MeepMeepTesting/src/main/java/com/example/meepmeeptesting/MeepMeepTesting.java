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

        // MEEPMEEPS
        RoadRunnerBotEntity RED_RIGHT_PURPLE = new DefaultBotBuilder(meepMeep)
                .setColorScheme(new ColorSchemeBlueDark())
                .setConstraints(60, 60, Math.toRadians(90), Math.toRadians(90), 18)
                .build();
// ORIGINAL

//        RED_RIGHT_PURPLE.runAction(RED_RIGHT_PURPLE.getDrive().actionBuilder(new Pose2d(13, -60, Math.toRadians(90)))
//                        .splineTo(new Vector2d(13, -46), Math.toRadians(90))
//                        .splineTo(new Vector2d(18, -38), Math.toRadians(60))
//                        .build());
//        RED_RIGHT_PURPLE.runAction(RED_RIGHT_PURPLE.getDrive().actionBuilder(new Pose2d(18, -38, Math.toRadians(60)))
//
//                .splineToConstantHeading(new Vector2d(13, -46), Math.toRadians(60))
//                                .turn(Math.toRadians(30))
//                .strafeTo(new Vector2d(9.7, -58.9))
//                .strafeTo(new Vector2d(107, -58))
//                .build());



//        RED_RIGHT_PURPLE.runAction(RED_RIGHT_PURPLE.getDrive().actionBuilder(new Pose2d(5, -34, Math.toRadians(135)))
////                .splineToConstantHeading(new Vector2d(8.8, -33.9), Math.toRadians(315))
////        .splineToSplineHeading(new Pose2d(10, -48, Math.toRadians(90)), Math.toRadians(0))
//                       .strafeTo(new Vector2d(10.7,-39.3))
//                                .turn(Math.toRadians(-45))
//                                .strafeTo(new Vector2d(10.7,-58.9))
////                .splineToConstantHeading(new Vector2d(10, -48), Math.toRadians(90))
////                .splineTo(new Vector2d(8, -60), Math.toRadians(315))
//                .build());

//        RED_RIGHT_PURPLE.runAction(RED_RIGHT_PURPLE.getDrive().actionBuilder(new Pose2d(5, -34, Math.toRadians(9135)))
//
//                .splineToConstantHeading(new Vector2d(15, -48), Math.toRadians(270))
//                .splineToSplineHeading(new Pose2d(15, -58, Math.toRadians(90)), Math.toRadians(0))
//                .splineToConstantHeading(new Vector2d(60, -58), Math.toRadians(0))
//                .build());

//
//        RED_RIGHT_PURPLE.runAction(RED_RIGHT_PURPLE.getDrive().actionBuilder(new Pose2d(13, -60, Math.toRadians(90))
//                .splineToConstantHeading(new Vector2d(13, -48), Math.toRadians(90))
//                .splineTo(new Vector2d(7.5, -36.5), Math.toRadians(135))
//                .build()));
                //park
//                RED_RIGHT_PURPLE.runAction(RED_RIGHT_PURPLE.getDrive().actionBuilder(new Pose2d(15, -60, Math.toRadians(90)))
//
//                        .splineToConstantHeading(new Vector2d(15, -48), Math.toRadians(90))
//                        .splineTo(new Vector2d(5, -34), Math.toRadians(135))
//                        .build());

//        RED_RIGHT_PURPLE.runAction(RED_RIGHT_PURPLE.getDrive().actionBuilder(new Pose2d(15, -60, Math.toRadians(90)))
//                .splineTo(new Vector2d(15, -48), Math.toRadians(90))
//                .splineTo(new Vector2d(5, -34), Math.toRadians(135))
//                .setReversed(true)
//                .splineTo(new Vector2d(5+(4*Math.cos(Math.toRadians(315))), -34+(4*Math.sin(Math.toRadians(315))) ), Math.toRadians(315))
//                .setReversed(false)
//                .setTangent(Math.toRadians(-15))
//                .splineToLinearHeading(new Pose2d(40, -36, Math.toRadians(0)), Math.toRadians(0))
//                .build());
//        RED_RIGHT_PURPLE.runAction(RED_RIGHT_PURPLE.getDrive().actionBuilder(new Pose2d(15, -60, Math.toRadians(90)))
//                        .splineTo(new Vector2d(11.7,-33),Math.toRadians(90))
//                .setReversed(true)
//                .splineTo(new Vector2d(11.7+(0*Math.cos(Math.toRadians(270))), -33+(4*Math.sin(Math.toRadians(270)))), Math.toRadians(270))
//              .setReversed(false)
//                .setTangent(Math.toRadians(-90))
//                .splineToLinearHeading(new Pose2d(40, -36, Math.toRadians(0)), Math.toRadians(0))
//                                .build());
        RED_RIGHT_PURPLE.runAction(RED_RIGHT_PURPLE.getDrive().actionBuilder(new Pose2d(15, -60, Math.toRadians(90)))
                .splineTo(new Vector2d(15, -48), Math.toRadians(90))
                .splineTo(new Vector2d(16.5, -35), Math.toRadians(45))
                .splineTo(new Vector2d(16.5+(-4*Math.cos(Math.toRadians(45))), -35+(4*Math.sin(Math.toRadians(45))) ), Math.toRadians(45))
//                .setReversed(false)
                .build());


        meepMeep.setBackground(MeepMeep.Background.FIELD_CENTERSTAGE_OFFICIAL)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(RED_RIGHT_PURPLE)

                .start();
    }
}
//        RoadRunnerBotEntity RED_RIGHT_YELLOW = new DefaultBotBuilder(meepMeep)
//                .setColorScheme(new ColorSchemeBlueDark())
//                .setConstraints(60, 60, Math.toRadians(90), Math.toRadians(0), 18)
//                .build();
//        RoadRunnerBotEntity BLUE_RIGHT_PURPLE = new DefaultBotBuilder(meepMeep)
//                .setColorScheme(new ColorSchemeBlueDark())
//                .setConstraints(60, 60, Math.toRadians(90), Math.toRadians(90), 18)
//                .build();
//        RoadRunnerBotEntity BLUE_RIGHT_YELLOW = new DefaultBotBuilder(meepMeep)
//                .setColorScheme(new ColorSchemeBlueDark())
//                .setConstraints(60, 60, Math.toRadians(90), Math.toRadians(0), 18)
//                .setConstraints(60, 60, Math.toRadians(270), Math.toRadians(90), 18)
//                .build();

// RIGHT
//        RED_RIGHT_PURPLE.runAction(RED_RIGHT_PURPLE.getDrive().actionBuilder(new Pose2d(15, -60, Math.toRadians(90)))
//                        .splineTo(new Vector2d(15, -46), Math.toRadians(90))
//                        .splineTo(new Vector2d(18, -38), Math.toRadians(60))
//                        .build());
// Park