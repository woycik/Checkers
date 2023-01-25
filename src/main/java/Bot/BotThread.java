package Bot;
import Model.Board;
import Model.BoardFactory;
import Model.Pawn;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import static javafx.scene.paint.Color.rgb;

public class BotThread extends Thread {
    final int port;
    Socket socket;
    PrintWriter out;
    BufferedReader in;
    public String playerColor;
    String gameVariant;
    int boardSize;

    public BotThread(int port){
        this.port=port;
    }

    @Override
    public void run() {
        try {
            socket = new Socket("localhost", port);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String serverMessage;
            String[] messageSplit;
            do {
                serverMessage = in.readLine();

                if (serverMessage.equals("ping")) {
                    out.println("pong");
                }
            } while (!serverMessage.split(";")[0].equals("start"));
            messageSplit = serverMessage.split(";");
            gameVariant = messageSplit[1];
            playerColor = messageSplit[2];
            boardSize = Integer.parseInt(messageSplit[3]);
            BotLogic bot = new BotLogic(this,getBoard(gameVariant, messageSplit[4]));
            bot.botMakeMove(getBoard(gameVariant, messageSplit[4]),playerColor);
            while (true) {
                serverMessage = in.readLine();
                messageSplit = serverMessage.split(";");
                if (messageSplit[0].equals("update")) {
                    Board board = getBoard(gameVariant, messageSplit[2]);
                    bot.botMakeMove(board,playerColor);
                } else if (messageSplit[0].equals("win")) {
                    break;
                } else if (messageSplit[0].equals("disconnect")) {
                    break;
                }
            }
        }  catch (Exception e) {
            e.printStackTrace();
            try {
                socket.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public Board getBoard(String gameVariant, String message) {
        BoardFactory boardFactory = new BoardFactory();
        Board board = boardFactory.createBoard(gameVariant);
        if(board == null) {
            return null;
        }
        String[] messageSplit = message.split(",");
        char pawn;
        int n = 0;
        for (int i = 0; i < board.getSize(); i++) {
            for (int j = 0; j < board.getSize(); j++) {
                pawn = messageSplit[n++].charAt(0);

                if (pawn == '0') { // empty field
                    board.getFields()[i][j].setPawn(null);
                } else if (pawn == 'w') { // white pawn
                    board.getFields()[i][j].setPawn(new Pawn(rgb(255, 255, 255)));
                } else if (pawn == 'b') { // black pawn
                    board.getFields()[i][j].setPawn(new Pawn(rgb(0, 0, 0)));
                } else if (pawn == 'W') { // white queen
                    board.getFields()[i][j].setPawn(new Pawn(rgb(255, 255, 255), true));
                } else if (pawn == 'B') { // black queen
                    board.getFields()[i][j].setPawn(new Pawn(rgb(0, 0, 0), true));
                }
            }
        }
        return board;
    }

    /**
     * Sends request to perform a move to the server.
     * @param x1 starting field x coordinate
     * @param y1 starting field y coordinate
     * @param x2 landing field x coordinate
     * @param y2 landing field y coordinate
     */
    public void makeMove(int x1, int y1, int x2, int y2) {
        out.println("move;" + x1 + ";" + y1 + ";" + x2 + ";" + y2);
    }

    /**
     * Safely closes the socket.
     */
    public void closeSocket() {
        try {
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
