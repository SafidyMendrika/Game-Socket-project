package listener;

import java.awt.event.MouseListener;
import java.net.InetAddress;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JTextField;

import server.MainSocket;

import java.awt.event.MouseEvent;

public class IPgetterListener implements MouseListener {
    JTextField field;
    String ipGetted;
    JFrame toClose;

    public IPgetterListener(JTextField field, JFrame toClose) {
        this.setField(field);
        this.setToClose(toClose);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // TODO Auto-generated method stub
        this.setIpGetted(this.getField().getText());
        this.getToClose().setVisible(false);
        try {

            if (isValidIp(String.valueOf(this.getIpGetted().toCharArray()))) {
                MainSocket client = new MainSocket(this.getIpGetted(), 1899);
            } else {
                MainSocket client = new MainSocket(InetAddress.getLocalHost(), 1899);
            }
        } catch (Exception a) {
            // MainSocket client = new MainSocket(new Socket(InetAddress.getLocalHost(),
            // 1899));
            a.printStackTrace();
            // TODO: handle exception
        }
    }

    private static boolean isValidIp(String ip) {
        int countIp = ip.toString().split(".").length;
        // if (countIp != 4)
        // return false;

        String alphabet = "abcdefghijklmnopqrstuvwxyz";
        char[] eachLetter = alphabet.toCharArray();
        for (int i = 0; i < eachLetter.length; i++) {
            if (ip.contains(String.valueOf(eachLetter[i]))
                    || ip.contains(String.valueOf(eachLetter[i]).toUpperCase())) {
                return false;
            }
        }

        return true;

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

    public JTextField getField() {
        return field;
    }

    public void setField(JTextField field) {
        this.field = field;
    }

    public String getIpGetted() {
        return ipGetted;
    }

    public void setIpGetted(String ipGetted) {
        this.ipGetted = ipGetted;
    }

    public JFrame getToClose() {
        return toClose;
    }

    public void setToClose(JFrame toClose) {
        this.toClose = toClose;
    }

}
