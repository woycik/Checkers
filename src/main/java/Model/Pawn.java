package Model;

import javafx.scene.paint.Color;

import java.util.ArrayList;

public class Pawn {
        private Color stoneColour;
        private boolean isQueen;

        public ArrayList<Pawn> possibleFields=new ArrayList<>();

        public Pawn(Color colour) {
            this.stoneColour = colour;
            this.isQueen = false;
        }

        public Pawn(Color colour, boolean isQueen) {
            this.stoneColour = colour;
            this.isQueen = isQueen;
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
