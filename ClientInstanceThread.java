package module;

import java.net.Socket;

import module.GameInteractionThread;
import server.MainServer;
import server.MainSocket;

public class ClientInstanceThread extends Thread {
    MainServer server;
    private boolean isStarted;

    public ClientInstanceThread(MainServer server) throws Exception {
        setStarted(true);
        setServer(server);
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
        super.run();
        GameInteractionThread git = new GameInteractionThread(this.getServer());
        try {
            Socket client = null;
            git.start();
            while (this.isStarted()) {
                client = this.getServer().accept();
                if (client != null) {
                    this.getServer().getClients().add(client);
                }
                this.getServer().treatUsers();
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    @Override
    public synchronized void start() {
        // TODO Auto-generated method stub
        super.start();
        this.setStarted(true);
    }

    public MainServer getServer() {
        return server;
    }

    public void setServer(MainServer server) {
        this.server = server;
    }

    public boolean isStarted() {
        return isStarted;
    }

    public void setStarted(boolean isStarted) {
        this.isStarted = isStarted;
    }

}
