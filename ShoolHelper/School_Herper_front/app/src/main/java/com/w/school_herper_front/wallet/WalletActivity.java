package com.w.school_herper_front.wallet;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.w.school_herper_front.HomePage.HomeActivity;
import com.w.school_herper_front.MainActivity;
import com.w.school_herper_front.R;
import com.w.school_herper_front.SendDatesToServer;
import com.w.school_herper_front.wallet.WalletDepositActivity;
public class WalletActivity extends AppCompatActivity {
    private RelativeLayout deposit1;
    private RelativeLayout withdraw1;
    private ImageView back1;
    private TextView money;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);
        back1 = findViewById(R.id.wallet_image_goback);
        deposit1 = findViewById(R.id.ln_deposit);
        withdraw1 = findViewById(R.id.ln_draw);
        money=findViewById(R.id.wallet_text1);
        money.setText(""+SendDatesToServer.user1.getMoney());
        /*
         * 姓名：赵璐
         * 日期：2018.12.12
         * 说明：基本的页面跳转
         *
        */

        deposit1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WalletActivity.this,WalletDepositActivity.class);
                startActivity(intent);
            }
        });;

        withdraw1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WalletActivity.this,WalletWithDrawActivity.class);
                startActivity(intent);
            }
        });

        back1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WalletActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 3){
        }
    }
}
