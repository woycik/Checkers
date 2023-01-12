package Model;

import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class Field {
    private Pawn pawn;
    private final int x;
    private final int y;
    private final ArrayList<Field> possibleMoves;
    private final ArrayList<Field> possibleCaptures;

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

    public boolean isOccupied() {
        return pawn != null;
    }

    public Color getPawnColor() {
        return pawn.getColor();
    }

    public Pawn getPawn() {
        return pawn;
    }

    public void setPawn(Pawn pawn) {
        this.pawn = pawn;
    }

    public void addToPossibleMoves(Field field) {
        this.possibleMoves.add(field);
    }

    public void addToPossibleCaptures(Field field) {
        this.possibleCaptures.add(field);
    }

    public List<Field> getPossibleMoves() {
        return possibleMoves;
    }

    public ArrayList<Field> getPossibleCaptures() {
        return possibleCaptures;
    }

    public void setPossibleCaptures(List<Field> f) {
        this.possibleCaptures.addAll(f);
    }

    public void clearPossibleMove() {
        this.possibleMoves.clear();
    }

    public void clearPossibleCaptures() {
        this.possibleCaptures.clear();
    }
}
