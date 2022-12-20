package Controller;

import Model.Board;
import Model.Field;
import Model.Pawn;
import Model.PlayerTurn;
import View.ServerView;
import javafx.application.Platform;
import javafx.scene.paint.Color;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

import static javafx.scene.paint.Color.rgb;

public class ServerThread extends Thread {
    private boolean stopRequest;
    int port;
    ServerView view;
    GameController gameController;
    ServerSocket serverSocket;
    Socket firstPlayerSocket;
    Socket secondPlayerSocket;

    public ServerThread(int port, ServerView view, GameController gameController) {
        this.port = port;
        this.view = view;
        this.gameController = gameController;
        this.firstPlayerSocket = null;
        this.secondPlayerSocket = null;
        this.stopRequest = false;
    }

    @Override
    public void run() {
        System.out.println("Server is listening on port " + port);

        try {
            serverSocket = new ServerSocket(port);
            do
            {
                if(!isConnected(firstPlayerSocket)) {
                    firstPlayerSocket = serverSocket.accept();
                }
                else if(!isConnected(secondPlayerSocket)) {
                    secondPlayerSocket = serverSocket.accept();
                }
                System.out.println("Client connected");
            } while(!isConnected(firstPlayerSocket) || !isConnected(secondPlayerSocket));

            System.out.println("Both players connected");
            Platform.runLater( () -> view.bothPlayersConnected());

            BufferedReader firstIn = new BufferedReader(new InputStreamReader(firstPlayerSocket.getInputStream()));
            PrintWriter firstOut = new PrintWriter(firstPlayerSocket.getOutputStream(), true);
            BufferedReader secondIn = new BufferedReader(new InputStreamReader(secondPlayerSocket.getInputStream()));
            PrintWriter secondOut = new PrintWriter(secondPlayerSocket.getOutputStream(), true);

            // sending message to start displaying board
            int boardSize = gameController.getBoardSize();
            String gameArguments = "start;" + boardSize + ";" + getSocketPrintableFormat(gameController.getBoard());
            firstOut.println(gameArguments);
            secondOut.println(gameArguments);
            System.out.println("Sent game start message to both players");

            String clientMessage = "";
            String boardString;
            int x1, x2, y1, y2;
            while(!stopRequest && !gameController.isWhiteWinner() && !gameController.isBlackWinner()) { // main game loop
                if(gameController.playerTurn == PlayerTurn.Black) {
                    clientMessage = firstIn.readLine();
                }
                else if(gameController.playerTurn == PlayerTurn.White) {
                    clientMessage = secondIn.readLine();
                }

                if(clientMessage != null && !clientMessage.isEmpty()) {
                    String[] messageSplit = clientMessage.split(";");
                    if(messageSplit[0].equals("move")) { // player wants to make a move
                        // move;x1;y1;x2;y2
                        x1 = Integer.parseInt(messageSplit[1]);
                        y1 = Integer.parseInt(messageSplit[2]);
                        x2 = Integer.parseInt(messageSplit[3]);
                        y2 = Integer.parseInt(messageSplit[4]);

                        if(gameController.isMoveLegal(x1, y1, x2, y2)) {
                            gameController.makeMove(x1, y1, x2, y2);
                            gameController.nextTurn();
                            boardString = getSocketPrintableFormat(gameController.getBoard());
                            firstOut.println("update;" + boardString);
                            secondOut.println("update;" + boardString);
                        }
                        else { // inform client about illegal move
                            if(gameController.playerTurn == PlayerTurn.Black) {
                                firstOut.println("illegalmove");
                            }
                            else if(gameController.playerTurn == PlayerTurn.White) {
                                secondOut.println("illegalmove");
                            }
                        }
                    }
                }
                clientMessage = "";
            }

            String winnerInfo;
            if(gameController.isWhiteWinner()) {
                winnerInfo = "win;White";
            }
            else {
                winnerInfo = "win;Black";
            }

            firstOut.println(winnerInfo);
            secondOut.println(winnerInfo);
        }
        catch(SocketException se) {
            System.out.println("Server socket closed");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getSocketPrintableFormat(Board board) {
        StringBuilder stringBuilder = new StringBuilder();

        Field[][] fields = board.getFields();
        Pawn pawn;
        char ch;
        for(int i = 0; i < board.getSize(); i++) {
            for(int j = 0; j < board.getSize(); j++) {
                pawn = fields[i][j].getPawn();
                if(pawn == null) {
                    ch = '0';
                }
                else {
                    if(pawn.getColor().equals(rgb(255, 255, 255))) {
                        ch = 'w';
                    }
                    else {
                        ch = 'b';
                    }

                    if(pawn.isQueen()) {
                        ch = Character.toUpperCase(ch);
                    }
                }
                stringBuilder.append(ch).append(",");
            }
        }
        return stringBuilder.toString();
    }

    private boolean isConnected(Socket socket) {
        try {
            if(socket == null || socket.isClosed() || !socket.isConnected()
                    || socket.isInputShutdown() || socket.isOutputShutdown()) {
                return false;
            }
            // sending ping and checking response
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            out.println("ping");
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            return in.readLine().equals("pong");
        }
        catch(Exception e) {
            return false;
        }
    }

    public void closeServerSocket() {
        try {
            if(serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close();
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void requestStop() {
        stopRequest = true;
    }
}
