package org.turings.turings.login;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.turings.turings.MainActivity;
import org.turings.turings.R;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class ForgetPasswordActivity extends AppCompatActivity {
    private ImageView ivBackLogin_ws;//返回上一页的图标
    private TextView tvGetCode_ws;//获得验证码控件
    private EditText et_forget_phone_ws;//输入手机号控件
    private EditText et_forget_password_ws;//输入新密码控件
    private EditText et_forget_code_ws;//输入验证码控件
    private ImageView ivCheckPhoneNum_ws;//手机号是否存在的提示
    private TextView tvFoundPwd_ws;//找回密码按钮
    private String result="";//服务器验证结果
    private String uTel;//用户输入的手机号
    private String uCode;//用户输入的验证码
    private String uPwd;//用户输入的新密码
    private String code;//随机4位验证码
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 100:
                    Toast.makeText(getApplicationContext(),"修改密码失败！",Toast.LENGTH_SHORT).show();
                    break;
                case 101:
                    Intent intent=new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    break;
                case 102:
                    if (result.equals("true")){//存在
                        ivCheckPhoneNum_ws.setImageResource(R.mipmap.loginyes);
                    }else{//不存在
                        ivCheckPhoneNum_ws.setImageResource(R.mipmap.loginno);
                    }
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        //初始化控件
        initControl();

        //返回上一页
        backLogin();

        //发送验证码短信
        getAPhoneCodeMessage();

        //修改密码
        changeUserPwd();

        //在用户填写手机号时，检查是否存在
        checkUserPhoneIsExist();
    }


    //修改密码
    private void changeUserPwd() {
        tvFoundPwd_ws.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uCode=et_forget_code_ws.getText().toString();
                if(uCode.equals(code)){
                    result="true";
                    modifyUserPwdByPhone();
                }else{
                    result="false";
                }
            }
        });
    }

    //修改用户新密码(在数据库里)
    private void modifyUserPwdByPhone() {
        new Thread(){
            @Override
            public void run() {
                uPwd=et_forget_password_ws.getText().toString();
                OkHttpClient okHttpClient=new OkHttpClient();
                //post-FormBody传输，在一定程度上保证用户信息的安全
                FormBody formBody=new FormBody.Builder()
                        .add("uTel",uTel)
                        .add("uPwd",uPwd)
                        .build();
                Request request=new Request.Builder().url("http://"+getResources().getString(R.string.ipConfig)+":8080/Turings/UserModifyPwdByPhone").post(formBody).build();
                Call call = okHttpClient.newCall(request);
                try {
                    result=call.execute().body().string();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Message message=new Message();
                if (result.equals("true")){//修改密码成功
                    message.what=101;
                }else{//修改密码失败
                    message.what=100;
                }
                handler.sendMessage(message);
            }
        }.start();
    }

    //在用户填写手机号时，检查是否存在
    private void checkUserPhoneIsExist() {
        et_forget_phone_ws.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                uTel=et_forget_phone_ws.getText().toString();
                //后台服务器查找用户填写手机号是否存在
                if (!hasFocus){//失去焦点
                    new Thread(){
                        @Override
                        public void run() {
                            OkHttpClient okHttpClient=new OkHttpClient();
                            //post-FormBody传输，在一定程度上保证用户信息的安全
                            FormBody formBody=new FormBody.Builder()
                                    .add("uTel",uTel)
                                    .build();
                            Request request=new Request.Builder().url("http://"+getResources().getString(R.string.ipConfig)+":8080/Turings/UserLoginCheckByPhone").post(formBody).build();
                            Call call = okHttpClient.newCall(request);
                            try {
                                result=call.execute().body().string();
                                Message message=new Message();
                                message.what=102;
                                handler.sendMessage(message);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }.start();
                }
            }
        });
    }

    //返回上一页
    private void backLogin() {
        ivBackLogin_ws.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    //初始化控件
    private void initControl() {
        ivBackLogin_ws=findViewById(R.id.ivBackLogin_ws);
        et_forget_phone_ws=findViewById(R.id.et_forget_phone_ws);
        tvGetCode_ws=findViewById(R.id.tvGetCode_ws);
        ivCheckPhoneNum_ws=findViewById(R.id.ivCheckPhoneNum_ws);
        et_forget_password_ws=findViewById(R.id.et_forget_password_ws);
        tvFoundPwd_ws=findViewById(R.id.tvFoundPwd_ws);
        et_forget_code_ws=findViewById(R.id.et_forget_code_ws);
    }

    //点击获得验证码，随机生成一个验证码，并向手机发送一条短信
    private void getAPhoneCodeMessage() {
        tvGetCode_ws.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //向用户手机号发送一条短信
                uTel=et_forget_phone_ws.getText().toString();
                new Thread(){
                    @Override
                    public void run() {
                        OkHttpClient okHttpClient=new OkHttpClient();
                        //post-FormBody传输，在一定程度上保证用户信息的安全
                        FormBody formBody=new FormBody.Builder()
                                .add("uTel",uTel)
                                .build();
                        Request request=new Request.Builder().url("http://"+getResources().getString(R.string.ipConfig)+":8080/Turings/SendCodeSmsToUser").post(formBody).build();
                        Call call = okHttpClient.newCall(request);
                        try {
                            code=call.execute().body().string();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }.start();
            }
        });
    }
}
