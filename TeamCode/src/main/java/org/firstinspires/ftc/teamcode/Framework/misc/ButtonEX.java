package org.firstinspires.ftc.teamcode.Framework.misc;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.gamepad1;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.gamepad2;

import java.util.function.BooleanSupplier;

public class ButtonEX {
    private boolean lastState;
    private boolean currState;
    private final BooleanSupplier buttonState;

    public ButtonEX(BooleanSupplier buttonValue) {
        buttonState = buttonValue;
        currState = buttonState.getAsBoolean();
        lastState = currState;
    }

    public void update() {
        lastState = currState;
        currState = buttonState.getAsBoolean();
    }

    public boolean risingEdge() {
        return !lastState && currState;
    }

    public boolean fallingEdge() {
        return lastState && !currState;
    }

    public enum Gamepad1EX {
        A(() -> gamepad1.a),
        B(() -> gamepad1.b),
        X(() -> gamepad1.x),
        Y(() -> gamepad1.y),
        DPAD_DOWN(() -> gamepad1.dpad_down),
        DPAD_LEFT(() -> gamepad1.dpad_left),
        DPAD_RIGHT(() -> gamepad1.dpad_right),
        DPAD_UP(() -> gamepad1.dpad_up),
        LEFT_BUMPER(() -> gamepad1.left_bumper),
        RIGHT_BUMPER(() -> gamepad1.right_bumper),
        START(() -> gamepad1.start),
        BACK(() -> gamepad1.back),
        GUIDE(() -> gamepad1.guide);

        private final ButtonEX buttonEX;

        Gamepad1EX(BooleanSupplier buttonStateSupplier) {
            this.buttonEX = new ButtonEX(buttonStateSupplier);
        }

        public boolean risingEdge() {
            return buttonEX.risingEdge();
        }

        public boolean fallingEdge() {
            return buttonEX.fallingEdge();
        }

        public static void updateAll() {
            for (Gamepad1EX button : values()) {
                button.buttonEX.update();
            }
        }
    }

    public enum Gamepad2EX {
        A(() -> gamepad2.a),
        B(() -> gamepad2.b),
        X(() -> gamepad2.x),
        Y(() -> gamepad2.y),
        DPAD_DOWN(() -> gamepad2.dpad_down),
        DPAD_LEFT(() -> gamepad2.dpad_left),
        DPAD_RIGHT(() -> gamepad2.dpad_right),
        DPAD_UP(() -> gamepad2.dpad_up),
        LEFT_BUMPER(() -> gamepad2.left_bumper),
        RIGHT_BUMPER(() -> gamepad2.right_bumper),
        START(() -> gamepad2.start),
        BACK(() -> gamepad2.back),
        GUIDE(() -> gamepad2.guide);

        private final ButtonEX buttonEX;

        Gamepad2EX(BooleanSupplier buttonStateSupplier) {
            this.buttonEX = new ButtonEX(buttonStateSupplier);
        }

        public boolean risingEdge() {
            return buttonEX.risingEdge();
        }

        public boolean fallingEdge() {
            return buttonEX.fallingEdge();
        }

        public static void updateAll() {
            for (Gamepad2EX button : values()) {
                button.buttonEX.update();
            }
        }
    }
}
