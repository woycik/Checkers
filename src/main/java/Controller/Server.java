package Controller;

import Model.CheckersControllerFactory;
import View.ServerView;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Server side checkers application.
 */
public class Server extends Application {
    ServerView view;
    final int port = 4444;
    ServerThread thread;

    /**
     * Creates and initializes view.
     *
     * @param stage
     */
    @Override
    public void start(Stage stage) {
        view = new ServerView(this, stage);
        view.init();
    }

    /**
     * Creates proper GameController and starts ServerThread.
     *
     * @param type checkers variant
     */
    public void prepareGame(String type, boolean isReapeted) {
        System.out.println("Preparing " + type + " checkers...");
        CheckersControllerFactory checkersControllerFactory = new CheckersControllerFactory();
        GameController gameController = checkersControllerFactory.createGameController(type);
        if (gameController == null) {
            return;
        }
        if (!isReapeted) {
            thread = new ServerThread(port, view, gameController);
        } else {
            thread = new HibernateServerThread(port, view, gameController);
        }
        thread.start();
    }

    /**
     * Safely closes server socket and stops server thread.
     */
    @Override
    public void stop() {
        thread.closeServerSocket();
        thread.requestStop();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
