package Bot;

import javafx.application.Application;
import javafx.stage.Stage;

public class Bot extends Application {
    private final int port = 4444;
    BotThread bot;

    @Override
    public void start(Stage stage) throws Exception {
        bot = new BotThread(port);
        bot.start();
    }

    @Override
    public void stop() {
        bot.closeSocket();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
