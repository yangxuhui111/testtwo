package com.steadyjack.server.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import com.steadyjack.server.dao.ConnectionDao;
import com.steadyjack.server.model.Connection;
@Repository
public class ConnectionDaoImpl extends BaseDaoImpl implements ConnectionDao{

	public void setConnection(Connection con) {
		getSession().save(con);
	}
	
	@SuppressWarnings("unchecked")
	public List<Connection> selectConnection(int receiverId){
		//开始查询
		String hql = "from Connection where receiverId = ?";
		Query query = getSession().createQuery(hql);
		query.setParameter(0,receiverId);
		List<Connection> connections = query.list();
		//返回查询值
		return connections;
	}
	
	@SuppressWarnings("unchecked")
	public List<Connection> selectConnectionone(int posterId){
		//开始查询
		String hql = "from Connection where posterId = ?";
		Query query = getSession().createQuery(hql);
		query.setParameter(0,posterId);
		List<Connection> connections = query.list();
		//返回查询值
		return connections;
	}
}
