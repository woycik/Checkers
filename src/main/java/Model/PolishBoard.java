package Model;

import javafx.scene.paint.Color;

public class PolishBoard extends Board {

    public PolishBoard() {
        super(10, 4);
    }

    @Override
    public String getGameVariant() {
        return "Polish";
    }

    @Override
    public void addToPossibleCaptures(String color) {
        Color playerColor = getPlayerRGBColor(color);
            for (int x = 0; x < getSize(); x++) {
                for (int y = 0; y < getSize(); y++) {
                    fields[x][y].clearPossibleCaptures();
                    if (fields[x][y].isOccupied()) {
                        if(!playerColor.equals(fields[x][y].getPawnColor())){
                            continue;
                        }
                        if (!fields[x][y].getPawn().isQueen()) {
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
                        } else {

                            int currx = x;
                            int curry = y;
                            int stateOfCaptures = 0;
                            while (currx > 0 && curry > 0) {
                                currx--;
                                curry--;
                                if (!fields[currx][curry].isOccupied() && stateOfCaptures == 0) {
                                    continue;
                                }
                                if (!fields[currx][curry].isOccupied() && stateOfCaptures == 1) {

                                    fields[x][y].addToPossibleCaptures(fields[currx][curry]);

                                } else if (fields[currx][curry].isOccupied() && !fields[currx][curry].getPawnColor().equals(fields[x][y].getPawnColor())) {
                                    if ((currx - 1) >= 0 && (curry - 1) >= 0 && !fields[currx - 1][curry - 1].isOccupied()) {
                                        stateOfCaptures++;
                                    }
                                    else{
                                        break;
                                    }

                                } else {
                                    break;
                                }
                            }


                            currx = x;
                            curry = y;
                            stateOfCaptures = 0;
                            while (currx < (getSize() - 1) && curry > 0) {
                                currx++;
                                curry--;
                                if (!fields[currx][curry].isOccupied() && stateOfCaptures == 0) {
                                    continue;
                                }
                                if (!fields[currx][curry].isOccupied() && stateOfCaptures == 1) {

                                    fields[x][y].addToPossibleCaptures(fields[currx][curry]);

                                } else if (fields[currx][curry].isOccupied() && !fields[currx][curry].getPawnColor().equals(fields[x][y].getPawnColor())) {
                                    if ((currx + 1) < getSize() && (curry - 1) >= 0 && !fields[currx + 1][curry - 1].isOccupied()) {
                                        stateOfCaptures++;
                                    }
                                    else{
                                        break;
                                    }

                                } else {
                                    break;
                                }
                            }

                            //queen bicie dół prawo
                            currx = x;
                            curry = y;
                            stateOfCaptures = 0;
                            while (currx < (getSize() - 1) && curry < (getSize() - 1)) {
                                currx++;
                                curry++;
                                if (!fields[currx][curry].isOccupied() && stateOfCaptures == 0) {
                                    continue;
                                }
                                if (!fields[currx][curry].isOccupied() && stateOfCaptures == 1) {

                                    fields[x][y].addToPossibleCaptures(fields[currx][curry]);

                                } else if (fields[currx][curry].isOccupied() && !fields[currx][curry].getPawnColor().equals(fields[x][y].getPawnColor())) {
                                    if ((currx + 1) < getSize() && (curry + 1) < getSize() && !fields[currx + 1][curry + 1].isOccupied()) {
                                        stateOfCaptures++;
                                    }
                                    else{
                                        break;
                                    }

                                } else {
                                    break;
                                }
                            }

                            //queen bicie dół lewo
                            currx = x;
                            curry = y;
                            stateOfCaptures = 0;
                            while (currx > 0 && curry < (getSize() - 1)) {
                                currx--;
                                curry++;
                                if (!fields[currx][curry].isOccupied() && stateOfCaptures == 0) {
                                    continue;
                                }
                                if (!fields[currx][curry].isOccupied() && stateOfCaptures == 1) {

                                    fields[x][y].addToPossibleCaptures(fields[currx][curry]);

                                } else if (fields[currx][curry].isOccupied() && !fields[currx][curry].getPawnColor().equals(fields[x][y].getPawnColor())) {
                                    if ((currx - 1) >= 0 && (curry + 1) < getSize() && !fields[currx - 1][curry + 1].isOccupied()) {
                                        stateOfCaptures++;
                                    }
                                    else{
                                        break;
                                    }

                                } else {
                                    break;
                                }
                            }

                        }
                    }
                }
            }
        }
    }

