import fs from "fs";
import path from "path";
import utils from "./Utils";

class FileHelper {
  readFile(fileName: string) {
    utils.log(path.join(process.resourcesPath, "data", `${fileName}`));
    const filePath = path.join(process.resourcesPath, "data", `${fileName}`);

    const datas = fs.readFileSync(filePath, "utf8");
    return datas;
  }

  writeFile(fileName: string, data: any) {
    utils.log(path.join(process.resourcesPath, "data", `${fileName}`));
    const filePath = path.join(process.resourcesPath, "data", `${fileName}`);

    fs.writeFileSync(filePath, data);
  }
}

const fileHelper = new FileHelper();
export default fileHelper;
