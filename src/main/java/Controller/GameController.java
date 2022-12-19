package Controller;

import Model.Board;
import Model.PlayerTurn;

public abstract class GameController {
    public Board board;
    public PlayerTurn playerTurn;
    public abstract boolean makeMove(int x, int y,int i, int j);
    public abstract boolean isMoveLegal(int i, int j, int m, int n);
    public abstract int getPawnRows();
    public GameController(int boardSize, int pawnRows) {
        this.board = new Board(boardSize, pawnRows);
    }
    public int getBoardSize() {
        return board.getSize();
    }
}
