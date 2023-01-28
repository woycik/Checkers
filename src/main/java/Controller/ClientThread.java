package Controller;

import Model.*;
import View.ClientView;
import javafx.application.Platform;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import static javafx.scene.paint.Color.rgb;

/**
 * Thread communicating Client with Server.
 * View requests this thread to send moves to Server,
 * and it updates the view according to Server's response.
 */
public class ClientThread extends Thread {
    public String playerColor;
    protected final int port;
    protected final ClientView view;
    protected Socket socket;
    protected PrintWriter out;
    protected String gameVariant;

    /**
     * @param port port on which the server listens
     * @param view ClientView which must be connected with this thread's logic
     */
    public ClientThread(int port, ClientView view) {
        this.port = port;
        this.view = view;
    }

    /**
     * Opens socket and tries to connect with server.
     * Listens to server messages and performs adequate actions.
     * Requests view updates if needed.
     */
    @Override
    public void run() {
        try {
            socket = new Socket("localhost", port);
            out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            updateViewWaitingForOpponent();

            String serverMessage;
            String[] messageSplit;
            do {
                serverMessage = in.readLine();

                if (serverMessage.equals("ping")) { // if server sends ping, respond with pong
                    out.println("pong");
                }
            } while (!serverMessage.split(";")[0].equals("start"));

            messageSplit = serverMessage.split(";");
            gameVariant = messageSplit[1];
            playerColor = messageSplit[2];
            int boardSize = Integer.parseInt(messageSplit[3]);
            boolean isRepeated = messageSplit[5].equals("true");
            updateViewShowBoard(boardSize, isRepeated);
            updateViewFlipBoard();
            Board initBoard = getBoard(gameVariant, messageSplit[4]);
            updateViewUpdateBoard(initBoard, "White");
            while (true) {
                serverMessage = in.readLine();
                messageSplit = serverMessage.split(";");
                if (messageSplit[0].equals("update")) {
                    handleUpdate(messageSplit);
                } else if (messageSplit[0].equals("win")) {
                    String winner = messageSplit[1];
                    updateViewAnnounceWinner(winner);
                    break;
                } else if (messageSplit[0].equals("disconnect")) {
                    updateViewServerDisconnected();
                    break;
                }
            }
        } catch (IOException ioe) {
            updateViewConnectionFailed();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                socket.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * Handles server message with board update.
     *
     * @param messageSplit server message divided in Strings
     */
    protected void handleUpdate(String[] messageSplit) {
        String playerTurn = messageSplit[1];
        Board board = getBoard(gameVariant, messageSplit[2]);
        updateViewUpdateBoard(board, playerTurn);
    }

    /**
     * Updates view and sets scene to Waiting for opponent if view is not null.
     */
    protected void updateViewWaitingForOpponent() {
        if (view != null) {
            Platform.runLater(() -> view.waitingForOpponent());
        }
    }

    /**
     * Updates view and shows board if view is not null.
     *
     * @param boardSize  number of fields horizontally and vertically
     * @param isRepeated is this a replay of game
     */
    protected void updateViewShowBoard(int boardSize, boolean isRepeated) {
        if (view != null) {
            Platform.runLater(() -> view.showBoard(boardSize, isRepeated));
        }
    }

    /**
     * Updates view and flips board according to player's color if view is not null.
     */
    protected void updateViewFlipBoard() {
        if (view != null) {
            Platform.runLater(() -> view.flipBoard());
        }
    }

    /**
     * Updates view and displays current board state if view is not null.
     *
     * @param board      current board state
     * @param playerTurn color of player allowed to make next move
     */
    protected void updateViewUpdateBoard(Board board, String playerTurn) {
        if (view != null) {
            Platform.runLater(() -> view.updateBoard(board, playerTurn));
        }
    }

    /**
     * Updates view and sets scene to Announce Winner if view is not null.
     *
     * @param winner color of winning player
     */
    protected void updateViewAnnounceWinner(String winner) {
        if (view != null) {
            Platform.runLater(() -> view.announceWinner(winner));
        }
    }

    /**
     * Updates view and sets scene to Server Disconnected if view is not null.
     */
    protected void updateViewServerDisconnected() {
        if (view != null) {
            Platform.runLater(() -> view.serverDisconnected());
        }
    }

    /**
     * Updates view and sets scene to Connection Failed if view is not null.
     */
    protected void updateViewConnectionFailed() {
        if (view != null) {
            Platform.runLater(() -> view.connectionFailed());
        }
    }

    /**
     * Reconstructs game board basing on server message
     *
     * @param message String of fields separated with commas. Each character represents one field:
     *                w - white pawn
     *                b - black pawn
     *                W - white queen
     *                B - black queen
     *                0 - empty field
     * @return Board reconstructed from server's message
     */
    protected Board getBoard(String gameVariant, String message) {
        BoardFactory boardFactory = new BoardFactory();
        Board board = boardFactory.createBoard(gameVariant);
        if (board == null) {
            return null;
        }

        String[] messageSplit = message.split(",");
        char pawn;
        int n = 0;
        for (int i = 0; i < board.getSize(); i++) {
            for (int j = 0; j < board.getSize(); j++) {
                pawn = messageSplit[n++].charAt(0);

                if (pawn == '0') { // empty field
                    board.getFields()[i][j].setPawn(null);
                } else if (pawn == 'w') { // white pawn
                    board.getFields()[i][j].setPawn(new Pawn(rgb(255, 255, 255)));
                } else if (pawn == 'b') { // black pawn
                    board.getFields()[i][j].setPawn(new Pawn(rgb(0, 0, 0)));
                } else if (pawn == 'W') { // white queen
                    board.getFields()[i][j].setPawn(new Pawn(rgb(255, 255, 255), true));
                } else if (pawn == 'B') { // black queen
                    board.getFields()[i][j].setPawn(new Pawn(rgb(0, 0, 0), true));
                }
            }
        }
        return board;
    }

    /**
     * Sends request to perform a move to the server.
     *
     * @param x1 starting field x coordinate
     * @param y1 starting field y coordinate
     * @param x2 landing field x coordinate
     * @param y2 landing field y coordinate
     */
    public void makeMove(int x1, int y1, int x2, int y2) {
        out.println("move;" + x1 + ";" + y1 + ";" + x2 + ";" + y2);
    }

    /**
     * Safely closes the socket.
     */
    public void closeSocket() {
        try {
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}