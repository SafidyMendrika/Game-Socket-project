package launcher;

import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import listener.IPgetterListener;

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
        this.getToClose().setVisible(false);
        if (src.getName().equals("host")) {
            System.out.println("go");
        } else {
            String askedIp = null;
            askForIp(askedIp);

            System.out.println(askedIp);
        }
    }

    public void askForIp(String ip) {
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

        IPgetterListener ipGetterListener = new IPgetterListener(field);
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
