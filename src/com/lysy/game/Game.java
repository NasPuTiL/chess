package com.lysy.game;

import com.lysy.figure.*;
import com.lysy.util.Move;
import com.lysy.util.Util;
import netscape.javascript.JSObject;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Game {
    public static final int sizeOfMap = 8;
    private static Util.Turn turn;
    private static List<Figure> figures = new ArrayList<>(32);
    private static List<Figure> denseFigures = new ArrayList<>();

    public Game() {
        Game.turn = Util.Turn.WHITE;
        initBoard();
    }

    private void initBoard() {
        Figure rookL = new Rook("RR_W", Util.Turn.WHITE, new Point(0, 0));
        Figure knightL = new Knight("RK_W", Util.Turn.WHITE, new Point(1, 0));
        Figure bishopL = new Bishop("RB_W", Util.Turn.WHITE, new Point(2, 0));
        Figure queen = new Queen("Q__W", Util.Turn.WHITE, new Point(3, 0));
        Figure king = new King("K__W", Util.Turn.WHITE, new Point(4, 0));
        Figure knightR = new Knight("LK_W", Util.Turn.WHITE, new Point(5, 0));
        Figure bishopR = new Bishop("LB_W", Util.Turn.WHITE, new Point(6, 0));
        Figure rookR = new Rook("LR_W", Util.Turn.WHITE, new Point(7, 0));

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

        rookL = new Rook("RR_B", Util.Turn.BLACK, new Point(0, 7));
        knightL = new Knight("RK_B", Util.Turn.BLACK, new Point(1, 7));
        bishopL = new Bishop("RB_B", Util.Turn.BLACK, new Point(2, 7));
        queen = new Queen("Q__B", Util.Turn.BLACK, new Point(3, 7));
        king = new King("K__B", Util.Turn.BLACK, new Point(4, 7));
        knightR = new Knight("RK_B", Util.Turn.BLACK, new Point(5, 7));
        bishopR = new Bishop("LB_B", Util.Turn.BLACK, new Point(6, 7));
        rookR = new Rook("LR_B", Util.Turn.BLACK, new Point(7, 7));

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
    }

    private void drawMap() {
        cleanScreen();
        for (int i = Game.sizeOfMap - 1; i >= 0; i--) {
            System.out.print(i + 1 + " ");
            for (int j = 0; j < Game.sizeOfMap; j++) {
                Figure flg = findFigure(i, j);
                if (flg != null) {
                    System.out.print(" " + flg.getName() + " ");
                } else {
                    System.out.print("  _   ");
                }
            }
            System.out.println();
        }
        System.out.println("    A     B     C     D     E     F     G     H");
    }

    private void cleanScreen() {
        System.out.flush();
    }

    private Figure findFigure(int i, int j) {
        return figures.stream().filter(x -> x.findFigure(new Point(j, i)) == true).findAny().orElse(null);
    }

    public void run() {
        while (true) {
            drawMap();
            Move move = getMove();
            if (possibleMove(move)) {
                setPosition(move);
                if (isMate(move)) {
                    System.out.println("Check and mate");
                    break;
                }
                switchTurn();
            }
        }
    }

    private boolean isMate(Move move) {
        Figure figure = move.getFigure();
        Point oldPoint = move.getPosition();

        if (!check(move)) {
            return false;
        }
        for (Figure f : figures) {
            if (f.getTurn() != turn) {
                continue;
            }

            Point position = f.getPosition();
            ArrayList<Point> listOfPosibleMoves = f.getListOfPosibleMoves(figures);
            for (Point p : listOfPosibleMoves) {
                f.setPosition(p);
                if (!check(move)) {
                    f.setPosition(position);
                    return false;
                }
            }
        }

        figure.setPosition(oldPoint);
        return true;
    }

    private void switchTurn() {
        if (turn == Util.Turn.WHITE) {
            turn = Util.Turn.BLACK;
        } else {
            turn = Util.Turn.WHITE;
        }
    }

    private void setPosition(Move move) {
        Figure figure = move.getFigure();
        Point poit = move.getPosition();

        Figure figureToRemove = null;
        for (Figure f : figures) {
            if (f.getPosition().getX() == poit.getX() && f.getPosition().getY() == poit.getY()) {
                denseFigures.add(f);
                if (f.getTurn() == figure.getTurn()) {
                    throw new UnsupportedOperationException("Field is occupied by your own color1!");
                }
                figureToRemove = f;
            }
        }

        figures.remove(figureToRemove);
        figure.setPosition(poit);
    }

    private boolean possibleMove(Move move) {
        Figure figure = move.getFigure();
        if (figure == null) {
            return false;
        }
        List<Point> listOfPosibleMoves = new ArrayList<>(figure.getListOfPosibleMoves(figures));
        System.out.println("listOfPosibleMoves = " + listOfPosibleMoves);

        if (check(move)) {
            return false;
        }
        ;
        return listOfPosibleMoves.contains(move.getPosition());
    }

    private boolean check(Move move) {
        Figure figure = move.getFigure();
        Util.Turn turn = figure.getTurn();
        Point point = findKingPositionForOpositeSide(figure);
        for (Figure f : figures) {
            if (f.getTurn() == turn) {
                break;
            }
            if (f.getListOfPosibleMoves(figures).contains(point)) {
                return true;
            }

        }
        return false;
    }

    private Point findKingPositionForOpositeSide(Figure figure) {
        Util.Turn turn = figure.getTurn();
        for (Figure f : figures) {
            if (f.getTurn() != turn && f instanceof King) {
                return f.getPosition();
            }
        }
        return null;
    }

    private Move getMove() {
        Scanner scan = new Scanner(System.in);
        String line;
        Move move;
        do {
            JSObject x;
            x =
            System.out.println("//Scheme:Name position.x position.y");
            System.out.print("Tourn (" + this.turn.name() + "), Get move:");

            line = scan.nextLine();
            line.replaceAll(" ", "");
            move = new Move(line, figures, Game.turn);
        } while (line.length() == 6 && move.isFigure());
        return move;
    }

    public static List<Figure> getFigures() {
        return figures;
    }
}
