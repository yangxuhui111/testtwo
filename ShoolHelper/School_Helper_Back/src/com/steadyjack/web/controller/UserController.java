package com.steadyjack.web.controller;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.steadyjack.server.model.School;
import com.steadyjack.server.model.User;
import com.steadyjack.server.service.UserService;

@Controller
public class UserController {

	@Autowired
	private UserService userservice;
	
	
	@RequestMapping("/addmoney")
	@ResponseBody
	public void addMoney(ModelMap map,HttpServletRequest request,HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		PrintWriter writer = response.getWriter();
		double money=Double.parseDouble(request.getParameter("money"));
		String phone=request.getParameter("phone");
		User user=userservice.checkPhone(phone);
		JSONObject json10 = new JSONObject();
		JSONArray array = new JSONArray();
		double moneyone=money+user.getUserMoney();
		user.setUserMoney(moneyone);
		try {
			userservice.reviseUser(user);
			json10.put("success", "充值成功");
			json10.put("error", "error");
			json10.put("money", user.getUserMoney());
			array.put(json10);
			writer.println(array.toString());
			writer.flush();
			writer.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@RequestMapping("/getmoney")
	@ResponseBody
	public void getMoney(ModelMap map,HttpServletRequest request,HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		PrintWriter writer = response.getWriter();
		double money=Double.parseDouble(request.getParameter("money"));
		String phone=request.getParameter("phone");
		User user=userservice.checkPhone(phone);
		JSONObject json11 = new JSONObject();
		JSONArray array = new JSONArray();
		double moneyone=user.getUserMoney()-money;
		user.setUserMoney(moneyone);
		try {
			userservice.reviseUser(user);
			System.out.println(user.getUserName());
			System.out.println(user.getUserPassword());
			System.out.println(user.getUserRealname());
			json11.put("success", "提现成功");
			json11.put("error", "error");
			json11.put("money", user.getUserMoney());
			array.put(json11);
			writer.println(array.toString());
			writer.flush();
			writer.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@RequestMapping("/login")
	@ResponseBody
	public void logIn(ModelMap map,HttpServletRequest request,HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		PrintWriter writer = response.getWriter();
		String phone = request.getParameter("phone");
		String password = request.getParameter("password");
		JSONObject json3 = new JSONObject();
		JSONObject json4 = new JSONObject();
		JSONObject json5 = new JSONObject();
		JSONArray array = new JSONArray();
		List<User> userList =userservice.getAllUser();
		User user=new User();
		for(User thisUser:userList) {
			if(thisUser.getUserPhone().equals(phone)) {
				user=thisUser;
			}
		}
		if(user.getUserPhone()==null) {
			json3.put("error", "用户不存在");
			json3.put("success", "success");
			json3.put("userId", 0);
			json3.put("userName", "name");
			json3.put("password", "pass");
			json3.put("schoolId", 0);
			json3.put("stuNum", "num");
			json3.put("phone", "phone");
			json3.put("image", "image");
			json3.put("money", 0);
			json3.put("value", 0);
			json3.put("took", 0);
			json3.put("publish", 0);
			json3.put("identification", "identification");
			json3.put("signature", "signature");
			json3.put("realname", "realname");
			json3.put("sex", "sex");
			array.put(json3);
		}else {
			if(!user.getUserPassword().equals(password)) {
				json4.put("success", "success");
				json4.put("error", "密码错误");
				json4.put("userId", 0);
				json4.put("userName", "name");
				json4.put("password", "pass");
				json4.put("schoolId", 0);
				json4.put("stuNum", "num");
				json4.put("phone", "phone");
				json4.put("image", "image");
				json4.put("money", 0);
				json4.put("value", 0);
				json4.put("took", 0);
				json4.put("publish", 0);
				json4.put("identification", "identification");
				json4.put("signature", "signature");
				json4.put("realname", "realname");
				json4.put("sex", "sex");
				array.put(json4);
			}else {
				json5.put("success", "登录成功");
				json5.put("error", "error");
				json5.put("userId", user.getUserId());
				json5.put("userName", user.getUserName());
				json5.put("password", user.getUserPassword());
				json5.put("schoolId", user.getSchool());
				json5.put("stuNum", user.getUserStudentNum());
				json5.put("phone", user.getUserPhone());
				json5.put("image", user.getImage());
				json5.put("money", user.getUserMoney());
				json5.put("value", user.getUserReputationValue());
				json5.put("took", user.getUserTookCount());
				json5.put("publish", user.getUserPublishCount());
				json5.put("identification", user.getUserIdentification());
				json5.put("signature", user.getUserSignature());
				json5.put("realname", user.getUserRealname());
				json5.put("sex", user.getUserSex());
				System.out.println(user.getUserName());
				array.put(json5);
			}
		}
		writer.println(array.toString());
		writer.flush();
		writer.close();
	}
	
	
	@RequestMapping("/register")
	@ResponseBody
	public void register(ModelMap map,HttpServletRequest request,HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		PrintWriter writer = response.getWriter();
		String name=request.getParameter("name");
		String password=request.getParameter("password");
		String phone=request.getParameter("phone");
		System.out.println(name);
		School school=new School(1,"河北师范大学");
		String stuNumber=request.getParameter("stuNumber");
		String image="images/geren.png";
		Double money=0.00;
		int value=60;
		int took=0;
		int publish=0;
		String identification="未认证";
		User user=new User();
		List<User> userList=userservice.getAllUser();
		JSONObject json1 = new JSONObject();
		JSONObject json2 = new JSONObject();
		JSONArray array = new JSONArray();
		for(User thisUser:userList) {
			if(thisUser.getUserPhone().equals(phone)){
				user=thisUser;
			}
		}
		if(user.getUserPhone()==null) {
			user.setUserName(name);
			user.setUserPassword(password);
			user.setUserPhone(phone);
			user.setUserStudentNum(stuNumber);
			user.setSchool(school);
			user.setImage(image);
			user.setUserMoney(money);
			user.setUserReputationValue(value);
			user.setUserTookCount(took);
			user.setUserPublishCount(publish);
			user.setUserIdentification(identification);
			json1.put("success", "注册成功");
			json1.put("error","error");
			array.put(json1);
			System.out.println(user);
			userservice.setUser(user);
		}else {
			json2.put("error", "用户已存在");
			json2.put("success", "success");
			array.put(json2);
		}
		writer.println(array.toString());
		writer.flush();
		writer.close();
	}
	
	@RequestMapping("/update")
	@ResponseBody
	public void update(ModelMap map,HttpServletRequest request,HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		PrintWriter writer = response.getWriter();
		int id=Integer.parseInt(request.getParameter("id"));
		System.err.println(id);
		String name=request.getParameter("name");
		String phone=request.getParameter("phone");
		String signature=request.getParameter("write");
		String realname=request.getParameter("realname");
		String sex=request.getParameter("sex");
		String identification=request.getParameter("identification");
		JSONObject json7 = new JSONObject();
		JSONObject json9 = new JSONObject();
		JSONArray array = new JSONArray();
		User user=new User();
		int flag=0;
		List<User> userList = userservice.getAllUser(); 
		for(User thisUser:userList) {
			if(thisUser.getUserPhone().equals(phone)) {
				if(thisUser.getUserId()==id) {
					user=thisUser;
					flag=1;
				}else {
					json9.put("success", "success");
					json9.put("error", "该手机号已被注册");
					json9.put("userId", "id");
					json9.put("phone", "phone");
					json9.put("userName", "name");
					json9.put("identification", "identification");
					json9.put("signature", "signature");
					json9.put("realname", "real");
					json9.put("sex", "sex");
					array.put(json9);
					flag=0;
					break;
				}
			}else {
				if(thisUser.getUserId()==id) {
					user=thisUser;
					flag=1;
				}
			}
		}
		if(flag==1) {
			user.setUserName(name);
			user.setUserPhone(phone);
			user.setUserSignature(signature);
			user.setUserRealname(realname);
			user.setUserIdentification(identification);
			user.setUserSex(sex);
			try {
				userservice.reviseUser(user);
				json7.put("success", "修改成功");
				json7.put("error", "error");
				json7.put("phone", user.getUserPhone());
				json7.put("userId", user.getUserId());
				json7.put("userName", user.getUserName());
				json7.put("identification", user.getUserIdentification());
				json7.put("signature", user.getUserSignature());
				json7.put("realname", user.getUserRealname());
				json7.put("sex", user.getUserSex());
				array.put(json7);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		writer.println(array.toString());
		writer.flush();
		writer.close();
	}
	
	
	@RequestMapping("/updateone")
	@ResponseBody
	public void updateOne(ModelMap map,HttpServletRequest request,HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		PrintWriter writer = response.getWriter();
		String password=request.getParameter("password");
		String phone=request.getParameter("phone");
		User user=new User();
		JSONObject json8 = new JSONObject();
		JSONArray array = new JSONArray();
		List<User> userList = userservice.getAllUser(); 
		for(User thisUser:userList) {
			if(thisUser.getUserPhone().equals(phone)) {
				user=thisUser;
			}
		}
		user.setUserPassword(password);
		try {
			userservice.reviseUser(user);
			json8.put("success", "修改成功");
			json8.put("error", "error");
			json8.put("password", user.getUserPassword());
			array.put(json8);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		writer.println(array.toString());
		writer.flush();
		writer.close();
	}
	
	
	/*测试专用*/
//	@RequestMapping("/loginone")
//	@ResponseBody
//	public void loginOne(ModelMap map,HttpServletRequest request,HttpServletResponse response) throws Exception {
//		request.setCharacterEncoding("utf-8");
//		response.setCharacterEncoding("utf-8");
//		PrintWriter writer = response.getWriter();
//		int posterId = Integer.parseInt(request.getParameter("posterId"));
//		String rewardTitle = request.getParameter("rewardTitle");
//		String rewardContent = request.getParameter("rewardContent");
//		String rewardImage = request.getParameter("imgUrl");
//		String rewardTime = request.getParameter("publishTime");
//		String rewardDeadline = request.getParameter("deadline");
//		double rewardMoney = Double.parseDouble(request.getParameter("rewardMoney"));
//		System.out.println(posterId);
//		System.out.println(rewardTitle);
//		System.out.println(rewardContent);
//		JSONObject js = new JSONObject();
//		JSONArray array = new JSONArray();
//		List<User> userList =userservice.getAllUser();
//		
//		array.put(js);
//		writer.println(array.toString());
//		writer.flush();
//		writer.close();
//	}
//	@RequestMapping("/logintwo")
//	@ResponseBody
//	public void loginTwo(ModelMap map,HttpServletRequest request,HttpServletResponse response) throws Exception {
//		request.setCharacterEncoding("utf-8");
//		response.setCharacterEncoding("utf-8");
//		String name=request.getParameter("user");
//		PrintWriter writer = response.getWriter();
//		System.out.println(name);
//		JSONObject js = new JSONObject();
//		JSONArray array = new JSONArray();
//		js.put("success", "成功");
//		array.put(js);
//		writer.println(array.toString());
//		writer.flush();
//		writer.close();
//	}
}
