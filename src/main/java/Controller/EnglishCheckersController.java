package Controller;

import Model.EnglishBoard;
import Model.PlayerTurn;

public class EnglishCheckersController extends GameController {
    /**
     * English checkers controller Constructor
     */
    public EnglishCheckersController() {
        board = new EnglishBoard();
    }

    /**
     * Pawn move method
     *
     * @param x1 x-coordinate of pawn starting position
     * @param y1 y-coordinate of pawn starting position
     * @param x2 x-coordinate of pawn ending position
     * @param y2 y-coordinate of pawn ending position
     * @return true if move was made done correctly
     */
    @Override
    protected boolean makeMove(int x1, int y1, int x2, int y2) {
        if (!isCaptureFinished) {
            prepareBoard();
        }
        if (board.isCapturePossible()) {
            if (board.checkCapture(x1, y1, x2, y2)) {
                board.capturePawn(x1, y1, x2, y2);
                board.capturePossible.clear();
                if (board.canICaptureOneMoreTime(x2, y2, playerTurn.toString())) {
                    board.capturePossible.add(board.getFields()[x2][y2]);
                    isCaptureFinished = true;
                    return false;
                }
                isCaptureFinished = false;
                return true;
            }
        } else {
            if (board.isMoveLegal(x1, y1, x2, y2)) {
                return board.movePawn(x1, y1, x2, y2);
            }
        }
        return false;
    }

    /**
     * Board size returning method
     *
     * @return board size
     */

    @Override
    public int getBoardSize() {
        return 8;
    }

    /**
     * Number od pawn rows returning method
     *
     * @return number of pawn rown
     */

    @Override
    public int getPawnRows() {
        return 3;
    }

    /**
     * Game variant returning method
     *
     * @return game variant
     */
    @Override
    public String getGameVariant() {
        return "English";
    }
}
