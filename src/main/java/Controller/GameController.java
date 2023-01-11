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
    protected ArrayList<Field> capturePossible;
    protected int numberOfWhitePawns;
    protected int numberOfBlackPawns;
    protected boolean finishCapture;

    protected abstract boolean makeMove(int x1, int y1, int x2, int y2) throws CloneNotSupportedException;


    public abstract int getBoardSize();

    public abstract int getPawnRows();

    public GameController() {
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
            if (!board.getFields()[x][y].getPawnColor().equals(Color.rgb(0, 0, 0))) {
                board.getFields()[x][y].getPawn().makeQueen();
            }
        }
        else if(y==board.getSize()-1){
            if(!board.getFields()[x][y].getPawnColor().equals(Color.rgb(255,255,255))){
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

    public boolean isCapturePossibleTopRight(int x,int y,Color color){
        if((x+2)<=(this.getBoardSize()-1) && (y-2)>=0){
            if(board.getFields()[x+1][y-1].isOccupied() && !board.getFields()[x+2][y-2].isOccupied() ) {
                return ((!board.getFields()[x + 1][y - 1].getPawnColor().equals(color)));
            }
        }
        return false;
    }
    public boolean isCapturePossibleTopLeft(int x,int y,Color color){
        if((x-2)>=0 && (y-2)>=0){
            if(board.getFields()[x-1][y-1].isOccupied() && !board.getFields()[x-2][y-2].isOccupied() ) {
                return ((!board.getFields()[x - 1][y - 1].getPawnColor().equals(color)));
            }
        }
        return false;
    }


    public boolean isCapturePossibleBottomLeft(int x,int y,Color color){
        if((y + 2)<=(this.getBoardSize()-1) && (x-2)>=0){
            if(board.getFields()[x-1][y+1].isOccupied() && !board.getFields()[x-2][y+2].isOccupied() ) {
                return ((!board.getFields()[x - 1][y + 1].getPawnColor().equals(color)));
            }
        }
        return false;
    }

    public boolean isCapturePossibleBottomRight(int x,int y,Color color){
        if((x+2)<=(this.getBoardSize()-1) && (y+2)<=(this.getBoardSize()-1)){
            if(board.getFields()[x+1][y+1].isOccupied() && !board.getFields()[x+2][y+2].isOccupied() && board.getFields()[x][y].isOccupied()) {
                return ((!board.getFields()[x + 1][y + 1].getPawnColor().equals(color)));
            }
        }
        return false;
    }

    public void removePawnsFromList(){
        blackPawns.clear();
        whitePawns.clear();
    }

    public void setMyPawns() {
        for (int i = 0; i < this.getBoardSize(); i++) {
            for (int j = 0; j < this.getBoardSize(); j++) {
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

    public boolean move(int x1, int y1, int x2, int y2) throws CloneNotSupportedException {
        if(makeMove(x1, y1, x2, y2)) {
            nextTurn();
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
