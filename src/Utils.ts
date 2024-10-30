import { mainWindow } from ".";

class Utils {
  generateRandomName(maxLength: number = 16): string {
    // Define the characters to use for generating random names
    const characters =
      "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    let randomName = "";

    // Generate random characters up to the specified maxLength
    for (let i = 0; i < maxLength; i++) {
      const randomIndex = Math.floor(Math.random() * characters.length);
      randomName += characters[randomIndex];
    }
    utils.log(`Generated Name: ${randomName}`);
    return randomName;
  }

  getListOfRandomNames(listLength: number = 100) {
    const randomNames = [];
    utils.logTitle(`Generating ${listLength} Names`);
    for (let index = 0; index < listLength; index++) {
      randomNames.push(this.generateRandomName());
    }
    return randomNames;
  }

  stringToHex(input: string): string[] {
    // Convert each character to its hexadecimal representation
    return Array.from(input).map((char) =>
      char.charCodeAt(0).toString(16).padStart(2, "0")
    );
  }

  log(msg: string) {
    console.log(msg);
    mainWindow.webContents.send("log", msg);
  }

  logTitle(msg: string) {
    console.log(`============ ${msg} ============ `);
    mainWindow.webContents.send("log", `============ ${msg} ============ `);
  }
}

const utils = new Utils();
export default utils;
