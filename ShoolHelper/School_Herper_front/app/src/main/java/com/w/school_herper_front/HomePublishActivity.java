package com.w.school_herper_front;

/*
 * CONTENT: publish content
 * DEVELOPER: Zhangxixian
 * DATE: 18/12/11
 */

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.TimePickerView;
import com.w.school_herper_front.HomePage.HomeActivity;
import com.w.school_herper_front.bean.RewardBean;
import com.w.school_herper_front.connect.SendData;
import com.w.school_herper_front.listener.TextChange;
import com.w.school_herper_front.wallet.GetMoney;
import com.w.school_herper_front.wallet.WalletDepositActivity;
import com.w.school_herper_front.wallet.WalletWithDrawActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class HomePublishActivity extends AppCompatActivity   {

    private TextView tvShowTime;
    private Button btnSelectTime,btnaddImg ,btnPublish;
    private RelativeLayout relativeLayout;
    private ImageView pic;
    private EditText etAddMoney,etTitle,etContent;
    private static final int ALBUM_REQUEST_CODE = 1;
    //相机请求码
    private static final int CAMERA_REQUEST_CODE = 2;
    //剪裁请求码
    private static final int CROP_REQUEST_CODE = 3;
    //调用照相机返回图片文件
    private File tempFile;
    private RewardBean reward;
    private TextView money;
    private TextView addmoney;
    Handler handler=new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SendDatesToServer.SEND_SUCCESS:
                    Toast.makeText(HomePublishActivity.this, "任务发布成功", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(HomePublishActivity.this, HomeActivity.class);
                    startActivity(intent);
                    break;
                case SendDatesToServer.SEND_FAIL:
                    Toast.makeText(HomePublishActivity.this, "数据上传失败", Toast.LENGTH_SHORT).show();
                    break;
//                case SendDatesToServer.SEND_FAIL1:
//                    Toast.makeText(HomePublishActivity.this, "任务发布失败，请重新发布", Toast.LENGTH_SHORT).show();
//                    break;
                default:
                    break;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_publish);
        money=findViewById(R.id.money);
        money.setText(""+SendDatesToServer.user1.getMoney());
        addmoney=findViewById(R.id.home_publish_tv_addmoney);
        addmoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePublishActivity.this, com.w.school_herper_front.wallet.WalletDepositActivity.class);
                startActivity(intent);
            }
        });
        getWidgets();
        btnSelectTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePicker(tvShowTime);
            }
        });
        //添加图片功能
        btnaddImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupWindow();
            }
        });
        //Button改变颜色
        tvShowTime.addTextChangedListener(new TextChange(etAddMoney,etTitle,tvShowTime,btnPublish));
        //submit

        btnPublish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fillDataFromView();
            }
        });

    }

    /**
     * function:fill data in reward
     * param:null
     * return:void
     */
    private void fillDataFromView(){
        int posterId = SendDatesToServer.user1.getUserId();
        String title = etTitle.getText().toString();
        String content = etContent.getText().toString();
//        Double money = Double.valueOf(etAddMoney.getText().toString());
        double money = Double.parseDouble(etAddMoney.getText().toString());
        if(money>SendDatesToServer.user1.getMoney()){
            Toast.makeText(HomePublishActivity.this, "余额不足", Toast.LENGTH_LONG).show();
        }else {
            String phone = SendDatesToServer.user1.getPhone();
            User user1 = new User(phone, money);
            new GetMoney(handler).GetMoney(user1);
            String deadline = tvShowTime.getText().toString();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
            Date date = new Date(System.currentTimeMillis());
            String publishTime = simpleDateFormat.format(date);

            reward = new RewardBean(posterId, content, title, deadline, money);
            reward.setPublishTime(publishTime);
            new SendData(handler).SendDatasToServer(reward);
        }
    }

    /**
     * function:get all Widgets by id from layout ;
     * param: void ;
     * return: void ;
     * */
    private void getWidgets(){
        btnSelectTime = findViewById(R.id.home_publish_btn_timepicker);
        btnaddImg = findViewById(R.id.home_publish_btn_addimage);
        relativeLayout = findViewById(R.id.home_publish);
        pic = findViewById(R.id.home_publish_img);
        etAddMoney = findViewById(R.id.home_publish_et_moneycount);
        btnPublish = findViewById(R.id.home_publish_btn_publish);
        etTitle = findViewById(R.id.home_publish_et_title);
        tvShowTime = findViewById(R.id.home_publish_tv_showtime);
        etContent = findViewById(R.id.home_publish_et_content);
    }


    /**
    * function:show time picked by user on TimePicker in Param ;
    * param: TextView tvTime;
    * return:void ;
    * */
    private void showTimePicker(final TextView tvTime){

        TimePickerView pvTime = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {

            @Override
            public void onTimeSelect(Date date, View v) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String format = simpleDateFormat.format(date);
                tvTime.setText(format);
                Log.e("time=============",format);
//                Toast.makeText(HomePublishActivity.this, date.toString(), Toast.LENGTH_SHORT).show();
            }
        })
                .setDate(Calendar.getInstance())//设置默认时间
                .setCancelText("取消")//取消按钮文字
                .setSubmitText("确定")//确认按钮文字
                .build();
//        pvTime.setDate(Calendar.getInstance());//设置默认时间
        pvTime.show();

    }
    /**
     * 获取图片
     */
    public void showPopupWindow(){
        //创建PopupWindow对象
        final PopupWindow popupWindow = new PopupWindow(this);
        //设置宽度
        popupWindow.setWidth(ConstraintLayout.LayoutParams.MATCH_PARENT);
        //设置显示的视图
        View view = getLayoutInflater().inflate(R.layout.popupwindow,null);
        Button button1 = view.findViewById(R.id.btn1);
        Button button2 = view.findViewById(R.id.btn2);
        Button button3 = view.findViewById(R.id.btn3);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPicFromCamera();
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPicFromAlbm();
                popupWindow.dismiss();
            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //隐藏PopupWindow
                popupWindow.dismiss();
            }
        });
        popupWindow.setContentView(view);
        //显示PopupWindow
        popupWindow.showAtLocation(relativeLayout, Gravity.NO_GRAVITY,0,0);
    }
    /**
     * 从相册获取图片
     */
    private void getPicFromCamera() {
        //用于保存调用相机拍照后所生成的文件
        tempFile = new File(Environment.getExternalStorageDirectory().getPath(), System.currentTimeMillis() + ".jpg");
        //跳转到调用系统相机
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //判断版本
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {   //如果在Android7.0以上,使用FileProvider获取Uri
            intent.setFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            Uri contentUri = FileProvider.getUriForFile(HomePublishActivity.this, "com.hansion.chosehead", tempFile);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, contentUri);
            Log.e("dasd", contentUri.toString());
        } else {    //否则使用Uri.fromFile(file)方法获取Uri
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempFile));
        }
        startActivityForResult(intent, CAMERA_REQUEST_CODE);
    }

    private void getPicFromAlbm() {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, ALBUM_REQUEST_CODE);
    }
    /**
     * 裁剪图片
     */
    private void cropPhoto(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 300);
        intent.putExtra("outputY", 300);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, CROP_REQUEST_CODE);
    }
    /**
     * 处理返回数据
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        switch (requestCode) {
            case CAMERA_REQUEST_CODE:   //调用相机后返回
                if (resultCode == RESULT_OK) {
                    //用相机返回的照片去调用剪裁也需要对Uri进行处理
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        Uri contentUri = FileProvider.getUriForFile(HomePublishActivity.this, "com.hansion.chosehead", tempFile);
                        cropPhoto(contentUri);
                    } else {
                        cropPhoto(Uri.fromFile(tempFile));
                    }
                }
                break;
            case ALBUM_REQUEST_CODE:    //调用相册后返回
                if (resultCode == RESULT_OK) {
                    Uri uri = intent.getData();
                    cropPhoto(uri);
                }
                break;
            case CROP_REQUEST_CODE:     //调用剪裁后返回
                Bundle bundle = intent.getExtras();
                if (bundle != null) {
                    //在这里获得了剪裁后的Bitmap对象，可以用于上传
                    Bitmap image = bundle.getParcelable("data");
                    //设置到ImageView上
                    pic.setImageBitmap(image);
                    //也可以进行一些保存、压缩等操作后上传
//                    String path = saveImage("crop", image);
                }
                break;
        }
    }
    /**
     * 保存图片
     */
    public String saveImage(String name, Bitmap bmp) {
        File appDir = new File(Environment.getExternalStorageDirectory().getPath());
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = name + ".jpg";
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();
            return file.getAbsolutePath();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


}
