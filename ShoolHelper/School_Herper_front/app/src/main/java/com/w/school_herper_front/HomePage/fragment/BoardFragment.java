package com.w.school_herper_front.HomePage.fragment;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;
import com.scwang.smartrefresh.layout.header.BezierRadarHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.w.school_herper_front.HomePage.fragment.board.BoardAdapter;
import com.w.school_herper_front.HomePage.fragment.board.board;
import com.w.school_herper_front.HomePublishActivity;
import com.w.school_herper_front.HomeShowContentActivity;
import com.w.school_herper_front.R;
import com.w.school_herper_front.ServerUrl;
import com.w.school_herper_front.User;

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
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class BoardFragment extends Fragment {

    public View view;
    ListView listView;
    final List<board> boards = new ArrayList<>();
    private String url = new ServerUrl().getUrl();
    private SmartRefreshLayout smartRefreshLayout;

    private EditText editText;
    private ImageView imageView;

    public BoardFragment() {
        // Required empty public constructor
    }


    /*
     * 开发人：尚一飞
     * 这是布告栏页
     * 简介：与BoardFragment  board交互使用
     * */
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_board,container,false);

        imageView = view.findViewById(R.id.find_it);
        editText = view.findViewById(R.id.find_message);

        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boards.clear();
                new BoardAsyncTask().execute();
            }
        });
        /*
         * 悬浮按钮绑定点击事件
         * */
        FloatingActionButton floatingActionButton = view.findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),HomePublishActivity.class);
                startActivity(intent);
            }
        });


        smartRefreshLayout=view.findViewById(R.id.SmartRlBoard);
        smartRefreshLayout.setRefreshHeader(new BezierRadarHeader(getContext()));
        smartRefreshLayout.setRefreshFooter(new BallPulseFooter(getContext()));
        //给智能刷新控件注册下拉刷新监听器
        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        boards.clear();
                        new BoardAsyncTask().execute();
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
         * 布告栏内容
         * 数据库建立好后所有信息从数据库中存取
         * 预计每次遍历10个
         */
//        board board1 = new board(R.drawable.myhead,"我的名字","机场接人","有人这周日有时间吗？愿不愿意去机场接下人，顺便帮忙拎行李....","2018-1-1","￥6.00");
//        boards.add(board1);
//        board board2 = new board(R.drawable.myhead,"我的名字","机场接人",R.drawable.testimage,"有人这周日有时间吗？愿不愿意去机场接下人，顺便帮忙拎行李....","2018-1-1","￥6.00");
//        boards.add(board2);
//        board board3 = new board(R.drawable.myhead,"我的名字","机场接人","有人这周日有时间吗？愿不愿意去机场接下人，顺便帮忙拎行李....","2018-1-1","￥6.00");
//        boards.add(board3);
//        board board4 = new board(R.drawable.myhead,"我的名字","机场接人",R.drawable.testimage,"有人这周日有时间吗？愿不愿意去机场接下人，顺便帮忙拎行李....","2018-1-1","￥6.00");
//        boards.add(board4);


        /**
         * 开启异步任务
         */
        new BoardAsyncTask().execute();
        /**
         * 每个listview绑定点击事件
         */
        listView = view.findViewById(R.id.lv_boards);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.setClass(getContext(), HomeShowContentActivity.class);

                board Tboard = boards.get(position);
                intent.putExtra("user",Tboard);

                startActivity(intent);
            }
        });
        boards.clear();

        return view;
    }


    /**
     * 创建异步任务AsyncTask
     */
    class BoardAsyncTask extends AsyncTask<Void,Void,List<board>>{


        @Override
        protected List<board> doInBackground(Void... voids) {
            String str = editText.getText().toString();
            final StringBuffer stringBuffer = new StringBuffer(url);
            if(str.equals("")){
                stringBuffer.append("/School_Helper_Back/boraditem");
            }else{
                stringBuffer.append("/School_Helper_Back/search");//后台筛选数据的
                stringBuffer.append("?");
                stringBuffer.append("word=");
                stringBuffer.append(str);
            }
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
                    board1.setUserId(object.getInt("userId"));
                    board1.setRewardId(object.getInt("rewardId"));
                    board1.setName(object.getString("name"));
                    board1.setSex(object.getString("sex"));
                    board1.setTitle(object.getString("title"));
                    board1.setContent(object.getString("content"));
                    board1.setRewardTime(object.getString("rewardTime"));
                    board1.setEndTime(object.getString("endTime"));
                    board1.setMoney("￥"+ object.getDouble("money"));
                    board1.setState("1");
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
            BoardAdapter boardAdapter = new BoardAdapter(getContext(), R.layout.board_list_item, boards);
            listView.setAdapter(boardAdapter);
        }
    }



}


