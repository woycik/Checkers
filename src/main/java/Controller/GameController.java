package Controller;

import Model.Checkers;
import Model.Pawn;

public abstract class GameController {
    Checkers checkers;
    public abstract void play();
    public abstract boolean isMoveLegal(Pawn pawn);
}
