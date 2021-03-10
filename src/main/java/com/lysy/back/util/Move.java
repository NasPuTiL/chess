package com.lysy.back.util;

import com.lysy.back.figure.Figure;

import java.awt.*;
import java.util.List;

public class Move {
    private Point point;
    private Figure figure;
    private String name;

    public Move(String line, List<Figure> figures, Util.Turn turn) {
        if (line.length() != 7) {
            this.figure = null;
        }

        String name = line.substring(0, 4);
        String x = line.substring(4, 5);
        String y = line.substring(5, 6);

        this.name = name;
        this.figure = figures.stream().filter(f -> f.getName().equals(name)).findAny().orElse(null);
        this.point = new Point(Integer.parseInt(x), Integer.parseInt(y));
        if (this.figure == null || !this.figure.getTurn().equals(turn)) {
            this.figure = null;
        }
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
}
