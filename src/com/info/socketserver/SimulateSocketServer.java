package com.info.socketserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.HashMap;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.info.comm.ParseConfigXML;

public class SimulateSocketServer {
	private static final Logger logger = Logger.getLogger(SimulateSocketServer.class);
	public static void main(String[] args) {
		try {
			PropertyConfigurator.configure("./config/log4j.properties");
			if (logger.isDebugEnabled()) 
        	{
        		logger.debug("开始运行仿真程序服务器端......");
        	}
			HashMap<String,String> hashMap = ParseConfigXML.getXmlConfig(); //解析配置文件
			String serverport = (String) hashMap.get("serverport"); // 从配置文件中获取交易侦听端口号
			int iport = Integer.parseInt(serverport); // 转换类型
			ServerSocket serverSocket = null;
			boolean listening = true;
			try {
				serverSocket = new ServerSocket(iport); // 建立ServerSocket，用于与客户端的连接
				serverSocket.setReuseAddress(true); // 允许连接处于超时状态时绑定套接字
			} catch (IOException e) {
				// 连接不成功时写日志到文件中
				logger.error("Could not listen on port:" +iport);
				if (serverSocket != null)
					serverSocket.close();
				System.exit(-1);
			}
			try {
				while (listening) {
					// 如果连接成功，则新建一个进程
					new SocketSeverThread(serverSocket.accept()).start();
				}
			} catch (Exception ep) {
				logger.error("Socket Exception:" +ep.toString());
			} finally {
				if (serverSocket != null) {
					serverSocket.close();
					logger.error("ServerSocket have been closed\n");
				}
			}
		} catch (Exception e) {
			System.out.println("模拟测试程序启动失败");
			e.printStackTrace();
			System.exit(-1);
		}
	}
}
