package Controller;

import Model.Checkers;
import Model.Pawn;

public abstract class GameController {
    Checkers checkers;
    public abstract boolean play(int x, int y,int i, int j,String color);
    public abstract boolean isMoveLegal(int i, int j, int m, int n);
}
