package Controller;

import Model.Pawn;

public class PolishCheckersController extends GameController {
    public PolishCheckersController() {

    }
    public void play() {
        System.out.println("Polskie");
    }

    @Override
    public boolean isMoveLegal(Pawn pawn) {
        return true;
    }
}
