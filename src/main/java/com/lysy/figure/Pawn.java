package com.lysy.figure;

import com.lysy.game.Game;
import com.lysy.util.LastMove;
import com.lysy.util.Util;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Pawn extends Figure {
    private int direction;
    private int startDir;

    public Pawn(String name, Util.Turn turn, Point position) {
        super(name, turn, position);
        this.direction = (this.color == Util.Turn.WHITE) ? 1 : -1;
        this.startDir = (this.color == Util.Turn.WHITE) ? 1 : 6;
    }

    @Override
    public List<Point> getListOfPossibleMoves(List<Figure> figures) {
        List<Point> possibleMoves = new ArrayList<>();
        int startX = (int) this.position.getX();
        int startY = (int) this.position.getY();

        int tmpX = startX;
        int tmpY = startY;


        tmpY = nextStep(tmpY);
        if (tmpX >= 0 && tmpX < Game.sizeOfMap && tmpY >= 0 && tmpY < Game.sizeOfMap && checkFieldIsAvailable(figures, tmpX, tmpY)) {
            possibleMoves.add(new Point(tmpX, tmpY));
        }

        tmpX = startX - 1;
        if (checkEnemyPositionFor(tmpX, tmpY)) {
            if (tmpX >= 0 && tmpX < Game.sizeOfMap && tmpY >= 0 && tmpY < Game.sizeOfMap && checkFieldIsAvailable(possibleMoves, figures, tmpX, tmpY)) {
                possibleMoves.add(new Point(tmpX, tmpY));
            }
        }

        tmpX = startX + 1;
        if (checkEnemyPositionFor(tmpX, tmpY)) {
            if (tmpX >= 0 && tmpX < Game.sizeOfMap && tmpY >= 0 && tmpY < Game.sizeOfMap && checkFieldIsAvailable(possibleMoves, figures, tmpX, tmpY)) {
                possibleMoves.add(new Point(tmpX, tmpY));
            }
        }

        if (startY == startDir) {
            tmpX = startX;
            tmpY = nextStep(tmpY);
            if (tmpX >= 0 && tmpX < Game.sizeOfMap && tmpY >= 0 && tmpY < Game.sizeOfMap && checkFieldIsAvailable(figures, tmpX, tmpY)) {
                possibleMoves.add(new Point(tmpX, tmpY));
            }
        }

        addBeatingFlight(possibleMoves);

        return possibleMoves;
    }

    private void addBeatingFlight(List<Point> possibleMoves) {
        if (!LastMove.isLastMoveWasDoubleMove()) {
            return;
        }
        if (this.position.y == 3 && LastMove.getLastMove().y == 3 ||
                this.position.y == 4 && LastMove.getLastMove().y == 4) {
            Figure lastMovedFigure = LastMove.getLastMovedFigure();
            int dif = this.position.x - LastMove.getLastMove().x;
            if (Math.abs(dif) < 2) {
                if (lastMovedFigure instanceof Pawn && !lastMovedFigure.color.equals(this.color)) {
                    int xDir = this.color.equals(Util.Turn.WHITE) ? 5 : 2;
                    possibleMoves.add(new Point(this.position.x - dif, xDir));
                }
            }
        }
    }

    private boolean checkFieldIsAvailable(List<Figure> figures, int x, int y) {
        for (Figure f : figures) {
            if (f.getPosition().getX() == x && f.getPosition().getY() == y) {
                return false;
            }
        }
        return true;
    }

    private boolean checkEnemyPositionFor(int tmpX, int tmpY) {
        Point p = new Point(tmpX, tmpY);
        for (Figure f : Game.getFigures()) {
            if (f.color != this.color && f.position.getX() == p.getX() && f.position.getY() == p.getY()) {
                return true;
            }
        }
        return false;
    }

    private int nextStep(int tmpY) {
        return tmpY + 1 * this.direction;
    }
}
