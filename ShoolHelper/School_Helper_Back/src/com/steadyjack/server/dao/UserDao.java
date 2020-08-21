package com.steadyjack.server.dao;

import java.util.List;

import com.steadyjack.server.model.User;

public interface UserDao {
	
	public void setUser(User user);
	
	public int reviseUser(User user);
	public void money(User user);
	public List<User> getAllUser();
	
	public User checkUser(int userId);
	public User checkPhone(String phone);
}
