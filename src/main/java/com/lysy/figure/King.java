package com.lysy.figure;

import com.lysy.game.Game;
import com.lysy.util.Util;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class King extends Figure implements MovementValidation {
    private boolean isMoved;
    private List<Point> castleBusyFieldLeft;
    private List<Point> castleBusyFieldRight;

    public King(String name, Util.Turn turn, Point position) {
        super(name, turn, position);
        this.isMoved = false;
        if (this.color.equals(Util.Turn.WHITE)) {
            this.castleBusyFieldLeft = new ArrayList<>(Arrays.asList(new Point(1, 0), new Point(2, 0), new Point(3, 0)));
            this.castleBusyFieldRight = new ArrayList<>(Arrays.asList(new Point(5, 0), new Point(6, 0)));
        } else {
            this.castleBusyFieldLeft = new ArrayList<>(Arrays.asList(new Point(1, 7), new Point(2, 7), new Point(3, 7)));
            this.castleBusyFieldRight = new ArrayList<>(Arrays.asList(new Point(5, 7), new Point(6, 7)));
        }
    }

    @Override
    public List<Point> getListOfPossibleMoves(List<Figure> figures) {
        List<Point> possibleMoves = new ArrayList<>();
        int startX = (int) this.position.getX();
        int startY = (int) this.position.getY();

        if ((startX + 1) >= 0 && (startX + 1) < Game.sizeOfMap && (startY + 1) >= 0
                && (startY + 1) < Game.sizeOfMap && checkFieldIsAvailable(possibleMoves, figures, startX + 1, startY + 1)) {
            possibleMoves.add(new Point(startX + 1, startY + 1));
        }
        if ((startX + 1) >= 0 && (startX + 1) < Game.sizeOfMap && (startY - 1) >= 0
                && (startY - 1) < Game.sizeOfMap && checkFieldIsAvailable(possibleMoves, figures, startX + 1, startY - 1)) {
            possibleMoves.add(new Point(startX + 1, startY - 1));
        }
        if ((startX - 1) >= 0 && (startX - 1) < Game.sizeOfMap && (startY - 1) >= 0
                && (startY - 1) < Game.sizeOfMap && checkFieldIsAvailable(possibleMoves, figures, startX - 1, startY - 1)) {
            possibleMoves.add(new Point(startX - 1, startY - 1));
        }
        if ((startX - 1) >= 0 && (startX - 1) < Game.sizeOfMap && (startY + 1) >= 0
                && (startY + 1) < Game.sizeOfMap && checkFieldIsAvailable(possibleMoves, figures, startX - 1, startY + 1)) {
            possibleMoves.add(new Point(startX - 1, startY + 1));
        }
        if (startX >= 0 && startX < Game.sizeOfMap && (startY + 1) >= 0
                && (startY + 1) < Game.sizeOfMap && checkFieldIsAvailable(possibleMoves, figures, startX, startY + 1)) {
            possibleMoves.add(new Point(startX, startY + 1));
        }
        if (startX >= 0 && startX < Game.sizeOfMap && (startY - 1) >= 0
                && (startY - 1) < Game.sizeOfMap && checkFieldIsAvailable(possibleMoves, figures, startX, startY - 1)) {
            possibleMoves.add(new Point(startX, startY - 1));
        }
        if ((startX + 1) >= 0 && (startX + 1) < Game.sizeOfMap && startY >= 0
                && startY < Game.sizeOfMap && checkFieldIsAvailable(possibleMoves, figures, startX + 1, startY)) {
            possibleMoves.add(new Point(startX + 1, startY));
        }
        if ((startX - 1) >= 0 && (startX - 1) < Game.sizeOfMap && startY >= 0
                && startY < Game.sizeOfMap && checkFieldIsAvailable(possibleMoves, figures, startX - 1, startY)) {
            possibleMoves.add(new Point(startX - 1, startY));
        }

        castleMove(possibleMoves);
        validateEntryIntoCheck(possibleMoves, figures);

        return possibleMoves;
    }

    private void validateEntryIntoCheck(List<Point> possibleMoves, List<Figure> figures) {
        if (possibleMoves.isEmpty()) {
            return;
        }
        List<Point> toRemove = new ArrayList<>();
        List<Figure> figuresCopy = new ArrayList<>(figures);
        for (Point p : possibleMoves) {
            Figure fig = Game.findFigure(p.x, p.y);
            if(fig == null || fig.getTurn().equals(this.color)) {
                continue;
            } else {
                figuresCopy.remove(fig);
            }

            for(Figure f : figuresCopy) {
                if( f.getTurn().equals(this.color)) {//f instanceof King ||
                    continue;
                }
                if(f.getListOfPossibleMoves(figuresCopy).contains(p)) {
                    toRemove.add(p);
                }
            }
            figuresCopy.add(fig);
        }
        if(!toRemove.isEmpty()) {
            possibleMoves.removeAll(toRemove);
        }
    }

    private void castleMove(List<Point> posibleMoves) {
        if (isMoved) {
            return;
        }
        List<Figure> figures = Game.getFigures();
        Figure leftRook = null;
        Figure rightRook = null;
        boolean leftCastle = true;
        boolean rightCastle = true;

        for (Figure figure : figures) {
            if (figure instanceof Rook && figure.color.equals(this.color)) {
                if (figure.getName().equals(getLeftRookName())) {
                    leftRook = figure;
                } else {
                    rightRook = figure;
                }
            }

            if (castleBusyFieldLeft.contains(figure.getPosition())) {
                leftCastle = false;
            }
            if (castleBusyFieldRight.contains(figure.getPosition())) {
                rightCastle = false;
            }
        }

        if(leftRook != null && !((Rook)leftRook).isMoved() && leftCastle && !this.isMoved) {
            if(this.color.equals(Util.Turn.WHITE)) {
                posibleMoves.add(new Point(2, 0));
            } else {
                posibleMoves.add(new Point(2, 7));
            }
        }
        if (rightRook != null && !((Rook)rightRook).isMoved() && rightCastle && !this.isMoved) {
            if(this.color.equals(Util.Turn.WHITE)) {
                posibleMoves.add(new Point(6, 0));
            } else {
                posibleMoves.add(new Point(6, 7));
            }
        }
    }

    private String getLeftRookName() {
        if (this.color.equals(Util.Turn.WHITE)) {
            return Util.LEFT_ROOK_WHITE;
        } else {
            return Util.LEFT_ROOK_BLACK;
        }
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
