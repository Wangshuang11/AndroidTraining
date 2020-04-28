package org.turings.turings;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.util.Log;

import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMGroupReadAck;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMOptions;
import com.hyphenate.easeui.EaseUI;

import java.util.List;

public class TApplication extends Application {
    private EaseUI easeUI;
    @Override
    public void onCreate() {
        super.onCreate();
        easeUI = EaseUI.getInstance();
        EaseUI.getInstance().init(this,null);
        EMClient.getInstance().setDebugMode(true);
        registerMessageListener();
    }

    //全局监听，后台应用或者被杀死，从这里接收
    protected void registerMessageListener() {
        EMMessageListener messageListener = new EMMessageListener() {
            private BroadcastReceiver broadCastReceiver = null;

            @Override
            public void onMessageReceived(List<EMMessage> messages) {
                Log.e("msg",messages.toString());
            }

            @Override
            public void onCmdMessageReceived(List<EMMessage> messages) {
                for (EMMessage message : messages) {

                }
            }

            @Override
            public void onMessageRead(List<EMMessage> messages) {
            }

            @Override
            public void onGroupMessageRead(List<EMGroupReadAck> list) {

            }

            @Override
            public void onReadAckForGroupMessageUpdated() {

            }

            @Override
            public void onMessageDelivered(List<EMMessage> message) {
            }

            @Override
            public void onMessageRecalled(List<EMMessage> list) {

            }

            @Override
            public void onMessageChanged(EMMessage message, Object change) {

            }
        };
        EMClient.getInstance().chatManager().addMessageListener(messageListener);
    }
}
