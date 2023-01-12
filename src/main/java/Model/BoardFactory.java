package Model;

public class BoardFactory {
    public Board createBoard(String gameVariant) {
        Board board;

        switch(gameVariant) {
            case "Polish":
                board = new PolishBoard();
                break;
            case "Russian":
                board = new RussianBoard();
                break;
            case "English":
                board = new EnglishBoard();
                break;
            default:
                board = null;
                break;
        }

        return board;
    }
}
