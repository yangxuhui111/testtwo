package com.w.school_herper_front.HomePage.fragment.task;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.w.school_herper_front.HomePage.fragment.board.board;
import com.w.school_herper_front.R;

import java.util.ArrayList;
import java.util.List;

public class TaskAdapter extends BaseAdapter {
    private Context context;
    private int itemLayout;
    private List<board> boards = new ArrayList<>();

    public TaskAdapter(Context context, int itemLayout, List<board> boards) {
        this.context = context;
        this.itemLayout = itemLayout;
        this.boards = boards;
    }

    @Override
    public int getCount() {
        return boards.size();
    }

    @Override
    public Object getItem(int position) {
        return boards.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (null == convertView){
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            convertView = layoutInflater.inflate(itemLayout,null);
        }
        ImageView myHead = convertView.findViewById(R.id.myhead);
        myHead.setImageResource(boards.get(position).getMyhead());
        TextView name = convertView.findViewById(R.id.name);
        name.setText(boards.get(position).getName());
        TextView endTime = convertView.findViewById(R.id.endtime);
        String e = boards.get(position).getEndTime().substring(0, 4)+"-"+boards.get(position).getEndTime().substring(5,7)+"-"+boards.get(position).getEndTime().substring(8,10);
        endTime.setText(e);
//        endTime.setText(boards.get(position).getEndTime());
        TextView tName = convertView.findViewById(R.id.Tname);
        tName.setText(boards.get(position).getTitle());
        TextView condition = convertView.findViewById(R.id.condition);
        /**
         * 判断当前任务状态
         */

        if ("1".equals(boards.get(position).getState())){
            condition.setText("待接收");
        }
        if ("3".equals(boards.get(position).getState())){
            condition.setText("待验收");
            condition.setTextColor(Color.parseColor("#E51C23"));
        }

        if("2".equals(boards.get(position).getState())){
            condition.setText("待完成");
            condition.setTextColor(Color.parseColor("#E51C23"));
        }
        if ("4".equals(boards.get(position).getState())){
            condition.setText("已完成");
        }
        if ("5".equals(boards.get(position).getState())){
            condition.setText("已截止");
        }




        return convertView;
    }
}
