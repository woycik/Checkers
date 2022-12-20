package Controller;

import View.ClientView;
import javafx.application.Application;
import javafx.stage.Stage;

public class Client extends Application {
    final int port = 4444;
    ClientView view;
    public ClientThread thread;


    @Override
    public void start(Stage stage) {
        view = new ClientView(this, stage);
        view.init();

        thread = new ClientThread(port, view);
        thread.start();
    }

    @Override
    public void stop() {
        thread.closeSocket();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
