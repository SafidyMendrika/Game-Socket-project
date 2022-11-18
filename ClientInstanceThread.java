package module;


import server.MainServer;

public class ClientInstanceThread extends Thread {
    MainServer server;
    private boolean isStarted;
    
    public ClientInstanceThread(MainServer server)throws Exception{

    }

    
    @Override
    public void run() {
        // TODO Auto-generated method stub
        super.run();
        while (this.isStarted()) {
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
