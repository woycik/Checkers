package Controller;

import View.ServerView;
import javafx.application.Application;
import javafx.stage.Stage;

public class Server extends Application {
    ServerView view;
    final int port = 4444;
    ServerThread thread;

    @Override
    public void start(Stage stage) {
        view = new ServerView(this, stage);
        view.init();
    }

    public void prepareGame(String type) {
        GameController gameController;
        if(type.equals("Polish")) {
            gameController = new PolishCheckersController();
        }
        else if(type.equals("Russian")) {
            gameController = new RussianCheckersController();
        }
        else {
            gameController = new EnglishCheckersController();
        }

        thread = new ServerThread(port, view, gameController);
        thread.start();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
