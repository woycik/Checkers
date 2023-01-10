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
        switch (type) {
            case "Polish":
                System.out.println("Preparing Polish checkers");
                gameController = new PolishCheckersController();
                break;
            case "Russian":
                System.out.println("Preparing Russian checkers");
                gameController = new RussianCheckersController();
                break;
            case "English":
                System.out.println("Preparing English checkers");
                gameController = new EnglishCheckersController();
                break;
            default:
                System.out.println("Invalid game type.");
                return;
        }

        thread = new ServerThread(port, view, gameController);
        thread.start();
    }

    @Override
    public void stop() {
        thread.closeServerSocket();
        thread.requestStop();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
