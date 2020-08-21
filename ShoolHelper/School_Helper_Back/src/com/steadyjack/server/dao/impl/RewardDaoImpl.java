package com.steadyjack.server.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;
import com.steadyjack.server.dao.RewardDao;
import com.steadyjack.server.model.Reward;

@Repository
public class RewardDaoImpl extends BaseDaoImpl implements RewardDao{
	
	public void setReward(Reward reward) {
		getSession().save(reward);
	}
	
	@SuppressWarnings("unchecked")
	public List<Reward> selectBoardReward(){
		//开始查询
		String hql = "from Reward where rewardState = '1'";
		Query query = getSession().createQuery(hql);
		List<Reward> rewards = query.list();
		//返回查询值
		return rewards;
	}
	
	@SuppressWarnings("unchecked")
	public List<Reward> MyPublish(int posterId){
		//开始查询
		String hql = "from Reward where poster.userId = ?";
		Query query = getSession().createQuery(hql);
		query.setParameter(0,posterId);
		List<Reward> rewards = query.list();
		//返回查询值
		return rewards;
	}
	
	@SuppressWarnings("unchecked")
	public Reward MyPublishone(int rewardId){
		//开始查询
		String hql = "from Reward where rewardId = ?";
		Query query = getSession().createQuery(hql);
		query.setParameter(0,rewardId);
		Reward rewards = (Reward)query.uniqueResult();
		//返回查询值
		return rewards;
	}
	
	@SuppressWarnings("unchecked")
	public List<Reward> MyPublishtwo(int userId){
		//开始查询
		String hql = "from Reward where receiver.userId = ?";
		Query query = getSession().createQuery(hql);
		query.setParameter(0,userId);
		List<Reward> rewards = query.list();
		//返回查询值
		return rewards;
	}
	
	@SuppressWarnings("unchecked")
	public List<Reward> MyPublishthree(int userId){
		//开始查询
		String hql = "from Reward where poster.userId = ?";
		Query query = getSession().createQuery(hql);
		query.setParameter(0,userId);
		List<Reward> rewards = query.list();
		//返回查询值
		return rewards;
	}
	
	public int save(Reward reward) {
		//开始更新
//		Transaction tx = getSession().beginTransaction();
		String hql = "update Reward set rewardState = ? where rewardId = ?";
		Query query = getSession().createQuery(hql);
		query.setParameter(0,reward.getRewardState());
		query.setParameter(1,reward.getRewardId());
		int ret = query.executeUpdate();
//		tx.commit();
		return ret;
	}
	
	@SuppressWarnings("unchecked")
	public List<Reward> selectRelateReward(String word){
	 
		//开始查询
		String hql = "from Reward where rewardTitle like '%"+word+"%'";
		Query query = getSession().createQuery(hql);
		List<Reward> rewards = query.list();
		return rewards;
	}
	
	
	public int updateReward(Reward reward) {
		//开始更新
//		Transaction tx = getSession().beginTransaction();
		String hql = "update Reward set rewardState = ?,receiver=? where rewardId = ?";
		Query query = getSession().createQuery(hql);
		query.setParameter(0,reward.getRewardState());
		query.setParameter(1,reward.getReceiver());
		query.setParameter(2,reward.getRewardId());
		int ret = query.executeUpdate();
//		tx.commit();
		return ret;
	}
	
	public int reviseState(Reward reward) {
		//开始更新
//		Transaction tx = getSession().beginTransaction();
		String hql = "update Reward set rewardState = ?,receiver.userId=? where rewardId = ?";
		Query query = getSession().createQuery(hql);
		query.setParameter(0,reward.getRewardState());
		query.setParameter(1,reward.getReceiver().getUserId());
		query.setParameter(2,reward.getRewardId());
		int ret = query.executeUpdate();
//		tx.commit();
		return ret;
	}
	@SuppressWarnings("unchecked")
	public List<Reward> getAllReward(){
		//开始查询
		String hql = "from Reward";
		Query query = getSession().createQuery(hql);
		List<Reward> rewards = query.list();
		//返回查询值
		return rewards;
	}
	public Reward getReward(int rewardId){
		//开始查询
		String hql = "from Reward where rewardId=?";
		Query query = getSession().createQuery(hql);
		query.setParameter(0,rewardId);
		Reward reward = (Reward)query.uniqueResult();
		//返回查询值
		return reward;
	}
	public void deleteReward(int rewardId) {
		String hql="delete Reward where rewardId=?";
		Query query=getSession().createQuery(hql);
		query.setParameter(0, rewardId);
		query.executeUpdate();
	}
}
