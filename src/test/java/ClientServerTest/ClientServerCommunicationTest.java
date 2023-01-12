package ClientServerTest;

import Controller.*;
import Model.Board;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class ClientServerCommunicationTest {
    private ServerThread serverThread;
    private ClientThread firstClientThread;
    private ClientThread secondClientThread;

    private boolean areBoardsEqual(Board first, Board second) {
        if(first == null && second == null) {
            return true;
        }

        if(first == null || second == null) {
            return false;
        }

        if(first.getSize() != second.getSize()) {
            return false;
        }

        int size = first.getSize();
        Model.Field firstField, secondField;
        for(int x = 0; x < size; x++) {
            for(int y = 0; y < size; y++) {
                firstField = first.getFields()[x][y];
                secondField = second.getFields()[x][y];

                if((firstField.isOccupied() != secondField.isOccupied()) ||
                        ((firstField.getPawn() != null && secondField.getPawn() != null)
                        && !firstField.getPawnColor().equals(secondField.getPawnColor()))) {
                    return false;
                }
            }
        }

        return true;
    }

    @Before
    public void initializeThreads() {
        GameController controller = new PolishCheckersController();
        serverThread = new ServerThread(4444, null, controller);
        serverThread.start();
        firstClientThread = new ClientThread(4444, null);
        firstClientThread.start();
        secondClientThread = new ClientThread(4444, null);
        secondClientThread.start();
    }

    @Test
    public void testBoardConstruction() {
        try {
            Field gameControllerField = ServerThread.class.getDeclaredField("gameController");
            gameControllerField.setAccessible(true);
            GameController gameController = (GameController)gameControllerField.get(serverThread);
            Board serverBoard = gameController.getBoard();
            String gameVariant = gameController.getGameVariant();

            Method getSocketPrintableFormatMethod = ServerThread.class.getDeclaredMethod("getSocketPrintableFormat", Board.class);
            getSocketPrintableFormatMethod.setAccessible(true);
            String serverBoardMessage = (String)getSocketPrintableFormatMethod.invoke(serverThread, serverBoard);

            Method getBoardMethod = ClientThread.class.getDeclaredMethod("getBoard", String.class, String.class);
            getBoardMethod.setAccessible(true);
            Board clientBoard = (Board)getBoardMethod.invoke(firstClientThread, gameVariant, serverBoardMessage);

            assertTrue(areBoardsEqual(serverBoard, clientBoard));
        }
        catch(Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @After
    public void killThreads() {
        serverThread.closeServerSocket();
        serverThread.requestStop();
        firstClientThread.closeSocket();
        secondClientThread.closeSocket();
    }
}
