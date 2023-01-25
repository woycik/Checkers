package View;

import Controller.Client;
import Controller.ClientThread;
import Model.HibernateGame;
import Model.HibernateMove;
import Model.HibernateUtil;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import java.util.List;

public class BoardEventHandler implements EventHandler<MouseEvent> {
    private ClientThread client;
    SessionFactory sf;
    List<Object[]> list;
    int n = 0;


    BoardEventHandler(ClientThread client) {
        this.client = client;
        this.hibernateReques();
    }


    private void hibernateReques() {
        sf = HibernateUtil.getSessionFactory();
        if (sf == null) {
            System.out.println("Error: Initialize database from the file db.sql");
            return;
        }
        Session session = sf.openSession();
        Transaction tx = session.beginTransaction();
        list = session.createQuery("select beginX,beginY,endX,endY from Model.HibernateMove WHERE gameId IN (select max(id) from Model.HibernateGame)  ORDER BY moveNumber").getResultList();
        tx.commit();
    }

    @Override
    public void handle(MouseEvent event) {
        if (event.getEventType() == MouseEvent.MOUSE_CLICKED) {
            if (n < list.size()) {
                client.makeMove(Integer.parseInt(list.get(n)[0].toString()), Integer.parseInt(list.get(n)[1].toString()), Integer.parseInt(list.get(n)[2].toString()), Integer.parseInt(list.get(n)[3].toString()));
                n++;
            }
        }
    }
}

