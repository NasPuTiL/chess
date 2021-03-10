package com.lysy.back.game;

import com.lysy.back.figure.Figure;
import com.lysy.back.figure.King;
import com.lysy.back.util.Move;
import com.lysy.back.util.Util;
import netscape.javascript.JSObject;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Game {
    public static final int sizeOfMap = 8;
    private static Util.Turn turn;
    private static List<Figure> figures;
    private static List<Figure> denseFigures;

    public Game() {
        Game.turn = Util.Turn.WHITE;
        Game.figures = new ArrayList<>(Util.initBoard());
        Game.denseFigures = new ArrayList<>();
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
            List<Point> listOfPossibleMoves = f.getListOfPossibleMoves(figures);
            for (Point p : listOfPossibleMoves) {
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
        List<Point> listOfPossibleMoves = new ArrayList<>(figure.getListOfPossibleMoves(figures));

        if (check(move)) {
            return false;
        }

        return listOfPossibleMoves.contains(move.getPosition());
    }

    private boolean check(Move move) {
        Figure figure = move.getFigure();
        Util.Turn turn = figure.getTurn();
        Point point = findKingPositionForOpositeSide(figure);
        for (Figure f : figures) {
            if (f.getTurn() == turn) {
                break;
            }
            if (f.getListOfPossibleMoves(figures).contains(point)) {
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
            System.out.println("//Scheme:Name position.x position.y");
            System.out.print("Tourn (" + this.turn.name() + "), Get move:");

            line = scan.nextLine().trim();
            move = new Move(line, figures, Game.turn);
        } while (line.length() == 6 && move.isFigure());
        return move;
    }

    public static List<Figure> getFigures() {
        return figures;
    }
}
