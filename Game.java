package engine;

import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import java.awt.Dimension;
import java.awt.GridLayout;

import server.MainServer;

import server.MainSocket;
import listener.IpListener;

public class Game {
    // Socket connector;
    // ServerSocket mainServer;

    public Game(InetAddress ip) {
        // checkConnection(ip);
    }

    public void checkConnection(InetAddress ip) {

        try {

        } catch (Exception e) {
            // TODO: handle exception
        }

        // try {
        // Socket mySocket = null;
        // if (this.isMyServer()) {
        // mySocket = new Socket(InetAddress.getLocalHost(), 1899);
        // } else {
        // String ip = askForIP();
        // mySocket = new Socket(ip, 1899);
        // }
        // this.setConnector(new MainSocket(mySocket));
        // } catch (Exception e) {
        // // TODO: handle exception
        // if (this.getMainServer() != null) {

        // } else {
        // System.out.println("server innexitante");
        // try {
        // MainServer server = new MainServer(1899);
        // setMainServer(server);
        // setMyServer(true);

        // this.checkConnection();
        // } catch (Exception a) {
        // a.printStackTrace();
        // }

        // System.out.println("connection checked");

        // }

        // }

    }

    // public String askForIP() {
    // String ip = null;
    // JFrame frame = new JFrame();
    // frame.setSize(new Dimension(200, 200));
    // frame.setLayout(new GridLayout(2, 1));
    // frame.setLocationRelativeTo(null);
    // frame.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);

    // frame.add(new JLabel("enter ip : "));

    // JPanel areaPanel = new JPanel();
    // areaPanel.setLayout(new GridLayout(1, 2));
    // JTextField ipField = new JTextField();

    // JButton submit = new JButton("submit");
    // submit.addMouseListener(new IpListener(ipField, ip, frame));

    // areaPanel.add(ipField);
    // areaPanel.add(submit);
    // frame.add(areaPanel);

    // frame.setVisible(true);
    // return ip;
    // }

    // public Socket getConnector() {
    // return connector;
    // }

    // public void setConnector(Socket connector) {
    // this.connector = connector;
    // }

    // public ServerSocket getMainServer() {
    // return mainServer;
    // }

    // public void setMainServer(ServerSocket mainServer) {
    // this.mainServer = mainServer;
    // }

    // public boolean isMyServer() {
    // return myServer;
    // }

    // public void setMyServer(boolean myServer) {
    // this.myServer = myServer;
    // }
}
