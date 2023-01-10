package Controller;

import Model.Field;
import Model.PlayerTurn;
import javafx.scene.paint.Color;
import java.util.ArrayList;

public class PolishCheckersController extends GameController {

    @Override
    public boolean makeMove(int x1, int y1, int x2, int y2) {
        this.setMyPawns();                                                                       //poznajemy położenie pionków
        if (!finishCapture) {
            if (playerTurn == PlayerTurn.Black) {
                this.captureFieldList(blackPawns);
            }                                                                                   //zapisz czarne pola z których mozliwe jest bicie}
            else {
                this.captureFieldList(whitePawns);
            }                                                                                   //zapisz biale pola z których mozliwe jest bicie
            if (this.isCapturePossible()) {                                                     //sprawdz czy mozliwe jest bicie dla (bialego/czarnego)                 //czy doszloby do wykonania bicia?
                if (this.checkCapture(x1, y1, x2, y2)) {                                        //zapisz pola na które może wybrany pionek wskoczyc po wykonaniu bicia i sprawdz czy ten pionek nalezy do listy
                    this.capturePawn(x1, y1, x2, y2); //jak tak to zbij
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
                    return this.movePawn(x1, y1, x2, y2);                                           //koniec ruchu dla danego gracza o ile nie wybral niewlasciwego pola
                }
            }
        } else {
            if (this.checkCapture(x1, y1, x2, y2)) {                                                //zapisz pola na które może wybrany pionek wskoczyc po wykonaniu bicia i sprawdz czy ten pionek nalezy do listy
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
                if (!boardField.getPawn().isQueen()) {

                    if (isCapturePossibleTopLeft(x, y, color)) {
                        capturePossible.add(boardField);
                    } else if (isCapturePossibleTopRight(x, y, color)) {
                        capturePossible.add(boardField);
                    } else if (isCapturePossibleBottomLeft(x, y, color)) {
                        capturePossible.add(boardField);
                    } else if (isCapturePossibleBottomRight(x, y, color)) {
                        capturePossible.add(boardField);
                    }
                } else {
                    int currx = x;
                    int curry = y;
                    int prevx = x;
                    int prevy = y;
                    while (currx >= 0 && curry >= 0) {
                        if (!(isCapturePossibleTopLeft(currx, curry, color))) {
                            currx--;
                            curry--;
                            if (isMovePossible(prevx, prevy, currx, curry)) {
                                prevy--;
                                prevx--;
                            } else {
                                break;
                            }
                        } else {
                            capturePossible.add(boardField);
                            break;
                        }
                    }

                    currx = x;
                    curry = y;
                    prevx = x;
                    prevy = y;
                    while (currx <= (this.getBoardSize() - 1) && curry >= 0) {
                        if (!(isCapturePossibleTopRight(currx, curry, color))) {
                            currx++;
                            curry--;
                            if (isMovePossible(prevx, prevy, currx, curry)) {
                                prevx++;
                                prevy--;
                            } else {
                                break;
                            }
                        } else {
                            capturePossible.add(boardField);
                            break;
                        }
                    }

                    currx = x;
                    curry = y;
                    prevx = x;
                    prevy = y;
                    while (currx <= (this.getBoardSize() - 1) && curry <= (this.getBoardSize() - 1)) {
                        if (!(isCapturePossibleBottomRight(currx, curry, color))) {
                            currx++;
                            curry++;
                            if (isMovePossible(prevx, prevy, currx, curry)) {
                                prevy++;
                                prevx++;
                            } else {
                                break;
                            }
                        } else {
                            capturePossible.add(boardField);
                            break;
                        }
                    }

                    currx = x;
                    curry = y;
                    prevx = x;
                    prevy = y;
                    while (currx >= 0 && curry <= (this.getBoardSize() - 1)) {
                        if (!(isCapturePossibleBottomLeft(currx, curry, color))) {
                            currx--;
                            curry++;
                            if (isMovePossible(prevx, prevy, currx, curry)) {
                                prevy++;
                                prevx--;
                            } else {
                                break;
                            }
                        } else {
                            capturePossible.add(boardField);
                            break;
                        }
                    }
                }
            }
        }
        this.removePawnsFromList();
    }

    public void bestCaptureRule() {
        for (Field fields : capturePossible) {
            int x = fields.getX();
            int y = fields.getY();
            int currX = x;
            int currY = y;
            Color color = fields.getPawnColor();
            while (canICaptureOneMoreTime(currX, currY)) {

            }
        }
    }

    public boolean checkCapture(int x1, int y1, int x2, int y2) {
        if (capturePossible.contains(board.getFields()[x1][y1])) {
            if (!board.getFields()[x1][y1].getPawn().isQueen()) {
                if (Math.abs(x1 - x2) == 2 && Math.abs(y1 - y2) == 2 && board.getFields()[(x1 + x2) / 2][(y1 + y2) / 2].isOccupied() && !board.getFields()[x2][y2].isOccupied()) {
                    return !board.getFields()[(x1 + x2) / 2][(y1 + y2) / 2].getPawnColor().equals(board.getFields()[x1][y1].getPawnColor());
                }
            } else {
                if (!board.getFields()[x2][y2].isOccupied()) {
                    int diffX;
                    int diffY;

                    diffX = x2 - x1;
                    diffY = y2 - y1;

                    if (diffY == diffX || -diffY == diffX) {
                        if (diffY > 0 && diffX > 0) {
                            for (int i = 1; i < diffX; i++) {
                                if (board.getFields()[x1 + i][y1 + i].isOccupied()) {
                                    if (!board.getFields()[x1 + i][y1 + i].getPawnColor().equals(board.getFields()[x1][y1].getPawnColor())) {
                                        return true;
                                    }
                                }
                            }
                        } else if (diffY > 0 && diffX < 0) {
                            for (int i = 1; i < Math.abs(diffX); i++) {
                                if (board.getFields()[x1 - i][y1 + i].isOccupied()) {
                                    if (!board.getFields()[x1 - i][y1 + i].getPawnColor().equals(board.getFields()[x1][y1].getPawnColor())) {
                                        return true;
                                    }
                                }
                            }
                        } else if (diffY < 0 && diffX < 0) {
                            for (int i = 1; i < Math.abs(diffX); i++) {
                                if (board.getFields()[x1 - i][y1 - i].isOccupied()) {
                                    if (!board.getFields()[x1 - i][y1 - i].getPawnColor().equals(board.getFields()[x1][y1].getPawnColor())) {
                                        return true;
                                    }
                                }
                            }
                        } else if (diffY < 0 && diffX > 0) {
                            for (int i = 1; i < diffX; i++) {
                                if (board.getFields()[x1 + i][y1 - i].isOccupied()) {
                                    if (!board.getFields()[x1 + i][y1 - i].getPawnColor().equals(board.getFields()[x1][y1].getPawnColor())) {
                                        return true;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }


    public boolean isMovePossible(int x1, int y1, int x2, int y2) {
        if (x2 < this.getBoardSize() && x2 >= 0 && y2 < this.getBoardSize() && y2 >= 0) {
            if (!board.getFields()[x2][y2].isOccupied()) {
                return Math.abs(x1 - x2) == 1 && Math.abs(y1 - y2) == 1;
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
                    } else {                                //do tej sytuacji nie dojdzie jesli będzie jakiekolwiek bicie, tzn wystarczy sprawdzic cze pola miedzy poczatkiem a konczem są puste.
                        int diffX;
                        int diffY;

                        diffX = x2 - x1;
                        diffY = y2 - y1;
                        if (diffY == diffX || -diffY == diffX) {
                            if (diffY > 0 && diffX > 0) {
                                for (int i = 1; i < diffX; i++) {
                                    if (board.getFields()[x1 + i][y1 + i].isOccupied()) {
                                        return false;
                                    }
                                }
                            }
                            if (diffY > 0 && diffX < 0) {
                                for (int i = 1; i < Math.abs(diffX); i++) {
                                    if (board.getFields()[x1 - i][y1 + i].isOccupied()) {
                                        return false;
                                    }
                                }
                            }
                            if (diffY < 0 && diffX < 0) {
                                for (int i = 1; i < Math.abs(diffX); i++) {
                                    if (board.getFields()[x1 - i][y1 - i].isOccupied()) {
                                        return false;
                                    }
                                }
                            }
                            if (diffY < 0 && diffX > 0) {
                                for (int i = 1; i < diffX; i++) {
                                    if (board.getFields()[x1 + i][y1 - i].isOccupied()) {
                                        return false;
                                    }
                                }
                            }
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public void capturePawn(int x1, int y1, int x2, int y2) {

        if (!board.getFields()[x1][y1].getPawn().isQueen()) {
            board.getFields()[x2][y2].setPawn(board.getFields()[x1][y1].getPawn());
            board.getFields()[x1][y1].setPawn(null);
            board.getFields()[(x1 + x2) / 2][(y1 + y2) / 2].setPawn(null);
            if (board.getFields()[x2][y2].getPawnColor().equals(Color.rgb(0, 0, 0))) {
                numberOfWhitePawns--;
            } else if (board.getFields()[x2][y2].getPawnColor().equals(Color.rgb(255, 255, 255))) {
                numberOfBlackPawns--;
            }
        } else {
            board.getFields()[x2][y2].setPawn(board.getFields()[x1][y1].getPawn());
            board.getFields()[x1][y1].setPawn(null);
            int diffX;
            int diffY;

            diffX = x2 - x1;
            diffY = y2 - y1;


            if (diffY == diffX || -diffY == diffX) {
                if (diffY > 0 && diffX > 0) {
                    for (int i = 1; i < diffX; i++) {
                        if (board.getFields()[x1 + i][y1 + i].isOccupied()) {
                            board.getFields()[x1 + i][y1 + i].setPawn(null);
                            break;
                        }
                    }
                }
                if (diffY > 0 && diffX < 0) {
                    for (int i = 1; i < Math.abs(diffX); i++) {
                        if (board.getFields()[x1 - i][y1 + i].isOccupied()) {
                            board.getFields()[x1 - i][y1 + i].setPawn(null);
                            break;
                        }
                    }
                }
                if (diffY < 0 && diffX < 0) {
                    for (int i = 1; i < Math.abs(diffX); i++) {
                        if (board.getFields()[x1 - i][y1 - i].isOccupied()) {
                            board.getFields()[x1 - i][y1 - i].setPawn(null);
                            break;
                        }
                    }
                }
                if (diffY < 0 && diffX > 0) {
                    for (int i = 1; i < diffX; i++) {
                        if (board.getFields()[x1 + i][y1 - i].isOccupied()) {
                            board.getFields()[x1 + i][y1 - i].setPawn(null);
                            break;
                        }
                    }
                }
            }
        }
        capturePossible.clear();
        this.createNewQueen(x2, y2);

    }

    public boolean canICaptureOneMoreTime(int x, int y) {
        Color color = board.getFields()[x][y].getPawnColor();
        if (!board.getFields()[x][y].getPawn().isQueen()) {
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
        } else {
            int currx = x;
            int curry = y;
            int prevx = x;
            int prevy = y;
            while (currx >= 0 && curry >= 0) {
                if (!(isCapturePossibleTopLeft(currx, curry, color))) {
                    currx--;
                    curry--;
                    if (isMovePossible(prevx, prevy, currx, curry)) {
                        prevy--;
                        prevx--;
                    } else {
                        break;
                    }
                } else {
                    return true;
                }
            }

            currx = x;
            curry = y;
            prevx = x;
            prevy = y;
            while (currx <= (this.getBoardSize() - 1) && curry >= 0) {
                if (!(isCapturePossibleTopRight(currx, curry, color))) {
                    currx++;
                    curry--;
                    if (isMovePossible(prevx, prevy, currx, curry)) {
                        prevx++;
                        prevy--;
                    } else {
                        break;
                    }
                } else {
                    return true;
                }
            }

            currx = x;
            curry = y;
            prevx = x;
            prevy = y;
            while (currx <= (this.getBoardSize() - 1) && curry <= (this.getBoardSize() - 1)) {
                if (!(isCapturePossibleBottomRight(currx, curry, color))) {
                    currx++;
                    curry++;
                    if (isMovePossible(prevx, prevy, currx, curry)) {
                        prevy++;
                        prevx++;
                    } else {
                        break;
                    }
                } else {
                    return true;
                }
            }

            currx = x;
            curry = y;
            prevx = x;
            prevy = y;
            while (currx >= 0 && curry <= (this.getBoardSize() - 1)) {
                if (!(isCapturePossibleBottomLeft(currx, curry, color))) {
                    currx--;
                    curry++;
                    if (isMovePossible(prevx, prevy, currx, curry)) {
                        prevy++;
                        prevx--;
                    } else {
                        break;
                    }
                } else {
                    return true;
                }
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
