package com.w.school_herper_front.Talk;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.w.school_herper_front.MainApplication;
import com.w.school_herper_front.R;
import com.w.school_herper_front.SendDatesToServer;
import com.w.school_herper_front.bean.Friend;
import com.w.school_herper_front.thread.ClientThread;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @CONTENT: MessageTalkActivity
 * @DEVELOPER: Zhangxixian
 * @DATE: 19/06/04
 */
public class MessageTalkActivity extends Activity implements View.OnClickListener {
    private static final String TAG = "MessageTalkActivity";

    private static Context mContext;
    private TextView tv_other;
    private EditText et_input;
    private static TextView tv_show;
    private static LinearLayout ll_show;
    private Button btn_send;
    private ImageView back1;
    private String mOtherId;
//    private static int dip_margin;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message_talk);

        mContext = getApplicationContext();
        tv_other = findViewById(R.id.tv_other);
        et_input = findViewById(R.id.et_input);
//        tv_show = findViewById(R.id.tv_show);
        ll_show = findViewById(R.id.ll_show);
        btn_send = findViewById(R.id.btn_send);
        back1 = findViewById(R.id.img_back);
        btn_send.setOnClickListener(this);

//        dip_margin = MetricsUtil.dip2px(mContext,5);

        Intent intent =getIntent();
//        Bundle bundle = getIntent().getExtras();
//        mOtherId = bundle.getString("otherId","");

        if( intent.getSerializableExtra("msg")!=null){
            Friend friend =(Friend) intent.getSerializableExtra("msg");
            mOtherId=friend.getName();
            tv_other.setText(friend.getName());

        }else{
            mOtherId=intent.getStringExtra("name");
            tv_other.setText(intent.getStringExtra("name"));
        }



        //返回设置页
        back1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("id",1);
                setResult(0,intent);
                finish();
            }
        });


    }


    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btn_send){
            if(MainApplication.getInstance().getNickName() == null){
                MainApplication.getInstance().setNickName(SendDatesToServer.user1.getName());
//                MainApplication.getInstance().setNickName("test_myName");
            }

            String body = et_input.getText().toString();
            String append = String.format("%s %s\n%s",
                    MainApplication.getInstance().getNickName(),//昵称
                    new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()), //时间
                    body); //消息体
            appendMsg(Build.SERIAL, append);//自己的消息

            //加入socket队列
            MainApplication.getInstance().sendAction(ClientThread.SENDMSG, mOtherId, body);
            et_input.setText("");

        }
    }

    /**
     * 显示对话
     * @param deviceId
     * @param append 消息内容
     *
     * ll_show ( ll_append(tv_show) )
     */
    private static void appendMsg(String deviceId, String append) {
        //tv_show.setText(tv_show.getText().toString() + append);
        int gravity = deviceId.equals(Build.SERIAL) ? Gravity.RIGHT : Gravity.LEFT;
        int bg_color = deviceId.equals(Build.SERIAL) ? 0xffccccff : 0xffffcccc;

        LinearLayout ll_append = new LinearLayout(mContext);
        LinearLayout.LayoutParams ll_params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

//        ll_params.setMargins(dip_margin, dip_margin, dip_margin, dip_margin);
        ll_params.setMargins(5,5,5,5);
        ll_append.setLayoutParams(ll_params);
        ll_append.setGravity(gravity);

        TextView tv_append = new TextView(mContext);
//        tv_append.setText(tv_show.getText().toString() + append);//填充
        tv_append.setText( append);//填充
        tv_append.setTextColor(Color.BLACK);
        LinearLayout.LayoutParams tv_params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        tv_append.setLayoutParams(tv_params);
        tv_append.setBackgroundColor(bg_color);
        ll_append.addView(tv_append);

        ll_show.addView(ll_append);
    }

    /**
     * 广播接收器
     */
    public static class RecvMsgReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null) {
                Log.d(TAG, "onReceive");
                String content = intent.getStringExtra(ClientThread.CONTENT);
                if (mContext != null && content != null && content.length() > 0) {
                    int pos = content.indexOf(ClientThread.SPLIT_LINE);
                    String head = content.substring(0, pos);
                    String body = content.substring(pos + 1);
                    String[] splitArray = head.split(ClientThread.SPLIT_ITEM);
                    if (splitArray[0].equals(ClientThread.RECVMSG)) {
                        String append = String.format("%s %s\n%s",
                                splitArray[2],
                                splitArray[3],//DateUtil.formatTime(splitArray[3]),
                                body);
                        appendMsg(splitArray[1], append);
                    } else {
                        String hint = String.format("%s\n%s", splitArray[0], body);
                        Toast.makeText(mContext, hint, Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
    }



}
