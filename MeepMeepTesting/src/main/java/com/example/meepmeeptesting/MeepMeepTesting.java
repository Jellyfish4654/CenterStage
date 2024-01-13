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
//        RED_RIGHT_PURPLE.runAction(RED_RIGHT_PURPLE.getDrive().actionBuilder(new Pose2d(15, -60, Math.toRadians(90)))
//                .splineTo(new Vector2d(15, -32), Math.toRadians(90))
//                .splineToConstantHeading(new Vector2d(15, -60), Math.toRadians(0))
//                .lineToX(60)
//                .build());
//        park
        RED_RIGHT_PURPLE.runAction(RED_RIGHT_PURPLE.getDrive().actionBuilder(new Pose2d(18, -38, Math.toRadians(60)))
                .splineToConstantHeading(new Vector2d(15, -46), Math.toRadians(90))
                .splineToLinearHeading(new Pose2d(20, -60, 0), Math.toRadians(0))
                .lineToX(60)
                .build());

//        RED_RIGHT_PURPLE.runAction(RED_RIGHT_PURPLE.getDrive().actionBuilder(new Pose2d(46.3, -12, Math.toRadians(0)))
//                                            .splineToConstantHeading(new Vector2d(59, -12), Math.toRadians(0))
//                                            .build());

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