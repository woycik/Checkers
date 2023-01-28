package Controller;

import Model.Board;
import Model.Field;
import javafx.scene.paint.Color;

import java.util.List;
import java.util.Random;

public class BotLogic {
    private static final Random random = new Random();
    private final BotThread botThread;

    public BotLogic(BotThread botThread) {
        this.botThread = botThread;
    }

    public void botMakeMove(Board board, String color) {
        prepareBoard(board, color);

        Field fieldBegin = null;
        Field fieldEnd = null;

        if (!board.capturePossible.isEmpty()) {
            fieldBegin = board.capturePossible.get(random.nextInt(board.capturePossible.size()));
            fieldEnd = fieldBegin.getPossibleCaptures().get(0);
        } else {
            board.addToPossibleMoves();
            while (fieldBegin == null) {
                Field randField = board.getFields()[random.nextInt(board.getSize())][random.nextInt(board.getSize())];
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
            fieldEnd = f.get(random.nextInt(f.size()));
        }
        botThread.makeMove(fieldBegin.getX(), fieldBegin.getY(), fieldEnd.getX(), fieldEnd.getY());
    }

    private void prepareBoard(Board board, String color) {
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

