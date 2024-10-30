import { app, BrowserWindow, ipcMain } from "electron";
import path from "path";
import { APP_HEIGHT, APP_WIDTH, supportVersions } from "./globalVariables";
import utils from "./Utils";
import ddos from "./DDoS";
import { DDoSConfig } from "./types";
import proxyServer from "./proxyServer";
import fileHelper from "./fileHelper";

export let mainWindow: BrowserWindow;

let isFocus = true;

async function createWindow() {
  mainWindow = new BrowserWindow({
    width: APP_WIDTH,
    height: APP_HEIGHT,
    icon: path.join(process.resourcesPath, "icon.ico"),
    webPreferences: {
      nodeIntegration: true,
      contextIsolation: false,
      webSecurity: false,
    },
  });

  mainWindow.loadFile("dist/index.html");
  mainWindow.on("ready-to-show", () => mainWindow.show());

  //turn off the "file, edit, view, window, help" menu
  mainWindow.setMenuBarVisibility(false);
  mainWindow.on("focus", () => {
    isFocus = true;
    return mainWindow.webContents.send("focus", isFocus);
  });
  mainWindow.on("blur", () => {
    isFocus = false;
    return mainWindow.webContents.send("blur", isFocus);
  });
  init();
}

function init() {
  mainWindow.webContents.send("update-versions", supportVersions);

  let settings = JSON.parse(fileHelper.readFile("lastDDoS.json"));
  mainWindow.webContents.send("load-default-ddos-settings", settings);
}
app.setName("Simple Minecraft DDoS");
app.on("ready", createWindow);

ipcMain.on("start-proxy", (event) => {
  proxyServer.switchState();
});

ipcMain.on("start-ddos", async (event, data: DDoSConfig) => {
  await ddos.start(data);
  fileHelper.writeFile("lastDDoS.json", JSON.stringify(data));
});

ipcMain.on("stop-ddos", async (event) => {
  await ddos.stop();
});

process.on("uncaughtException", (error) => {
  console.error("Unhandled error in main process:", error);
  if (error.toString().includes("AggregateError"))
    utils.log(
      "Test connection failed, maybe the server you want to connect isn't open yet or it isn't exists."
    );
});
