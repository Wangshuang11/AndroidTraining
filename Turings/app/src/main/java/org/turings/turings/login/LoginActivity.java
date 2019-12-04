package org.turings.turings.login;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.turings.turings.Fragment.MyselfFragment;
import org.turings.turings.MainActivity;
import org.turings.turings.R;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class LoginActivity extends AppCompatActivity {
    private ImageView ivBackMyFragment_ws;//返回我的页面
    private TextView tvPhoneCodeLogin_ws;//手机号密码登录切换
    private TextView tvLogin_ws;//登录按钮
    private View layout_progress_ws;//登录动画控件
    private View layout_nameAndPwd_ws;//账户密码控件
    private View layout_phoneAndCode_ws;//手机号验证码控件
    private float mWidth;//控件的宽
    private float mHeight;//控件的高
    private EditText etName_ws;//姓名的输入框
    private EditText etPassWord_ws;//密码输入框
    private EditText etPhone_ws;//手机号的输入框
    private EditText etCode_ws;//验证码输入框
    private CheckBox cbRememberPwd_ws;//记住密码复选框
    private TextView tvGetCode_ws;//获得验证码控件
    private TextView tvRegisterNewUser_ws;//注册新用户
    private TextView tvRememberPwd_ws;//记住密码文字控件
    private TextView tvForgetPwd_ws;//忘记密码文字控件
    private String result="";//服务器验证结果
    private String code;//后台生成的随机4位验证码也是用户短信验证码
    private String uTel;//用户输入的登录手机号
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 100:
                    if(tvPhoneCodeLogin_ws.getText().toString().equals("手机验证码登录")){
                        //姓名，密码不匹配，恢复登录样式
                        Toast.makeText(getApplicationContext(),"信息有误！",Toast.LENGTH_SHORT).show();
                        recovery(layout_nameAndPwd_ws,etName_ws,etPassWord_ws);
                    }else{
                        Toast.makeText(getApplicationContext(),"信息有误！",Toast.LENGTH_SHORT).show();
                        recovery(layout_phoneAndCode_ws,etPhone_ws,etCode_ws);
                   }
                    break;
                case 101:
                    /*Toast.makeText(getApplicationContext(),"验证成功，转跳",Toast.LENGTH_SHORT).show();*/
                    //转跳到我的页面
                    Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                    intent.setAction("loginBackMyself");
                    startActivity(intent);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //初始化控件
        initControl();

        //点击登录按钮，验证用户输入信息的同时，开始输入动画
        clickLoginButton();

        //点击手机验证码登陆，切换登录方式
        changeLoginMethod();

        //下次登录是否记住密码
        nextLoginRememberNameAndPwd();

        //点击获得验证码短信
        getAPhoneCodeMessage();

        //注册新用户页面跳转
        registerNewUser();

        //忘记密码页面跳转
        forgetPassword();

        //返回我的页面
        backMyFragment();
    }

    //返回我的页面
    private void backMyFragment() {
        ivBackMyFragment_ws.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(), MainActivity.class);
                intent.setAction("loginBackMyself");
                startActivity(intent);
            }
        });
    }

    //下次登录是否记住密码
    private void nextLoginRememberNameAndPwd() {
        if(cbRememberPwd_ws.isChecked()){
            SharedPreferences sharedPreferences=getSharedPreferences("userInfo",MODE_PRIVATE);
            String name=sharedPreferences.getString("name","");
            String password=sharedPreferences.getString("password","");
            etPassWord_ws.setText(password);
            etName_ws.setText(name);
        }
        cbRememberPwd_ws.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(!isChecked){//未选中，删除记住密码
                    SharedPreferences sharedPreferences=getSharedPreferences("userInfo",MODE_PRIVATE);
                    SharedPreferences.Editor editor=sharedPreferences.edit();
                    editor.putString("name","");
                    editor.putString("password","");
                    editor.commit();
                    etPassWord_ws.setText("");
                    etName_ws.setText("");
                    cbRememberPwd_ws.setChecked(false);
                }
            }
        });
    }

    //点击手机验证码登陆，切换登录方式
    private void changeLoginMethod() {
        tvPhoneCodeLogin_ws.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tvPhoneCodeLogin_ws.getText().toString().equals("手机验证码登录")){
                    tvRememberPwd_ws.setVisibility(View.INVISIBLE);
                    cbRememberPwd_ws.setVisibility(View.INVISIBLE);
                    layout_nameAndPwd_ws.setVisibility(View.INVISIBLE);
                    layout_phoneAndCode_ws.setVisibility(View.VISIBLE);
                    tvPhoneCodeLogin_ws.setText("账号密码登录");
                }else{
                    tvRememberPwd_ws.setVisibility(View.VISIBLE);
                    cbRememberPwd_ws.setVisibility(View.VISIBLE);
                    etName_ws.setVisibility(View.VISIBLE);
                    etPassWord_ws.setVisibility(View.VISIBLE);
                    layout_nameAndPwd_ws.setVisibility(View.VISIBLE);
                    layout_phoneAndCode_ws.setVisibility(View.INVISIBLE);
                    tvPhoneCodeLogin_ws.setText("手机验证码登录");
                }
            }
        });
    }

    //点击登录按钮，验证用户输入信息的同时，开始输入动画
    private void clickLoginButton() {
        tvLogin_ws.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 1、计算出控件的高与宽：
                mWidth = tvLogin_ws.getMeasuredWidth();
                mHeight = tvLogin_ws.getMeasuredHeight();
                // 2、隐藏输入框
                etName_ws.setVisibility(View.INVISIBLE);
                etPassWord_ws.setVisibility(View.INVISIBLE);
                //3、开始动画
                if(tvPhoneCodeLogin_ws.getText().toString().equals("手机验证码登录")){
                    inputAnimator(layout_nameAndPwd_ws, mWidth, mHeight);
                }else{
                    inputAnimator(layout_phoneAndCode_ws, mWidth, mHeight);
                }
                //4、判断用户输入信息是否匹配。若不匹配，结束动画，加载登录样式；若匹配，登录跳转
                new Thread(){
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(3000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        String uName=etName_ws.getText().toString();
                        String uPwd=etPassWord_ws.getText().toString();
                        String uTel=etPhone_ws.getText().toString();
                        String uCode=etCode_ws.getText().toString();
                        //若登录验证成功，返回用户在用户表的id，即uId
                        if (tvPhoneCodeLogin_ws.getText().toString().equals("手机验证码登录")){
                            //账号密码验证登录
                            result=userLoginCheck("uName",uName,"http://"+getResources().getString(R.string.ipConfig)+":8080/Turings/UserLoginCheckByUserPwd","uPwd",uPwd);

                            Log.i("lww", "run: id是"+result);
                        }else{
                            //手机验证码验证登录:
                            //1、验证手机号存不存在
                            result=userLoginCheck("uTel",uTel,"http://"+getResources().getString(R.string.ipConfig)+":8080/Turings/UserLoginCheckByPhone","uCode",uCode);
                            if(!result.equals("false")){//2、验证用户输入的验证码对不对
                                if(!uCode.equals(code)){
                                    result="false";
                                }
                            }
                        }
                        Message message=new Message();
                        if(!result.equals("false")){//匹配
                            SharedPreferences sharedPreferences=getSharedPreferences("userInfo",MODE_PRIVATE);
                            SharedPreferences.Editor editor=sharedPreferences.edit();
                            editor.putString("name",uName);
                            editor.putString("password",uPwd);
                            editor.putString("phone",uTel);
                            editor.putString("uId",result);
                            editor.commit();
                            message.what=101;
                        }else{//不匹配
                            message.what=100;
                        }
                        handler.sendMessage(message);
                    }
                }.start();
            }
        });
    }

    //用户登录验证：
    private String userLoginCheck(final String s1, final String s2, final String url, final String s3, final String s4) {
        OkHttpClient okHttpClient=new OkHttpClient();
        //post-FormBody传输，在一定程度上保证用户信息的安全
        FormBody formBody=new FormBody.Builder()
                .add(s1,s2)
                .add(s3,s4)
                .build();
        Request request=new Request.Builder().url(url).post(formBody).build();
        Call call = okHttpClient.newCall(request);
        try {
            result=call.execute().body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }


    //忘记密码
    private void forgetPassword() {
        tvForgetPwd_ws.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),ForgetPasswordActivity.class);
                startActivity(intent);
            }
        });
    }

    //注册新用户
    private void registerNewUser() {
        tvRegisterNewUser_ws.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //转跳到注册新用户界面
                Intent intent=new Intent(getApplicationContext(),RegisterNewUserActivity.class);
                startActivity(intent);

            }
        });
    }

    //点击获得验证码短信
    private void getAPhoneCodeMessage() {
        tvGetCode_ws.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uTel=etPhone_ws.getText().toString();
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

    //初始化控件
    private void initControl() {
        tvLogin_ws=findViewById(R.id.tvLogin_ws);
        layout_progress_ws=findViewById(R.id.layout_progress_ws);
        layout_nameAndPwd_ws=findViewById(R.id.layout_nameAndPwd_ws);
        etName_ws=findViewById(R.id.etName_ws);
        etPassWord_ws=findViewById(R.id.etPassWord_ws);
        tvPhoneCodeLogin_ws=findViewById(R.id.tvPhoneCodeLogin_ws);
        layout_phoneAndCode_ws=findViewById(R.id.layout_phoneAndCode_ws);
        cbRememberPwd_ws=findViewById(R.id.cbRememberPwd_ws);
        etCode_ws=findViewById(R.id.etCode_ws);
        etPhone_ws=findViewById(R.id.etPhone_ws);
        tvGetCode_ws=findViewById(R.id.tvGetCode_ws);
        tvRegisterNewUser_ws=findViewById(R.id.tvRegisterNewUser_ws);
        tvRememberPwd_ws=findViewById(R.id.tvRememberPwd_ws);
        tvForgetPwd_ws=findViewById(R.id.tvForgetPwd_ws);
        ivBackMyFragment_ws=findViewById(R.id.ivBackMyFragment_ws);
    }

    //输入框的动画效果
    private void inputAnimator(final View view, float w, float h) {
        AnimatorSet set = new AnimatorSet();
        ValueAnimator animator = ValueAnimator.ofFloat(0, w);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (Float) animation.getAnimatedValue();
                ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) view
                        .getLayoutParams();
                params.leftMargin = (int) value;
                params.rightMargin = (int) value;
                view.setLayoutParams(params);
            }
        });

        ObjectAnimator animator2 = ObjectAnimator.ofFloat(view, "scaleX", 1f, 0.5f);
        set.setDuration(1000);
        set.setInterpolator(new AccelerateDecelerateInterpolator());
        set.playTogether(animator, animator2);
        set.start();
        set.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }
            @Override
            public void onAnimationRepeat(Animator animation) {

            }
            @Override
            public void onAnimationEnd(Animator animation) {
                //动画结束后，先显示加载的动画，然后再隐藏输入框
                layout_progress_ws.setVisibility(View.VISIBLE);
                progressAnimator(view);
                view.setVisibility(View.INVISIBLE);

            }
            @Override
            public void onAnimationCancel(Animator animation) {

            }
        });
    }

    //出现进度动画
    private void progressAnimator(final View view) {
        PropertyValuesHolder animator = PropertyValuesHolder.ofFloat("scaleX",
                0.5f, 1f);
        PropertyValuesHolder animator2 = PropertyValuesHolder.ofFloat("scaleY",
                0.5f, 1f);
        ObjectAnimator animator3 = ObjectAnimator.ofPropertyValuesHolder(view,
                animator, animator2);
        animator3.setDuration(1000);
        animator3.setInterpolator(new AccelerateDecelerateInterpolator());
        animator3.start();
    }

    //恢复初始状态
    private void recovery(View view,EditText editText1,EditText editText2) {
        layout_progress_ws.setVisibility(View.GONE);
        view.setVisibility(View.VISIBLE);
        editText1.setVisibility(View.VISIBLE);
        editText2.setVisibility(View.VISIBLE);

        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        params.leftMargin = 0;
        params.rightMargin = 0;
        view.setLayoutParams(params);

        ObjectAnimator animator2 = ObjectAnimator.ofFloat(view, "scaleX", 0.5f,1f );
        animator2.setDuration(500);
        animator2.setInterpolator(new AccelerateDecelerateInterpolator());
        animator2.start();
    }

}
