package Controller;

import Model.Field;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class PolishCheckersControllerTest extends GameControllerTest {

    @Test
    public void testStartingPosition() {
        PolishCheckersController controller = new PolishCheckersController();
        assertEquals(10, controller.getBoardSize());
        assertEquals(4, controller.getPawnRows());
        Field[][] fields = controller.getBoard().getFields();
        // white pawns
        assertTrue(fields[0][6].isOccupied());
        assertTrue(fields[2][6].isOccupied());
        assertTrue(fields[4][6].isOccupied());
        assertTrue(fields[6][6].isOccupied());
        assertTrue(fields[8][6].isOccupied());
        assertTrue(fields[1][7].isOccupied());
        assertTrue(fields[3][7].isOccupied());
        assertTrue(fields[5][7].isOccupied());
        assertTrue(fields[7][7].isOccupied());
        assertTrue(fields[9][7].isOccupied());
        assertTrue(fields[0][8].isOccupied());
        assertTrue(fields[2][8].isOccupied());
        assertTrue(fields[4][8].isOccupied());
        assertTrue(fields[6][8].isOccupied());
        assertTrue(fields[8][8].isOccupied());
        assertTrue(fields[3][9].isOccupied());
        assertTrue(fields[5][9].isOccupied());
        assertTrue(fields[7][9].isOccupied());
        assertTrue(fields[9][9].isOccupied());
        assertEquals(white, fields[0][6].getPawnColor());
        assertEquals(white, fields[2][6].getPawnColor());
        assertEquals(white, fields[4][6].getPawnColor());
        assertEquals(white, fields[6][6].getPawnColor());
        assertEquals(white, fields[8][6].getPawnColor());
        assertEquals(white, fields[1][7].getPawnColor());
        assertEquals(white, fields[3][7].getPawnColor());
        assertEquals(white, fields[5][7].getPawnColor());
        assertEquals(white, fields[7][7].getPawnColor());
        assertEquals(white, fields[0][8].getPawnColor());
        assertEquals(white, fields[2][8].getPawnColor());
        assertEquals(white, fields[4][8].getPawnColor());
        assertEquals(white, fields[6][8].getPawnColor());
        assertEquals(white, fields[8][8].getPawnColor());
        assertEquals(white, fields[1][9].getPawnColor());
        assertEquals(white, fields[3][9].getPawnColor());
        assertEquals(white, fields[5][9].getPawnColor());
        assertEquals(white, fields[7][9].getPawnColor());
        assertEquals(white, fields[9][9].getPawnColor());

        // black pawns
        assertTrue(fields[9][3].isOccupied());
        assertTrue(fields[7][3].isOccupied());
        assertTrue(fields[5][3].isOccupied());
        assertTrue(fields[3][3].isOccupied());
        assertTrue(fields[1][3].isOccupied());
        assertTrue(fields[8][2].isOccupied());
        assertTrue(fields[6][2].isOccupied());
        assertTrue(fields[4][2].isOccupied());
        assertTrue(fields[2][2].isOccupied());
        assertTrue(fields[0][2].isOccupied());
        assertTrue(fields[9][1].isOccupied());
        assertTrue(fields[7][1].isOccupied());
        assertTrue(fields[5][1].isOccupied());
        assertTrue(fields[3][1].isOccupied());
        assertTrue(fields[1][1].isOccupied());
        assertTrue(fields[8][0].isOccupied());
        assertTrue(fields[6][0].isOccupied());
        assertTrue(fields[4][0].isOccupied());
        assertTrue(fields[2][0].isOccupied());
        assertTrue(fields[0][0].isOccupied());
        assertEquals(black, fields[9][3].getPawnColor());
        assertEquals(black, fields[7][3].getPawnColor());
        assertEquals(black, fields[5][3].getPawnColor());
        assertEquals(black, fields[3][3].getPawnColor());
        assertEquals(black, fields[1][3].getPawnColor());
        assertEquals(black, fields[8][2].getPawnColor());
        assertEquals(black, fields[6][2].getPawnColor());
        assertEquals(black, fields[4][2].getPawnColor());
        assertEquals(black, fields[2][2].getPawnColor());
        assertEquals(black, fields[0][2].getPawnColor());
        assertEquals(black, fields[9][1].getPawnColor());
        assertEquals(black, fields[7][1].getPawnColor());
        assertEquals(black, fields[5][1].getPawnColor());
        assertEquals(black, fields[3][1].getPawnColor());
        assertEquals(black, fields[1][1].getPawnColor());
        assertEquals(black, fields[8][0].getPawnColor());
        assertEquals(black, fields[6][0].getPawnColor());
        assertEquals(black, fields[4][0].getPawnColor());
        assertEquals(black, fields[2][0].getPawnColor());
        assertEquals(black, fields[0][0].getPawnColor());
    }
}
