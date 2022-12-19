package Controller;

import Model.Board;
import Model.Field;
import Model.PlayerTurn;

import java.util.ArrayList;

public abstract class GameController {
    public Board board;
    protected Field[][] fields;
    public PlayerTurn playerTurn;
    protected ArrayList<Field> blackPawns;
    protected ArrayList<Field> whitePawns;
    protected ArrayList<Field> capturePossible; //lista pól w które może wskoczyc pionek w ramach bicia
    protected int numberOfWhitePawns;
    protected int numberOfBlackPawns;
    protected boolean finishCapture;

    public abstract boolean makeMove(int x1, int y1, int x2, int y2);

    public abstract boolean isMoveLegal(int x1, int y1, int x2, int y2);

    public abstract int getBoardSize();

    public abstract int getPawnRows();

    public GameController() {
        this.board = new Board(getBoardSize(), getPawnRows());
        this.numberOfBlackPawns = getBoardSize() / 2 * getPawnRows();
        this.numberOfWhitePawns = getBoardSize() / 2 * getPawnRows();
        this.blackPawns = new ArrayList<>();
        this.whitePawns = new ArrayList<>();
        this.capturePossible = new ArrayList<>();
        this.finishCapture = false;
        this.fields = board.getFields();
    }

    public boolean isCapturePossible() {
        return capturePossible.size() > 0;
    }

    //sprawdzenie czy białe wygrały
    public boolean isWhiteWinner() {
        return numberOfBlackPawns == 0;
    }

    //sprawdzenie czy czarne wygrały
    public boolean isBlackWinner() {
        return numberOfWhitePawns == 0;
    }

    //zmiana lokalizacji pionka, juz nie trzeba sprawdzac poprawnosci
    public boolean movePawn(int x1, int y1, int x2, int y2) {
        if (fields[x1][y1].isOccupied()) {
            fields[x2][y2].setPawn(fields[x1][y1].getPawn());
            fields[x1][y1].setPawn(null);
            return true;
        }
        return false;
    }
}
