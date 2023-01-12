package View;

import Controller.ClientThread;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 * Graphically represents one pawn on the game board.
 */
public class PawnView extends Circle {
    private final int boardSize;
    private final Color color;
    public final PawnEventHandler pawnEventHandler;

    /**
     * PawnView constructor. Calls Circle constructor.
     * @param x center horizontal position in pixels
     * @param y center vertical position in pixels
     * @param radius pawn radius in pixels
     * @param color pawn color
     * @param boardSize number of fields horizontally and vertically on the game board
     * @param clientThread client thread instance
     */
    PawnView(int x, int y, int radius, Color color, int boardSize, ClientThread clientThread) {
        super(x, y, radius, color);
        this.color = color;
        this.boardSize = boardSize;
        this.pawnEventHandler = new PawnEventHandler(this, clientThread);
        setOnMouseDragged(pawnEventHandler);
        setOnMouseReleased(pawnEventHandler);
        setOnMousePressed(pawnEventHandler);
    }

    public boolean hit(double x, double y) {
        return getBoundsInLocal().contains(x, y);
    }

    /**
     * Updates pawn center horizontal position
     * @param x new center horizontal position in pixels
     */
    public void changeX(double x) {
        setCenterX(getCenterX() + x);
    }

    /**
     * Updates pawn center vertical position
     * @param y new center vertical position in pixels
     */
    public void changeY(double y) {
        setCenterY(getCenterY() + y);
    }

    /**
     * Enables or disables pawns moving depending on players turn.
     * @param controlsEnabled flag defining whether pawn controls should be enabled or disabled
     */
    public void setControlsEnabled(boolean controlsEnabled) {
        pawnEventHandler.controlsEnabled = controlsEnabled;
    }

    /**
     * Calculates field's position in game board fields array according to screen position in pixels.
     * @return horizontal field cooridinate in game board fields array
     */
    public int getFieldX() {
        double width = 500;
        double fieldWidth = width / boardSize;
        return (int) Math.floor(this.getCenterX() / fieldWidth);
    }

    /**
     * Calculates field's position in game board fields array according to screen position in pixels.
     * @return vertical field cooridinate in game board fields array
     */
    public int getFieldY() {
        double height = 500;
        double fieldHeight = height / boardSize;
        return (int) Math.floor(this.getCenterY() / fieldHeight);
    }

    /**
     * Returns pawn color
     * @return pawn color
     */
    public Color getColor() {
        return color;
    }
}
