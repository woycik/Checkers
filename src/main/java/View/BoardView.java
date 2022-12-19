package View;

import Model.Board;
import Model.Field;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


public class BoardView extends Pane{
    Field[][] fields;
    int boardSize;
    private Rectangle[][] board;

    public BoardView(int boardSize) {
        super();
        this.boardSize=boardSize;

        board = new Rectangle[boardSize][boardSize];


        for(int x=0; x < boardSize; x++){
            for(int j=0; j < boardSize; j++){
                board[x][j] = new Rectangle();
                board[x][j].setX(x * 50);
                board[x][j].setY(j * 50);
                board[x][j].setWidth(50);
                board[x][j].setHeight(50);
            }
        }


        for(int x=0; x < boardSize; x++){
            for(int j=0; j < boardSize; j++){
                if((x+j)%2==0){
                    board[x][j].setFill(Color.BROWN);
                }
                else{
                    board[x][j].setFill(Color.BISQUE);
                }
                getChildren().add(board[x][j]);
            }
        }
    }



    public void update(Board board) {
        fields=board.getFields();
        for(int i=0;i<board.getSize();i++){
            for(int j=0;j<board.getSize();j++){
                if(fields[i][j].isOccupied()){

                    PawnView pawn=new PawnView(50*i+25,50*j+25,20,fields[i][j].getColor());
                    this.getChildren().add(pawn);

                }
            }

        }

    }
}
