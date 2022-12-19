package Controller;

import Model.Board;
import Model.Field;
import Model.Pawn;
import View.ClientView;
import javafx.application.Platform;
import javafx.scene.paint.Color;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientThread extends Thread {
    int port;
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
            Platform.runLater( () -> view.waitingForOpponent());

            String line;
            do {
                line = in.readLine();

                if(line.equals("ping")) { // if server sends ping, respond with pong
                    out.println("pong");
                }
            } while(!line.split(";")[0].equals("start"));

            // arguments to displaying board
            String[] gameArgs = line.split(";");
            int boardSize = Integer.parseInt(gameArgs[1]);
            int pawnRows = Integer.parseInt(gameArgs[2]);

            System.out.println("Board size: " + boardSize);
            System.out.println("Number of rows with pawns: " + pawnRows);

            Board board = new Board(boardSize, pawnRows);
            // mock board creation
            Field field = new Field(0, 0);
            field.setPawn(new Pawn(Color.BLACK));
            board.getFields()[0][0] = field;
            // end of mock board creation

            // TODO: fill the board with an actual state

            Platform.runLater( () -> view.showBoard(boardSize));

            // TODO: game loop with Platform.runLater( () -> view.updateBoard(board));
            Platform.runLater( () -> view.updateBoard(board));
        }
        catch(IOException ioe) {
            Platform.runLater( () -> view.connectionFailed());
        }
        catch(Exception e) {
            e.printStackTrace();
            try {
                socket.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    private String executeOnServer(String command) {
        try {
            out.println(command); // send command to server
            return in.readLine(); // receive response
        } catch (Exception e) {
            e.printStackTrace();
            return "Error connecting with server";
        }
    }

    public void closeSocket() {
        try {
            if(socket != null && !socket.isClosed()) {
                socket.close();
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
}