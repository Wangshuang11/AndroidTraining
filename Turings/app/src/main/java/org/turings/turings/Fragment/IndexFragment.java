package org.turings.turings.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import org.turings.turings.MyFragmentPager;
import org.turings.turings.R;
import org.turings.turings.index.fragment.FirstFragment;
import org.turings.turings.index.fragment.SecondFragment;
import org.turings.turings.index.fragment.ThirdFragment;

public class IndexFragment extends Fragment {
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private List<String> titles = new ArrayList<>();
    private List<Integer>imageViews=new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.activity_index_fragment,container,false);
        intData();
        initView(view);
        initEvent();
        return view;
    }
    private void intData() {
        titles.clear();
        titles.add("我的课程");
        titles.add("心灵加油站");
        titles.add("象牙塔");
        imageViews.clear();
        imageViews.add(R.drawable.course);
        imageViews.add(R.drawable.boilt);
        imageViews.add(R.drawable.colleague);
    }

    private void initView(View view) {
        mTabLayout = view.findViewById(R.id.tablayout);
        mViewPager = view. findViewById(R.id.viewpager);
    }
    private void initEvent() {
        for (int i = 0; i < titles.size(); i++) {
            mTabLayout.addTab(mTabLayout.newTab().setText(titles.get(i)));
        }
        List<Fragment> fragments = new ArrayList<>();
        fragments.clear();
        fragments.add(new FirstFragment());
        fragments.add(new SecondFragment());
        fragments.add(new ThirdFragment());


        MyFragmentPager fragmentPager = new MyFragmentPager(getChildFragmentManager(),getContext(), fragments, titles,imageViews);
        mViewPager.setAdapter(fragmentPager);
        mTabLayout.setupWithViewPager(mViewPager);
        for (int i = 0; i < titles.size(); i++) {
            mTabLayout.getTabAt(i).setCustomView(getTabView(i));
        }
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                changeTabStatus(tab,true);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                changeTabStatus(tab,false);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
    /**
     * 获取自定义布局
     *
     * @param postion
     * @return
     */
    private View getTabView(final int postion) {
        final View view = LayoutInflater.from(getContext()).inflate(R.layout.activity_item, null);
        ImageView ivTab =  view.findViewById(R.id.iv_tab);
        TextView tvTab =  view.findViewById(R.id.tv_tab);
        ivTab.setImageResource(imageViews.get(postion));
        tvTab.setText(titles.get(postion));
        return view;
    }
    /**
     * 在标签页状态改变的时候更改状态
     * @param tab
     * @param selected
     */
    private void changeTabStatus(TabLayout.Tab tab, boolean selected) {
        View view = tab.getCustomView();
        final ImageView imgTitle = view.findViewById(R.id.iv_tab);
        TextView txtTitle = view.findViewById(R.id.tv_tab);
        imgTitle.setVisibility(View.VISIBLE);
        if (selected) {
            txtTitle.setTextColor(getResources().getColor(R.color.tab_text_selected));
        } else {
            txtTitle.setTextColor(getResources().getColor(R.color.tab_text_normal));
        }
    }
}
