package com.steadyjack.server.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.steadyjack.server.dao.UserDao;
import com.steadyjack.server.model.User;
import com.steadyjack.server.service.UserService;

@Service
public class UserServiceImpl implements UserService{
	@Autowired
	private UserDao userDao;
	
	public void setUser(User user) {
		userDao.setUser(user);
	}
	
	public void money(User user) {
		userDao.money(user);
	}
	public int reviseUser(User user) {
		return userDao.reviseUser(user);
	}

	public List<User> getAllUser(){
		return userDao.getAllUser();
	}
	
	public User checkUser(int userId) {
		return userDao.checkUser(userId);
	}
	public User checkPhone(String phone) {
		return userDao.checkPhone(phone);
	}
}
