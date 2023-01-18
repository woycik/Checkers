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
                        calculatePossibleMoves(1,x,y);

                        calculatePossibleMoves(-1,x,y);

                    } else {
                        calculatePossibleMovesForQueen(1,-1,x,y);
                        calculatePossibleMovesForQueen(-1,-1,x,y);
                        calculatePossibleMovesForQueen(-1,1,x,y);
                        calculatePossibleMovesForQueen(1,1,x,y);
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
                        calculatePossibleCaptures(1,x,y);
                        calculatePossibleCaptures(-1,x,y);
                    } else {
                        calculatePossibleCapturesForQueen(1,-1,x,y);
                        calculatePossibleCapturesForQueen(-1,-1,x,y);
                        calculatePossibleCapturesForQueen(1,1,x,y);
                        calculatePossibleCapturesForQueen(-1,1,x,y);
                    }
                }
            }
        }
    }


    /**
     * Check if field is in board
     * @param x x-coordinate of field
     * @param y y-coordinate of field
     * @return true if selected field is inside board
     */
    public boolean isFieldInBoard(int x,int y){
        return x < getSize() && y < getSize() && x >= 0 && y >= 0;
    }

    /**
     * Calculate possible moves for every field method
     * @param signX 1 or -1
     * @param x x-coordinate of field
     * @param y y-coordinate of field
     */

    public void calculatePossibleMoves(int signX,int x,int y) {
        if (fields[x][y].getPawnColor().equals(Color.rgb(255, 255, 255))) {
            if (isFieldInBoard(x + signX, y -1) && !fields[x + signX][y -1].isOccupied()) {
                fields[x][y].addToPossibleMoves(fields[x + signX][y-1]); //jedne i obojetnie
            }
        } else {
            if (isFieldInBoard(x - signX, y +1) && !fields[x - signX][y +1].isOccupied()) {
                fields[x][y].addToPossibleMoves(fields[x - signX][y + 1]);
            }
        }

    }

    /**
     * Calculate possible captures for every field method
     * @param signX -1 or 1
     * @param signY -1 or 1
     * @param x x-coordinate of field
     * @param y y-coordinate of field
     */
    public void calculatePossibleMovesForQueen(int signX,int signY,int x,int y){
        if (isFieldInBoard(x+signX,y+signY) && !fields[x + signX][y + signY].isOccupied()) {
            fields[x][y].addToPossibleMoves(fields[x + signX][y + signY]);
        }
    }

    /**
     * Calculate possible captures for every queen field method
     * @param signX -1 or 1
     * @param signY -1 or 1
     * @param x x-coordinate of field
     * @param y y-coordinate of field
     */
    public void calculatePossibleCapturesForQueen(int signX,int signY,int x,int y) {
        if (isFieldInBoard(x + 2 * signX, y + 2 * signY) && fields[x + signX][y + signY].isOccupied() && !fields[x + 2 * signX][y + 2 * signY].isOccupied()) {
            if (!fields[x + signX][y + signY].getPawnColor().equals(fields[x][y].getPawnColor())) {
                fields[x][y].addToPossibleCaptures(fields[x + 2 * signX][y + 2 * signY]);
            }
        }
    }

    /**
     * Caltulate possible captures for every field
     * @param signX -1 or 1
     * @param x x-coordinate of field
     * @param y y-coordinate of field
     */
    public  void calculatePossibleCaptures(int signX,int x,int y) {
        if (fields[x][y].getPawnColor().equals(Color.rgb(255, 255, 255))) {
            if (isFieldInBoard(x + 2 * signX, y - 2 ) && fields[x + signX][y -1].isOccupied() && !fields[x + 2 * signX][y - 2 ].isOccupied()) {
                if (!fields[x + signX][y -1].getPawnColor().equals(fields[x][y].getPawnColor())) {
                    fields[x][y].addToPossibleCaptures(fields[x + 2 * signX][y - 2 ]);
                }
            }
        } else {
            if (isFieldInBoard(x - 2 * signX, y + 2 ) && fields[x - signX][y +1].isOccupied() && !fields[x - 2 * signX][y + 2 ].isOccupied()) {
                if (!fields[x - signX][y +1].getPawnColor().equals(fields[x][y].getPawnColor())) {
                    fields[x][y].addToPossibleCaptures(fields[x - 2 * signX][y + 2 ]);
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
