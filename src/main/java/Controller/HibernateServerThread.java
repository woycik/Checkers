package Controller;

import Model.HibernateUtil;
import Model.PlayerTurn;
import View.ServerView;
import javafx.application.Platform;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.SocketException;
import java.util.List;

public class HibernateServerThread extends  ServerThread{
    public HibernateServerThread(int port, ServerView view, GameController gameController) {
        super(port,view,gameController);
    }

    @Override
    public void run() {
        System.out.println("Server is listening on port " + port);
        try {
            serverSocket = new ServerSocket(port);
            do {
                if (!isConnected(firstPlayerSocket)) {
                    firstPlayerSocket = serverSocket.accept();
                }
                System.out.println("Client connected");
            } while (!isConnected(firstPlayerSocket));

            System.out.println("Player connected");
            Platform.runLater(() -> view.bothPlayersConnected());

            firstIn = new BufferedReader(new InputStreamReader(firstPlayerSocket.getInputStream()));
            firstOut = new PrintWriter(firstPlayerSocket.getOutputStream(), true);


            // sending message to start displaying board
            String gameVariant = gameController.getGameVariant();
            int boardSize = gameController.getBoardSize();
            String boardString = getSocketPrintableFormat(gameController.getBoard());
            sendGameStartMessages(gameVariant, boardSize, boardString);

            String clientMessage = "";
            while (!stopRequest && !gameController.isWhiteWinner() && !gameController.isBlackWinner()) { // main game loop
                if (gameController.playerTurn == PlayerTurn.White || gameController.playerTurn==PlayerTurn.Black)
                {
                    clientMessage = firstIn.readLine();
                    if (clientMessage == null) {
                        continue;
                    }
                }

                System.out.println("Received message : " + clientMessage);

                if (clientMessage != null && !clientMessage.isEmpty()) {
                    System.out.println("Jestem tutaj");
                    String[] messageSplit = clientMessage.split(";");
                    if (messageSplit[0].equals("move")) { // player wants to make a move
                        handleMoveRequest(clientMessage);
                        System.out.println("a ja tutaj");
                    }
                }
                clientMessage = "";
            }
            sendWinnerMessage();
        } catch (SocketException se) {
            System.out.println("Server socket closed");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendGameStartMessages(String gameVariant, int boardSize, String boardString) {
        // start;game;color;boardSize;boardString
        firstOut.println("start;" + gameVariant + ";White;" + boardSize + ";" + boardString + ";true");
        System.out.println("Sent game start message to both players");
    }

    private void handleMoveRequest(String message) {
        System.out.println("Jestem w srodku funkcji");
        String[] messageSplit = message.split(";");
        // move;x1;y1;x2;y2
        int x1 = Integer.parseInt(messageSplit[1]);
        int y1 = Integer.parseInt(messageSplit[2]);
        int x2 = Integer.parseInt(messageSplit[3]);
        int y2 = Integer.parseInt(messageSplit[4]);

        if (gameController.move(x1, y1, x2, y2)) {
            System.out.println("Changing player turn and sending update to both players.");
            firstOut.println(getUpdateMessage());
            System.out.println(gameController.playerTurn.toString() + "'s move");
        } else { // inform client about illegal move
            firstOut.println(getUpdateMessage());
            System.out.println("Illegal move. Player notified.");
        }
    }

    private void sendWinnerMessage() {
        String winner;
        if (gameController.isWhiteWinner()) {
            winner = "White";
        } else {
            winner = "Black";
        }
        String winnerInfo = "win;" + winner;
        firstOut.println(winnerInfo);
        Platform.runLater(() -> view.announceWinner(winner));
    }

    public void closeServerSocket() {
        try {
            if (firstOut != null) {
                firstOut.println("disconnect");
            }
            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
