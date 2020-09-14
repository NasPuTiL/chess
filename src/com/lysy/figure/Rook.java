package com.lysy.figure;

import com.lysy.util.Util;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Rook extends Figure {
    public Rook(String name, Util.Turn turn, Point position) {
        super(name, turn, position);
    }

    @Override
    protected boolean checkFieldIsAvaliable(List<Point> posibleMoves, List<Figure> figures, int x, int y) {
        return false;
    }

    @Override
    public ArrayList<Point> getListOfPosibleMoves(List<Figure> figures) {
        return null;
    }
}
