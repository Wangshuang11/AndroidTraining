package org.turings.turings.myself.sxn;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import org.turings.turings.MainActivity;
import org.turings.turings.R;
import org.turings.turings.myself.entity.School;
import org.turings.turings.myself.tools.MyUrl;


import java.util.List;

/*
*心仪大学页面
* 我的心仪大学包括：logo  img，名字name，信息information
* 点击进入建，跳转到详情链接（学校官网）
 */
public class MySchoolActivity extends AppCompatActivity {

    private ListView lvSch;
    private Button back;
    private org.turings.turings.myself.tools.MyUrl myUrl;
    private Intent intent;
    private List<School> schools;
    private org.turings.turings.myself.tools.ImageAdapter imageAdapter;
    public int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (getSupportActionBar() != null) { getSupportActionBar().hide(); }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sxn_activity_school);
        lvSch=(ListView) findViewById(R.id.sxn_school_lv);
        back=findViewById(R.id.sxn_school_back);
        id=Integer.parseInt(getSharedPreferences("userInfo",MODE_PRIVATE).getString("uId","0"));
        myUrl=new MyUrl(this );
        myUrl.sendToServerListview(getResources().getString(R.string.connUrl)+"/GetSchoolsList?uid="+id,
                R.layout.sxn_item_school,lvSch);

        lvSch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.e("listschool", 111+"=====================");

            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent=new Intent();
                intent.setClass(MySchoolActivity.this, MainActivity.class);
                intent.setAction("loginBackMyself");
                startActivityForResult(intent,0);
            }
        });
    }
}
