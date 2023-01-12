package View;

import Controller.Server;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ServerView {
    final Server server;
    final Stage stage;

    public ServerView(Server server, Stage stage) {
        this.server = server;
        this.stage = stage;
    }

    public void init() {
        final BorderPane borderPane = new BorderPane();

        //select mode
        Button polishButton = new Button("Polish mode");
        Button russianButton = new Button("Russian mode");
        Button englishButton = new Button("English mode");

        //adding labels and buttons into gridpane
        Label chooseModeLabel = new Label("Choose mode:");
        GridPane menu = new GridPane();
        menu.add(chooseModeLabel, 1, 0);
        menu.add(polishButton, 0, 1);
        menu.add(russianButton, 1, 1);
        menu.add(englishButton, 2, 1);
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
            startGame(scene, "Polish");
        });

        russianButton.setOnAction(event -> {
            startGame(scene, "Russian");
        });

        englishButton.setOnAction(event -> {
            startGame(scene, "English");
        });

        stage.setOnCloseRequest(e -> Platform.exit());
        stage.setTitle("Checkers server");
        stage.setScene(scene);
        stage.show();
    }

    public void startGame(Scene scene, String type) {
        final BorderPane borderPane = new BorderPane();
        final VBox vbox = new VBox(20);
        Label serverStatusLabel = new Label("Selected " + type + " Checkers. Waiting for players to connect.");
        Button stopButton = new Button("Stop");
        stopButton.setStyle("-fx-background-color: darkred; -fx-text-fill: white; -fx-padding: 15px;");
        stopButton.setOnAction(event -> {
            server.stop();
            init();
        });
        serverStatusLabel.setId("serverStatusLabel");
        vbox.setAlignment(Pos.CENTER);
        vbox.getChildren().add(serverStatusLabel);
        vbox.getChildren().add(stopButton);
        borderPane.setCenter(vbox);
        scene.setRoot(borderPane);
        server.prepareGame(type);
    }

    public void announceWinner(String winner) {
        Scene scene = stage.getScene();
        Label serverStatusLabel = (Label) scene.lookup("#serverStatusLabel");
        if(serverStatusLabel == null) {
            serverStatusLabel = new Label();
            serverStatusLabel.setId("serverStatusLabel");
        }
        serverStatusLabel.setText(winner + " wins!");
    }

    public void bothPlayersConnected() {
        Scene scene = stage.getScene();
        Label serverStatusLabel = (Label) scene.lookup("#serverStatusLabel");
        if(serverStatusLabel == null) {
            serverStatusLabel = new Label();
            serverStatusLabel.setId("serverStatusLabel");
        }
        serverStatusLabel.setText("Both players connected. Starting game.");
    }
}
