package Controller;

public class ClientThread extends Thread {
    int port;

    public ClientThread(int port) {
        this.port = port;
    }

    @Override
    public void run() {
        System.out.println("Polaczono z serverem na porcie " + port);
    }
}