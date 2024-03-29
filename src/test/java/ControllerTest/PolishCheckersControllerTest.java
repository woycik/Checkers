package ControllerTest;

import Controller.PolishCheckersController;
import Model.PlayerTurn;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PolishCheckersControllerTest extends GameControllerTest {

    @Before
    public void initializeController() {
        initialize(new PolishCheckersController());
    }

    /**
     * Verifies correct starting game board state.
     */
    @Test
    public void testStartingPosition() {
        Assert.assertEquals(10, controller.getBoardSize());
        Assert.assertEquals(4, controller.getPawnRows());
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

    /**
     * Test correct legal moves in starting game board position.
     */
    @Test
    public void testLegalFirstMoves() {
        assertTrue(controller.getBoard().isMoveLegal(0, 6, 1, 5));
        assertTrue(controller.getBoard().isMoveLegal(2, 6, 1, 5));
        assertTrue(controller.getBoard().isMoveLegal(2, 6, 3, 5));
        assertTrue(controller.getBoard().isMoveLegal(4, 6, 3, 5));
        assertTrue(controller.getBoard().isMoveLegal(4, 6, 5, 5));
        assertTrue(controller.getBoard().isMoveLegal(6, 6, 5, 5));
        assertTrue(controller.getBoard().isMoveLegal(6, 6, 7, 5));
        assertTrue(controller.getBoard().isMoveLegal(8, 6, 7, 5));
        assertTrue(controller.getBoard().isMoveLegal(8, 6, 9, 5));
    }

    /**
     * Tests correct player turns' order.
     */
    @Test
    public void testPlayerTurns() {
        Assert.assertEquals(PlayerTurn.White, controller.playerTurn);
        controller.move(4, 6, 5, 5);
        Assert.assertEquals(PlayerTurn.Black, controller.playerTurn);
        controller.move(3, 3, 4, 4);
        Assert.assertEquals(PlayerTurn.White, controller.playerTurn);
        controller.move(5, 5, 3, 3);
        Assert.assertEquals(PlayerTurn.Black, controller.playerTurn);
        controller.move(2, 2, 4, 4);
        Assert.assertEquals(PlayerTurn.White, controller.playerTurn);
        controller.move(5, 7, 4, 6);
        Assert.assertEquals(PlayerTurn.Black, controller.playerTurn);
        controller.move(9, 3, 8, 4);
        Assert.assertEquals(PlayerTurn.White, controller.playerTurn);
        controller.move(4, 8, 5, 7);
        Assert.assertEquals(PlayerTurn.Black, controller.playerTurn);
        controller.move(4, 2, 3, 3);
        Assert.assertEquals(PlayerTurn.White, controller.playerTurn);
        controller.move(2, 6, 3, 5);
        Assert.assertEquals(PlayerTurn.Black, controller.playerTurn);
        controller.move(4, 4, 2, 6);
        Assert.assertEquals(PlayerTurn.Black, controller.playerTurn);  // black again, because capture is available
        controller.move(2, 6, 4, 8);
        Assert.assertEquals(PlayerTurn.White, controller.playerTurn);  // double capture finished
        controller.move(5, 9, 3, 7);
        Assert.assertEquals(PlayerTurn.Black, controller.playerTurn);
    }

    /**
     * Performs a sequence of moves to promote a pawn to a queen.
     */
    @Test
    public void testQueenPromotion() {
        controller.move(6, 6, 5, 5);
        controller.move(3, 3, 4, 4);
        controller.move(5, 5, 3, 3);
        controller.move(2, 2, 4, 4);
        controller.move(4, 6, 5, 5);
        controller.move(4, 4, 6, 6);
        controller.move(7, 7, 5, 5);
        controller.move(5, 3, 4, 4);
        controller.move(5, 5, 3, 3);
        controller.move(4, 2, 2, 4);
        controller.move(2, 6, 3, 5);
        controller.move(2, 4, 4, 6);
        controller.move(5, 7, 3, 5);
        controller.move(1, 3, 2, 4);
        controller.move(3, 5, 1, 3);
        controller.move(0, 2, 2, 4);
        controller.move(0, 6, 1, 5);
        controller.move(2, 4, 0, 6);
        controller.move(1, 7, 2, 6);
        controller.move(7, 3, 6, 4);
        controller.move(0, 8, 1, 7);
        controller.move(6, 2, 5, 3);
        controller.move(2, 6, 3, 5);
        controller.move(5, 1, 4, 2);
        controller.move(1, 7, 2, 6);
        controller.move(7, 1, 6, 2);
        controller.move(3, 7, 4, 6);
        controller.move(8, 2, 7, 3);
        controller.move(2, 8, 3, 7);
        controller.move(9, 1, 8, 2);
        controller.move(1, 9, 2, 8);
        controller.move(7, 3, 8, 4);
        controller.move(4, 6, 5, 5);
        controller.move(6, 4, 4, 6);
        controller.move(4, 6, 2, 4);
        controller.move(2, 6, 3, 5);
        controller.move(2, 4, 4, 6);
        controller.move(3, 7, 5, 5);
        controller.move(8, 2, 7, 3);
        controller.move(2, 8, 3, 7);
        controller.move(0, 6, 1, 7);
        controller.move(3, 7, 4, 6);
        controller.move(1, 7, 0, 8);
        controller.move(4, 8, 3, 7);
        controller.move(0, 8, 1, 9);

        assertTrue(controller.getBoard().getFields()[1][9].getPawn().isQueen());
    }

    /**
     * Performs sequence of moves to force game controller to allow only the best capture.
     */
    @Test
    public void bestCaptureRuleTest() {
        controller.move(4, 6, 5, 5);
        controller.move(3, 3, 2, 4);
        controller.move(3, 7, 4, 6);
        controller.move(5, 3, 6, 4);
        controller.move(5, 5, 4, 4);
        controller.move(6, 4, 7, 5);
        controller.move(8, 6, 6, 4);
        controller.move(7, 3, 5, 5);
        //Dochodzi tutaj do sytuacji w której możliwe jest bicie wielu pionków, algorytm słusznie wybiera najlepszą opcję.
        assertFalse(controller.move(5, 5, 3, 3));
        assertFalse(controller.move(5, 5, 3, 7));
        assertTrue(controller.move(3, 7, 1, 5));
    }
}
