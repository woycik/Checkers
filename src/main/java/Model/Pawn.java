package Model;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Pawn {
        private Color stoneColour;
        private boolean isQueen;

        public Pawn(Color colour) {
            this.stoneColour = colour;
            this.isQueen = false;
        }

        public Color getStoneColour() {
            return stoneColour;
        }

        public boolean isQueen() {
            return isQueen;
        }
        public void makeQueen() {
            isQueen = true;
        }
}
