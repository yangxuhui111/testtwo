package com.w.school_herper_front.wallet;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.w.school_herper_front.ChangePassword.ChangePassword;
import com.w.school_herper_front.HomePage.HomeActivity;
import com.w.school_herper_front.MainActivity;
import com.w.school_herper_front.R;
import com.w.school_herper_front.SendDatesToServer;
import com.w.school_herper_front.User;

public class WalletWithDrawActivity extends AppCompatActivity {
    private ImageView back1;
    private EditText et2;
    private Button bt2;
    private Button bt3;
    Handler handler=new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SendDatesToServer.SEND_SUCCESS:
                    Toast.makeText(WalletWithDrawActivity.this, "提现成功", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(WalletWithDrawActivity.this, WalletActivity.class);
                    startActivity(intent);
                    break;
                case SendDatesToServer.SEND_FAIL:
                    Toast.makeText(WalletWithDrawActivity.this, "数据发送失败", Toast.LENGTH_SHORT).show();
                    break;
                case SendDatesToServer.SEND_FAIL1:
                    Toast.makeText(WalletWithDrawActivity.this, "提现失败", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet_with_draw);
        back1 = findViewById(R.id.back_wallet2);
        et2=findViewById(R.id.edit_text2);
        bt2=findViewById(R.id.btn2);
        bt3=findViewById(R.id.btn3);
        bt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et2.setText(SendDatesToServer.user1.getMoney()+"");
            }
        });
        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double money1=Double.parseDouble(et2.getText().toString());
                if(money1>10000){
                    Toast.makeText(WalletWithDrawActivity.this, "一次提现不能超过10000", Toast.LENGTH_LONG).show();
                }else if(money1>SendDatesToServer.user1.getMoney()){
                    Toast.makeText(WalletWithDrawActivity.this, "余额不足", Toast.LENGTH_LONG).show();
                }else {
                    String phone = SendDatesToServer.user1.getPhone();
                    User user1 = new User(phone, money1);
                    new GetMoney(handler).GetMoney(user1);
                }
            }
        });
        /*
         * 姓名：赵璐
         * 日期：2018.12.12
         * 说明：基本的页面跳转
         * */
        back1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WalletWithDrawActivity.this, WalletActivity.class);
                startActivity(intent);
            }
        });
    }
}
