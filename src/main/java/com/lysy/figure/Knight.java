package com.lysy.figure;

import com.lysy.game.Game;
import com.lysy.util.Util;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Knight extends Figure {
    public Knight(String name, Util.Turn turn, Point position) {
        super(name, turn, position);
    }

    @Override
    public List<Point> getListOfPossibleMoves(List<Figure> figures) {
        List<Point> posibleMoves = new ArrayList<>();
        int startX = (int) this.position.getX();
        int startY = (int) this.position.getY();

        int[][] posibilities = getPosibiliteMoves(startX, startY);
        for (int i = 0; i < posibilities.length; i++) {
            int tmpX = posibilities[i][0];
            int tmpY = posibilities[i][1];
            if (tmpX >= 0 && tmpX < Game.sizeOfMap && tmpY >= 0 && tmpY < Game.sizeOfMap && checkFieldIsAvailable(posibleMoves, figures, tmpX, tmpY)) {
                posibleMoves.add(new Point(tmpX, tmpY));
            }
        }
        return posibleMoves;
    }

    private int[][] getPosibiliteMoves(int startX, int startY) {
        int[][] posibilities = new int[8][8];
        posibilities[0][0] = startX + 1;
        posibilities[0][1] = startY + 2;

        posibilities[1][0] = startX + 1;
        posibilities[1][1] = startY - 2;

        posibilities[2][0] = startX - 1;
        posibilities[2][1] = startY - 2;

        posibilities[3][0] = startX - 1;
        posibilities[3][1] = startY + 2;

        posibilities[4][0] = startX + 2;
        posibilities[4][1] = startY + 1;

        posibilities[5][0] = startX + 2;
        posibilities[5][1] = startY - 1;

        posibilities[6][0] = startX - 2;
        posibilities[6][1] = startY - 1;

        posibilities[7][0] = startX - 2;
        posibilities[7][1] = startY + 1;

        return posibilities;
    }
}
