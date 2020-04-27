package org.turings.turings.myself.sxn;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import org.turings.turings.MainActivity;
import org.turings.turings.R;
import org.turings.turings.myself.tools.MyUrl;

/*
*我的关注
*头像，昵称，座右铭
* 点击进入之后。。。。。。。。。。。。。。。。。。。
 */

public class MyConcernActivity extends AppCompatActivity {

    private MyUrl myUrl;
    private ListView lvCon;
    private Context mc;
    private Button back;
    private Intent intent;
    private int uId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (getSupportActionBar() != null) { getSupportActionBar().hide(); }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sxn_activity_concern);
        getViews();
        mc=MyConcernActivity.this;
        myUrl=new MyUrl( mc );
        uId= Integer.parseInt(getSharedPreferences("userInfo",MODE_PRIVATE).getString("uId","0"));
        myUrl.sendToServerListview(getResources().getString(R.string.connUrl)+"/AtList?fid="+uId,
                R.layout.sxn_item_concerns,
                lvCon);
        //每一项点击事件

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent=new Intent();
                intent.setClass(MyConcernActivity.this, MainActivity.class);
                intent.setAction("loginBackMyself");
                startActivityForResult(intent,0);
            }
        });
    }
    private void getViews(){
        lvCon=findViewById(R.id.sxn_concern_lv);
        back=findViewById(R.id.sxn_concern_back);
    }
}
