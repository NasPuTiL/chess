package com.lysy.back.figure;

import com.lysy.back.game.Game;
import com.lysy.back.util.Util;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Queen extends Figure {
    public Queen(String name, Util.Turn turn, Point position) {
        super(name, turn, position);
    }

    @Override
    public List<Point> getListOfPossibleMoves(List<Figure> figures) {
        List<Point> posibleMoves = new ArrayList<>();
        generateStrightLines(posibleMoves, figures);
        generateDiagonalsLines(posibleMoves, figures);
        return posibleMoves;
    }

    private void generateDiagonalsLines(List<Point> posibleMoves, List<Figure> figures) {
        int startX = (int) this.position.getX();
        int startY = (int) this.position.getY();

        int tmpX = startX;
        int tmpY = startY;
        for (; ; ) {
            tmpX--;
            tmpY--;
            if (tmpX >= 0 && tmpX < Game.sizeOfMap && tmpY >= 0 && tmpY < Game.sizeOfMap && checkFieldIsAvailable(posibleMoves, figures, tmpX, tmpY)) {
                posibleMoves.add(new Point(tmpX, tmpY));
            } else {
                break;
            }
        }

        tmpX = startX;
        tmpY = startY;
        for (; ; ) {
            tmpX--;
            tmpY++;
            if (tmpX >= 0 && tmpX < Game.sizeOfMap && tmpY >= 0 && tmpY < Game.sizeOfMap && checkFieldIsAvailable(posibleMoves, figures, tmpX, tmpY)) {
                posibleMoves.add(new Point(tmpX, tmpY));
            } else {
                break;
            }
        }

        tmpX = startX;
        tmpY = startY;
        for (; ; ) {
            tmpX++;
            tmpY--;
            if (tmpX >= 0 && tmpX < Game.sizeOfMap && tmpY >= 0 && tmpY < Game.sizeOfMap && checkFieldIsAvailable(posibleMoves, figures, tmpX, tmpY)) {
                posibleMoves.add(new Point(tmpX, tmpY));
            } else {
                break;
            }
        }

        tmpX = startX;
        tmpY = startY;
        for (; ; ) {
            tmpX++;
            tmpY++;
            if (tmpX >= 0 && tmpX < Game.sizeOfMap && tmpY >= 0 && tmpY < Game.sizeOfMap && checkFieldIsAvailable(posibleMoves, figures, tmpX, tmpY)) {
                posibleMoves.add(new Point(tmpX, tmpY));
            } else {
                break;
            }
        }
    }

    private void generateStrightLines(List<Point> posibleMoves, List<Figure> figures) {
        int startX = (int) this.position.getX();
        int startY = (int) this.position.getY();

        int tmpX = startX;
        int tmpY = startY;
        for (; ; ) {
            tmpX++;
            if (tmpX >= 0 && tmpX < Game.sizeOfMap && tmpY >= 0 && tmpY < Game.sizeOfMap && checkFieldIsAvailable(posibleMoves, figures, tmpX, tmpY)) {
                posibleMoves.add(new Point(tmpX, tmpY));
            } else {
                break;
            }
        }

        tmpX = startX;
        tmpY = startY;
        for (; ; ) {
            tmpX--;
            if (tmpX >= 0 && tmpX < Game.sizeOfMap && tmpY >= 0 && tmpY < Game.sizeOfMap && checkFieldIsAvailable(posibleMoves, figures, tmpX, tmpY)) {
                posibleMoves.add(new Point(tmpX, tmpY));
            } else {
                break;
            }
        }

        tmpX = startX;
        tmpY = startY;
        for (; ; ) {
            tmpY++;
            if (tmpX >= 0 && tmpX < Game.sizeOfMap && tmpY >= 0 && tmpY < Game.sizeOfMap && checkFieldIsAvailable(posibleMoves, figures, tmpX, tmpY)) {
                posibleMoves.add(new Point(tmpX, tmpY));
            } else {
                break;
            }
        }

        tmpX = startX;
        tmpY = startY;
        for (; ; ) {
            tmpY--;
            if (tmpX >= 0 && tmpX < Game.sizeOfMap && tmpY >= 0 && tmpY < Game.sizeOfMap && checkFieldIsAvailable(posibleMoves, figures, tmpX, tmpY)) {
                posibleMoves.add(new Point(tmpX, tmpY));
            } else {
                break;
            }
        }
    }
}
