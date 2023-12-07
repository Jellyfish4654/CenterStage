package org.firstinspires.ftc.teamcode.Framework.misc;

import com.qualcomm.robotcore.hardware.Gamepad;

import java.util.ArrayList;
import java.util.EnumMap;

public class GamepadEX {

    private Gamepad[] gamepads;

    public enum Button {
        Y, X, A, B, LEFT_BUMPER, RIGHT_BUMPER, BACK, START,
        DPAD_UP, DPAD_DOWN, DPAD_LEFT, DPAD_RIGHT,
        LEFT_STICK_BUTTON, RIGHT_STICK_BUTTON,
        TRIANGLE, SQUARE, CROSS, CIRCLE, SHARE, OPTIONS
    }

    public enum Trigger {
        LEFT_TRIGGER, RIGHT_TRIGGER
    }

    public class ButtonReader
    {
        private boolean oldState = false, newState = false;
        private GamepadEX gamepadEX;
        private Button button;

        public ButtonReader(Button button)
        {
            this.button = button;
            gamepadEX = GamepadEX.this;
        }

        public void read()
        {
            oldState = newState;
            newState = gamepadEX.getButton(button);
        }

        public boolean isPressed() { return newState; }
        public boolean isReleased() { return (!newState); }

        public boolean justPressed() { return ((!oldState) && (newState)); }
        public boolean justReleased() { return ((oldState) && (!newState)); }
        public boolean justChanged() { return (oldState != newState); }
    }

    private final EnumMap<Button, ButtonReader> buttonReaders = new EnumMap<>(Button.class);


    public GamepadEX(Gamepad... gamepads) {
        ArrayList<Gamepad> gamepadArrayList = new ArrayList<>();
        for (Gamepad gamepad : gamepads) {
            if (gamepad != null) {
                gamepadArrayList.add(gamepad);
            }
        }
        this.gamepads = gamepadArrayList.toArray(new Gamepad[0]);
        for (Button button : Button.values()) {
            buttonReaders.put(button, this.new ButtonReader(button));
        }
    }

    private static boolean getButton(Button button, Gamepad gamepad) {
        boolean buttonValue = false;
        switch (button) {
            case Y:
            case TRIANGLE:
                buttonValue = gamepad.y || gamepad.triangle;
                break;
            case X:
            case SQUARE:
                buttonValue = gamepad.x || gamepad.square;
                break;
            case A:
            case CROSS:
                buttonValue = gamepad.a || gamepad.cross;
                break;
            case B:
            case CIRCLE:
                buttonValue = gamepad.b || gamepad.circle;
                break;
            case LEFT_BUMPER:
                buttonValue = gamepad.left_bumper;
                break;
            case RIGHT_BUMPER:
                buttonValue = gamepad.right_bumper;
                break;
            case DPAD_UP:
                buttonValue = gamepad.dpad_up;
                break;
            case DPAD_DOWN:
                buttonValue = gamepad.dpad_down;
                break;
            case DPAD_LEFT:
                buttonValue = gamepad.dpad_left;
                break;
            case DPAD_RIGHT:
                buttonValue = gamepad.dpad_right;
                break;
            case BACK:
            case SHARE:
                buttonValue = gamepad.back || gamepad.share;
                break;
            case START:
            case OPTIONS:
                buttonValue = gamepad.start || gamepad.options;
                break;
            case LEFT_STICK_BUTTON:
                buttonValue = gamepad.left_stick_button;
                break;
            case RIGHT_STICK_BUTTON:
                buttonValue = gamepad.right_stick_button;
                break;
            default:
                break;
        }
        return buttonValue;
    }

    public boolean getButton(Button button)
    {
        boolean buttonValue = false;
        for (Gamepad gamepad : gamepads) {
            buttonValue = (buttonValue || getButton(button, gamepad));
        }
        return buttonValue;
    }


    private static double getTrigger(Trigger trigger, Gamepad gamepad) {
        double triggerValue = 0.0;
        switch (trigger) {
            case LEFT_TRIGGER:
                triggerValue = gamepad.left_trigger;
                break;
            case RIGHT_TRIGGER:
                triggerValue = gamepad.right_trigger;
                break;
            default:
                break;
        }
        return triggerValue;
    }

    public double getTrigger(Trigger trigger)
    {
        double triggerValue = 0.0;
        for (Gamepad gamepad : gamepads) {
            triggerValue = Math.max(triggerValue, getTrigger(trigger, gamepad));
        }
        return triggerValue;
    }


    private static double getLeftY(Gamepad gamepad) {
        return -gamepad.left_stick_y;
    }
    public double getLeftY() {
        double leftY = 0.0;
        for (Gamepad gamepad : gamepads) {
            leftY += getLeftY(gamepad);
        }
        if (gamepads.length > 0) {
            leftY /= gamepads.length;
        }
        return leftY;
    }

    private static double getRightY(Gamepad gamepad) {
        return -gamepad.right_stick_y;
    }
    public double getRightY() {
        double rightY = 0.0;
        for (Gamepad gamepad : gamepads) {
            rightY += getRightY(gamepad);
        }
        if (gamepads.length > 0) {
            rightY /= gamepads.length;
        }
        return rightY;
    }

    private static double getLeftX(Gamepad gamepad) {
        return gamepad.left_stick_x;
    }
    public double getLeftX() {
        double leftX = 0.0;
        for (Gamepad gamepad : gamepads) {
            leftX += getLeftX(gamepad);
        }
        if (gamepads.length > 0) {
            leftX /= gamepads.length;
        }
        return leftX;
    }

    private static double getRightX(Gamepad gamepad) {
        return gamepad.right_stick_x;
    }
    public double getRightX() {
        double rightX = 0.0;
        for (Gamepad gamepad : gamepads) {
            rightX += getRightX(gamepad);
        }
        if (gamepads.length > 0) {
            rightX /= gamepads.length;
        }
        return rightX;
    }


    public void readButtons()
    {
        for (Button button : Button.values()) {
            buttonReaders.get(button).read();
        }
    }

    public boolean isPressed(Button button) {
        return buttonReaders.get(button).isPressed();
    }

    public boolean isReleased(Button button) {
        return buttonReaders.get(button).isReleased();
    }

    public boolean justPressed(Button button) {
        return buttonReaders.get(button).justPressed();
    }

    public boolean justReleased(Button button) {
        return buttonReaders.get(button).justReleased();
    }

    public boolean justChanged(Button button) {
        return buttonReaders.get(button).justChanged();
    }

}