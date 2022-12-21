package View;

import Controller.Client;
import Model.Board;
import Model.Field;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;
import javafx.scene.image.Image;


import java.util.ArrayList;


public class BoardView extends Pane {
    private final Client client;
    private ArrayList<PawnView> pawnViews;

    public BoardView(int boardSize, Client client) {
        super();
        this.client = client;
        this.pawnViews = new ArrayList<>();

        Rectangle[][] rectangles = new Rectangle[boardSize][boardSize];
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                rectangles[i][j] = new Rectangle();
                rectangles[i][j].setX(i * 50);
                rectangles[i][j].setY(j * 50);
                rectangles[i][j].setWidth(50);
                rectangles[i][j].setHeight(50);
            }
        }

        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                if ((i + j) % 2 == 0) {
                    rectangles[i][j].setFill(Color.BROWN);
                } else {
                    rectangles[i][j].setFill(Color.BISQUE);
                }
                getChildren().add(rectangles[i][j]);
            }
        }
    }

    public void update(Board board, String color) {
        getChildren().removeAll(pawnViews);
        pawnViews.clear();
        Field[][] fields = board.getFields();
        for (int i = 0; i < board.getSize(); i++) {
            for (int j = 0; j < board.getSize(); j++) {

                if (fields[i][j].isOccupied()) {
                    Image img;
                    PawnView pawnView = new PawnView(50 * i + 25, 50 * j + 25, 20, fields[i][j].getColor(), board.getSize(), client.thread);
                    pawnViews.add(pawnView);

                    if(!fields[i][j].getPawn().isQueen() && fields[i][j].getColor().equals(Color.rgb(255,255,255))){
                        img = new Image("file:whitePawn.jpg");
                        pawnView.setFill(new ImagePattern(img));
                    }
                    else if(!fields[i][j].getPawn().isQueen() && fields[i][j].getColor().equals(Color.rgb(0,0,0))){
                        img = new Image("file:blackPawn.jpg");
                        pawnView.setFill(new ImagePattern(img));
                    }
                    else if( fields[i][j].getColor().equals(Color.rgb(255,255,255))){
                        img = new Image("file:whiteQueenPawn.jpg");
                        pawnView.setFill(new ImagePattern(img));
                    }
                    else if(fields[i][j].getColor().equals(Color.rgb(0,0,0))){
                        img = new Image("file:blackQueenPawn.jg");
                        pawnView.setFill(new ImagePattern(img));
                    }
                    this.getChildren().add(pawnView);
                }
            }
        }
        activateClientMovement(color);
    }

    public void activateClientMovement(String color) {
        if (!color.equals(client.thread.playerColor)) {
            return;
        }

        for (PawnView pawn : pawnViews) {
            if (color.equals("Black")) {
                if (pawn.getColor().equals(Color.rgb(0, 0, 0))) {
                    pawn.setControlsEnabled(true);
                }
            } else if (color.equals("White")) {
                if (pawn.getColor().equals(Color.rgb(255, 255, 255))) {
                    pawn.setControlsEnabled(true);
                }
            }
        }
    }

    public void flipScene(){
        if(client.thread.playerColor.equals("Black")){
            Rotate rotate = new Rotate(180,250,250);
            this.getTransforms().addAll(rotate);
        }
    }
}
