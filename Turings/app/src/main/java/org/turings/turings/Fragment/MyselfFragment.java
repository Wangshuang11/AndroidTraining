package org.turings.turings.Fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import org.turings.turings.MainActivity;
import org.turings.turings.R;
import org.turings.turings.login.LoginActivity;
import org.turings.turings.login.RegisterNewUserActivity;
import org.turings.turings.myself.sxn.MyAchieveActivity;
import org.turings.turings.myself.sxn.MyAvatarActivity;
import org.turings.turings.myself.sxn.MyConcernActivity;
import org.turings.turings.myself.sxn.MyFansActivity;
import org.turings.turings.myself.sxn.MySchoolActivity;
import org.turings.turings.myself.sxn.PersonalDataActivity;

import static android.content.Context.MODE_PRIVATE;


public class MyselfFragment extends Fragment {
    private Intent intent=new Intent();
    private LinearLayout logged;
    private LinearLayout myfans;
    private LinearLayout myattention;
    private LinearLayout myachieve;
    private RelativeLayout mycourse;
    private RelativeLayout school;
    private RelativeLayout track;
    private ImageView avatarI;
    private TextView nameT;
    private TextView mottoT;
    private TextView scoreT;
    private MyListener myListener=new MyListener();

    private ImageView ivUnLogOfMyself_ws;//退出登录按钮
    private  LinearLayout parent_ws;//弹出窗口父视图
    private ImageView ivBackground_ws;//头像上方的封面图片
    private TextView tvJumpLogin_ws;//未登录界面的登录按钮
    private ImageView ivUnLogin_ws;//未登录界面的上方图片
    private TextView tvJumpRegister_ws;//未登录界面的注册按钮
    private View view;

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

        //登录
        view = inflater.inflate(R.layout.sxn_activity_logged, container,false);

        //初始化控件
        initController();

        //绑定事件监听器
        bindingListener();

        //无缝填充头像上方的封面
        centerCropCoverPhoto();

        //退出登录
        unLogin();

        return view;
    }

    //退出登录
    private void unLogin() {
        ivUnLogOfMyself_ws.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //弹出PopupWindow  ：样式待改，和修改昵称的相同
                final PopupWindow popupWindow=new PopupWindow(getContext());
                popupWindow.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
                LayoutInflater layoutInflater=getLayoutInflater();
                View popupView=layoutInflater.inflate(R.layout.ws_popup_unlogin,null);
                Button btnCancel_ws=popupView.findViewById(R.id.btnCancel_ws);
                btnCancel_ws.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                    }
                });
                Button btnUnLogin_ws=popupView.findViewById(R.id.btnUnLogin_ws);
                btnUnLogin_ws.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //清空个人登录信息
                        SharedPreferences sharedPreferences=getContext().getSharedPreferences("userInfo",MODE_PRIVATE);
                        SharedPreferences.Editor editor=sharedPreferences.edit();
                        editor.putString("name","");
                        editor.putString("password","");
                        editor.putString("phone","");
                        editor.putString("uId","");
                        editor.commit();
                        //跳回我的fragment
                        popupWindow.dismiss();
                        Intent intent=new Intent(getContext(), MainActivity.class);
                        intent.setAction("loginBackMyself");
                        startActivity(intent);
                    }
                });
                popupWindow.setContentView(popupView);
                popupWindow.showAtLocation(parent_ws, Gravity.CENTER,0,0);
                }

        });
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

    //绑定事件监听器
    private void bindingListener() {
        logged.setOnClickListener(myListener);
        myachieve.setOnClickListener(myListener);
        myattention.setOnClickListener(myListener);
        myfans.setOnClickListener(myListener);
        myfans.setOnClickListener(myListener);
        school.setOnClickListener(myListener);
    }

    //无缝填充头像上方的封面
    private void centerCropCoverPhoto() {
        RequestOptions requestOptions=new RequestOptions().centerCrop();
        Glide.with(view).load(R.mipmap.myselfbackground).apply(requestOptions).into(ivBackground_ws);
    }

    //初始化控件
    private void initController() {
        ivBackground_ws=view.findViewById(R.id.ivBackground_ws);
        logged=view.findViewById(R.id.sxn_log_linear);
        myachieve=view.findViewById(R.id.sxn_myachieve_linear);
        myattention=view.findViewById(R.id.sxn_myattention_linear);
        myfans=view.findViewById(R.id.sxn_myfans_linear);
        mycourse=view.findViewById(R.id.sxn_course_linear);
        school=view.findViewById(R.id.sxn_school_linear);
        //本页面我的头像
        avatarI= view.findViewById(R.id.sxn_avatar_img);
        //本页面我的昵称
        nameT=view.findViewById(R.id.sxn_nickname_text);
        //本页面我的motto
        mottoT=view.findViewById(R.id.sxn_motto_text);
        ivUnLogOfMyself_ws=view.findViewById(R.id.ivUnLogOfMyself_ws);
        parent_ws=view.findViewById(R.id.parent_ws);
    }


    class MyListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.sxn_log_linear:
                    //跳转到个人信息，传id或字符串
                    intent.setClass(getContext(), PersonalDataActivity.class);
                    startActivity(intent);
                    break;
                case R.id.sxn_myachieve_linear:
                    intent.setClass(getContext(), MyAchieveActivity.class);
                    startActivity(intent);
                    break;
                case R.id.sxn_myfans_linear:
                    intent.setClass(getContext(), MyFansActivity.class);
                    startActivity(intent);
                    break;
                case R.id.sxn_myattention_linear:
                    intent.setClass(getContext(), MyConcernActivity.class);
                    startActivity(intent);
                    break;
                case R.id.sxn_track_linear:
                    break;
                case R.id.sxn_school_linear:
                    intent.setClass(getContext(), MySchoolActivity.class);
                    startActivity(intent);
                    break;
                case R.id.sxn_avatar_img:
                    //直接选择
                    intent.setClass(getContext(), MyAvatarActivity.class);
                    startActivity(intent);
                    break;
                case R.id.sxn_share_linear:
                    break;
            }
        }
    }

}
