package Model;

/**
 * Checkers game polish board operation handling class
 */

public class PolishBoard extends Board {
    /**
     * Constructor
     */
    public PolishBoard() {
        super(10, 4);
    }

    /**
     * Game variant returning method
     *
     * @return game variant
     */
    @Override
    public String getGameVariant() {
        return "Polish";
    }
}

