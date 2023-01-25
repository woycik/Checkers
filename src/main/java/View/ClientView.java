package View;

import Controller.Client;
import Model.Board;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * Displays current connection and game board state.
 */
public class ClientView {
    private final Client client;
    private final Stage stage;
    private BoardView boardView;

    public ClientView(Client client, Stage stage) {
        this.client = client;
        this.stage = stage;
    }

    /**
     * Initializes scene and displays connection status.
     */
    public void init() {
        final BorderPane borderPane = new BorderPane();
        Label clientStatusLabel = new Label("Trying to connect with server.");
        clientStatusLabel.setId("clientStatusLabel");
        borderPane.setCenter(clientStatusLabel);

        final Scene scene = new Scene(borderPane, 500, 500);
        stage.setResizable(false);
        stage.setOnCloseRequest(e -> Platform.exit());
        stage.setTitle("Checkers client");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Informs client about successful connection and awaiting the second client.
     */
    public void waitingForOpponent() {
        Scene scene = stage.getScene();
        Label clientStatusLabel = (Label) scene.lookup("#clientStatusLabel");
        clientStatusLabel.setText("Connected succesfully. Waiting for the oponent.");
    }

    /**
     * Informs client about connection failure.
     */
    public void connectionFailed() {
        Scene scene = stage.getScene();
        Label clientStatusLabel = (Label) scene.lookup("#clientStatusLabel");
        clientStatusLabel.setText("Could not connect with the server. Please try again later.");
    }

    /**
     * Informs client about server disconnection.
     */
    public void serverDisconnected() {
        final BorderPane borderPane = new BorderPane();
        Scene scene = stage.getScene();
        Label clientStatusLabel = new Label("Server disconnected.");
        borderPane.setCenter(clientStatusLabel);
        scene.setRoot(borderPane);
    }

    /**
     * Initializes and displays game board for the first time.
     * @param boardSize Number of fields horizontally and vertically on the board.
     */
    public void showBoard(int boardSize,boolean isRepeated) {
        Scene scene = stage.getScene();
        boardView = new BoardView(boardSize, client,isRepeated);
        scene.setRoot(boardView);
    }

    /**
     * Informs about game end and announces winning player.
     * @param winner Winner's color
     */
    public void announceWinner(String winner) {
        final BorderPane borderPane = new BorderPane();
        Scene scene = stage.getScene();
        String message = client.thread.playerColor.equals(winner) ? "You won!" : "You lost.";
        Label clientStatusLabel = new Label(message);
        borderPane.setCenter(clientStatusLabel);
        scene.setRoot(borderPane);
    }

    /**
     * Updates current game board state.
     * @param board Board object reconstructed from server's message
     * @param playerColor Color of player allowed to make next move
     */
    public void updateBoard(Board board, String playerColor) {
        boardView.update(board, playerColor);
    }

    /**
     * Flips board vertically to place player's pawns on the bottom of the board.
     */
    public void flipBoard(){
        boardView.flipScene();
    }
}
