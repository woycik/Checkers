package View;

import Model.Board;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class BoardView extends GridPane{
    Pane[][] pane;

    public BoardView(int boardSize) {
        super();
        this.setGridLinesVisible(true);

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
                this.add(pane[i][j], i, j);
            }
        }
    }

    public void update(Board board) {
        // TODO: update view with an acutal board state
    }
}
