package Controller;

import Model.PlayerTurn;
import Model.RussianBoard;

public class RussianCheckersController extends GameController {
    /**
     * Russian checkers controller Constructor
     */
    public RussianCheckersController() {
        board = new RussianBoard();
    }
    /**
     * Pawn move method
     * @param x1 x-coordinate of pawn starting position
     * @param y1 y-coordinate of pawn starting position
     * @param x2 x-coordinate of pawn ending position
     * @param y2 y-coordinate of pawn ending position
     * @return true if move was made done correctly
     */
    @Override
    protected boolean makeMove(int x1, int y1, int x2, int y2) {
        if (!finishCapture) {
            if (playerTurn == PlayerTurn.Black) {
                board.captureFieldList(board.blackPawns);
            }                                                                                   //zapisz czarne pola z których mozliwe jest bicie}
            else {
                board.captureFieldList(board.whitePawns);
            }
            if (board.isCapturePossible()) {                                                     //sprawdz czy mozliwe jest bicie dla (bialego/czarnego)                 //czy doszloby do wykonania bicia?
                if (board.checkCapture(x1, y1, x2, y2)) {                                        //zapisz pola na które może wybrany pionek wskoczyc po wykonaniu bicia i sprawdz czy ten pionek nalezy do listy
                    board.capturePawn(x1, y1, x2, y2);
                    board.capturePossible.clear();
                    if (board.canICaptureOneMoreTime(x2, y2,playerTurn.toString())) {
                        board.capturePossible.add(board.getFields()[x2][y2]);
                        finishCapture = true;
                        return false;
                    }
                    return true;
                }
                return false;
            } else {
                if (board.isMoveLegal(x1, y1, x2, y2)) {
                    return board.movePawn(x1, y1, x2, y2);                                           //koniec ruchu dla danego gracza o ile nie wybral niewlasciwego pola
                }
            }
        } else {
            if (board.checkCapture(x1, y1, x2, y2)) {                                                //zapisz pola na które może wybrany pionek wskoczyc po wykonaniu bicia i sprawdz czy ten pionek nalezy do listy
                board.capturePawn(x1, y1, x2, y2); //jak tak to zbij
                board.capturePossible.clear();
                if (board.canICaptureOneMoreTime(x2, y2,playerTurn.toString())) {
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

    /**
     * Board size returning method
     * @return board size
     */
    @Override
    public int getBoardSize() {
        return 8;
    }
    /**
     * Number od pawn rows returning method
     * @return number of pawn rown
     */
    @Override
    public int getPawnRows() {
        return 3;
    }
    /**
     * Game variant returning method
     * @return game variant
     */
    @Override
    public String getGameVariant() {
        return "Russian";
    }
}
