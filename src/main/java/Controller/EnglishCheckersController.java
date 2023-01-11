package Controller;

import Model.Field;
import Model.PlayerTurn;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class EnglishCheckersController extends GameController {

    @Override
    public boolean makeMove(int x1, int y1, int x2, int y2) {
        this.board = ;
        this.setMyPawns();
        if (!finishCapture) {
            if (playerTurn == PlayerTurn.Black) {
                this.captureFieldList(blackPawns);
            } else {
                this.captureFieldList(whitePawns);
            }
            if (this.isCapturePossible()) {
                if (this.checkCapture(x1, y1, x2, y2)) {
                    this.capturePawn(x1, y1, x2, y2);
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
                    return this.movePawn(x1, y1, x2, y2);
                }
            }
        } else {
            if (this.checkCapture(x1, y1, x2, y2)) {
                this.capturePawn(x1, y1, x2, y2);
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
            int x = boardField.getX();
            int y = boardField.getY();

            if (boardField.isOccupied()) {
                Color color = boardField.getPawnColor();

                if (isCapturePossibleTopLeft(x, y, color)) {
                    capturePossible.add(boardField);
                } else if (isCapturePossibleTopRight(x, y, color)) {
                    capturePossible.add(boardField);
                } else if (isCapturePossibleBottomLeft(x, y, color)) {
                    capturePossible.add(boardField);
                } else if (isCapturePossibleBottomRight(x, y, color)) {
                    capturePossible.add(boardField);
                }
            }
        }
        this.removePawnsFromList();
    }

    public boolean checkCapture(int x1, int y1, int x2, int y2) {
        if (capturePossible.contains(board.getFields()[x1][y1])) {
            if (Math.abs(x1 - x2) == 2 && Math.abs(y1 - y2) == 2 && board.getFields()[(x1 + x2) / 2][(y1 + y2) / 2].isOccupied() && !board.getFields()[x2][y2].isOccupied()) {
                return !board.getFields()[(x1 + x2) / 2][(y1 + y2) / 2].getPawnColor().equals(board.getFields()[x1][y1].getPawnColor());
            }
        }
        return false;
    }

    @Override
    public boolean isMoveLegal(int x1, int y1, int x2, int y2) {
        if (board.getFields()[x1][y1].getPawn() != null) {
            if (x2 < this.getBoardSize() && x2 >= 0 && y2 < this.getBoardSize() && y2 >= 0) {
                if (!board.getFields()[x2][y2].isOccupied()) {
                    if (!board.getFields()[x1][y1].getPawn().isQueen()) {
                        if (this.playerTurn == PlayerTurn.White) {
                            return (y1 - 1 == y2 && (x1 + 1 == x2 || x1 - 1 == x2));
                        } else if (this.playerTurn == PlayerTurn.Black) {
                            return (y1 + 1 == y2 && (x1 + 1 == x2 || x1 - 1 == x2));
                        }
                    } else {
                        return (y1 - 1 == y2 && (x1 + 1 == x2 || x1 - 1 == x2) || (y1 + 1 == y2 && (x1 + 1 == x2 || x1 - 1 == x2)));
                    }
                }
            }
        }

        return false;
    }

    public void capturePawn(int x1, int y1, int x2, int y2) {

        board.getFields()[x2][y2].setPawn(board.getFields()[x1][y1].getPawn());
        board.getFields()[x1][y1].setPawn(null);
        board.getFields()[(x1 + x2) / 2][(y1 + y2) / 2].setPawn(null);
        if (board.getFields()[x2][y2].getPawnColor().equals(Color.rgb(0, 0, 0))) {
            numberOfWhitePawns--;
        } else if (board.getFields()[x2][y2].getPawnColor().equals(Color.rgb(255, 255, 255))) {
            numberOfBlackPawns--;
        }

        capturePossible.clear();
        this.createNewQueen(x2, y2);

    }

    public boolean canICaptureOneMoreTime(int x, int y) {
        Color color = board.getFields()[x][y].getPawnColor();
        if (this.isCapturePossibleBottomRight(x, y, color)) {
            return true;
        }
        if (this.isCapturePossibleTopRight(x, y, color)) {
            return true;
        }
        if (this.isCapturePossibleTopLeft(x, y, color)) {
            return true;
        }
        if (this.isCapturePossibleBottomLeft(x, y, color)) {
            return true;
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
