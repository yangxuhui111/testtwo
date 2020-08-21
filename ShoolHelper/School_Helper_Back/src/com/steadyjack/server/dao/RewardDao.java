package com.steadyjack.server.dao;

import java.util.List;

import com.steadyjack.server.model.Reward;


public interface RewardDao {
	
	public void setReward(Reward reward);
	
	public List<Reward> selectBoardReward();
	
	public List<Reward> MyPublish(int posterId);
	public List<Reward> selectRelateReward(String word);
	public Reward MyPublishone(int rewardId);
	public List<Reward> MyPublishtwo(int userId);
	public List<Reward> MyPublishthree(int userId);
	public int reviseState(Reward reward);
	public int updateReward(Reward reward);
	public int save(Reward reward);
	public List<Reward> getAllReward();
	public Reward getReward(int rewardId);
	
	public void deleteReward(int rewardId);
}
