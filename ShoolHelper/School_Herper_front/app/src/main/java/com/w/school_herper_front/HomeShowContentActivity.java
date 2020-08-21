package com.w.school_herper_front;
/**
 * @CONTENT: publish content
 * @DEVELOPER: Zhangxixian
 * @DATE: 18/12/16
 */

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.w.school_herper_front.HomePage.HomeActivity;
import com.w.school_herper_front.HomePage.fragment.board.board;
import com.w.school_herper_front.Talk.MessageTalkActivity;
import com.w.school_herper_front.connect.SendData;
import com.w.school_herper_front.connect.SendDatas;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class HomeShowContentActivity extends AppCompatActivity {
    private TextView tvDay,tvHour1,tvHour2,tvMinute1,tvMinute2,tvSecond1,tvSecond2;
    private long leftTime ;      //剩余时间 = 截止时间 - 当前时间
    long day,hour,minute,second;
    private Runnable update_thread ;
    private final Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    leftTime = 0;
                    handler.removeCallbacks(update_thread);
                    break;
                case 2: //任务完成，停止计时，但是leftTime不等于0
                    handler.removeCallbacks(update_thread);
                    break;
                case SendData.SEND_SUCCESS:  //接赏金跳转
                    Toast.makeText(HomeShowContentActivity.this, "成功接下悬赏令~", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(HomeShowContentActivity.this, HomeActivity.class);
                    startActivity(intent);
                    break;
                case SendData.SEND_FAIL:
                    Toast.makeText(HomeShowContentActivity.this, "接赏金失败，请重试~", Toast.LENGTH_SHORT).show();
                    break;
                case SendData.CHANGE_SUCCESS:  //状态改变
                    Toast.makeText(HomeShowContentActivity.this, "确认完成", Toast.LENGTH_SHORT).show();
                    finish();
                    break;
                case SendData.CHANGE_FAIL:
                    finish();
                    Toast.makeText(HomeShowContentActivity.this, "确认失败，", Toast.LENGTH_SHORT).show();
                    break;
                case SendDatas.CANCEL_SUCCESS:   //取消悬赏令
                    Toast.makeText(HomeShowContentActivity.this,"取消成功~",Toast.LENGTH_SHORT).show();
                    finish();
                    break;
                case SendDatas.CANCEL_FAIL:
                    Toast.makeText(HomeShowContentActivity.this,"取消失败，请重试",Toast.LENGTH_SHORT).show();
                    break;
                case SendData.FAIL:
                    Toast.makeText(HomeShowContentActivity.this, "抱歉，连接服务器失败，请重试", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
//            super.handleMessage(msg);
        }

    };
    private ImageView ivUserHead,ivUserSex,ivHead1;
    private TextView tvUserName,tvMoney,tvContent,tvPublishTime,tvName1,tvState, tvDelete,tvType,tvType1;
    private LinearLayout llValue,llvalue1,llUser1,llTail,llNone;
    private Button btnState,btnchat,btnBottomChat,btnGetTask,btnRtn;
    private User my = SendDatesToServer.user1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_show_content);

        getWidgets();
        btnRtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Intent intent = getIntent();
        judgeToShowWidgets(intent);


    }

    /**
     * function:to judge whether to show widgets by intent
     * param:Intent intent;
     */
    private void judgeToShowWidgets(Intent intent){
        String state;
        if(intent.getSerializableExtra("poster") != null){                   //我的接收
            final board poster = (board)intent.getSerializableExtra("poster");
            setState();
            fillViewFromMy(my,llValue);

            eventFromReceiver(poster);  //Button
            fillViewFromList(poster,llvalue1);
            tvType.setText("发布者");
            btnchat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(HomeShowContentActivity.this, MessageTalkActivity.class);
                    i.putExtra("id",poster.getUserId());
                    startActivity(i);
                }
            });
            countTime(poster.getEndTime());
            showDeleteTv("放弃任务","receiver",poster);

        }else if(intent.getSerializableExtra("user")!=null){                 //布告栏
            final board user = (board)intent.getSerializableExtra("user");
            fillViewFromMain(user,llValue);
            countTime(user.getEndTime());
            btnBottomChat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(HomeShowContentActivity.this, MessageTalkActivity.class);
//                    i.putExtra("id",user.getUserId());
                    i.putExtra("name",user.getName());

                    startActivity(i);
                }
            });

            btnGetTask.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new SendData(handler).SendDatasToServer(
                            packgeMap(user.getUserId()+"",
                                    my.getUserId()+"",
                                    user.getRewardId()+"",
                                    "2")
                    );
                }
            });

        }else if(intent.getSerializableExtra("receiver") != null){            //我的发布
            fillViewFromMy(my,llValue);
            final board receiver = (board) intent.getSerializableExtra("receiver");
            setState();
            state = receiver.getState();
            eventFromPoster(receiver);
            if("1"==state){ //1 :无人接下悬赏令
                showDeleteTv("删除任务","poster",receiver);
                llNone.setVisibility(View.VISIBLE);
                llvalue1.setVisibility(View.GONE);
            }else{
                fillViewFromList(receiver,llvalue1);
                tvType.setText("接收者");
                btnchat.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(HomeShowContentActivity.this, MessageTalkActivity.class);
                        i.putExtra("id",receiver.getUserId());
                        startActivity(i);
                    }
                });
            }
            countTime(receiver.getEndTime());
            showDeleteTv("删除任务","poster",receiver);
        }else if(intent.getSerializableExtra("posterFinished")!=null ){        //4 已完成
            fillViewFromMy(my,llValue);
            final board pf = (board)intent.getSerializableExtra("posterFinished");
            setState();
            state = pf.getState();
            eventFromReceiver(pf);
            fillViewFromList(pf,llvalue1);
            tvType.setText("接收者");
            btnchat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(HomeShowContentActivity.this, MessageTalkActivity.class);
                    i.putExtra("id",pf.getUserId());
                    startActivity(i);
                }
            });
            showDeleteTv("删除任务","",null);
        }else if (intent.getSerializableExtra("wait")!= null){               //2,3待处理
            setState();
            fillViewFromMy(my,llValue);
            board wait = (board)intent.getSerializableExtra("wait");
            state = wait.getState();
            fillViewFromList(wait,llvalue1);
            if("2".equals(state)){  //2 待完成 我的接收
                eventFromReceiver(wait);
                tvType.setText("发布者");
                showDeleteTv("放弃任务","receiver",wait);
            }else if("3".equals(state)){ //3 待确认 我的发布
                eventFromPoster(wait);
                tvType.setText("接收者");
                showDeleteTv("删除任务","",wait);
            }
            countTime(wait.getEndTime());
        }
    }
    /**
     * @function: show button event by judge state From Poster;
     * @param: String state;
     * @info: my receive;
     */
    private void eventFromPoster(final board receiver){
        final String state = receiver.getState();
        switch (state){
            case "1": //待接收
                tvState.setText("任务尚未被接收");
                btnState.setVisibility(View.INVISIBLE);
                break;
            case "2"://待完成
                tvState.setText("任务已被接收，等待对方完成");
                btnState.setText("待确认");
                btnState.setBackgroundColor(Color.GRAY);  //待确认按钮为灰色且不可点击，知道对方确认完成
                btnState.setEnabled(false);
                break;
            case "3"://待确认
                tvState.setText("任务已提交，等待您的确认");
                btnState.setText("待确认");
                btnState.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        btnState.setBackgroundColor(Color.GRAY);
                        btnState.setText("已完成");
                        btnState.setEnabled(false);//不可点击

                        //-----------------给揭下悬赏令的用户发布悬赏金，（告知后台）
                        new SendData(handler).changeState(
                                packgeMap(my.getUserId()+"",
                                        receiver.getUserId()+"",
                                        receiver.getRewardId()+"",
                                        "4")  //已完成
                        );

                        // 并发送消息到接收者，提示任务已经被确认，悬赏金已经被发放-----------

                    }
                });
                break;
            case "4"://已完成
                tvState.setText("任务已结束");
                btnState.setText("已完成");
                btnState.setBackgroundColor(Color.GRAY);//灰色
                btnState.setEnabled(false);//不可点击

                Message message = new Message();//结束倒计时
                message.what = 2;
                handler.sendMessage(message);

                break;
            case "5":  //已截止 5
                tvState.setText("任务已截止");
                btnState.setText("已截止");
                btnState.setBackgroundColor(Color.GRAY);//灰色
                btnState.setEnabled(false);//不可点击
                RelativeLayout thisPage = findViewById(R.id.rl_show_content);
                thisPage.setAlpha((float) 0.3);

                Message message1 = new Message();//结束倒计时
                message1.what = 1;
                handler.sendMessage(message1);

                break;

        }
    }

    /**
     * @function: show button event by judge state FromReceiver;
     * @param: String state;
     * @info: my receive;
     */
    private void eventFromReceiver(final board poster){
        final String state = poster.getState();
        switch (state){
            case "2"://待完成
                tvState.setText("已接下悬赏令，等待您的完成");
                btnState.setText("待完成");
                btnState.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        btnState.setBackgroundColor(Color.GRAY);
                        btnState.setText("待确认");
                        btnState.setEnabled(false);//不可点击

                        //-----------------发送消息到发布者，请求确认-----------

                        //----------后台状态改变
                        new SendData(handler).changeState(
                                packgeMap(poster.getUserId()+"",
                                        my.getUserId()+"",
                                        poster.getRewardId()+"",
                                        "3")   //待确认
                        );

                    }
                });

                break;
            case "3"://待确认
                tvState.setText("已提交，等待发布者确认完成");
                btnState.setText("待确认");
                btnState.setBackgroundColor(Color.GRAY);//灰色
                btnState.setEnabled(false);//不可点击
                break;
            case "4"://已完成
                tvState.setText("任务已结束");
                btnState.setText("已完成");
                btnState.setBackgroundColor(Color.GRAY);//灰色
                btnState.setEnabled(false);//不可点击

                Message message = new Message();//结束倒计时
                message.what = 2;
                handler.sendMessage(message);

                break;
            case "5":  //已截止 5
                tvState.setText("任务已截止");
                btnState.setText("已截止");
                btnState.setBackgroundColor(Color.GRAY);//灰色
                btnState.setEnabled(false);//不可点击
                RelativeLayout thisPage = findViewById(R.id.rl_show_content);
                thisPage.setAlpha((float) 0.3);

                Message message1 = new Message();//结束倒计时
                message1.what = 1;
                handler.sendMessage(message1);

                break;

        }
    }

    /**
     * @function connect with back and send message to change reward state;
     * @param posterId
     * @param receiverId
     * @param rewardId
     * @return map
     */
    private Map<String,String> packgeMap(String posterId, String receiverId , String rewardId, String state){
        Map<String , String> map = new HashMap<>();
        map.put("posterId",posterId);
        map.put("receiverId",receiverId);
        map.put("rewardId",rewardId);
        map.put("state",state);
        return map;
    }

    private Map<String,String> packgeMap2(String userId,String posterId, String receiverId , String rewardId, String state){
        Map<String , String> map = new HashMap<>();
        map.put("userId",userId);
        map.put("posterId",posterId);
        map.put("receiverId",receiverId);
        map.put("rewardId",rewardId);
        map.put("state",state);
        return map;
    }
    /**
     * @function TextView choice event differ from state;
     * @param str
     * @param ori
     * @param p
     */
    private void showDeleteTv( String str , String ori,final board p){
        final String state = p.getState();
        tvDelete.setText(str);
        switch (state){
            case "1"://待接收
                tvDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        //---------跳出对话框“您确认要放弃任务吗”
                        new AlertDialog.Builder(HomeShowContentActivity.this)
                                .setTitle("请三思")
                                .setMessage("您确认要删除已发布的悬赏令吗")
                                .setPositiveButton("是的", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        //---------确认，则删除从后台任务
                                        new SendDatas(handler).deleteReward(
                                            packgeMap2(my.getUserId()+"",p.getUserId()+"","",p.getRewardId()+"",
                                                    state)
                                        );
                                    }
                                })
                                .setNegativeButton("我再考虑考虑", null)
                                .create()
                                .show();

                    }
                });
            case "2": //待完成,（已接下任务状态，点击放弃任务要扣信誉值）
                if("receiver".equals(ori)){ //接收者要放弃任务
                    tvDelete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            //跳出对话框以警示会降低信誉值-----
                            new AlertDialog.Builder(HomeShowContentActivity.this)
                                    .setTitle("请三思")
                                    .setMessage("您要放弃已接下的悬赏令吗？")
                                    .setPositiveButton("是的，我意已决", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                            //从该用户的“我的接收”中删除悬赏令
                                            new SendDatas(handler).deleteReward(
                                                    packgeMap2(my.getUserId()+"",p.getUserId()+"",my.getUserId()+"",
                                                            p.getRewardId()+"",p.getState())
                                            );
                                            //改变任务的状态为未接单，
                                            new SendData(handler).changeState(
                                                    packgeMap(p.getUserId()+"","",
                                                            p.getRewardId()+"","1")
                                            );

                                            //---------同时发送消息通知任务发布者，并扣除接单者信誉值----------

                                        }
                                    })
                                    .setNegativeButton("我再考虑考虑",null)
                                    .create()
                                    .show();

                        }
                    });
                }else if("poster".equals(ori)){ //发布者要删除任务
                    tvDelete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            //----------------跳出对话框以警示会降低信誉值-----
                            //--“您的悬赏令已被接下，确定要放弃任务吗，会扣除5%的悬赏金哦，并且会降低信誉值”
                            new AlertDialog.Builder(HomeShowContentActivity.this)
                                    .setTitle("请三思")
                                    .setMessage("您确定要删除已被揭下的的悬赏令吗？要赔付5%的违约金并降低信誉值哦")
                                    .setPositiveButton("已阅，坚持删除", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                            //发布者删除悬赏令,接受者也要删除
                                            new SendDatas(handler).deleteReward(
                                                    packgeMap2(my.getUserId()+"",my.getUserId()+"",p.getUserId()+"",
                                                            p.getRewardId()+"",state)
                                            );

                                            //---------同时发送消息通知任务发布者，并扣除接单者信誉值----------
                                            //-------通知任务发布者，告知任务已取消成功，退还剩余悬赏金，

                                        }
                                    })
                                    .setNegativeButton("我再考虑考虑",null)
                                    .create()
                                    .show();


                        }
                    });
                }

                break;
            case "3": //待确认
                tvDelete.setVisibility(View.INVISIBLE);
                break;
            case "4": //已完成
                tvDelete.setVisibility(View.INVISIBLE);
                break;
            case "5": //已截止
                tvDelete.setVisibility(View.INVISIBLE);
                break;


        }
    }
    /**
     * function:get all widgets
     * param:void
     * return:void
     * */
    private void getWidgets(){
        tvDay = findViewById(R.id.showcontent_tv_day);
        tvHour1 = findViewById(R.id.showcontent_tv_hour1);
        tvMinute1 = findViewById(R.id.showcontent_tv_minute1);
        tvSecond1 = findViewById(R.id.showcontent_tv_second1);
        tvHour2 = findViewById(R.id.showcontent_tv_hour2);
        tvMinute2 = findViewById(R.id.showcontent_tv_minute2);
        tvSecond2 = findViewById(R.id.showcontent_tv_second2);
        ivUserHead = findViewById(R.id.showcontent_iv_head);
        ivUserSex = findViewById(R.id.showcontent_iv_sex);
        tvUserName = findViewById(R.id.showcontent_tv_name);
        llValue = findViewById(R.id.showcontent_ll_value);
        tvMoney = findViewById(R.id.showcontent_tv_money);
        tvContent = findViewById(R.id.showcontent_tv_content);
        tvPublishTime = findViewById(R.id.showcontent_tv_publish_time);
        llUser1 = findViewById(R.id.showcontent_ll_user1);
        tvName1 = findViewById(R.id.showcontent_tv_name1);
        llvalue1 = findViewById(R.id.showcontent_ll_value1);
        btnchat = findViewById(R.id.showcontent_btn_user1_chat);
        btnState = findViewById(R.id.showcontent_btn_submit);
        ivHead1 = findViewById(R.id.showcontent_iv_head1);
        tvState = findViewById(R.id.showcontent_tv_state);
        llTail = findViewById(R.id.showcontent_ll_tail);
        tvDelete = findViewById(R.id.showcontent_tv_choice);
        llNone = findViewById(R.id.showcontent_ll_none);
        tvType = findViewById(R.id.showcontent_tv_type);
        tvType = findViewById(R.id.showcontent_tv_type1);
        btnBottomChat = findViewById(R.id.showcontent_btn_botttom_chat);
        btnGetTask = findViewById(R.id.showcontent_btn_gettask);
        btnRtn = findViewById(R.id.showcontent_btn_rtn);

    }

    /**
     * function : show common state when enter from my riceive and my post;
     * param :
     */
    private void setState(){
        btnState.setVisibility(View.VISIBLE);   //button
        llUser1.setVisibility(View.VISIBLE);   //LinearLayout
        tvState.setVisibility(View.VISIBLE);
        tvDelete.setVisibility(View.VISIBLE);
        llTail.setVisibility(View.GONE);        //隐藏tail

    }

    /**
     * function: fill header where date from ;
     * return :User; LinearLayout
     * info:except center and bottom(fillViewFromList) where date filled from list;
     */
    private void fillViewFromMy(User user,LinearLayout layout){
//        ivUserHead.setImageResource();
        tvUserName.setText(user.getName());
        if("女".equals(user.getSex())){
            ivUserSex.setImageResource(R.drawable.girl);
        }else{
            ivUserSex.setImageResource(R.drawable.boy);
        }
//        int value = user.getValue();
//        showValue(layout,value);
        showValue(layout,10);

    }

    /**
     * function: fill content in center and bottom;
     * return :board ; LinearLayout
     * info:except header(fillViewFromMy) where date  filled from my;
     */
    private void fillViewFromList(board user,LinearLayout layout){
        tvMoney.setText("赏金："+user.getMoney());
//        ivHead1.setImageResource();
        tvName1.setText(user.getName());

//        int value = user.getValue();
//        showValue(layout,value);
        showValue(layout,10);

        tvContent.setText(user.getContent());
        tvPublishTime.setText(user.getRewardTime());
    }

    /**
     * function: show user informateion when enter from main activity;
     * return :board ; LinearLayout
     * info:
     */
    private void fillViewFromMain(board user,LinearLayout layout) {
//        ivUserHead.setImageResource();
        tvUserName.setText(user.getName());
        if("女".equals(user.getSex())){
            ivUserSex.setImageResource(R.drawable.girl);
        }else{
            ivUserSex.setImageResource(R.drawable.boy);
        }

//        int value = user.getValue();
//        showValue(layout,value);
        showValue(layout,15);

        tvMoney.setText("赏金："+user.getMoney());
        tvContent.setText(user.getContent());
        tvPublishTime.setText(user.getRewardTime());

    }

    /**
     * function:show value
     * param:LinearLayout Layout; value
     */
    private void showValue(LinearLayout layout,int value){
        layout.removeAllViews();
        if(value<5){
            ImageView imageView = new ImageView(this);
            imageView.setLayoutParams(new LinearLayout.LayoutParams(20,20));
            imageView.setImageResource(R.drawable.love);
            llValue.addView(imageView);
        }else if(value<10){
            for(int i =0 ;i<2;i++){
                ImageView imageView = new ImageView(this);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(20,20);
                lp.setMargins(10,0,0,0);
                imageView.setLayoutParams(lp);
                imageView.setImageResource(R.drawable.love);
                layout.addView(imageView);
            }
        }else if(value<15){
            ImageView imageView = new ImageView(this);
            imageView.setLayoutParams(new LinearLayout.LayoutParams(20,20));
            imageView.setImageResource(R.drawable.star);
            layout.addView(imageView);
        }else if(value<20){
            for(int i =0 ;i<2;i++){
                ImageView imageView = new ImageView(this);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(20,20);
                lp.setMargins(10,0,0,0);
                imageView.setLayoutParams(lp);
                imageView.setImageResource(R.drawable.star);
                layout.addView(imageView);
            }
        }else{
            ImageView imageView = new ImageView(this);
            imageView.setLayoutParams(new LinearLayout.LayoutParams(20,20));
            imageView.setImageResource(R.drawable.diamond);
            layout.addView(imageView);
        }
    }

    /**
     * function:get deadline to count time and invoke other relavent function;
     * param:String deadline
     */
    private void countTime(String deadline){
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try
        {
            Date d1 = df.parse(df.format(new Date()));//当前时间
            Date d2 = df.parse(deadline);
//            Date d1 = df.parse("2018-12-12 13:31:40");
//            Date d2 = df.parse("2018-12-22 11:30:24");
            leftTime = (d2.getTime() - d1.getTime())/1000;
            countDown();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        leftTime = 0;
        handler.removeCallbacks(update_thread);
    }

    /**
     * function:count left time
     * param:void
     * return:void
     * */
    public void cutTime() {
            day = leftTime / (60 * 60 * 24);
            hour = (leftTime - day*( 60 * 60 * 24))/( 60 * 60);
            minute = (leftTime-day*(60 * 60 * 24)-hour*(60 * 60))/60;
            second = leftTime - day*(24*60*60) - hour*(60*60) - minute*60;

    }
    /**
    * function:count down
    * param:void
    * return:void
    * */
    private void countDown() {
        update_thread = new Runnable() {
            @Override
            public void run() {
//                leftTime --;
                cutTime();
                if (leftTime >= 0) {
                    tvDay.setText("距离任务截止还有" + String.valueOf(day) + "天");
                    tvHour1.setText(String.valueOf(hour / 10));
                    tvHour2.setText(String.valueOf(hour % 10));
                    tvMinute1.setText(String.valueOf(minute / 10));
                    tvMinute2.setText(String.valueOf(minute % 10));
                    tvSecond1.setText(String.valueOf(second / 10));
                    tvSecond2.setText(String.valueOf(second % 10));

                    handler.postDelayed(this, 1000);//每一秒执行一次

                } else {//倒计时结束
                    //处理业务流程
                    //发送消息，结束倒计时
                    Message message = new Message();
                    message.what = 1;
                    handler.sendMessage(message);
                }
                leftTime --;
            }
        };
        update_thread.run();
    }


}



