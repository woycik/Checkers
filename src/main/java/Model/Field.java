package Model;

import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

/**
 * Class responsible for the fields on the board
 */
public class Field {
    private Pawn pawn;
    private final int x;
    private final int y;
    private final ArrayList<Field> possibleMoves;
    private final ArrayList<Field> possibleCaptures;

    /**
     * Constructor
     * @param x x-coordintare of field position
     * @param y y-coordintare of field position
     */
    public Field(int x, int y) {
        this.x = x;
        this.y = y;
        this.pawn = null;
        this.possibleMoves = new ArrayList<>();
        this.possibleCaptures = new ArrayList<>();
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    /**
     * Check whether this field is occupied
     * @return boolean
     */
    public boolean isOccupied() {
        return pawn != null;
    }

    /**
     * Returning pawn color method
     * @return Color
     */

    public Color getPawnColor() {
        return pawn.getColor();
    }

    /**
     * Returning pawn method
     * @return Pawn
     */
    public Pawn getPawn() {
        return pawn;
    }

    /**
     * Setting pawn method
     * @param pawn
     */
    public void setPawn(Pawn pawn) {
        this.pawn = pawn;
    }

    /**
     * Method that adds a given pawn to the list of possible moves
     * @param field
     */
    public void addToPossibleMoves(Field field) {
        this.possibleMoves.add(field);
    }

    /**
     * Method that adds a given pawn to the list of possible captures
     * @param field
     */
    public void addToPossibleCaptures(Field field) {
        this.possibleCaptures.add(field);
    }
    /**
     * Method that returns  the list of possible moves
     */
    public List<Field> getPossibleMoves() {
        return possibleMoves;
    }
    /**
     * Method that returns  the list of possible captures
     */
    public ArrayList<Field> getPossibleCaptures() {
        return possibleCaptures;
    }

    /**
     * Method that clears the list of possible moves
     */
    public void clearPossibleMove() {
        this.possibleMoves.clear();
    }
    /**
     * Method that clears the list of possible captures
     */
    public void clearPossibleCaptures() {
        this.possibleCaptures.clear();
    }
}
