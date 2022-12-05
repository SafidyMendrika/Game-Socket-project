package module;

import javax.swing.JFrame;
import javax.swing.JPanel;

import engine.Player;

import java.awt.Color;
import java.awt.Graphics;

import java.util.ArrayList;
import java.util.Random;

public class Canvas extends JPanel {
    String action;
    ArrayList playersLocation;
    int lastPlayersCount = 0;

    public Canvas(JFrame container) { // set player
        super();
        this.setSize(container.getSize());
        this.setLocation(0, 0);
        this.setBackground(new Color(50, 50, 50));
        this.setPlayersLocation(new ArrayList<int[]>());
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        g.setColor(new Color(255, 255, 255));

        drawPlayers(g);
        try {
            Thread.sleep(1);
        } catch (Exception e) {
            // TODO: handle exception
        }
        repaint();
    }

    public void drawPlayers(Graphics g) {
        int[] temp = null;
        for (int i = 0; i < this.getPlayersLocation().size(); i++) {
            temp = (int[]) this.getPlayersLocation().get(i);

            // System.out.println("loc : " + temp[0] + " , " + temp[1]);
            g.fillRect(temp[0], temp[1], 25, 25);
        }
    }

    public void treatMoveResponse(String action) {
        String[] clientsAction = action.split("//");

        while (lastPlayersCount != clientsAction.length) {
            instanceNewPLayer();
            // System.out.println("new instance");
            lastPlayersCount++;
        }

        int[] temp = null;
        this.getPlayersLocation().clear();
        for (int i = 0; i < clientsAction.length; i++) {
            temp = coordinize(clientsAction[i]);
            this.getPlayersLocation().add(i, temp);
        }
    }

    public void instanceNewPLayer() {
        int[] newPlayerLocation = new int[2];

        this.getPlayersLocation().add(newPlayerLocation);

    }

    public int[] coordinize(String str) { // ex : (x,y)
        String[] splited = str.split(",");

        int x = Integer.parseInt(String.valueOf(splited[0].substring(1)));
        int y = Integer.parseInt(String.valueOf(splited[1].substring(0, splited[1].length() - 1)));

        int[] result = new int[2];
        result[0] = x;
        result[1] = y;
        // System.out.println("coordinize : " + result[0] + " , " + result[1]);

        return result;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public ArrayList getPlayersLocation() {
        return playersLocation;
    }

    public void setPlayersLocation(ArrayList playersLocation) {
        this.playersLocation = playersLocation;
    }

}