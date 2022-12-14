package Controller;

import Model.Pawn;

public class RussianCheckersController extends GameController {
    public RussianCheckersController() {

    }
    public void play() {
        System.out.println("Rosyjskie");
    }

    @Override
    public boolean isMoveLegal(Pawn pawn) {
        return true;
    }
}
