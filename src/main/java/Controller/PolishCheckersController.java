package Controller;

import Model.Board;
import Model.Field;

import javafx.scene.paint.Color;

import java.util.ArrayList;

public class PolishCheckersController extends GameController {
    private Field[][] fields;
    private ArrayList<Field> blackPawns;
    private ArrayList<Field> whitePawns;
    private ArrayList<Field> capturePossible; //lista pól w które może wskoczyc pionek w ramach bicia
    private final int size=10;
    boolean dokonczBicie=false;
    private int numberOfWhitePawns=20;
    private int numberOfBlackPawns=20;

    public PolishCheckersController() {
        super(10, 4);
        fields=board.getFields();
        blackPawns = new ArrayList<>();
        whitePawns = new ArrayList<>();
        capturePossible = new ArrayList<>();
    }

    public boolean play(int x, int y,int i, int j,String color) {
        this.setMyPawns();                                                                       //poznajemy położenie pionków
        if(!dokonczBicie) {
            if (color.equals("BLACK")) {this.captureFieldList(blackPawns);}                     //zapisz czarne pola z których mozliwe jest bicie}          
            else {this.captureFieldList(whitePawns);}                                                                           //zapisz biale pola z których mozliwe jest bicie
            if (this.isCapturePossible()) {                                                     //sprawdz czy mozliwe jest bicie dla (bialego/czarnego)
                                                                                                //czy doszloby do wykonania bicia?
                if (this.checkCapture(x, y, i, j)) {                                            //zapisz pola na które może wybrany pionek wskoczyc po wykonaniu bicia i sprawdz czy ten pionek nalezy do listy
                    this.capturePawn(x, y, i, j); //jak tak to zbij
                    this.capturePossible.clear();
                    if (this.canICaptureOneMoreTime(i, j)) {
                        capturePossible.add(fields[i][j]);
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
                    capturePossible.add(fields[i][j]);
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
    //przypisanie do listy capturePossible pol  z których mozliwe jest bicie w ogólnosci i dla queen tez
    public void captureFieldList(ArrayList<Field> typeOfPawns) {
        for (Field boardField : typeOfPawns) {
            int x = boardField.getX();
            int y = boardField.getY();

            if(!fields[x][y].getPawn().isQueen()) {
                if ((x + 2 < size) && (y + 2) < size) {
                    if (fields[x + 1][y + 1].isOccupied() && !fields[x + 2][y + 2].isOccupied()) {
                        if (fields[x + 1][y + 1].getPawn().getStoneColour() != fields[x][y].getPawn().getStoneColour()) {
                            capturePossible.add(boardField);
                        }
                    }
                }
                if ((x + 2 < size) && (y - 2) > 0) {
                    if (fields[x + 1][y - 1].isOccupied() && !fields[x + 2][y - 2].isOccupied()) {
                        if (fields[x + 1][y - 1].getPawn().getStoneColour() != fields[x][y].getPawn().getStoneColour()) {
                            capturePossible.add(boardField);
                        }
                    }
                }
                if ((x - 2) > 0 && (y - 2) > 0) {
                    if (fields[x - 1][y - 1].isOccupied() && !fields[x - 2][y - 2].isOccupied()) {
                        if (fields[x - 1][y - 1].getPawn().getStoneColour() != fields[x][y].getPawn().getStoneColour()) {
                            capturePossible.add(boardField);
                        }
                    }
                }
                if ((x - 2) > 0 && (y + 2) < size) {
                    if (fields[x - 1][y + 1].isOccupied() && !fields[x - 2][y + 2].isOccupied()) {
                        if (fields[x - 1][y + 1].getPawn().getStoneColour() != fields[x][y].getPawn().getStoneColour()) {
                            capturePossible.add(boardField);
                        }
                    }
                }
            }
            else{

                int i=1;
                while(x+i+1<size && y+1+i<size){
                    if(fields[x+i][y+i].getColor()!=fields[x][y].getColor()){
                        if(!fields[x+i+1][y+i+1].isOccupied()){
                            if(capturePossible.contains(fields[x][y])) {
                                capturePossible.add(fields[x][y]);
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
                    if(fields[x+i][y-i].getPawn().getStoneColour()!=fields[x][y].getPawn().getStoneColour()){
                        if(!fields[x+i+1][y-i-1].isOccupied()){
                            if(!capturePossible.contains(fields[x][y])) {
                                capturePossible.add(fields[x][y]);
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
                    if(fields[x-i][y-i].getPawn().getStoneColour()!=fields[x][y].getPawn().getStoneColour()){
                        if(!fields[x-i-1][y-i-1].isOccupied()){
                            if(!capturePossible.contains(fields[x][y])) {
                                capturePossible.add(fields[x][y]);
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
                    if(fields[x-i][y+i].getColor()!=fields[x][y].getColor()){
                        if(!fields[x-i-1][y+i+1].isOccupied()){
                            if(!capturePossible.contains(fields[x][y])) {
                                capturePossible.add(fields[x][y]);
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
        if(capturePossible.contains(fields[x][y])){
            if(!fields[x][y].getPawn().isQueen()){
                if(Math.abs(x-m)==2 && Math.abs(y-n)==2 && fields[(x+m)/2][(y+n)/2].isOccupied() && !fields[m][n].isOccupied()){
                    return fields[(x + m) / 2][(y + n) / 2].getColor() != fields[x][y].getColor();
                }
            }
            else{
                if(!fields[m][n].isOccupied()){
                    int diffX;
                    int diffY;

                    diffX =m - x;
                    diffY =n - y;
                    int count=0;
                    if(diffY==diffX || -diffY==diffX){
                        if(diffY>0 && diffX>0){
                            for(int i=1;i<diffX;i++){
                                if(fields[x+i][y+1].getColor()!=fields[x][y].getColor()){
                                    count++;
                                }
                            }
                        }
                        if(diffY>0 && diffX<0){
                            for(int i=1;i<Math.abs(diffX);i++){
                                if(fields[x-i][y+i].getColor()!=fields[x][y].getColor()){
                                    count++;
                                }
                            }
                        }
                        if(diffY<0 && diffX<0){
                            for(int i=1;i<Math.abs(diffX);i++){
                                if(fields[x-i][y-i].getColor()!=fields[x][y].getColor()){
                                    count++;
                                }
                            }
                        }
                        if(diffY<0 && diffX>0){
                            for(int i=1;i<diffX;i++){
                                if(fields[x-i][y+1].getColor()!=fields[x][y].getColor()){
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
        if(!fields[x][y].getPawn().isQueen()) {
            if (x + 2 < size && y + 2 < size) {
                if (fields[x + 1][y + 1].isOccupied() && !fields[x + 2][y + 2].isOccupied()) {
                    if (fields[x + 1][y + 1].getPawn().getStoneColour() != fields[x][y].getPawn().getStoneColour()) {
                        return true;
                    }
                }
            }
            if (x + 2 < size && y - 2 > 0) {
                if (fields[x + 1][y - 1].isOccupied() && !fields[x + 2][y - 2].isOccupied()) {
                    if (fields[x + 1][y - 1].getPawn().getStoneColour() != fields[x][y].getPawn().getStoneColour()) {
                        return true;
                    }
                }
            }
            if ((x - 2) > 0 && (y - 2) > 0) {
                if (fields[x - 1][y - 1].isOccupied() && !fields[x - 2][y - 2].isOccupied()) {
                    if (fields[x - 1][y - 1].getPawn().getStoneColour() != fields[x][y].getPawn().getStoneColour()) {
                        return true;
                    }
                }
            }
            if ((x - 2) > 0 && (y + 2) < size) {
                if (fields[x - 1][y + 1].isOccupied() && !fields[x - 2][y + 2].isOccupied()) {
                    if (fields[x - 1][y + 1].getPawn().getStoneColour() != fields[x][y].getPawn().getStoneColour()) {
                       return true;
                    }
                }
            }
        }
        else{
            int i=1;
            while(x+i+1<size && y+1+i<size){
                if(fields[x+i][y+i].getColor()!=fields[x][y].getColor()){
                    if(!fields[x+i+1][y+i+1].isOccupied()){
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
                if(fields[x+i][y-i].getPawn().getStoneColour()!=fields[x][y].getPawn().getStoneColour()){
                    if(!fields[x+i+1][y-i-1].isOccupied()){
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
                if(fields[x-i][y-i].getPawn().getStoneColour()!=fields[x][y].getPawn().getStoneColour()){
                    if(!fields[x-i-1][y-i-1].isOccupied()){
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
                if(fields[x-i][y+i].getColor()!=fields[x][y].getColor()){
                    if(!fields[x-i-1][y+i+1].isOccupied()){
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
        if(fields[x][y].getPawn()!=null) {
            if (!fields[m][n].isOccupied()) {
                if (!fields[x][y].getPawn().isQueen()) {
                    return (x + 1 == m && (y + 1 == n || y - 1 == n)) || (x - 1 == m && (y + 1 == n || y - 1 == n));

                } else {                                //do tej sytuacji nie dojdzie jesli będzie jakiekolwiek bicie, tzn wystarczy sprawdzic cze pola miedzy poczatkiem a konczem są puste.
                    int diffX;
                    int diffY;

                    diffX = m - x;
                    diffY = n - y;
                    if (diffY == diffX || -diffY == diffX) {
                        if (diffY > 0 && diffX > 0) {
                            for (int i = 1; i < diffX; i++) {
                                if (fields[x + i][y + i].isOccupied()) {
                                    return false;
                                }
                            }
                        }
                        if (diffY > 0 && diffX < 0) {
                            for (int i = 1; i < Math.abs(diffX); i++) {
                                if (fields[x - i][y + i].isOccupied()) {
                                    return false;
                                }
                            }
                        }
                        if (diffY < 0 && diffX < 0) {
                            for (int i = 1; i < Math.abs(diffX); i++) {
                                if (fields[x - i][y - i].isOccupied()) {
                                    return false;
                                }
                            }
                        }
                        if (diffY < 0 && diffX > 0) {
                            for (int i = 1; i < diffX; i++) {
                                if (fields[x + i][y - i].isOccupied()) {
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
        if (fields[x][y].isOccupied()) {
            fields[m][n].setPawn(fields[x][y].getPawn());
            fields[x][y].setPawn(null);
            return true;
        }
        return false;
    }

    //bicie i dla damek tez
    public void capturePawn(int x,int y,int m,int n){           //pozycje mn dla pionka zostaly zaakceptowane przez poprzednia funkcje wiec mozna wykonac bez sprawdzenia
            if(capturePossible.contains(fields[x][x])) {
                if (fields[x][y].getPawn().isQueen()) {
                    fields[m][n].setPawn(fields[x][y].getPawn());
                    fields[x][y].setPawn(null);
                    fields[(x + m) / 2][(y + n) / 2].setPawn(null);
                    if (fields[m][n].getColor().equals(Color.rgb(0, 0, 0))) {
                        numberOfBlackPawns--;
                    } else {
                        numberOfWhitePawns--;
                    }
                    capturePossible.clear();
                }
                else {
                    fields[m][n].setPawn(fields[x][y].getPawn());
                    int diffX;
                    int diffY;

                    diffX = m - x;
                    diffY = n - y;

                    //w tym wypadku mamy juz pewnosc, ze nie ma zadnych pionków poza jednym (o kolorze przeciwnym ) na naszej drodze, wiec zmieniamy na null napotkany pionek. 
                    if (diffY == diffX || -diffY == diffX) {                
                        if (diffY > 0 && diffX > 0) {
                            for (int i = 1; i < diffX; i++) {
                                if (fields[x + i][y + i].isOccupied()) {
                                    fields[x+i][y+i].setPawn(null);
                                    break;
                                }
                            }
                        }
                        if (diffY > 0 && diffX < 0) {
                            for (int i = 1; i < Math.abs(diffX); i++) {
                                if (fields[x - i][y + i].isOccupied()) {
                                    fields[x-i][y+i].setPawn(null);
                                    break;
                                }
                            }
                        }
                        if (diffY < 0 && diffX < 0) {
                            for (int i = 1; i < Math.abs(diffX); i++) {
                                if (fields[x - i][y - i].isOccupied()) {
                                    fields[x-i][y-i].setPawn(null);
                                    break;
                                }
                            }
                        }
                        if (diffY < 0 && diffX > 0) {
                            for (int i = 1; i < diffX; i++) {
                                if (fields[x + i][y - i].isOccupied()) {
                                    fields[x+i][y-i].setPawn(null);
                                    break;
                                }
                            }
                        }
                        
                    }
                    
                }
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
