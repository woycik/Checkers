package View;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class PawnView extends Circle {

    PawnView(int x, int y, int radius, Color color){
        super(x,y,radius,color);
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

    public int getFieldX(int size){
        double width=500;
        double fieldWidth = width/size;
        return (int)Math.floor(this.getCenterX()/fieldWidth);
    }

    public int getFieldY(int size){
        double height=500;
        double fieldHeight = height/size;
        return (int)Math.floor(this.getCenterY()/fieldHeight);
    }
}
