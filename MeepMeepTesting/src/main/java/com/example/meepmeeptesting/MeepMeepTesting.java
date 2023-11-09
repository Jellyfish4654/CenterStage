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

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(30, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .build();

        myBot.runAction(myBot.getDrive().actionBuilder(new Pose2d(23, -35, 0))
                .splineToLinearHeading(new Pose2d(43, -35, 0), 0)
                .build());
        
        RoadRunnerBotEntity myLeftmoreBot = new DefaultBotBuilder(meepMeep)
                .setColorScheme(new ColorSchemeRedDark())
                .setConstraints(30, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .build();
    
        myLeftmoreBot.runAction(myLeftmoreBot.getDrive().actionBuilder(new Pose2d(43, -35, 0))
                .splineToConstantHeading(new Vector2d(50, -10), 20)
                .splineToLinearHeading(new Pose2d(60, -10, 0), 0)
                .build());
    
        RoadRunnerBotEntity myLeftlessBot = new DefaultBotBuilder(meepMeep)
                .setColorScheme(new ColorSchemeRedDark())
                .setConstraints(30, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .build();
        
        myLeftlessBot.runAction(myLeftlessBot.getDrive().actionBuilder(new Pose2d(43, -35, 0))
                .splineToConstantHeading(new Vector2d(50, -59), 25)
                .splineToLinearHeading(new Pose2d(60, -59, 0), 0)
                .build());
    
        RoadRunnerBotEntity myLeftKindaNotReallyBot = new DefaultBotBuilder(meepMeep)
                .setColorScheme(new ColorSchemeRedDark())
                .setConstraints(30, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .build();
    
        myLeftKindaNotReallyBot.runAction(myLeftKindaNotReallyBot.getDrive().actionBuilder(new Pose2d(43, -35, 0))
                .splineToLinearHeading(new Pose2d(49, -35, 0), 0)
                .build());
    
        RoadRunnerBotEntity myLeftKindaBotYeahWhateverYouWant = new DefaultBotBuilder(meepMeep)
                .setColorScheme(new ColorSchemeRedDark())
                .setConstraints(30, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .build();
    
        myLeftKindaBotYeahWhateverYouWant.runAction(myLeftKindaBotYeahWhateverYouWant.getDrive().actionBuilder(new Pose2d(43, -35, 0))
                .splineToLinearHeading(new Pose2d(49, -27, 0), 0)
                .build());
    
        RoadRunnerBotEntity myRightKindaBotYeahWhateverYouWant = new DefaultBotBuilder(meepMeep)
                .setColorScheme(new ColorSchemeRedDark())
                .setConstraints(30, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .build();
    
        myRightKindaBotYeahWhateverYouWant.runAction(myRightKindaBotYeahWhateverYouWant.getDrive().actionBuilder(new Pose2d(43, -35, 0))
                .splineToLinearHeading(new Pose2d(49, -43, 0), 0)
                .build());

        RoadRunnerBotEntity bot1 = new DefaultBotBuilder(meepMeep)
                // We set this bot to be red
                .setColorScheme(new ColorSchemeBlueDark())
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .build();

        bot1.runAction(bot1.getDrive().actionBuilder(new Pose2d(7, -68, Math.toRadians(90)))
                .splineTo(new Vector2d(17.5, -32), Math.toRadians(90))
                .splineToConstantHeading(new Vector2d(24, -36), Math.toRadians(0))
                .build());

        meepMeep.setBackground(MeepMeep.Background.FIELD_CENTERSTAGE_OFFICIAL)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .addEntity(myLeftmoreBot)
                .addEntity(myLeftlessBot)
                .addEntity(myLeftKindaNotReallyBot)
                .addEntity(myLeftKindaBotYeahWhateverYouWant)
                .addEntity(myRightKindaBotYeahWhateverYouWant)
                .addEntity(bot1)
                .start();
    }
}