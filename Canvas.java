package module;

import javax.swing.JFrame;
import javax.swing.JPanel;

import engine.Player;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Dimension;

import java.util.ArrayList;
import java.util.Random;

public class Canvas extends JPanel implements Runnable {
    String action;
    ArrayList playersLocation;
    int lastPlayersCount = 0;
    int indexOfChassor;

    public boolean running = false;
    public int tickCount = 0;

    public Canvas(JFrame container) { // set player
        super();
        this.setSize(container.getSize());
        this.setLocation(0, 0);
        this.setBackground(new Color(50, 50, 50));
        this.setPlayersLocation(new ArrayList<int[]>());
        this.start();
    }

    public synchronized void start() {
        new Thread(this).start();
        running = true;
    }

    public synchronized void stop() {
        running = false;
    }

    //// GAME LOOP
    @Override
    public void run() {
        long lastTime = System.nanoTime();
        double nsPerTick = 1000000000D / 64;

        int ticks = 0;
        int frames = 0;

        long lastTimer = System.currentTimeMillis();
        double delta = 0;

        while (running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / nsPerTick;
            lastTime = now;
            boolean shouldRender = true;

            while (delta >= 1) {
                ticks++;
                tick();
                delta -= 1;
                shouldRender = true;
            }

            try {
                Thread.sleep(2);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }

            if (shouldRender) {
                frames++;
                render();
            }

            if (System.currentTimeMillis() - lastTimer >= 1000) {
                lastTimer += 1000;
                // System.out.println(ticks + " ticks, " + frames + " frames");
                frames = 0;
                ticks = 0;
            }
        }
    }

    public void tick() {
        tickCount++;
    }

    public void render() {
        // int[] temp = null;
        // for (int i = 0; i < this.getPlayersLocation().size(); i++) {
        // JPanel newPrs = new JPanel();
        // newPrs.setSize(new Dimension(GameInteractionThread.PWIDTH,
        // GameInteractionThread.PHEIGHT));
        // newPrs.setBackground(new Color(200, 200, 200));
        // if (i == indexOfChassor) {
        // newPrs.setBackground(Color.RED);
        // }
        // temp = (int[]) this.getPlayersLocation().get(i);
        // System.out.println("coord : " + temp[0] + " , " + temp[1]);
        // newPrs.setLocation(temp[0], temp[1]);
        // this.add(newPrs);
        // }
        repaint();
        // System.out.println("renderer hono ee");
    }
    ///////////// end of the game loop

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
        // repaint();
    }

    public void drawPlayers(Graphics g) {
        int[] temp = null;
        for (int i = 0; i < this.getPlayersLocation().size(); i++) {
            g.setColor(Color.WHITE);
            if (indexOfChassor == i) {
                g.setColor(Color.RED);
            }
            temp = (int[]) this.getPlayersLocation().get(i);

            // System.out.println("loc : " + temp[0] + " , " + temp[1]);
            g.fillRect(temp[0], temp[1], GameInteractionThread.PWIDTH, GameInteractionThread.PHEIGHT);
            g.drawString(String.valueOf(i + 1), temp[0], temp[1] - 5);
        }
    }

    public void treatMoveResponse(String action) {
        String[] clientsAction = action.split("//");
        // System.out.println("act : " + action);
        while (lastPlayersCount != clientsAction.length) {
            instanceNewPLayer();
            // System.out.println("new instance");
            lastPlayersCount++;
        }

        int[] temp = null;
        this.getPlayersLocation().clear();
        for (int i = 0; i < clientsAction.length; i++) {
            if (clientsAction[i].contains("(b)")) {
                indexOfChassor = i;
            }
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
        int y;
        if (!str.contains("(b)")) {
            y = Integer.parseInt(String.valueOf(splited[1].substring(0, splited[1].length() - 1)));
        } else {
            y = Integer.parseInt(String.valueOf(splited[1].substring(0, splited[1].length() - 4)));
        }

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