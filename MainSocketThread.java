package module;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import server.MainSocket;
import java.awt.Dimension;

public class MainSocketThread extends Thread {
    MainSocket socket;
    private boolean started;

    public MainSocketThread(MainSocket toListen) {
        setSocket(toListen);
        setStarted(started);

    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
        super.run();
        while (this.isStarted()) {

        }
    }

    @Override
    public synchronized void start() {
        // TODO Auto-generated method stub
        super.start();
        this.setStarted(true);
    }

    public void play() {
        /// frame
        JFrame frame = new JFrame();
        // settings
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(new Dimension(800, 400));
        frame.setLocationRelativeTo(null);
    }

    public MainSocket getSocket() {
        return socket;
    }

    public void setSocket(MainSocket socket) {
        this.socket = socket;
    }

    public boolean isStarted() {
        return started;
    }

    public void setStarted(boolean started) {
        this.started = started;
    }

}
