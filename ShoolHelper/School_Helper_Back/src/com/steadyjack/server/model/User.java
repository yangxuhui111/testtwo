package com.steadyjack.server.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/*
 * 功能：User映射
 * 开发人：赵璐
 * 开发时间：2019.5.31
 * */

@Entity
@Table(name="user")
public class User {
	private String userName;
	private String userPassword;
	private String userStudentNum;
	private String userPhone;
	private String image;
	private double userMoney;
	private int userReputationValue;
	private int userTookCount;
	private int userPublishCount;
	private String userIdentification;
	private String userSignature;
	private String userRealname;
	private String userSex;
	private int userId;
	private School school;
	private List<Reward> posterList = new ArrayList<Reward>();
	private List<Reward> receiverList = new ArrayList<Reward>();
	
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public User(String userName, String userPassword, School school, String userStudentNum, String userPhone,
			String image, double userMoney, int userReputationValue, int userTookCount, int userPublishCount,
			String userIdentification, String userSignature, String userRealname, String userSex, int userId) {
		super();
		this.userName = userName;
		this.userPassword = userPassword;
		this.school = school;
		this.userStudentNum = userStudentNum;
		this.userPhone = userPhone;
		this.image = image;
		this.userMoney = userMoney;
		this.userReputationValue = userReputationValue;
		this.userTookCount = userTookCount;
		this.userPublishCount = userPublishCount;
		this.userIdentification = userIdentification;
		this.userSignature = userSignature;
		this.userRealname = userRealname;
		this.userSex = userSex;
		this.userId = userId;
	}
	
	@Column(name="user_name")
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	@Column(name="user_password")
	public String getUserPassword() {
		return userPassword;
	}
	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}
	
	@Column(name="user_student_num")
	public String getUserStudentNum() {
		return userStudentNum;
	}
	public void setUserStudentNum(String userStudentNum) {
		this.userStudentNum = userStudentNum;
	}
	@Column(name="user_phone")
	public String getUserPhone() {
		return userPhone;
	}
	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}
	@Column(name="user_image")
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	@Column(name="user_money")
	public double getUserMoney() {
		return userMoney;
	}
	public void setUserMoney(double userMoney) {
		this.userMoney = userMoney;
	}
	@Column(name="user_reputation_value")
	public int getUserReputationValue() {
		return userReputationValue;
	}
	public void setUserReputationValue(int userReputationValue) {
		this.userReputationValue = userReputationValue;
	}
	@Column(name="user_took_count")
	public int getUserTookCount() {
		return userTookCount;
	}
	public void setUserTookCount(int userTookCount) {
		this.userTookCount = userTookCount;
	}
	@Column(name="user_publish_count")
	public int getUserPublishCount() {
		return userPublishCount;
	}
	public void setUserPublishCount(int userPublishCount) {
		this.userPublishCount = userPublishCount;
	}
	@Column(name="user_identification")
	public String getUserIdentification() {
		return userIdentification;
	}
	public void setUserIdentification(String userIdentification) {
		this.userIdentification = userIdentification;
	}
	@Column(name="user_signature")
	public String getUserSignature() {
		return userSignature;
	}
	public void setUserSignature(String userSignature) {
		this.userSignature = userSignature;
	}
	@Column(name="user_realname")
	public String getUserRealname() {
		return userRealname;
	}
	public void setUserRealname(String userRealname) {
		this.userRealname = userRealname;
	}
	@Column(name="user_sex")
	public String getUserSex() {
		return userSex;
	}
	public void setUserSex(String userSex) {
		this.userSex = userSex;
	}
	
	@Id
	@Column(name="user_id")
	@GeneratedValue(generator="my_gen")
	@GenericGenerator(name="my_gen",strategy="native")
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}

	@ManyToOne
	@JoinColumn(name="school_id")
	public School getSchool() {
		return school;
	}

	public void setSchool(School school) {
		this.school = school;
	}

	@OneToMany(mappedBy="poster", targetEntity=Reward.class,
			cascade=CascadeType.ALL)
	public List<Reward> getPosterList() {
		return posterList;
	}

	public void setPosterList(List<Reward> posterList) {
		this.posterList = posterList;
	}

	@OneToMany(mappedBy="receiver", targetEntity=Reward.class,
			cascade=CascadeType.ALL)
	public List<Reward> getReceiverList() {
		return receiverList;
	}

	public void setReceiverList(List<Reward> receiverList) {
		this.receiverList = receiverList;
	}

	@Override
	public String toString() {
		return "User [userName=" + userName + ", userPassword=" + userPassword + ", userStudentNum=" + userStudentNum
				+ ", userPhone=" + userPhone + "]";
	}
	
}
