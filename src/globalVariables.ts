import { ProxyConfig } from "./types";

export const APP_HEIGHT = 600;
export const APP_WIDTH = 800;
export const config: ProxyConfig = {
  proxyPort: 8080,
  targetHost: "localhost",
  targetPort: 25565,
};

export const supportVersions: string[] = ["1.21.1"];
