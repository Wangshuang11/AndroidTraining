package org.turings.turings.index;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import org.turings.turings.MainActivity;
import org.turings.turings.R;
import org.turings.turings.index.adapter.ViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class IndexActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener{
    private ViewPager vp;
    private ViewPagerAdapter vpAdapter;
    private List<View> views;
    private ImageView[]dots;
    private Button btn_start;
    private int[]ids={R.id.iv1,R.id.iv2,R.id.iv3};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lph_activity_index);
        SharedPreferences sharedPreferences =getSharedPreferences("flag", Context.MODE_PRIVATE);
        SharedPreferences.Editor  editor=sharedPreferences.edit();
        editor.putString("flag","false");
        editor.commit();
        initViews();
        innitDots();
    }

    private void initViews() {
        LayoutInflater inflater= LayoutInflater.from(this);
        views=new ArrayList<>();
        views.add(inflater.inflate(R.layout.lph_one,null));
        views.add(inflater.inflate(R.layout.lph_two,null));
        views.add(inflater.inflate(R.layout.lph_three,null));
        vpAdapter=new ViewPagerAdapter(views,this);
        vp=findViewById(R.id.viewpager);
        vp.setAdapter(vpAdapter);
        btn_start=views.get(2).findViewById(R.id.btn_start);
        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
	            intent.setAction("跳广告");
                intent.setClass(IndexActivity.this,MainActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.in, R.anim.out);
                IndexActivity.this.finish();
            }
        });
        vp.addOnPageChangeListener(this);
    }

    private void innitDots(){
        dots=new ImageView[views.size()];
        for(int i=0;i<dots.length;i++){
            dots[i]=findViewById(ids[i]);
        }
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int i) {
        for(int j=0;j<ids.length;j++){
            if(j==i){
                dots[j].setImageResource(R.drawable.selected_point);
            }else{
                dots[j].setImageResource(R.drawable.point);
            }
        }
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }
}
