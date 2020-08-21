package com.w.school_herper_front.Message;

import android.animation.RectEvaluator;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.w.school_herper_front.Authen.AuthenNoActivity;
import com.w.school_herper_front.ChangePassword.ChangePassword;
import com.w.school_herper_front.HomePage.HomeActivity;
import com.w.school_herper_front.MainActivity;
import com.w.school_herper_front.R;
import com.w.school_herper_front.SendDatesToServer;
import com.w.school_herper_front.User;
import com.w.school_herper_front.bean.ReviseUser;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class MessageSetActivity extends Activity {
    private int userId;
    private RelativeLayout authen;
    private RelativeLayout changepsd;
    private ImageView back1;
    private ImageView headphoto;
    private EditText username;
    private EditText names;
    private EditText sex;
    private EditText phone;
    private TextView authentication;
    private EditText write;
    private TextView change;
    private ConstraintLayout constraintLayout;
    private static final int ALBUM_REQUEST_CODE = 1;
    //相机请求码
    private static final int CAMERA_REQUEST_CODE = 2;
    //剪裁请求码
    private static final int CROP_REQUEST_CODE = 3;
    //调用照相机返回图片文件
    private File tempFile;
    /*
     * 姓名：赵璐
     * 日期：2018.12.14
     * 说明：多线程
     * */
    Handler handler=new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SendDatesToServer.SEND_SUCCESS:
                    Toast.makeText(MessageSetActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                    break;
                case SendDatesToServer.SEND_FAIL:
                    Toast.makeText(MessageSetActivity.this, "数据错误或没有获取回应", Toast.LENGTH_SHORT).show();
                    break;
                case SendDatesToServer.SEND_FAIL1:
                    Toast.makeText(MessageSetActivity.this, "该手机号已被注册", Toast.LENGTH_SHORT).show();
                    break;
                case SendDatesToServer.SEND_FAIL2:
                    Toast.makeText(MessageSetActivity.this, "修改错误", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }
    };


    public void handleReviseUser(ReviseUser reviseUser){

    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message_set);
        authen = findViewById(R.id.ln_authens);
        changepsd = findViewById(R.id.ln_changepsd);
        back1 = findViewById(R.id.back_1);
        /*
         * 姓名：赵璐
         * 日期：2018.12.14
         * 说明：根据id进行数据导入
         * */
        change=findViewById(R.id.img_change);
        headphoto = findViewById(R.id.img_touxiang);
        authentication = findViewById(R.id.img_authentication);
        constraintLayout=findViewById(R.id.root);
        username=findViewById(R.id.name);
        names=findViewById(R.id.realname);
        sex=findViewById(R.id.sex);
        phone=findViewById(R.id.phone);
        write=findViewById(R.id.write);
        names.setText(SendDatesToServer.user1.getRealname());
        username.setText(SendDatesToServer.user1.getName());
        sex.setText(SendDatesToServer.user1.getSex());
        phone.setText(SendDatesToServer.user1.getPhone());
        write.setText(SendDatesToServer.user1.getStuWriter());
        authentication.setText(SendDatesToServer.user1.getIdentification());
        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username1=username.getText().toString();
                String names1=names.getText().toString();
                String sex1=sex.getText().toString();
                String phone1=phone.getText().toString();
                String write1=write.getText().toString();
                String identification1=authentication.getText().toString();
                User user=new User(identification1,username1,names1,phone1,sex1,write1);
                new SetMessageToServer(handler).SetMessageToServer(user);
            }
        });

        /*
         * 姓名：赵璐
         * 日期：2018.12.12
         * 说明：基本的页面跳转
         * */
        //返回个人主页
        back1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("id",1);
                setResult(4,intent);
                finish();
            }
        });
        headphoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupWindow();
            }
        });
        //跳转到认证页面
        authen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MessageSetActivity.this,AuthenNoActivity.class);
                startActivity(intent);
            }
        });

        //跳转到修改密码
        changepsd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MessageSetActivity.this,ChangePassword.class);
                startActivity(intent);
            }
        });
    }
    /*
     * 姓名：杨旭辉
     * 日期：2018.12.10
     * 说明：相册中图片的上传
     * */
    private void getPicFromCamera() {
        //用于保存调用相机拍照后所生成的文件
        tempFile = new File(Environment.getExternalStorageDirectory().getPath(), System.currentTimeMillis() + ".jpg");
        //跳转到调用系统相机
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //判断版本
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {   //如果在Android7.0以上,使用FileProvider获取Uri
            intent.setFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            Uri contentUri = FileProvider.getUriForFile(MessageSetActivity.this, "com.hansion.chosehead", tempFile);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, contentUri);
            Log.e("dasd", contentUri.toString());
        } else {    //否则使用Uri.fromFile(file)方法获取Uri
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempFile));
        }
        startActivityForResult(intent, CAMERA_REQUEST_CODE);
    }
    /**
     * 从相册获取图片
     */
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
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        switch (requestCode) {
            case CAMERA_REQUEST_CODE:   //调用相机后返回
                if (resultCode == RESULT_OK) {
                    //用相机返回的照片去调用剪裁也需要对Uri进行处理
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        Uri contentUri = FileProvider.getUriForFile(MessageSetActivity.this, "com.hansion.chosehead", tempFile);
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
                    headphoto.setImageBitmap(image);
                    //也可以进行一些保存、压缩等操作后上传
//                    String path = saveImage("crop", image);
                }
                break;
        }
    }
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
    private void showPopupWindow(){
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
        popupWindow.showAtLocation(constraintLayout, Gravity.NO_GRAVITY,0,0);
    }
}
