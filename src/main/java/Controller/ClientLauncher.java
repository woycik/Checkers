package Controller;

import View.BoardGui;
import View.ClientView;
import javafx.application.Application;
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

public class ClientLauncher extends Application {
    Stage stage;
    @Override
    public void start(Stage stage) {
        stage = new ClientView();
    }
    public static void main(String[] args) {
        launch(args);
    }
}
