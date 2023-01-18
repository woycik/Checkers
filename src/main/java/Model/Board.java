package Model;

import javafx.scene.paint.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static javafx.scene.paint.Color.rgb;

/**
 * Checkers game board operation handling class
 */

public class Board implements Cloneable {
    public List<Field> blackPawns;
    public List<Field> whitePawns;
    public final List<Field> capturePossible;
    protected int numberOfWhitePawns;
    protected int numberOfBlackPawns;
    protected final Field[][] fields;
    private final int size;
    private final int pawnRows;

    /**
     * Board constructor
     * @param size board size
     */
    public Board(int size) {
        this.size = size;
        this.pawnRows = 0;
        this.fields = new Field[size][size];
        this.capturePossible = new ArrayList<>();
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                this.fields[x][y] = new Field(x, y);
            }
        }
    }

    /**
     * Board constructor
     * @param size board size
     * @param pawnRows number od pawn rows
     */

    public Board(int size, int pawnRows) {
        this.size = size;
        this.pawnRows = pawnRows;
        this.numberOfBlackPawns = getSize() / 2 * getPawnRows();
        this.numberOfWhitePawns = getSize() / 2 * getPawnRows();
        this.blackPawns = new ArrayList<>();
        this.whitePawns = new ArrayList<>();
        this.capturePossible = new ArrayList<>();
        this.fields = new Field[size][size];
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                fields[x][y] = new Field(x, y);
                if ((x + y) % 2 == 0 && y < pawnRows) {
                    fields[x][y].setPawn(new Pawn(rgb(0, 0, 0)));
                } else if ((x + y) % 2 == 0 && y > size - pawnRows - 1) {
                    fields[x][y].setPawn(new Pawn(rgb(255, 255, 255)));
                }
            }
        }
    }

    /**
     * Fields returning method
     * @return Field[][]
     */

    public Field[][] getFields() {
        return fields;
    }

    /**
     * Board size returning method
     * @return int
     */

    public int getSize() {
        return size;
    }

    /**
     * Method that returns number of pawn rows
     * @return int
     */

    public int getPawnRows() {
        return pawnRows;
    }

    /**
     * Check if capture is possible
     * @return boolean
     */

    public boolean isCapturePossible() {
        return capturePossible.size() > 0;
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
                        calculatePossibleMoves(1,-1,x,y);
                        calculatePossibleMoves(-1,-1,x,y);
                        calculatePossibleMoves(1,1,x,y);
                        calculatePossibleMoves(-1,1,x,y);

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

    public void calculatePossibleMoves(int signX,int signY,int x,int y){
        if (isFieldInBoard(x+signX,y+signY) && !fields[x + signX][y + signY].isOccupied()) {
            fields[x][y].addToPossibleMoves(fields[x + signX][y + signY]);
        }
    }

    public void calculatePossibleMovesForQueen(int signX,int signY,int x,int y){
        int currx = x;
        int curry = y;
        while (isFieldInBoard(currx+signX,curry+signY)) {
            currx=currx + signX;
            curry=curry + signY;
            if (!fields[currx][curry].isOccupied()) {
                fields[x][y].addToPossibleMoves(fields[currx][curry]);
            } else {
                break;
            }
        }
    }

    /**
     * Method that assigns to each field tha fields it can move on after capturing
     */
    public void addToPossibleCaptures(String color) {
        Color playerColor = getPlayerRGBColor(color);

            for (int x = 0; x < getSize(); x++) {
                for (int y = 0; y < getSize(); y++) {
                    fields[x][y].clearPossibleCaptures();
                    if (fields[x][y].isOccupied()) {
                        if(!playerColor.equals(fields[x][y].getPawnColor())){
                            continue;
                        }
                        if (!fields[x][y].getPawn().isQueen()) {
                            this.calculatePossibleFields(1,-1,x,y);
                            this.calculatePossibleFields(-1,-1,x,y);
                            this.calculatePossibleFields(1,1,x,y);
                            this.calculatePossibleFields(-1,1,x,y);
                        } else {

                            this.calculatePossibleFieldsForQueen(-1,-1,x,y);
                            this.calculatePossibleFieldsForQueen(1,-1,x,y);
                            this.calculatePossibleFieldsForQueen(1,1,x,y);
                            this.calculatePossibleFieldsForQueen(-1,1,x,y);

                        }
                    }
                }
            }
        }

        public boolean isFieldInBoard(int x,int y){
            return x < getSize() && y < getSize() && x >= 0 && y >= 0;
        }

        public void calculatePossibleFields(int signX,int signY,int x,int y){
            if (isFieldInBoard(x+2*signX,y+2*signY) && fields[x + signX][y + signY].isOccupied() && !fields[x + 2*signX][y +2*signY].isOccupied()) {
                if (!fields[x + signX][y + signY].getPawnColor().equals(fields[x][y].getPawnColor())) {
                    fields[x][y].addToPossibleCaptures(fields[x + 2*signX][y + 2*signY]);
                }
            }
        }


        public void calculatePossibleFieldsForQueen(int singX,int signY,int x,int y){
            int currX = x;
            int currY = y;
            int stateOfCaptures = 0;
            while (isFieldInBoard(currX+singX,currY+signY)) {
                currX = currX + singX;
                currY = currY + signY;
                if (!fields[currX][currY].isOccupied() && stateOfCaptures == 0) {
                    continue;
                }
                if (!fields[currX][currY].isOccupied() && stateOfCaptures == 1) {

                    fields[x][y].addToPossibleCaptures(fields[currX][currY]);

                } else if (fields[currX][currY].isOccupied() && !fields[currX][currY].getPawnColor().equals(fields[x][y].getPawnColor())) {
                    if (isFieldInBoard(currX+singX,currY+signY) && !fields[currX + singX][currY + signY].isOccupied()) {
                        stateOfCaptures++;
                    }
                    else{
                        break;
                    }

                } else {
                    break;
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
        if (this.getFields()[x1][y1].isOccupied()) {
            if (this.getFields()[x1][y1].getPawnColor().equals(Color.rgb(0, 0, 0))) {
                numberOfWhitePawns--;
            } else {
                numberOfBlackPawns--;
            }
            if (!this.getFields()[x1][y1].getPawn().isQueen()) {
                this.getFields()[x2][y2].setPawn(this.getFields()[x1][y1].getPawn());
                this.getFields()[x1][y1].setPawn(null);
                this.getFields()[(x1 + x2) / 2][(y1 + y2) / 2].setPawn(null);
            } else {
                int diffX;
                int diffY;

                diffX = x2 - x1;
                diffY = y2 - y1;
                this.getFields()[x2][y2].setPawn(this.getFields()[x1][y1].getPawn());
                this.getFields()[x1][y1].setPawn(null);

                if (diffY == diffX || -diffY == diffX) {
                    if (diffY > 0 && diffX > 0) {
                        capture(1,1,Math.abs(diffX),x1,y1);
                    }
                    if (diffY > 0 && diffX < 0) {
                        capture(-1,1,Math.abs(diffX),x1,y1);
                    }
                    if (diffY < 0 && diffX < 0) {
                        capture(-1,-1,Math.abs(diffX),x1,y1);
                    }
                    if (diffY < 0 && diffX > 0) {
                        capture(1,-1,Math.abs(diffX),x1,y1);
                    }
                }
            }
            this.createNewQueen(x2, y2);
        }
    }

    public void capture(int signX,int signY,int diff,int x,int y){
        for (int i = 1; i < diff; i++) {
            if (this.getFields()[x + (signX*i)][y + (signY* i)].isOccupied()) {
                this.getFields()[x + (signX*i)][y + (signY* i)].setPawn(null);
                break;
            }
        }
    }

    /**
     * Method that creates a new queen on board
     * @param x x-coordinate of pawn position
     * @param y y-coordinate of pawn position
     */

    public void createNewQueen(int x, int y) {
        if (y == 0) {
            if (!this.getFields()[x][y].getPawnColor().equals(Color.rgb(0, 0, 0))) {
                this.getFields()[x][y].getPawn().makeQueen();
            }
        } else if (y == this.getSize() - 1) {
            if (!this.getFields()[x][y].getPawnColor().equals(Color.rgb(255, 255, 255))) {
                this.getFields()[x][y].getPawn().makeQueen();
            }
        }
    }

    /**
     * Assigning to the list all fields from which capturing is possible
     * @param typeOfPawns whitePawns/blackPawns
     */
    public void captureFieldList(List<Field> typeOfPawns) {
        for (Field boardField : typeOfPawns) {
            if (boardField.isOccupied()) {
                if (boardField.getPossibleCaptures().size() > 0) {
                    this.capturePossible.add(boardField);
                }
            }
        }
        this.removePawnsFromList();
    }

    /**
     * Method that clear blackPawns list and whitePans list
     */

    public void removePawnsFromList() {
        blackPawns.clear();
        whitePawns.clear();
    }

    /**
     * Method that separates pawns of different colors and assigns them to the appropriate list
     */

    public void setMyPawns() {
        for (int i = 0; i < this.getSize(); i++) {
            for (int j = 0; j < this.getSize(); j++) {
                if (this.getFields()[i][j].getPawn() != null) {
                    if (this.getFields()[i][j].getPawn().getColor().equals(Color.rgb(0, 0, 0))) {
                        blackPawns.add(this.getFields()[i][j]);

                    } else if (this.getFields()[i][j].getPawn().getColor().equals(Color.rgb(255, 255, 255))) {
                        whitePawns.add(this.getFields()[i][j]);
                    }
                }
            }
        }
    }

    /**
     * Check whether capture from starting field to ending field is possible
     * @param x1 x-coordinate of pawn starting position
     * @param y1 y-coordinate of pawn starting position
     * @param x2 x-coordinate of pawn ending position
     * @param y2 y-coordinate of pawn ending position
     * @return true if  this capture is legal
     */

    public boolean checkCapture(int x1, int y1, int x2, int y2) {
        if (capturePossible.contains(this.getFields()[x1][y1])) {
                return this.getFields()[x1][y1].getPossibleCaptures().contains(this.getFields()[x2][y2]);
        }
        return false;
    }

    /**
     * Check whether capture from starting field to ending field is possible and it's the best one
     * @param x1 x-coordinate of pawn starting position
     * @param y1 y-coordinate of pawn starting position
     * @param x2 x-coordinate of pawn ending position
     * @param y2 y-coordinate of pawn ending position
     * @return true if  this capture is legal
     */

    public boolean checkCapture(int x1, int y1, int x2, int y2, String color) {
        if (capturePossible.contains(getFields()[x1][y1])) {
            if (getLongestCaptures(color).contains(getFields()[x2][y2])) {
                return getFields()[x1][y1].getPossibleCaptures().contains(getFields()[x2][y2]);
            }
        }
        return false;
    }




    /**
     * Check whether move is possible
     * @param x1 x-coordinate of pawn starting position
     * @param y1 y-coordinate of pawn starting position
     * @param x2 x-coordinate of pawn ending position
     * @param y2 y-coordinate of pawn starting position
     * @return true if this move is legal
     */
    public boolean isMoveLegal(int x1, int y1, int x2, int y2) {
        if (this.getFields()[x1][y1].getPawn() != null) {
            if (x2 < this.getSize() && x2 >= 0 && y2 < this.getSize() && y2 >= 0) {
                if (!this.getFields()[x2][y2].isOccupied()) {
                    return this.getFields()[x1][y1].getPossibleMoves().contains(this.getFields()[x2][y2]);
                }
            }
        }
        return false;
    }

    /**
     * Pawn move method
     * @param x1 x-coordinate of pawn starting position
     * @param y1 y-coordinate of pawn starting position
     * @param x2 x-coordinate of pawn ending position
     * @param y2 y-coordinate of pawn starting position
     * @return true if move was done correctly
     */

    public boolean movePawn(int x1, int y1, int x2, int y2) {
        if (this.getFields()[x1][y1].isOccupied()) {
            this.getFields()[x2][y2].setPawn(this.getFields()[x1][y1].getPawn());
            this.getFields()[x1][y1].setPawn(null);
            this.createNewQueen(x2, y2);
            return true;
        }
        return false;
    }

    /**
     * Check whether this pawn can capture one more time
     * @param x x-coordinate of pawn position
     * @param y y-coordinate of pawn position
     * @param color pawn color
     * @return true if capture is possible one more time
     */
    public boolean canICaptureOneMoreTime(int x, int y,String color) {
        this.addToPossibleCaptures(color);
        return (this.getFields()[x][y].getPossibleCaptures().size() > 0);
    }

    /**
     * Method that removes from the capture list all fields that do not make the best capture
     * @param color
     * @return list of fields that makes the longest capture
     */

    public List<Field> getLongestCaptures(String color) {
        List<Move> moves = new ArrayList<>();
        List<Integer> length = new ArrayList<>();

        for (Field f : capturePossible) {
            moves.addAll(getLongestPawnCaptures(f, color));
        }

        for (Move move : moves) {
            length.add(move.length);
        }
        moves.removeIf(move -> move.length < Collections.max(length));
        return moves.stream().map(Move::getEndField).collect(Collectors.toList());
    }

    /**
     * Returns list of fields that makes the longest capture
     * @param field
     * @param color
     * @return list of fields that makes the longest capture
     */
    public List<Move> getLongestPawnCaptures(Field field, String color) {
        List<Move> moves = new ArrayList<>();
        List<Integer> length = new ArrayList<>();
        for (Field f : fields[field.getX()][field.getY()].getPossibleCaptures()) {
            PolishBoard bc = (PolishBoard) clone();
            bc.capturePawn(field.getX(), field.getY(), f.getX(), f.getY());
            bc.addToPossibleCaptures(color);
            if (!bc.getLongestPawnCaptures(f, color).isEmpty()) {
                moves.add(new Move(field, f, 1 + bc.getLongestPawnCaptures(f, color).get(0).length));
            } else {
                moves.add(new Move(f, f, 0));
            }

        }
        for (Move move : moves) {
            length.add(move.length);
        }
        moves.removeIf(move -> move.length < Collections.max(length));
        return moves;
    }

    /**
     *
     * Method that removes from the list of captures all fields that do not make the best capture
     * @param color
     */
    public void filterLongestCaptures(String color) {
        List<Field> longestCaptures = getLongestCaptures(color);

        for (Field field : capturePossible) {
            field.getPossibleCaptures().removeIf(f -> !longestCaptures.contains(f));
        }
    }

    /**
     * Number of white pawns returning method
     * @return number of white pawns
     */

    public int getNumberOfWhitePawns() {
        return numberOfWhitePawns;
    }

    /**
     * Number of black pawns returning method
     * @return number of black pawns
     */

    public int getNumberOfBlackPawns() {
        return numberOfBlackPawns;
    }

    /**
     * Clones the instance of PolishBoard
     * @return Board
     */

    @Override
    public Board clone() {
        BoardFactory boardFactory = new BoardFactory();
        Board boardClone = boardFactory.createBoard(getGameVariant());
        for (int x = 0; x < getSize(); x++) {
            for (int y = 0; y < getSize(); y++) {
                Field f = new Field(fields[x][y].getX(), fields[x][y].getY());
                f.setPawn(fields[x][y].getPawn());
                boardClone.fields[x][y] = f;
            }
        }
        return boardClone;
    }

    /**
     * Player color returning method
     * @param playerColor
     * @return player's pawns color
     */

    public Color getPlayerRGBColor(String playerColor) {
        if (playerColor.equals("White")) {
            return Color.rgb(255, 255, 255);
        } else {
            return Color.rgb(0, 0, 0);
        }
    }

    /**
     * Game variant returning method
     * @return game variant
     */
    public String getGameVariant() {
        return "None";
    }
}
