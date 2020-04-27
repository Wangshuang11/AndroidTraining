package org.turings.turings.mistaken;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.turings.turings.MainActivity;
import org.turings.turings.R;
import org.turings.turings.mistaken.subjectFragment.ChineseFragment;
import org.turings.turings.mistaken.subjectFragment.EnglishFragment;
import org.turings.turings.mistaken.subjectFragment.MathFragment;

import java.util.ArrayList;

public class LookUpAndErrorReDoActivity extends AppCompatActivity implements View.OnClickListener{
    private ImageView back;//返回
    private TextView math_text_ylx;//选择数学
    private TextView chinese_text_ylx;//选择语文
    private TextView english_text_ylx;//选择英语
    private ViewPager viewPager;
    private ArrayList<Fragment> mDatas;
    private ImageView cursor;
    private int bmpw = 0, mCurrentIndex = 0, mScreen1_5, fixLeftMargin;// bmpw游标宽度,mCurrentIndex表示当前所在页面
    private LinearLayout.LayoutParams params;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_look_up_and_error_re_do_ylx);
        initView();
        initImg();
    }
    // 初始化imageview
    private void initImg() {
        Display disPlay = this.getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        disPlay.getMetrics(outMetrics);
        mScreen1_5 = outMetrics.widthPixels / 5;
        bmpw = outMetrics.widthPixels / 3;
        fixLeftMargin = (bmpw - mScreen1_5) / 2;
        ViewGroup.LayoutParams layoutParams = cursor.getLayoutParams();
        layoutParams.width = mScreen1_5;
        cursor.setLayoutParams(layoutParams);
        /**
         * 设置左侧固定距离
         */
        params = (LinearLayout.LayoutParams) cursor.getLayoutParams();
        params.leftMargin = fixLeftMargin;
        cursor.setLayoutParams(params);
    }

    // 改变游动条
    private void changeTextView(int position) {
        math_text_ylx.setTextColor(getResources().getColor(R.color.black));
        chinese_text_ylx.setTextColor(getResources().getColor(R.color.black));
        english_text_ylx.setTextColor(getResources().getColor(R.color.black));
        switch (position) {
            case 0:// 数学
                math_text_ylx.setTextColor(getResources().getColor(R.color.colorBlue));
                break;
            case 1:// 语文
                chinese_text_ylx.setTextColor(getResources().getColor(R.color.colorBlue));
                break;
            case 2:// 英语
                english_text_ylx.setTextColor(getResources().getColor(R.color.colorBlue));
                break;
        }
        mCurrentIndex = position;
    }

    // 初始化控件
    private void initView() {
        LinearLayout noticeLayout = findViewById(R.id.layout_math);
        noticeLayout.setOnClickListener(this);
        LinearLayout findLayout = findViewById(R.id.layout_chinese);
        findLayout.setOnClickListener(this);
        LinearLayout linkmanLayout = findViewById(R.id.layout_english);
        linkmanLayout.setOnClickListener(this);
        math_text_ylx = findViewById(R.id.math_text_ylx);
        chinese_text_ylx = findViewById(R.id.chinese_text_ylx);
        english_text_ylx = findViewById(R.id.english_text_ylx);
        back=findViewById(R.id.img_ylx);
        back.setOnClickListener(this);
        cursor = findViewById(R.id.cursor);
        viewPager = findViewById(R.id.viewpager);

        mDatas = new ArrayList<>();
        MathFragment tab01 = MathFragment.newInstance("数学");
        ChineseFragment tab02 =  ChineseFragment.newInstance("语文");
        EnglishFragment tab03 = EnglishFragment.newInstance("英语");

        mDatas.add(tab01);
        mDatas.add(tab02);
        mDatas.add(tab03);

        FragmentPagerAdapter adapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public int getCount() {
                return mDatas.size();
            }

            @Override
            public Fragment getItem(int positon) {
                return mDatas.get(positon);
            }
        };

        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int positon) {
                // 滑动结束
                changeTextView(positon);
            }

            @Override
            public void onPageScrolled(int positon, float positonOffset, int positonOffsetPx) {
                // 滑动过程
                if (mCurrentIndex == 0 && positon == 0) {// 0-->1
                    params.leftMargin = (int) (mCurrentIndex * bmpw + positonOffset
                            * bmpw)
                            + fixLeftMargin;
                } else if (mCurrentIndex == 1 && positon == 0) {// 1-->0
                    params.leftMargin = (int) (mCurrentIndex * bmpw + (positonOffset - 1)
                            * bmpw)
                            + fixLeftMargin;
                } else if (mCurrentIndex == 1 && positon == 1) {// 1-->1
                    params.leftMargin = (int) (mCurrentIndex * bmpw + positonOffset
                            * bmpw)
                            + fixLeftMargin;
                } else if (mCurrentIndex == 2 && positon == 1) {// 2-->1
                    params.leftMargin = (int) (mCurrentIndex * bmpw + (positonOffset - 1)
                            * bmpw)
                            + fixLeftMargin;
                } else if (mCurrentIndex == 2 && positon == 2) {// 2-->2
                    params.leftMargin = (int) (mCurrentIndex * bmpw + positonOffset
                            * bmpw)
                            + fixLeftMargin;
                }
                cursor.setLayoutParams(params);
            }

            @Override
            public void onPageScrollStateChanged(int positon) {

            }
        });
        viewPager.setOffscreenPageLimit(mDatas.size());// 缓存
    }

    // 控件点击事件
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_math:// 数学
                viewPager.setCurrentItem(0);
                break;
            case R.id.layout_chinese:// 语文
                viewPager.setCurrentItem(1);
                break;
            case R.id.layout_english:// 英语
                viewPager.setCurrentItem(2);
                break;
            case R.id.img_ylx://返回
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.setAction("mistake");
                startActivity(intent);
                finish();
                break;
        }
    }
}
