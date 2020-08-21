package com.steadyjack.server.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/*
 * 功能：School映射
 * 开发人：赵璐
 * 开发时间：2019.5.31
 * */

@Entity
@Table(name="school")
public class School {
	private int schoolId;
	private String schoolName;
	private List<User> userList = new ArrayList<User>();
	
	public School() {
		super();
		// TODO Auto-generated constructor stub
	}
	public School(int id,String name) {
		this.schoolId=id;
		this.schoolName=name;
	}
	public School(String name) {
		super();
		this.schoolName = name;
	}
	
	@Id
	@Column(name="school_id")
	@GeneratedValue(generator="my_gen")
	@GenericGenerator(name="my_gen",strategy="native")
	public int getSchoolId() {
		return schoolId;
	}
	public void setSchoolId(int schoolId) {
		this.schoolId = schoolId;
	}
	@Column(name="school_name")
	public String getSchoolName() {
		return schoolName;
	}
	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}
	
	@OneToMany(mappedBy="school", targetEntity=User.class,
			cascade=CascadeType.ALL)
	public List<User> getUserList() {
		return userList;
	}
	public void setUserList(List<User> userList) {
		this.userList = userList;
	}
}
