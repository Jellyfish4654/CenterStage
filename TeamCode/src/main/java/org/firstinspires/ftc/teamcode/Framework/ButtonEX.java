package org.firstinspires.ftc.teamcode.Framework;

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

    public boolean isPressed() {
        return !lastState && currState;
    }

    public enum Gamepad1EX {
        A(() -> gamepad1.a),
        B(() -> gamepad1.b);

        private final ButtonEX ButtonEX;

        Gamepad1EX(BooleanSupplier buttonStateSupplier) {
            this.ButtonEX = new ButtonEX(buttonStateSupplier);
        }

        public boolean isPressed() {
            return ButtonEX.isPressed();
        }

        public static void updateAll() {
            for (Gamepad1EX button : values()) {
                button.ButtonEX.update();
            }
        }
    }

    public enum Gamepad2EX {
        A(() -> gamepad2.a),
        B(() -> gamepad2.b);

        private final ButtonEX ButtonEX;

        Gamepad2EX(BooleanSupplier buttonStateSupplier) {
            this.ButtonEX = new ButtonEX(buttonStateSupplier);
        }

        public boolean isPressed() {
            return ButtonEX.isPressed();
        }

        public static void updateAll() {
            for (Gamepad2EX button : values()) {
                button.ButtonEX.update();
            }
        }
    }
}
