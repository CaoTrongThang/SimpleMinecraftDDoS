package src.Main.DDosSoftware.DDosLogic.Client;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;

import src.Main.DDosSoftware.DDosLogic.Packets.IPacket;
import src.Main.DDosSoftware.DDosLogic.Packets.Packet_1_18_2;
import src.Main.DDosSoftware.Enums.MinecraftVersion;

public class ClientPacketManager {

    // ASCII Table:
    // https://upload.wikimedia.org/wikipedia/commons/thumb/1/1b/ASCII-Table-wide.svg/2560px-ASCII-Table-wide.svg.png

    public static List<IPacket> packetList = new ArrayList<>();

    public static double packetGenerateProcess = 0;

    /**
     * Packet amount is depended on the DDoS's intensity && the minecraft version
     */
    public static void generatePacketForClient() {
        System.out.println();
        System.out.println("Generating packets...");

        // TODO 1.18.2 VERSION
        if (ClientManager.clientVersion == MinecraftVersion.V1_18_2) {
            for (int clientIndex = 0; clientIndex < ClientManager.clients.size(); clientIndex++) {
                Packet_1_18_2 packet = new Packet_1_18_2();
                // * Generate random player's name for packet
                byte[] randName = RandomStringUtils.randomAlphanumeric(16).getBytes();
                for (int index = 20; index < packet.NAME_PACKET.length; index++) {
                    packet.NAME_PACKET[index] = randName[index - 20];
                }
                ClientManager.clients.get(clientIndex).packet = packet;
            }
        }

        // TODO 1.16.5 VERSION
        if (ClientManager.clientVersion == MinecraftVersion.V1_16_5) {
        }

        System.out.println("All the packets are generated!");
        System.out.println();
        try {
            Thread.sleep(1000);
        } catch (Exception e) {

        }
    }
}
