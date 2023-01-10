package Controller;

import Model.Board;
import Model.Pawn;
import View.ClientView;
import javafx.application.Platform;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import static javafx.scene.paint.Color.rgb;

public class ClientThread extends Thread {
    int port;
    public String playerColor;
    ClientView view;
    Socket socket;
    PrintWriter out;
    BufferedReader in;

    public ClientThread(int port, ClientView view) {
        this.port = port;
        this.view = view;
    }

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
            playerColor = messageSplit[1];
            int boardSize = Integer.parseInt(messageSplit[2]);

            Platform.runLater(() -> view.showBoard(boardSize));
            Platform.runLater(() -> view.flipBoard());
            Board initBoard = getBoard(boardSize, messageSplit[3]);
            Platform.runLater(() -> view.updateBoard(initBoard, "White"));

            while (true) {
                serverMessage = in.readLine();
                messageSplit = serverMessage.split(";");

                if (messageSplit[0].equals("update")) {
                    String playerTurn = messageSplit[1];
                    Board board = getBoard(boardSize, messageSplit[2]);
                    Platform.runLater(() -> view.updateBoard(board, playerTurn));
                } else if (messageSplit[0].equals("win")) {
                    String winner = messageSplit[1];
                    Platform.runLater(() -> view.announceWinner(winner));
                    break;
                } else if(messageSplit[0].equals("disconnect")) {
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

    private Board getBoard(int boardSize, String message) {
        Board board = new Board(boardSize);
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

    public void makeMove(int x1, int y1, int x2, int y2) {
        out.println("move;" + x1 + ";" + y1 + ";" + x2 + ";" + y2);
    }

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