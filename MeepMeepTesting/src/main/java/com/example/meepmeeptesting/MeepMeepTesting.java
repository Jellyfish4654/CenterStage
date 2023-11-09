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

        RoadRunnerBotEntity RED_PURPLE_LEFT = new DefaultBotBuilder(meepMeep)
                .setColorScheme(new ColorSchemeBlueDark())
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .build();

        RED_PURPLE_LEFT.runAction(RED_PURPLE_LEFT.getDrive().actionBuilder(new Pose2d(7, -68, Math.toRadians(90)))
                .splineToConstantHeading(new Vector2d(7, -38), Math.toRadians(90))
                .splineToConstantHeading(new Vector2d(0, -32), Math.toRadians(90))
                .splineToConstantHeading(new Vector2d(7, -38), Math.toRadians(315))
                .splineToConstantHeading(new Vector2d(24, -36), Math.toRadians(0))
                .build());

        RoadRunnerBotEntity RED_PURPLE_CENTER = new DefaultBotBuilder(meepMeep)
                .setColorScheme(new ColorSchemeBlueDark())
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .build();

        RED_PURPLE_CENTER.runAction(RED_PURPLE_CENTER.getDrive().actionBuilder(new Pose2d(7, -68, Math.toRadians(90)))
                .splineToConstantHeading(new Vector2d(7, -48), Math.toRadians(90))
                .splineToConstantHeading(new Vector2d(17.5, -32), Math.toRadians(90))
                .splineToConstantHeading(new Vector2d(24, -36), Math.toRadians(0))
                .build());

        RoadRunnerBotEntity RED_TRAJECTORY_1 = new DefaultBotBuilder(meepMeep)
                .setConstraints(30, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .build();

        RED_TRAJECTORY_1.runAction(RED_TRAJECTORY_1.getDrive().actionBuilder(new Pose2d(23, -35, 0))
                .splineToLinearHeading(new Pose2d(43, -35, 0), 0)
                .build());

        RoadRunnerBotEntity RED_YELLOW_LEFT = new DefaultBotBuilder(meepMeep)
                .setColorScheme(new ColorSchemeBlueDark())
                .setConstraints(30, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .build();

        RED_YELLOW_LEFT.runAction(RED_YELLOW_LEFT.getDrive().actionBuilder(new Pose2d(43, -35, 0))
                .splineToLinearHeading(new Pose2d(49, -27, 0), 0)
                .build());

        RoadRunnerBotEntity RED_YELLOW_CENTER = new DefaultBotBuilder(meepMeep)
                .setColorScheme(new ColorSchemeBlueDark())
                .setConstraints(30, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .build();

        RED_YELLOW_CENTER.runAction(RED_YELLOW_CENTER.getDrive().actionBuilder(new Pose2d(43, -35, 0))
                .splineToLinearHeading(new Pose2d(49, -35, 0), 0)
                .build());

        RoadRunnerBotEntity YELLOW_RIGHT = new DefaultBotBuilder(meepMeep)
                .setColorScheme(new ColorSchemeBlueDark())
                .setConstraints(30, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .build();

        YELLOW_RIGHT.runAction(YELLOW_RIGHT.getDrive().actionBuilder(new Pose2d(43, -35, 0))
                .splineToLinearHeading(new Pose2d(49, -43, 0), 0)
                .build());

        RoadRunnerBotEntity PARK_LEFT = new DefaultBotBuilder(meepMeep)
                .setColorScheme(new ColorSchemeRedDark())
                .setConstraints(30, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .build();

        PARK_LEFT.runAction(PARK_LEFT.getDrive().actionBuilder(new Pose2d(43, -35, 0))
                .splineToConstantHeading(new Vector2d(50, -10), 20)
                .splineToLinearHeading(new Pose2d(60, -10, 0), 0)
                .build());

        RoadRunnerBotEntity RED_PARK_RIGHT = new DefaultBotBuilder(meepMeep)
                .setColorScheme(new ColorSchemeRedDark())
                .setConstraints(30, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .build();

        RED_PARK_RIGHT.runAction(RED_PARK_RIGHT.getDrive().actionBuilder(new Pose2d(43, -35, 0))
                .splineToConstantHeading(new Vector2d(50, -59), 25)
                .splineToLinearHeading(new Pose2d(60, -59, 0), 0)
                .build());

        meepMeep.setBackground(MeepMeep.Background.FIELD_CENTERSTAGE_OFFICIAL)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(RED_PURPLE_LEFT)
                .addEntity(RED_PURPLE_CENTER)
                .addEntity(RED_TRAJECTORY_1)
                .addEntity(RED_YELLOW_LEFT)
                .addEntity(RED_YELLOW_CENTER)
                .addEntity(YELLOW_RIGHT)
                .addEntity(PARK_LEFT)
                .addEntity(RED_PARK_RIGHT)
                .start();
    }
}