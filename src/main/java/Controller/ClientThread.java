package Controller;

import View.ClientView;
import javafx.application.Platform;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientThread extends Thread {
    int port;
    ClientView view;
    public Socket socket;
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
            } while(!line.split(" ")[0].equals("start"));

            // arguments to displaying board
            String[] gameArgs = line.split(";");
            int boardSize = Integer.parseInt(gameArgs[0]);
            int rows = Integer.parseInt(gameArgs[1]);

            Platform.runLater( () -> view.showBoard());
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
}