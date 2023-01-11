package Controller;

import Model.EnglishBoard;
import Model.Field;

import Model.PlayerTurn;
import Model.RussianBoard;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class RussianCheckersController extends GameController {

    public RussianCheckersController() {
        this.board = new RussianBoard();
    }

    @Override
    public boolean makeMove(int x1, int y1, int x2, int y2) {
        this.board.setMyPawns();
        this.board.addToPossibleMoves();
        this.board.addToPossibleCaptures();
        if (!finishCapture) {
            if (playerTurn == PlayerTurn.Black) {
                this.board.captureFieldList(board.blackPawns);
            }                                                                                   //zapisz czarne pola z których mozliwe jest bicie}
            else {
                this.board.captureFieldList(board.whitePawns);
            }                                                                                   //zapisz biale pola z których mozliwe jest bicie
            if (this.board.isCapturePossible()) {                                                     //sprawdz czy mozliwe jest bicie dla (bialego/czarnego)                 //czy doszloby do wykonania bicia?
                if (this.board.checkCapture(x1, y1, x2, y2)) {                                        //zapisz pola na które może wybrany pionek wskoczyc po wykonaniu bicia i sprawdz czy ten pionek nalezy do listy
                    this.board.capturePawn(x1, y1, x2, y2);
                    this.board.capturePossible.clear();
                    if (this.board.canICaptureOneMoreTime(x2, y2)) {
                        board.capturePossible.add(board.getFields()[x2][y2]);
                        finishCapture = true;
                        return false;
                    }
                    return true;
                }
                return false;
            } else {
                if (this.board.isMoveLegal(x1, y1, x2, y2)) {
                    return this.board.movePawn(x1, y1, x2, y2);                                           //koniec ruchu dla danego gracza o ile nie wybral niewlasciwego pola
                }
            }
        } else {
            if (this.board.checkCapture(x1, y1, x2, y2)) {                                                //zapisz pola na które może wybrany pionek wskoczyc po wykonaniu bicia i sprawdz czy ten pionek nalezy do listy
                this.board.capturePawn(x1, y1, x2, y2); //jak tak to zbij
                this.board.capturePossible.clear();
                if (this.board.canICaptureOneMoreTime(x2, y2)) {
                    board.capturePossible.add(board.getFields()[x2][y2]);
                    finishCapture = true;
                    return false;
                }
                finishCapture = false;
                return true;
            }

        }
        return false;                                                                               //powtórzenie ruchu
    }


    @Override
    public int getBoardSize() {
        return 8;
    }

    @Override
    public int getPawnRows() {
        return 3;
    }
}
