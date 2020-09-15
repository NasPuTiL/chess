package com.lysy.figure;

import com.lysy.game.Game;
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
    public ArrayList<Point> getListOfPosibleMoves(List<Figure> figures) {
        List<Point> posibleMoves = new ArrayList<>();
        int startX = (int) this.position.getX();
        int startY = (int) this.position.getY();

        int tmpX = startX;
        int tmpY = startY;


        tmpY = nextStep(tmpY);
        if (tmpX >= 0 && tmpX < Game.sizeOfMap && tmpY >= 0 && tmpY < Game.sizeOfMap && checkFieldIsAvaliable(posibleMoves, figures, tmpX, tmpY)) {
            posibleMoves.add(new Point(tmpX, tmpY));
        }

        tmpX = startX - 1;
        if(checkEnemyPositionFor(tmpX, tmpY)) {
            if (tmpX >= 0 && tmpX < Game.sizeOfMap && tmpY >= 0 && tmpY < Game.sizeOfMap && checkFieldIsAvaliable(posibleMoves, figures, tmpX, tmpY)) {
                posibleMoves.add(new Point(tmpX, tmpY));
            }
        }

        tmpX = startX + 1;
        if(checkEnemyPositionFor(tmpX, tmpY)) {
            if (tmpX >= 0 && tmpX < Game.sizeOfMap && tmpY >= 0 && tmpY < Game.sizeOfMap && checkFieldIsAvaliable(posibleMoves, figures, tmpX, tmpY)) {
                posibleMoves.add(new Point(tmpX, tmpY));
            }
        }

        if (startY == startDir) {
            tmpX = startX ;
            tmpY = nextStep(tmpY);
            if (tmpX >= 0 && tmpX < Game.sizeOfMap && tmpY >= 0 && tmpY < Game.sizeOfMap && checkFieldIsAvaliable(posibleMoves, figures, tmpX, tmpY)) {
                posibleMoves.add(new Point(tmpX, tmpY));
            }
        }

        //TODO: Beating in flight

        return (ArrayList<Point>) posibleMoves;
    }

    private boolean checkEnemyPositionFor(int tmpX, int tmpY) {
        Point p = new Point(tmpX, tmpY);
        for(Figure f : Game.getFigures()){
            if(f.color != this.color && f.position == p){
                return true;
            }
        }
        return false;
    }

    private int nextStep(int tmpY) {
        return tmpY + 1 * this.direction;
    }
}
