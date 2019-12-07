package org.turings.turings.Fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import org.turings.turings.R;
import org.turings.turings.login.LoginActivity;
import org.turings.turings.login.RegisterNewUserActivity;

import static android.content.Context.MODE_PRIVATE;

public class NearFragment extends Fragment {
    private View view;
    private TextView tvJumpLogin_ws;//未登录界面的登录按钮
    private ImageView ivUnLogin_ws;//未登录界面的上方图片
    private TextView tvJumpRegister_ws;//未登录界面的注册按钮

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //用户是否登录
        if(!checkUserIsLogin()){//未登录
            view = inflater.inflate(R.layout.sxn_activity_unlogged, container,false);
            //加载上方图片
            loadTopImg();
            //点击跳转按钮，跳到登录或注册界面
            jumpToLogin();
            return view;
        }
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    //加载上方图片
    private void loadTopImg() {
        ivUnLogin_ws=view.findViewById(R.id.ivUnLogin_ws);
        RequestOptions requestOptions=new RequestOptions().circleCrop();
        Glide.with(getContext()).asGif().load(R.mipmap.myselfthinkingtwo).apply(requestOptions).into(ivUnLogin_ws);
    }

    //点击跳转按钮，跳到登录或注册界面
    private void jumpToLogin() {
        tvJumpLogin_ws= view.findViewById(R.id.tvJumpLogin_ws);
        tvJumpRegister_ws=view.findViewById(R.id.tvJumpRegister_ws);
        tvJumpLogin_ws.setOnClickListener(new View.OnClickListener() {//跳到登录界面
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getContext(), LoginActivity.class);
                startActivity(intent);
            }
        });
        tvJumpRegister_ws.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getContext(), RegisterNewUserActivity.class);
                startActivity(intent);
            }
        });
    }

    //用户是否登录
    private boolean checkUserIsLogin() {
        SharedPreferences sharedPreferences=getContext().getSharedPreferences("userInfo",MODE_PRIVATE);
        String uName=sharedPreferences.getString("name","");
        String uTel=sharedPreferences.getString("phone","");
        if (uName.equals("") && uTel.equals("")){//用户名或者密码两个都为空，就是用户没登录
            return false;
        }else{//只要用户名或者密码有一个不为空，就是用户登录了
            return true;
        }
    }
}
