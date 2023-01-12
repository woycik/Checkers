package Controller;

import Model.*;

/**
 *  Class handling the polish checkers game operations
 */
public class PolishCheckersController extends GameController {
    /**
     * Polish checkers controller Constructor
     */
    public PolishCheckersController() {
        board = new PolishBoard();
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
            board.capturePossible.clear();
            if (playerTurn == PlayerTurn.Black) {
                board.captureFieldList(board.blackPawns);
            } else {
                board.captureFieldList(board.whitePawns);
            }
            if (board.isCapturePossible()) {
                PolishBoard polishBoard = (PolishBoard)board;
                polishBoard.filterLongestCaptures(playerTurn.toString());
                if (board.checkCapture(x1, y1, x2, y2)) {
                    board.capturePawn(x1, y1, x2, y2);
                    board.createNewQueen(x2, y2);
                    board.capturePossible.clear();
                    if (board.canICaptureOneMoreTime(x2, y2,playerTurn.toString())) {
                        board.capturePossible.add(board.getFields()[x2][y2]); //zmien na settera
                        finishCapture = true;
                        return false;
                    }
                    return true;
                }
                return false;
            } else {
                if (board.isMoveLegal(x1, y1, x2, y2)) {
                    return board.movePawn(x1, y1, x2, y2);
                }
            }
        } else {
            if (board.checkCapture(x1, y1, x2, y2, playerTurn.toString())) {
                board.capturePawn(x1, y1, x2, y2);
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
        return false;
    }
    /**
     * Board size returning method
     * @return board size
     */
    @Override
    public int getBoardSize() {
        return 10;
    }
    /**
     * Number od pawn rows returning method
     * @return number of pawn rown
     */
    @Override
    public int getPawnRows() {
        return 4;
    }
    /**
     * Game variant returning method
     * @return game variant
     */
    @Override
    public String getGameVariant() {
        return "Polish";
    }
}
