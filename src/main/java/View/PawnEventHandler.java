package View;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;


public class PawnEventHandler implements EventHandler<MouseEvent> {
    PawnView pawn;
    private double x;
    private double y;

    private void changePosition(MouseEvent event) {
        double dx = event.getX() - x;
        double dy = event.getY() - y;

        if (pawn.hit(x, y)) {
            pawn.changeX(dx);
            pawn.changeY(dy);
        }

        x += dx;
        y += dy;
    }

    @Override
    public void handle(MouseEvent event) {
        pawn = (PawnView) event.getSource();

        if (event.getEventType()==MouseEvent.MOUSE_DRAGGED) {
            changePosition(event);
        }
    }
}
