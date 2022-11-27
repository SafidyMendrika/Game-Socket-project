package server;

import java.util.ArrayList;

import module.ClientInstanceThread;
import server.MainSocket;

import java.net.ServerSocket;
import java.net.Socket;

public class MainServer extends ServerSocket {
    ArrayList clients;

    public MainServer() throws Exception {
    }

    public MainServer(int port) throws Exception {
        super(port);
        this.setClients(new ArrayList<>());

        System.out.println("creation du server");
        ClientInstanceThread clientChecker = new ClientInstanceThread(this);
        clientChecker.start();

        System.out.println("server cree");
    }

    public void treatUsers() {
        Socket socket = null;
        MainSocket mainSocketObject = null;

        for (int i = 0; i < this.getClients().size(); i++) {

            mainSocketObject = (MainSocket) this.getClients().get(i);
            socket = mainSocketObject.getSocket();

            System.out.println("client(" + i + ") : " + socket);
        }
        System.out.println("\n");
    }

    public ArrayList getClients() {
        return clients;
    }

    public void setClients(ArrayList clients) {
        this.clients = clients;
    }
}
