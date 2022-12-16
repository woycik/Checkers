package Model;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.ArrayList;

public class Pawn {
        private Color stoneColour;
        private boolean isQueen;

        public ArrayList<Pawn> possibleFields=new ArrayList<>();

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
