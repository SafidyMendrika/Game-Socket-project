package module;

import server.MainServer;

public class GameInteractionThread extends Thread {
    MainServer server;
    private boolean started;

    public GameInteractionThread(MainServer server) {
        super();
        this.setServer(server);
    }

    public boolean isStarted() {
        return started;
    }

    public void setStarted(boolean started) {
        this.started = started;
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
        super.run();
        while (this.isStarted()) {
            System.out.println("traitement : users " + this.getServer().getClients().size());
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
}
