package com.w.school_herper_front.connect;

import android.os.Handler;
import android.util.Log;

import com.w.school_herper_front.SendDatesToServer;
import com.w.school_herper_front.ServerUrl;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SendDatas {
    private static String url=new ServerUrl().getUrl();//服务器地址
    public static final int SEND_SUCCESS=0x124;
    public static final int SEND_FAIL=0x125;
    public JSONObject object;
    public JSONArray array;
    private String servletUrl;
    private Handler handler;
    public static final int FAIL=0x123;
    public static final int CANCEL_SUCCESS=0x128;
    public static final int CANCEL_FAIL=0x129;
    public SendDatas(Handler handler) {
        this.handler=handler;
    }

    /**
     * @function delete reward from back by rewardId
     * @param connect
     */
    public void deleteReward(final Map<String,String> connect ){

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    if (sendGetRequest(connect,url,"utf-8")) {
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
    private  boolean sendGetRequest(Map<String, String> param, String url,String servletUrl) throws Exception {
        // TODO Auto-generated method stub
        StringBuffer sb = new StringBuffer(url);
        if (!url.equals("")&!param.isEmpty()) {
            sb.append("/School_Helper_Back/deletereward");
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
                        SendDatesToServer.user1.setMoney(Double.parseDouble(object.optString("money")));
//                        SendDatesToServer.user1.setMoney(object.getDouble("money"));
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
