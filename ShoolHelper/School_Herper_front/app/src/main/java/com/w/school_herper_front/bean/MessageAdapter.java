package com.w.school_herper_front.bean;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.w.school_herper_front.R;

import java.util.ArrayList;
import java.util.List;

public class MessageAdapter extends BaseAdapter {

    private Context context;
    private List<Friend> friendList = new ArrayList<>();
    private int itemLayout;

    public MessageAdapter(Context context, List<Friend> friendList, int itemLayout) {
        this.context = context;
        this.friendList = friendList;
        this.itemLayout = itemLayout;
    }

    @Override
    public int getCount() {
        return friendList.size();
    }

    @Override
    public Object getItem(int position) {
        return friendList.get(position);
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
        //填充
//        XCRoundImageView head = convertView.findViewById(R.id.item_iv_head);
        TextView name = convertView.findViewById(R.id.item_name);
        TextView message = convertView.findViewById(R.id.item_message);
        TextView time = convertView.findViewById(R.id.item_time);
        name.setText(friendList.get(position).getName());
        message.setText(friendList.get(position).getMessage());
        time.setText(friendList.get(position).getTime());
        return convertView;
    }
}
