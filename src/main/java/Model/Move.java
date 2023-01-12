package Model;

/**
 * class responsible for pawn movement
 */
public class Move {
    public final Field startField;
    public final Field endField;
    public final int length;

    /**
     * Constructor
     * @param startField start field
     * @param endField end field
     * @param length length
     */
    Move(Field startField, Field endField, int length) {
        this.startField = startField;
        this.endField = endField;
        this.length = length;
    }

    /**
     * Returns end field of the move
     * @return Field
     */
    public Field getEndField() {
        return endField;
    }
}
