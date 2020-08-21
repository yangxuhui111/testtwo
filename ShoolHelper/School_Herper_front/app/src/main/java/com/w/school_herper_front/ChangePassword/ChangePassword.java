package com.w.school_herper_front.ChangePassword;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.w.school_herper_front.HomePage.HomeActivity;
import com.w.school_herper_front.MainActivity;
import com.w.school_herper_front.Message.MessageSetActivity;
import com.w.school_herper_front.R;
import com.w.school_herper_front.SendDatesToServer;
import com.w.school_herper_front.User;
/*
 * 功能：密码修改
 * 开发人：杨旭辉
 * 开发时间：2018.12.19
 */
public class ChangePassword extends Activity {
    private ImageView back1;
    private EditText et1;
    private EditText et2;
    private EditText et3;
    private TextView tv1;

    Handler handler=new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SendDatesToServer.SEND_SUCCESS:
                    Toast.makeText(ChangePassword.this, "修改成功,请重新登录", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ChangePassword.this, MainActivity.class);
                    startActivity(intent);
                    break;
                case SendDatesToServer.SEND_FAIL:
                    Toast.makeText(ChangePassword.this, "数据错误或没有获取回应", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }
    };
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_password);
        back1 = findViewById(R.id.img_back);
        et1=findViewById(R.id.et1);
        et2=findViewById(R.id.et2);
        et3=findViewById(R.id.et3);
        tv1=findViewById(R.id.tv1);
        tv1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String pass1=et1.getText().toString();
                String pass2=et2.getText().toString();
                String pass3=et3.getText().toString();
                String pass4=SendDatesToServer.user1.getPassword();
                if(!pass1.equals(pass4)){
//                    Log.e("原密码",pass4);
                    Log.e("输入的原密码",pass1);
                    Toast.makeText(ChangePassword.this, "原密码不正确", Toast.LENGTH_LONG).show();
                }else if(!pass2.equals(pass3)){
                    Toast.makeText(ChangePassword.this, "两次输入密码不同", Toast.LENGTH_LONG).show();
                }else if(pass1.equals(pass2)){
                    Toast.makeText(ChangePassword.this, "新旧密码不能相同", Toast.LENGTH_LONG).show();
                }
                else{
                    User user=new User(pass2);
                    new SendPassword(handler).SendPassword(user);
                }
            }
        });
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
    }
}
