package ControllerTest;

import Controller.GameController;
import Model.Board;
import Model.Field;
import Model.PlayerTurn;
import javafx.scene.paint.Color;

import static javafx.scene.paint.Color.rgb;

public class GameControllerTest {
    protected Board board;
    protected GameController controller;
    protected Field[][] fields;
    protected final Color white = rgb(255, 255, 255);
    protected final Color black = rgb(0, 0, 0);

    protected void initialize(GameController controller) {
        this.controller = controller;
        this.fields = board.getFields();
        this.board = controller.getBoard();
    }
}
