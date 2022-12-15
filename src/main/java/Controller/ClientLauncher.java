package Controller;

import View.ClientView;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientLauncher extends Application {
    Stage stage;
    Socket socket;
    PrintWriter out;
    BufferedReader in;

    @Override
    public void start(Stage stage) {
        stage = new ClientView();

        try {
            socket = new Socket("localhost", 4444);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void stop() {
        try {
            socket.close();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
