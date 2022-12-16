package Controller;

import Model.Board;
import Model.Field;
import Model.Pawn;

import javafx.scene.paint.Color;

import java.util.ArrayList;

public class PolishCheckersController extends GameController {
    private Board board;
    private Field[][] field;

    private ArrayList<Field> blackPawns=new ArrayList<>();

    private ArrayList<Field> whitePawns=new ArrayList<>();
    private ArrayList<Field> available=new ArrayList<>(); //lista pionków które mogą wykonać bicie
    private ArrayList<Field> capturePossible=new ArrayList<>();//lista pól w które może wskoczyc pionek w ramach bicia
    private int size=10;
    private boolean captureHasBeenDone=false;
    private int numberOfWhitePawns=20;
    private int numberOfBlackPawns=20;
    private boolean correctMove=false;


    public PolishCheckersController(Board board) {
        this.board=board;//stwóż klienta i jakis enum na to czy moze grac
        field=board.getField();
    }
    public boolean play(int x, int y,int i, int j,String color) {
        this.setMyPaws();                                                       //poznajemy położenie pionków
        if(color.equals("BLACK")){this.captureFieldList(blackPawns);}           //zapisz czarne pola z których mozliwe jest bicie
        else{this.captureFieldList(whitePawns);}                                //zapisz biale pola z których mozliwe jest bicie
        if(this.isCapturePossible()){                                           //sprawdz czy mozliwe jest bicie dla (bialego/czarnego)

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
    public void setMyPaws(){
        for(int i=0;i<size;i++){
            for(int j=0;j<size;j++) {
                if (field[i][j].getPawn() != null) {

                    if (field[i][j].getPawn().getStoneColour().equals(Color.rgb(0, 0, 0))) {
                        blackPawns.add(field[i][j]);
                    }
                    else if (field[i][j].getPawn().getStoneColour().equals(Color.rgb(255, 255, 255))){
                      whitePawns.add(field[i][j]);
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

                if (field[x + 1][y + 1].isOccupied() && !field[x + 2][y + 2].isOccupied()) {
                    if (field[x + 1][y + 1].getPawn().getStoneColour() != field[x][y].getPawn().getStoneColour()) {
                        capturePossible.add(boardField);
                    }
                }
                if (field[x + 1][y - 1].isOccupied() && !field[x + 2][y - 2].isOccupied()) {
                    if (field[x + 1][y - 1].getPawn().getStoneColour() != field[x][y].getPawn().getStoneColour()) {
                        capturePossible.add(boardField);
                    }
                }
                if (field[x - 1][y - 1].isOccupied() && !field[x - 2][y - 2].isOccupied()) {
                    if (field[x - 1][y - 1].getPawn().getStoneColour() != field[x][y].getPawn().getStoneColour()) {
                        capturePossible.add(boardField);
                    }
                }
                if (field[x - 1][y + 1].isOccupied() && !field[x - 2][y + 2].isOccupied()) {
                    if (field[x - 1][y + 1].getPawn().getStoneColour() != field[x][y].getPawn().getStoneColour()) {
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

        if(capturePossible.contains(field[x][y])) {
            if(x+2<size && y+2<size){
                if (field[x + 1][y + 1].isOccupied() && !field[x + 2][y + 2].isOccupied()) {
                    if (field[x + 1][y + 1].getPawn().getStoneColour() != field[x][y].getPawn().getStoneColour()) {
                        available.add(field[x + 2][y + 2]);
                    }
                }
                }
            if(x+2<size && y-2>0){
                if (field[x + 1][y - 1].isOccupied() && !field[x + 2][y - 2].isOccupied()) {
                    if (field[x + 1][y - 1].getPawn().getStoneColour() != field[x][y].getPawn().getStoneColour()) {
                        available.add(field[x + 2][y - 2]);
                    }
                }
                }
            if((x-2)>0 && (y-2)>0){
                if (field[x - 1][y - 1].isOccupied() && !field[x - 2][y - 2].isOccupied()) {
                    if (field[x - 1][y - 1].getPawn().getStoneColour() != field[x][y].getPawn().getStoneColour()) {
                        available.add(field[x - 2][y - 2]);
                    }
                }
                }
            if((x-2)>0 && (y+2)<size) {
                if (field[x - 1][y + 1].isOccupied() && !field[x - 2][y + 2].isOccupied()) {
                    if (field[x - 1][y + 1].getPawn().getStoneColour() != field[x][y].getPawn().getStoneColour()) {
                        available.add(field[x - 2][y + 2]);
                    }
                }
            }
        }
    }

    public void fieldsWhereCaptureIsPossibleAgain(int x,int y){
        if(x+2<size && y+2<size){
            if (field[x + 1][y + 1].isOccupied() && !field[x + 2][y + 2].isOccupied()) {
                if (field[x + 1][y + 1].getPawn().getStoneColour() != field[x][y].getPawn().getStoneColour()) {
                    available.add(field[x + 2][y + 2]);
                }
            }
        }
        if(x+2<size && y-2>0){
            if (field[x + 1][y - 1].isOccupied() && !field[x + 2][y - 2].isOccupied()) {
                if (field[x + 1][y - 1].getPawn().getStoneColour() != field[x][y].getPawn().getStoneColour()) {
                    available.add(field[x + 2][y - 2]);
                }
            }
        }
        if((x-2)>0 && (y-2)>0){
            if (field[x - 1][y - 1].isOccupied() && !field[x - 2][y - 2].isOccupied()) {
                if (field[x - 1][y - 1].getPawn().getStoneColour() != field[x][y].getPawn().getStoneColour()) {
                    available.add(field[x - 2][y - 2]);
                }
            }
        }
        if((x-2)>0 && (y+2)<size) {
            if (field[x - 1][y + 1].isOccupied() && !field[x - 2][y + 2].isOccupied()) {
                if (field[x - 1][y + 1].getPawn().getStoneColour() != field[x][y].getPawn().getStoneColour()) {
                    available.add(field[x - 2][y + 2]);
                }
            }
        }
    }


    //sprawdzenie czy zwykły ruch jest możliwy
    @Override
    public boolean isMoveLegal(int i,int j, int m, int n) {
        if(field[i][j].getPawn()!=null) {
            if (!field[m][n].isOccupied()) {
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
        if (field[x][y].isOccupied()) {
            field[m][n].setPawn(field[x][y].getPawn());
            field[x][y].setPawn(null);
            return true;
        }
        return false;
    }

    //bicie
    public void capturePawn(int x,int y,int m,int n){
            if(available.contains(field[m][n])){
                field[m][n].setPawn(field[x][y].getPawn());
                field[x][y].setPawn(null);
                field[(x+m)/2][(y+n)/2].setPawn(null);
                if(field[m][n].getColor().equals(Color.rgb(0,0,0))){
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


    //sprawdzenie czy białe wygrały
    public boolean isWhiteWinner(){
        return numberOfBlackPawns == 0;
    }

    //sprawdzenie czy czarne wygrały
    public boolean isBlackWinner(){
        return numberOfWhitePawns == 0;
    }
}
