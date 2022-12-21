package View;

import Controller.ClientThread;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;


public class PawnEventHandler implements EventHandler<MouseEvent> {
    private PawnView pawnView;
    private final ClientThread clientThread;
    private double x;
    private double y;
    private final int startingFieldX;
    private final int startingFieldY;
    public boolean controlsEnabled;


    public PawnEventHandler(PawnView pawnView, ClientThread clientThread) {
        super();
        this.pawnView = pawnView;
        this.startingFieldX = pawnView.getFieldX();
        this.startingFieldY = pawnView.getFieldY();
        this.clientThread = clientThread;
        this.controlsEnabled = false;
    }

    private void changePosition(MouseEvent event) {
        double dx = event.getX() - x;
        double dy = event.getY() - y;

        if (pawnView.hit(x, y)) {
            pawnView.changeX(dx);
            pawnView.changeY(dy);
        }

        x += dx;
        y += dy;
    }

    @Override
    public void handle(MouseEvent event) {
        if(controlsEnabled) {
            if (event.getEventType() == MouseEvent.MOUSE_DRAGGED) {
                changePosition(event);
            }
            else if(event.getEventType() == MouseEvent.MOUSE_RELEASED) {
                int fieldX = pawnView.getFieldX();
                int fieldY = pawnView.getFieldY();
                clientThread.makeMove(startingFieldX, startingFieldY, fieldX, fieldY);
            }
        }
    }
}
