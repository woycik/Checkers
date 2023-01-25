package Controller;

import Model.*;
import View.ServerView;
import javafx.application.Platform;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;


import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import static javafx.scene.paint.Color.rgb;

public class ServerThread extends Thread {
    protected boolean stopRequest;
    protected final int port;
    protected final ServerView view;
    protected final GameController gameController;
    protected ServerSocket serverSocket;
    protected Socket firstPlayerSocket;
    protected Socket secondPlayerSocket;
    protected BufferedReader firstIn;
    protected PrintWriter firstOut;
    protected BufferedReader secondIn;
    protected PrintWriter secondOut;


    SessionFactory sf;
    Session factory;
    int moveNumber=0;
    Set<HibernateMove> moves = new HashSet<HibernateMove>();
    HibernateGame game;


    public ServerThread(int port, ServerView view, GameController gameController) {
        this.port = port;
        this.view = view;
        this.gameController = gameController;
        this.firstPlayerSocket = null;
        this.secondPlayerSocket = null;
        this.prepareHibernate();
    }

    public  void  prepareHibernate(){
        sf = HibernateUtil.getSessionFactory();
        if (sf == null) {
            System.out.println("Error: Initialize database from the file db.sql");
            return;
        }
        factory = sf.openSession();
        factory.beginTransaction();
        game = new HibernateGame(gameController.getGameVariant());
    }


    /**
     * Opens ServerSocket, waits for both players to connect, prepares and starts a checkers game.
     * Listens to players' input streams for move requests, verifies if they are legal, performs moves on board
     * and sends update messages to both of them.
     */
    @Override
    public void run() {
        System.out.println("Server is listening on port " + port);
        try {
            serverSocket = new ServerSocket(port);
            do {
                if (!isConnected(firstPlayerSocket)) {
                    firstPlayerSocket = serverSocket.accept();
                } else if (!isConnected(secondPlayerSocket)) {
                    secondPlayerSocket = serverSocket.accept();
                }
                System.out.println("Client connected");
            } while (!isConnected(firstPlayerSocket) || !isConnected(secondPlayerSocket));

            System.out.println("Both players connected");
            Platform.runLater(() -> view.bothPlayersConnected());

            firstIn = new BufferedReader(new InputStreamReader(firstPlayerSocket.getInputStream()));
            firstOut = new PrintWriter(firstPlayerSocket.getOutputStream(), true);
            secondIn = new BufferedReader(new InputStreamReader(secondPlayerSocket.getInputStream()));
            secondOut = new PrintWriter(secondPlayerSocket.getOutputStream(), true);

            // sending message to start displaying board
            String gameVariant = gameController.getGameVariant();
            int boardSize = gameController.getBoardSize();
            String boardString = getSocketPrintableFormat(gameController.getBoard());
            sendGameStartMessages(gameVariant, boardSize, boardString);

            String clientMessage = "";
            while (!stopRequest && !gameController.isWhiteWinner() && !gameController.isBlackWinner()) { // main game loop
                if (gameController.playerTurn == PlayerTurn.White) {
                    clientMessage = firstIn.readLine();
                    if (clientMessage == null) {
                        continue;
                    }
                    System.out.println("Received message from White: " + clientMessage);
                } else if (gameController.playerTurn == PlayerTurn.Black) {
                    clientMessage = secondIn.readLine();
                    if (clientMessage == null) {
                        continue;
                    }
                    System.out.println("Received message from Black: " + clientMessage);
                }

                if (clientMessage != null && !clientMessage.isEmpty()) {
                    String[] messageSplit = clientMessage.split(";");
                    if (messageSplit[0].equals("move")) { // player wants to make a move
                        handleMoveRequest(clientMessage);
                        addMoves(clientMessage);
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

    /**
     * Sends game starting message to both players.
     * @param gameVariant Checkers variant
     * @param boardSize Number of fields horizontally and vertically on Board
     * @param boardString Initial state of Board
     */
    private void sendGameStartMessages(String gameVariant, int boardSize, String boardString) {
        // start;game.hbm.xml;color;boardSize;boardString
        firstOut.println("start;" + gameVariant + ";White;" + boardSize + ";" + boardString + ";false");
        secondOut.println("start;" + gameVariant + ";Black;" + boardSize + ";" + boardString +";flase");
        System.out.println("Sent game start message to both players");
    }

    /**
     * Handles player's move request. Parses message into starting and landing fields' coordinates,
     * verifies whether move is legal, performs move on board and sends update messages to both players.
     * @param message move request in format move;x1;y1;x2;y2
     */
    private void handleMoveRequest(String message) {
        String[] messageSplit = message.split(";");
        // move;x1;y1;x2;y2
        int x1 = Integer.parseInt(messageSplit[1]);
        int y1 = Integer.parseInt(messageSplit[2]);
        int x2 = Integer.parseInt(messageSplit[3]);
        int y2 = Integer.parseInt(messageSplit[4]);

        if (gameController.move(x1, y1, x2, y2)) {
            System.out.println("Changing player turn and sending update to both players.");
            firstOut.println(getUpdateMessage());
            secondOut.println(getUpdateMessage());
            System.out.println(gameController.playerTurn.toString() + "'s move");
        } else { // inform client about illegal move
            if (gameController.playerTurn == PlayerTurn.White) {
                firstOut.println(getUpdateMessage());
            } else if (gameController.playerTurn == PlayerTurn.Black) {
                secondOut.println(getUpdateMessage());
            }
            System.out.println("Illegal move. Player notified.");
        }
    }

    /**
     * Determines the winner and sends appropriate message to both players.
     */
    private void sendWinnerMessage() {
        String winner;
        if (gameController.isWhiteWinner()) {
            winner = "White";
        } else {
            winner = "Black";
        }
        String winnerInfo = "win;" + winner;

        firstOut.println(winnerInfo);
        secondOut.println(winnerInfo);
        Platform.runLater(() -> view.announceWinner(winner));
    }

    /**
     * Converts Board object into String message to send to Client.
     * @param board Checkers board object to convert
     * @return String representation of current board state. Each character represents one field:
     *                w - white pawn
     *                b - black pawn
     *                W - white queen
     *                B - black queen
     *                0 - empty field
     */
    protected String getSocketPrintableFormat(Board board) {
        StringBuilder stringBuilder = new StringBuilder();
        Field[][] fields = board.getFields();
        Pawn pawn;
        char ch;
        for (int i = 0; i < board.getSize(); i++) {
            for (int j = 0; j < board.getSize(); j++) {
                pawn = fields[i][j].getPawn();
                if (pawn == null) {
                    ch = '0';
                } else {
                    if (pawn.getColor().equals(rgb(255, 255, 255))) {
                        ch = 'w';
                    } else {
                        ch = 'b';
                    }

                    if (pawn.isQueen()) {
                        ch = Character.toUpperCase(ch);
                    }
                }
                stringBuilder.append(ch).append(",");
            }
        }
        return stringBuilder.toString();
    }

    /**
     * Prepares a update message to inform players about current game board state.
     * @return String in format of update;playerColor;board
     */
    protected String getUpdateMessage() {
        String playerColor;
        if (gameController.playerTurn == PlayerTurn.White) {
            playerColor = "White";
        } else if (gameController.playerTurn == PlayerTurn.Black) {
            playerColor = "Black";
        } else {
            playerColor = "None";
        }

        return "update;" + playerColor + ";" + getSocketPrintableFormat(gameController.board);
    }

    /**
     * Determines whether client socket is still connected with server. Pings client and awaits response.
     * @param socket Client socket
     * @return true if socket is connected and false otherwise
     */
    protected boolean isConnected(Socket socket) {
        try {
            if (socket == null || socket.isClosed() || !socket.isConnected()
                    || socket.isInputShutdown() || socket.isOutputShutdown()) {
                return false;
            }
            // sending ping and checking response
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            out.println("ping");
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            return in.readLine().equals("pong");
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Safely closes ServerSocket and informs clients beforehand.
     */
    public void closeServerSocket() {
        try {
            if (firstOut != null) {
                firstOut.println("disconnect");
            }

            if (secondOut != null) {
                secondOut.println("disconnect");
            }

            if (serverSocket != null && !serverSocket.isClosed()) {
                saveInDataBase();
                serverSocket.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Sets stopRequest flag to true. Safely demands for thread stop.
     */
    public void requestStop() {
        stopRequest = true;
    }

    public void addMoves(String message){
        moveNumber++;
        String[] messageSplit = message.split(";");
        // move;x1;y1;x2;y2
        int x1 = Integer.parseInt(messageSplit[1]);
        int y1 = Integer.parseInt(messageSplit[2]);
        int x2 = Integer.parseInt(messageSplit[3]);
        int y2 = Integer.parseInt(messageSplit[4]);
        HibernateMove move = new HibernateMove(moveNumber,x1, y1, x2, y2, gameController.playerTurn.toString(),game);
        moves.add(move);
    }

    public void saveInDataBase(){
        game.setMoves( moves);
        factory.persist(game);
        factory.getTransaction().commit();
        HibernateUtil.shutdown();
    }
}
