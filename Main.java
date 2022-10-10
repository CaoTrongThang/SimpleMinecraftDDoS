package src.Main.DDosSoftware;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

import src.Main.DDosSoftware.DDosLogic.Packets.Packet_1_18_2;
import src.Main.DDosSoftware.GUI.GUI;

public class Main {

    public static void main(String[] args) {
        GUI.start();
    }

    public static void proxyStart() {
        Scanner scan = new Scanner(System.in);
        int DEFAULT_BYTE_SIZE = 10000;
        int DEFAULT_SERVER_BYTE_SIZE = 10000;

        String IP;
        String PORT;

        ServerSocket proxySocket = null;
        Socket client = null;
        Socket server = null;

        InputStream cIS = null;
        OutputStream cOS = null;
        byte[] cBuffer = new byte[DEFAULT_BYTE_SIZE];

        InputStream sIS = null;
        OutputStream sOS = null;
        byte[] sBuffer = new byte[DEFAULT_SERVER_BYTE_SIZE];

        System.out.println("Type IP: ");
        IP = scan.nextLine();
        if (IP.equalsIgnoreCase("")) {
            IP = "127.0.0.1";
        }
        System.out.println("Type PORT:");
        PORT = scan.nextLine();
        if (PORT.equalsIgnoreCase("")) {
            PORT = "25565";
        }

        try {
            proxySocket = new ServerSocket(5000);
            System.out.println("Proxy is started!");
            client = proxySocket.accept();
            System.out.println(client.getLocalPort() + " accepted");

            if (client != null) {
                cIS = client.getInputStream();
                cOS = client.getOutputStream();
            }

            server = new Socket(IP, Integer.parseInt(PORT));
            if (server != null) {
                sIS = server.getInputStream();
                sOS = server.getOutputStream();
            }

            Packet_1_18_2 packet = new Packet_1_18_2();

            while (true) {
                if (cIS.available() > 0) {

                    int similarCounter = 0;

                    cIS.read(cBuffer);
                    sOS.write(cBuffer);

                    System.out.print("\u001B[31mCLIENT: ");
                    for (int index = 0; index < cBuffer.length; index++) {
                        System.out.print(cBuffer[index] + " ");
                    }
                    System.out.println("\n");
                    Thread.sleep(2000);
                    cBuffer = new byte[DEFAULT_BYTE_SIZE];
                    System.out.println("\u001B[37m");
                }

                if (sIS.available() > 0) {
                    sIS.read(sBuffer);
                    cOS.write(sBuffer);

                    System.out.print("SERVER: + ");
                    System.out.println("\n");
                    Thread.sleep(2000);
                    sBuffer = new byte[DEFAULT_SERVER_BYTE_SIZE];
                }
            }
        } catch (Exception e) {

        }

    }
}
