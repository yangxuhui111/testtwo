package com.steadyjack.server.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;
import com.steadyjack.server.dao.UserDao;
import com.steadyjack.server.model.User;

@Repository
public class UserDaoImpl extends BaseDaoImpl implements UserDao{
	
	public void setUser(User user) {
		getSession().save(user);
	}
	
	public int reviseUser(User user) {
		//开始更新
//		Transaction tx = getSession().beginTransaction();
		String hql = "update User set userName =?,userPassword=?,userPhone=?,userMoney=?,userTookCount=?,userPublishCount=?,userIdentification=?,userSignature=?,userRealname=?,userSex=? where userId = ?";
		Query query = getSession().createQuery(hql);
		query.setParameter(0,user.getUserName());
		query.setParameter(1,user.getUserPassword());
		query.setParameter(2,user.getUserPhone());
		query.setParameter(3,user.getUserMoney());
		query.setParameter(4,user.getUserTookCount());
		query.setParameter(5,user.getUserPublishCount());
		query.setParameter(6,user.getUserIdentification());
		query.setParameter(7,user.getUserSignature());
		query.setParameter(8,user.getUserRealname());
		query.setParameter(9,user.getUserSex());
		query.setParameter(10,user.getUserId());
		int ret = query.executeUpdate();
//		tx.commit();
		return ret;
	}	
	public void money(User user) {
		String hql="update User set userMoney=? where userId=?";
		Query query = getSession().createQuery(hql);
		query.setParameter(0,user.getUserMoney());
		query.setParameter(1,user.getUserId());
		query.executeUpdate();
	}
	@SuppressWarnings("unchecked")
	public List<User> getAllUser(){
		//开始查询
		String hql = "from User";
		Query query = getSession().createQuery(hql);
		List<User> users = query.list();
		//返回查询值
		return users;
	}
	
	public User checkUser(int userId) {
		//开始查询
		String hql = "from User where userId = ?";
		Query query = getSession().createQuery(hql);
		query.setParameter(0,userId);
		User user = (User)query.uniqueResult();
		//返回查询值
		return user;		
	}
	public User checkPhone(String phone) {
		//开始查询
		String hql = "from User where userPhone = ?";
		Query query = getSession().createQuery(hql);
		query.setParameter(0,phone);
		User user = (User)query.uniqueResult();
		//返回查询值
		return user;		
	}
}
