package Model;

import java.util.ArrayList;
import java.util.Set;

public class HibernateGame {
    int id;
    String gameVariant;
    Set<HibernateMove> moves;
    public HibernateGame(String gameVariant){
        this.gameVariant = gameVariant;
    }

    public HibernateGame() {

    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Set<HibernateMove> getMoves() {
        return moves;
    }

    public void setMoves(Set<HibernateMove> moves) {
        this.moves = moves;
    }

    public String getGameVariant() {
        return gameVariant;
    }

    public void setGameVariant(String gameVariant) {
        this.gameVariant = gameVariant;
    }
}
