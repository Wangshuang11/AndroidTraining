package org.turings.turings.myself.sxn;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;


import org.turings.turings.Fragment.MyselfFragment;
import org.turings.turings.R;
import org.turings.turings.myself.tools.MyUrl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
/*
*我的关注
*头像，昵称，座右铭
* 点击进入之后。。。。。。。。。。。。。。。。。。。
 */

public class MyConcernActivity extends AppCompatActivity {

    private MyUrl myUrl;
    private List<HashMap<String,Object>> lists;
    private ListView lvCon;
    private Context mc;
    private Button back;
    private Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sxn_activity_concern);
        lvCon=findViewById(R.id.sxn_concern_lv);
        back=findViewById(R.id.sxn_concern_back);
        mc=MyConcernActivity.this;
        myUrl=new MyUrl( mc );
        lists=new ArrayList<>();
        lists=myUrl.sendToServerUser(getResources().getString(R.string.connUrl));
        SimpleAdapter simleAdapter = new SimpleAdapter(getApplicationContext(), lists,
                R.layout.sxn_item_concerns, new String[]{"userAvatar", "userName", "userMotto"},
                new int[]{R.id.sxn_concern_img, R.id.sxn_concern_nickname_text, R.id.sxn_concern_motto_text});
        lvCon.setAdapter(simleAdapter);
        //每一项点击事件
        lvCon.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent=new Intent();
                intent.setClass(MyConcernActivity.this, MyselfFragment.class);
                startActivity(intent);
            }
        });
    }
}
