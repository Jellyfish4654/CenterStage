package org.firstinspires.ftc.teamcode.Framework.misc;

public class Sides
{

    public enum Position
    {
        LEFT, CENTER, RIGHT, UNKNOWN
    }

    public enum Color
    {
        BLUE, RED
    }

    public static Color currentColor = Color.RED;
    public static Position currentPosition = Position.UNKNOWN;

    public static void setColor(Color color)
    {
        currentColor = color;
    }

    public static void setPosition(Position position)
    {
        currentPosition = position;
    }

    public static Color getColor()
    {
        return currentColor;
    }

    public static Position getPosition()
    {
        return currentPosition;
    }
}
