package Model;

import javafx.scene.paint.Color;

public class EnglishBoard extends Board {
    public EnglishBoard() {
        super(8, 3);
    }

    public void addToPossibleMoves() {
        for (int x = 0; x < getSize(); x++) {
            for (int y = 0; y < getSize(); y++) {
                fields[x][y].clearPossibleMove();
                if (fields[x][y].isOccupied()) {
                    if (!fields[x][y].getPawn().isQueen()) {
                        //góra prawo normalny ruch
                        if ((x + 1) < getSize() && (y - 1) >= 0 && !fields[x + 1][y - 1].isOccupied()) {
                            if (fields[x][y].getPawnColor().equals(Color.rgb(255, 255, 255))) {
                                fields[x][y].addToPossibleMoves(fields[x + 1][y - 1]);
                            }
                        }
                        //góra lewo normalny ruch
                        if ((x - 1) >= 0 && (y - 1) >= 0 && !fields[x - 1][y - 1].isOccupied()) {
                            if (fields[x][y].getPawnColor().equals(Color.rgb(255, 255, 255))) {
                                fields[x][y].addToPossibleMoves(fields[x - 1][y - 1]);
                            }
                        }
                        //dół lewo normalny ruch
                        if ((x - 1) >= 0 && (y + 1) < getSize() && !fields[x - 1][y + 1].isOccupied()) {
                            if (fields[x][y].getPawnColor().equals(Color.rgb(0, 0, 0))) {
                                fields[x][y].addToPossibleMoves(fields[x - 1][y + 1]);
                            }
                        }
                        //dół prawo normalny ruch
                        if ((x + 1) < getSize() && (y + 1) < getSize() && !fields[x + 1][y + 1].isOccupied()) {
                            if (fields[x][y].getPawnColor().equals(Color.rgb(0, 0, 0))) {
                                fields[x][y].addToPossibleMoves(fields[x + 1][y + 1]);
                            }
                        }

                    } else {
                        //queen  góra lewo
                        if ((x + 1) < getSize() && (y - 1) >= 0 && !fields[x + 1][y - 1].isOccupied()) {
                            fields[x][y].addToPossibleMoves(fields[x + 1][y - 1]);
                        }
                        //góra lewo normalny ruch
                        if ((x - 1) >= 0 && (y - 1) >= 0 && !fields[x - 1][y - 1].isOccupied()) {
                            fields[x][y].addToPossibleMoves(fields[x - 1][y - 1]);
                        }
                        //dół lewo normalny ruch
                        if ((x - 1) >= 0 && (y + 1) < getSize() && !fields[x - 1][y + 1].isOccupied()) {
                            fields[x][y].addToPossibleMoves(fields[x - 1][y + 1]);
                        }
                        //dół prawo normalny ruch
                        if ((x + 1) < getSize() && (y + 1) < getSize() && !fields[x + 1][y + 1].isOccupied()) {
                            fields[x][y].addToPossibleMoves(fields[x + 1][y + 1]);
                        }
                    }
                }
            }
        }
    }


    public void addToPossibleCaptures() {
        for (int x = 0; x < getSize(); x++) {
            for (int y = 0; y < getSize(); y++) {
                fields[x][y].clearPossibleCaptures();
                if (fields[x][y].isOccupied()) {
                    if (!fields[x][y].getPawn().isQueen()) {
                        //góra prawo kłucie
                        if ((x + 2) < getSize() && (y - 2) >= 0 && fields[x + 1][y - 1].isOccupied() && !fields[x + 2][y - 2].isOccupied()) {
                            if (fields[x][y].getPawnColor().equals(Color.rgb(255, 255, 255))) {
                                if (!fields[x + 1][y - 1].getPawnColor().equals(fields[x][y].getPawnColor())) {
                                    fields[x][y].addToPossibleCaptures(fields[x + 2][y - 2]);
                                }
                            }
                        }
                        //góra lewo kucie
                        if ((x - 2) >= 0 && (y - 2) >= 0 && fields[x - 1][y - 1].isOccupied() && !fields[x - 2][y - 2].isOccupied()) {
                            if (fields[x][y].getPawnColor().equals(Color.rgb(255, 255, 255))) {
                                if (!fields[x - 1][y - 1].getPawnColor().equals(fields[x][y].getPawnColor())) {
                                    fields[x][y].addToPossibleCaptures(fields[x - 2][y - 2]);
                                }
                            }
                        }
                        //dół prawo kłócie
                        if ((x + 2) < getSize() && (y + 2) < getSize() && fields[x + 1][y + 1].isOccupied() && !fields[x + 2][y + 2].isOccupied()) {
                            if (fields[x][y].getPawnColor().equals(Color.rgb(0, 0, 0))) {
                                if (!fields[x + 1][y + 1].getPawnColor().equals(fields[x][y].getPawnColor())) {
                                    fields[x][y].addToPossibleCaptures(fields[x + 2][y + 2]);
                                }
                            }
                        }
                        //dół lewo kłocie
                        if ((x - 2) >= 0 && (y + 2) < getSize() && fields[x - 1][y + 1].isOccupied() && !fields[x - 2][y + 2].isOccupied()) {
                            if (fields[x][y].getPawnColor().equals(Color.rgb(0, 0, 0))) {
                                if (!fields[x - 1][y + 1].getPawnColor().equals(fields[x][y].getPawnColor())) {
                                    fields[x][y].addToPossibleCaptures(fields[x - 2][y + 2]);
                                }
                            }
                        }
                    } else {
                        //góra prawo kłucie
                        if ((x + 2) < getSize() && (y - 2) >= 0 && fields[x + 1][y - 1].isOccupied() && !fields[x + 2][y - 2].isOccupied()) {
                            if (!fields[x + 1][y - 1].getPawnColor().equals(fields[x][y].getPawnColor())) {
                                fields[x][y].addToPossibleCaptures(fields[x + 2][y - 2]);
                            }
                        }
                        //góra lewo kucie
                        if ((x - 2) >= 0 && (y - 2) >= 0 && fields[x - 1][y - 1].isOccupied() && !fields[x - 2][y - 2].isOccupied()) {
                            if (!fields[x - 1][y - 1].getPawnColor().equals(fields[x][y].getPawnColor())) {
                                fields[x][y].addToPossibleCaptures(fields[x - 2][y - 2]);
                            }
                        }
                        //dół prawo kłócie
                        if ((x + 2) < getSize() && (y + 2) < getSize() && fields[x + 1][y + 1].isOccupied() && !fields[x + 2][y + 2].isOccupied()) {
                            if (!fields[x + 1][y + 1].getPawnColor().equals(fields[x][y].getPawnColor())) {
                                fields[x][y].addToPossibleCaptures(fields[x + 2][y + 2]);
                            }
                        }
                        //dół lewo kłocie
                        if ((x - 2) >= 0 && (y + 2) < getSize() && fields[x - 1][y + 1].isOccupied() && !fields[x - 2][y + 2].isOccupied()) {
                            if (!fields[x - 1][y + 1].getPawnColor().equals(fields[x][y].getPawnColor())) {
                                fields[x][y].addToPossibleCaptures(fields[x - 2][y + 2]);
                            }
                        }
                    }
                }
            }
        }

    }


    public void capturePawn(int x1, int y1, int x2, int y2) {
        if (this.getFields()[x1][y1].isOccupied()) {
            if (this.getFields()[x1][y1].getPawnColor().equals(Color.rgb(0, 0, 0))) {
                numberOfWhitePawns--;
            } else {
                numberOfBlackPawns--;
            }
            this.getFields()[x2][y2].setPawn(this.getFields()[x1][y1].getPawn());
            this.getFields()[x1][y1].setPawn(null);
            this.getFields()[(x1 + x2) / 2][(y1 + y2) / 2].setPawn(null);
            this.createNewQueen(x2, y2);
        }
    }
}
