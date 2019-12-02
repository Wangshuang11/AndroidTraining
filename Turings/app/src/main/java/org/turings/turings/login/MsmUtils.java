package org.turings.turings.login;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;


public class MsmUtils extends BroadcastReceiver {
    /**
     * 发送 MSM
     * @param activity
     * @param targetNumber
     * @param content
     */
    public static void sendMsm(Activity activity, String targetNumber, String content) {
        //创建一个PendingIntent对象
        PendingIntent pi = PendingIntent.getActivity(activity, 0, new Intent(), 0);
        //获取SmsManager
        SmsManager sManager = SmsManager.getDefault();
        //发送短信
        sManager.sendTextMessage(targetNumber, null, content, pi, null);
    }

    /**
     * 接收到新MSM
     * @param context
     * @param intent
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        SmsMessage smsMessage;
        if (null != bundle) {
            Object[] smsObj = (Object[]) bundle.get("pdus");
            for (Object object : smsObj) {
                smsMessage = SmsMessage.createFromPdu((byte[]) object);
                String content = smsMessage.getDisplayMessageBody();
                String from = smsMessage.getOriginatingAddress();
                //Do your coding here
            }
        }
    }
}
