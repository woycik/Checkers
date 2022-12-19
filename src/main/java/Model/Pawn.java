package Model;

import javafx.scene.paint.Color;

import java.util.ArrayList;

public class Pawn {
    public ArrayList<Pawn> possibleFields;
    private final Color color;
    private boolean isQueen;

    public Pawn(Color color) {
        this.color = color;
        this.isQueen = false;
        this.possibleFields = new ArrayList<>();
    }

    public Pawn(Color color, boolean isQueen) {
        this.color = color;
        this.isQueen = isQueen;
        this.possibleFields = new ArrayList<>();
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
