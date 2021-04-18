package com.lysy.util;

import com.lysy.figure.Figure;
import com.lysy.game.Game;

import java.awt.*;
import java.util.List;

public class Move {
    private Point point;
    private Figure figure;
    private String name;
    private int x;
    private int y;
    public static LastMove lastMove = null;
    public Move(String line) {
        this.x = line.charAt(0) - 97;
        this.y = (int) line.charAt(1) - 49;
    }

    public Move(String line, List<Figure> figures, Util.Turn turn) {
        if (line.length() != 7) {
            this.figure = null;
        }

        String name = line.substring(0, 4);
        String x = line.substring(4, 5);
        String y = line.substring(5, 6);

        this.name = name;
        this.figure = Game.findFigure(name);
        this.point = new Point(Integer.parseInt(x), Integer.parseInt(y));
        if (this.figure == null || !this.figure.getTurn().equals(turn)) {
            this.figure = null;
        }
    }

    public static String getAlphabeticalPosition(Point point) {
        int i = (int) point.getX() + 97;
        int j = (int) point.getY() + 49;
        char x = (char) i;
        char y = (char) j;
        return String.valueOf(x) + String.valueOf(y);
    }

    public boolean isFigure() {
        return this.figure == null;
    }

    public Figure getFigure() {
        return this.figure;
    }

    public Point getPosition() {
        return this.point;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }
}
