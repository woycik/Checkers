package Model;

/**
 * Checkers game russian board operation handling class
 */
public class RussianBoard extends Board{
    /**
     * Constructor
     */
    public RussianBoard() {
        super(8,3);
    }

    /**
     * Game variant returning method
     * @return String
     */
    @Override
    public String getGameVariant() {
        return "Russian";
    }
}
