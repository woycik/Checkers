package View;

import Controller.Server;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class ServerView {
    Server server;
    Stage stage;

    public ServerView(Server server, Stage stage) {
        this.server = server;
        this.stage = stage;
    }

    public void init() {
        final BorderPane borderPane=new BorderPane();

        //select mode
        Button polishButton = new Button("Polish mode");
        Button russianButton = new Button("Russian mode");
        Button englishButton = new Button("English mode");

        //adding labels and buttons into gridpane
        Label chooseModeLabel = new Label("Choose mode: ");
        GridPane menu = new GridPane();
        menu.add(chooseModeLabel,1,0);
        menu.add(polishButton,0,1);
        menu.add(russianButton,1,1);
        menu.add(englishButton,2,1);
        menu.setHgap(10);
        menu.setVgap(20);

        //setting styles
        polishButton.setStyle("-fx-background-color: lightblue; -fx-text-fill: white; -fx-padding: 15px;");
        russianButton.setStyle("-fx-background-color: lightblue; -fx-text-fill: white; -fx-padding: 15px;");
        englishButton.setStyle("-fx-background-color: lightblue; -fx-text-fill: white; -fx-padding: 15px;");

        menu.setAlignment(Pos.CENTER);
        borderPane.setCenter(menu);
        final Scene scene = new Scene(borderPane, 500, 500);

        polishButton.setOnAction(event -> {
            final BorderPane borderPane13 = new BorderPane();
            Label serverStatusLabel = new Label("Selected Polish Checkers. Waiting for players to connect.");
            serverStatusLabel.setId("serverStatusLabel");
            borderPane13.setCenter(serverStatusLabel);
            scene.setRoot(borderPane13);
            server.prepareGame("Polish");
        });

        russianButton.setOnAction(event -> {
            final BorderPane borderPane12 = new BorderPane();
            Label serverStatusLabel = new Label("Selected Russian Checkers. Waiting for players to connect.");
            serverStatusLabel.setId("serverStatusLabel");
            borderPane12.setCenter(serverStatusLabel);
            scene.setRoot(borderPane12);
            server.prepareGame("Russian");
        });

        englishButton.setOnAction(event -> {
            final BorderPane borderPane1 = new BorderPane();
            Label serverStatusLabel = new Label("Selected English Checkers. Waiting for players to connect.");
            serverStatusLabel.setId("serverStatusLabel");
            borderPane1.setCenter(serverStatusLabel);
            scene.setRoot(borderPane1);
            server.prepareGame("English");
        });

        stage.setOnCloseRequest(e -> Platform.exit());
        stage.setTitle("Checkers server");
        stage.setScene(scene);
        stage.show();
    }

    public void bothPlayersConnected() {
        Scene scene = stage.getScene();
        Label serverStatusLabel = (Label)scene.lookup("#serverStatusLabel");
        serverStatusLabel.setText("Both players connected. Starting game.");
    }
}
