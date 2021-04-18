package com.lysy.game;

import com.google.gson.Gson;
import com.lysy.figure.Figure;
import com.lysy.figure.King;
import com.lysy.figure.Pawn;
import com.lysy.figure.Queen;
import com.lysy.util.LastMove;
import com.lysy.util.Move;
import com.lysy.util.Util;

import java.awt.*;
import java.util.*;
import java.util.List;

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

    public static Figure findFigure(int i, int j) {
        return figures.stream().filter(x -> x.findFigure(new Point(i, j)) == true).findAny().orElse(null);
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

        if (!isCheck(move)) {
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
                if (!isCheck(move)) {
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
        boolean isPawnMakeDoubleMove = idPawnDoubleMove(figure, poit);
        figure.setPosition(poit);
        LastMove.setLastMove(poit, figure, isPawnMakeDoubleMove);
    }

    private boolean idPawnDoubleMove(Figure figure, Point point) {
        if (figure instanceof Pawn) {
            if (point.y == 3 && figure.getPosition().y == 1 ||
                    point.y == 4 && figure.getPosition().y == 6) {
                return true;
            }
        }
        return false;
    }

    private boolean possibleMove(Move move) {
        Figure figure = move.getFigure();
        if (figure == null) {
            return false;
        }
        List<Point> listOfPossibleMoves = new ArrayList<>(figure.getListOfPossibleMoves(figures));

        if (isCheck(move)) {
            return false;
        }

        return listOfPossibleMoves.contains(move.getPosition());
    }

    private boolean isCheck(Move move) {
        Figure figure = move.getFigure();
        return isCheck(figure);
    }

    private boolean isCheck(Figure figure) {
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

    private Point findKingPosition(Figure figure) {
        Util.Turn turn = figure.getTurn();
        for (Figure f : figures) {
            if (f.getTurn() == turn && f instanceof King) {
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

    public List<String> findPossibleMoves(String square) {
        Move move = new Move(square);
        Figure figure = findFigure(move.getX(), move.getY());

        List<Point> listOfLegalMoves = getListOfLegalMoves(figure);

        List<String> results = new ArrayList<>();
        for (Point point : listOfLegalMoves) {
            results.add(Move.getAlphabeticalPosition(point));
        }
        return results;
    }

    public static List<Figure> getFigures() {
        return figures;
    }

    private String convertTurn(String turn) {
        if (turn.equals("w")) {

            return "WHITE";
        } else {
            return "BLACK";
        }
    }

    public void setTurn(String turn) {
        Game.turn.setValue(convertTurn(turn));
    }

    public String setFigurePosition(String source, String target) {
        Map<String, String> response = new HashMap<>();
        int i = source.charAt(0) - 97;
        int j = (int) source.charAt(1) - 49;
        Figure figure = findFigure(i, j);
        Point startPoint = new Point(i, j);

        i = target.charAt(0) - 97;
        j = (int) target.charAt(1) - 49;
        Figure figureToRemove = findFigure(i, j);
        Point endPoint = new Point(i, j);

        i = target.charAt(0) - 97;
        j = (int) target.charAt(1) - 49;

        if (figureToRemove != null) {
            figures.remove(figureToRemove);
        }

        setFightBetting(endPoint, figure, response);
        setCascade(startPoint, endPoint);
        LastMove.setLastMove(endPoint, figure, idPawnDoubleMove(figure, endPoint));
        figure = checkAvans(figure, new Point(i, j));
        figure.setPosition(new Point(i, j));

        isMate(figure, response);
        return new Gson().toJson(response);
    }

    private Figure checkAvans(Figure figure, Point point) {
        int pos = (figure.getTurn().equals(Util.Turn.WHITE)) ? 7 : 0;
        Figure newQueen = null;
        if(figure instanceof Pawn && point.y == pos) {
            newQueen = new Queen(figure.getName() + Util.AVANS_SUFFIX_NAME, figure.getTurn(), point);
        }

        if(newQueen != null) {
            figures.remove(figure);
            figures.add(newQueen);
            return newQueen;
        }

        return figure;
    }

    private void isMate(Figure figure, Map<String, String> response) {
        Util.Turn turn = figure.getTurn();
        for(Figure f  : figures) {
            if(f.getTurn().equals(turn)) {
                continue;
            }
            if(getListOfLegalMoves(f).size() > 0) {
                return;
            }
        }

        response.put("IS_MATE", turn + " color win!");
    }

    private void setFightBetting(Point endPoint, Figure figure, Map<String, String> response) {
        int xDir = figure.getTurn().equals(Util.Turn.WHITE) ? 5 : 2;
        if(xDir != endPoint.y) {
            return;
        }

        if (figure instanceof Pawn) {
            if (endPoint.x != figure.getPosition().x && LastMove.isLastMoveWasDoubleMove() && LastMove.getLastMove().x == endPoint.x) {
                Figure pawnFigureToRemove = findFigure(endPoint.x, figure.getPosition().y);
                if (pawnFigureToRemove instanceof Pawn == false) {
                    throw new UnsupportedOperationException("Unsupported flight betting!");
                }
                response.put("FLY_BEAT", Move.getAlphabeticalPosition(pawnFigureToRemove.getPosition()));
                figures.remove(pawnFigureToRemove);
            }
        }
    }

    private void setCascade(Point source, Point target) {
        if (Util.LEFT_CASTLE_POSITIONS_W[0].equals(source) || (Util.LEFT_CASTLE_POSITIONS_B[0].equals(source))) {
            Figure rook;
            if (Util.LEFT_CASTLE_POSITIONS_W[1].equals(target)) {
                rook = Game.findFigure(Util.LEFT_ROOK_WHITE);
                rook.setPosition(new Point(3, 0));
            } else if (Util.RIGHT_CASTLE_POSITIONS_W[1].equals(target)) {
                rook = Game.findFigure(Util.RIGHT_ROOK_WHITE);
                rook.setPosition(new Point(5, 0));
            } else if (Util.LEFT_CASTLE_POSITIONS_B[1].equals(target)) {
                rook = Game.findFigure(Util.LEFT_ROOK_BLACK);
                rook.setPosition(new Point(3, 7));
            } else if (Util.RIGHT_CASTLE_POSITIONS_B[1].equals(target)) {
                rook = Game.findFigure(Util.RIGHT_ROOK_BLACK);
                rook.setPosition(new Point(5, 7));
            }
        }
    }

    public static Figure findFigure(String name) {
        return figures.stream().filter(f -> f.getName().equals(name)).findAny().orElse(null);
    }

    private List<Point> getListOfLegalMoves(Figure figure) {
        List<Point> possibleMoves = figure.getListOfPossibleMoves(figures);
        List<Point> toRemove = new ArrayList<>();
        Point originalPosition = figure.getPosition();

        Point kingPosition = findKingPosition(figure);
        for (Point p : possibleMoves) {
            figure.setPosition(p, false);
            for (Figure f : figures) {
                if (f.getTurn() != figure.getTurn()) {
                    identifyBlockedField(figure, f, p, kingPosition, toRemove);
                }
            }
        }
        figure.setPosition(originalPosition, false);
        possibleMoves.removeAll(toRemove);
        return possibleMoves;
    }

    private void identifyBlockedField(Figure figure, Figure f, Point point, Point kingPosition, List<Point> toRemove) {
        List<Figure> potentialDenseFigure = new ArrayList<>();
        for (Figure potentialDense : figures) {
            if (potentialDense.getPosition().equals(point)) {
                potentialDenseFigure.add(potentialDense);
            }
        }

        if (figure instanceof King == false) {
            if (potentialDenseFigure.contains(f)) {
                return;
            }
            if (f.getListOfPossibleMoves(figures).contains(kingPosition)) {
                toRemove.add(point);
            }
        } else {
            if (f.getListOfPossibleMoves(figures).contains(point)) {
                toRemove.add(point);
            }
        }
    }
}
