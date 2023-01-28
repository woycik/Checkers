package View;

import Controller.ClientThread;
import Model.HibernateUtil;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class BoardEventHandler implements EventHandler<MouseEvent> {
    private final ClientThread client;
    private List<Object[]> movesList;
    private int moveNumber;

    BoardEventHandler(ClientThread client) {
        moveNumber = 0;
        this.client = client;
        this.hibernateRequest();
    }

    private void hibernateRequest() {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        if (sessionFactory == null) {
            System.out.println("Session Factory is null.");
            return;
        }
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        movesList = session.createQuery("select beginX,beginY,endX,endY from Model.HibernateMove " +
                "WHERE gameId IN (select max(id) from Model.HibernateGame) " +
                "ORDER BY moveNumber").getResultList();
        tx.commit();
    }

    @Override
    public void handle(MouseEvent event) {
        if (event.getEventType() == MouseEvent.MOUSE_CLICKED) {
            if (moveNumber < movesList.size()) {
                client.makeMove(Integer.parseInt(movesList.get(moveNumber)[0].toString()),
                        Integer.parseInt(movesList.get(moveNumber)[1].toString()),
                        Integer.parseInt(movesList.get(moveNumber)[2].toString()),
                        Integer.parseInt(movesList.get(moveNumber)[3].toString())
                );
                moveNumber++;
            }
        }
    }
}

