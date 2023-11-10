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

        RoadRunnerBotEntity RED_RIGHT_PURPLE_LEFT = new DefaultBotBuilder(meepMeep)
                .setColorScheme(new ColorSchemeBlueDark())
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .build();

        RED_RIGHT_PURPLE_LEFT.runAction(RED_RIGHT_PURPLE_LEFT.getDrive().actionBuilder(new Pose2d(7, -68, Math.toRadians(90)))
                .splineToConstantHeading(new Vector2d(7, -42), Math.toRadians(90))
                .splineToConstantHeading(new Vector2d(0, -32), Math.toRadians(90))
                .splineToConstantHeading(new Vector2d(7, -42), Math.toRadians(315))
                .splineToConstantHeading(new Vector2d(24, -42), Math.toRadians(0))
                .build());

        RoadRunnerBotEntity RED_RIGHT_PURPLE_CENTER = new DefaultBotBuilder(meepMeep)
                .setColorScheme(new ColorSchemeBlueDark())
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .build();

        RED_RIGHT_PURPLE_CENTER.runAction(RED_RIGHT_PURPLE_CENTER.getDrive().actionBuilder(new Pose2d(7, -68, Math.toRadians(90)))
                .splineToConstantHeading(new Vector2d(7, -42), Math.toRadians(90))
                .splineToConstantHeading(new Vector2d(17.5, -32), Math.toRadians(90))
                .splineToConstantHeading(new Vector2d(24, -42), Math.toRadians(0))
                .build());
    
        RoadRunnerBotEntity RED_LEFT_PURPLE_LEFT = new DefaultBotBuilder(meepMeep)
                .setColorScheme(new ColorSchemeBlueDark())
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .build();
    
        RED_LEFT_PURPLE_LEFT.runAction(RED_LEFT_PURPLE_LEFT.getDrive().actionBuilder(new Pose2d(-41, -68, Math.toRadians(90)))
                .splineToConstantHeading(new Vector2d(-41, -42), Math.toRadians(90))
                .splineToConstantHeading(new Vector2d(0, -32), Math.toRadians(90))
                .splineToConstantHeading(new Vector2d(-41, -42), Math.toRadians(315))
                .splineToConstantHeading(new Vector2d(-24, -42), Math.toRadians(0))
                .build());
    
        RoadRunnerBotEntity RED_LEFT_PURPLE_CENTER = new DefaultBotBuilder(meepMeep)
                .setColorScheme(new ColorSchemeBlueDark())
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .build();
    
        RED_LEFT_PURPLE_CENTER.runAction(RED_LEFT_PURPLE_CENTER.getDrive().actionBuilder(new Pose2d(-41, -68, Math.toRadians(90)))
                .splineToConstantHeading(new Vector2d(-41, -42), Math.toRadians(90))
                .splineToConstantHeading(new Vector2d(-30.5, -32), Math.toRadians(90))
                .splineToConstantHeading(new Vector2d(-24, -42), Math.toRadians(0))
                .build());

        RoadRunnerBotEntity RED_TRAJECTORY_1 = new DefaultBotBuilder(meepMeep)
                .setConstraints(30, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .build();

        RED_TRAJECTORY_1.runAction(RED_TRAJECTORY_1.getDrive().actionBuilder(new Pose2d(23, -42, 0))
                .splineToLinearHeading(new Pose2d(43, -42, 0), 0)
                .build());
    
        RoadRunnerBotEntity RED_TRAJECTORY_2 = new DefaultBotBuilder(meepMeep)
                .setConstraints(30, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .build();
    
        RED_TRAJECTORY_2.runAction(RED_TRAJECTORY_2.getDrive().actionBuilder(new Pose2d(-23, -42, 0))
                .splineToLinearHeading(new Pose2d(43, -42, 0), 0)
                .build());

        RoadRunnerBotEntity RED_YELLOW_LEFT = new DefaultBotBuilder(meepMeep)
                .setColorScheme(new ColorSchemeBlueDark())
                .setConstraints(30, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .build();

        RED_YELLOW_LEFT.runAction(RED_YELLOW_LEFT.getDrive().actionBuilder(new Pose2d(43, -42, 0))
                .splineToLinearHeading(new Pose2d(49, -27, 0), 0)
                .build());

        RoadRunnerBotEntity RED_YELLOW_CENTER = new DefaultBotBuilder(meepMeep)
                .setColorScheme(new ColorSchemeBlueDark())
                .setConstraints(30, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .build();

        RED_YELLOW_CENTER.runAction(RED_YELLOW_CENTER.getDrive().actionBuilder(new Pose2d(43, -42, 0))
                .splineToLinearHeading(new Pose2d(49, -42, 0), 0)
                .build());

        RoadRunnerBotEntity RED_YELLOW_RIGHT = new DefaultBotBuilder(meepMeep)
                .setColorScheme(new ColorSchemeBlueDark())
                .setConstraints(30, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .build();

        RED_YELLOW_RIGHT.runAction(RED_YELLOW_RIGHT.getDrive().actionBuilder(new Pose2d(43, -42, 0))
                .splineToLinearHeading(new Pose2d(49, -43, 0), 0)
                .build());

        RoadRunnerBotEntity RED_PARK_LEFT = new DefaultBotBuilder(meepMeep)
                .setColorScheme(new ColorSchemeRedDark())
                .setConstraints(30, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .build();

        RED_PARK_LEFT.runAction(RED_PARK_LEFT.getDrive().actionBuilder(new Pose2d(43, -42, 0))
                .splineToConstantHeading(new Vector2d(50, -10), 20)
                .splineToLinearHeading(new Pose2d(60, -10, 0), 0)
                .build());

        RoadRunnerBotEntity RED_PARK_RIGHT = new DefaultBotBuilder(meepMeep)
                .setColorScheme(new ColorSchemeRedDark())
                .setConstraints(30, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .build();

        RED_PARK_RIGHT.runAction(RED_PARK_RIGHT.getDrive().actionBuilder(new Pose2d(43, -42, 0))
                .splineToConstantHeading(new Vector2d(50, -59), 25)
                .splineToLinearHeading(new Pose2d(60, -59, 0), 0)
                .build());
        
        RoadRunnerBotEntity BLUE_RIGHT_PURPLE_LEFT = new DefaultBotBuilder(meepMeep)
                .setColorScheme(new ColorSchemeBlueDark())
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .build();
        
        BLUE_RIGHT_PURPLE_LEFT.runAction(BLUE_RIGHT_PURPLE_LEFT.getDrive().actionBuilder(new Pose2d(7, 68, Math.toRadians(90)))
                .splineToConstantHeading(new Vector2d(7, 42), -Math.toRadians(90))
                .splineToConstantHeading(new Vector2d(0, 32), -Math.toRadians(90))
                .splineToConstantHeading(new Vector2d(7, 42), -Math.toRadians(315))
                .splineToConstantHeading(new Vector2d(24, 42), -Math.toRadians(0))
                .build());
    
        RoadRunnerBotEntity BLUE_RIGHT_PURPLE_CENTER = new DefaultBotBuilder(meepMeep)
                .setColorScheme(new ColorSchemeBlueDark())
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .build();
    
        BLUE_RIGHT_PURPLE_CENTER.runAction(BLUE_RIGHT_PURPLE_CENTER.getDrive().actionBuilder(new Pose2d(7, 68, Math.toRadians(90)))
                .splineToConstantHeading(new Vector2d(7, 42), -Math.toRadians(90))
                .splineToConstantHeading(new Vector2d(17.5, 32), -Math.toRadians(90))
                .splineToConstantHeading(new Vector2d(24, 42), -Math.toRadians(0))
                .build());
    
        RoadRunnerBotEntity BLUE_LEFT_PURPLE_LEFT = new DefaultBotBuilder(meepMeep)
                .setColorScheme(new ColorSchemeBlueDark())
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .build();
    
        BLUE_LEFT_PURPLE_LEFT.runAction(BLUE_LEFT_PURPLE_LEFT.getDrive().actionBuilder(new Pose2d(-41, 68, Math.toRadians(90)))
                .splineToConstantHeading(new Vector2d(-41, 42), -Math.toRadians(90))
                .splineToConstantHeading(new Vector2d(0, 32), -Math.toRadians(90))
                .splineToConstantHeading(new Vector2d(-41, 42), -Math.toRadians(315))
                .splineToConstantHeading(new Vector2d(-24, 42), -Math.toRadians(0))
                .build());
    
        RoadRunnerBotEntity BLUE_LEFT_PURPLE_CENTER = new DefaultBotBuilder(meepMeep)
                .setColorScheme(new ColorSchemeBlueDark())
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .build();
    
        BLUE_LEFT_PURPLE_CENTER.runAction(BLUE_LEFT_PURPLE_CENTER.getDrive().actionBuilder(new Pose2d(-41, 68, Math.toRadians(90)))
                .splineToConstantHeading(new Vector2d(-41, 42), -Math.toRadians(90))
                .splineToConstantHeading(new Vector2d(-30.5, 32), -Math.toRadians(90))
                .splineToConstantHeading(new Vector2d(-24, 42), -Math.toRadians(0))
                .build());
    
        RoadRunnerBotEntity BLUE_TRAJECTORY_1 = new DefaultBotBuilder(meepMeep)
                .setConstraints(30, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .build();
    
        BLUE_TRAJECTORY_1.runAction(BLUE_TRAJECTORY_1.getDrive().actionBuilder(new Pose2d(-23, 42, 0))
                .splineToLinearHeading(new Pose2d(43, 42, 0), 0)
                .build());
    
        RoadRunnerBotEntity BLUE_TRAJECTORY_2 = new DefaultBotBuilder(meepMeep)
                .setConstraints(30, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .build();
    
        BLUE_TRAJECTORY_2.runAction(BLUE_TRAJECTORY_2.getDrive().actionBuilder(new Pose2d(-23, 42, 0))
                .splineToLinearHeading(new Pose2d(43, 42, 0), 0)
                .build());
    
        RoadRunnerBotEntity BLUE_YELLOW_RIGHT = new DefaultBotBuilder(meepMeep)
                .setColorScheme(new ColorSchemeBlueDark())
                .setConstraints(30, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .build();
    
        BLUE_YELLOW_RIGHT.runAction(BLUE_YELLOW_RIGHT.getDrive().actionBuilder(new Pose2d(43, 42, 0))
                .splineToLinearHeading(new Pose2d(49, 27, 0), 0)
                .build());
    
        RoadRunnerBotEntity BLUE_YELLOW_CENTER = new DefaultBotBuilder(meepMeep)
                .setColorScheme(new ColorSchemeBlueDark())
                .setConstraints(30, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .build();
    
        BLUE_YELLOW_CENTER.runAction(BLUE_YELLOW_CENTER.getDrive().actionBuilder(new Pose2d(43, 42, 0))
                .splineToLinearHeading(new Pose2d(49, 42, 0), 0)
                .build());
    
        RoadRunnerBotEntity BLUE_YELLOW_LEFT = new DefaultBotBuilder(meepMeep)
                .setColorScheme(new ColorSchemeBlueDark())
                .setConstraints(30, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .build();
    
        BLUE_YELLOW_LEFT.runAction(BLUE_YELLOW_LEFT.getDrive().actionBuilder(new Pose2d(43, 42, 0))
                .splineToLinearHeading(new Pose2d(49, 43, 0), 0)
                .build());
    
        RoadRunnerBotEntity BLUE_PARK_RIGHT = new DefaultBotBuilder(meepMeep)
                .setColorScheme(new ColorSchemeRedDark())
                .setConstraints(30, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .build();
    
        BLUE_PARK_RIGHT.runAction(BLUE_PARK_RIGHT.getDrive().actionBuilder(new Pose2d(43, 42, 0))
                .splineToConstantHeading(new Vector2d(50, 10), -20)
                .splineToLinearHeading(new Pose2d(60, 10, 0), 0)
                .build());
    
        RoadRunnerBotEntity BLUE_PARK_LEFT = new DefaultBotBuilder(meepMeep)
                .setColorScheme(new ColorSchemeRedDark())
                .setConstraints(30, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .build();
    
        BLUE_PARK_LEFT.runAction(BLUE_PARK_LEFT.getDrive().actionBuilder(new Pose2d(43, 42, 0))
                .splineToConstantHeading(new Vector2d(50, 59), -25)
                .splineToLinearHeading(new Pose2d(60, 59, 0), 0)
                .build());

        meepMeep.setBackground(MeepMeep.Background.FIELD_CENTERSTAGE_OFFICIAL)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(RED_RIGHT_PURPLE_LEFT)
                .addEntity(RED_RIGHT_PURPLE_CENTER)
                .addEntity(RED_LEFT_PURPLE_LEFT)
                .addEntity(RED_LEFT_PURPLE_CENTER)
                .addEntity(RED_TRAJECTORY_1)
                .addEntity(RED_TRAJECTORY_2)
                .addEntity(RED_YELLOW_LEFT)
                .addEntity(RED_YELLOW_CENTER)
                .addEntity(RED_YELLOW_RIGHT)
                .addEntity(RED_PARK_LEFT)
                .addEntity(RED_PARK_RIGHT)
        
                .addEntity(BLUE_RIGHT_PURPLE_LEFT)
                .addEntity(BLUE_RIGHT_PURPLE_CENTER)
                .addEntity(BLUE_LEFT_PURPLE_LEFT)
                .addEntity(BLUE_LEFT_PURPLE_CENTER)
                .addEntity(BLUE_TRAJECTORY_1)
                .addEntity(BLUE_TRAJECTORY_2)
                .addEntity(BLUE_YELLOW_LEFT)
                .addEntity(BLUE_YELLOW_CENTER)
                .addEntity(BLUE_YELLOW_RIGHT)
                .addEntity(BLUE_PARK_LEFT)
                .addEntity(BLUE_PARK_RIGHT)
                .start();
    }
}