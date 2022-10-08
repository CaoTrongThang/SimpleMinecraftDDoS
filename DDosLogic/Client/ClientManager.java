package src.Main.DDosSoftware.DDosLogic.Client;

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
            clients.add(new Client(id));
            return true;
        } else {
            return false;
        }
    }
}
