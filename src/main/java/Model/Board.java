package Model;

import static javafx.scene.paint.Color.rgb;

public class Board {
    Field[][] field;
    int size;


    public Board(Field[][] field) {
        this.field = field;
    }

    public Board(int size,int columnsWithPaws) {
        this.size=size;
        this.field = new Field[size][size];
        for(int i=0;i<size;i++){
            for(int j=0;j<size;j++){
                field[i][j]=new Field(i,j);
                if((i+j)%2==0 && i<columnsWithPaws){
                    field[i][j].setPawn(new Pawn(rgb(0,0,0)));
                }
                else if((i+j)%2==0 && i>(size-columnsWithPaws)) {
                    field[i][j].setPawn(new Pawn(rgb(255,255,255)));
                }
            }
        }
    }



    public Field[][] getField(){
        return field;
    }

}
