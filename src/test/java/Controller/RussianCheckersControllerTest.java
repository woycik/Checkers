package Controller;

import Model.Field;
import org.junit.Test;

import static org.junit.Assert.*;

public class RussianCheckersControllerTest extends GameControllerTest {

    @Test
    public void testStartingPosition() {
        RussianCheckersController controller = new RussianCheckersController();
        assertEquals(8, controller.getBoardSize());
        assertEquals(3, controller.getPawnRows());
        Field[][] fields = controller.getBoard().getFields();
        // white pawns
        assertTrue(fields[1][5].isOccupied());
        assertTrue(fields[3][5].isOccupied());
        assertTrue(fields[5][5].isOccupied());
        assertTrue(fields[7][5].isOccupied());
        assertTrue(fields[0][6].isOccupied());
        assertTrue(fields[2][6].isOccupied());
        assertTrue(fields[4][6].isOccupied());
        assertTrue(fields[6][6].isOccupied());
        assertTrue(fields[1][7].isOccupied());
        assertTrue(fields[3][7].isOccupied());
        assertTrue(fields[5][7].isOccupied());
        assertTrue(fields[7][7].isOccupied());
        assertEquals(white, fields[1][5].getPawnColor());
        assertEquals(white, fields[3][5].getPawnColor());
        assertEquals(white, fields[5][5].getPawnColor());
        assertEquals(white, fields[7][5].getPawnColor());
        assertEquals(white, fields[0][6].getPawnColor());
        assertEquals(white, fields[2][6].getPawnColor());
        assertEquals(white, fields[4][6].getPawnColor());
        assertEquals(white, fields[6][6].getPawnColor());
        assertEquals(white, fields[1][7].getPawnColor());
        assertEquals(white, fields[1][5].getPawnColor());
        assertEquals(white, fields[3][5].getPawnColor());
        assertEquals(white, fields[5][5].getPawnColor());
        assertEquals(white, fields[7][5].getPawnColor());

        // black pawns
        assertTrue(fields[6][2].isOccupied());
        assertTrue(fields[4][2].isOccupied());
        assertTrue(fields[2][2].isOccupied());
        assertTrue(fields[0][2].isOccupied());
        assertTrue(fields[1][1].isOccupied());
        assertTrue(fields[3][1].isOccupied());
        assertTrue(fields[5][1].isOccupied());
        assertTrue(fields[7][1].isOccupied());
        assertTrue(fields[6][0].isOccupied());
        assertTrue(fields[4][0].isOccupied());
        assertTrue(fields[2][0].isOccupied());
        assertTrue(fields[0][0].isOccupied());
        assertEquals(black, fields[6][2].getPawnColor());
        assertEquals(black, fields[4][2].getPawnColor());
        assertEquals(black, fields[2][2].getPawnColor());
        assertEquals(black, fields[0][2].getPawnColor());
        assertEquals(black, fields[1][1].getPawnColor());
        assertEquals(black, fields[3][1].getPawnColor());
        assertEquals(black, fields[5][1].getPawnColor());
        assertEquals(black, fields[7][1].getPawnColor());
        assertEquals(black, fields[6][0].getPawnColor());
        assertEquals(black, fields[4][0].getPawnColor());
        assertEquals(black, fields[2][0].getPawnColor());
        assertEquals(black, fields[0][0].getPawnColor());
    }
}
