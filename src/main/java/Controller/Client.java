package Controller;

import View.ClientView;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client extends Application {
    ClientView view;
    Socket socket;
    PrintWriter out;
    BufferedReader in;
    final int port = 4444;

    @Override
    public void start(Stage stage) {
        view = new ClientView(this);
        view.init(stage);

        try {
            socket = new Socket("localhost", port);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        }
        catch(IOException ioe) {
            // TODO: update view to inform about failed connection
            System.out.println("Nie mozna nawiazac polaczenia z serverem");
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void stop() {
        try {
            if(socket != null) {
                socket.close();
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
