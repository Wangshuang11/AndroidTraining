package org.turings.turings;

import android.graphics.Color;

import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import org.turings.turings.Fragment.IndexFragment;
import org.turings.turings.Fragment.MistakenFragment;
import org.turings.turings.Fragment.MyselfFragment;
import org.turings.turings.Fragment.NearFragment;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private Map<String,ImageView> imageViewMap=new HashMap<>();
    private Map<String, TextView> textViewHashMap=new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentTabHost fragmentTabHost=findViewById(android.R.id.tabhost);
        fragmentTabHost.setup(this,getSupportFragmentManager(),android.R.id.tabcontent);
        TabHost.TabSpec tabSpec1=fragmentTabHost.newTabSpec("tag1")
                .setIndicator(getTabSpecView("tag1",R.mipmap.indexgray,"首页"));
        fragmentTabHost.addTab(tabSpec1, IndexFragment.class, null);
        TabHost.TabSpec tabSpec2=fragmentTabHost.newTabSpec("tag2")
                .setIndicator(getTabSpecView("tag2",R.mipmap.mistakengray,"错题本"));
        fragmentTabHost.addTab(tabSpec2, MistakenFragment.class, null);
        TabHost.TabSpec tabSpec3=fragmentTabHost.newTabSpec("tag3")
                .setIndicator(getTabSpecView("tag3",R.mipmap.neargray,"附近"));
        fragmentTabHost.addTab(tabSpec3, NearFragment.class, null);
        TabHost.TabSpec tabSpec4=fragmentTabHost.newTabSpec("tag4")
                .setIndicator(getTabSpecView("tag4",R.mipmap.myselfgray,"我的"));
        fragmentTabHost.addTab(tabSpec4, MyselfFragment.class, null);

        fragmentTabHost.setCurrentTab(0);
        imageViewMap.get("tag1").setImageResource(R.mipmap.indexblue);
        textViewHashMap.get("tag1").setTextColor(Color.argb(100,26,168,215));

        //切换选项卡,图片变色
        fragmentTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                textViewHashMap.get(tabId).setTextColor(Color.argb(100,26,168,215));
                switch (tabId){
                    case "tag1":
                        imageViewMap.get("tag1").setImageResource(R.mipmap.indexblue);
                        imageViewMap.get("tag2").setImageResource(R.mipmap.mistakengray);
                        imageViewMap.get("tag3").setImageResource(R.mipmap.neargray);
                        imageViewMap.get("tag4").setImageResource(R.mipmap.myselfgray);
                        textViewHashMap.get("tag2").setTextColor(getResources().getColor(android.R.color.darker_gray));
                        textViewHashMap.get("tag3").setTextColor(getResources().getColor(android.R.color.darker_gray));
                        textViewHashMap.get("tag4").setTextColor(getResources().getColor(android.R.color.darker_gray));
                        break;
                    case "tag2":
                        imageViewMap.get("tag1").setImageResource(R.mipmap.indexgray);
                        imageViewMap.get("tag2").setImageResource(R.mipmap.mistakenblue);
                        imageViewMap.get("tag3").setImageResource(R.mipmap.neargray);
                        imageViewMap.get("tag4").setImageResource(R.mipmap.myselfgray);
                        textViewHashMap.get("tag1").setTextColor(getResources().getColor(android.R.color.darker_gray));
                        textViewHashMap.get("tag3").setTextColor(getResources().getColor(android.R.color.darker_gray));
                        textViewHashMap.get("tag4").setTextColor(getResources().getColor(android.R.color.darker_gray));
                        break;
                    case "tag3":
                        imageViewMap.get("tag1").setImageResource(R.mipmap.indexgray);
                        imageViewMap.get("tag2").setImageResource(R.mipmap.mistakengray);
                        imageViewMap.get("tag3").setImageResource(R.mipmap.nearblue);
                        imageViewMap.get("tag4").setImageResource(R.mipmap.myselfgray);
                        textViewHashMap.get("tag1").setTextColor(getResources().getColor(android.R.color.darker_gray));
                        textViewHashMap.get("tag2").setTextColor(getResources().getColor(android.R.color.darker_gray));
                        textViewHashMap.get("tag4").setTextColor(getResources().getColor(android.R.color.darker_gray));
                        break;
                    case "tag4":
                        imageViewMap.get("tag1").setImageResource(R.mipmap.indexgray);
                        imageViewMap.get("tag2").setImageResource(R.mipmap.mistakengray);
                        imageViewMap.get("tag3").setImageResource(R.mipmap.neargray);
                        imageViewMap.get("tag4").setImageResource(R.mipmap.myselfblue);
                        textViewHashMap.get("tag1").setTextColor(getResources().getColor(android.R.color.darker_gray));
                        textViewHashMap.get("tag2").setTextColor(getResources().getColor(android.R.color.darker_gray));
                        textViewHashMap.get("tag3").setTextColor(getResources().getColor(android.R.color.darker_gray));
                        break;
                }
            }
        });
    }

    //加载选项卡布局:
    public View getTabSpecView(String tagId, int imageResId, String title){
        LayoutInflater layoutInflater=getLayoutInflater();
        View view=layoutInflater.inflate(R.layout.tabspec_layout,null);
        ImageView imageView=view.findViewById(R.id.iv_icon);
        imageView.setImageResource(imageResId);
        imageViewMap.put(tagId,imageView);
        TextView textView=view.findViewById(R.id.tv_title);
        textView.setText(title);
        textViewHashMap.put(tagId,textView);
        return  view;
    }
}
