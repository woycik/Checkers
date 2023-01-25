package Model;

/**
 * Checkers game russian board operation handling class
 */
public class RussianBoard extends Board {
    /**
     * Russian board constructor
     */
    public RussianBoard() {
        super(8, 3);
    }

    /**
     * Game variant returning method
     *
     * @return game variant
     */
    @Override
    public String getGameVariant() {
        return "Russian";
    }
}
