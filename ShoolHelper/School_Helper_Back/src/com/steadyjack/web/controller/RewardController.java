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

import com.steadyjack.server.model.Connection;
import com.steadyjack.server.model.Reward;
import com.steadyjack.server.model.User;
import com.steadyjack.server.service.ConnectionService;
import com.steadyjack.server.service.RewardService;
import com.steadyjack.server.service.UserService;



@Controller
public class RewardController {

	@Autowired
	private RewardService rewardservice;
	@Autowired
	private UserService userservice;
	
	@RequestMapping("/boraditem")
	@ResponseBody
	public void boardItem(ModelMap map,HttpServletRequest request,HttpServletResponse response) throws Exception{
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		List<Reward> rewardList = rewardservice.selectBoardReward();
		JSONArray array = new JSONArray();
		for(Reward reward:rewardList) {
			JSONObject object = new JSONObject();
			object.put("userId", reward.getPoster().getUserId());
			object.put("rewardId", reward.getRewardId());
			object.put("name", reward.getPoster().getUserName());
			object.put("sex", reward.getPoster().getUserSex());
			object.put("title", reward.getRewardTitle());
			object.put("content", reward.getRewardContent());
			object.put("rewardTime", reward.getRewardTime());
			object.put("endTime", reward.getRewardDeadline());
			object.put("money", reward.getRewardMoney());
			array.put(object);
		}
		response.getWriter().append(array.toString()).append(request.getContextPath());

	}
	
	@RequestMapping("/myfinish")
	@ResponseBody
	public void myFinish(ModelMap map,HttpServletRequest request,HttpServletResponse response) throws Exception{
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		int userId=Integer.parseInt(request.getParameter("userId"));
		JSONArray array=new JSONArray();
		List<Reward> rewardList=rewardservice.MyPublish(userId);
		for(Reward reward:rewardList) {
			if(reward.getRewardState().equals("4")) {
			JSONObject json14=new JSONObject();
			json14.put("userId", reward.getPoster().getUserId());
			json14.put("rewardId", reward.getRewardId());
			json14.put("name", reward.getPoster().getUserName());
			json14.put("sex", reward.getPoster().getUserSex());
			json14.put("title", reward.getRewardTitle());
			json14.put("content", reward.getRewardContent());
			json14.put("rewardTime", reward.getRewardTime());
			json14.put("endTime", reward.getRewardDeadline());
			json14.put("money", reward.getRewardMoney());
			json14.put("state", reward.getRewardState());
			array.put(json14);
			System.err.println(json14);
			}
		}
		response.getWriter().append(array.toString()).append(request.getContextPath());
	}
	
	@RequestMapping("/myreceiver")
	@ResponseBody
	public void myReceiver(ModelMap map,HttpServletRequest request,HttpServletResponse response) throws Exception{
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		int userId=Integer.parseInt(request.getParameter("userId"));
		JSONArray array=new JSONArray();
		List<Reward> rewardList=rewardservice.MyPublishtwo(userId);
		for(Reward reward:rewardList) {
			JSONObject json14=new JSONObject();
			json14.put("userId", reward.getPoster().getUserId());
			json14.put("rewardId", reward.getRewardId());
			json14.put("name", reward.getPoster().getUserName());
			json14.put("sex", reward.getPoster().getUserSex());
			json14.put("title", reward.getRewardTitle());
			json14.put("content", reward.getRewardContent());
			json14.put("rewardTime", reward.getRewardTime());
			json14.put("endTime", reward.getRewardDeadline());
			json14.put("money", reward.getRewardMoney());
			json14.put("state", reward.getRewardState());
			array.put(json14);
			System.err.println(json14);
		}
		response.getWriter().append(array.toString()).append(request.getContextPath());
	}
	
	
	@RequestMapping("/mypublish")
	@ResponseBody
	public void myPublish(ModelMap map,HttpServletRequest request,HttpServletResponse response) throws Exception{
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		int userId=Integer.parseInt(request.getParameter("userId"));
		JSONArray array=new JSONArray();
		List<Reward> rewardList = rewardservice.MyPublish(userId);
		for(Reward reward:rewardList) {
			JSONObject json12=new JSONObject();
			json12.put("userId", reward.getPoster().getUserId());
			json12.put("rewardId", reward.getRewardId());
			json12.put("name", reward.getPoster().getUserName());
			json12.put("sex", reward.getPoster().getUserSex());
			json12.put("title", reward.getRewardTitle());
			json12.put("content", reward.getRewardContent());
			json12.put("rewardTime", reward.getRewardTime());
			json12.put("endTime", reward.getRewardDeadline());
			json12.put("money", reward.getRewardMoney());
			json12.put("state", reward.getRewardState());
			array.put(json12);
			System.err.println(json12);
		}
		response.getWriter().append(array.toString()).append(request.getContextPath());
	}
	

	@RequestMapping("/search")
		@ResponseBody
		public void  search(ModelMap map,HttpServletRequest request,HttpServletResponse response) throws Exception{
			request.setCharacterEncoding("utf-8");
			response.setCharacterEncoding("utf-8");
			PrintWriter writer = response.getWriter();
			JSONArray array=new JSONArray();
			String rewardTitle = request.getParameter("word");
			List<Reward> rewardList = rewardservice.selectRelateReward(rewardTitle);
			for(Reward reward:rewardList) {
				if(reward.getRewardState().equals("1")) {
				JSONObject json17 = new JSONObject();
				json17.put("userId", reward.getPoster().getUserId());
				json17.put("rewardId", reward.getRewardId());
				json17.put("name", reward.getPoster().getUserName());
				json17.put("sex", reward.getPoster().getUserSex());
				json17.put("title", reward.getRewardTitle());
				json17.put("content", reward.getRewardContent());
				json17.put("rewardTime", reward.getRewardTime());
				json17.put("endTime", reward.getRewardDeadline());
				json17.put("money", reward.getRewardMoney());
				json17.put("state", reward.getRewardState());
				array.put(json17);
				System.err.println(json17);
				}
			}
			writer.println(array.toString());
			writer.flush();
			writer.close();
			
		}
	
	
	@RequestMapping("/deletereward")
	@ResponseBody
	public void deleteReward(ModelMap map,HttpServletRequest request,HttpServletResponse response) throws Exception{
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		PrintWriter writer = response.getWriter();
		int rewardId=Integer.parseInt(request.getParameter("rewardId"));
		int userId=Integer.parseInt(request.getParameter("userId"));
		int posterId=Integer.parseInt(request.getParameter("posterId"));
		int receiverId=Integer.parseInt(request.getParameter("receiverId"));
		JSONObject json19 = new JSONObject();
		JSONArray array=new JSONArray();
		User user=userservice.checkUser(userId);
		if(userId==posterId) {
			Reward reward=rewardservice.getReward(rewardId);
			if(reward.getRewardState().equals("2")) {
				Double money1=user.getUserMoney();
				Double money2=reward.getRewardMoney();
				Double money=money1+money2;
				user.setUserMoney(money);
				userservice.money(user);
				rewardservice.deleteReward(rewardId);
			}else if(reward.getRewardState().equals("1")) {
				Double money1=user.getUserMoney();
				Double money2=reward.getRewardMoney();
				Double money=money1+money2;
				user.setUserMoney(money);
				userservice.money(user);
				rewardservice.deleteReward(rewardId);
			}
		}else if(userId==receiverId) {
			Reward reward=rewardservice.getReward(rewardId);
			reward.setReceiver(null);
			reward.setRewardState("1");
			rewardservice.updateReward(reward);
		}
		json19.put("response", "success");
		json19.put("money", user.getUserMoney());
		array.put(json19);
		writer.println(array.toString());
		writer.flush();
		writer.close();
	}
	
	@RequestMapping("/publishreward")
	@ResponseBody
	public void publishReward(ModelMap map,HttpServletRequest request,HttpServletResponse response) throws Exception{
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		JSONArray array=new JSONArray();
		int posterId = Integer.parseInt(request.getParameter("posterId"));
		String rewardTitle = request.getParameter("rewardTitle");
		String rewardContent = request.getParameter("rewardContent");
		String rewardTime = request.getParameter("publishTime");
		String rewardDeadline = request.getParameter("deadline");
		double rewardMoney = Double.parseDouble(request.getParameter("rewardMoney"));
		//此为创建赏金，状态默认为“1”
		String rewardState = "1";
		User user=userservice.checkUser(posterId);
		Reward reward =new Reward();
		reward.setPoster(user);
		reward.setRewardContent(rewardContent);
		reward.setRewardTitle(rewardTitle);
		reward.setRewardMoney(rewardMoney);
		reward.setRewardDeadline(rewardDeadline);
		reward.setRewardTime(rewardTime);
		reward.setRewardState(rewardState);
		rewardservice.setReward(reward);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("response", "success");
		array.put(jsonObject);
		response.getWriter().append(array.toString()).append(request.getContextPath());
	}
	
	
	
	@RequestMapping("/gettask")
	@ResponseBody
	public void getTask(ModelMap map,HttpServletRequest request,HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		PrintWriter writer = response.getWriter();
		int posterId=Integer.parseInt(request.getParameter("posterId"));
		int receiverId=Integer.parseInt(request.getParameter("receiverId"));
		int rewardId=Integer.parseInt(request.getParameter("rewardId"));
		Reward reward=rewardservice.getReward(rewardId);
		JSONObject json17 = new JSONObject();
		JSONArray array = new JSONArray();
		String state="2";
		reward.setRewardState(state);
		User receiver=userservice.checkUser(receiverId);
		reward.setReceiver(receiver);
		try {
			rewardservice.reviseState(reward);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		json17.put("response", "success");
		array.put(json17);
		writer.println(array.toString());
		writer.flush();
		writer.close();
	}
	
	
	@RequestMapping("/task")
	@ResponseBody
	public void task(ModelMap map,HttpServletRequest request,HttpServletResponse response) throws Exception{
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		JSONArray array=new JSONArray();
		int userId=Integer.parseInt(request.getParameter("userId"));
		//发布任务的被接受然后接收者点击已完成在发布人那里显示待确认
		List<Reward> rewardListtwo = rewardservice.MyPublishthree(userId);
		for(Reward rewardone:rewardListtwo) {
			if(rewardone.getRewardState().equals("3")) {
			JSONObject json15 = new JSONObject();
			json15.put("userId", rewardone.getPoster().getUserId());
			json15.put("rewardId", rewardone.getRewardId());
			json15.put("name", rewardone.getPoster().getUserName());
			json15.put("sex", rewardone.getPoster().getUserSex());
			json15.put("title", rewardone.getRewardTitle());
			json15.put("content", rewardone.getRewardContent());
			json15.put("rewardTime", rewardone.getRewardTime());
			json15.put("endTime", rewardone.getRewardDeadline());
			json15.put("money", rewardone.getRewardMoney());
			json15.put("state", rewardone.getRewardState());
			array.put(json15);
			System.err.println(json15);
			}
		}	
			
		//接受发布人的任务在接受人那里显示待完成
			List<Reward> rewardListone = rewardservice.MyPublishtwo(userId);
			for(Reward reward:rewardListone) {
				if(reward.getRewardState().equals("2")) {
				JSONObject json16 = new JSONObject();
				json16.put("userId", reward.getPoster().getUserId());
				json16.put("rewardId", reward.getRewardId());
				json16.put("name", reward.getPoster().getUserName());
				json16.put("sex", reward.getPoster().getUserSex());
				json16.put("title", reward.getRewardTitle());
				json16.put("content", reward.getRewardContent());
				json16.put("rewardTime", reward.getRewardTime());
				json16.put("endTime", reward.getRewardDeadline());
				json16.put("money", reward.getRewardMoney());
				json16.put("state", reward.getRewardState());
				array.put(json16);
				System.err.println(json16);
				}
			}
		response.getWriter().append(array.toString()).append(request.getContextPath());
	}
	
	
	@RequestMapping("/changestate")
	@ResponseBody
	public void changeState(ModelMap map,HttpServletRequest request,HttpServletResponse response) throws Exception{
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		JSONArray array=new JSONArray();
		int rewardId=Integer.parseInt(request.getParameter("rewardId"));
		String state=request.getParameter("state");
		JSONObject json18 = new JSONObject();
		Reward thisreward =rewardservice.getReward(rewardId);
		if(thisreward.getRewardState().equals("2")) {
		try {
			thisreward.setRewardState(state);
			rewardservice.save(thisreward);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}else{
			if(thisreward.getRewardState().equals("3")) {
					User user=new User();
					user=userservice.checkUser(thisreward.getReceiver().getUserId());
					Double money1=user.getUserMoney();
					Double money2=thisreward.getRewardMoney();
					Double money=money1+money2;
					user.setUserMoney(money);
					userservice.reviseUser(user);
					thisreward.setRewardState(state);
					rewardservice.save(thisreward);
				} 
				}
			
		json18.put("response", "success");
		array.put(json18);
		response.getWriter().append(array.toString()).append(request.getContentType());
		
	}
}
