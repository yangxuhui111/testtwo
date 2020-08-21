package com.w.school_herper_front.Authen;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.w.school_herper_front.ChangePassword.ChangePassword;
import com.w.school_herper_front.Message.MessageSetActivity;
import com.w.school_herper_front.R;

public class AuthenNoActivity extends Activity {
    private ImageView back1;
    private LinearLayout getauthen;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.authen_no);
        back1 = findViewById(R.id.img_back);
        getauthen = findViewById(R.id.go_authenget);

        /*
         * 姓名：赵璐
         * 日期：2018.12.12
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

        //上传认证跳转
        getauthen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AuthenNoActivity.this,AuthenGetActivity.class);
                startActivity(intent);
            }
        });
    }


}
