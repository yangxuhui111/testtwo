package com.steadyjack.server.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/*
 * 功能：Reward映射
 * 开发人：赵璐
 * 开发时间：2019.5.31
 * */

@Entity
@Table(name="reward")
public class Reward {

	private int rewardId;
	private String rewardContent;
	private String rewardTitle;
	private double rewardMoney;
	private String rewardTime;
	private String rewardDeadline;
	private String rewardState;
	private String rewardImage;
	private User poster;
	private User receiver;
	private int posterId;
	
	public Reward() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	public Reward(int rewardId, User poster, String rewardContent, String rewardTitle, double rewardMoney,
			String rewardTime, String rewardDeadline, String rewardState, String rewardImage) {
		super();
		this.rewardId = rewardId;
		this.poster = poster;
		this.rewardContent = rewardContent;
		this.rewardTitle = rewardTitle;
		this.rewardMoney = rewardMoney;
		this.rewardTime = rewardTime;
		this.rewardDeadline = rewardDeadline;
		this.rewardState = rewardState;
		this.rewardImage = rewardImage;
	}
	
	public Reward(int rewardId, User poster, User receiver,String rewardContent, String rewardTitle, double rewardMoney,
			String rewardTime, String rewardDeadline, String rewardState, String rewardImage) {
		super();
		this.rewardId = rewardId;
		this.poster = poster;
		this.receiver = receiver;
		this.rewardContent = rewardContent;
		this.rewardTitle = rewardTitle;
		this.rewardMoney = rewardMoney;
		this.rewardTime = rewardTime;
		this.rewardDeadline = rewardDeadline;
		this.rewardState = rewardState;
		this.rewardImage = rewardImage;
	}
	public Reward(int posterId, String rewardContent, String rewardTitle, double rewardMoney, String rewardState) {
		super();
		this.posterId = posterId;
		this.rewardContent = rewardContent;
		this.rewardTitle = rewardTitle;
		this.rewardMoney = rewardMoney;
		this.rewardState = rewardState;
	}
	public Reward(int posterId, String rewardContent, String rewardTitle, double rewardMoney, String rewardTime,
			String rewardDeadline, String rewardState) {
		super();
		this.posterId = posterId;
		this.rewardContent = rewardContent;
		this.rewardTitle = rewardTitle;
		this.rewardMoney = rewardMoney;
		this.rewardTime = rewardTime;
		this.rewardDeadline = rewardDeadline;
		this.rewardState = rewardState;
	}

	public Reward(User poster, String rewardContent, String rewardTitle, double rewardMoney, String rewardTime,
			String rewardDeadline, String rewardState, String rewardImage) {
		super();
		this.poster = poster;
		this.rewardContent = rewardContent;
		this.rewardTitle = rewardTitle;
		this.rewardMoney = rewardMoney;
		this.rewardTime = rewardTime;
		this.rewardDeadline = rewardDeadline;
		this.rewardState = rewardState;
		this.rewardImage = rewardImage;
	}

	public Reward( User poster, User receiver,String rewardContent, String rewardTitle, double rewardMoney,
			String rewardTime, String rewardDeadline, String rewardState, String rewardImage) {
		super();
		this.poster = poster;
		this.receiver = receiver;
		this.rewardContent = rewardContent;
		this.rewardTitle = rewardTitle;
		this.rewardMoney = rewardMoney;
		this.rewardTime = rewardTime;
		this.rewardDeadline = rewardDeadline;
		this.rewardState = rewardState;
		this.rewardImage = rewardImage;
	}

	@Id
	@Column(name="reward_id")
	@GeneratedValue(generator="my_gen")
	@GenericGenerator(name="my_gen",strategy="native")
	public int getRewardId() {
		return rewardId;
	}
	public void setRewardId(int rewardId) {
		this.rewardId = rewardId;
	}
	
	@Column(name="reward_content")
	public String getRewardContent() {
		return rewardContent;
	}
	public void setRewardContent(String rewardContent) {
		this.rewardContent = rewardContent;
	}
	
	@Column(name="reward_title")
	public String getRewardTitle() {
		return rewardTitle;
	}
	public void setRewardTitle(String rewardTitle) {
		this.rewardTitle = rewardTitle;
	}
	
	@Column(name="reward_money")
	public double getRewardMoney() {
		return rewardMoney;
	}
	public void setRewardMoney(double rewardMoney) {
		this.rewardMoney = rewardMoney;
	}
	
	@Column(name="reward_time")
	public String getRewardTime() {
		return rewardTime;
	}
	public void setRewardTime(String rewardTime) {
		this.rewardTime = rewardTime;
	}
	
	@Column(name="reward_deadline")
	public String getRewardDeadline() {
		return rewardDeadline;
	}
	public void setRewardDeadline(String rewardDeadline) {
		this.rewardDeadline = rewardDeadline;
	}
	
	@Column(name="reward_state")
	public String getRewardState() {
		return rewardState;
	}
	public void setRewardState(String rewardState) {
		this.rewardState = rewardState;
	}
	
	@Column(name="reward_image")
	public String getRewardImage() {
		return rewardImage;
	}
	public void setRewardImage(String rewardImage) {
		this.rewardImage = rewardImage;
	}

	@ManyToOne
	@JoinColumn(name="poster_id")
	public User getPoster() {
		return poster;
	}


	public void setPoster(User poster) {
		this.poster = poster;
	}

	@ManyToOne
	@JoinColumn(name="receiver_id")
	public User getReceiver() {
		return receiver;
	}


	public void setReceiver(User receiver) {
		this.receiver = receiver;
	}
	
}
