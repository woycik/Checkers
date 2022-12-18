package View;

import Model.Board;
import Model.Field;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Screen;
import javafx.stage.Window;

import java.util.ArrayList;

public class BoardView extends GridPane{
    Pane[][] pane;
    Field[][] fields;
    int boardSize;

    public BoardView(int boardSize) {
        super();
        this.setGridLinesVisible(true);
        this.boardSize=boardSize;

        Background lightBackground = new Background(new BackgroundFill(Color.rgb(231, 207, 174), null, null));
        Background darkBackground = new Background(new BackgroundFill(Color.rgb(146, 73, 49), null, null));

       pane = new Pane[boardSize][];
       for (int i = 0; i < boardSize; i++) {
           ColumnConstraints columnConstraints = new ColumnConstraints();
           columnConstraints.setPercentWidth(100.0 / boardSize);
           this.getColumnConstraints().add(columnConstraints);

           RowConstraints rowConstraints = new RowConstraints();
           rowConstraints.setPercentHeight(100.0 / boardSize);
           this.getRowConstraints().add(rowConstraints);

           pane[i] = new Pane[boardSize];
           for (int j = 0; j < boardSize; j++) {
               pane[i][j] = new Pane();
               if((i + j) % 2 == 0) {
                   pane[i][j].setBackground(lightBackground);
               }
               else{
                   pane[i][j].setBackground(darkBackground);
               }
               this.add(pane[i][j],i,j);
           }

       }


    }

    public void update(Board board) {
        // TODO: update view with an acutal board state

        fields=board.getFields();
        for(int i=0;i<board.getSize();i++){
            for(int j=0;j<board.getSize();j++){
                pane[i][j].getChildren().clear();
                if(fields[i][j].isOccupied()){
                    PawnView pawn=new PawnView(25,25,20,fields[i][j].getColor());
                    pane[i][j].getChildren().add(pawn);

                }
            }
        }

    }
}
