package View;

import Controller.Client;
import Model.Board;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class ClientView {
    Client client;
    Stage stage;
    BoardView boardView;

    public ClientView(Client client, Stage stage) {
        this.client = client;
        this.stage = stage;
    }

    public void init() {
        final BorderPane borderPane = new BorderPane();
        Label clientStatusLabel = new Label("Trying to connect with server.");
        clientStatusLabel.setId("clientStatusLabel");
        borderPane.setCenter(clientStatusLabel);

        final Scene scene = new Scene(borderPane, 500, 500);
        stage.setOnCloseRequest(e -> Platform.exit());
        stage.setTitle("Checkers client");
        stage.setScene(scene);
        stage.show();
    }

    public void waitingForOpponent() {
        Scene scene = stage.getScene();
        Label clientStatusLabel = (Label) scene.lookup("#clientStatusLabel");
        clientStatusLabel.setText("Connected succesfully. Waiting for the oponent.");
    }

    public void connectionFailed() {
        Scene scene = stage.getScene();
        Label clientStatusLabel = (Label) scene.lookup("#clientStatusLabel");
        clientStatusLabel.setText("Could not connect with the server. Please try again later.");
    }

    public void showBoard(int boardSize) {
        Scene scene = stage.getScene();
        boardView = new BoardView(boardSize, client);
        scene.setRoot(boardView);
    }

    public void announceWinner(String winner) {
        final BorderPane borderPane = new BorderPane();
        Scene scene = stage.getScene();
        Label clientStatusLabel = new Label(winner + " wins!");
        borderPane.setCenter(clientStatusLabel);
        scene.setRoot(borderPane);
    }

    public void updateBoard(Board board, String playerColor) {
        boardView.update(board, playerColor);
    }
}
