package com.lysy.figure;

import com.lysy.game.Game;
import com.lysy.util.Util;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Rook extends Figure implements MovementValidation{
    private boolean isMoved;
    public Rook(String name, Util.Turn turn, Point position) {
        super(name, turn, position);
        this.isMoved = false;
    }

    @Override
    public List<Point> getListOfPossibleMoves(List<Figure> figures) {
        List<Point> possibleMoves = new ArrayList<>();
        int startX = (int) this.position.getX();
        int startY = (int) this.position.getY();

        int tmpX = startX;
        int tmpY = startY;
        for (; ; ) {
            tmpX++;
            if (tmpX >= 0 && tmpX < Game.sizeOfMap && tmpY >= 0 && tmpY < Game.sizeOfMap && checkFieldIsAvailable(possibleMoves, figures, tmpX, tmpY)) {
                possibleMoves.add(new Point(tmpX, tmpY));
            } else {
                break;
            }
        }

        tmpX = startX;
        tmpY = startY;
        for (; ; ) {
            tmpX--;
            if (tmpX >= 0 && tmpX < Game.sizeOfMap && tmpY >= 0 && tmpY < Game.sizeOfMap && checkFieldIsAvailable(possibleMoves, figures, tmpX, tmpY)) {
                possibleMoves.add(new Point(tmpX, tmpY));
            } else {
                break;
            }
        }

        tmpX = startX;
        tmpY = startY;
        for (; ; ) {
            tmpY++;
            if (tmpX >= 0 && tmpX < Game.sizeOfMap && tmpY >= 0 && tmpY < Game.sizeOfMap && checkFieldIsAvailable(possibleMoves, figures, tmpX, tmpY)) {
                possibleMoves.add(new Point(tmpX, tmpY));
            } else {
                break;
            }
        }

        tmpX = startX;
        tmpY = startY;
        for (; ; ) {
            tmpY--;
            if (tmpX >= 0 && tmpX < Game.sizeOfMap && tmpY >= 0 && tmpY < Game.sizeOfMap && checkFieldIsAvailable(possibleMoves, figures, tmpX, tmpY)) {
                possibleMoves.add(new Point(tmpX, tmpY));
            } else {
                break;
            }
        }

        return possibleMoves;
    }

    public boolean isMoved() {
        return isMoved;
    }

    @Override
    public void setPosition(Point position) {
        this.position = position;
        moved();
    }

    public void moved() {
        if(!this.isMoved) {
            this.isMoved = true;
        }
    }
}
