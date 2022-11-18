package server;

import java.util.ArrayList;

import module.ClientInstanceThread;

import java.net.ServerSocket;

public class MainServer extends ServerSocket{
    ArrayList clients;

    public MainServer()throws Exception{}

    public MainServer(int port)throws Exception{
        super(port);
        this.setClients(new ArrayList<>());

        System.out.println("creation du server");
        ClientInstanceThread clientChecker = new ClientInstanceThread(this);
        clientChecker.start();

        System.out.println("server cree");
    }

    public ArrayList getClients() {
        return clients;
    }

    public void setClients(ArrayList clients) {
        this.clients = clients;
    }
}
