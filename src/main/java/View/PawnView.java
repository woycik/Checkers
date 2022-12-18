package View;

import Model.Pawn;
import javafx.geometry.Rectangle2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Screen;
import javafx.stage.Window;

public class PawnView extends Circle {

    PawnView(int x, int y, int radious, Color color){
        super(x,y,radious,color);
        setOnMouseDragged(new PawnEventHandler());

    }

    public boolean hit(double x, double y) {
        return getBoundsInLocal().contains(x,y);
    }

    public void changeX(double x) {
        setCenterX(getCenterX()+x);
    }

    public void changeY(double y) {
        setCenterY(getCenterY()+y);
    }

    public int getX(int size){
        Rectangle2D screenBounds = Screen.getPrimary().getBounds();
        double width=screenBounds.getWidth();
        double fieldWidth = width/size;
        int positionX=(int)Math.floor(this.getCenterX()/fieldWidth);

        return positionX;
    }

    public int getY(int size){
        Rectangle2D screenBounds = Screen.getPrimary().getBounds();
        double height=screenBounds.getHeight();
        double fieldHeight = height/size;
        int positionY=(int)Math.floor(this.getCenterY()/fieldHeight);

        return positionY;
    }


}
