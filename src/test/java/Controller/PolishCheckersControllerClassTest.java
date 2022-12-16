package Controller;

import Model.Board;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class PolishCheckersControllerClassTest {

    @Test
    public void test() {
        PolishCheckersController polishCheckersController = new PolishCheckersController();
        assertTrue(polishCheckersController.play(3,1,4,2,"WHITE"));
        assertTrue(polishCheckersController.play(4,2,5,3,"WHITE"));
    }
}
