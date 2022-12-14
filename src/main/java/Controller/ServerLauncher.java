package Controller;

import View.BoardGui;
import View.ServerView;
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

public class ServerLauncher extends Application {
    Stage stage;

    @Override
    public void start(Stage stage) {
        stage = new ServerView();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
