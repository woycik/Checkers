package Controller;

import Model.EnglishBoard;
import Model.PlayerTurn;

public class EnglishCheckersController extends GameController {

    public EnglishCheckersController() {
        this.board = new EnglishBoard();
    }

    @Override
    public boolean makeMove(int x1, int y1, int x2, int y2) {
        this.board.setMyPawns();
        this.board.addToPossibleMoves();
        this.board.addToPossibleCaptures();
        if (!finishCapture) {
            if (playerTurn == PlayerTurn.Black) {
                this.board.captureFieldList(board.blackPawns);
            } else {
                this.board.captureFieldList(board.whitePawns);
            }
            if (this.board.isCapturePossible()) {
                if (this.board.checkCapture(x1, y1, x2, y2)) {
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
                    return this.board.movePawn(x1, y1, x2, y2);
                }
            }
        } else {
            if (this.board.checkCapture(x1, y1, x2, y2)) {
                this.board.capturePawn(x1, y1, x2, y2);
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
}
