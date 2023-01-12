package Model;

public class RussianBoard extends Board{
    public RussianBoard() {
        super(8,3);
    }

    @Override
    public int getBoardSize() {
        return 8;
    }

    @Override
    public int getPawnRows() {
        return 3;
    }

    @Override
    public String getGameVariant() {
        return "Russian";
    }
}
