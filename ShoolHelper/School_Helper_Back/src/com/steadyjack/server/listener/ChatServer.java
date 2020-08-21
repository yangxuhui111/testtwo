package com.steadyjack.server.listener;

import java.net.ServerSocket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ChatServer {
	private static final int SOCKET_PORT =52000;
	public static ArrayList<SocketBean> mSocketList = new ArrayList<SocketBean>();

	public ChatServer() {
		
	}
	
	private void initServer() {
		try {
//			 创建一个ServerSocket，用于监听客户端Socket的连接请求
			ServerSocket server = new ServerSocket(SOCKET_PORT);
			while (true) {
//				每当接收到客户端的Socket请求，服务器端也相应的创建一个Socket
				SocketBean socket = new SocketBean(new Date(), server.accept());
				mSocketList.add(socket);
//				 每连接一个客户端，启动一个ServerThread线程为该客户端服务
				new Thread(new ServerThread(socket)).start();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	void invoke() {
		ChatServer server = new ChatServer();
		server.initServer();
	}

}
