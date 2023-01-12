package Model;

import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static javafx.scene.paint.Color.rgb;

public class Board implements Cloneable {
    public List<Field> blackPawns;
    public List<Field> whitePawns;
    public final List<Field> capturePossible;
    protected int numberOfWhitePawns;
    protected int numberOfBlackPawns;
    protected final Field[][] fields;
    private final int size;
    private final int pawnRows;


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

    public Field[][] getFields() {
        return fields;
    }

    public int getSize() {
        return size;
    }

    public int getPawnRows() {
        return pawnRows;
    }

    public boolean isCapturePossible() {
        return capturePossible.size() > 0;
    }

    public void addToPossibleMoves() {
        for (int x = 0; x < getSize(); x++) {
            for (int y = 0; y < getSize(); y++) {
                fields[x][y].clearPossibleMove();
                if (fields[x][y].isOccupied()) {
                    if (!fields[x][y].getPawn().isQueen()) {
                        //góra prawo normalny ruch
                        if ((x + 1) < getSize() && (y - 1) >= 0 && !fields[x + 1][y - 1].isOccupied()) {
                            if (fields[x][y].getPawnColor().equals(Color.rgb(255, 255, 255))) {
                                fields[x][y].addToPossibleMoves(fields[x + 1][y - 1]);
                            }
                        }
                        //góra lewo normalny ruch
                        if ((x - 1) >= 0 && (y - 1) >= 0 && !fields[x - 1][y - 1].isOccupied()) {
                            if (fields[x][y].getPawnColor().equals(Color.rgb(255, 255, 255))) {
                                fields[x][y].addToPossibleMoves(fields[x - 1][y - 1]);
                            }
                        }
                        //dół lewo normalny ruch
                        if ((x - 1) >= 0 && (y + 1) < getSize() && !fields[x - 1][y + 1].isOccupied()) {
                            if (fields[x][y].getPawnColor().equals(Color.rgb(0, 0, 0))) {
                                fields[x][y].addToPossibleMoves(fields[x - 1][y + 1]);
                            }
                        }
                        //dół prawo normalny ruch
                        if ((x + 1) < getSize() && (y + 1) < getSize() && !fields[x + 1][y + 1].isOccupied()) {
                            if (fields[x][y].getPawnColor().equals(Color.rgb(0, 0, 0))) {
                                fields[x][y].addToPossibleMoves(fields[x + 1][y + 1]);
                            }
                        }

                    } else {
                        //queen  góra lewo
                        int currx = x;
                        int curry = y;
                        while (currx > 0 && curry > 0) {
                            currx--;
                            curry--;
                            if (!fields[currx][curry].isOccupied()) {
                                fields[x][y].addToPossibleMoves(fields[currx][curry]);
                            } else {
                                break;
                            }
                        }

                        //queen góra prawo
                        currx = x;
                        curry = y;
                        while (currx < (getSize() - 1) && curry > 0) {
                            currx++;
                            curry--;
                            if (!fields[currx][curry].isOccupied()) {
                                fields[x][y].addToPossibleMoves(fields[currx][curry]);
                            } else {
                                break;
                            }
                        }
                        //queen bicie w  prawo dół
                        currx = x;
                        curry = y;
                        while (currx < (getSize() - 1) && curry < (getSize() - 1)) {
                            currx++;
                            curry++;
                            if (!fields[currx][curry].isOccupied()) {
                                fields[x][y].addToPossibleMoves(fields[currx][curry]);
                            } else {
                                break;
                            }
                        }
                        //queen bicie lewo dół
                        currx = x;
                        curry = y;
                        while (currx > 0 && curry < (getSize() - 1)) {
                            currx--;
                            curry++;
                            if (!fields[currx][curry].isOccupied()) {
                                fields[x][y].addToPossibleMoves(fields[currx][curry]);
                            } else {
                                break;
                            }
                        }
                    }
                }
            }
        }
    }


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
                        //góra prawo kłucie
                        if ((x + 2) < getSize() && (y - 2) >= 0 && fields[x + 1][y - 1].isOccupied() && !fields[x + 2][y - 2].isOccupied()) {
                            if (!fields[x + 1][y - 1].getPawnColor().equals(fields[x][y].getPawnColor())) {
                                fields[x][y].addToPossibleCaptures(fields[x + 2][y - 2]);
                            }
                        }
                        //góra lewo kucie
                        if ((x - 2) >= 0 && (y - 2) >= 0 && fields[x - 1][y - 1].isOccupied() && !fields[x - 2][y - 2].isOccupied()) {
                            if (!fields[x - 1][y - 1].getPawnColor().equals(fields[x][y].getPawnColor())) {
                                fields[x][y].addToPossibleCaptures(fields[x - 2][y - 2]);
                            }
                        }
                        //dół prawo kłócie
                        if ((x + 2) < getSize() && (y + 2) < getSize() && fields[x + 1][y + 1].isOccupied() && !fields[x + 2][y + 2].isOccupied()) {
                            if (!fields[x + 1][y + 1].getPawnColor().equals(fields[x][y].getPawnColor())) {
                                fields[x][y].addToPossibleCaptures(fields[x + 2][y + 2]);
                            }
                        }
                        //dół lewo kłocie
                        if ((x - 2) >= 0 && (y + 2) < getSize() && fields[x - 1][y + 1].isOccupied() && !fields[x - 2][y + 2].isOccupied()) {
                            if (!fields[x - 1][y + 1].getPawnColor().equals(fields[x][y].getPawnColor())) {
                                fields[x][y].addToPossibleCaptures(fields[x - 2][y + 2]);
                            }
                        }
                    } else {

                        int currx = x;
                        int curry = y;
                        int stateOfCaptures = 0;
                        while (currx > 0 && curry > 0) {
                            currx--;
                            curry--;
                            if (!fields[currx][curry].isOccupied() && stateOfCaptures == 0) {
                                continue;
                            }
                            if (!fields[currx][curry].isOccupied() && stateOfCaptures == 1) {
                                fields[x][y].addToPossibleCaptures(fields[currx][curry]);
                            } else if (fields[currx][curry].isOccupied() && !fields[currx][curry].getPawnColor().equals(fields[x][y].getPawnColor())) {
                                if ((currx - 1) >= 0 && (curry - 1) >= 0 && !fields[currx - 1][curry - 1].isOccupied()) {
                                    stateOfCaptures++;
                                }

                            } else {
                                break;
                            }
                        }

                        currx = x;
                        curry = y;
                        stateOfCaptures = 0;
                        while (currx < (getSize() - 1) && curry > 0) {
                            currx++;
                            curry--;
                            if (!fields[currx][curry].isOccupied() && stateOfCaptures == 0) {
                                continue;
                            }
                            if (!fields[currx][curry].isOccupied() && stateOfCaptures == 1) {
                                fields[x][y].addToPossibleCaptures(fields[currx][curry]);
                            } else if (fields[currx][curry].isOccupied() && !fields[currx][curry].getPawnColor().equals(fields[x][y].getPawnColor())) {
                                if ((currx + 1) < getSize() && (curry - 1) >= 0 && !fields[currx + 1][curry - 1].isOccupied()) {
                                    stateOfCaptures++;
                                }

                            } else {
                                break;
                            }
                        }

                        //queen bicie dół prawo
                        currx = x;
                        curry = y;
                        stateOfCaptures = 0;
                        while (currx < (getSize() - 1) && curry < (getSize() - 1)) {
                            currx++;
                            curry++;
                            if (!fields[currx][curry].isOccupied() && stateOfCaptures == 0) {
                                continue;
                            }
                            if (!fields[currx][curry].isOccupied() && stateOfCaptures == 1) {
                                fields[x][y].addToPossibleCaptures(fields[currx][curry]);
                            } else if (fields[currx][curry].isOccupied() && !fields[currx][curry].getPawnColor().equals(fields[x][y].getPawnColor())) {
                                if ((currx + 1) < getSize() && (curry + 1) < getSize() && !fields[currx + 1][curry + 1].isOccupied()) {
                                    stateOfCaptures++;
                                }

                            } else {
                                break;
                            }
                        }

                        //queen bicie dół lewo
                        currx = x;
                        curry = y;
                        stateOfCaptures = 0;
                        while (currx > 0 && curry < (getSize() - 1)) {
                            currx--;
                            curry++;
                            if (!fields[currx][curry].isOccupied() && stateOfCaptures == 0) {
                                continue;
                            }
                            if (!fields[currx][curry].isOccupied() && stateOfCaptures == 1) {
                                fields[x][y].addToPossibleCaptures(fields[currx][curry]);
                            } else if (fields[currx][curry].isOccupied() && !fields[currx][curry].getPawnColor().equals(fields[x][y].getPawnColor())) {
                                if ((currx - 1) >= 0 && (curry + 1) < getSize() && !fields[currx - 1][curry + 1].isOccupied()) {
                                    stateOfCaptures++;
                                }

                            } else {
                                break;
                            }
                        }
                    }
                }
            }
        }
    }


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
                        for (int i = 1; i < diffX; i++) {
                            if (this.getFields()[x1 + i][y1 + i].isOccupied()) {
                                this.getFields()[x1 + i][y1 + i].setPawn(null);
                                break;
                            }
                        }
                    }
                    if (diffY > 0 && diffX < 0) {
                        for (int i = 1; i < Math.abs(diffX); i++) {
                            if (this.getFields()[x1 - i][y1 + i].isOccupied()) {
                                this.getFields()[x1 - i][y1 + i].setPawn(null);
                                break;
                            }
                        }
                    }
                    if (diffY < 0 && diffX < 0) {
                        for (int i = 1; i < Math.abs(diffX); i++) {
                            if (this.getFields()[x1 - i][y1 - i].isOccupied()) {
                                this.getFields()[x1 - i][y1 - i].setPawn(null);
                                break;
                            }
                        }
                    }
                    if (diffY < 0 && diffX > 0) {
                        for (int i = 1; i < diffX; i++) {
                            if (this.getFields()[x1 + i][y1 - i].isOccupied()) {
                                this.getFields()[x1 + i][y1 - i].setPawn(null);
                                break;
                            }
                        }
                    }
                }
            }
            this.createNewQueen(x2, y2);
        }
    }

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

    public void removePawnsFromList() {
        blackPawns.clear();
        whitePawns.clear();
    }

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

    public boolean checkCapture(int x1, int y1, int x2, int y2) {
        if (capturePossible.contains(this.getFields()[x1][y1])) {
                return this.getFields()[x1][y1].getPossibleCaptures().contains(this.getFields()[x2][y2]);
        }
        return false;
    }

    public boolean checkCapture(int x1, int y1, int x2, int y2, String color) {
        if (capturePossible.contains(getFields()[x1][y1])) {
            if (getLongestCaptures(color).contains(getFields()[x2][y2])) {
                return getFields()[x1][y1].getPossibleCaptures().contains(getFields()[x2][y2]);
            }
        }
        return false;
    }

    public void filterLongestCaptures(String color) {
        List<Field> longestCaptures = getLongestCaptures(color);

        for (Field field : capturePossible) {
            field.getPossibleCaptures().removeIf(f -> !longestCaptures.contains(f));
        }
    }

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

    public boolean movePawn(int x1, int y1, int x2, int y2) {
        if (this.getFields()[x1][y1].isOccupied()) {
            this.getFields()[x2][y2].setPawn(this.getFields()[x1][y1].getPawn());
            this.getFields()[x1][y1].setPawn(null);
            this.createNewQueen(x2, y2);
            return true;
        }
        return false;
    }

    public boolean canICaptureOneMoreTime(int x, int y,String color) {
        this.addToPossibleCaptures(color);
        return (this.getFields()[x][y].getPossibleCaptures().size() > 0);
    }

    public int getNumberOfWhitePawns() {
        return numberOfWhitePawns;
    }

    public int getNumberOfBlackPawns() {
        return numberOfBlackPawns;
    }

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

    public Color getPlayerRGBColor(String playerColor) {
        if (playerColor.equals("White")) {
            return Color.rgb(255, 255, 255);
        } else {
            return Color.rgb(0, 0, 0);
        }
    }

    public String getGameVariant() {
        return "None";
    }
}
