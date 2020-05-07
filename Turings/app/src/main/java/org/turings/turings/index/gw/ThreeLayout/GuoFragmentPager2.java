package org.turings.turings.index.gw.ThreeLayout;

import android.support.v4.app.Fragment;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * @ClassName GuoFragmentPager2
 * @Description
 * @Author liupenghui
 * @Date 2020-05-06 09:16
 */
public class GuoFragmentPager2 extends FragmentStatePagerAdapter {

    private String []titles;
    private List<Fragment> fragments;

    public GuoFragmentPager2(FragmentManager fm, String[] titles, List<Fragment> fragments) {
        super(fm);
        this.titles = titles;
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
    /**
     * 设置标签栏的标题
     *
     * @param position
     * @return
     */
    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
