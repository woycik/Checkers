package Controller;

import Model.*;
import javafx.scene.paint.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class PolishCheckersController extends GameController{

    @Override
    public boolean makeMove(int x1, int y1, int x2, int y2)  {
        this.board = new PolishBoard();
        this.board.setMyPawns();
        this.board.addToPossibleMoves();
        this.board.addToPossibleCaptures();
        if (!finishCapture) {
            this.board.capturePossible.clear();
            if (playerTurn == PlayerTurn.Black) {this.board.captureFieldList(blackPawns);}
            else {this.board.captureFieldList(whitePawns);}
            if (this.board.isCapturePossible()) {
                if(!this.board.getLongestMove().contains(board.getFields()[x2][y2])){
                    return false;
                }
                if (this.board.checkCapture(x1, y1, x2, y2)) {
                    this.board.capturePawn(x1, y1, x2, y2);
                    this.board.createNewQueen(x2,y2);
                    this.board.capturePossible.clear();
                    if (this.board.canICaptureOneMoreTime(x2, y2)) {
                        this.board.capturePossible.add(board.getFields()[x2][y2]); //zmien na settera
                        finishCapture = true;
                        return false;
                    }
                    return true;
                }
                return false;
            } else {
                if (this.board.isMoveLegal(x1, y1, x2, y2)) {
                    return this.board.movePawn(x1, y1, x2, y2);
                }
            }
        } else {
            if (this.board.checkCapture(x1, y1, x2, y2)) {
                this.board.capturePawn(x1, y1, x2, y2);
                this.capturePossible.clear();
                if (this.board.canICaptureOneMoreTime(x2, y2)) {
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



    @Override
    public int getBoardSize() {
        return 10;
    }

    @Override
    public int getPawnRows() {
        return 4;
    }
}
