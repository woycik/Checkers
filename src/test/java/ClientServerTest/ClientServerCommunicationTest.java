package ClientServerTest;

import Controller.ClientThread;
import Controller.ServerThread;
import View.ServerView;
import org.junit.Before;

public class ClientServerCommunicationTest {
    private ServerThread serverThread;
    private ClientThread clientThread;

    @Before
    public void initializeController() {
        serverThread = new ServerThread(4444, new ServerView());
        clientThread = new ClientThread();
    }
}
