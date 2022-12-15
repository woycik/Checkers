package Controller;

import View.ServerView;
import javafx.application.Application;
import javafx.stage.Stage;

import java.net.ServerSocket;
import java.net.Socket;

public class ServerLauncher extends Application {
    Stage stage;

    @Override
    public void start(Stage stage) {
        stage = new ServerView();

        try (ServerSocket serverSocket = new ServerSocket(4444)) {
            System.out.println("Server nasluchuje na porcie 4444");

            while(true) {
                Socket socket = serverSocket.accept();
                System.out.println("Polaczyl sie nowy klient.");
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
