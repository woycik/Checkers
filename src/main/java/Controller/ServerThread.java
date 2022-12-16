package Controller;

import View.ServerView;
import javafx.application.Platform;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerThread extends Thread {
    int port;
    ServerView view;
    GameController gameController;
    Socket firstPlayerSocket;
    Socket secondPlayerSocket;

    public ServerThread(int port, ServerView view, GameController gameController) {
        this.port = port;
        this.view = view;
        this.gameController = gameController;
        this.firstPlayerSocket = null;
        this.secondPlayerSocket = null;
    }

    @Override
    public void run() {
        System.out.println("Server is listening on port " + port);

        try (ServerSocket serverSocket = new ServerSocket(port)) {
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
            int boardSize = 8; // TODO: get from gameController
            int pawnRows = 2; // TODO: get from gameController
            String gameArguments = "start " + boardSize + " " + pawnRows;

            firstOut.println(gameArguments);
            secondOut.println(gameArguments);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
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
}
