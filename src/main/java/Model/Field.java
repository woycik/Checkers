package Model;

import javafx.scene.paint.Color;

import java.util.ArrayList;

public class Field {
    private Pawn pawn;
    private final int x;
    private final int y;

    public Field(int x, int y) {
        this.x = x;
        this.y = y;
        this.pawn = null;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isOccupied() {
        return pawn != null;
    }

    public Color getColor(){
        return pawn.getColor();
    }

    public Pawn getPawn() {
        return pawn;
    }

    public void setPawn(Pawn pawn){
        this.pawn = pawn;
    }

    public void addToPossibleFields(Pawn pawn){
        pawn.possibleFields.add(pawn);
    }

    public ArrayList<Pawn> getPossibleFields() {
        return pawn.possibleFields;
    }
}
