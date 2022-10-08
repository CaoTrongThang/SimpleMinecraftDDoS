package src.Main.DDosSoftware.DDosLogic.Server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class ServerManager {
    private static InetAddress ip;
    private static int port;
    private static Socket server;
    private static boolean canConnect = false;

    private ServerManager() {
    }

    public static Socket getServer() {
        return server;
    }

    public static InetAddress getIp() {
        return ip;
    }

    public static int getPort() {
        return port;
    }

    /**
     * Bind the IP and PORT of the server
     * 
     * @param ip   the IP of the server
     * @param port the PORT of the server
     */
    public static void bindIPAndPort(String Ip, int Port) {
        try {
            ip = InetAddress.getByName(Ip);
        } catch (UnknownHostException e) {
            System.out.println("Host " + ip + " is not exist!");
        }
        port = Port;
    }

    /**
     * Connect to the server
     * 
     * @return false if connection isn't established, true if connection is
     *         established
     */
    public static boolean connectionTest() {
        System.out.println("Connecting to " + ip.getHostAddress() + ":" + port + "...");

        try {
            server = new Socket(ip, port);
        } catch (UnknownHostException e) {
            canConnect = false;
            return false;
        } catch (IOException e) {
            canConnect = false;
            return false;
        }
        try {
            server.close();
        } catch (Exception e) {
            return false;
        }
        canConnect = true;
        return true;
    }

    public static boolean canConnect() {
        return canConnect;
    }

}
