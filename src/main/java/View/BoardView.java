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
    private final ArrayList<PawnView> pawnViews;
    final double size;
    Board board ;

    public BoardView(int boardSize, Client client) {
        super();
        board = new Board(boardSize);
        this.client = client;
        this.pawnViews = new ArrayList<>();
        size= 500.0/boardSize;

        Rectangle[][] rectangles = new Rectangle[boardSize][boardSize];
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                rectangles[i][j] = new Rectangle();
                rectangles[i][j].setX(i * size);
                rectangles[i][j].setY(j * size);
                rectangles[i][j].setWidth(size);
                rectangles[i][j].setHeight(size);
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
        this.board = board;
        getChildren().removeAll(pawnViews);
        pawnViews.clear();
        Field[][] fields = board.getFields();

        for (int i = 0; i < board.getSize(); i++) {
            for (int j = 0; j < board.getSize(); j++) {
                if (fields[i][j].isOccupied()) {
                    Image img;
                    PawnView pawnView = new PawnView((int)size * i + (int)Math.floor(size/2), (int)size * j + (int)Math.floor(size/2), 20, fields[i][j].getPawnColor(), board.getSize(), client.thread);
                    pawnViews.add(pawnView);

                    if(!fields[i][j].getPawn().isQueen() && fields[i][j].getPawnColor().equals(Color.rgb(255,255,255))){
                        img = new Image("file:whitePawn.jpg");
                        pawnView.setFill(new ImagePattern(img));
                    }
                    else if(!fields[i][j].getPawn().isQueen() && fields[i][j].getPawnColor().equals(Color.rgb(0,0,0))){
                        img = new Image("file:blackPawn.jpg");
                        pawnView.setFill(new ImagePattern(img));
                    }
                    else if( fields[i][j].getPawnColor().equals(Color.rgb(255,255,255))){
                        img = new Image("file:whiteQueenPawn.jpg");
                        pawnView.setFill(new ImagePattern(img));
                    }
                    else if(fields[i][j].getPawnColor().equals(Color.rgb(0,0,0))){
                        img = new Image("file:blackQueenPawn.jpg");
                        pawnView.setFill(new ImagePattern(img));
                    }
                    getChildren().add(pawnView);
                }
            }
        }
        activateClientMovement(color);
    }

    public Board getBoard(){
        return board;
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
