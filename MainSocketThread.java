package module;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import module.Canvas;
import server.MainSocket;
import java.awt.Dimension;
import java.awt.Robot;
import java.io.DataOutputStream;
import java.awt.Rectangle;

public class MainSocketThread extends Thread {
    MainSocket socket;
    JFrame container;
    private boolean started;

    public MainSocketThread(MainSocket toListen, JFrame container) {
        setSocket(toListen);
        setContainer(container);
    }

    @Override
    public void run() {
        super.run();
        DataOutputStream dos;
        try {

            while (this.isStarted()) {
                dos = new DataOutputStream(this.getSocket().getOutputStream());

                System.out.println(this.getSocket().getAction() + " act");
                if (this.getSocket().getAction() != null) {
                    dos.writeUTF(this.getSocket().getAction());
                    this.getSocket().setAction(null);
                } else {
                    dos.writeUTF("");
                }
                dos.flush();
                Thread.sleep(500);
            }

        } catch (Exception e) {
            e.printStackTrace();
            // TODO: handle exception
        }
    }

    @Override
    public void start() {
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

    public JFrame getContainer() {
        return container;
    }

    public void setContainer(JFrame container) {
        this.container = container;
    }

}
