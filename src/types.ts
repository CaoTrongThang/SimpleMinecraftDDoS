export type ProxyConfig = {
  proxyPort: number;
  targetHost: string;
  targetPort: number;
};

export type DDoSConfig = {
  minecraftVersion: string;
  serverIp: string;
  serverPort: number;
  totalBots: number;
  delayEachBot: number;
};
