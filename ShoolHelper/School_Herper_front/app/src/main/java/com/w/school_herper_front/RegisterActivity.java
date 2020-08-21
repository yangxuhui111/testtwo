package com.w.school_herper_front;

/*
 * 功能：注册页面的前台实现
 * 开发人：赵璐
 * 开发时间：2018.11.27
 *
 * 描述：注册页面，相关xml：activity_main.xml
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends Activity {
    private EditText phone;
    private EditText password;
    private EditText name;
    private Spinner school;
    private EditText stuNumber;
    private TextView register;
    private TextView login;

    Handler handler=new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SendDateToServer.SEND_SUCCESS:
                    Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(RegisterActivity.this,MainActivity.class);
                    startActivity(intent);
                    break;
                case SendDateToServer.SEND_FAIL1:
                    Toast.makeText(RegisterActivity.this, "用户已存在", Toast.LENGTH_SHORT).show();
                    break;
                case SendDateToServer.SEND_FAIL:
                    Toast.makeText(RegisterActivity.this, "注册失败", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);
        //获取组件A
        password  = findViewById(R.id.tv_password);
        phone = findViewById(R.id.tv_phone);
        name = findViewById(R.id.tv_name);
        school = findViewById(R.id.sp_school);
        stuNumber = findViewById(R.id.tv_stuNumber);
        //获取组件B
        register = findViewById(R.id.tv_register);
        login = findViewById(R.id.tv_login);



        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //获取组件数据
                String phone1 = phone.getText().toString();
                String password1 = password.getText().toString();
                String name1 = name.getText().toString();
                String school1 = school.getSelectedItem().toString();
                String stuNumber1 = stuNumber.getText().toString();
                //打包在user里
                if(phone1.equals("") || password1.equals("") || name1.equals("") || school1.equals("") || stuNumber1.equals("")){
                    Toast.makeText(RegisterActivity.this, "注册信息不能为空", Toast.LENGTH_LONG).show();
                }else{
                    User user1 = new User(phone1,password1,name1,school1,stuNumber1);
                    new SendDateToServer(handler).SendDataToServer(user1);
                }
            }
        });

        //不注册直接登录，界面跳转
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });


    }
}
