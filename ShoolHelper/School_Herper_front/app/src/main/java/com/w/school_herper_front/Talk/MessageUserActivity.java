package com.w.school_herper_front.Talk;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.w.school_herper_front.R;

public class MessageUserActivity extends Activity {
    private ImageView back1;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message_user);
        back1 = findViewById(R.id.img_back);
        /*
         * 姓名：赵璐
         * 日期：2018.12.24
         * 说明：基本的页面跳转
         * */
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
}
