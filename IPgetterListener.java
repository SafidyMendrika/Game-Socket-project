package listener;

import java.awt.event.MouseListener;

import javax.swing.JTextField;

import java.awt.event.MouseEvent;

public class IPgetterListener implements MouseListener {
    JTextField field;
    String ipGetted;

    public IPgetterListener(JTextField field) {
        this.setField(field);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // TODO Auto-generated method stub
        this.setIpGetted(this.getField().getText());

        if (isValidIp(String.valueOf(this.getIpGetted().toCharArray()))) {
            System.out.println(this.getIpGetted());
        } else {
            System.out.println("invalid");
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

}
