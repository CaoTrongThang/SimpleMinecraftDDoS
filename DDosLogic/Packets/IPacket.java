package src.Main.DDosSoftware.DDosLogic.Packets;

import java.net.Socket;

public interface IPacket {
    public void sendPacket(Socket socket);
}
