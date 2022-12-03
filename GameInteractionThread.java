package module;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.io.OutputStreamWriter;
import java.io.IOException;

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
        super.run();
        ArrayList clientList = null;
        // ArrayList inputStreamList = null;
        Socket eachClient = null;
        try {

            System.out.println("runned");
            String getted = "";
            DataInputStream dis = null;
            while (this.isStarted()) {
                clientList = this.getServer().getClients();
                getted = "";

                for (int i = 0; i < clientList.size(); i++) {
                    eachClient = (Socket) clientList.get(i);

                    dis = new DataInputStream(eachClient.getInputStream());

                    getted += i + "::" + dis.readUTF() + "// ";
                    // bufIn = new BufferedReader(new
                    // InputStreamReader(eachClient.getInputStream()));
                    // System.out.println("miandry 1 " + i);
                    // try {
                    // String msg = bufIn.read();
                    // System.out.println("miandry 2" + i);

                    // System.out.println("Received: " + msg);
                    // getted += i + "::" + msg;
                    // } catch (IOException e) {
                    // e.printStackTrace();
                    // }

                    // }
                    // System.out.println("getted : " + getted);

                    // System.out.println("while be foana " + ikk);
                }
                Thread.sleep(500);
                System.out.println("got : " + getted);
            }
        } catch (Exception e) {
            e.printStackTrace();
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
}