package org.turings.turings.myself.sxn;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import org.turings.turings.MainActivity;
import org.turings.turings.R;
import org.turings.turings.myself.tools.MyUrl;

public class MyFansActivity extends AppCompatActivity {

    private ListView fansList;
    private Button back;
    private Intent intent;
    private org.turings.turings.myself.tools.MyUrl myUrl;

    private int uId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (getSupportActionBar() != null) { getSupportActionBar().hide(); }
        super.onCreate(savedInstanceState);
        spContext();
        setContentView(R.layout.sxn_activity_fans);
        myUrl=new MyUrl(this);
        getViews();
        uId= Integer.parseInt(getSharedPreferences("userInfo",MODE_PRIVATE).getString("uId","0"));
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent=new Intent();
                intent.setClass(MyFansActivity.this, MainActivity.class);
                intent.setAction("loginBackMyself");
                startActivityForResult(intent,0);
            }
        });
        myUrl.sendToServerListview(getResources().getString(R.string.connUrl)+"/FansList?uid="+uId+"",
                R.layout.sxn_item_fans,fansList);
    }
    private void getViews(){
        fansList=findViewById(R.id.sxn_fans_lv);
        back=findViewById(R.id.sxn_fans_back);
    }

    private void spContext(){
        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("achStatus", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt("status",1);
        editor.commit();
    }
}
