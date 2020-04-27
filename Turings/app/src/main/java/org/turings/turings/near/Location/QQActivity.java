package org.turings.turings.near.Location;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.ui.EaseChatFragment;
import com.hyphenate.exceptions.HyphenateException;

import org.turings.turings.R;

import static com.hyphenate.easeui.EaseConstant.CHATTYPE_GROUP;

public class QQActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qq);

//        signin();
        EaseChatFragment chatFragment = new EaseChatFragment();
//        chatFragment.setArguments(getIntent().getExtras());
        Bundle args = new Bundle();
        args.putInt(EaseConstant.EXTRA_CHAT_TYPE, EaseConstant.CHATTYPE_SINGLE);
        args.putString(EaseConstant.EXTRA_USER_ID, "user1");
        chatFragment.setArguments(args);
        getSupportFragmentManager().beginTransaction().add(R.id.container,chatFragment).commit();

    }

    //    注册
    private void signup(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    SharedPreferences sharedPreferences=getApplicationContext().getSharedPreferences("userInfo",MODE_PRIVATE);
                    int aid = Integer.parseInt(sharedPreferences.getString("uId",""));
                    EMClient.getInstance().createAccount(aid+"","wangshuang");
                    Log.e("lyh","注册成功");
                } catch (HyphenateException e) {
                    e.printStackTrace();
                    Log.e("lyh","注册失败"+e.getErrorCode()+","+e.getMessage());
                }
            }
        }).start();
    }
    //    登录
    private void signin(){
        EMClient.getInstance().login("王大爽", "wangshaung", new EMCallBack() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onError(int i, String s) {

            }

            @Override
            public void onProgress(int i, String s) {

            }
        });
    }


}
