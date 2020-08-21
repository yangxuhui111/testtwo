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
import com.steadyjack.server.service.ConnectionService;
import com.steadyjack.server.service.RewardService;


@Controller
public class ConnectionController {

	@Autowired
	private ConnectionService connectionservice;
	private RewardService rewardservice;
	
	
//	@RequestMapping("/gettask")
//	@ResponseBody
//	public void getTask(ModelMap map,HttpServletRequest request,HttpServletResponse response) throws Exception {
//		request.setCharacterEncoding("utf-8");
//		response.setCharacterEncoding("utf-8");
//		PrintWriter writer = response.getWriter();
//		int posterId=Integer.parseInt(request.getParameter("posterId"));
//		int receiverId=Integer.parseInt(request.getParameter("receiverId"));
//		int rewardId=Integer.parseInt(request.getParameter("rewardId"));
//		Connection con=new Connection();
//		con.setPosterId(posterId);
//		con.setRecriverId(receiverId);
//		con.setRewardId(rewardId);
//		connectionservice.setConnection(con);
//		JSONObject json17 = new JSONObject();
//		JSONArray array = new JSONArray();
//		String state="2";
//		Reward reward=new Reward();
//		List<Reward> rewardList=rewardservice.getAllReward();
//		for(Reward thisReward:rewardList) {
//			if(thisReward.getRewardId()==rewardId) {
//				reward=thisReward;
//			}
//		}
//		reward.setRewardState(state);
//		try {
//			rewardservice.reviseState(reward);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		json17.put("response", "success");
//		array.put(json17);
//		writer.println(array.toString());
//		writer.flush();
//		writer.close();
//	}
}
