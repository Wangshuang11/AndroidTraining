package org.turings.turings.mistaken;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.turings.turings.R;
import org.turings.turings.mistaken.customAdapterAndDialog.CustomAdapterInPagerYLX;

import java.util.ArrayList;
import java.util.List;

public class ShowPaperActivity extends AppCompatActivity implements View.OnClickListener {
    private List<SubjectMsg> lsData = new ArrayList<>();//选在篮子里的所有题目
    private List<SubjectMsg> lsDataX = new ArrayList<>();//选择题集合
    private List<SubjectMsg> lsDataT = new ArrayList<>();//填空题集合
    private List<SubjectMsg> lsDataD = new ArrayList<>();//大题集合
    //适配器
    private CustomAdapterInPagerYLX customAdapterInPagerYLXX;//选择题的适配器
    private CustomAdapterInPagerYLX customAdapterInPagerYLXT;//填空题的适配器
    private CustomAdapterInPagerYLX customAdapterInPagerYLXD;//大题的适配器
    //listView
    private ListView listViewX;//选择
    private ListView listViewT;//填空
    private ListView listViewD;//大题
    //控件
    private TextView txX;
    private TextView txT;
    private TextView txD;
    private ImageView back;//返回箭头
    private TextView titleName;//卷名
    private Button btnNow;
    private Button btnDown;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ylx_activity_show_paper);
        getViews();
        inits();
        registers();
    }


    //获取控件
    private void getViews() {
        txX = findViewById(R.id.tv_x);
        txD = findViewById(R.id.tv_D);
        txT = findViewById(R.id.tv_T);
        listViewX = findViewById(R.id.lvSubX_ylx);
        listViewT = findViewById(R.id.lvSubT_ylx);
        listViewD = findViewById(R.id.lvSubD_ylx);
        back = findViewById(R.id.imgBack_ylx);
        titleName = findViewById(R.id.titleName_ylx);
        btnNow = findViewById(R.id.btnNow_ylx);
        btnDown = findViewById(R.id.btnDown_ylx);
    }

    //初始化数据
    private void inits() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        lsData = (List<SubjectMsg>) bundle.getSerializable("subInCart");
        Log.i("www", "onCreate: "+lsData.toString());
        titleName.setText(intent.getStringExtra("title"));
        for(SubjectMsg subjectMsg:lsData){
            if(subjectMsg.getType().equals("选择题")){
                lsDataX.add(subjectMsg);
            }else if(subjectMsg.getType().equals("填空题")){
                lsDataT.add(subjectMsg);
            }else {
                lsDataD.add(subjectMsg);
            }
        }
        txX.setText("一、选择题(共"+lsDataX.size()+"题)");
        txT.setText("二、填空题(共"+lsDataT.size()+"题)");
        txD.setText("三、大题(共"+lsDataD.size()+"题)");
        //注册适配器绑定数据源
        customAdapterInPagerYLXX = new CustomAdapterInPagerYLX(lsDataX,getApplicationContext(), R.layout.ylx_paper_item_layout);
        customAdapterInPagerYLXT = new CustomAdapterInPagerYLX(lsDataT,getApplicationContext(), R.layout.ylx_paper_item_layout);
        customAdapterInPagerYLXD = new CustomAdapterInPagerYLX(lsDataD,getApplicationContext(), R.layout.ylx_paper_item_layout);
        listViewX.setAdapter(customAdapterInPagerYLXX);
        listViewT.setAdapter(customAdapterInPagerYLXT);
        listViewD.setAdapter(customAdapterInPagerYLXD);
        ListViewUtil.setListViewHeightBasedOnChildren(listViewX);
        ListViewUtil.setListViewHeightBasedOnChildren(listViewT);
        ListViewUtil.setListViewHeightBasedOnChildren(listViewD);
    }
    //注册事件
    private void registers() {
        back.setOnClickListener(this);
        btnNow.setOnClickListener(this);
        btnDown.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.imgBack_ylx://返回
                break;
            case R.id.btnNow_ylx://在线做题
                break;
            case R.id.btnDown_ylx://下载
                break;
        }
    }
}
