package Model;

import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class PolishBoard extends Board {

    public PolishBoard() {
        super(10, 4);
    }

    @Override
    public int getBoardSize() {
        return 10;
    }

    @Override
    public int getPawnRows() {
        return 4;
    }

    @Override
    public String getGameVariant() {
        return "Polish";
    }

    public List<Field> getLongestMove(String color) {
        ArrayList<Move> moves = new ArrayList<>();
        ArrayList<Integer> length = new ArrayList<>();

        for (Field f : capturePossible) {
            moves.addAll(this.longestPawnTake(f,color));
        }
        
        for (Move move : moves) {
            length.add(move.length);
        }
        moves.removeIf(move -> move.length < Collections.max(length));
        return moves.stream().map(Move::getEndField).collect(Collectors.toList());
    }

    public ArrayList<Move> longestPawnTake(Field field,String color) {
        ArrayList<Move> moves = new ArrayList<>();
        ArrayList<Integer> length = new ArrayList<>();
        for (Field f : fields[field.getX()][field.getY()].getPossibleCaptures()) {
            PolishBoard bc = (PolishBoard) this.clone();
            bc.capturePawn(field.getX(), field.getY(), f.getX(), f.getY());
            bc.addToPossibleCaptures(color);
            if (!bc.longestPawnTake(f,color).isEmpty()) {
                moves.add(new Move(field, f, 1 + bc.longestPawnTake(f,color).get(0).length));
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

    @Override
    public void fillterLongestCapture(){
        List<Field> longestMove =this.getLongestMove();

        for(Field field : capturePossible){
            field.getPossibleCaptures().removeIf(f -> !longestMove.contains(f));
        }
    }
    @Override
    public boolean checkCapture(int x1, int y1, int x2, int y2) {
        if (capturePossible.contains(this.getFields()[x1][y1])) {
            if(this.getLongestMove().contains(this.getFields()[x2][y2])) {
                return this.getFields()[x1][y1].getPossibleCaptures().contains(this.getFields()[x2][y2]);
            }
        }
        return false;
    }

    public void addToPossibleCaptures(String color) {
        Color c;
        if(color.equals("White")){
            c=Color.rgb(255,255,255);
        }
        else{
            c=Color.rgb(0,0,0);
        }
        List<Field> longestCapture = this.getLongestMove();
        for (int x = 0; x < getSize(); x++) {
            for (int y = 0; y < getSize(); y++) {
                fields[x][y].clearPossibleCaptures();
                if (fields[x][y].isOccupied()) {
                    if(!c.equals(fields[x][y].getPawnColor())){
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
}
