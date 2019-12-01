package org.turings.turings.myself.sxn;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.SimpleAdapter;


import org.turings.turings.Fragment.MyselfFragment;
import org.turings.turings.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MyAchieveActivity extends AppCompatActivity {

    private GridView gridView;
    private List<HashMap<String,Object>> lists;
    private HashMap<String,Object> map;
    private Button back;
    private Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sxn_activity_achieve);
        gridView=findViewById(R.id.sxn_achieve_gv);
        back=findViewById(R.id.sxn_achieve_back);
        lists=new ArrayList<>();
        map=new HashMap<>();
        map.put("img",getResources().getIdentifier("img1","mipmap",getBaseContext().getPackageName()));
        map.put("name","错题达人");
        lists.add(map);
        Log.e("成绩也","achieve");
        gridView.setAdapter(new SimpleAdapter(MyAchieveActivity.this, lists,
                R.layout.sxn_item_achieve,
                new String[]{"img", "name"},
                new int[]{R.id.sxn_achieve_img, R.id.sxn_achieve_honor}));
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent=new Intent(MyAchieveActivity.this, MyselfFragment.class);
                startActivity(intent);
            }
        });
    }
}
