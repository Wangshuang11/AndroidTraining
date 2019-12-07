package org.turings.turings.index.gw.CouseXiangqing;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import org.turings.turings.R;
import org.turings.turings.index.gw.ThreeLayout.CourseOneFragment;
import org.turings.turings.index.gw.ThreeLayout.CourseThreeFragment;
import org.turings.turings.index.gw.ThreeLayout.CourseTwoFragment;
import org.turings.turings.index.gw.ThreeLayout.GuoFragmentPager;

import java.util.ArrayList;
import java.util.List;

public class CourseDetail extends AppCompatActivity {
    Toolbar toolbar;
    CollapsingToolbarLayout collapsingToolbar;
    TabLayout tabLayout;
    AppBarLayout appbar;
    private ViewPager viewpager;
    private Button button;
    private SmartRefreshLayout srl;
    private ImageView zixun;

    //*******************
    private String [] mTitles = {"课程简介", "目录", "评论"};
    private GuoFragmentPager guoFragmentPager;
    private CourseTwoFragment courseTwoFragment;
    private CourseOneFragment courseOneFragment;
    private CourseThreeFragment courseThreeFragment;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //当系统版本为4.4或者4.4以上时可以使用沉浸式状态栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gw_course_detail);
        //toolbar = (Toolbar) findViewById(R.id.toolbar);
        collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsingToolbar);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        appbar = (AppBarLayout) findViewById(R.id.appbar);
        viewpager = (ViewPager) findViewById(R.id.viewpagerguo);
        button = findViewById(R.id.qiangou);
        zixun = findViewById(R.id.zixun);
        zixun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isQQClientAvailable(getApplication())) {
                    // 跳转到客服的QQ
                    String url = "mqqwpa://im/chat?chat_type=wpa&uin=3337258081";
                    Intent intent2 = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    // 跳转前先判断Uri是否存在，如果打开一个不存在的Uri，App可能会崩溃
                    if (isValidIntent(getApplication(),intent2)) {
                        startActivity(intent2);
                    }else {
                        Toast.makeText(getApplication(),"客服QQ异常",Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(getApplication(),"请先安装QQ客户端",Toast.LENGTH_SHORT).show();
                }
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplication(),"购买成功",Toast.LENGTH_SHORT).show();
                button.setText("购买成功");
            }
        });
        collapsingToolbar.setExpandedTitleColor(Color.parseColor("#00ffffff"));//设置还没收缩时状态下字体颜色
        collapsingToolbar.setCollapsedTitleTextColor(Color.WHITE);//设置收缩后Toolbar上字体的
        addTabToTabLayout();

        Intent intent = getIntent();
        String parentId = intent.getStringExtra("parentId");

        courseTwoFragment = new CourseTwoFragment();
        courseOneFragment = new CourseOneFragment();
        courseThreeFragment = new CourseThreeFragment();
        Bundle bundle = new Bundle();
        bundle.putString("parentId",parentId);
        courseTwoFragment.setArguments(bundle);
        courseThreeFragment.setArguments(bundle);
        courseOneFragment.setArguments(bundle);






        setupWithViewPager();
    }




    private void addTabToTabLayout() {
        for (int i = 0; i < mTitles.length; i++) {
            tabLayout.addTab(tabLayout.newTab().setText(mTitles[i]));
        }
    }

    /**
     * Description：初始化FragmentPagerAdapter适配器并给ViewPager设置上该适配器，最后关联TabLayout和ViewPager
     */
    private void setupWithViewPager() {
        List<Fragment> mFragments = new ArrayList<>();
        mFragments.add(courseOneFragment);
        mFragments.add(courseTwoFragment);
        mFragments.add(courseThreeFragment);

        guoFragmentPager = new GuoFragmentPager(getSupportFragmentManager());
        guoFragmentPager.addTitlesAndFragments(mTitles, mFragments);

        viewpager.setAdapter(guoFragmentPager); // 给ViewPager设置适配器
        tabLayout.setupWithViewPager(viewpager); //关联TabLayout和ViewPager
    }
    public static boolean isQQClientAvailable(Context context) {
        final PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equalsIgnoreCase("com.tencent.qqlite") || pn.equalsIgnoreCase("com.tencent.mobileqq")) {
                    return true;
                }
            }
        }
        return false;
    }
    /**
     * 判断Uri是否有效
     */
    public static boolean isValidIntent(Context context, Intent intent) {
        PackageManager packageManager = context.getPackageManager();
        List<ResolveInfo> activities = packageManager.queryIntentActivities(intent, 0);
        return !activities.isEmpty();
    }

}
