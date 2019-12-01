package org.turings.turings.myself.sxn;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;


import org.turings.turings.Fragment.MyselfFragment;
import org.turings.turings.R;
import org.turings.turings.myself.entity.Fan;
import org.turings.turings.myself.tools.MyUrl;

import java.util.HashMap;
import java.util.List;

public class MyFansActivity extends AppCompatActivity {

    private ListView fansList;
    private Button back;
    private List<Fan> fans;
    private List<HashMap<String,Object>> lists;
    private HashMap<String,Object> map;
    private Intent intent;
    private MyUrl myUrl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sxn_activity_fans);
        back=findViewById(R.id.sxn_fans_back);
        myUrl=new MyUrl(this);
        getViews();
        lists=myUrl.sendToServerFan(getResources().getString(R.string.connUrl)+"/FansList?uid=1");
        SimpleAdapter simleAdapter = new SimpleAdapter(MyFansActivity.this, lists,
                R.layout.sxn_item_fans, new String[]{"userAvatar", "userName", "userMotto"},
                new int[]{R.id.sxn_fans_img, R.id.sxn_fans_nickname_text, R.id.sxn_fans_motto_text});
        fansList.setAdapter(simleAdapter);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent=new Intent();
                intent.setClass(MyFansActivity.this, MyselfFragment.class);
                startActivity(intent);
            }
        });

    }
    private void getViews(){
        fansList=findViewById(R.id.sxn_fans_lv);
    }

}
