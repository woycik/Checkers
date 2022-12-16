package Controller;

import Model.Board;
import Model.Field;
import Model.Pawn;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class EnglishCheckersController extends GameController {
    private Board board;
    private Field[][] field;

    private ArrayList<Field> blackPawns=new ArrayList<>();

    private ArrayList<Field> whitePawns=new ArrayList<>();
    private ArrayList<Field> capturePossible=new ArrayList<>();//lista pól w które może wskoczyc pionek w ramach bicia
    private int size=8;
    boolean dokonczBicie=false;
    private int numberOfWhitePawns=12;
    private int numberOfBlackPawns=12;


    public EnglishCheckersController() {
        this.board=new Board(10,4);
        field=board.getField();
    }
    public boolean play(int x, int y,int i, int j,String color) {
        this.setMyPaws();                                                                       //poznajemy położenie pionków
        if(!dokonczBicie) {
            if (color.equals("BLACK")) {this.captureFieldList(blackPawns);}                     //zapisz czarne pola z których mozliwe jest bicie}
            else {this.captureFieldList(whitePawns);}                                                                           //zapisz biale pola z których mozliwe jest bicie
            if (this.isCapturePossible()) {                                                     //sprawdz czy mozliwe jest bicie dla (bialego/czarnego)
                //czy doszloby do wykonania bicia?
                if (this.checkCapture(x, y, i, j)) {                                            //zapisz pola na które może wybrany pionek wskoczyc po wykonaniu bicia i sprawdz czy ten pionek nalezy do listy
                    this.capturePawn(x, y, i, j); //jak tak to zbij
                    this.capturePossible.clear();
                    if (this.canICaptureOneMoreTime(i, j)) {
                        capturePossible.add(field[i][j]);
                        dokonczBicie = true;
                        return false;
                    }
                }

                return false;
            } else {
                if (this.isMoveLegal(x, y, i, j)) {
                    return this.movePawn(x, y, i, j);                                           //koniec ruchu dla danego gracza o ile nie wybral niewlasciwego pola
                }
            }
        }
        else{
            if (this.checkCapture(x, y, i, j)) {                                       //zapisz pola na które może wybrany pionek wskoczyc po wykonaniu bicia i sprawdz czy ten pionek nalezy do listy
                this.capturePawn(x, y, i, j); //jak tak to zbij
                this.capturePossible.clear();
                if (this.canICaptureOneMoreTime(i, j)) {
                    capturePossible.add(field[i][j]);
                    dokonczBicie = true;
                    return false;
                }
                dokonczBicie=false;
            }

        }
        return false;                                                                    //powtórzenie ruchu
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
    //przypisanie do listy capturePossible pol  z których mozliwe jest bicie w ogólnosci i dla queen tez
    public void captureFieldList(ArrayList<Field> typeOfPawns) {
        for (Field boardField : typeOfPawns) {
            int x = boardField.getX();
            int y = boardField.getY();

            if(!field[x][y].getPawn().isQueen()) {
                if ((x + 2 < size) && (y + 2) < size) {
                    if (field[x + 1][y + 1].isOccupied() && !field[x + 2][y + 2].isOccupied()) {
                        if (field[x + 1][y + 1].getPawn().getStoneColour() != field[x][y].getPawn().getStoneColour()) {
                            capturePossible.add(boardField);
                        }
                    }
                }
                if ((x + 2 < size) && (y - 2) > 0) {
                    if (field[x + 1][y - 1].isOccupied() && !field[x + 2][y - 2].isOccupied()) {
                        if (field[x + 1][y - 1].getPawn().getStoneColour() != field[x][y].getPawn().getStoneColour()) {
                            capturePossible.add(boardField);
                        }
                    }
                }
                if ((x - 2) > 0 && (y - 2) > 0) {
                    if (field[x - 1][y - 1].isOccupied() && !field[x - 2][y - 2].isOccupied()) {
                        if (field[x - 1][y - 1].getPawn().getStoneColour() != field[x][y].getPawn().getStoneColour()) {
                            capturePossible.add(boardField);
                        }
                    }
                }
                if ((x - 2) > 0 && (y + 2) < size) {
                    if (field[x - 1][y + 1].isOccupied() && !field[x - 2][y + 2].isOccupied()) {
                        if (field[x - 1][y + 1].getPawn().getStoneColour() != field[x][y].getPawn().getStoneColour()) {
                            capturePossible.add(boardField);
                        }
                    }
                }
            }
            else{

                int i=1;
                while(x+i+1<size && y+1+i<size){
                    if(field[x+i][y+i].getColor()!=field[x][y].getColor()){
                        if(!field[x+i+1][y+i+1].isOccupied()){
                            if(capturePossible.contains(field[x][y])) {
                                capturePossible.add(field[x][y]);
                            }
                        }
                    }
                    else{
                        break;
                    }
                    i++;
                }
                i=1;
                while(x+i+1<size && y-1-i>0){
                    if(field[x+i][y-i].getPawn().getStoneColour()!=field[x][y].getPawn().getStoneColour()){
                        if(!field[x+i+1][y-i-1].isOccupied()){
                            if(!capturePossible.contains(field[x][y])) {
                                capturePossible.add(field[x][y]);
                            }
                        }
                    }
                    else{
                        break;
                    }
                    i++;
                }
                i=1;
                while(x-i-1>0 && y-1-i>0){
                    if(field[x-i][y-i].getPawn().getStoneColour()!=field[x][y].getPawn().getStoneColour()){
                        if(!field[x-i-1][y-i-1].isOccupied()){
                            if(!capturePossible.contains(field[x][y])) {
                                capturePossible.add(field[x][y]);
                            }
                        }
                    }
                    else{
                        break;
                    }
                    i++;
                }
                i=1;
                while(x-i-1>0 && y+1+i<size){
                    if(field[x-i][y+i].getColor()!=field[x][y].getColor()){
                        if(!field[x-i-1][y+i+1].isOccupied()){
                            if(!capturePossible.contains(field[x][y])) {
                                capturePossible.add(field[x][y]);
                            }
                        }
                    }
                    else{
                        break;
                    }
                    i++;
                }


            }
        }


    }


    //Sprawdzam czy istanieje możliwosć bicia poprzes sprawdzenie dluzgosci listy capturepossible
    public boolean isCapturePossible(){
        if(capturePossible.size()>0){ return true;}
        return false;
    }

    //Sprawdzam czy mozliwe jest bicie dla podanych lokalizacji

    public boolean checkCapture(int x, int y, int m, int n){
        if(capturePossible.contains(field[x][y])){
            if(!field[x][y].getPawn().isQueen()){
                if(Math.abs(x-m)==2 && Math.abs(y-n)==2 && field[(x+m)/2][(y+n)/2].isOccupied() && !field[m][n].isOccupied()){
                    return field[(x + m) / 2][(y + n) / 2].getColor() != field[x][y].getColor();
                }
            }
            else{
                if(!field[m][n].isOccupied()){
                    int diffX;
                    int diffY;

                    diffX =m - x;
                    diffY =n - y;
                    int count=0;
                    if(diffY==diffX || -diffY==diffX){
                        if(diffY>0 && diffX>0){
                            for(int i=1;i<diffX;i++){
                                if(field[x+i][y+1].getColor()!=field[x][y].getColor()){
                                    count++;
                                }
                            }
                        }
                        if(diffY>0 && diffX<0){
                            for(int i=1;i<Math.abs(diffX);i++){
                                if(field[x-i][y+i].getColor()!=field[x][y].getColor()){
                                    count++;
                                }
                            }
                        }
                        if(diffY<0 && diffX<0){
                            for(int i=1;i<Math.abs(diffX);i++){
                                if(field[x-i][y-i].getColor()!=field[x][y].getColor()){
                                    count++;
                                }
                            }
                        }
                        if(diffY<0 && diffX>0){
                            for(int i=1;i<diffX;i++){
                                if(field[x-i][y+1].getColor()!=field[x][y].getColor()){
                                    count++;
                                }
                            }
                        }
                        return count == 1;
                    }
                }
            }
        }
        return false;
    }



    public boolean canICaptureOneMoreTime(int x,int y){
        if(!field[x][y].getPawn().isQueen()) {
            if (x + 2 < size && y + 2 < size) {
                if (field[x + 1][y + 1].isOccupied() && !field[x + 2][y + 2].isOccupied()) {
                    if (field[x + 1][y + 1].getPawn().getStoneColour() != field[x][y].getPawn().getStoneColour()) {
                        return true;
                    }
                }
            }
            if (x + 2 < size && y - 2 > 0) {
                if (field[x + 1][y - 1].isOccupied() && !field[x + 2][y - 2].isOccupied()) {
                    if (field[x + 1][y - 1].getPawn().getStoneColour() != field[x][y].getPawn().getStoneColour()) {
                        return true;
                    }
                }
            }
            if ((x - 2) > 0 && (y - 2) > 0) {
                if (field[x - 1][y - 1].isOccupied() && !field[x - 2][y - 2].isOccupied()) {
                    if (field[x - 1][y - 1].getPawn().getStoneColour() != field[x][y].getPawn().getStoneColour()) {
                        return true;
                    }
                }
            }
            if ((x - 2) > 0 && (y + 2) < size) {
                if (field[x - 1][y + 1].isOccupied() && !field[x - 2][y + 2].isOccupied()) {
                    if (field[x - 1][y + 1].getPawn().getStoneColour() != field[x][y].getPawn().getStoneColour()) {
                        return true;
                    }
                }
            }
        }
        else{
            int i=1;
            while(x+i+1<size && y+1+i<size){
                if(field[x+i][y+i].getColor()!=field[x][y].getColor()){
                    if(!field[x+i+1][y+i+1].isOccupied()){
                        return true;
                    }
                }
                else{
                    break;
                }
                i++;
            }
            i=1;
            while(x+i+1<size && y-1-i>0){
                if(field[x+i][y-i].getPawn().getStoneColour()!=field[x][y].getPawn().getStoneColour()){
                    if(!field[x+i+1][y-i-1].isOccupied()){
                        return true;
                    }
                }
                else{
                    break;
                }
                i++;
            }
            i=1;
            while(x-i-1>0 && y-1-i>0){
                if(field[x-i][y-i].getPawn().getStoneColour()!=field[x][y].getPawn().getStoneColour()){
                    if(!field[x-i-1][y-i-1].isOccupied()){
                        return true;
                    }
                }
                else{
                    break;
                }
                i++;
            }
            i=1;
            while(x-i-1>0 && y+1+i<size){
                if(field[x-i][y+i].getColor()!=field[x][y].getColor()){
                    if(!field[x-i-1][y+i+1].isOccupied()){
                        return true;

                    }
                }
                else{
                    break;
                }
                i++;
            }

        }
        return false;
    }


    //sprawdzenie czy zwykły ruch jest możliwy i dla damki tez
    @Override
    public boolean isMoveLegal(int x,int y, int m, int n) {
        if(field[x][y].getPawn()!=null) {
            if (!field[m][n].isOccupied()) {
                if (!field[x][y].getPawn().isQueen()) {
                    return (x + 1 == m && (y + 1 == n || y - 1 == n)) || (x - 1 == m && (y + 1 == n || y - 1 == n));

                } else {                                //do tej sytuacji nie dojdzie jesli będzie jakiekolwiek bicie, tzn wystarczy sprawdzic cze pola miedzy poczatkiem a konczem są puste.
                    int diffX;
                    int diffY;

                    diffX = m - x;
                    diffY = n - y;
                    if (diffY == diffX || -diffY == diffX) {
                        if (diffY > 0 && diffX > 0) {
                            for (int i = 1; i < diffX; i++) {
                                if (field[x + i][y + i].isOccupied()) {
                                    return false;
                                }
                            }
                        }
                        if (diffY > 0 && diffX < 0) {
                            for (int i = 1; i < Math.abs(diffX); i++) {
                                if (field[x - i][y + i].isOccupied()) {
                                    return false;
                                }
                            }
                        }
                        if (diffY < 0 && diffX < 0) {
                            for (int i = 1; i < Math.abs(diffX); i++) {
                                if (field[x - i][y - i].isOccupied()) {
                                    return false;
                                }
                            }
                        }
                        if (diffY < 0 && diffX > 0) {
                            for (int i = 1; i < diffX; i++) {
                                if (field[x + i][y - i].isOccupied()) {
                                    return false;
                                }
                            }
                        }
                        return true;
                    }
                }
            }
        }

        return false;
    }




    //zmiana lokalizacji pionka, juz nie trzeba sprawdzac poprawnosci
    public boolean movePawn(int x,int y,int m,int n) {
        if (field[x][y].isOccupied()) {
            field[m][n].setPawn(field[x][y].getPawn());
            field[x][y].setPawn(null);
            return true;
        }
        return false;
    }

    //bicie i dla damek tez
    public void capturePawn(int x,int y,int m,int n){           //pozycje mn dla pionka zostaly zaakceptowane przez poprzednia funkcje wiec mozna wykonac bez sprawdzenia
        if(capturePossible.contains(field[x][x])) {
            if (field[x][y].getPawn().isQueen()) {
                field[m][n].setPawn(field[x][y].getPawn());
                field[x][y].setPawn(null);
                field[(x + m) / 2][(y + n) / 2].setPawn(null);
                if (field[m][n].getColor().equals(Color.rgb(0, 0, 0))) {
                    numberOfBlackPawns--;
                } else {
                    numberOfWhitePawns--;
                }
                capturePossible.clear();
            }
            else {
                field[m][n].setPawn(field[x][y].getPawn());
                int diffX;
                int diffY;

                diffX = m - x;
                diffY = n - y;

                //w tym wypadku mamy juz pewnosc, ze nie ma zadnych pionków poza jednym (o kolorze przeciwnym ) na naszej drodze, wiec zmieniamy na null napotkany pionek.
                if (diffY == diffX || -diffY == diffX) {
                    if (diffY > 0 && diffX > 0) {
                        for (int i = 1; i < diffX; i++) {
                            if (field[x + i][y + i].isOccupied()) {
                                field[x+i][y+i].setPawn(null);
                                break;
                            }
                        }
                    }
                    if (diffY > 0 && diffX < 0) {
                        for (int i = 1; i < Math.abs(diffX); i++) {
                            if (field[x - i][y + i].isOccupied()) {
                                field[x-i][y+i].setPawn(null);
                                break;
                            }
                        }
                    }
                    if (diffY < 0 && diffX < 0) {
                        for (int i = 1; i < Math.abs(diffX); i++) {
                            if (field[x - i][y - i].isOccupied()) {
                                field[x-i][y-i].setPawn(null);
                                break;
                            }
                        }
                    }
                    if (diffY < 0 && diffX > 0) {
                        for (int i = 1; i < diffX; i++) {
                            if (field[x + i][y - i].isOccupied()) {
                                field[x+i][y-i].setPawn(null);
                                break;
                            }
                        }
                    }

                }

            }
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
