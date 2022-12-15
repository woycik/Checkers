package View;

import Controller.Server;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class ServerView {
    Server server;

    public ServerView(Server server) {
        this.server = server;
    }

    public void init(Stage stage) {
        final BorderPane borderPane=new BorderPane();

        //select mode
        Button polishButton = new Button("Polish mode");
        Button russianButton = new Button("Russian mode");
        Button englishButton = new Button("English mode");

        //adding labels and buttons into gridpane
        Label text=new Label("Choose mode: ");
        GridPane menu = new GridPane();
        menu.add(text,1,0);
        menu.add(polishButton,0,1);
        menu.add(russianButton,1,1);
        menu.add(englishButton,2,1);
        menu.setHgap(10);
        menu.setVgap(20);

        //setting styles
        polishButton.setStyle("-fx-background-color: lightblue; -fx-text-fill: white; -fx-padding: 15px;");
        russianButton.setStyle("-fx-background-color: lightblue; -fx-text-fill: white; -fx-padding: 15px;");
        englishButton.setStyle("-fx-background-color: lightblue; -fx-text-fill: white; -fx-padding: 15px;");

        menu.setAlignment(Pos.CENTER);
        borderPane.setCenter(menu);
        final Scene scene = new Scene(borderPane, 500, 500);

        polishButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event){
                BoardGui boardGui =new BoardGui();
                scene.setRoot(boardGui);
            }
        });

        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent e) {
                System.exit(0);
            }
        });
        stage.setTitle("Checkers server");
        stage.setScene(scene);
        stage.show();
    }
}
