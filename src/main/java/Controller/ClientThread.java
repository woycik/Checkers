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

            Platform.runLater( () -> view.showBoard(boardSize));

            String serverMessage;
            String[] messageSplit;
            while(true) {
                serverMessage = in.readLine();
                messageSplit = serverMessage.split(";");

                if(messageSplit[0].equals("update")) {
                    Board board = getBoard(boardSize, messageSplit);
                    Platform.runLater( () -> view.updateBoard(board));
                }
                else if(messageSplit[0].equals("win")) {
                    String[] finalMessageSplit = messageSplit;
                    Platform.runLater( () -> view.announceWinner(finalMessageSplit[1]));
                    break;
                }
            }
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

    private Board getBoard(int boardSize, String[] messageSplit) {
        Board board = new Board(boardSize);
        char pawn;
        int n = 1;
        for(int i = 0; i < board.getSize(); i++) {
            for(int j = 0; j < board.getSize(); j++) {
                pawn = messageSplit[n++].charAt(0);

                if(pawn == '0') { // empty field
                    board.getFields()[i][j].setPawn(null);
                }
                else if(pawn == 'w') { // white pawn
                    board.getFields()[i][j].setPawn(new Pawn(Color.WHITE));
                }
                else if(pawn == 'b') { // black pawn
                    board.getFields()[i][j].setPawn(new Pawn(Color.BLACK));
                }
                else if(pawn == 'W') { // white queen
                    board.getFields()[i][j].setPawn(new Pawn(Color.WHITE, true));
                }
                else if(pawn == 'B') { // black queen
                    board.getFields()[i][j].setPawn(new Pawn(Color.BLACK, true));
                }
            }
        }
        return board;
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