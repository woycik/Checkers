package Model;

import javafx.scene.paint.Color;

import java.util.ArrayList;

import static javafx.scene.paint.Color.rgb;

public class Board implements Cloneable{
    private Field[][] fields;
    private int size;

    public Board(int size) {
        this.size = size;
        this.fields = new Field[size][size];
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                this.fields[x][y] = new Field(x, y);
            }
        }
    }

    public Board(int size, int pawnRows) {
        this.size=size;
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

    @Override
    public Board clone() {
        Board boardClone = new Board(size);
        for (int i = 0; i < this.size; i++) {
            for (int j = 0; j < this.size; j++) {
                Field f = new Field(fields[i][j].getX(),fields[i][j].getY());
                f.setPawn(fields[i][j].getPawn());
                boardClone.fields[i][j] = f;

            }
        }
        return boardClone;
    }

    public Field[][] getFields() {
        return fields;
    }

    public int getSize() {
        return fields.length;
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
                        //góra prawo kłucie
                        if ((x + 2) < getSize() && (y - 2) >= 0 && fields[x + 1][y - 1].isOccupied() && !fields[x + 2][y - 2].isOccupied()) {
                            if (!fields[x + 1][y - 1].getPawnColor().equals(fields[x][y].getPawnColor())) {
                                fields[x][y].addToPossibleMoves(fields[x + 2][y - 2]);
                            }
                        }
                        //góra lewo kucie
                        if ((x - 2) >= 0 && (y - 2) >= 0 && fields[x - 1][y - 1].isOccupied() && !fields[x - 2][y - 2].isOccupied()) {
                            if (!fields[x - 1][y - 1].getPawnColor().equals(fields[x][y].getPawnColor())) {
                                fields[x][y].addToPossibleMoves(fields[x - 2][y - 2]);
                            }
                        }
                        //dół prawo kłócie
                        if ((x + 2) < getSize() && (y + 2) < getSize() && fields[x + 1][y + 1].isOccupied() && !fields[x + 2][y + 2].isOccupied()) {
                            if (!fields[x + 1][y + 1].getPawnColor().equals(fields[x][y].getPawnColor())) {
                                fields[x][y].addToPossibleMoves(fields[x + 2][y + 2]);
                            }
                        }
                        //dół lewo kłocie
                        if ((x - 2) >= 0 && (y + 2) < getSize() && fields[x - 1][y + 1].isOccupied() && !fields[x - 2][y + 2].isOccupied()) {
                            if (!fields[x - 1][y + 1].getPawnColor().equals(fields[x][y].getPawnColor())) {
                                fields[x][y].addToPossibleMoves(fields[x - 2][y + 2]);
                            }
                        }
                    }
                    else{
                    //queen bicie góra lewo
                    int currx = x;
                    int curry = y;
                    while (currx > 0 && curry > 0) {
                        currx--;
                        curry--;
                        if (!fields[currx][curry].isOccupied()) {
                            fields[x][y].addToPossibleMoves(fields[currx][curry]);
                        } else if (fields[currx][curry].isOccupied() && !fields[currx][curry].getPawnColor().equals(fields[x][y].getPawnColor())) {
                            if ((currx - 1) >= 0 && (curry - 1) >= 0 && !fields[currx - 1][curry - 1].isOccupied()) {
                                continue;
                            }
                            break;
                        } else {
                            break;
                        }
                    }
                        //queen bicie góra prawo
                        currx = x;
                        curry = y;
                        while (currx < (getSize() - 1) && curry > 0) {
                            currx++;
                            curry--;
                            if (!fields[currx][curry].isOccupied()) {
                                fields[x][y].addToPossibleMoves(fields[currx][curry]);
                            } else if (fields[currx][curry].isOccupied() && !fields[currx][curry].getPawnColor().equals(fields[x][y].getPawnColor())) {
                                if ((currx + 1) < getSize() && (curry - 1) >= 0 && !fields[currx + 1][curry - 1].isOccupied()) {
                                    continue;
                                }
                                break;
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
                            } else if (fields[currx][curry].isOccupied() && !fields[currx][curry].getPawnColor().equals(fields[x][y].getPawnColor())) {
                                if ((currx + 1) < getSize() && (curry + 1) < getSize() && !fields[currx + 1][curry + 1].isOccupied()) {
                                    continue;
                                }
                                break;
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
                            } else if (fields[currx][curry].isOccupied() && !fields[currx][curry].getPawnColor().equals(fields[x][y].getPawnColor())) {
                                if ((currx - 1) >= 0 && (curry + 1) < getSize() && !fields[currx - 1][curry + 1].isOccupied()) {
                                    continue;
                                }
                                break;
                            } else {
                                break;
                            }
                        }
                    }
                }
            }
        }
    }


    public void addToPossibleCaptures() {
        for (int x = 0; x < getSize(); x++) {
            for (int y = 0; y < getSize(); y++) {
                fields[x][y].clearPossibleCaptures();
                if (fields[x][y].isOccupied()) {
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
                    }
                    else {
                        //queen bicie góra lewo
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

                        //queen bicie góra prawo
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
                                if ((currx + 1) < getSize() && (curry + 1) > getSize() && !fields[currx + 1][curry + 1].isOccupied()) {
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
                                if ((currx - 1) >= 0 && (curry + 1) > getSize() && !fields[currx - 1][curry + 1].isOccupied()) {
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
        if(this.getFields()[x1][y1].isOccupied()) {
            if (!this.getFields()[x1][y1].getPawn().isQueen()) {
                this.getFields()[x2][y2].setPawn(this.getFields()[x1][y1].getPawn());
                this.getFields()[x1][y1].setPawn(null);
                this.getFields()[(x1 + x2) / 2][(y1 + y2) / 2].setPawn(null);
                this.getFields()[x1][y1].setPawn(null);

                int diffX;
                int diffY;

                diffX = x2 - x1;
                diffY = y2 - y1;


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
        }
    }



    public int longestPawnTake(int x, int y) {
        ArrayList<Integer> moveLengths = new ArrayList<>();
       for(Field f : fields[x][y].getPossibleCaptures()){
           Board bc =this.clone();
           bc.capturePawn(x,y,f.getX(),f.getY());
           moveLengths.add(1 + bc.longestPawnTake(f.getX(),f.getY()));
           System.out.println(1+bc.longestPawnTake(f.getX(),f.getY()));
       }

        int maxLength = 0;
        for (Integer moveLengthElement : moveLengths) {
            maxLength = Math.max(maxLength, moveLengthElement);
        }

        return maxLength;
    }
}
