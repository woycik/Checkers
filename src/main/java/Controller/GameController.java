package Controller;

import Model.Board;
import Model.PlayerTurn;
import javafx.scene.paint.Color;

public abstract class GameController {
    protected Board board;
    public PlayerTurn playerTurn;
    protected int numberOfWhitePawns;
    protected int numberOfBlackPawns;
    protected boolean finishCapture;

    protected abstract boolean makeMove(int x1, int y1, int x2, int y2);
    public abstract int getBoardSize();
    public abstract int getPawnRows();

    public GameController() {
        finishCapture = false;
        playerTurn = PlayerTurn.White;
        numberOfBlackPawns = getBoardSize() / 2 * getPawnRows();
        numberOfWhitePawns = getBoardSize() / 2 * getPawnRows();
    }

    //sprawdzenie czy białe wygrały
    public boolean isWhiteWinner() {
        setNumberOfPawns();
        return numberOfBlackPawns == 0;
    }

    //sprawdzenie czy czarne wygrały
    public boolean isBlackWinner() {
        setNumberOfPawns();
        return numberOfWhitePawns == 0;
    }

    public Board getBoard() {
        return board;
    }

    public boolean move(int x1, int y1, int x2, int y2) {
        board.setMyPawns();
        board.addToPossibleMoves();
        board.addToPossibleCaptures(playerTurn.toString());
        if (makeMove(x1, y1, x2, y2)) {
            nextTurn();
            return true;
        }
        return false;
    }

    protected void setNumberOfPawns() {
        numberOfBlackPawns = board.getNumberOfBlackPawns();
        numberOfWhitePawns = board.getNumberOfWhitePawns();
    }

    public void nextTurn() {
        if (playerTurn == PlayerTurn.White) {
            playerTurn = PlayerTurn.Black;
        } else {
            playerTurn = PlayerTurn.White;
        }
    }

    public String getGameVariant() {
        if(board != null) {
            return board.getGameVariant();
        }
        return "None";
    }
}
