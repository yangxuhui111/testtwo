package com.steadyjack.server.dao;

import java.util.List;

import com.steadyjack.server.model.Connection;

public interface ConnectionDao {
	
	public void setConnection(Connection con);
	
	public List<Connection> selectConnection(int receiverId);
	
	public List<Connection> selectConnectionone(int posterId);
}
