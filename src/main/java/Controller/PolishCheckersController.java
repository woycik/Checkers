package Controller;

import Model.Board;
import Model.Field;
import Model.PlayerTurn;
import javafx.scene.paint.Color;
import java.util.ArrayList;

public class PolishCheckersController extends GameController{

    @Override
    public boolean makeMove(int x1, int y1, int x2, int y2) throws CloneNotSupportedException {
        this.setMyPawns();
        this.board.addToPossibleMoves();
        this.board.addToPossibleCaptures();
        if (!finishCapture) {
            this.capturePossible.clear();
            if (playerTurn == PlayerTurn.Black) {this.captureFieldList(blackPawns);}
            else {this.captureFieldList(whitePawns);}
            if (this.isCapturePossible()) {
                capturePossible = this.getLongestMove();
                if (this.checkCapture(x1, y1, x2, y2)) {
                    this.board.capturePawn(x1, y1, x2, y2);
                    this.createNewQueen(x2,y2);
                    this.capturePossible.clear();
                    if (this.canICaptureOneMoreTime(x2, y2)) {
                        capturePossible.add(board.getFields()[x2][y2]);
                        finishCapture = true;
                        return false;
                    }
                    return true;
                }
                return false;
            } else {
                if (this.isMoveLegal(x1, y1, x2, y2)) {
                    this.createNewQueen(x2,y2);
                    return this.movePawn(x1, y1, x2, y2);
                }
            }
        } else {
            if (this.checkCapture(x1, y1, x2, y2)) {
                this.board.capturePawn(x1, y1, x2, y2);
                this.capturePossible.clear();
                if (this.canICaptureOneMoreTime(x2, y2)) {
                    capturePossible.add(board.getFields()[x2][y2]);
                    finishCapture = true;
                    return false;
                }
                finishCapture = false;
                return true;
            }

        }
        return false;
    }


    public void captureFieldList(ArrayList<Field> typeOfPawns) {
        for (Field boardField : typeOfPawns) {
            if (boardField.isOccupied()) {
                if(boardField.getPossibleCaptures().size()>0){
                    this.capturePossible.add(boardField);
                }
            }
        }
        this.removePawnsFromList();
    }


    public boolean checkCapture(int x1, int y1, int x2, int y2) {
        if (capturePossible.contains(board.getFields()[x1][y1])) {
            return board.getFields()[x1][y1].getPossibleCaptures().contains(board.getFields()[x2][y2]);
        }
        return false;
    }


    @Override
    public boolean isMoveLegal(int x1, int y1, int x2, int y2) {
        if (board.getFields()[x1][y1].getPawn() != null) {
            if (x2 < this.getBoardSize() && x2 >= 0 && y2 < this.getBoardSize() && y2 >= 0) {
                if (!board.getFields()[x2][y2].isOccupied()) {
                    return board.getFields()[x1][y1].getPossibleMoves().contains(board.getFields()[x2][y2]);
                }
            }
        }
        return false;
    }

    public ArrayList<Field> getLongestMove() {
        int max = 0;
        ArrayList<Field> maxFields = new ArrayList<>();
        for (Field f : capturePossible){
            int maxValue = board.longestPawnTake(f.getX(),f.getY());
            if(maxValue>max){
                max = maxValue;
                maxFields.clear();
                maxFields.add(f);
            }
            if(maxValue==max){
                maxFields.add(f);
            }
        }
        return maxFields;
    }


    public boolean canICaptureOneMoreTime(int x, int y) {
        this.board.addToPossibleCaptures();
        return (board.getFields()[x][y].getPossibleCaptures().size() > 0);
    }

    @Override
    public int getBoardSize() {
        return 10;
    }

    @Override
    public int getPawnRows() {
        return 4;
    }
}
