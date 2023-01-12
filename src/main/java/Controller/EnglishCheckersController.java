package Controller;

import Model.EnglishBoard;
import Model.PlayerTurn;

public class EnglishCheckersController extends GameController {

    public EnglishCheckersController() {
        board = new EnglishBoard();
    }

    @Override
    protected boolean makeMove(int x1, int y1, int x2, int y2) {
        if (!finishCapture) {
            if (playerTurn == PlayerTurn.Black) {
                board.captureFieldList(board.blackPawns);
            } else {
                board.captureFieldList(board.whitePawns);
            }
            if (board.isCapturePossible()) {
                if (board.checkCapture(x1, y1, x2, y2)) {
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
                    return board.movePawn(x1, y1, x2, y2);
                }
            }
        } else {
            if (board.checkCapture(x1, y1, x2, y2)) {
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


    @Override
    public int getBoardSize() {
        return 8;
    }

    @Override
    public int getPawnRows() {
        return 3;
    }

    @Override
    public String getGameVariant() {
        return "English";
    }
}
