package server;

import java.net.Socket;

import javax.swing.JFrame;

import module.MainSocketThread;
import module.Canvas;

import java.awt.Dimension;

public class MainSocket {
    Socket socket;

    public MainSocket(Socket socket) {
        setSocket(socket);

        JFrame test = new JFrame("FortNight");
        test.setSize(new Dimension(500, 500));
        test.setLocationRelativeTo(null);
        test.setVisible(true);
        MainSocketThread myThread = new MainSocketThread(this, test);
        myThread.start();
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

}
