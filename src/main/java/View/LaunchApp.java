package View;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.scene.control.Label;
import javafx.scene.control.Button;


public class LaunchApp extends Application {
    @Override
    public void start(Stage stage) {
        final BorderPane borderPane=new BorderPane();

        //select mode
        Button german = new Button("German mode");
        Button russian = new Button("Russian mode");
        Button poland = new Button("Polish mode");

        //adding labels and buttons into gridpane
        Label text=new Label("Choose mode: ");
        GridPane menu = new GridPane();
        menu.add(text,1,0);
        menu.add(german,0,1);
        menu.add(russian,1,1);
        menu.add(poland,2,1);
        menu.setHgap(10);
        menu.setVgap(20);

        //setting styles
        poland.setStyle("-fx-background-color: lightblue; -fx-text-fill: white; -fx-padding: 15px;");
        russian.setStyle("-fx-background-color: lightblue; -fx-text-fill: white; -fx-padding: 15px;");
        german.setStyle("-fx-background-color: lightblue; -fx-text-fill: white; -fx-padding: 15px;");



        menu.setAlignment(Pos.CENTER);
        borderPane.setCenter(menu);
        final Scene scene = new Scene(borderPane, 500, 500);


        german.setOnAction(new EventHandler<ActionEvent>() {
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
        stage.setTitle("Checkers");
        stage.setScene(scene);
        stage.show();


    }

    public static void main(String[] args) {
        launch(args);
    }
}