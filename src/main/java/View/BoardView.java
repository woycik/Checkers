package View;

import Model.Board;
import Model.Field;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


public class BoardView extends Pane {
    public BoardView(int boardSize) {
        super();
        Rectangle[][] rectangles = new Rectangle[boardSize][boardSize];

        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                rectangles[i][j] = new Rectangle();
                rectangles[i][j].setX(i * 50);
                rectangles[i][j].setY(j * 50);
                rectangles[i][j].setWidth(50);
                rectangles[i][j].setHeight(50);
            }
        }

        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                if ((i + j) % 2 == 0) {
                    rectangles[i][j].setFill(Color.BROWN);
                }
                else {
                    rectangles[i][j].setFill(Color.BISQUE);
                }
                getChildren().add(rectangles[i][j]);
            }
        }
    }

    public void update(Board board) {
        Field[][] fields = board.getFields();
        for (int i = 0; i < board.getSize(); i++) {
            for (int j = 0; j < board.getSize(); j++) {
                if (fields[i][j].isOccupied()) {
                    PawnView pawn = new PawnView(50 * i + 25, 50 * j + 25, 20, fields[i][j].getColor());
                    this.getChildren().add(pawn);
                }
            }
        }
    }
}
