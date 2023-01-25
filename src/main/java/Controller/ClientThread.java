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
    final int port;
    public String playerColor;
    final ClientView view;
    Socket socket;
    PrintWriter out;
    BufferedReader in;

    /**
     *
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
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            Platform.runLater(() -> view.waitingForOpponent());

            String serverMessage;
            String[] messageSplit;
            do {
                serverMessage = in.readLine();

                if (serverMessage.equals("ping")) { // if server sends ping, respond with pong
                    out.println("pong");
                }
            } while (!serverMessage.split(";")[0].equals("start"));

            messageSplit = serverMessage.split(";");
            String gameVariant = messageSplit[1];
            playerColor = messageSplit[2];
            int boardSize = Integer.parseInt(messageSplit[3]);
            boolean isRepeated = messageSplit[5].equals("true");
            Platform.runLater(() -> view.showBoard(boardSize,isRepeated));
            Platform.runLater(() -> view.flipBoard());
            Board initBoard = getBoard(gameVariant, messageSplit[4]);
            Platform.runLater(() -> view.updateBoard(initBoard, "White"));
            while (true) {
                serverMessage = in.readLine();
                messageSplit = serverMessage.split(";");
                if (messageSplit[0].equals("update")) {
                    String playerTurn = messageSplit[1];
                    Board board = getBoard(gameVariant, messageSplit[2]);
                    Platform.runLater(() -> view.updateBoard(board, playerTurn));
                } else if (messageSplit[0].equals("win")) {
                    String winner = messageSplit[1];
                    Platform.runLater(() -> view.announceWinner(winner));
                    break;
                } else if (messageSplit[0].equals("disconnect")) {
                    Platform.runLater(() -> view.serverDisconnected());
                    break;
                }
            }
        } catch (IOException ioe) {
            Platform.runLater(() -> view.connectionFailed());
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
     * Reconstructs game board basing on server message
     * @param gameVariant type of checkers game
     * @param message String of fields separated with commas. Each character represents one field:
     *                w - white pawn
     *                b - black pawn
     *                W - white queen
     *                B - black queen
     *                0 - empty field
     * @return Board reconstructed from server's message
     */
    private Board getBoard(String gameVariant, String message) {
        BoardFactory boardFactory = new BoardFactory();
        Board board = boardFactory.createBoard(gameVariant);
        if(board == null) {
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