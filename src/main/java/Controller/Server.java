package Controller;

import View.ServerView;
import javafx.application.Application;
import javafx.stage.Stage;

public class Server extends Application {
    ServerView view;
    final int port = 4444;

    @Override
    public void start(Stage stage) {
        view = new ServerView(this);
        view.init(stage);

        new ServerThread(port).start();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
