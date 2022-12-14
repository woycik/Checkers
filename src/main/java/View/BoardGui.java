package View;

import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class BoardGui extends GridPane{
    Pane pane[][];
    BoardGui() {
        super();
        this.setGridLinesVisible(true);
        this.setGermanBoard();
        this.setStartingPosition();
    }

    public void setGermanBoard(){
        int numCols = 8;
        int numRows = 8;

        pane = new Pane[numCols][];
        for (int i = 0; i < numCols; i++) {
            pane[i] = new Pane[numRows];
        }


        for (int i = 0; i < numCols; i++) {
            ColumnConstraints colcolumn = new ColumnConstraints();
            colcolumn.setPercentWidth(100.0 / numCols);
           this.getColumnConstraints().add(colcolumn);
        }

        for (int i = 0; i < numRows; i++) {
            RowConstraints rowcolumn = new RowConstraints();
            rowcolumn.setPercentHeight(100.0 / numRows);
            this.getRowConstraints().add(rowcolumn);
        }

        for (int i = 0; i < numCols; i++) {
            for (int j = 0; j < numRows; j++) {
                pane[i][j] = new Pane();
                if((i+j)%2==0){
                    pane[i][j].setBackground(new Background(new BackgroundFill(Color.rgb(231,207,174), null, null)));
                }
                else{
                    pane[i][j].setBackground(new Background(new BackgroundFill(Color.rgb(146,73,49), null, null)));
                }
                this.add(pane[i][j], i, j);
            }
        }
    }

    public void setStartingPosition(){
        pane[1][0].getChildren().add(new Pawn(30,10,10,Color.rgb(0,0,0)));
    }


}
