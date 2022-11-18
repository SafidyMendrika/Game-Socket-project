package engine;

import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import server.MainServer;

public class Game {
    Socket connector;

    public Game(){
        checkConnection();
    }
    public void checkConnection(){
        try {
            Socket mySocket = new Socket(InetAddress.getLocalHost(), 1899);
            
            this.setConnector(mySocket);
        } catch (Exception e) {
            System.out.println("server innexitante");
            try {
                MainServer server = new MainServer(1899);
                this.checkConnection();
            } catch (Exception a) {
                a.printStackTrace();
            }
        }
        System.out.println("connection checked");
        
    }



    public Socket getConnector() {
        return connector;
    }

    public void setConnector(Socket connector) {
        this.connector = connector;
    }
} 
