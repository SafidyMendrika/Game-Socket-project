package run;

import launcher.GameProcess;

public class ClientMain {
    public static void main(String[] args) {
        try {
            new GameProcess().askForIp();
        } catch (Exception e) {
            e.printStackTrace();
            // TODO: handle exception
        }
    }
}
