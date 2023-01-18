package Model;

/**
 * Allows creating Board of given type.
 * Implements Factory creational pattern.
 */
public class BoardFactory {
    /**
     * Creates Board for given game variant.
     *
     * @param gameVariant checkers variant
     * @return Board of given type or null if gameVariant is invalid
     */
    public Board createBoard(String gameVariant) {
        Board board;

        switch (gameVariant) {
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
