package src.Main.DDosSoftware.GUI;

import java.lang.reflect.Field;
import java.util.Scanner;

import src.Main.DDosSoftware.DDosLogic.Flood.DDoSManager;
import src.Main.DDosSoftware.DDosLogic.Server.ServerManager;
import src.Main.DDosSoftware.Enums.Intensity;

/**
 * <p>
 * Get data from user to prepare the DDoS
 * </p>
 */
public class GUI {
    private Scanner scan = new Scanner(System.in);

    private GUI() {
        ServerManager.bindIPAndPort(getIP(), getPort());
        System.out.println();

        // Check if can connect to the server, if no, stop the program
        ServerManager.connectionTest();

        DDoSManager.setIntensity(getIntensity());
        DDoSManager.setDelayTime(getDelayTime());

        printDDoSInfo();

        if (doubleCheck()) {
            DDoSManager.start();
        } else {
            System.out.println("Stopping the program...");
            System.exit(0);
        }

    }

    public static GUI start() {
        return new GUI();
    }

    public String getIP() {
        System.out.println("Server's IP: ");
        System.out.print("- ");
        String IP = scan.nextLine();
        if (IP.equalsIgnoreCase("") || IP.isEmpty()) {
            return "localhost";
        } else {
            return IP;
        }

    }

    public int getPort() {
        System.out.println("Server's PORT: ");
        System.out.print("- ");
        String PORT = scan.nextLine();
        System.out.println();
        if (PORT == null || PORT.equalsIgnoreCase("")) {
            return 25565;
        } else {
            return Integer.parseInt(PORT);
        }
    }

    public Intensity getIntensity() {
        System.out.println("Intensity: ");
        for (Field field : Intensity.class.getFields()) {
            System.out.println("+ " + field.getName());
        }
        System.out.println("Type the intensity: ");
        System.out.print("- ");
        String intense = scan.nextLine().toUpperCase();
        if (intense == null || intense.equalsIgnoreCase("")) {
            return Intensity.valueOf("LOW");
        } else {
            return Intensity.valueOf(intense.toUpperCase());
        }
    }

    public long getDelayTime() {
        System.out.println();
        System.out.println("Delay time per bot (in miliseconds): ");
        System.out.print("- ");
        String delayTime = scan.nextLine();
        System.out.println();
        if (delayTime == null || delayTime.equalsIgnoreCase("")) {
            return DDoSManager.DEFAULT_DELAY_TIME;
        } else {
            return Long.parseLong(delayTime);
        }
    }

    public void printDDoSInfo() {
        System.out.println("<===========> DDOS STATS <===========>");
        System.out.println("\n");

        System.out.println("SERVER'S IP: " + ServerManager.getIp().getHostAddress());
        System.out.println("SERVER'S PORT: " + ServerManager.getPort());
        System.out.println("DDoS INTENSITY: " + DDoSManager.getIntensity());
        System.out
                .println("DELAY TIME PER BOT (150 IS RECOMMENED): " + (double) DDoSManager.getDelayTime() / 1000
                        + "s/bot");
        System.out.println(
                "PACKET'S SIZE (BASED ON INTENSITY): "
                        + DDoSManager.getBotSizeBaseOnIntensity(DDoSManager.getIntensity()));
        System.out.println("BOT'S VERSION (DEFAULT): " + "1.21.1");

        System.out.println("\n");
        System.out.println("<===========> DDOS STATS <===========>");
    }

    public boolean doubleCheck() {
        System.out.println();
        System.out.print("ARE YOU READY? (Y/N): ");
        String yesOrNo = scan.nextLine();
        if (yesOrNo.equalsIgnoreCase("yes") || yesOrNo.equalsIgnoreCase("y") || yesOrNo.equalsIgnoreCase("")) {
            return true;
        } else {
            return false;
        }

    }
}
