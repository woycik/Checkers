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
            }                     //zapisz czarne pola z których mozliwe jest bicie}
            else {
                this.captureFieldList(whitePawns);
            }                                                                           //zapisz biale pola z których mozliwe jest bicie
            if (this.isCapturePossible()) {                                                     //sprawdz czy mozliwe jest bicie dla (bialego/czarnego)                 //czy doszloby do wykonania bicia?
                if (this.checkCapture(x1, y1, x2, y2)) {                                            //zapisz pola na które może wybrany pionek wskoczyc po wykonaniu bicia i sprawdz czy ten pionek nalezy do listy
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
            }
            else {
                if (this.isMoveLegal(x1, y1, x2, y2)) {
                    return this.movePawn(x1, y1, x2, y2);                                           //koniec ruchu dla danego gracza o ile nie wybral niewlasciwego pola
                }
            }
        }
        else {
            if (this.checkCapture(x1, y1, x2, y2)) {                                       //zapisz pola na które może wybrany pionek wskoczyc po wykonaniu bicia i sprawdz czy ten pionek nalezy do listy
                this.capturePawn(x1, y1, x2, y2); //jak tak to zbij
                this.capturePossible.clear();
                if (this.canICaptureOneMoreTime(x2, y2)) {
                    capturePossible.add(board.getFields()[x2][y2]);
                    finishCapture = true;
                    return false;
                }
                finishCapture = false;
            }

        }
        return false;                                                                    //powtórzenie ruchu
    }

    //przypisuje do myPaws wszytkie pionki danego gracza
    //najpierw sprawdzamy czy możliwe jest bicie
    //jesli jest trzeba je wykonać
    //jesli nie robimy normalny ruch
    //po czym oddajemy mozliwosc poruszania się przeciwnikowi

    //zwraca fields na których możliwe jest bicie

    //do listy myPaws przypisuje (jak narazie) wszytkie pionki o kolorze czarnym.
    public void setMyPawns() {
        for (int i = 0; i < getBoardSize(); i++) {
            for (int j = 0; j < getBoardSize(); j++) {
                if (board.getFields()[i][j].getPawn() != null) {
                    if (board.getFields()[i][j].getPawn().getColor().equals(Color.rgb(0, 0, 0))) {
                        blackPawns.add(board.getFields()[i][j]);
                    } else if (board.getFields()[i][j].getPawn().getColor().equals(Color.rgb(255, 255, 255))) {
                        whitePawns.add(board.getFields()[i][j]);
                    }
                }
            }
        }
    }

    //przypisanie do listy capturePossible pol  z których mozliwe jest bicie w ogólnosci i dla queen tez
    public void captureFieldList(ArrayList<Field> typeOfPawns) {
        for (Field boardField : typeOfPawns) {
            int x = boardField.getX();
            int y = boardField.getY();
            if (!board.getFields()[x][y].getPawn().isQueen()) {

                if ((x + 2 < getBoardSize()) && (y + 2) < getBoardSize()) {
                    if (board.getFields()[x + 1][y + 1].isOccupied() && !board.getFields()[x + 2][y + 2].isOccupied()) {
                        if (!board.getFields()[x + 1][y + 1].getPawn().getColor().equals(board.getFields()[x][y].getPawn().getColor())) {
                            capturePossible.add(boardField);
                        }
                    }
                }
                if ((x + 2 < getBoardSize()) && (y - 2) > 0) {
                    if (board.getFields()[x + 1][y - 1].isOccupied() && !board.getFields()[x + 2][y - 2].isOccupied()) {
                        if (!board.getFields()[x + 1][y - 1].getPawn().getColor().equals(board.getFields()[x][y].getPawn().getColor())) {
                            capturePossible.add(boardField);
                        }
                    }
                }

                if ((x - 2) > 0 && (y - 2) > 0) {
                    if (board.getFields()[x - 1][y - 1].isOccupied() && !board.getFields()[x - 2][y - 2].isOccupied()) {
                        if (!board.getFields()[x - 1][y - 1].getPawn().getColor().equals(board.getFields()[x][y].getPawn().getColor())) {
                            capturePossible.add(boardField);
                        }
                    }
                }
                if ((x - 2) > 0 && (y + 2) < getBoardSize()) {
                    if (board.getFields()[x - 1][y + 1].isOccupied() && !board.getFields()[x - 2][y + 2].isOccupied()) {
                        if (!board.getFields()[x - 1][y + 1].getPawn().getColor().equals(board.getFields()[x][y].getPawn().getColor())) {
                            capturePossible.add(boardField);
                        }
                    }
                }
            } else {

                int i = 1;
                while (x + i + 1 < getBoardSize() && y + 1 + i < getBoardSize()) {
                    if (board.getFields()[x + i][y + i].getColor().equals(board.getFields()[x][y].getColor())) {
                        if (!board.getFields()[x + i + 1][y + i + 1].isOccupied()) {
                            if (capturePossible.contains(board.getFields()[x][y])) {
                                capturePossible.add(board.getFields()[x][y]);
                            }
                        }
                    } else {
                        break;
                    }
                    i++;
                }
                i = 1;
                while (x + i + 1 < getBoardSize() && y - 1 - i > 0) {
                    if (board.getFields()[x + i][y - i].getPawn().getColor().equals(board.getFields()[x][y].getPawn().getColor())) {
                        if (!board.getFields()[x + i + 1][y - i - 1].isOccupied()) {
                            if (!capturePossible.contains(board.getFields()[x][y])) {
                                capturePossible.add(board.getFields()[x][y]);
                            }
                        }
                    } else {
                        break;
                    }
                    i++;
                }
                i = 1;
                while (x - i - 1 > 0 && y - 1 - i > 0) {
                    if (board.getFields()[x - i][y - i].getPawn().getColor().equals(board.getFields()[x][y].getPawn().getColor())) {
                        if (!board.getFields()[x - i - 1][y - i - 1].isOccupied()) {
                            if (!capturePossible.contains(board.getFields()[x][y])) {
                                capturePossible.add(board.getFields()[x][y]);
                            }
                        }
                    } else {
                        break;
                    }
                    i++;
                }
                i = 1;
                while (x - i - 1 > 0 && y + 1 + i < getBoardSize()) {
                    if (board.getFields()[x - i][y + i].getColor().equals(board.getFields()[x][y].getColor())) {
                        if (!board.getFields()[x - i - 1][y + i + 1].isOccupied()) {
                            if (!capturePossible.contains(board.getFields()[x][y])) {
                                capturePossible.add(board.getFields()[x][y]);
                            }
                        }
                    } else {
                        break;
                    }
                    i++;
                }


            }
        }
        this.removePawnsFromList();


    }

    public void removePawnsFromList(){
        blackPawns.clear();
        whitePawns.clear();
    }

    //Sprawdzam czy mozliwe jest bicie dla podanych lokalizacji
    public boolean checkCapture(int x1, int y1, int x2, int y2) {
        if (capturePossible.contains(board.getFields()[x1][y1])) {
            if (!board.getFields()[x1][y1].getPawn().isQueen()) {
                if (Math.abs(x1 - x2) == 2 && Math.abs(y1 - y2) == 2 && board.getFields()[(x1 + x2) / 2][(y1 + y2) / 2].isOccupied() && !board.getFields()[x2][y2].isOccupied()) {
                    System.out.println("it contains this pawn inside");
                    return !board.getFields()[(x1 + x2) / 2][(y1 + y2) / 2].getColor().equals(board.getFields()[x1][y1].getColor());
                }
            } else {
                if (!board.getFields()[x2][y2].isOccupied()) {
                    int diffX;
                    int diffY;

                    diffX = x2 - x1;
                    diffY = y2 - y1;
                    int count = 0;
                    if (diffY == diffX || -diffY == diffX) {
                        if (diffY > 0 && diffX > 0) {
                            for (int i = 1; i < diffX; i++) {
                                if (board.getFields()[x1 + i][y1 + 1].getColor().equals(board.getFields()[x1][y1].getColor())) {
                                    count++;
                                }
                            }
                        }
                        if (diffY > 0 && diffX < 0) {
                            for (int i = 1; i < Math.abs(diffX); i++) {
                                if (board.getFields()[x1 - i][y1 + i].getColor().equals(board.getFields()[x1][y1].getColor())) {
                                    count++;
                                }
                            }
                        }
                        if (diffY < 0 && diffX < 0) {
                            for (int i = 1; i < Math.abs(diffX); i++) {
                                if (board.getFields()[x1 - i][y1 - i].getColor().equals(board.getFields()[x1][y1].getColor())) {
                                    count++;
                                }
                            }
                        }
                        if (diffY < 0 && diffX > 0) {
                            for (int i = 1; i < diffX; i++) {
                                if (board.getFields()[x1 - i][y1 + 1].getColor().equals(board.getFields()[x1][y1].getColor())) {
                                    count++;
                                }
                            }
                        }
                        return count == 1;
                    }
                }
            }
        }
        return false;
    }

    public boolean canICaptureOneMoreTime(int x, int y) {
        if (!board.getFields()[x][y].getPawn().isQueen()) {
            if (x + 2 < getBoardSize() && y + 2 < getBoardSize()) {
                if (board.getFields()[x + 1][y + 1].isOccupied() && !board.getFields()[x + 2][y + 2].isOccupied()) {
                    if (!board.getFields()[x + 1][y + 1].getPawn().getColor().equals(board.getFields()[x][y].getPawn().getColor())) {
                        return true;
                    }
                }
            }
            if (x + 2 < getBoardSize() && y - 2 > 0) {
                if (board.getFields()[x + 1][y - 1].isOccupied() && !board.getFields()[x + 2][y - 2].isOccupied()) {
                    if (!board.getFields()[x + 1][y - 1].getPawn().getColor().equals(board.getFields()[x][y].getPawn().getColor())) {
                        return true;
                    }
                }
            }
            if ((x - 2) > 0 && (y - 2) > 0) {
                if (board.getFields()[x - 1][y - 1].isOccupied() && !board.getFields()[x - 2][y - 2].isOccupied()) {
                    if (!board.getFields()[x - 1][y - 1].getPawn().getColor().equals(board.getFields()[x][y].getPawn().getColor())) {
                        return true;
                    }
                }
            }
            if ((x - 2) > 0 && (y + 2) < getBoardSize()) {
                if (board.getFields()[x - 1][y + 1].isOccupied() && !board.getFields()[x - 2][y + 2].isOccupied()) {
                    if (!board.getFields()[x - 1][y + 1].getPawn().getColor().equals(board.getFields()[x][y].getPawn().getColor())) {
                        return true;
                    }
                }
            }
        } else {
            int i = 1;
            while (x + i + 1 < getBoardSize() && y + 1 + i < getBoardSize()) {
                if (board.getFields()[x + i][y + i].getColor() != board.getFields()[x][y].getColor()) {
                    if (!board.getFields()[x + i + 1][y + i + 1].isOccupied()) {
                        return true;
                    }
                } else {
                    break;
                }
                i++;
            }
            i = 1;
            while (x + i + 1 < getBoardSize() && y - 1 - i > 0) {
                if (board.getFields()[x + i][y - i].getPawn().getColor() != board.getFields()[x][y].getPawn().getColor()) {
                    if (!board.getFields()[x + i + 1][y - i - 1].isOccupied()) {
                        return true;
                    }
                } else {
                    break;
                }
                i++;
            }
            i = 1;
            while (x - i - 1 > 0 && y - 1 - i > 0) {
                if (board.getFields()[x - i][y - i].getPawn().getColor() != board.getFields()[x][y].getPawn().getColor()) {
                    if (!board.getFields()[x - i - 1][y - i - 1].isOccupied()) {
                        return true;
                    }
                } else {
                    break;
                }
                i++;
            }
            i = 1;
            while (x - i - 1 > 0 && y + 1 + i < getBoardSize()) {
                if (board.getFields()[x - i][y + i].getColor() != board.getFields()[x][y].getColor()) {
                    if (!board.getFields()[x - i - 1][y + i + 1].isOccupied()) {
                        return true;

                    }
                } else {
                    break;
                }
                i++;
            }

        }
        return false;
    }

    //sprawdzenie czy zwykły ruch jest możliwy i dla damki tez
    @Override
    public boolean isMoveLegal(int x1, int y1, int x2, int y2) {
        if (board.getFields()[x1][y1].getPawn() != null) {
            if (!board.getFields()[x2][y2].isOccupied()) {
                if (!board.getFields()[x1][y1].getPawn().isQueen()) {
                    return (x1 + 1 == x2 && (y1 + 1 == y2 || y1 - 1 == y2)) || (x1 - 1 == x2 && (y1 + 1 == y2 || y1 - 1 == y2));

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

        return false;
    }

    //bicie i dla damek tez
    public void capturePawn(int x1, int y1, int x2, int y2) {           //pozycje mn dla pionka zostaly zaakceptowane przez poprzednia funkcje wiec mozna wykonac bez sprawdzenia

            if (!board.getFields()[x1][y1].getPawn().isQueen()) {
                board.getFields()[x2][y2].setPawn(board.getFields()[x1][y1].getPawn());
                board.getFields()[x1][y1].setPawn(null);
                board.getFields()[(x1 + x2) / 2][(y1 + y2) / 2].setPawn(null);
                if (board.getFields()[x2][y2].getColor().equals(Color.rgb(0, 0, 0))) {
                    numberOfWhitePawns--;
                }
                else if(board.getFields()[x2][y2].getColor().equals(Color.rgb(255, 255, 255))) {
                    numberOfBlackPawns--;
                }
            }
            else {
                board.getFields()[x2][y2].setPawn(board.getFields()[x1][y1].getPawn());
                int diffX;
                int diffY;

                diffX = x2 - x1;
                diffY = y2 - y1;

                //w tym wypadku mamy juz pewnosc, ze nie ma zadnych pionków poza jednym (o kolorze przeciwnym ) na naszej drodze, wiec zmieniamy na null napotkany pionek.
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
