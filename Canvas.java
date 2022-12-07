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
    ArrayList lastPlayersLocations;
    int lastPlayersCount = 0;
    int indexOfChassor;
    int[] speedUpLocation;

    // shieds
    private int[] shieldLocation;
    int shieldPossessor = -1;
    //

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

        try {
            drawObjects(g);
            Thread.sleep(1);
        } catch (Exception e) {
            // TODO: handle exception
        }
        // repaint();
    }

    public void drawObjects(Graphics g) {
        int[] temp = null;
        int[] lTemp = null;

        // draw the star
        g.setColor(Color.YELLOW);
        g.fillOval(this.getSpeedUpLocation()[0], this.getSpeedUpLocation()[1],
                GameInteractionThread.PWIDTH,
                GameInteractionThread.PHEIGHT);
        // g.fill(new Ellipse2D.Double(this.getSpeedUpLocation()[0],
        // this.getSpeedUpLocation()[1],
        // GameInteractionThread.PWIDTH, GameInteractionThread.PHEIGHT));

        g.setColor(Color.BLACK);
        g.drawString("B", this.getSpeedUpLocation()[0] + 8, this.getSpeedUpLocation()[1] + 18);
        //

        // draw the boost
        if (this.getShieldLocation() != null) {
            g.setColor(Color.BLUE);
            g.fillOval(this.getShieldLocation()[0], this.getShieldLocation()[1], 25, 25);
            g.setColor(Color.WHITE);
            g.drawString("S", this.getShieldLocation()[0] + 10, this.getShieldLocation()[1] + 15);
        }
        //
        for (int i = 0; i < this.getPlayersLocation().size(); i++) {
            g.setColor(Color.WHITE);
            if (indexOfChassor == i) {
                g.setColor(Color.RED);
            }
            temp = (int[]) this.getPlayersLocation().get(i);
            if (this.getLastPlayersLocations() != null
                    && this.getPlayersLocation().size() == this.getLastPlayersLocations().size()) {
                lTemp = (int[]) this.getLastPlayersLocations().get(i);

                if (lTemp[0] > temp[0]) {
                    lTemp[0]--;
                } else if (lTemp[0] < temp[0]) {
                    lTemp[0]++;
                } else if (lTemp[1] > temp[1]) {
                    lTemp[1]--;
                } else if (lTemp[1] < temp[1]) {
                    lTemp[1]++;
                }
                // draw the player
                g.drawString(String.valueOf(i + 1), lTemp[0], lTemp[1] - 5);
                g.fillRect(lTemp[0], lTemp[1], GameInteractionThread.PWIDTH,
                        GameInteractionThread.PHEIGHT);
                // g.fill(new Rectangle2D.Double(lTemp[0], lTemp[1],
                // GameInteractionThread.PWIDTH,
                // GameInteractionThread.PHEIGHT));
                //
                // draw the shield possessor
                if (i == this.getShieldPossessor()) {
                    g.setColor(Color.BLUE);
                    g.fillOval(lTemp[0], lTemp[1], 15, 15);
                    // g.fill(new Ellipse2D.Double(lTemp[0], lTemp[1], 15, 15));
                }
            } else {

                // System.out.println("loc : " + temp[0] + " , " + temp[1]);
                g.fillRect(temp[0], temp[1], GameInteractionThread.PWIDTH, GameInteractionThread.PHEIGHT);
                g.drawString(String.valueOf(i + 1), temp[0], temp[1] - 5);

                if (i == this.getShieldPossessor()) {
                    g.setColor(Color.BLUE);
                    g.fillOval(temp[0], temp[1], 15, 15);
                }
            }

        }
    }

    public void treatMoveResponse(String action) {
        // set parameters
        String[] actWithParam = action.split("/::/");
        String[] params = actWithParam[1].split("//");
        this.setSpeedUpLocation(coordinize(params[0]));

        /// for shield
        try {
            this.setShieldLocation(coordinize(params[1]));
            this.setShieldPossessor(-1);
        } catch (Exception e) {
            this.setShieldLocation(null);
            this.setShieldPossessor(Integer.parseInt(params[1]));
        }
        ///
        //
        String[] clientsAction = actWithParam[0].split("//");
        // System.out.println("act : " + action);
        while (lastPlayersCount != clientsAction.length) {
            instanceNewPLayer();
            // System.out.println("new instance");
            lastPlayersCount++;
        }

        int[] temp = null;
        this.setLastPlayersLocations((ArrayList) this.getPlayersLocation().clone());
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

    public ArrayList getLastPlayersLocations() {
        return lastPlayersLocations;
    }

    public void setLastPlayersLocations(ArrayList lastPlayersLocations) {
        this.lastPlayersLocations = lastPlayersLocations;
    }

    public int getLastPlayersCount() {
        return lastPlayersCount;
    }

    public void setLastPlayersCount(int lastPlayersCount) {
        this.lastPlayersCount = lastPlayersCount;
    }

    public int getIndexOfChassor() {
        return indexOfChassor;
    }

    public void setIndexOfChassor(int indexOfChassor) {
        this.indexOfChassor = indexOfChassor;
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public int getTickCount() {
        return tickCount;
    }

    public void setTickCount(int tickCount) {
        this.tickCount = tickCount;
    }

    public int[] getSpeedUpLocation() {
        return speedUpLocation;
    }

    public void setSpeedUpLocation(int[] speedUpLocation) {
        this.speedUpLocation = speedUpLocation;
    }

    public int[] getShieldLocation() {
        return shieldLocation;
    }

    public void setShieldLocation(int[] shieldLocation) {
        this.shieldLocation = shieldLocation;
    }

    public int getShieldPossessor() {
        return shieldPossessor;
    }

    public void setShieldPossessor(int shieldPossessor) {
        this.shieldPossessor = shieldPossessor;
    }

}