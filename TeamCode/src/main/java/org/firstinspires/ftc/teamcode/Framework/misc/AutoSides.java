package org.firstinspires.ftc.teamcode.Framework.misc;

public class AutoSides {

    public enum Position {
        LEFT, CENTER, RIGHT
    }

    public enum Color {
        BLUE, RED
    }
    private static Color currentColor = Color.RED;
    private static Position currentPosition = Position.LEFT;

    public static void setColor(Color color) {
        currentColor = color;
    }

    public static void setPosition(Position position) {
        currentPosition = position;
    }

    public static Color getColor() {
        return currentColor;
    }

    public static Position getPosition() {
        return currentPosition;
    }
}
