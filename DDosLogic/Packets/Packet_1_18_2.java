package src.Main.DDosSoftware.DDosLogic.Packets;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Packet_1_18_2 implements IPacket {
    /*
     * 1.18.2 Minecraft Packet
     * Second row of PACKET_NAME is the name of the player
     */
    public byte[] NAME_PACKET = {
            16, 0, -10, 5, 9, 108, 111, 99, 97, 108, 104, 111, 115, 116, 19, -120, 2, 17, 0, 15,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };

    OutputStream os;

    @Override
    public void sendPacket(Socket server) {
        try {
            OutputStream os = server.getOutputStream();
            os.write(NAME_PACKET);
        } catch (Exception e) {

        }
    }
}
