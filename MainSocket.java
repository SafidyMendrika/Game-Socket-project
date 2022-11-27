package server;

import java.net.Socket;

import module.MainSocketThread;

public class MainSocket extends Socket {
    Socket socket;

    public MainSocket(Socket socket) {
        setSocket(socket);

        MainSocketThread myThread = new MainSocketThread(this);
        myThread.start();
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }
}
