package View;

import Controller.ClientThread;
import Model.*;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.List;

/**
 * Handles Pawn controls mouse dragged, released and pressed events.
 */
public class PawnEventHandler implements EventHandler<MouseEvent> {
    private final PawnView pawnView;
    private final ClientThread clientThread;
    private double x;
    private double y;
    private final int startingFieldX;
    private final int startingFieldY;
    public boolean controlsEnabled;
    final List<Rectangle> captures;
    Board board;

    /**
     * Default constructor. Controls are disabled by default.
     *
     * @param pawnView     connected pawn view
     * @param clientThread client thread instance
     */
    public PawnEventHandler(PawnView pawnView, ClientThread clientThread) {
        this.pawnView = pawnView;
        startingFieldX = pawnView.getFieldX();
        startingFieldY = pawnView.getFieldY();
        this.clientThread = clientThread;
        controlsEnabled = false;
        captures = new ArrayList<>();
    }

    /**
     * Determines whether there are any captures available on given game board.
     *
     * @param board board object representing current game state
     * @return true if there is at least one legal pawn capture and false otherwise
     */
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

    /**
     * Creates green rectangle and places it on field pawn can move on.
     *
     * @param f    field on which the rectangle will be placed
     * @param size rectangle horizontal and vertical size in pixels
     */
    private void createRectangle(Field f, double size) {
        Rectangle rectHighlight = new Rectangle();
        rectHighlight.setX(f.getX() * size);
        rectHighlight.setY(f.getY() * size);
        rectHighlight.setWidth(size);
        rectHighlight.setHeight(size);
        rectHighlight.setFill(Color.GREENYELLOW);
        captures.add(rectHighlight);
    }

    /**
     * Highlights fields on which selected pawn can move.
     *
     * @param event  mouse event triggering handler
     * @param parent board view
     */
    private void highlightFields(MouseEvent event, BoardView parent) {
        board = parent.getBoard();
        board.addToPossibleCaptures(clientThread.playerColor);
        board.addToPossibleMoves();
        if (board instanceof PolishBoard) {
            board.filterLongestCaptures(clientThread.playerColor);
        }

        int x = (int) Math.floor(event.getX() / 500 * board.getSize());
        int y = (int) Math.floor(event.getY() / 500 * board.getSize());
        if (isCapturePossible(board)) {
            highlightFields(board.getFields()[x][y].getPossibleCaptures());
        } else {
            highlightFields(board.getFields()[x][y].getPossibleMoves());
        }
    }

    /**
     * Highlights all fields in List passed as parameter.
     *
     * @param fields list of all fields to highlight
     */
    private void highlightFields(List<Field> fields) {
        double size = 500.0 / board.getSize();
        for (Field f : fields) {
            createRectangle(f, size);
        }
    }

    /**
     * Moves pawn while dragging mouse.
     *
     * @param event mouse dragged event
     */
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

    /**
     * Handles mouse events.
     *
     * @param event mouse dragged, released or pressed event
     */
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
                highlightFields(event, boardView);
                boardView.getChildren().addAll(captures);
            }
        }
    }
}
