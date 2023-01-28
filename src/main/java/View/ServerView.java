package View;

import Controller.Server;
import Model.HibernateGame;
import Model.HibernateUtil;
import javafx.application.Platform;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;


/**
 * Displays game server controls and displays information about connection.
 */
public class ServerView {
    final Server server;
    final Stage stage;
    SessionFactory sf;
    Query q;

    public ServerView(Server server, Stage stage) {
        this.server = server;
        this.stage = stage;
    }

    /**
     * Initializes scene and displays main menu.
     */
    public void init() {
        final BorderPane borderPane = new BorderPane();

        Button polishButton = new Button("Polish mode");
        Button russianButton = new Button("Russian mode");
        Button englishButton = new Button("English mode");
        Button latestGameButton = new Button("Latest game");


        //adding labels and buttons into GridPane
        Label chooseModeLabel = new Label("Choose mode:");;
        GridPane menu = new GridPane();
        menu.add(chooseModeLabel, 1, 0, 2, 1);
        GridPane.setHalignment(chooseModeLabel, HPos.CENTER);
        menu.add(polishButton, 0, 1);
        menu.add(russianButton, 1, 1);
        menu.add(englishButton, 2, 1);
        menu.add(latestGameButton, 3, 1);
        menu.setHgap(10);
        menu.setVgap(20);
        menu.setAlignment(Pos.CENTER);
        borderPane.setCenter(menu);
        final Scene scene = new Scene(borderPane, 500, 500);

        //setting button styles
        polishButton.setStyle("-fx-background-color: lightblue; -fx-text-fill: white; -fx-padding: 15px;");
        russianButton.setStyle("-fx-background-color: lightblue; -fx-text-fill: white; -fx-padding: 15px;");
        englishButton.setStyle("-fx-background-color: lightblue; -fx-text-fill: white; -fx-padding: 15px;");
        latestGameButton.setStyle("-fx-background-color: lightblue; -fx-text-fill: white; -fx-padding: 15px;");


        // setting button on click actions to prepare and start adequate game
        polishButton.setOnAction(event -> {
            choosePlayers(scene, "Polish");
        });
        russianButton.setOnAction(event -> {
            choosePlayers(scene, "Russian");
        });
        englishButton.setOnAction(event -> {
            choosePlayers(scene, "English");
        });
        latestGameButton.setOnAction(event -> {
            sf = HibernateUtil.getSessionFactory();
            if (sf == null) {
                System.out.println("Error: Initialize database from the file db.sql");
                return;
            }
            Session session = sf.openSession();
            Transaction tx = session.beginTransaction();
            q = session.createQuery("select gameVariant from Model.HibernateGame WHERE id IN (select max(id) from Model.HibernateGame)");
            List<String> list = q.list();
            tx.commit();
            viewGameAgain(scene, list.get(0));
        });

        stage.setOnCloseRequest(e -> Platform.exit());
        stage.setTitle("Checkers server");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Allows to choose Player vs Player or Player vs Computer game variant.
     *
     * @param scene main scene
     * @param type  checkers variant
     */
    public void choosePlayers(Scene scene, String type) {
        final BorderPane borderPane = new BorderPane();
        final VBox vbox = new VBox(20);
        Label serverStatusLabel = new Label("Selected " + type + " Checkers. Select number of players.");
        serverStatusLabel.setId("serverStatusLabel");

        Button pvpButton = new Button("Player vs Player");
        pvpButton.setStyle("-fx-background-color: lightblue; -fx-text-fill: white; -fx-padding: 15px;");
        pvpButton.setOnAction(event -> {
            startGame(scene, type, false);
        });

        Button pvcButton = new Button("Player vs Computer");
        pvcButton.setStyle("-fx-background-color: lightblue; -fx-text-fill: white; -fx-padding: 15px;");
        pvcButton.setOnAction(event -> {
            startGame(scene, type, true);
        });

        vbox.setAlignment(Pos.CENTER);
        vbox.getChildren().addAll(pvpButton, pvcButton);
        borderPane.setCenter(vbox);
        scene.setRoot(borderPane);
    }

    /**
     * Prepares and starts checkers game of proper variant.
     *
     * @param scene main scene
     * @param type  checkers variant
     * @param computerPlayer player vs computer game if true and player vs player game otherwise
     */
    public void startGame(Scene scene, String type, boolean computerPlayer) {
        final BorderPane borderPane = new BorderPane();
        final VBox vbox = new VBox(20);
        Label serverStatusLabel = new Label("Selected " + type + " Checkers. Waiting for players to connect.");
        Button stopButton = new Button("Stop");
        stopButton.setStyle("-fx-background-color: darkred; -fx-text-fill: white; -fx-padding: 15px;");
        stopButton.setOnAction(event -> {
            server.stop();
            init();
        });
        serverStatusLabel.setId("serverStatusLabel");
        vbox.setAlignment(Pos.CENTER);
        vbox.getChildren().add(serverStatusLabel);
        vbox.getChildren().add(stopButton);
        borderPane.setCenter(vbox);
        scene.setRoot(borderPane);
        server.prepareGame(type, false, computerPlayer);
    }

    /**
     * Prepares and starts checkers game of proper variant.
     *
     * @param scene main scene
     * @param type  checkers variant
     */
    public void viewGameAgain(Scene scene, String type) {
        final BorderPane borderPane = new BorderPane();
        final VBox vbox = new VBox(20);
        Label serverStatusLabel = new Label("You have chosen to watch previous game.");
        Button stopButton = new Button("Stop");
        stopButton.setStyle("-fx-background-color: darkred; -fx-text-fill: white; -fx-padding: 15px;");
        stopButton.setOnAction(event -> {
            server.stop();
            init();
        });
        serverStatusLabel.setId("serverStatusLabel");
        vbox.setAlignment(Pos.CENTER);
        vbox.getChildren().add(serverStatusLabel);
        vbox.getChildren().add(stopButton);
        borderPane.setCenter(vbox);
        scene.setRoot(borderPane);
        server.prepareGame(type, true, false);
    }

    /**
     * Displays information about game and announces winner.
     *
     * @param winner color of winning player
     */
    public void announceWinner(String winner) {
        Scene scene = stage.getScene();
        Label serverStatusLabel = (Label) scene.lookup("#serverStatusLabel");
        if (serverStatusLabel == null) {
            serverStatusLabel = new Label();
            serverStatusLabel.setId("serverStatusLabel");
        }
        serverStatusLabel.setText(winner + " wins!");
    }

    /**
     * Displays information about both players connecting and preparing game start.
     */
    public void bothPlayersConnected() {
        Scene scene = stage.getScene();
        Label serverStatusLabel = (Label) scene.lookup("#serverStatusLabel");
        if (serverStatusLabel == null) {
            serverStatusLabel = new Label();
            serverStatusLabel.setId("serverStatusLabel");
        }
        serverStatusLabel.setText("Both players connected. Starting game.");
    }
}
