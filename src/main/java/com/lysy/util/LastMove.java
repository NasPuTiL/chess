package com.lysy.util;

import com.lysy.figure.Figure;

import java.awt.*;

public class LastMove {
    private static Point position;
    private static Figure figure;
    private static boolean isPawnMakeDoubleMove;

    public static void setLastMove(Point point, Figure f, boolean isPawnMakeDoubleMove) {
        LastMove.position = point;
        LastMove.figure = f;
        LastMove.isPawnMakeDoubleMove = isPawnMakeDoubleMove;
    }

    public static Point getLastMove() {
        return LastMove.position;
    }

    public static Figure getLastMovedFigure() {
        return LastMove.figure;
    }

    public static boolean isLastMoveWasDoubleMove() {
        return isPawnMakeDoubleMove;
    }
}
