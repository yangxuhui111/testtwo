package com.w.school_herper_front.HomePage.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.w.school_herper_front.Authen.AuthenNoActivity;
import com.w.school_herper_front.Message.MessageSetActivity;
import com.w.school_herper_front.R;
import com.w.school_herper_front.SendDatesToServer;
import com.w.school_herper_front.wallet.WalletActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class MineFragment extends Fragment {
    
    private TextView setMessage;
    private ImageView moneybag;
    private TextView tv_name;
    private TextView tv_write;
    private TextView tv_phone;
    private TextView tv_realname;
    private TextView tv_sex;

    public MineFragment() {
        // Required empty public constructor
    }

    /*
    * 这是个人主页
    * */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mine, container, false);
        setMessage = view.findViewById(R.id.tv_set);
        moneybag = view.findViewById(R.id.img_money);
        // Inflate the layout for this fragment
        tv_name=view.findViewById(R.id.tv_name);
        tv_phone=view.findViewById(R.id.tv_phone);
        tv_realname=view.findViewById(R.id.tv_realname);
        tv_sex=view.findViewById(R.id.tv_sex);
        tv_write=view.findViewById(R.id.tv_write);
        tv_name.setText(SendDatesToServer.user1.getName());
        tv_phone.setText(SendDatesToServer.user1.getPhone());
        tv_sex.setText(SendDatesToServer.user1.getSex());
        tv_write.setText(SendDatesToServer.user1.getStuWriter());
        tv_realname.setText(SendDatesToServer.user1.getRealname());
        /*
         * 姓名：赵璐
         * 日期：2018.12.12
         * 说明：基本的页面跳转
         * */
        //跳转到设置页面
        setMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MessageSetActivity.class);
                startActivity(intent);
            }
        });
        //跳转到钱包
        moneybag.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), WalletActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 4){
        }
    }
}
