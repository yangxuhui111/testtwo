package com.w.school_herper_front.HomePage;

import android.content.res.Resources;
import android.graphics.Color;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;
import android.content.Intent;
import com.w.school_herper_front.HomePage.fragment.BoardFragment;
import com.w.school_herper_front.HomePage.fragment.MessageFragment;
import com.w.school_herper_front.HomePage.fragment.MineFragment;
import com.w.school_herper_front.HomePage.fragment.TaskFragment;
import com.w.school_herper_front.R;


import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/*
 * 姓名：尚一飞
 * 日期：2018.12.5
 * 说明：整个页面的tabhost按钮
 * */
public class HomeActivity extends AppCompatActivity {

    private Map<String,View> tabspecViews = new HashMap<>();
    private Map<String,ImageView> imageViewMap = new HashMap<>();
    private Map<String,TextView> textViewMap = new HashMap<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);



        int id = getIntent().getIntExtra("id", 0);

        FragmentTabHost fragmentTabHost = findViewById(android.R.id.tabhost);
        fragmentTabHost.setup(this,getSupportFragmentManager(),android.R.id.tabcontent);
        /*布告栏*/
        TabHost.TabSpec tabSpec1 = fragmentTabHost.newTabSpec("tab1")
                .setIndicator(getTabSpecView("布告栏", R.drawable.board2,"tab1"));
        fragmentTabHost.addTab(tabSpec1, BoardFragment.class,null);
        /*消息*/
        TabHost.TabSpec tabSpec2 = fragmentTabHost.newTabSpec("tab2")
                .setIndicator(getTabSpecView("消息",R.drawable.message1,"tab2"));
        fragmentTabHost.addTab(tabSpec2, MessageFragment.class,null);
        /*任务*/
        TabHost.TabSpec tabSpec3 = fragmentTabHost.newTabSpec("tab3")
                .setIndicator(getTabSpecView("任务",R.drawable.done1,"tab3"));
        fragmentTabHost.addTab(tabSpec3, TaskFragment.class,null);
        /*我*/
        TabHost.TabSpec tabSpec4 = fragmentTabHost.newTabSpec("tab4")
                .setIndicator(getTabSpecView("我",R.drawable.my1,"tab4"));
        fragmentTabHost.addTab(tabSpec4, MineFragment.class,null);

        //        fragmentTabHost.setCurrentTab(0);    设置第一个页面

        /*
        * tabhost的点击实践用于更换点击后的颜色
        * */
        fragmentTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                Set<String> keys = tabspecViews.keySet();
                for (String str:keys){
                    View view = tabspecViews.get(str);
                    ImageView imageView1 = imageViewMap.get("tab1");
                    ImageView imageView2 = imageViewMap.get("tab2");
                    ImageView imageView3 = imageViewMap.get("tab3");
                    ImageView imageView4 = imageViewMap.get("tab4");
                    TextView textView1 = textViewMap.get("tab1");
                    TextView textView2 = textViewMap.get("tab2");
                    TextView textView3 = textViewMap.get("tab3");
                    TextView textView4 = textViewMap.get("tab4");
                    if (tabId.equals("tab1")){
                        imageView1.setImageResource(R.drawable.board2);
                        imageView2.setImageResource(R.drawable.message1);
                        imageView3.setImageResource(R.drawable.done1);
                        imageView4.setImageResource(R.drawable.my1);
                        textView1.setTextColor(Color.parseColor("#F8B511"));
                        textView2.setTextColor(Color.parseColor("#a9a9a9"));
                        textView3.setTextColor(Color.parseColor("#a9a9a9"));
                        textView4.setTextColor(Color.parseColor("#a9a9a9"));
                    }else if (tabId.equals("tab2")){
                        imageView1.setImageResource(R.drawable.board1);
                        imageView2.setImageResource(R.drawable.message2);
                        imageView3.setImageResource(R.drawable.done1);
                        imageView4.setImageResource(R.drawable.my1);
                        textView1.setTextColor(Color.parseColor("#a9a9a9"));
                        textView2.setTextColor(Color.parseColor("#F8B511"));
                        textView3.setTextColor(Color.parseColor("#a9a9a9"));
                        textView4.setTextColor(Color.parseColor("#a9a9a9"));
                    }else if (tabId.equals("tab3")){
                        imageView1.setImageResource(R.drawable.board1);
                        imageView2.setImageResource(R.drawable.message1);
                        imageView3.setImageResource(R.drawable.done2);
                        imageView4.setImageResource(R.drawable.my1);
                        textView1.setTextColor(Color.parseColor("#a9a9a9"));
                        textView2.setTextColor(Color.parseColor("#a9a9a9"));
                        textView3.setTextColor(Color.parseColor("#F8B511"));
                        textView4.setTextColor(Color.parseColor("#a9a9a9"));
                    }else if (tabId.equals("tab4")){
                        imageView1.setImageResource(R.drawable.board1);
                        imageView2.setImageResource(R.drawable.message1);
                        imageView3.setImageResource(R.drawable.done1);
                        imageView4.setImageResource(R.drawable.my2);
                        textView1.setTextColor(Color.parseColor("#a9a9a9"));
                        textView2.setTextColor(Color.parseColor("#a9a9a9"));
                        textView3.setTextColor(Color.parseColor("#a9a9a9"));
                        textView4.setTextColor(Color.parseColor("#F8B511"));
                    }
                }
            }
        });


    }

    private View getTabSpecView(String name, int imageId, String tag){
        LayoutInflater layoutInflater = getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.tabspec_layout,null);
        ImageView imageView = view.findViewById(R.id.imageView);
        imageView.setImageResource(imageId);
        TextView textView = view.findViewById(R.id.textView);
        textView.setText(name);
        if(tag.equals("tab1")){
            textView.setTextColor(Color.parseColor("#F8B511"));
        }
        tabspecViews.put(tag,view);
        imageViewMap.put(tag,imageView);
        textViewMap.put(tag,textView);

        return view;
    }
}
