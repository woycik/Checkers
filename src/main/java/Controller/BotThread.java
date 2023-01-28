package Controller;

import Model.Board;

/**
 * Thread communicating Bot with Server.
 * This class extends ClientThread and uses BotLogic to make moves.
 */
public class BotThread extends ClientThread {
    private final BotLogic botLogic;

    public BotThread(int port) {
        super(port, null);
        botLogic = new BotLogic(this);
    }

    @Override
    protected void handleUpdate(String[] messageSplit) {
        String playerTurn = messageSplit[1];
        Board board = getBoard(gameVariant, messageSplit[2]);

        try {
            Thread.sleep(100);
        } catch (Exception ignored) {
        }

        botLogic.botMakeMove(board, playerTurn);
    }
}
