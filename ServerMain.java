package run;

import server.MainServer;

public class ServerMain {
    public static void main(String[] args) {
        try {
            new MainServer(1899);
        } catch (Exception e) {
            e.printStackTrace();
            // TODO: handle exception
        }
    }
}
