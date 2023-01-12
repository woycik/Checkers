package ControllerTest;

import Controller.EnglishCheckersController;
import Model.PlayerTurn;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class EnglishCheckersControllerTest extends GameControllerTest {

    @Before
    public void initializeController() {
        initialize(new EnglishCheckersController());
    }

    @Test
    public void testStartingPosition() {
        assertEquals(8, board.getBoardSize());
        assertEquals(3, board.getPawnRows());
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

    @Test
    public void testLegalFirstMoves() {
        assertTrue(board.isMoveLegal(1, 5, 0, 4));
        assertTrue(board.isMoveLegal(1, 5, 2, 4));
        assertTrue(board.isMoveLegal(3, 5, 2, 4));
        assertTrue(board.isMoveLegal(3, 5, 4, 4));
        assertTrue(board.isMoveLegal(5, 5, 4, 4));
        assertTrue(board.isMoveLegal(5, 5, 6, 4));
        assertTrue(board.isMoveLegal(7, 5, 6, 4));
    }

    @Test
    public void testPlayerTurns() {
        assertEquals(PlayerTurn.White, controller.playerTurn);
        controller.move(1, 5, 0, 4);
        assertEquals(PlayerTurn.Black, controller.playerTurn);
        controller.move(0, 2, 1, 3);
        assertEquals(PlayerTurn.White, controller.playerTurn);
        controller.move(3, 5, 2, 4);
        assertEquals(PlayerTurn.Black, controller.playerTurn);
        controller.move(1, 3, 3, 5);
        assertEquals(PlayerTurn.White, controller.playerTurn);
        controller.move(2, 6, 4, 4);
        assertEquals(PlayerTurn.Black, controller.playerTurn);
        controller.move(1, 1, 0, 2);
        assertEquals(PlayerTurn.White, controller.playerTurn);
        controller.move(4, 6, 3, 5);
        assertEquals(PlayerTurn.Black, controller.playerTurn);
        controller.move(0, 2, 1, 3);
        assertEquals(PlayerTurn.White, controller.playerTurn);
        controller.move(4, 4, 5, 3);
        assertEquals(PlayerTurn.Black, controller.playerTurn);
        controller.move(6, 2, 4, 4);
        assertEquals(PlayerTurn.Black, controller.playerTurn); // black again, because capture is available
        controller.move(4, 4, 2, 6);
        assertEquals(PlayerTurn.White, controller.playerTurn); // double capture finished
        controller.move(1, 7, 3, 5);
        assertEquals(PlayerTurn.Black, controller.playerTurn);
    }

    @Test
    public void testQueenPromotion() {
        controller.move(5, 5, 4, 4);
        controller.move(2, 2, 3, 3);
        controller.move(4, 4, 2, 2);
        controller.move(1, 1, 3, 3);
        controller.move(3, 5, 4, 4);
        controller.move(3, 3, 5, 5);
        controller.move(6, 6, 4, 4);
        controller.move(4, 2, 3, 3);
        controller.move(4, 4, 2, 2);
        controller.move(3, 1, 1, 3);
        controller.move(1, 5, 2, 4);
        controller.move(1, 3, 3, 5);
        controller.move(4, 6, 2, 4);
        controller.move(0, 2, 1, 3);
        controller.move(2, 4, 0, 2);
        controller.move(0, 0, 1, 1);
        controller.move(0, 6, 1, 5);
        controller.move(1, 1, 2, 2);
        controller.move(7, 5, 6, 4);
        controller.move(2, 0, 3, 1);
        controller.move(2, 6, 3, 5);
        controller.move(2, 2, 3, 3);
        controller.move(0, 2, 1, 1);
        controller.move(3, 1, 4, 2);
        controller.move(1, 1, 0, 0);
        assertTrue(controller.getBoard().getFields()[0][0].getPawn().isQueen());
    }
}
