package org.turings.turings;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.ContextCompat;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;

import java.util.List;

public class MyFragmentPager extends FragmentStatePagerAdapter {
    private Context mContext;
    private List<Fragment> mFragments;
    private List<String> mTitles;
    private List<Integer>imageViews;
    public MyFragmentPager(FragmentManager fm, List<Fragment> fragments, List<String> titles) {
        super(fm);
        mFragments = fragments;
        mTitles = titles;
    }

    public MyFragmentPager(FragmentManager fm, Context mContext, List<Fragment> mFragments, List<String> mTitles, List<Integer> imageViews) {
        super(fm);
        this.mContext = mContext;
        this.mFragments = mFragments;
        this.mTitles = mTitles;
        this.imageViews = imageViews;
    }

    @Override
    public Fragment getItem(int position) {
        return  mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    /**
     * 设置标签栏的标题
     *
     * @param position
     * @return
     */
    @Override
//    public CharSequence getPageTitle(int position) {
//        return mTitles.get(position);
//    }

    public CharSequence getPageTitle(int position) {
        Drawable image = ContextCompat.getDrawable(mContext,imageViews.get(position));
        image.setBounds(0, 0, image.getIntrinsicWidth(), image.getIntrinsicHeight());
        SpannableString sb = new SpannableString(" ");
        ImageSpan imageSpan = new ImageSpan(image, ImageSpan.ALIGN_BOTTOM);
        sb.setSpan(imageSpan, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return sb;
    }

}
