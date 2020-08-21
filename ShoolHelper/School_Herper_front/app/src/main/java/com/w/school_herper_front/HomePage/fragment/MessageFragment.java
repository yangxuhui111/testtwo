package com.w.school_herper_front.HomePage.fragment;



import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.w.school_herper_front.MainApplication;
import com.w.school_herper_front.R;
import com.w.school_herper_front.Talk.MessageTalkActivity;
import com.w.school_herper_front.Talk.MessageUserActivity;
import com.w.school_herper_front.bean.Friend;
import com.w.school_herper_front.bean.MessageAdapter;
import com.w.school_herper_front.thread.ClientThread;

import java.util.ArrayList;

import static android.support.constraint.Constraints.TAG;


/**
 * @CONTENT: message List
 * @DEVELOPER: Zhangxixian
 * @DATE: 19/06/04
 *
 * A simple {@link Fragment} subclass.
 */
public class MessageFragment extends Fragment {

    public View view;
    private ImageView enter;
    private static Context mContext ;
    private static ListView listView;
    private Handler mHandler = new Handler();
    private Runnable mRefresh = new Runnable() {
        @Override
        public void run() {
            MainApplication.getInstance().sendAction(ClientThread.GETLIST,"","");
        }
    };

    public MessageFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_message, container, false);
        listView = view.findViewById(R.id.listview);
        mContext = getContext().getApplicationContext();//通过fragment获取应用的上下文，生命周期是整个应用，应用摧毁它才摧毁。
        enter = view.findViewById(R.id.btn_enter);
        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, MessageUserActivity.class);
                mContext.startActivity(intent);
            }
        });
        ArrayList<Friend> list = new ArrayList<>();
        list.add(new Friend("头像1","name1","message1","time1"));
        list.add(new Friend("头像2","name2","message2","time2"));
        list.add(new Friend("头像3","name3","message3","time3"));
        fillItem(list);

        mHandler.post(mRefresh);

        return view;
    }

    @Override
    public void onResume() {
        mHandler.postDelayed(mRefresh, 500);
        super.onResume();
        //onResume这个方法在活动准备好和用户进行交互的时候调用。此时的活动一定位于返回栈的栈顶，并且处于运行状态.
    }

//    @Override
//    public void onDestroy() {
//        MainApplication.getInstance().sendAction(ClientThread.LOGOUT, "", "");
//        super.onDestroy();
//    }

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if(requestCode == 0){
//        }
//    }

    //接收广播，多对一
    public static class GetListReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null) {
                Log.d(TAG, "onReceive");
                String content = intent.getStringExtra(ClientThread.CONTENT);
                if (mContext != null && content != null && content.length() > 0) {
                    int pos = content.indexOf(ClientThread.SPLIT_LINE);//第一次子字符串出现的索引
                    String head = content.substring(0, pos);
                    String body = content.substring(pos + 1);
                    String[] splitArray = head.split(ClientThread.SPLIT_ITEM);

                    //广播接收器，判断是不是接收列表的广播（XXX，，...head  |friend，friend，...body）
                    if (splitArray[0].equals(ClientThread.GETLIST)) {
                        String[] bodyArray = body.split("\\|");//划分成好友列表
                        final ArrayList<Friend> friendList = new ArrayList<Friend>();

                        for (int i = 0; i < bodyArray.length; i++) {
                            String[] itemArray = bodyArray[i].split(ClientThread.SPLIT_ITEM);//单个好友的信息列表
                            if (bodyArray[i].length() > 0 && itemArray != null && itemArray.length >= 4) {
                                //头像；名字；消息；时间
                                friendList.add(new Friend(itemArray[0], itemArray[1], itemArray[2],itemArray[3]));
                            }
                        }
                        if (friendList.size() > 0) {
                            fillItem(friendList);
                        }
                    } else {
                        String hint = String.format("%s\n%s", splitArray[0], body);
                        Toast.makeText(mContext, hint, Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }

    }

    private static void fillItem(final ArrayList<Friend> friendList){

        MessageAdapter messageAdapter = new MessageAdapter(mContext,friendList,R.layout.fragment_message_item);
        listView.setAdapter(messageAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //跳转页面
                Intent intent = new Intent(mContext, MessageTalkActivity.class);
                Friend friendMsg = friendList.get(position);
                intent.putExtra("msg",friendMsg);
                mContext.startActivity(intent);
            }
        });



    }
}


