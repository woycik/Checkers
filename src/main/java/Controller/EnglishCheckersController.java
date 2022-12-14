package Controller;

import Model.Pawn;

public class EnglishCheckersController extends GameController {
    public EnglishCheckersController() {

    }
    public void play() {
        System.out.println("Angielskie");
    }

    @Override
    public boolean isMoveLegal(Pawn pawn) {
        return true;
    }
}
