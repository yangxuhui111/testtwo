package com.w.school_herper_front.connect;


/**
 * CONTENT:SENDDATA
 * DEVELOPER:Zhangxixina
 * Date:18/12/18
 */
;
import android.os.Handler;
import android.util.Log;

import com.w.school_herper_front.SendDatesToServer;
import com.w.school_herper_front.ServerUrl;
import com.w.school_herper_front.User;
import com.w.school_herper_front.bean.RewardBean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SendData{
    private static String url=new ServerUrl().getUrl();//服务器地址
    public static final int FAIL=0x123;
    public static final int SEND_SUCCESS=0x124;
    public static final int SEND_FAIL=0x125;
    public static final int CHANGE_SUCCESS=0x126;
    public static final int CHANGE_FAIL=0x127;
    public static final int CANCEL_SUCCESS=0x128;
    public static final int CANCEL_FAIL=0x129;
//    public static final int SUBMIT_SUCCESS=0x130;
//    public static final int SUBMIT_FAIL=0x131;
    public JSONObject object;
    public JSONArray array;
    private String servletUrl;
    private Handler handler;

    public SendData(Handler handler) {
        this.handler=handler;
    }

    /**
     * @function delete reward from back by rewardId
     * @param connect
     */
    public void deleteReward(final Map<String,String> connect ){
        servletUrl ="/School_Helper_Back/DeleteRewardServlet";
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    if (sendGetRequest(connect,url,servletUrl)) {
                        if(object.getString("response").equals("success")){
                            handler.sendEmptyMessage(CANCEL_SUCCESS);//通知主线程数据发送成功
                        }else{
                            handler.sendEmptyMessage(CANCEL_FAIL);//将数据发送给服务器失败或者用户名不存在
                        }
                    }else {
                        handler.sendEmptyMessage(FAIL);//将数据发送给服务器失败
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * @function after poster ensure task
     * @param connect
     */
    public void changeState(final Map<String,String> connect ){
        servletUrl ="/School_Helper_Back/changestate";
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    if (sendGetRequest(connect,url,servletUrl)) {
                        if(object.getString("response").equals("success")){
                            handler.sendEmptyMessage(CHANGE_SUCCESS);//通知主线程数据发送成功
                        }else{
                            handler.sendEmptyMessage(CHANGE_FAIL);//将数据发送给服务器失败或者用户名不存在
                        }
                    }else {
                        handler.sendEmptyMessage(FAIL);//将数据发送给服务器失败
                    }
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }).start();
    }


    /**
     * @function send datas where come from showContent Page;
     * @param connect
     */
    public void SendDatasToServer(final Map<String,String> connect ){
        servletUrl ="/School_Helper_Back/gettask";
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    if (sendGetRequest(connect,url,servletUrl)) {
                        if(object.getString("response").equals("success")){
                            handler.sendEmptyMessage(SEND_SUCCESS);//通知主线程数据发送成功
                        }else{
                            handler.sendEmptyMessage(SEND_FAIL);//将数据发送给服务器失败或者用户名不存在
                        }
                    }else {
                        handler.sendEmptyMessage(FAIL);//将数据发送给服务器失败
                    }
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * @function send datas where come from publish Page;
     * @param reward
     */
    public void SendDatasToServer(RewardBean reward) {
        servletUrl ="/School_Helper_Back/publishreward";
        final Map<String, String> map=new HashMap<String,String>();
        map.put("posterId", SendDatesToServer.user1.getUserId()+"");
        map.put("rewardTitle",reward.getRewardTitle());
        map.put("rewardContent",reward.getRewardContent()) ;
        map.put("publishTime",reward.getPublishTime()+"");
        map.put("deadline",reward.getDeadline()+"");
        map.put("rewardMoney",reward.getRewardMoney()+"");

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    if (sendGetRequest(map,url,servletUrl)) {
                        if(object.getString("response").equals("success")){
                            handler.sendEmptyMessage(SEND_SUCCESS);//通知主线程数据发送成功
                        }else{
                            handler.sendEmptyMessage(SEND_FAIL);//将数据发送给服务器失败或者用户名不存在
                        }
                    }else {
                        handler.sendEmptyMessage(FAIL);//将数据发送给服务器失败
                    }
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }).start();
    }

//    private  boolean sendGetRequest(Map<String, String> param, String url,String encoding) throws Exception {
//        // TODO Auto-generated method stub
//        StringBuffer sb = new StringBuffer(url);
//        if (!url.equals("")&!param.isEmpty()) {
//            sb.append("/School_Helper_Back/publishreward");
//            sb.append("?");
//            for (Map.Entry<String, String>entry:param.entrySet()) {
//                sb.append(entry.getKey()+"=");
//                sb.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
//                sb.append("&");
//            }
//            sb.deleteCharAt(sb.length()-1);//删除字符串最后 一个字符“&”
//        }

        private  boolean sendGetRequest(Map<String, String> param, String url,String servletUrl) throws Exception {
            // TODO Auto-generated method stub
            StringBuffer sb = new StringBuffer(url);
            if (!url.equals("")&!param.isEmpty()) {
//                sb.append("/School_Helper_Back/publishreward");
                sb.append(servletUrl);
                sb.append("?");
                for (Map.Entry<String, String>entry:param.entrySet()) {
                    sb.append(entry.getKey()+"=");
                    sb.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
                    sb.append("&");
                }
                sb.deleteCharAt(sb.length()-1);//删除字符串最后 一个字符“&”
            }

        //1.构建一个OkHttpClient
        OkHttpClient okHttpClient = new OkHttpClient();
        //2.构建一个Request
        final Request request = new Request.Builder()
                .url(sb.toString())
                .build();
        //3.获得Call对象
        Call call = okHttpClient.newCall(request);

        Response response = call.execute();
        //响应成功
        if(response.isSuccessful()){
            String json = response.body().string();
            try {
                array = new JSONArray(json);
                for (int i = 0; i < array.length(); i++) {
                    object = array.getJSONObject(i);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return true;



//        HttpURLConnection conn=(HttpURLConnection) new URL(sb.toString()).openConnection();
//        conn.setReadTimeout(5000);
//        conn.setRequestMethod("GET");
//        conn.setRequestProperty("charset","UTF-8");
//        conn.setConnectTimeout(5000);
//        conn.setDoInput(true);
//        if (conn.getResponseCode()==200) {
//            InputStream in = conn.getInputStream();
//            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
//            String res=reader.readLine();
//            object = new JSONObject(res);
//
//            return true;
//        }else{
//            return false;
//        }
    }

}

