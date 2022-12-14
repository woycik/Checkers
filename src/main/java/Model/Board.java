package Model;

public class Board {
    Pawn[][] pawns;

    public Board(Pawn[][] pawns) {
        this.pawns = pawns;
    }

    public Board(int size) {
        this.pawns = new Pawn[size][size];
    }
}
