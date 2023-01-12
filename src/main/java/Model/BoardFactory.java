package Model;

public class BoardFactory {
    /**
     * Method that creates different variants of board
     * @param gameVariant variant of the game
     * @return Board
     */
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
