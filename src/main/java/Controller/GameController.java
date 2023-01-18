package Controller;

import Model.Board;
import Model.PlayerTurn;

/**
 *Class responsible for controlling the game of checkers
 */

public abstract class GameController {
    protected Board board;
    public PlayerTurn playerTurn;
    protected int numberOfWhitePawns;
    protected int numberOfBlackPawns;
    protected boolean isCaptureFinished;

    protected abstract boolean makeMove(int x1, int y1, int x2, int y2);
    public abstract int getBoardSize();
    public abstract int getPawnRows();

    /**
     * Constructor of game controller
     */
    public GameController() {
        isCaptureFinished = false;
        playerTurn = PlayerTurn.White;
        numberOfBlackPawns = getBoardSize() / 2 * getPawnRows();
        numberOfWhitePawns = getBoardSize() / 2 * getPawnRows();
    }

    /**
     * Checks whether white player is a winner
     * @return true if white is a winner
     */
    public boolean isWhiteWinner() {
        setNumberOfPawns();
        return numberOfBlackPawns == 0;
    }

    /**
     * Checks whether black player is a winner
     * @return true if black is a winner
     */
    public boolean isBlackWinner() {
        setNumberOfPawns();
        return numberOfWhitePawns == 0;
    }

    /**
     * Board returning method
     * @return board
     */
    public Board getBoard() {
        return board;
    }

    /**
     * Move pawn method
     * @param x1 x-coordinate of  pawn starting position
     * @param y1 y-coordinate of  pawn starting position
     * @param x2 x-coordinate of pawn ending position
     * @param y2 y-coordinate of pawn ending position
     * @return true if move was made done correctly
     */
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

    /**
     * Sets the number of pawns for each color
     */
    protected void setNumberOfPawns() {
        numberOfBlackPawns = board.getNumberOfBlackPawns();
        numberOfWhitePawns = board.getNumberOfWhitePawns();
    }

    /**
     * Change player turn
     */
    public void nextTurn() {
        if (playerTurn == PlayerTurn.White) {
            playerTurn = PlayerTurn.Black;
        } else {
            playerTurn = PlayerTurn.White;
        }
    }

    /**
     * Game varinat returning method
     * @return game variant
     */
    public String getGameVariant() {
        if(board != null) {
            return board.getGameVariant();
        }
        return "None";
    }

    public void prepareBoard(){
        board.capturePossible.clear();
        if (playerTurn == PlayerTurn.Black) {
            board.captureFieldList(board.blackPawns);
        } else {
            board.captureFieldList(board.whitePawns);
        }
    }
}
