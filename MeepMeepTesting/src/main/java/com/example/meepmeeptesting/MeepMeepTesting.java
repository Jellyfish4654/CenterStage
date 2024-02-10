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
        RoadRunnerBotEntity RED_TRAJ = new DefaultBotBuilder(meepMeep)
                .setColorScheme(new ColorSchemeBlueDark())
                .setConstraints(60, 60, Math.toRadians(90), Math.toRadians(90), 18)
                .build();
        RoadRunnerBotEntity RED_FAR1 = new DefaultBotBuilder(meepMeep)
                .setColorScheme(new ColorSchemeBlueDark())
                .setConstraints(60, 60, Math.toRadians(90), Math.toRadians(90), 18)
                .build();
        RoadRunnerBotEntity BLUE_LEFT_YELLOW = new DefaultBotBuilder(meepMeep)
                .setColorScheme(new ColorSchemeBlueDark())
                .setConstraints(60,60, Math.toRadians(90), Math.toRadians(90),18)
                .build();
        RoadRunnerBotEntity BLUE_TRAJ = new DefaultBotBuilder(meepMeep)
                .setColorScheme(new ColorSchemeBlueDark())
                .setConstraints(60, 60, Math.toRadians(90), Math.toRadians(90), 18)
                .build();
        RoadRunnerBotEntity BLUE_FAR1 = new DefaultBotBuilder(meepMeep)
                .setColorScheme(new ColorSchemeBlueDark())
                .setConstraints(60, 60, Math.toRadians(90), Math.toRadians(90), 18)
                .build();
        RoadRunnerBotEntity RED_CLOSE_YELLOW = new DefaultBotBuilder(meepMeep)
                .setColorScheme(new ColorSchemeBlueDark())
                .setConstraints(60, 60, Math.toRadians(90), Math.toRadians(90), 18)
                .build();

//        RED_CLOSE_YELLOW.runAction(RED_CLOSE_YELLOW.getDrive().actionBuilder(new Pose2d(40,-36,Math.toRadians(0)))
//                .splineToConstantHeading(new Vector2d(48, -29), Math.toRadians(0))
//                .build());
//        RED_CLOSE_YELLOW.runAction(RED_CLOSE_YELLOW.getDrive().actionBuilder(new Pose2d(40,36,Math.toRadians(0)))
//                .splineToConstantHeading(new Vector2d(48, 29), Math.toRadians(0))
//                .build());
//        RED_FAR1.runAction(RED_FAR1.getDrive().actionBuilder(new Pose2d(-70.5 + (5.5 + 24), 70.5 - 10.375, Math.toRadians(270)))
//              .splineToConstantHeading(new Vector2d(32, -10), Math.toRadians(0))
//                .splineToConstantHeading(new Vector2d(36, -24), Math.toRadians(270))
//                .splineToConstantHeading(new Vector2d(36, -38), Math.toRadians(0))
//                .splineToConstantHeading(new Vector2d(40.75, -49), Math.toRadians(0))

//                .splineToConstantHeading(new Vector2d(32, -10), Math.toRadians(0))
//                .splineToConstantHeading(new Vector2d(36, -27), Math.toRadians(270))
//                .splineToConstantHeading(new Vector2d(36, -41), Math.toRadians(0))
//                .splineToConstantHeading(new Vector2d(40.75, -52), Math.toRadians(0))

//        RED_FAR1.runAction(RED_FAR1.getDrive().actionBuilder(new Pose2d(-70.5 + (5.5 + 24), 70.5 - 10.375, Math.toRadians(270)))
//                .splineTo(new Vector2d(-39, 50), Math.toRadians(270))
//                .splineTo(new Vector2d(-46, 36), Math.toRadians(225))
//                .splineToConstantHeading(new Vector2d(-30 + (2 * Math.cos(Math.toRadians(135))), 36 + (2 * Math.sin(Math.toRadians(135)))), Math.toRadians(135))
//                .splineToConstantHeading(new Vector2d(-30 + (2 * Math.cos(Math.toRadians(135))) + (6 * Math.cos(Math.toRadians(225))), 36 + (2 * Math.sin(Math.toRadians(135))) + (6 * Math.sin(Math.toRadians(225)))), Math.toRadians(244.18)) // Adjusting angle to 244.18 degrees
//                .splineToConstantHeading(new Vector2d(-41, 17.5), Math.toRadians(244.18))
//                .splineToSplineHeading(new Pose2d(-48, 12, Math.toRadians(0)), Math.toRadians(180))
//                .build());

//        RED_FAR1.runAction(RED_FAR1.getDrive().actionBuilder(new Pose2d(-70.5 + (5.5 + 24), 70.5 - 10.375, Math.toRadians(270)))
//                .splineTo(new Vector2d(-39, 48), Math.toRadians(270))
//                .splineTo(new Vector2d(-40, 40), Math.toRadians(315))
//                .splineToConstantHeading(new Vector2d(-40 + (8 * Math.cos(Math.toRadians(135))), 40 + (8 * Math.sin(Math.toRadians(135)))), Math.toRadians(135))
//                .splineToConstantHeading(new Vector2d(-40 + (8 * Math.cos(Math.toRadians(135))), 30 + (8 * Math.sin(Math.toRadians(135)))), Math.toRadians(244.18)) // Adjusting angle to 244.18 degrees
//                .splineToConstantHeading(new Vector2d(-32, 24), Math.toRadians(244.18))
//                .splineToSplineHeading(new Pose2d(-48, 12, Math.toRadians(0)), Math.toRadians(180))
//                .build());
        RED_LEFT_PURPLE.runAction(RED_LEFT_PURPLE.getDrive().actionBuilder(new Pose2d(-70.5 + (5.5+24), -70.5+10.375, Math.toRadians(90)))
                .splineTo(new Vector2d(-39, -48), Math.toRadians(90))
                .splineTo(new Vector2d(-40, -40), Math.toRadians(135))
                .splineToConstantHeading(new Vector2d(-40 + (8 * Math.cos(Math.toRadians(315))), -40 + (8 * Math.sin(Math.toRadians(315)))), Math.toRadians(315))
                .splineToConstantHeading(new Vector2d(-40 + (8 * Math.cos(Math.toRadians(315))), -30 + (8 * Math.sin(Math.toRadians(315)))), Math.toRadians(90))
                .splineToConstantHeading(new Vector2d(-32, -24), Math.toRadians(135))
                .splineToSplineHeading(new Pose2d(-48, -12, Math.toRadians(0)), Math.toRadians(180))
                .build());
//        BLUE_LEFT_PURPLE.runAction(BLUE_RIGHT_PURPLE.getDrive().actionBuilder(new Pose2d(-39, 60, Math.toRadians(90)))
//                .splineTo(new Vector2d(-39, 50), Math.toRadians(270))
//                .splineTo(new Vector2d(-30, 36), Math.toRadians(315))
//                .splineToConstantHeading(new Vector2d(-30 + (4 * Math.cos(Math.toRadians(135))), 36 + (4 * Math.sin(Math.toRadians(135)))), Math.toRadians(135))
//                .splineToConstantHeading(new Vector2d(-30 + (4 * Math.cos(Math.toRadians(135))) + (6 * Math.cos(Math.toRadians(225))), 36 + (4 * Math.sin(Math.toRadians(135))) + (6 * Math.sin(Math.toRadians(225)))), Math.toRadians(244.18)) // Adjusting angle to 244.18 degrees
//                .splineToConstantHeading(new Vector2d(-41, 17.5), Math.toRadians(244.18))
//                .splineToSplineHeading(new Pose2d(-48, 12, Math.toRadians(0)), Math.toRadians(180))
//                .build());
//        BLUE_FAR1.runAction(BLUE_FAR1.getDrive().actionBuilder(new Pose2d(15, 10, Math.toRadians(0)))
//                .splineToConstantHeading(new Vector2d(32, 10), Math.toRadians(0))
//                .splineToConstantHeading(new Vector2d(36, 24), Math.toRadians(90))
//                .splineToConstantHeading(new Vector2d(38, 36), Math.toRadians(0))
//                .splineToConstantHeading(new Vector2d(48, 35), Math.toRadians(0))
//                .build());
//        BLUE_TRAJ.runAction(BLUE_TRAJ.getDrive().actionBuilder(new Pose2d(-48, 12, Math.toRadians(0)))
//                .splineToConstantHeading(new Vector2d(-12, 10), Math.toRadians(0))
//                .splineToConstantHeading(new Vector2d(15, 10), Math.toRadians(0))
//                .build());
//
//        RED_TRAJ.runAction(RED_TRAJ.getDrive().actionBuilder(new Pose2d(-48, -12, Math.toRadians(0)))
//                .splineToConstantHeading(new Vector2d(-12, -10), Math.toRadians(0))
//                .splineToConstantHeading(new Vector2d(15, -10), Math.toRadians(0))
//                .build());
//

//
//        BLUE_RIGHT_PURPLE.runAction(BLUE_RIGHT_PURPLE.getDrive().actionBuilder(new Pose2d(15, 60, Math.toRadians(270)))
//                .splineTo(new Vector2d(15, 48), Math.toRadians(270))
//                .splineTo(new Vector2d(18, 38), Math.toRadians(315))
//                .splineToConstantHeading(new Vector2d(18 + (4 * Math.cos(Math.toRadians(135))), 38 + (4 * Math.sin(Math.toRadians(135)))), Math.toRadians(135))
//                .splineToSplineHeading(new Pose2d(28, 44.17 + (4 * Math.sin(Math.toRadians(135))), Math.toRadians(0)), Math.toRadians(0))
//                .splineToConstantHeading(new Vector2d(40, 36), Math.toRadians(0))
//                .build());

//        RED_LEFT_PURPLE.runAction(RED_LEFT_PURPLE.getDrive().actionBuilder(new Pose2d(-70.5 + (5.5+24), -70.5+10.375, Math.toRadians(90)))
//                .splineTo(new Vector2d(-39, -50), Math.toRadians(90))
//                .splineTo(new Vector2d(-30, -36), Math.toRadians(45))
//                .splineToConstantHeading(new Vector2d(-30 + (2 * Math.cos(Math.toRadians(225))), -36 + (2 * Math.sin(Math.toRadians(225)))), Math.toRadians(225))
//                .splineToConstantHeading(new Vector2d(-30 + (2 * Math.cos(Math.toRadians(225))) + (6 * Math.cos(Math.toRadians(135))), -36 + (2 * Math.sin(Math.toRadians(225))) + (6 * Math.sin(Math.toRadians(135)))), Math.toRadians(115.82))
//                .splineToConstantHeading(new Vector2d(-41, -17.5), Math.toRadians(115.82))
//                .splineToSplineHeading(new Pose2d(-48, -12, Math.toRadians(0)), Math.toRadians(180))
//                .build());
//
//       BLUE_LEFT_PURPLE.runAction(BLUE_LEFT_PURPLE.getDrive().actionBuilder(new Pose2d(-39,60,Math.toRadians(270)))
//               .splineTo(new Vector2d(-34.5, 31), Math.toRadians(300))
//               .splineToConstantHeading(new Vector2d(-34.5 + (4 * Math.cos(Math.toRadians(120))), 31 + (4 * Math.sin(Math.toRadians(120)))), Math.toRadians(120))
//               .splineToConstantHeading(new Vector2d(-34.5 + (4 * Math.cos(Math.toRadians(120))) + (6 * Math.cos(Math.toRadians(210))), 31 + (4 * Math.sin(Math.toRadians(120))) + (6 * Math.sin(Math.toRadians(210)))), Math.toRadians(235))
//               .splineToSplineHeading(new Pose2d(-48, 12, Math.toRadians(0)), Math.toRadians(180))
//               .build());
//        RED_FAR1.runAction(RED_FAR1.getDrive().actionBuilder(new Pose2d(15, -10, Math.toRadians(0)))
//                .splineToConstantHeading(new Vector2d(32, -10), Math.toRadians(0))
//                .splineToConstantHeading(new Vector2d(36, -24), Math.toRadians(270))
//                .splineToConstantHeading(new Vector2d(38, -36), Math.toRadians(0))
//                .splineToConstantHeading(new Vector2d(48, -29), Math.toRadians(0))
//               .build());
//
//        RED_FAR1.runAction(RED_FAR1.getDrive().actionBuilder(new Pose2d(40.75, -58, Math.toRadians(0)))
//                .splineToConstantHeading(new Vector2d(46, -41), Math.toRadians(180))
//                .splineToConstantHeading(new Vector2d(46, -10), Math.toRadians(0))
//                .splineToConstantHeading(new Vector2d(58, -10), Math.toRadians(0))
//                .build());
        meepMeep.setBackground(MeepMeep.Background.FIELD_CENTERSTAGE_OFFICIAL)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
//                .addEntity(RED_RIGHT_PURPLE)
                .addEntity(RED_LEFT_PURPLE)
//                .addEntity(RED_TRAJ)
//                .addEntity(RED_FAR1)
//                .addEntity(BLUE_LEFT_PURPLE)
//                .addEntity(BLUE_TRAJ)
//                .addEntity(BLUE_FAR1)
//                .addEntity(BLUE_RIGHT_PURPLE)
//                .addEntity(BLUE_LEFT_PURPLE)
//                .addEntity(RED_CLOSE_YELLOW)
                .start();
    }
}