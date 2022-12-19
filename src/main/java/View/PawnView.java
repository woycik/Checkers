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
