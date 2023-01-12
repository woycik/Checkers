package Model;

public class Move {
    public Field startField;
    public Field endField;
    public int length;

    Move(Field startField, Field endField, int length) {
        this.startField = startField;
        this.endField = endField;
        this.length = length;
    }

    public Field getEndField() {
        return endField;
    }
}
