package View;

import Controller.ClientThread;
import Model.Board;
import Model.Field;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;


public class PawnEventHandler implements EventHandler<MouseEvent> {
    private final PawnView pawnView;
    private final ClientThread clientThread;
    private double x;
    private double y;
    private final int startingFieldX;
    private final int startingFieldY;
    public boolean controlsEnabled;
    ArrayList<Rectangle> captures;
    Board board;


    public PawnEventHandler(PawnView pawnView, ClientThread clientThread) {
        super();
        this.pawnView = pawnView;
        this.startingFieldX = pawnView.getFieldX();
        this.startingFieldY = pawnView.getFieldY();
        this.clientThread = clientThread;
        this.controlsEnabled = false;
        this.captures= new ArrayList<>();
    }

    private boolean checkIfCaptureIsNotPossible(Board board){
        for(int x=0;x< board.getSize();x++){
            for(int y =0;y< board.getSize();y++){
                if(!board.getFields()[x][y].getPossibleCaptures().isEmpty()){
                    return false;
                }
            }
        }
        return true;
    }

    private void createRectangle(Field f,double size){
        Rectangle rectHighlight = new Rectangle();
        rectHighlight.setX(f.getX() * size);
        rectHighlight.setY(f.getY() * size);
        rectHighlight.setWidth(size);
        rectHighlight.setHeight(size);
        rectHighlight.setFill(Color.GREENYELLOW);
        captures.add(rectHighlight);
    }

    private void displayFields(MouseEvent event,BoardView parent){
        board = parent.getBoard();
        board.addToPossibleCaptures();
        board.addToPossibleMoves();
        double size= 500.0/board.getSize();
        int x = (int)Math.floor(event.getX()/500* board.getSize()) ;
        int y = (int)Math.floor(event.getY()/500* board.getSize());
        if(this.checkIfCaptureIsNotPossible(board)){
            for(Field f : board.getFields()[x][y].getPossibleMoves()){
                createRectangle(f,size);
            }
        }
        else {
            for (Field f : board.getFields()[x][y].getPossibleCaptures()) {
                createRectangle(f, size);
            }
        }

    }

    private void changePosition(MouseEvent event) {
        double dx = event.getX() - x;
        double dy = event.getY() - y;

        if (pawnView.hit(x, y)) {
            pawnView.changeX(dx);
            pawnView.changeY(dy);
            pawnView.toFront();
        }

        x += dx;
        y += dy;
    }

    @Override
    public void handle(MouseEvent event) {
        PawnView pawnView = (PawnView) event.getSource();
        BoardView boardView = (BoardView) pawnView.getParent();
        if (controlsEnabled) {
            if (event.getEventType() == MouseEvent.MOUSE_DRAGGED) {
                changePosition(event);
            } else if (event.getEventType() == MouseEvent.MOUSE_RELEASED) {
                int fieldX = pawnView.getFieldX();
                int fieldY = pawnView.getFieldY();
                clientThread.makeMove(startingFieldX, startingFieldY, fieldX, fieldY);
                boardView.getChildren().removeAll(captures);
                captures.clear();
            }
            else if(event.getEventType() == MouseEvent.MOUSE_PRESSED){
                displayFields(event,boardView);
                boardView.getChildren().addAll(captures);
            }
        }
    }
}
