package com.w.school_herper_front;

/*
 * 功能：登录页面的前台实现
 * 开发人：赵璐
 * 开发时间：2018.11.30
 *
 * 描述：注册页面，相关xml：activity_main.xml
 */

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.w.school_herper_front.HomePage.HomeActivity;
import com.w.school_herper_front.thread.ClientThread;

public class MainActivity extends AppCompatActivity {
    private EditText phone;
    private EditText password;
    private Button forgotKey;
    private Button changeKey;
    private Button sign;
    private Button register;

    Handler handler=new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SendDatesToServer.SEND_SUCCESS:
                    Toast.makeText(MainActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                    startActivity(intent);
                    break;
                case SendDatesToServer.SEND_FAIL:
                    Toast.makeText(MainActivity.this, "数据发送失败", Toast.LENGTH_SHORT).show();
                    break;
                case SendDatesToServer.SEND_FAIL1:
                    Toast.makeText(MainActivity.this, "用户不存在", Toast.LENGTH_SHORT).show();
                    break;
                case SendDatesToServer.SEND_FAIL2:
                    Toast.makeText(MainActivity.this, "密码错误", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //获取组件A
        phone = findViewById(R.id.signin_phone);
        password = findViewById(R.id.signin_password);
        //获取组件B
        forgotKey = findViewById(R.id.signin_forgotKey);
        sign = findViewById(R.id.signin);
        register = findViewById(R.id.signin_register);


        sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //获取组件数据
                String phone1 = phone.getText().toString();
                String password1 = password.getText().toString();

                /*测试用跳转到HomeActivity
                 * 随时删除
                 * 开发人：尚一飞
                 * */
//                Intent intent = new Intent(MainActivity.this, HomeActivity.class);
//                startActivity(intent);


                //打包在user里
                if(phone1.equals("") || password1.equals("")){
                    Toast.makeText(MainActivity.this, "登录信息不能为空", Toast.LENGTH_LONG).show();
                }else{
                    User user1 = new User(phone1,password1);

                    new SendDatesToServer(handler).SendDatasToServer(user1);


                }


            }
        });



        //忘记密码
        forgotKey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,ForgotKeyActivity.class);
                startActivity(intent);
            }
        });

        //不注册直接登录，界面跳转
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });
    }


}
