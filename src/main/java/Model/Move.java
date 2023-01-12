package Model;

public class Move {
    public final Field startField;
    public final Field endField;
    public final int length;

    Move(Field startField, Field endField, int length) {
        this.startField = startField;
        this.endField = endField;
        this.length = length;
    }

    public Field getEndField() {
        return endField;
    }
}
