package com.steadyjack.server.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.steadyjack.server.dao.ConnectionDao;
import com.steadyjack.server.model.Connection;
import com.steadyjack.server.service.ConnectionService;

@Service
public class ConnectionServiceImpl implements ConnectionService{
	@Autowired
	private ConnectionDao connectionDao;
	
	public void setConnection(Connection con) {
		connectionDao.setConnection(con);
	}
	
	public List<Connection> selectConnection(int receiverId){
		return connectionDao.selectConnection(receiverId);
	}
	
	public List<Connection> selectConnectionone(int posterId){
		return connectionDao.selectConnectionone(posterId);
	}
}
