package src.Main.DDosSoftware.DDosLogic.Client;

import java.net.Socket;

import src.Main.DDosSoftware.DDosLogic.Packets.IPacket;
import src.Main.DDosSoftware.Enums.MinecraftVersion;

public class Client {
    public Socket socket;
    private MinecraftVersion version;
    public IPacket packet;

    public MinecraftVersion getVersion() {
        return version;
    }

    public Client(MinecraftVersion version) {
        this.version = version;
    }

}
