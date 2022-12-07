package module;

import java.awt.Rectangle;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;
import java.io.OutputStreamWriter;
import java.io.IOException;

import server.MainServer;

public class GameInteractionThread extends Thread {
    MainServer server;
    private boolean started;
    ArrayList playersLocation;
    private int lastPlayersCount = 0;
    int indexOfChassor;
    private boolean chassStarted = false;

    private int[] speedUpLocation;

    // shield
    private int[] shieldLocation;
    int shieldPossessor = -1;
    //
    // variables for the location limits
    private static int WIDTH = 800;
    private static int HEIGHT = 600;

    public static int PWIDTH = 25;
    public static int PHEIGHT = 25;

    public GameInteractionThread(MainServer server) {
        super();
        this.setServer(server);
        this.setPlayersLocation(new ArrayList<int[]>());
    }

    public boolean isStarted() {
        return started;
    }

    public void setStarted(boolean started) {
        this.started = started;
    }

    @Override
    public void run() {
        super.run();
        ArrayList clientList = null;
        // ArrayList inputStreamList = null;
        Socket eachClient = null;
        try {

            System.out.println("runned");
            String getted = "";
            DataInputStream dis = null;
            DataOutputStream dos = null;
            newSpeedInstance();
            newShieldInstance();
            while (this.isStarted()) {
                clientList = this.getServer().getClients();
                getted = "";

                if (clientList.size() != lastPlayersCount) {
                    instanceNewPLayer();
                    lastPlayersCount++;
                }

                if (lastPlayersCount > 2 && chassStarted == false) {
                    startChass();
                    chassStarted = true;
                }

                for (int i = 0; i < clientList.size(); i++) {
                    eachClient = (Socket) clientList.get(i);

                    if (eachClient.isConnected()) {
                        dis = new DataInputStream(eachClient.getInputStream());

                        if (i != clientList.size() - 1) {

                            getted += i + "::" + dis.readUTF() + "//";
                        } else {
                            getted += i + "::" + dis.readUTF();
                        }

                    }

                }
                treatAction(getted);
                checkColising();
                for (int i = 0; i < clientList.size(); i++) {
                    eachClient = (Socket) clientList.get(i);
                    if (!eachClient.isClosed()) {

                        dos = new DataOutputStream(eachClient.getOutputStream());

                        dos.writeUTF(allLocations());

                        dos.flush();
                    }
                }
                Thread.sleep(50);
                // printLoccation();
                // System.out.println("got : " + getted);
            }
        } catch (Exception e) {
            e.printStackTrace();
            // TODO: handle exception
        }
    }

    private void newSpeedInstance() {
        int[] newSpeedInstance = new int[2];
        newSpeedInstance[0] = new Random().nextInt(WIDTH - 50);
        newSpeedInstance[1] = new Random().nextInt(HEIGHT - 50);

        this.setSpeedUpLocation(newSpeedInstance);
    }

    // shield
    private void newShieldInstance() {
        int[] newSheeldInstance = new int[2];
        newSheeldInstance[0] = new Random().nextInt(WIDTH - 50);
        newSheeldInstance[1] = new Random().nextInt(HEIGHT - 50);

        this.setShieldLocation(newSheeldInstance);
        this.setShieldPossessor(-1);
    }

    public void startChass() {
        indexOfChassor = new Random().nextInt(this.getPlayersLocation().size());
    }

    // private void printLoccation() {
    // String a = "";

    // int[] temp = null;
    // for (int i = 0; i < this.getPlayersLocation().size(); i++) {
    // temp = (int[]) this.getPlayersLocation().get(i);
    // a += i + " : (" + temp[0] + "," + temp[1] + ") / ";
    // }
    // System.out.println(a);
    // }

    private void treatAction(String get) {
        String[] eachAction = get.split("//");

        for (int i = 0; i < eachAction.length; i++) {

            doAction(i, eachAction[i]);

        }
    }

    private String allLocations() {
        String result = "";

        int size = this.getPlayersLocation().size();
        for (int i = 0; i < size; i++) {
            if (i != size - 1) {
                if (i == indexOfChassor) {
                    result += locationOf(i) + "(b)//";
                } else {
                    result += locationOf(i) + "//";
                }
            } else {
                if (i == indexOfChassor) {
                    result += locationOf(i) + "(b)";
                } else {
                    result += locationOf(i) + "";
                }
            }
        }
        // for the boost
        result += "/::/(" + this.getSpeedUpLocation()[0] + "," + this.getSpeedUpLocation()[1] + ")";
        //

        // for the shield
        if (shieldPossessor == -1 && this.getShieldLocation() != null) {
            result += "//(" + this.getShieldLocation()[0] + "," + this.getShieldLocation()[1] + ")";
        } else {
            result += "//" + shieldPossessor;
        }
        //
        // System.out.println("res : " + result);
        return result;
    }

    private void randomizePlocation(int index) {
        int[] temp = (int[]) this.getPlayersLocation().get(index);

        temp[0] = new Random().nextInt(WIDTH - 25);
        temp[1] = new Random().nextInt(HEIGHT - 25);
    }

    private String locationOf(int index) {
        int[] loc = (int[]) this.getPlayersLocation().get(index);

        return "(" + loc[0] + "," + loc[1] + ")";
    }

    private void doAction(int index, String action) {
        String[] actGetted = action.split("::");

        if (actGetted.length != 1) {

            if (actGetted[1].equals("Z")) {
                playerMove(index, "up");
            } else if (actGetted[1].equals("S")) {
                playerMove(index, "down");
            } else if (actGetted[1].equals("Q")) {
                playerMove(index, "left");
            } else if (actGetted[1].equals("D")) {
                playerMove(index, "right");
            }
        }

    }

    public void instanceNewPLayer() {
        int[] newPlayerLocation = new int[2];

        Random rand = new Random();

        newPlayerLocation[0] = rand.nextInt(500);
        newPlayerLocation[1] = rand.nextInt(500);

        this.getPlayersLocation().add(newPlayerLocation);

    }

    private void playerMove(int playerIndex, String movement) {
        int[] location = (int[]) this.getPlayersLocation().get(playerIndex);
        int[] speedUpLoc = this.getSpeedUpLocation();
        int[] shieldLoc = this.getShieldLocation();

        if (movement.equals("up")) {

            if (location[1] - 10 >= 0) {
                location[1] -= 10;

                if (isColising(location, speedUpLoc)) {
                    location[1] -= 20;
                    newSpeedInstance();
                }

            }
        } else if (movement.equals("down")) {

            if (location[1] + 10 + PHEIGHT <= HEIGHT) {
                location[1] += 10;
                if (isColising(location, speedUpLoc)) {
                    location[1] += 20;
                    newSpeedInstance();
                }
            }

        } else if (movement.equals("right")) {

            if (location[0] + 10 + PWIDTH <= WIDTH) {
                location[0] += 10;

                if (isColising(location, speedUpLoc)) {
                    location[0] += 20;
                    newSpeedInstance();
                }
            }

        } else if (movement.equals("left")) {

            if (location[0] - 10 >= 0) {
                location[0] -= 10;
                if (isColising(location, speedUpLoc)) {
                    location[0] -= 20;
                    newSpeedInstance();
                }
            }

        }
        if (shieldLoc != null && isColising(location, shieldLoc) && playerIndex != indexOfChassor) {
            System.out.println("shield : " + playerIndex);
            this.setShieldPossessor(playerIndex);
            this.setShieldLocation(null);
        }
    }

    public void checkColising() {
        int[] temp, chassor;

        for (int i = 0; i < this.getPlayersLocation().size(); i++) {
            if (i != indexOfChassor) {
                temp = (int[]) this.getPlayersLocation().get(i);
                chassor = (int[]) this.getPlayersLocation().get(indexOfChassor);
                if (isColising(chassor, temp)) {
                    if (this.getShieldPossessor() == i) {
                        this.setShieldPossessor(-1);
                        newShieldInstance();
                    } else {
                        this.indexOfChassor = i;
                    }
                    randomizePlocation(i);

                }
            }
        }
    }

    public boolean isColising(int[] playerA, int[] playerB) {
        int x1, x2, y1, y2;
        x1 = playerA[0];
        y1 = playerA[1];
        x2 = playerB[0];
        y2 = playerB[1];

        if (x1 > x2 && x1 < x2 + PWIDTH) {
            if (y1 > y2 && y1 < y2 + PHEIGHT) {
                return true;
            }
        }
        if (x2 > x1 && x2 < x1 + PWIDTH) {
            if (y2 > y1 && y2 < y1 + PHEIGHT) {
                return true;
            }
        }

        // if ((x2 > x1 && x2 < x1 + PWIDTH) && (y2 > y1 && y2 < y1 + PHEIGHT)) {
        // System.out.println("aoo 1");
        // return true;
        // }
        // if ((x1 > x2 && x1 < x2 + PWIDTH) && (y1 > y2 && y1 < y2 + PHEIGHT)) {
        // System.out.println("aoo 2");
        // return true;
        // }
        // Rectangle rect1 = new Rectangle();
        // rect1.setSize(PWIDTH, PHEIGHT);
        // rect1.setLocation(x1, y1);
        // Rectangle rect2 = new Rectangle();
        // rect1.setSize(PWIDTH, PHEIGHT);
        // rect1.setLocation(x2, y2);

        return false;
    }

    @Override
    public synchronized void start() {
        // TODO Auto-generated method stub
        super.start();
        this.setStarted(true);
    }

    public MainServer getServer() {
        return server;
    }

    public void setServer(MainServer server) {
        this.server = server;
    }

    public ArrayList getPlayersLocation() {
        return playersLocation;
    }

    public void setPlayersLocation(ArrayList playersLocation) {
        this.playersLocation = playersLocation;
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

    public boolean isChassStarted() {
        return chassStarted;
    }

    public void setChassStarted(boolean chassStarted) {
        this.chassStarted = chassStarted;
    }

    public int[] getSpeedUpLocation() {
        return speedUpLocation;
    }

    public void setSpeedUpLocation(int[] speedUpLocation) {
        this.speedUpLocation = speedUpLocation;
    }

    public static int getWIDTH() {
        return WIDTH;
    }

    public static void setWIDTH(int wIDTH) {
        WIDTH = wIDTH;
    }

    public static int getHEIGHT() {
        return HEIGHT;
    }

    public static void setHEIGHT(int hEIGHT) {
        HEIGHT = hEIGHT;
    }

    public static int getPWIDTH() {
        return PWIDTH;
    }

    public static void setPWIDTH(int pWIDTH) {
        PWIDTH = pWIDTH;
    }

    public static int getPHEIGHT() {
        return PHEIGHT;
    }

    public static void setPHEIGHT(int pHEIGHT) {
        PHEIGHT = pHEIGHT;
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