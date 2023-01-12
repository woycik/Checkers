package Model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class PolishBoard extends Board {


    public PolishBoard() {
        super(10, 4);
    }

    @Override
    public PolishBoard clone() {
        PolishBoard boardClone = new PolishBoard();
        for (int i = 0; i < this.getBoardSize(); i++) {
            for (int j = 0; j < this.getBoardSize(); j++) {
                Field f = new Field(fields[i][j].getX(), fields[i][j].getY());
                f.setPawn(fields[i][j].getPawn());
                boardClone.fields[i][j] = f;

            }
        }
        return boardClone;
    }

    public List<Field> getLongestMove() {
        ArrayList<Move> moves = new ArrayList<>();
        ArrayList<Integer> length = new ArrayList<>();

        for (Field f : capturePossible) {
            moves.addAll(this.longestPawnTake(f));

        }
        for (Move move : moves) {
            length.add(move.length);

        }
        moves.removeIf(move -> move.length < Collections.max(length));


        return moves.stream().map(Move::getEndField).collect(Collectors.toList());
    }

    public ArrayList<Move> longestPawnTake(Field field) {
        ArrayList<Move> moves = new ArrayList<>();
        ArrayList<Integer> length = new ArrayList<>();
        for (Field f : fields[field.getX()][field.getY()].getPossibleCaptures()) {
            PolishBoard bc = this.clone();
            bc.capturePawn(field.getX(), field.getY(), f.getX(), f.getY());
            bc.addToPossibleCaptures();
            if (!bc.longestPawnTake(f).isEmpty()) {
                moves.add(new Move(field, f, 1 + bc.longestPawnTake(f).get(0).length));
            } else {
                moves.add(new Move(f, f, 0));
            }

        }
        for (Move move : moves) {
            length.add(move.length);
        }
        moves.removeIf(move -> move.length < Collections.max(length));


        return moves;
    }


    @Override
    public int getBoardSize() {
        return 10;
    }

    @Override
    public int getPawnRows() {
        return 4;
    }
}
