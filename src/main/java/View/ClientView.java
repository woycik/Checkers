package View;

import Controller.Client;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class ClientView {
    Client client;
    public ClientView(Client client) {
        this.client = client;
    }
    public void init(Stage stage) {
        final BorderPane borderPane=new BorderPane();

        Label text = new Label("Waiting for the other player");
        borderPane.setCenter(text);

        final Scene scene = new Scene(borderPane, 500, 500);
        stage.setTitle("Checkers client");
        stage.setScene(scene);
        stage.show();
    }
}
