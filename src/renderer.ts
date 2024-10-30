import { ipcRenderer } from "electron";
import path from "path";

const startButton = document.getElementById("start-button");
const stopButton = document.getElementById("stop-button");
const startProxyButton = document.getElementById("start-proxy-button");
const logs = document.getElementById("logs");

const minecraftVersion = document.getElementById(
  "minecraft-version"
) as HTMLInputElement;
const serverIp = document.getElementById("server-ip") as HTMLInputElement;
const serverPort = document.getElementById("server-port") as HTMLInputElement;
const totalBots = document.getElementById("total-bots") as HTMLInputElement;
const delayEachBot = document.getElementById(
  "delay-each-bot"
) as HTMLInputElement;

let isFocus: Electron.IpcRendererEvent;

let logMsgs: string[] = [];
let maxlogMsgs = 100;
let previouslogMsgs: string[] = [];

startButton?.addEventListener("click", () => {
  ipcRenderer.send("start-ddos", {
    serverIp: serverIp.value,
    serverPort: serverPort.value,
    totalBots: totalBots.value,
    minecraftVersion: minecraftVersion.value,
    delayEachBot: delayEachBot.value,
  });
});

stopButton?.addEventListener("click", () => {
  ipcRenderer.send("stop-ddos");
});

startProxyButton?.addEventListener("click", () => {
  ipcRenderer.send("start-proxy");
});

ipcRenderer.on("load-default-ddos-settings", (event, data) => {
  serverIp.value = data.serverIp;
  serverPort.value = data.serverPort;
  totalBots.value = data.totalBots;
  delayEachBot.value = data.delayEachBot;
});

ipcRenderer.on("update-versions", (event, data) => {
  while (minecraftVersion.firstChild)
    minecraftVersion.removeChild(minecraftVersion.firstChild);
  for (const x of data) {
    const option = document.createElement("option");
    option.value = x;
    option.textContent = x;
    minecraftVersion.appendChild(option);
  }
});

ipcRenderer.on("log", (event, message) => {
  while (logMsgs.length >= maxlogMsgs) logMsgs.shift();

  logMsgs.push(message);
  console.log(message);
});

setInterval(() => {
  if (isFocus) {
    while (logs?.firstChild) {
      logs.removeChild(logs.firstChild);
    }

    for (const x of logMsgs) {
      const p = document.createElement("label");
      p.textContent = x;
      logs?.appendChild(p);
    }

    previouslogMsgs = logMsgs;
  }
}, 1000 * 2);

ipcRenderer.on("focus", (data) => {
  isFocus = data;
});

ipcRenderer.on("blur", (data) => {
  isFocus = data;
});
