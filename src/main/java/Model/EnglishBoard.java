package Model;

import javafx.scene.paint.Color;

/**
 * Checkers game english board operation handling class
 */

public class EnglishBoard extends Board {

    /**
     * English board constructor
     */
    public EnglishBoard() {
        super(8, 3);
    }

    /**
     * Returning game variant method
     * @return game variant
     */
    @Override
    public String getGameVariant() {
        return "English";
    }

    /**
     * Method that assigns to each field the fields it can move on
     */

    public void addToPossibleMoves() {
        for (int x = 0; x < getSize(); x++) {
            for (int y = 0; y < getSize(); y++) {
                fields[x][y].clearPossibleMove();
                if (fields[x][y].isOccupied()) {
                    if (!fields[x][y].getPawn().isQueen()) {
                        if ((x + 1) < getSize() && (y - 1) >= 0 && !fields[x + 1][y - 1].isOccupied()) {
                            if (fields[x][y].getPawnColor().equals(Color.rgb(255, 255, 255))) {
                                fields[x][y].addToPossibleMoves(fields[x + 1][y - 1]);
                            }
                        }
                        if ((x - 1) >= 0 && (y - 1) >= 0 && !fields[x - 1][y - 1].isOccupied()) {
                            if (fields[x][y].getPawnColor().equals(Color.rgb(255, 255, 255))) {
                                fields[x][y].addToPossibleMoves(fields[x - 1][y - 1]);
                            }
                        }
                        if ((x - 1) >= 0 && (y + 1) < getSize() && !fields[x - 1][y + 1].isOccupied()) {
                            if (fields[x][y].getPawnColor().equals(Color.rgb(0, 0, 0))) {
                                fields[x][y].addToPossibleMoves(fields[x - 1][y + 1]);
                            }
                        }
                        if ((x + 1) < getSize() && (y + 1) < getSize() && !fields[x + 1][y + 1].isOccupied()) {
                            if (fields[x][y].getPawnColor().equals(Color.rgb(0, 0, 0))) {
                                fields[x][y].addToPossibleMoves(fields[x + 1][y + 1]);
                            }
                        }

                    } else {
                        if ((x + 1) < getSize() && (y - 1) >= 0 && !fields[x + 1][y - 1].isOccupied()) {
                            fields[x][y].addToPossibleMoves(fields[x + 1][y - 1]);
                        }
                        if ((x - 1) >= 0 && (y - 1) >= 0 && !fields[x - 1][y - 1].isOccupied()) {
                            fields[x][y].addToPossibleMoves(fields[x - 1][y - 1]);
                        }
                        if ((x - 1) >= 0 && (y + 1) < getSize() && !fields[x - 1][y + 1].isOccupied()) {
                            fields[x][y].addToPossibleMoves(fields[x - 1][y + 1]);
                        }
                        if ((x + 1) < getSize() && (y + 1) < getSize() && !fields[x + 1][y + 1].isOccupied()) {
                            fields[x][y].addToPossibleMoves(fields[x + 1][y + 1]);
                        }
                    }
                }
            }
        }
    }
    /**
     * Method that assigns to each field tha fields it can move on after capturing
     */
    @Override
    public void addToPossibleCaptures(String color) {
        Color playerColor = getPlayerRGBColor(color);

        for (int x = 0; x < getSize(); x++) {
            for (int y = 0; y < getSize(); y++) {
                fields[x][y].clearPossibleCaptures();
                if (fields[x][y].isOccupied()) {
                    if (!playerColor.equals(fields[x][y].getPawnColor())) {
                        continue;
                    }
                    if (!fields[x][y].getPawn().isQueen()) {
                        if ((x + 2) < getSize() && (y - 2) >= 0 && fields[x + 1][y - 1].isOccupied() && !fields[x + 2][y - 2].isOccupied()) {
                            if (fields[x][y].getPawnColor().equals(Color.rgb(255, 255, 255))) {
                                if (!fields[x + 1][y - 1].getPawnColor().equals(fields[x][y].getPawnColor())) {
                                    fields[x][y].addToPossibleCaptures(fields[x + 2][y - 2]);
                                }
                            }
                        }
                        if ((x - 2) >= 0 && (y - 2) >= 0 && fields[x - 1][y - 1].isOccupied() && !fields[x - 2][y - 2].isOccupied()) {
                            if (fields[x][y].getPawnColor().equals(Color.rgb(255, 255, 255))) {
                                if (!fields[x - 1][y - 1].getPawnColor().equals(fields[x][y].getPawnColor())) {
                                    fields[x][y].addToPossibleCaptures(fields[x - 2][y - 2]);
                                }
                            }
                        }
                        if ((x + 2) < getSize() && (y + 2) < getSize() && fields[x + 1][y + 1].isOccupied() && !fields[x + 2][y + 2].isOccupied()) {
                            if (fields[x][y].getPawnColor().equals(Color.rgb(0, 0, 0))) {
                                if (!fields[x + 1][y + 1].getPawnColor().equals(fields[x][y].getPawnColor())) {
                                    fields[x][y].addToPossibleCaptures(fields[x + 2][y + 2]);
                                }
                            }
                        }
                        if ((x - 2) >= 0 && (y + 2) < getSize() && fields[x - 1][y + 1].isOccupied() && !fields[x - 2][y + 2].isOccupied()) {
                            if (fields[x][y].getPawnColor().equals(Color.rgb(0, 0, 0))) {
                                if (!fields[x - 1][y + 1].getPawnColor().equals(fields[x][y].getPawnColor())) {
                                    fields[x][y].addToPossibleCaptures(fields[x - 2][y + 2]);
                                }
                            }
                        }
                    } else {
                        if ((x + 2) < getSize() && (y - 2) >= 0 && fields[x + 1][y - 1].isOccupied() && !fields[x + 2][y - 2].isOccupied()) {
                            if (!fields[x + 1][y - 1].getPawnColor().equals(fields[x][y].getPawnColor())) {
                                fields[x][y].addToPossibleCaptures(fields[x + 2][y - 2]);
                            }
                        }
                        if ((x - 2) >= 0 && (y - 2) >= 0 && fields[x - 1][y - 1].isOccupied() && !fields[x - 2][y - 2].isOccupied()) {
                            if (!fields[x - 1][y - 1].getPawnColor().equals(fields[x][y].getPawnColor())) {
                                fields[x][y].addToPossibleCaptures(fields[x - 2][y - 2]);
                            }
                        }
                        if ((x + 2) < getSize() && (y + 2) < getSize() && fields[x + 1][y + 1].isOccupied() && !fields[x + 2][y + 2].isOccupied()) {
                            if (!fields[x + 1][y + 1].getPawnColor().equals(fields[x][y].getPawnColor())) {
                                fields[x][y].addToPossibleCaptures(fields[x + 2][y + 2]);
                            }
                        }
                        if ((x - 2) >= 0 && (y + 2) < getSize() && fields[x - 1][y + 1].isOccupied() && !fields[x - 2][y + 2].isOccupied()) {
                            if (!fields[x - 1][y + 1].getPawnColor().equals(fields[x][y].getPawnColor())) {
                                fields[x][y].addToPossibleCaptures(fields[x - 2][y + 2]);
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Pawn capture method
     * @param x1 x-coordinate of starting position
     * @param y1 y-coordinate of starting position
     * @param x2 x-coordinate of ending position
     * @param y2 x-coordinate of ending position
     */

    public void capturePawn(int x1, int y1, int x2, int y2) {
        if (getFields()[x1][y1].isOccupied()) {
            if (getFields()[x1][y1].getPawnColor().equals(Color.rgb(0, 0, 0))) {
                numberOfWhitePawns--;
            } else {
                numberOfBlackPawns--;
            }
            getFields()[x2][y2].setPawn(getFields()[x1][y1].getPawn());
            getFields()[x1][y1].setPawn(null);
            getFields()[(x1 + x2) / 2][(y1 + y2) / 2].setPawn(null);
            createNewQueen(x2, y2);
        }
    }
}
