package View;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Pawn extends Circle {
        private int x,y;
        private double diameter;
        private Color stoneColour;

        public Pawn(int x, int y, double diameter, Color colour){
            super(x,y,diameter,colour);
            this.x = x;
            this.y = y;
            this.diameter=diameter;
            this.stoneColour = colour;
        }

        public int getX() {
            return x;
        }

        public double getDiameter() {
            return diameter;
        }


        public int getY() {
            return y;
        }

        public Color getStoneColour() {
            return stoneColour;
        }

}
