package module;

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

    // variables for the location limits
    private static int WIDTH = 800;
    private static int HEIGHT = 600;

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
            while (this.isStarted()) {
                clientList = this.getServer().getClients();
                getted = "";

                if (clientList.size() != lastPlayersCount) {
                    instanceNewPLayer();
                    lastPlayersCount++;
                }

                for (int i = 0; i < clientList.size(); i++) {
                    eachClient = (Socket) clientList.get(i);

                    dis = new DataInputStream(eachClient.getInputStream());

                    if (i != clientList.size() - 1) {

                        getted += i + "::" + dis.readUTF() + "//";
                    } else {
                        getted += i + "::" + dis.readUTF();
                    }

                }
                treatAction(getted);
                for (int i = 0; i < clientList.size(); i++) {
                    eachClient = (Socket) clientList.get(i);

                    dos = new DataOutputStream(eachClient.getOutputStream());

                    dos.writeUTF(allLocations());

                    dos.flush();
                }
                Thread.sleep(50);
                // System.out.println("got : " + getted);
            }
        } catch (Exception e) {
            e.printStackTrace();
            // TODO: handle exception
        }
    }

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
                result += locationOf(i) + "//";
            } else {
                result += locationOf(i);

            }
        }
        // System.out.println(result);
        return result;
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
        if (movement.equals("up")) {
            int[] location = (int[]) this.getPlayersLocation().get(playerIndex);

            if (location[1] - 10 >= 0) {
                location[1] -= 10;
            }
        } else if (movement.equals("down")) {
            int[] location = (int[]) this.getPlayersLocation().get(playerIndex);

            if (location[1] + 10 + 25 <= HEIGHT) {
                location[1] += 10;
            }

        } else if (movement.equals("right")) {
            int[] location = (int[]) this.getPlayersLocation().get(playerIndex);

            if (location[0] + 10 + 25 <= WIDTH) {
                location[0] += 10;
            }

        } else if (movement.equals("left")) {
            int[] location = (int[]) this.getPlayersLocation().get(playerIndex);

            if (location[0] - 10 >= 0) {
                location[0] -= 10;
            }

        }
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
}