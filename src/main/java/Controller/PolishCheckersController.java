package Controller;

import Model.Board;
import Model.Field;

import javafx.scene.paint.Color;

import java.util.ArrayList;

public class PolishCheckersController extends GameController {
    private Field[][] fields;
    private ArrayList<Field> blackPawns;
    private ArrayList<Field> whitePawns;
    private ArrayList<Field> available; //lista pionków które mogą wykonać bicie
    private ArrayList<Field> capturePossible; //lista pól w które może wskoczyc pionek w ramach bicia
    private final int size = 10;
    private boolean captureHasBeenDone=false;
    private int numberOfWhitePawns = 20;
    private int numberOfBlackPawns = 20;

    public PolishCheckersController() {
        super(10, 4);
        this.fields = board.getFields();
        this.blackPawns = new ArrayList<>();
        this.whitePawns = new ArrayList<>();
        this.available = new ArrayList<>();
        this.capturePossible = new ArrayList<>();
    }

    public boolean play(int x, int y,int i, int j,String color) {
        this.setMyPawns();                                                       //poznajemy położenie pionków
        if(color.equals("BLACK")){this.captureFieldList(blackPawns);}           //zapisz czarne pola z których mozliwe jest bicie
        else{this.captureFieldList(whitePawns);}                                //zapisz biale pola z których mozliwe jest bicie
        if(this.isCapturePossible()) {                                           //sprawdz czy mozliwe jest bicie dla (bialego/czarnego)
            this.getAvailableFields(x,y);                                       //zapisz pola na które może wybrany pionek wskoczyc po wykonaniu bicia i sprawdz czy ten pionek nalezy do listy
            this.capturePawn(x,y,i,j);                                          //czy doszlo do wykonania bicia?
            if(captureHasBeenDone) {
                this.fieldsWhereCaptureIsPossibleAgain(i, j);
                return !this.isAvailableSizeMoreThan0();                        //powtórzenie ruchu
            }
            return false;
        }
        else {
            if (this.isMoveLegal(x, y, i, j)) {
                return this.movePawn(x,y,i,j);                                  //koniec ruchu dla danego gracza o ile nie wybral niewlasciwego pola
            }
        }
        return false;                                                           //powtórzenie ruchu
    }

    //przypisuje do myPaws wszytkie pionki danego gracza
    //najpierw sprawdzamy czy możliwe jest bicie
    //jesli jest trzeba je wykonać
    //jesli nie robimy normalny ruch
    //po czym oddajemy mozliwosc poruszania się przeciwnikowi

    //zwraca fields na których możliwe jest bicie

    //do listy myPaws przypisuje (jak narazie) wszytkie pionki o kolorze czarnym.
    public void setMyPawns(){
        for(int i=0;i<size;i++){
            for(int j=0;j<size;j++) {
                if (fields[i][j].getPawn() != null) {

                    if (fields[i][j].getPawn().getStoneColour().equals(Color.rgb(0, 0, 0))) {
                        blackPawns.add(fields[i][j]);
                    }
                    else if (fields[i][j].getPawn().getStoneColour().equals(Color.rgb(255, 255, 255))){
                      whitePawns.add(fields[i][j]);
                    }
                }
            }
        }
    }
    //przypisanie do listy capturePossible pol (aktualnie dla pionków czarnych) z których mozliwe jest bicie w ogólnosci
    public void captureFieldList(ArrayList<Field> typeOfPawns) {
        for (Field boardField : typeOfPawns) {
            int x = boardField.getX();
            int y = boardField.getY();
            if ((x + 2 < size) && (x - 2) > 0 && (y - 2) > 0 && (y + 2) < size) {

                if (fields[x + 1][y + 1].isOccupied() && !fields[x + 2][y + 2].isOccupied()) {
                    if (fields[x + 1][y + 1].getPawn().getStoneColour() != fields[x][y].getPawn().getStoneColour()) {
                        capturePossible.add(boardField);
                    }
                }
                if (fields[x + 1][y - 1].isOccupied() && !fields[x + 2][y - 2].isOccupied()) {
                    if (fields[x + 1][y - 1].getPawn().getStoneColour() != fields[x][y].getPawn().getStoneColour()) {
                        capturePossible.add(boardField);
                    }
                }
                if (fields[x - 1][y - 1].isOccupied() && !fields[x - 2][y - 2].isOccupied()) {
                    if (fields[x - 1][y - 1].getPawn().getStoneColour() != fields[x][y].getPawn().getStoneColour()) {
                        capturePossible.add(boardField);
                    }
                }
                if (fields[x - 1][y + 1].isOccupied() && !fields[x - 2][y + 2].isOccupied()) {
                    if (fields[x - 1][y + 1].getPawn().getStoneColour() != fields[x][y].getPawn().getStoneColour()) {
                        capturePossible.add(boardField);
                    }
                }
            }

        }
    }


    //Sprawdzam czy istanieje możliwosć bicia (jak narazie dla pionków czarnych)
    public boolean isCapturePossible(){
        if(capturePossible.size()>0){ return true;}
        return false;
    }


    //Jesleli isCapturePossible==true => lista możliwych bić dla wybranego pionka, po zakoczeniu ustaw jaka sflage czy cos
    public void getAvailableFields(int x,int y) {

        if(capturePossible.contains(fields[x][y])) {
            if(x+2<size && y+2<size){
                if (fields[x + 1][y + 1].isOccupied() && !fields[x + 2][y + 2].isOccupied()) {
                    if (fields[x + 1][y + 1].getPawn().getStoneColour() != fields[x][y].getPawn().getStoneColour()) {
                        available.add(fields[x + 2][y + 2]);
                    }
                }
                }
            if(x+2<size && y-2>0){
                if (fields[x + 1][y - 1].isOccupied() && !fields[x + 2][y - 2].isOccupied()) {
                    if (fields[x + 1][y - 1].getPawn().getStoneColour() != fields[x][y].getPawn().getStoneColour()) {
                        available.add(fields[x + 2][y - 2]);
                    }
                }
                }
            if((x-2)>0 && (y-2)>0){
                if (fields[x - 1][y - 1].isOccupied() && !fields[x - 2][y - 2].isOccupied()) {
                    if (fields[x - 1][y - 1].getPawn().getStoneColour() != fields[x][y].getPawn().getStoneColour()) {
                        available.add(fields[x - 2][y - 2]);
                    }
                }
                }
            if((x-2)>0 && (y+2)<size) {
                if (fields[x - 1][y + 1].isOccupied() && !fields[x - 2][y + 2].isOccupied()) {
                    if (fields[x - 1][y + 1].getPawn().getStoneColour() != fields[x][y].getPawn().getStoneColour()) {
                        available.add(fields[x - 2][y + 2]);
                    }
                }
            }
        }
    }

    public void fieldsWhereCaptureIsPossibleAgain(int x,int y){
        if(x+2<size && y+2<size){
            if (fields[x + 1][y + 1].isOccupied() && !fields[x + 2][y + 2].isOccupied()) {
                if (fields[x + 1][y + 1].getPawn().getStoneColour() != fields[x][y].getPawn().getStoneColour()) {
                    available.add(fields[x + 2][y + 2]);
                }
            }
        }
        if(x+2<size && y-2>0){
            if (fields[x + 1][y - 1].isOccupied() && !fields[x + 2][y - 2].isOccupied()) {
                if (fields[x + 1][y - 1].getPawn().getStoneColour() != fields[x][y].getPawn().getStoneColour()) {
                    available.add(fields[x + 2][y - 2]);
                }
            }
        }
        if((x-2)>0 && (y-2)>0){
            if (fields[x - 1][y - 1].isOccupied() && !fields[x - 2][y - 2].isOccupied()) {
                if (fields[x - 1][y - 1].getPawn().getStoneColour() != fields[x][y].getPawn().getStoneColour()) {
                    available.add(fields[x - 2][y - 2]);
                }
            }
        }
        if((x-2)>0 && (y+2)<size) {
            if (fields[x - 1][y + 1].isOccupied() && !fields[x - 2][y + 2].isOccupied()) {
                if (fields[x - 1][y + 1].getPawn().getStoneColour() != fields[x][y].getPawn().getStoneColour()) {
                    available.add(fields[x - 2][y + 2]);
                }
            }
        }
    }


    //sprawdzenie czy zwykły ruch jest możliwy
    @Override
    public boolean isMoveLegal(int i,int j, int m, int n) {
        if(fields[i][j].getPawn()!=null) {
            if (!fields[m][n].isOccupied()) {
                return (i + 1 == m && (j + 1 == n || j - 1 == n)) || (i - 1 == m && (j + 1 == n || j - 1 == n));
            }
        }
            return false;
    }


    //Jesli zwykły ruch nie został wykonany, sprawdx czy można wykonać bicie



    //czy można wykonać bicie?
    public boolean isAvailableSizeMoreThan0(){
        int size=available.size();
        available.clear();
        return size > 0;
    }


    //zmiana lokalizacji pionka
    public boolean movePawn(int x,int y,int m,int n) {
        if (fields[x][y].isOccupied()) {
            fields[m][n].setPawn(fields[x][y].getPawn());
            fields[x][y].setPawn(null);
            return true;
        }
        return false;
    }

    //bicie
    public void capturePawn(int x,int y,int m,int n){
            if(available.contains(fields[m][n])){
                fields[m][n].setPawn(fields[x][y].getPawn());
                fields[x][y].setPawn(null);
                fields[(x+m)/2][(y+n)/2].setPawn(null);
                if(fields[m][n].getColor().equals(Color.rgb(0,0,0))){
                    numberOfBlackPawns--;
                }
                else{
                    numberOfWhitePawns--;
                }
                available.clear();
                capturePossible.clear();
                captureHasBeenDone=true;
            }
    }

    @Override
    public int getPawnRows() {
        return 4;
    }

    //sprawdzenie czy białe wygrały
    public boolean isWhiteWinner(){
        return numberOfBlackPawns == 0;
    }

    //sprawdzenie czy czarne wygrały
    public boolean isBlackWinner(){
        return numberOfWhitePawns == 0;
    }
}
