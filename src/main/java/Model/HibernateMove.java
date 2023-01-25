package Model;

public class HibernateMove {
    private int  id;
    private int moveNumber;
    private int beginX;
    private int beginY;
    private int endX;
    private int endY;
    private String  playerColor;
    private HibernateGame gameId;

    public HibernateMove(int move_number, int beginX, int beginY, int endX, int endY,String playerColor,HibernateGame gameId) {
        this.moveNumber = move_number;
        this.beginX = beginX;
        this.beginY = beginY;
        this.endX = endX;
        this.endY = endY;
        this.playerColor = playerColor;
        this.gameId = gameId;
    }

    public HibernateMove() {

    }

    public HibernateGame getGameId() {
        return gameId;
    }

    public int getMoveNumber() {
        return moveNumber;
    }

    public int getBeginX() {
        return beginX;
    }

    public int getBeginY() {
        return beginY;
    }

    public int getEndX() {
        return endX;
    }

    public int getEndY() {
        return endY;
    }

    public int getId() {
        return id;
    }

    public String getPlayerColor() {
        return playerColor;
    }
    public void setId(int id) {
        this.id = id;
    }

    public void setMoveNumber(int move_number) {
        this.moveNumber = move_number;
    }

    public void setBeginX(int beginX) {
        this.beginX = beginX;
    }

    public void setBeginY(int beginY) {
        this.beginY = beginY;
    }

    public void setEndX(int endX) {
        this.endX = endX;
    }

    public void setEndY(int endY) {
        this.endY = endY;
    }

    public void setGameId(HibernateGame gameId) {
        this.gameId = gameId;
    }

    public void setPlayerColor(String playerColor) {
        this.playerColor = playerColor;
    }
}

