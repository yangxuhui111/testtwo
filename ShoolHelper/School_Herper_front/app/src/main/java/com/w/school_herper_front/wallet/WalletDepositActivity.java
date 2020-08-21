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

import com.w.school_herper_front.HomePage.HomeActivity;
import com.w.school_herper_front.HomePage.fragment.MineFragment;
import com.w.school_herper_front.MainActivity;
import com.w.school_herper_front.R;
import com.w.school_herper_front.SendDatesToServer;
import com.w.school_herper_front.User;

//充值界面
public class WalletDepositActivity extends AppCompatActivity {
    private ImageView back1;
    private EditText et1;
    private Button btn;
    Handler handler=new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SendDatesToServer.SEND_SUCCESS:
                    Toast.makeText(WalletDepositActivity.this, "充值成功", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(WalletDepositActivity.this, WalletActivity.class);
                    startActivity(intent);
                    break;
                case SendDatesToServer.SEND_FAIL:
                    Toast.makeText(WalletDepositActivity.this, "数据发送失败", Toast.LENGTH_SHORT).show();
                    break;
                case SendDatesToServer.SEND_FAIL1:
                    Toast.makeText(WalletDepositActivity.this, "充值失败", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet_deposit);
        back1 = findViewById(R.id.back_wallet);
        et1=findViewById(R.id.edit_text);
        btn=findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double money=Double.parseDouble(et1.getText().toString());
                String phone=SendDatesToServer.user1.getPhone();
                User user=new User(phone,money);
                new IntoMoney(handler).IntoMoney(user);
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
                Intent intent = new Intent(WalletDepositActivity.this, WalletActivity.class);
                startActivity(intent);
            }
        });


    }
}
