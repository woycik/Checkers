package Model;

import javafx.scene.paint.Color;

public class PolishBoard extends Board {

    public PolishBoard() {
        super(10, 4);
    }

    @Override
    public String getGameVariant() {
        return "Polish";
    }
    }

