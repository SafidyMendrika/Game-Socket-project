package server;

import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import controller.KeyActionController;
import engine.Player;
import module.MainSocketThread;
import module.Canvas;

import java.awt.Dimension;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;

public class MainSocket extends Socket {
    Canvas canvas;
    Player player;
    String action;

    public MainSocket(String host, int port) throws UnknownHostException, IOException {
        super(host, port);
        this.setAction(null);
        instead();
    }

    public MainSocket(InetAddress address, int port) throws UnknownHostException, IOException {
        super(address, port);
        instead();
    }

    private void instead() {

        JFrame frame = new JFrame("Chass");
        frame.setSize(new Dimension(800, 600));
        frame.setResizable(false);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        Canvas canvas = new Canvas(frame);
        this.setCanvas(canvas);
        frame.add(canvas);

        // controller
        KeyActionController kac = new KeyActionController(this);
        frame.setFocusable(true);
        frame.addKeyListener(kac);
        //
        frame.setVisible(true);

        MainSocketThread myThread = new MainSocketThread(this, frame);
        myThread.start();
    }

    public void sendMouvementData(String action) {
        try {

            // BufferedWriter a = new BufferedWriter(new
            // OutputStreamWriter(this.getOutputStream()));

            // a.write(action);
            // a.write("\n");
            // a.flush();
            DataOutputStream dos = new DataOutputStream(this.getOutputStream());

            dos.writeUTF(action);

            dos.flush();

        } catch (

        Exception e) {
            e.printStackTrace();
        }
    }

    public Canvas getCanvas() {
        return canvas;
    }

    public void setCanvas(Canvas canvas) {
        this.canvas = canvas;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

}
