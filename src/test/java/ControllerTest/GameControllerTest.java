package ControllerTest;

import Controller.GameController;
import Model.Board;
import Model.Field;
import javafx.scene.paint.Color;

import static javafx.scene.paint.Color.rgb;

public class GameControllerTest {
    protected Board board;
    protected Field[][] fields;
    protected final Color white = rgb(255, 255, 255);
    protected final Color black = rgb(0, 0, 0);

    protected void initialize(Board board) {
        this.board = board;
        this.fields = board.getFields();
    }
}
