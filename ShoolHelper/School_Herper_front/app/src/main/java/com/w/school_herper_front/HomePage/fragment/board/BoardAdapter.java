package com.w.school_herper_front.HomePage.fragment.board;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.w.school_herper_front.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 功能：布告栏adapter
 * 开发人：尚一飞
 * 日期：2018.12.12
 * 简介：与BoardFragment  board交互使用
 */
public class BoardAdapter extends BaseAdapter {
    private Context context;
    private int itemLayout;
    private List<board> boards = new ArrayList<>();

    public BoardAdapter(Context context, int itemLayout, List<board> boards) {
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
        TextView title = convertView.findViewById(R.id.title);
        title.setText(boards.get(position).getTitle());
        ImageView image = convertView.findViewById(R.id.image);
        image.setImageResource(boards.get(position).getImage());
        TextView content = convertView.findViewById(R.id.content);
        content.setText(boards.get(position).getContent());
        TextView endTime = convertView.findViewById(R.id.endtime);
        /*转变日期的格式*/
        String e = boards.get(position).getEndTime().substring(0, 4)+"-"+boards.get(position).getEndTime().substring(5,7)+"-"+boards.get(position).getEndTime().substring(8,10);
        endTime.setText(e);
        TextView money = convertView.findViewById(R.id.money);
        money.setText(String.valueOf(boards.get(position).getMoney()));



        return convertView;
    }
}
