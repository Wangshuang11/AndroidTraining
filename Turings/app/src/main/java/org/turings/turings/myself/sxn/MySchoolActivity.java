package org.turings.turings.myself.sxn;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.turings.turings.Fragment.MyselfFragment;
import org.turings.turings.R;
import org.turings.turings.myself.entity.User;
import org.turings.turings.myself.tools.MyUrl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
/*
*心仪大学页面
* 我的心仪大学包括：logo  img，名字name，信息information
* 点击进入建，跳转到详情链接（学校官网）
 */
public class MySchoolActivity extends AppCompatActivity {

    private ListView lvSch;
    private Button button;
    private Button back;
    private List<HashMap<String,Object>> lists;
    private MyUrl myUrl;
    private Context mc;
    private List<User> users;
    private HashMap<String,Object> map;
    private Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sxn_activity_school);
        lvSch=findViewById(R.id.sxn_school_lv);
        button=findViewById(R.id.sxn_school_details);
        back=findViewById(R.id.sxn_school_back);
//        mc=MySchoolActivity.this;
//        myUrl=new MyUrl( mc );
//        lists=new ArrayList<>();
//        lists=myUrl.sendToServers(this.getResources().getString(R.string.connUrl));
        users = new ArrayList<>();
        lists= new ArrayList<>();
        User user=new User(1,"110","zhangsan","123","做自己的自己","img1.png",156,50);
        User user1=new User(2,"120","cendy","124","别闹","img1.png",106,10);
        users.add(user);
        users.add(user1);
        for (User user2 : users) {
            map = new HashMap<>();
            int nn = getResources().getIdentifier(user2.getuAvatar(), "mimap", getBaseContext().getPackageName());
            map.put("userName", user2.getuName());
            map.put("userMotto", user2.getuMotto());
            map.put("userAvatar", nn);
            lists.add(map);
            Log.e("aalist", String.valueOf(lists.size()));
        }
        SimpleAdapter simleAdapter = new SimpleAdapter(getApplicationContext(), lists,
                R.layout.sxn_item_school, new String[]{"userAvatar", "userName", "userMotto"},
                new int[]{R.id.sxn_school_img, R.id.sxn_school_name, R.id.sxn_school_information});
        lvSch.setAdapter(simleAdapter);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent=new Intent();
                intent.setClass(MySchoolActivity.this, MyselfFragment.class);
                startActivity(intent);
            }
        });
    }
}
