package com.lysy.util;

import com.google.gson.Gson;
import com.lysy.figure.*;
import com.lysy.game.Game;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Util {
    public static final String RETURN_MESSAGE_OK = new Gson().toJson("OK");
    public static final String RETURN_APPLICATION_MSSG = "application/json";
    public static final String RETURN_CODING = "UTF-8";
    public static final int RETURN_STATUS_CODE_OK = 200;
    public static final String LEFT_ROOK_WHITE = "LR_W";
    public static final String RIGHT_ROOK_WHITE = "RR_W";
    public static final String LEFT_ROOK_BLACK = "LR_B";
    public static final String RIGHT_ROOK_BLACK = "RR_B";

    public static final Point[] LEFT_CASTLE_POSITIONS_W = {new Point(4, 0), new Point(2, 0)};
    public static final Point[] RIGHT_CASTLE_POSITIONS_W = {new Point(4, 0), new Point(6, 0)};

    public static final Point[] LEFT_CASTLE_POSITIONS_B = {new Point(4, 7), new Point(2, 7)};
    public static final Point[] RIGHT_CASTLE_POSITIONS_B = {new Point(4, 7), new Point(6, 7)};
    public static final String AVANS_SUFFIX_NAME = "_Q";

    public enum Turn {WHITE("WHITE"), BLACK("BLACK");
        private String value;

        Turn(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value=value;
        }
    }

    public static List<Figure> initBoard() {
        List<Figure> figures = new ArrayList<>(32);
        Figure rookL = new Rook(LEFT_ROOK_WHITE, Util.Turn.WHITE, new Point(0, 0));
        Figure knightL = new Knight("LK_W", Util.Turn.WHITE, new Point(1, 0));
        Figure bishopL = new Bishop("LB_W", Util.Turn.WHITE, new Point(2, 0));
        Figure queen = new Queen("Q__W", Util.Turn.WHITE, new Point(3, 0));
        Figure king = new King("K__W", Util.Turn.WHITE, new Point(4, 0));
        Figure bishopR = new Bishop("RB_W", Util.Turn.WHITE, new Point(5, 0));
        Figure knightR = new Knight("RK_W", Util.Turn.WHITE, new Point(6, 0));
        Figure rookR = new Rook(RIGHT_ROOK_WHITE, Util.Turn.WHITE, new Point(7, 0));

        figures.add(knightL);
        figures.add(knightR);
        figures.add(rookL);
        figures.add(rookR);
        figures.add(bishopL);
        figures.add(bishopR);
        figures.add(queen);
        figures.add(king);
        for (int i = 0; i < Game.sizeOfMap; i++) {
            figures.add(new Pawn("P" + (i + 1) + "_W", Util.Turn.WHITE, new Point(i, 1)));
        }

        rookL = new Rook(LEFT_ROOK_BLACK, Util.Turn.BLACK, new Point(0, 7));
        knightL = new Knight("LK_B", Util.Turn.BLACK, new Point(1, 7));
        bishopL = new Bishop("LB_B", Util.Turn.BLACK, new Point(2, 7));
        queen = new Queen("Q__B", Util.Turn.BLACK, new Point(3, 7));
        king = new King("K__B", Util.Turn.BLACK, new Point(4, 7));
        bishopR = new Bishop("RB_B", Util.Turn.BLACK, new Point(5, 7));
        knightR = new Knight("RK_B", Util.Turn.BLACK, new Point(6, 7));
        rookR = new Rook(RIGHT_ROOK_BLACK, Util.Turn.BLACK, new Point(7, 7));

        figures.add(knightL);
        figures.add(knightR);
        figures.add(rookL);
        figures.add(rookR);
        figures.add(bishopL);
        figures.add(bishopR);
        figures.add(queen);
        figures.add(king);
        for (int i = 0; i < Game.sizeOfMap; i++) {
            figures.add(new Pawn("P" + (i + 1) + "_B", Util.Turn.BLACK, new Point(i, 6)));
        }
        return figures;
    }
}
