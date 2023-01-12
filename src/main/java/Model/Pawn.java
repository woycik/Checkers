package Model;

import javafx.scene.paint.Color;

public class Pawn {
    private final Color color;
    private boolean isQueen;

    public Pawn(Color color) {
        this.color = color;
        this.isQueen = false;
    }

    public Pawn(Color color, boolean isQueen) {
        this.color = color;
        this.isQueen = isQueen;
    }

    public Color getColor() {
        return color;
    }

    public boolean isQueen() {
        return isQueen;
    }

    public void makeQueen() {
        isQueen = true;
    }
}
