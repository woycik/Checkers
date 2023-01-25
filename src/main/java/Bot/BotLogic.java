package Bot;

import Model.Board;
import Model.Field;
import javafx.scene.paint.Color;

import java.util.List;
import java.util.Random;

public class BotLogic {
    BotThread bot;
    Board board;
    Field fieldBegin;
    Field fieldEnd;

    public BotLogic(BotThread bot, Board board) {
        this.bot = bot;
        this.board = board;
    }

    public void botMakeMove(Board board, String color) throws InterruptedException {
        Thread.sleep(100);
        Random rand = new Random();
        this.prepareBoard(board, color);
        if (!board.capturePossible.isEmpty()) {
            fieldBegin = board.capturePossible.get(rand.nextInt(board.capturePossible.size()));
            fieldEnd = fieldBegin.getPossibleCaptures().get(0);
        } else {
            board.addToPossibleMoves();
            while (fieldBegin == null) {
                Field randField = board.getFields()[rand.nextInt(bot.boardSize)][rand.nextInt(bot.boardSize)];
                if (color.equals("Black")) {
                    if (randField.isOccupied() && randField.getPawnColor().equals(Color.rgb(0, 0, 0)) && randField.getPossibleMoves().size() > 0) {
                        fieldBegin = randField;
                    }
                } else {
                    if (randField.isOccupied() && randField.getPawnColor().equals(Color.rgb(255, 255, 255)) && randField.getPossibleMoves().size() > 0) {
                        fieldBegin = randField;
                    }
                }
            }

            List<Field> f = fieldBegin.getPossibleMoves();
            fieldEnd = f.get(rand.nextInt(f.size()));
        }
        bot.makeMove(fieldBegin.getX(), fieldBegin.getY(), fieldEnd.getX(), fieldEnd.getY());
    }

    private void prepareBoard(Board board, String color) {
        fieldBegin = null;
        board.capturePossible.clear();
        board.setMyPawns();
        board.addToPossibleCaptures(color);
        if (color.equals("Black")) {
            board.captureFieldList(board.blackPawns);
        } else {
            board.captureFieldList(board.whitePawns);
        }
    }
}

