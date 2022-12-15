package Controller;

import View.ServerView;

import java.net.ServerSocket;
import java.net.Socket;

public class ServerThread extends Thread {
    int port;

    public ServerThread(int port) {
        this.port = port;
    }

    @Override
    public void run() {
        System.out.println("Server nasluchuje na porcie " + port);

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            while(true) {
                Socket socket = serverSocket.accept();
                System.out.println("Polaczyl sie nowy klient.");
                // TODO: create client thread and wait until 2 players connect
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
