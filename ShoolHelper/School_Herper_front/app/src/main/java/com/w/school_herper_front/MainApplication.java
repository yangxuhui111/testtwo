package com.w.school_herper_front;
/**
 * 作用：保存全局数据,新建一个类来继承Application,也可以使用getApplication()用于获取Application的一个对象实例：
 */

import android.app.Application;
import android.os.Build;
import android.os.Message;
import android.util.Log;

import com.w.school_herper_front.thread.ClientThread;

import java.text.SimpleDateFormat;

public class MainApplication extends Application {
    private static final String TAG = "MainApplication";

    private static MainApplication mApp = null;
    private String mNickName = null;
    private ClientThread mClientThread;

    /**
     * application 单例
     * @return
     */
    public static MainApplication getInstance() {
        if(mApp ==null){
            mApp = new MainApplication();
        }
        return mApp;
    }


    private MainApplication() {
        super.onCreate();
//        mApp = this;
        mClientThread = new ClientThread(mApp);

    }


    public void sendAction(String action, String otherId, String msgText) {
//        String content = String.format("%s,%s,%s,%s,%s%s%s\r\n",
//                action, Build.SERIAL, getNickName(), DateUtil.getNowTime(),
//                otherId, ClientThread.SPLIT_LINE, msgText);

                //包头格式：动作名称,设备编号,昵称,时间,对方设备编号 |
                String content = String.format("%s,%s,%s,%s,%s%s%s\r\n",
                action, "fasonngdexiaoxi", getNickName(), new SimpleDateFormat("YYYY-MM-DD HH:mm:ss").format( System.currentTimeMillis()),
                otherId, ClientThread.SPLIT_LINE, msgText);//msgTest：每一条发出的消息

        Log.d(TAG, "sendAction : " + content);
        Message msg = Message.obtain();//获取message对象
        msg.obj = content;
        if (mClientThread==null || mClientThread.mRecvHandler==null) {
            Log.d(TAG, "mClientThread or its mRecvHandler is null");
        } else {
            mClientThread.mRecvHandler.sendMessage(msg);
        }
    }
    
    public void setNickName(String nickName) {
        mApp.mNickName = nickName;
    }

    public String getNickName() {
        return mApp.mNickName;

    }
}