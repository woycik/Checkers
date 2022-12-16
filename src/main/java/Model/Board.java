package Model;

import static javafx.scene.paint.Color.rgb;

public class Board {
    private Field[][] fields;

    public Board(int size, int pawnRows) {
        this.fields = new Field[size][size];
        for(int i = 0; i < size; i++) {
            for(int j = 0; j < size; j++) {
                fields[i][j] = new Field(i, j);
                if((i + j) % 2 == 0 && i < pawnRows) {
                    fields[i][j].setPawn(new Pawn(rgb(0,0,0)));
                }
                else if((i + j) % 2 == 0 && i > (size - pawnRows)) {
                    fields[i][j].setPawn(new Pawn(rgb(255,255,255)));
                }
            }
        }
    }

    public Field[][] getFields(){
        return fields;
    }

    public int getSize() {
        return fields.length;
    }
}
