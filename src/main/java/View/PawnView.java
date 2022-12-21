package View;

import Controller.ClientThread;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class PawnView extends Circle {
    private final int boardSize;
    private final Color color;
    public final PawnEventHandler pawnEventHandler;

    PawnView(int x, int y, int radius, Color color, int boardSize, ClientThread clientThread) {
        super(x, y, radius, color);
        this.color = color;
        this.boardSize = boardSize;
        this.pawnEventHandler = new PawnEventHandler(this, clientThread);
        setOnMouseDragged(pawnEventHandler);
        setOnMouseReleased(pawnEventHandler);
    }

    public boolean hit(double x, double y) {
        return getBoundsInLocal().contains(x, y);
    }

    public void changeX(double x) {
        setCenterX(getCenterX() + x);
    }

    public void changeY(double y) {
        setCenterY(getCenterY() + y);
    }

    public void setControlsEnabled(boolean controlsEnabled) {
        pawnEventHandler.controlsEnabled = controlsEnabled;
    }

    public int getFieldX() {
        double width = 500;
        double fieldWidth = width / boardSize;
        return (int) Math.floor(this.getCenterX() / fieldWidth);
    }

    public int getFieldY() {
        double height = 500;
        double fieldHeight = height / boardSize;
        return (int) Math.floor(this.getCenterY() / fieldHeight);
    }

    public Color getColor() {
        return this.color;
    }
}
