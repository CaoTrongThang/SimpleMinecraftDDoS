import * as net from "net";
import { config } from "./globalVariables";
import utils from "./Utils";

export class ProxyServer {
  private server: net.Server;

  constructor() {
    this.server = net.createServer(this.handleClientConnection.bind(this));
  }

  public start() {
    this.server.listen(config.proxyPort, () => {
      console.log(`TCP proxy server listening on port ${config.proxyPort}`);
    });

    this.server.on("error", (err) => {
      console.error("Server error:", err.message);
    });
  }

  public stop() {
    this.server.close(() => {
      console.log("TCP proxy server closed");
    });
  }

  private handleClientConnection(clientSocket: net.Socket) {
    console.log(
      "Client connected:",
      clientSocket.remoteAddress,
      clientSocket.remotePort
    );

    // Create a reusable function to connect to the target server
    const connectToTarget = () => {
      const targetSocket = new net.Socket();

      targetSocket.connect(config.targetPort, config.targetHost, () => {
        console.log(
          `Connected to target server at ${config.targetHost}:${config.targetPort}`
        );
      });

      // Relay data from the client to the target server
      clientSocket.on("data", (data) => {
        const hexString =
          data
            .toString("hex")
            .match(/.{1,2}/g)
            ?.join(" ") || "";
        console.error(`Client:`, hexString);
        targetSocket.write(data);
      });

      // Relay data from the target server back to the client
      targetSocket.on("data", (data) => {
        clientSocket.write(data);
      });

      targetSocket.on("error", (err) => {});
    };

    connectToTarget();

    // Handle client socket close event
    clientSocket.on("close", () => {
      console.log("Client connection closed");
    });
  }

  switchState() {
    this.server.listening ? this.stop() : this.start();
    utils.log(
      `Proxy server state changed: ${this.server.listening ? "ON" : "OFF"}`
    );
  }
}

const proxyServer = new ProxyServer();
export default proxyServer;
