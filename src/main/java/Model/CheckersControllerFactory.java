package Model;

import Controller.EnglishCheckersController;
import Controller.GameController;
import Controller.PolishCheckersController;
import Controller.RussianCheckersController;

/**
 * Allows creating GameController of given type.
 * Implements Factory creational pattern.
 */
public class CheckersControllerFactory {
    /**
     * Creates GameController according to game variant.
     * @param type checkers variant
     * @return GameController related with type or null if type is invalid
     */
    public GameController createGameControler(String type) {
        GameController controller;

        switch(type) {
            case "Polish":
                controller = new PolishCheckersController();
                break;
            case "Russian":
                controller = new RussianCheckersController();
                break;
            case "English":
                controller = new EnglishCheckersController();
                break;
            default:
                controller = null;
                break;
        }

        return controller;
    }
}
