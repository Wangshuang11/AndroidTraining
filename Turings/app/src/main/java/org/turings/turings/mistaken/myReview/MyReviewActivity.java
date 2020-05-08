package org.turings.turings.mistaken.myReview;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.youth.banner.Banner;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;

import org.turings.turings.MainActivity;
import org.turings.turings.R;
import org.turings.turings.mistaken.LookUpAndErrorReDoActivity;

import java.util.ArrayList;
import java.util.List;

public class MyReviewActivity extends AppCompatActivity {
    private TextView tvWeekCount_ws;//本周错题量
    private TextView tvAllCount_ws;//总共错题量
    private TextView tvRedoMistaken_ws;//消灭错题
    private TextView tvRevisionRate_ws;//订正率
    private ImageView ivBack_ws;//返回
    private CustomOnclickListener listener;//事件监听器
    private Banner banner;//轮播图
    private TextView btnRedoMistaken_ws;//去重做按钮

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_review);

        //初始化控件
        initController();

        //绑定事件监听器
        registerListener();

        //设置语数外轮播图
        formMySubjectReportBanner();


    }

    //初始化控件
    private void initController() {
        tvWeekCount_ws=findViewById(R.id.tvWeekCount_ws);
        tvAllCount_ws=findViewById(R.id.tvAllCount_ws);
        ivBack_ws=findViewById(R.id.ivBack_ws);
        banner = (Banner) findViewById(R.id.banner);
        tvRedoMistaken_ws=findViewById(R.id.tvRedoMistaken_ws);
        tvRevisionRate_ws=findViewById(R.id.tvRevisionRate_ws);
        btnRedoMistaken_ws=findViewById(R.id.btnRedoMistaken_ws);
    }

    //绑定事件
    private void registerListener() {
        listener = new  CustomOnclickListener();
        ivBack_ws.setOnClickListener(listener);
        btnRedoMistaken_ws.setOnClickListener(listener);
    }

    //设置语数外轮播图
    private void formMySubjectReportBanner(){
        //设置图片加载器
        banner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        List<Integer> images=new ArrayList<>();
        images.add(R.mipmap.chinesews);
        images.add(R.mipmap.mathws);
        images.add(R.mipmap.englishws);
        banner.setImages(images);
        //设置banner动画效果
        banner.setBannerAnimation(Transformer.CubeOut);
        //设置图片点击事件
        banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                switch (position){
                    case 0:
                        Intent intent=new Intent(getApplicationContext(),MathMistakenReportActivity.class);
                        startActivity(intent);
                        break;
                    case 1:
                        Intent intent2=new Intent(getApplicationContext(),MathMistakenReportActivity.class);
                        startActivity(intent2);
                        break;
                    case 2:
                        Intent intent3=new Intent(getApplicationContext(),MathMistakenReportActivity.class);
                        startActivity(intent3);
                        break;
                }
            }
        });
        //banner设置方法全部调用完毕时最后调用
        banner.start();
    }

    public class GlideImageLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            //加载图片
            Glide.with(context).load(path).into(imageView);
        }
    }

    //点击事件
    public class CustomOnclickListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.ivBack_ws://点击返回
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.setAction("mistake");
                    startActivity(intent);
                    finish();
                    break;
                case R.id.btnRedoMistaken_ws://点击按钮，去重做错题
                    Intent intent2=new Intent(getApplicationContext(), LookUpAndErrorReDoActivity.class);
                    startActivity(intent2);
                    break;
            }
        }
    }

}

