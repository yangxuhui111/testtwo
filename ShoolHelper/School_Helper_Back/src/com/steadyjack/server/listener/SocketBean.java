package com.steadyjack.server.listener;

import java.net.Socket;
import java.util.Date;

public class SocketBean {
	public String id;
	public String deviceId;
	public Date date;
	public Socket socket;
	public String nickName;
	public String loginTime;
	
	public SocketBean(Date date, Socket socket) {
		super();
		this.date = date;
		this.socket = socket;
	}
	
	
	
	
	
	
}
