package src.Main.DDosSoftware.DDosLogic.Client;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import src.Main.DDosSoftware.DDosLogic.Server.ServerManager;

public class ClientManager {
    public static List<Client> clients = new ArrayList<>();

    private ClientManager() {

    }

    /**
     * create a client and add to {@link clients} in {@link clientMananger}
     * 
     * @return true if successfully add a client, false if server isn't connected.
     */
    public static boolean createClient(int id) {
        if (ServerManager.canConnect()) {
            clients.add(new Client());
            return true;
        } else {
            return false;
        }
    }

    /**
     * Connect to the server by sending fake Packets
     * 
     */
    public static void connect(Client client, int packetID) {
        try {
            client.socket = new Socket(ServerManager.getIp(), ServerManager.getPort());
        } catch (UnknownHostException e) {
            System.out.println("Unknown Host");
            return;
        } catch (IOException e) {
            return;
        }
        try {
            OutputStream os = client.socket.getOutputStream();
            os.write(ClientPacketManager.packetList.get(packetID));
        } catch (Exception e) {

        }
        System.out.println(client.socket.getLocalPort() + "'s connection is established!");
        return;
    }
}
