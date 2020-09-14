package com.lysy.figure;

import com.lysy.game.Game;
import com.lysy.util.Util;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class King extends Figure {
    public King(String name, Util.Turn turn, Point position) {
        super(name, turn, position);
    }

    @Override
    public ArrayList<Point> getListOfPosibleMoves(List<Figure> figures) {
        List<Point> posibleMoves = new ArrayList<>();
        int startX = (int) this.position.getX();
        int startY = (int) this.position.getY();

        if ((startX + 1) >= 0 && (startX + 1) < Game.sizeOfMap && (startY + 1) >= 0
                && (startY + 1) < Game.sizeOfMap && checkFieldIsAvaliable(posibleMoves, figures, startX + 1, startY + 1)) {
            posibleMoves.add(new Point(startX + 1, startY + 1));
        }
        if ((startX + 1) >= 0 && (startX + 1) < Game.sizeOfMap && (startY - 1) >= 0
                && (startY - 1) < Game.sizeOfMap && checkFieldIsAvaliable(posibleMoves, figures, startX + 1, startY - 1)) {
            posibleMoves.add(new Point(startX + 1, startY - 1));
        }
        if ((startX - 1) >= 0 && (startX - 1) < Game.sizeOfMap && (startY - 1) >= 0
                && (startY - 1) < Game.sizeOfMap && checkFieldIsAvaliable(posibleMoves, figures, startX - 1, startY - 1)) {
            posibleMoves.add(new Point(startX - 1, startY - 1));
        }
        if ((startX - 1) >= 0 && (startX - 1) < Game.sizeOfMap && (startY + 1) >= 0
                && (startY + 1) < Game.sizeOfMap && checkFieldIsAvaliable(posibleMoves, figures, startX - 1, startY + 1)) {
            posibleMoves.add(new Point(startX - 1, startY + 1));
        }
        if (startX >= 0 && startX < Game.sizeOfMap && (startY + 1) >= 0
                && (startY + 1) < Game.sizeOfMap && checkFieldIsAvaliable(posibleMoves, figures, startX, startY + 1)) {
            posibleMoves.add(new Point(startX, startY + 1));
        }
        if (startX >= 0 && startX < Game.sizeOfMap && (startY - 1) >= 0
                && (startY - 1) < Game.sizeOfMap && checkFieldIsAvaliable(posibleMoves, figures, startX, startY - 1)) {
            posibleMoves.add(new Point(startX, startY - 1));
        }
        if ((startX + 1) >= 0 && (startX + 1) < Game.sizeOfMap && startY >= 0
                && startY < Game.sizeOfMap && checkFieldIsAvaliable(posibleMoves, figures, startX + 1, startY)) {
            posibleMoves.add(new Point(startX + 1, startY));
        }
        if ((startX - 1) >= 0 && (startX - 1) < Game.sizeOfMap && startY >= 0
                && startY < Game.sizeOfMap && checkFieldIsAvaliable(posibleMoves, figures, startX - 1, startY)) {
            posibleMoves.add(new Point(startX - 1, startY));
        }

        //TODO: Remove fields ocupated by enemy figures!!!

        return (ArrayList<Point>) posibleMoves;
    }
}
