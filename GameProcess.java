package launcher;

import java.awt.event.MouseListener;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import listener.IPgetterListener;
import server.MainServer;
import server.MainSocket;

import java.awt.GridLayout;
import java.awt.Dimension;

import java.awt.event.MouseEvent;

public class GameProcess implements MouseListener {
    JFrame toClose;

    public GameProcess() {
    }

    public GameProcess(JFrame toClose) {
        setToClose(toClose);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // TODO Auto-generated method stub
        JButton src = (JButton) e.getSource();
        if (e.isAltDown() && e.isControlDown()) {
            this.getToClose().setVisible(false);
            
            if (src.getName().equals("host")) {
                try {
                    MainServer server = new MainServer(1899);
                    MainSocket client = new MainSocket(InetAddress.getLocalHost(), 1899);

                } catch (Exception ex) {
                    ex.printStackTrace();
                    // TODO: handle exception
                }
            } else {
                askForIp();
            }
        }
    }

    public void askForIp() {
        JFrame askIpFrame = new JFrame();
        askIpFrame.setSize(new Dimension(200, 100));
        askIpFrame.setLayout(new GridLayout(3, 1));
        askIpFrame.setLocationRelativeTo(null);

        JLabel label = new JLabel("enter host ip");
        label.setAlignmentX(SwingConstants.CENTER);

        askIpFrame.add(label);

        JTextField field = new JTextField();

        askIpFrame.add(field);

        JButton submit = new JButton();

        askIpFrame.add(submit);

        IPgetterListener ipGetterListener = new IPgetterListener(field, askIpFrame);
        submit.addMouseListener(ipGetterListener);

        askIpFrame.setVisible(true);

    }

    @Override
    public void mousePressed(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    public JFrame getToClose() {
        return toClose;
    }

    public void setToClose(JFrame toClose) {
        this.toClose = toClose;
    }

}
