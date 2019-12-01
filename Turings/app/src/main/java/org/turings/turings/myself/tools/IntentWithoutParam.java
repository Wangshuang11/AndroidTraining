package org.turings.turings.myself.tools;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;


public class IntentWithoutParam {
    private Intent intent;
    private AppCompatActivity activity;
    public void jump(Context context, Class toClass){
        intent=new Intent();
        activity= (AppCompatActivity) context;
        intent.setClass(context,toClass);
        activity.startActivity(intent);
    }
}
