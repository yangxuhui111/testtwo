package com.steadyjack.server.listener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ServerThread implements Runnable {

	private SocketBean mSocket = null;
	private BufferedReader mReader = null;

	public ServerThread(SocketBean mSocket) throws IOException {
		this.mSocket = mSocket;
		mReader = new BufferedReader(new InputStreamReader(mSocket.socket.getInputStream()));
	}

	@Override
	public void run() {
		try {
			String content = null;
			while ((content = mReader.readLine()) != null) {
				System.out.println("content=" + content);
				int pos = content.indexOf("|");
//				 包头格式为：动作名称,设备编号,昵称,时间,对方设备编号 | ..
				String head = content.substring(0, pos);
				String body = content.substring(pos + 1);
				String[] splitArray = head.split(",");
				String action = splitArray[0];
				System.out.println("action=" + action);

				if (action.equals("LOGIN")) {
					login(splitArray[1], splitArray[2], splitArray[3]);
				} else if (action.equals("LOGOUT")) {
					logout(splitArray[1]); // 传设备编号
					break;
				} else if (action.equals("SENDMSG")) {
					sendmsg(splitArray[2], splitArray[4], splitArray[1], body);// 昵称, 对方设备编号(Build.SERIAL), 设备编号,
																				// 消息体（msg）
				} else if (action.equals("GETLIST")) {
					getlist(splitArray[1]); // 设备编号
				}
//				else if (action.equals("LOGOUT")) {
//				logout(splitArray[1]);
//				break;
//			} else if (action.equals("SENDMSG")) {
//				sendmsg(splitArray[2], splitArray[4], splitArray[1], body);
//			} else if (action.equals("GETLIST")) {
//				getlist(splitArray[1]);
//			}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void login(String deviceId, String nickName, String loginTime) throws IOException {
		for (int i = 0; i < ChatServer.mSocketList.size(); i++) {
			SocketBean item = ChatServer.mSocketList.get(i);
			if (item.id.equals(mSocket.id)) {
				item.deviceId = deviceId;
				item.nickName = nickName;
				item.loginTime = loginTime;
				ChatServer.mSocketList.set(i, item);
				break;
			}
		}
	}

	/**
	 * @function 获取列表
	 * 
	 * @param deviceId 设备编号
	 * @throws IOException
	 */
	private void getlist(String deviceId) throws IOException {
		for (int i = 0; i < ChatServer.mSocketList.size(); i++) {
			SocketBean item = ChatServer.mSocketList.get(i);
			if (item.id.equals(mSocket.id) && item.deviceId.equals(deviceId)) {
				PrintStream printStream = new PrintStream(item.socket.getOutputStream());
				printStream.println(getFriend());
				break;
			}
		}
	}

	/**
	 * @fuction 包装消息体；loginTime :消息发送时间 (GETLIST,|deviceId, nickName,
	 *          loginTime|deviceId, nickName, loginTime)
	 * 
	 * @return
	 */
	private String getFriend() {
		String friends = "GETLIST,";
		for (SocketBean item : ChatServer.mSocketList) {
			if (item.deviceId != null && item.deviceId.length() > 0) {
				String friend = String.format("|%s,%s,%s", item.deviceId, item.nickName, item.loginTime);
				friends += friend;
			}
		}
		return friends;
	}

	/**
	 * @function 退出登录，从socket队列中移除
	 * 
	 * @param deviceId 设备编号
	 * @throws IOException
	 */
	private void logout(String deviceId) throws IOException {
//		循环socket队列
		for (int i = 0; i < ChatServer.mSocketList.size(); i++) {
			SocketBean item = ChatServer.mSocketList.get(i);
//			mSocket 当前的socket;当前的socket等于队列中的socket,并且设备id对上号
			if (item.id.equals(mSocket.id) && item.deviceId.equals(deviceId)) {
//				getOutputStream方法连接的另一端将得到输入，同时返回一个OutputStream对象实例。
				PrintStream printStream = new PrintStream(item.socket.getOutputStream());
				printStream.println("LOGOUT,|");
				item.socket.close();
				ChatServer.mSocketList.remove(i);
				break;
			}
		}
	}

	/**
	 * @function 发送消息
	 * @param otherName 昵称
	 * @param otherId   对方设备编号
	 * @param selfId    设备编号
	 * @param message   消息体
	 * @throws IOException
	 */
	private void sendmsg(String otherName, String otherId, String selfId, String message) throws IOException {
		for (int i = 0; i < ChatServer.mSocketList.size(); i++) {
			SocketBean item = ChatServer.mSocketList.get(i);
			if (item.deviceId.equals(otherId)) {
				String content = String.format("%s,%s,%s,%s|%s", "RECVMSG", selfId, otherName,
						new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()), message);
				PrintStream printStream = new PrintStream(item.socket.getOutputStream());
				printStream.println(content);
				break;
			}
		}
	}

}
