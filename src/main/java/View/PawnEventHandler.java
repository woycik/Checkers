package View;

import Controller.ClientThread;
import Model.Board;
import Model.Field;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.List;


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
        this.captures = new ArrayList<>();
    }

    private boolean isCapturePossible(Board board) {
        for (int x = 0; x < board.getSize(); x++) {
            for (int y = 0; y < board.getSize(); y++) {
                if (!board.getFields()[x][y].getPossibleCaptures().isEmpty()) {
                    return true;
                }
            }
        }
        return false;
    }

    private void createRectangle(Field f, double size) {
        Rectangle rectHighlight = new Rectangle();
        rectHighlight.setX(f.getX() * size);
        rectHighlight.setY(f.getY() * size);
        rectHighlight.setWidth(size);
        rectHighlight.setHeight(size);
        rectHighlight.setFill(Color.GREENYELLOW);
        captures.add(rectHighlight);
    }

    private void displayFields(MouseEvent event, BoardView parent) {
        board = parent.getBoard();
        board.addToPossibleCaptures(clientThread.playerColor);
        board.addToPossibleMoves();
        board.fillterLongestCapture();

        int x = (int) Math.floor(event.getX() / 500 * board.getSize());
        int y = (int) Math.floor(event.getY() / 500 * board.getSize());
        if (this.isCapturePossible(board)) {
            highlightFields(board.getFields()[x][y].getPossibleCaptures());
        } else {
            highlightFields(board.getFields()[x][y].getPossibleMoves());
        }
    }

    private void highlightFields(List<Field> fields) {
        double size = 500.0 / board.getSize();
        for (Field f : fields) {
            createRectangle(f, size);
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
            } else if (event.getEventType() == MouseEvent.MOUSE_PRESSED) {
                displayFields(event, boardView);
                boardView.getChildren().addAll(captures);
            }
        }
    }
}
