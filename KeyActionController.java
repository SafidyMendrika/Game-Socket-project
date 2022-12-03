package controller;

import java.awt.event.KeyListener;

import module.Canvas;
import server.MainSocket;

import java.awt.event.KeyEvent;

public class KeyActionController implements KeyListener {
    MainSocket socket;

    public KeyActionController(MainSocket docket) {
        this.setSocket(docket);
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub
    }

    @Override
    public void keyPressed(KeyEvent e) {
        System.out.println(e.getKeyText(e.getKeyCode()));
        // this.getSocket().sendMouvementData(e.getKeyText(e.getKeyCode()));
        this.getSocket().setAction(e.getKeyText(e.getKeyCode()));
        // TODO Auto-generated method stub

    }

    @Override
    public void keyReleased(KeyEvent e) {
        // TODO Auto-generated method stub

    }

    public MainSocket getSocket() {
        return socket;
    }

    public void setSocket(MainSocket socket) {
        this.socket = socket;
    }

}
