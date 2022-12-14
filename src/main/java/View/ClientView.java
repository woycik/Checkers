package View;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class ClientView extends Stage {
    public ClientView() {
        final BorderPane borderPane=new BorderPane();

        Label text = new Label("Waiting for the other player");
        borderPane.setCenter(text);

        final Scene scene = new Scene(borderPane, 500, 500);
        setTitle("Checkers client");
        setScene(scene);
        show();
    }
}
