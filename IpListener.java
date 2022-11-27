package listener;

import javax.swing.JFrame;
import javax.swing.JTextField;

import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;

public class IpListener implements MouseListener {
    JTextField IpValue;
    String returnValue;
    JFrame toClose;

    public IpListener(JTextField IpValue, String returnValue, JFrame toClose) {
        setIpValue(IpValue);
        setReturnValue(returnValue);
        setToClose(toClose);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // TODO Auto-generated method stub

        this.setReturnValue(IpValue.getText());
        System.out.println(IpValue.getText());
        toClose.setVisible(false);
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

    public JTextField getIpValue() {
        return IpValue;
    }

    public void setIpValue(JTextField ipValue) {
        IpValue = ipValue;
    }

    public String getReturnValue() {
        return returnValue;
    }

    public void setReturnValue(String returnValue) {
        this.returnValue = returnValue;
    }

    public JFrame getToClose() {
        return toClose;
    }

    public void setToClose(JFrame toClose) {
        this.toClose = toClose;
    }

}
