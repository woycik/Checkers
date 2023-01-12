package Controller;

import View.ClientView;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Client side checkers application.
 */
public class Client extends Application {
    private final int port = 4444;
    private ClientView view;
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
