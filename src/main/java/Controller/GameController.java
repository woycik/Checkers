package Controller;

import Model.Board;
import Model.Field;
import Model.PlayerTurn;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public abstract class GameController {
    protected Board board;
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
        this.playerTurn = PlayerTurn.White;
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

    public Board getBoard() {
        return board;
    }

    public  void createNewQueen(int x,int y){
        if(y==0) {
            if (!board.getFields()[x][y].getColor().equals(Color.rgb(0, 0, 0))) {
                board.getFields()[x][y].getPawn().makeQueen();
            }
        }
        else if(y==board.getSize()-1){
            if(!board.getFields()[x][y].getColor().equals(Color.rgb(255,255,255))){
                board.getFields()[x][y].getPawn().makeQueen();
            }
        }
    }

    //zmiana lokalizacji pionka, juz nie trzeba sprawdzac poprawnosci
    public boolean movePawn(int x1, int y1, int x2, int y2) {
        if (board.getFields()[x1][y1].isOccupied()) {
            board.getFields()[x2][y2].setPawn(board.getFields()[x1][y1].getPawn());
            board.getFields()[x1][y1].setPawn(null);
            this.createNewQueen(x2,y2);
            return true;
        }
        return false;
    }

    public void nextTurn() {
        if (playerTurn == PlayerTurn.White) {
            playerTurn = PlayerTurn.Black;
        } else {
            playerTurn = PlayerTurn.White;
        }
    }
}
