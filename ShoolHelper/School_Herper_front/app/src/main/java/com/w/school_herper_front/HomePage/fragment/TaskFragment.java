package com.w.school_herper_front.HomePage.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;
import com.scwang.smartrefresh.layout.header.BezierRadarHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.w.school_herper_front.HomePage.fragment.board.board;
import com.w.school_herper_front.HomePage.fragment.task.TaskAdapter;
import com.w.school_herper_front.HomePage.fragment.task.TaskFirstActivity;
import com.w.school_herper_front.HomePage.fragment.task.TaskSecondActivity;
import com.w.school_herper_front.HomePage.fragment.task.TaskThirdActivity;
import com.w.school_herper_front.HomePage.fragment.task.task;
import com.w.school_herper_front.HomeShowContentActivity;
import com.w.school_herper_front.R;
import com.w.school_herper_front.SendDatesToServer;
import com.w.school_herper_front.ServerUrl;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.loader.ImageLoader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class TaskFragment extends Fragment {
    private Banner myBanner;
    View view;
    ListView listView;
    final List<board> boards = new ArrayList<>();
    private String url = new ServerUrl().getUrl();
    List<Integer> ImageUrlData;
    List<String>ContentData;
    private SmartRefreshLayout smartRefreshLayout;
    /*
    * 这是任务页
    * */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_task,container,false);
        initBanner();


        // Inflate the layout for this fragment
        //View view = inflater.inflate(R.layout.fragment_task, container, false);

        /**
         * 绑定顶部三个按钮点击事件
         */
        RelativeLayout linearLayout1 = view.findViewById(R.id.tab1);
        RelativeLayout linearLayout2 = view.findViewById(R.id.tab2);
        RelativeLayout linearLayout3 = view.findViewById(R.id.tab3);
        linearLayout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), TaskFirstActivity.class);
                startActivity(intent);
            }
        });
        linearLayout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), TaskSecondActivity.class);
                startActivity(intent);
            }
        });
        linearLayout3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), TaskThirdActivity.class);
                startActivity(intent);
            }
        });


        smartRefreshLayout=view.findViewById(R.id.SmartRlTask);
        smartRefreshLayout.setRefreshHeader(new BezierRadarHeader(getContext()));
        smartRefreshLayout.setRefreshFooter(new BallPulseFooter(getContext()));
        //下拉刷新
        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        boards.clear();
                        new TaskAsyncTask().execute();
                        smartRefreshLayout.finishRefresh();
                    }
                },200);
            }
        });
        smartRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                //...
                smartRefreshLayout.finishLoadMore();
            }
        });
        /**
         * 列出所有任务Adapter
         * 数据库建立后从数据库获取
         * 预计获取10个
         */

//        task task1 = new task(R.drawable.myhead,"我的名字","2018-12-12","代取快递","待验收");
//        tasks.add(task1);
//        task task2 = new task(R.drawable.myhead,"我的名字","2018-12-12","代取快递","待验收");
//        tasks.add(task2);
//        task task3 = new task(R.drawable.myhead,"我的名字","2018-12-12","代取快递","待验收");
//        tasks.add(task3);

        new TaskAsyncTask().execute();
        listView = view.findViewById(R.id.lv_task);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.setClass(getContext(), HomeShowContentActivity.class);

                board Tboard = boards.get(position);
                intent.putExtra("wait",Tboard);

                startActivity(intent);
            }
        });
        boards.clear();


        return view;
    }

    /**
     * 轮播图方法
     */
    private void initBanner()
    {
        myBanner=(Banner)view.findViewById(R.id.banner);

        ImageUrlData=new ArrayList<>();
        ContentData=new ArrayList<>();
        ImageUrlData.add(R.drawable.guanggao1);
        ImageUrlData.add(R.drawable.guanggao2);
        ImageUrlData.add(R.drawable.guanggao3);
        ImageUrlData.add(R.drawable.guanggao4);
        ContentData.add("我就是测试的什么也没有用，你就将就看吧1");
        ContentData.add("我就是测试的什么也没有用，2");
        ContentData.add("我我没有作用，你就将就看吧3");
        ContentData.add("实在没有什么测试了就写这个吧4");

        //设置样式
        myBanner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE);
        //设置图片加载器
        myBanner.setImageLoader(new MyLoader());
        //设置图片加载地址
        myBanner.setImages(ImageUrlData);
        //轮播图片的文字
        myBanner.setBannerTitles(ContentData);
        //设置轮播的动画效果
        myBanner.setBannerAnimation(Transformer.Default);
        //设置轮播间隔时间
        myBanner.setDelayTime(3000);
        //设置是否为自动轮播，默认是true
        myBanner.isAutoPlay(true);
        //设置指示器的位置，小点点，居中显示
        myBanner.setIndicatorGravity(BannerConfig.CENTER);
        //开始调用的方法，启动轮播图。
        myBanner.start();

    }


    //自定义的图片加载器
    private class MyLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            //Glide设置图片
            Glide.with(getActivity()).load(path).into(imageView);

        }

    }

    /**
     * 创建异步任务
     */
    class TaskAsyncTask extends AsyncTask<Void,Void,List<board>>{

        @Override
        protected List<board> doInBackground(Void... voids) {
            final StringBuffer stringBuffer = new StringBuffer(url);
            stringBuffer.append("/School_Helper_Back/task");
            stringBuffer.append("?userId=");
            stringBuffer.append(URLEncoder.encode(String.valueOf(SendDatesToServer.user1.getUserId())));
            HttpURLConnection conn = null;
            try {
                conn = (HttpURLConnection) new URL(stringBuffer.toString()).openConnection();
                conn.setRequestMethod("GET");
                conn.setRequestProperty("charset","UTF-8");
            } catch (IOException e) {
                e.printStackTrace();
            }

            InputStream is = null;

            try {
                is = conn.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(is);
                BufferedReader reader = new BufferedReader(inputStreamReader);
                String res = reader.readLine();
                JSONArray array = new JSONArray(res);
                for (int i=0; i < array.length(); i++){
                    JSONObject object = array.getJSONObject(i);
                    board board1 = new board();
                    board1.setMyhead(R.drawable.myhead);
                    board1.setUserId(object.optInt("userId"));
                    board1.setRewardId(object.optInt("rewardId"));
                    board1.setName(object.optString("name"));
                    board1.setSex(object.optString("sex"));
                    board1.setTitle(object.optString("title"));
                    board1.setContent(object.optString("content"));
                    board1.setRewardTime(object.optString("rewardTime"));
                    board1.setEndTime(object.optString("endTime"));
                    board1.setMoney("￥"+ object.optDouble("money"));
                    board1.setState(object.optString("state"));
                    boards.add(board1);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return boards;
        }

        protected void onPostExecute(List<board> boards){
            super.onPostExecute(boards);
            TaskAdapter taskAdapter = new TaskAdapter(getContext(),R.layout.task_list_item,boards);
            listView.setAdapter(taskAdapter);
        }
    }

}
