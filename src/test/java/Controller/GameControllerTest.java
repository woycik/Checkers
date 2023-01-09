package Controller;

import Model.Field;
import javafx.scene.paint.Color;

import static javafx.scene.paint.Color.rgb;

public class GameControllerTest {
    protected GameController controller;
    protected Field[][] fields;
    protected final Color white = rgb(255, 255, 255);
    protected final Color black = rgb(0, 0, 0);

    protected void initialize(GameController controller) {
        this.controller = controller;
        this.fields = controller.getBoard().getFields();
    }
}
