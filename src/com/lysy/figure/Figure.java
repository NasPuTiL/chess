package com.lysy.figure;

import com.lysy.util.Util;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;


public abstract class Figure {
    protected Util.Turn color;
    protected Point position;
    protected String name;

    Figure(String name, Util.Turn turn, Point position) {
        this.name = name;
        this.color = turn;
        this.position = position;
    }

    public boolean findFigure(Point point) {
        if (point.x == this.position.getX() && point.y == this.position.getY()) {
            return true;
        }
        return false;
    }

    public String getName() {
        return this.name;
    }

    public Util.Turn getTurn() {
        return this.color;
    }

    protected boolean checkFieldIsAvaliable(java.util.List<Point> posibleMoves, java.util.List<Figure> figures, int x, int y) {
        for (Figure f : figures) {
            if (f.getPosition().getX() == x && f.getPosition().getY() == y) {
                if (f.getTurn() == this.color) {
                    return false;
                } else {
                    posibleMoves.add(new Point(x, y));
                    return false;
                }
            }
        }
        return true;
    }

    public abstract ArrayList<Point> getListOfPosibleMoves(List<Figure> figures);

    public Point getPosition() {
        return this.position;
    }

    public void setPosition(Point position){
        this.position = position;
    }
}
