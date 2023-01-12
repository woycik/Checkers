package Model;

import javafx.scene.paint.Color;

/**
 * class responsible for pawn properties
 */
public class Pawn {
    private final Color color;
    private boolean isQueen;

    /**
     * Constructor
     * @param color pawn color
     */
    public Pawn(Color color) {
        this.color = color;
        this.isQueen = false;
    }
    /**
     * Constructor
     * @param color pawn color
     * @param isQueen true if pawn is a queen
     */

    public Pawn(Color color, boolean isQueen) {
        this.color = color;
        this.isQueen = isQueen;
    }

    /**
     * Color returning method
     * @return Color
     */

    public Color getColor() {
        return color;
    }

    /**
     * Check if pawn is a queen
     * @return boolaen
     */

    public boolean isQueen() {
        return isQueen;
    }

    /**
     * Method that make queens
     */
    public void makeQueen() {
        isQueen = true;
    }
}
