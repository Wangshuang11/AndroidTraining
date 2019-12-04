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

import org.turings.turings.R;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class RegisterNewUserActivity extends AppCompatActivity {
    private TextView link_login_ws;//返回登录页面控件
    private ImageView ivBackLogin_ws;//返回上一页的图标
    private TextView tvGetCode_ws;//获得验证码控件
    private EditText input_phone_ws;//输入手机号控件
    private EditText input_name_ws;//输入姓名控件
    private EditText input_password_ws;//输入密码控件
    private EditText input_code_ws;//输入验证码控件
    private ImageView ivCheckNewName_ws;//用户名是否唯一的提示
    private ImageView ivCheckNewPhone_ws;//手机号是否有人用过的提示
    private String uTel;//用户输入的手机号
    private String uName;//用户输入的账号姓名
    private String uPwd;//用户输入的密码
    private String uCode;//用户输入的验证码
    private String result="";//后台服务器验证结果
    private String code;//后台生成的随机4位验证码也是用户短信验证码
    private TextView tvRegister_ws;//注册新用户按钮
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 100:
                    if (result.equals("true")){
                        Toast.makeText(getApplicationContext(),"注册成功！",Toast.LENGTH_LONG).show();
                        Intent intent=new Intent(getApplicationContext(),LoginActivity.class);
                        startActivity(intent);
                    }else{
                        Toast.makeText(getApplicationContext(),"注册失败！",Toast.LENGTH_LONG).show();
                    }
                    break;
                case 101:
                    if (result.equals("false")){//唯一
                        ivCheckNewName_ws.setImageResource(R.mipmap.loginyes);
                    }else{//不唯一
                        ivCheckNewName_ws.setImageResource(R.mipmap.loginno);
                    }
                    break;
                case 102:
                    if (result.equals("false")){//唯一
                        ivCheckNewPhone_ws.setImageResource(R.mipmap.loginyes);
                    }else{//不唯一
                        ivCheckNewPhone_ws.setImageResource(R.mipmap.loginno);
                    }
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_new_user);

        //初始化控件
        initControl();

        //已有账户，不注册了，返回登录
        backLogin();

        //返回上一页
        backLogin();

        //发送验证码短信
        getAPhoneCodeMessage();

        //在用户填写姓名时，检查姓名是否唯一
        checkUserNameIsOnly();

        //在用户填写手机号时，检查是否已经有人用过
        checkUserPhoneIsExist();

        //新增一个用户
        addAUser();
    }

    //新增一个用户
    private void addAUser() {
        tvRegister_ws.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uPwd=input_password_ws.getText().toString();
                uCode=input_code_ws.getText().toString();
                if (uCode.equals(code)){
                    new Thread(){
                        @Override
                        public void run() {
                            OkHttpClient okHttpClient=new OkHttpClient();
                            //post-FormBody传输，在一定程度上保证用户信息的安全
                            FormBody formBody=new FormBody.Builder()
                                    .add("uTel",uTel)
                                    .add("uName",uName)
                                    .add("uPwd",uPwd)
                                    .build();
                            Request request=new Request.Builder().url("http://"+getResources().getString(R.string.ipConfig)+":8080/Turings/UserRegister").post(formBody).build();
                            Call call = okHttpClient.newCall(request);
                            try {
                                result=call.execute().body().string();
                                Message message=new Message();
                                message.what=100;
                                handler.sendMessage(message);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }.start();
                }else{
                    result="false";
                }
            }
        });
    }

    //在用户填写手机号时，检查是否已经有人用过
    private void checkUserPhoneIsExist() {
        input_phone_ws.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                uTel=input_phone_ws.getText().toString();
                //后台服务器查找用户填写手机号是否存在
                if (!b){//失去焦点
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

    //在用户填写姓名时，检查姓名是否唯一
    private void checkUserNameIsOnly() {
        input_name_ws.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                uName=input_name_ws.getText().toString();
                //检查用户输入的姓名是否唯一
                if (!hasFocus){
                    new Thread(){
                        @Override
                        public void run() {
                            OkHttpClient okHttpClient=new OkHttpClient();
                            //post-FormBody传输，在一定程度上保证用户信息的安全
                            FormBody formBody=new FormBody.Builder()
                                    .add("uName",uName)
                                    .build();
                            Request request=new Request.Builder().url("http://"+getResources().getString(R.string.ipConfig)+":8080/Turings/UserLoginCheckByName").post(formBody).build();
                            Call call = okHttpClient.newCall(request);
                            try {
                                result=call.execute().body().string();
                                Message message=new Message();
                                message.what=101;
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

    //点击获得验证码短信
    private void getAPhoneCodeMessage() {
        tvGetCode_ws.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //向用户手机号发送一条短信
                uTel=input_phone_ws.getText().toString();
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

    //已有账户，不注册了，返回登录
    private void backLogin() {
        link_login_ws.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
            }
        });
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
        link_login_ws=findViewById(R.id.link_login_ws);
        ivBackLogin_ws=findViewById(R.id.ivBackLogin_ws);
        tvGetCode_ws=findViewById(R.id.tvGetCode_ws);
        input_phone_ws=findViewById(R.id.input_phone_ws);
        input_name_ws=findViewById(R.id.input_name_ws);
        ivCheckNewName_ws=findViewById(R.id.ivCheckNewName_ws);
        ivCheckNewPhone_ws=findViewById(R.id.ivCheckNewPhone_ws);
        input_password_ws=findViewById(R.id.input_password_ws);
        input_code_ws=findViewById(R.id.input_code_ws);
        tvRegister_ws=findViewById(R.id.tvRegister_ws);
    }
}
