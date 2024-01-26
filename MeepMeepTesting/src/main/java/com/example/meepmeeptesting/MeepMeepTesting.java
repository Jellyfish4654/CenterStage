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
        RoadRunnerBotEntity RED_LEFT_PURPLE = new DefaultBotBuilder(meepMeep)
                .setColorScheme(new ColorSchemeRedDark())
                .setConstraints(60, 60, Math.toRadians(90), Math.toRadians(90), 18)
                .build();
        RoadRunnerBotEntity BLUE_RIGHT_PURPLE = new DefaultBotBuilder(meepMeep)
                .setColorScheme(new ColorSchemeBlueDark())
                .setConstraints(60, 60, Math.toRadians(90), Math.toRadians(90), 18)
                .build();
        RoadRunnerBotEntity BLUE_LEFT_PURPLE = new DefaultBotBuilder(meepMeep)
                .setColorScheme(new ColorSchemeBlueDark())
                .setConstraints(60,60, Math.toRadians(90), Math.toRadians(90),18)
                .build();

        RED_RIGHT_PURPLE.runAction(RED_RIGHT_PURPLE.getDrive().actionBuilder(new Pose2d(15, -60, Math.toRadians(90)))
//                .splineTo(new Vector2d(15, -50), Math.toRadians(90))
//                .splineTo(new Vector2d(5, -36), Math.toRadians(135))
//                .splineToConstantHeading(new Vector2d(5+(4*Math.cos(Math.toRadians(315))), -36+(4*Math.sin(Math.toRadians(315))) ), Math.toRadians(315))
//                .splineToSplineHeading(new Pose2d(40, -36, Math.toRadians(0)), Math.toRadians(0))
                .splineTo(new Vector2d(15, -33), Math.toRadians(90))
                .splineToConstantHeading(new Vector2d(15, -36), Math.toRadians(270))
                .splineToSplineHeading(new Pose2d(40, -36, Math.toRadians(0)), Math.toRadians(0))
                .build());

        RED_LEFT_PURPLE.runAction(RED_LEFT_PURPLE.getDrive().actionBuilder(new Pose2d(-39, -60, Math.toRadians(90)))
                .splineTo(new Vector2d(-39, -50), Math.toRadians(90))
                .splineTo(new Vector2d(-30, -36), Math.toRadians(45))
                .splineToConstantHeading(new Vector2d(-30+(4*Math.cos(Math.toRadians(225))), -36+(4*Math.sin(Math.toRadians(225))) ), Math.toRadians(225))
                .splineToConstantHeading(new Vector2d(-30+(4*Math.cos(Math.toRadians(225)))+(6*Math.cos(Math.toRadians(135))), -36+(4*Math.sin(Math.toRadians(225)))+(6*Math.sin(Math.toRadians(135))) ), Math.toRadians(115.82))
                .splineToConstantHeading(new Vector2d(-41, -17.5 ), Math.toRadians(115.82))
                .splineToSplineHeading(new Pose2d(-48, -12 , Math.toRadians(0)), Math.toRadians(180))
                .build());

//       BLUE_RIGHT_PURPLE.runAction(BLUE_RIGHT_PURPLE.getDrive().actionBuilder(new Pose2d(15, 60, Math.toRadians(270)))
//                .splineTo(new Vector2d(15, 50), Math.toRadians(270))
//                .splineTo(new Vector2d(7, 36), Math.toRadians(225))
//                .splineToConstantHeading(new Vector2d(7+(4*Math.cos(Math.toRadians(45))), 36+(4*Math.sin(Math.toRadians(45))) ), Math.toRadians(45))
//                .splineToSplineHeading(new Pose2d(40, 36, Math.toRadians(0)), Math.toRadians(0))
//                .build());

       BLUE_LEFT_PURPLE.runAction(BLUE_LEFT_PURPLE.getDrive().actionBuilder(new Pose2d(-39,60,Math.toRadians(270)))
               .splineTo(new Vector2d(-39, 47), Math.toRadians(270))
               .splineTo(new Vector2d(-32, 36), Math.toRadians(315))
               .splineToConstantHeading(new Vector2d(-32+(4*Math.cos(Math.toRadians(135))), 36+(4*Math.sin(Math.toRadians(135))) ), Math.toRadians(135))
               .splineToConstantHeading(new Vector2d(-32+(4*Math.cos(Math.toRadians(135)))+(6*Math.cos(Math.toRadians(315))), 36+(4*Math.sin(Math.toRadians(135)))+(6*Math.sin(Math.toRadians(315))) ), Math.toRadians(115.82+180))
               .splineToConstantHeading(new Vector2d(-41, 17.5 ), Math.toRadians(115.82+180))
               .splineToSplineHeading(new Pose2d(-48, 12 , Math.toRadians(0)), Math.toRadians(180))
               .build());

        BLUE_RIGHT_PURPLE.runAction(BLUE_RIGHT_PURPLE.getDrive().actionBuilder(new Pose2d(15, 60, Math.toRadians(270)))
                .splineTo(new Vector2d(15, 33), Math.toRadians(270))
                .splineToConstantHeading(new Vector2d(15, 45), Math.toRadians(270))
                .splineToSplineHeading(new Pose2d(40, 36, Math.toRadians(0)), Math.toRadians(0))
                .build());




        meepMeep.setBackground(MeepMeep.Background.FIELD_CENTERSTAGE_OFFICIAL)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(RED_RIGHT_PURPLE)
                .addEntity(RED_LEFT_PURPLE)
                .addEntity(BLUE_RIGHT_PURPLE)
                .addEntity(BLUE_LEFT_PURPLE)
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