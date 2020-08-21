package com.w.school_herper_front;

/*
 * 功能：通过GET方式向服务器发送登录用户数据
 * 开发人：赵璐
 * 开发时间：2018.11.27
 *
 * 描述：注册页面，相关xml：activity_main.xml
 */

import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.w.school_herper_front.thread.ClientThread;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SendDatesToServer {
    private static String url=new ServerUrl().getUrl();//服务器地址
    public static final int SEND_SUCCESS=0x123;
    public static final int SEND_FAIL1=0x124;
    public static final int SEND_FAIL2=0x125;
    public static final int SEND_FAIL=0x126;
    public JSONObject object;
    public static User user1;
    public JSONArray array;
    private Handler handler;
    public SendDatesToServer(Handler handler) {
        // TODO Auto-generated constructor stub
        this.handler=handler;
    }


    public void SendDatasToServer(User user) {
        // TODO Auto-generated method stub
        final Map<String, String> map=new HashMap<String, String>();
        map.put("phone",user.getPhone());
        map.put("password", user.getPassword());
        new Thread(new Runnable() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                try {
                    if (sendGetRequest(map,url,"utf-8")) {
                        if(object.getString("success").equals("登录成功")){
                            handler.sendEmptyMessage(SEND_SUCCESS);//通知主线程数据发送成功
                        }else if(object.getString("error").equals("用户不存在")){
                            handler.sendEmptyMessage(SEND_FAIL1);//将数据发送给服务器失败或者用户名不存在
                        }else if (object.getString("error").equals("密码错误")){
                            handler.sendEmptyMessage(SEND_FAIL2);//将数据发送给服务器失败或者密码错误
                        }
                    }else {
                        handler.sendEmptyMessage(SEND_FAIL);//将数据发送给服务器失败
                    }
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }).start();
    }


    private  boolean sendGetRequest(Map<String, String> param, String url,String encoding) throws Exception {
        // TODO Auto-generated method stub
        StringBuffer sb = new StringBuffer(url);
        if (!url.equals("")&!param.isEmpty()) {
            sb.append("/School_Helper_Back/login");
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
                    try {
                        object = array.getJSONObject(i);
                        user1 = new User();
                        user1.setUserId(object.getInt("userId"));
                        user1.setPhone(object.getString("phone"));
                        user1.setMoney(object.getInt("money"));
                        user1.setPassword(object.getString("password"));
                        user1.setImage(object.getString("image"));
                        user1.setPublish(object.getInt("publish"));
                        user1.setStuName(object.getString("stuNum"));
                        user1.setTook(object.getInt("took"));
                        user1.setValue(object.getInt("value"));
                        user1.setSchoolId(object.getString("schoolId"));
                        user1.setName(object.getString("userName"));
                        user1.setRealname(object.getString("realname"));
                        user1.setIdentification(object.getString("identification"));
                        user1.setStuWriter(object.getString("signature"));
                        user1.setSex(object.getString("sex"));
                        MainApplication.getInstance().sendAction(ClientThread.LOGIN, user1.getName(), "");
                        Log.e("id", user1.getName());
                        Log.e("success",object.getString("success"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return true;
    }
}
