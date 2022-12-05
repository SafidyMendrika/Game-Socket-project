package server;

import java.util.ArrayList;

import module.ClientInstanceThread;
import module.GameInteractionThread;
import server.MainSocket;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class MainServer extends ServerSocket {
    ArrayList clients;
    ArrayList clientsPosition;

    public MainServer() throws Exception {
    }

    public MainServer(int port) throws Exception {
        super(port);
        this.setClients(new ArrayList<>());
        // this.setClientsPosition(new ArrayList<String>());
        // this.setInputStreamList(new ArrayList<>());

        ClientInstanceThread clientChecker = new ClientInstanceThread(this);
        clientChecker.start();

        GameInteractionThread git = new GameInteractionThread(this); // do te treatmet of the clients
                                                                     // actions
        git.start();
    }

    public void treatUsers() {
        Socket socket = null;

        // ObjectInputStream ois = null;
        // ObjectOutputStream oos = null;
        for (int i = 0; i < this.getClients().size(); i++) {
            socket = (Socket) this.getClients().get(i);

            // ois = new ObjectInputStream(socket.getInputStream());
            // oos = new ObjectOutputStream(socket.getOutputStream());
            System.out.println(" client :" + socket);
        }
        System.out.println("\n");
    }

    public ArrayList getClients() {
        return clients;
    }

    public void setClients(ArrayList clients) {
        this.clients = clients;
    }

    public ArrayList getClientsPosition() {
        return clientsPosition;
    }

    public void setClientsPosition(ArrayList clientsPosition) {
        this.clientsPosition = clientsPosition;
    }

}
