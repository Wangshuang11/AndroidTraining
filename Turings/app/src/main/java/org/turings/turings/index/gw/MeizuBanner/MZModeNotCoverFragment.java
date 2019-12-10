package org.turings.turings.index.gw.MeizuBanner;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.zhouwei.mzbanner.MZBannerView;
import com.zhouwei.mzbanner.holder.MZHolderCreator;
import com.zhouwei.mzbanner.holder.MZViewHolder;

import org.turings.turings.R;
import org.turings.turings.UrlActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhouwei on 17/5/31.
 */

public class MZModeNotCoverFragment extends Fragment {
    public static final String TAG = "MZModeBannerFragment";
    public static final int []BANNER = new int[]{R.drawable.indexcollege5, R.drawable.indexcollege3, R.drawable.indexcollege1, R.drawable.indexcollege2, R.drawable.indexcollege4};
    private MZBannerView mMZBanner;



    public static MZModeNotCoverFragment newInstance(){
        return new MZModeNotCoverFragment();
    }

    private void initView(View view) {

        mMZBanner = (MZBannerView) view.findViewById(R.id.banner);
        mMZBanner.setBannerPageClickListener(new MZBannerView.BannerPageClickListener() {
            @Override
            public void onPageClick(View view, int position) {
                switch (position){
                    case 0:
//                        Uri uri = Uri.parse("https://www.pku.edu.cn/");    //设置跳转的网站
//                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        Intent intent = new Intent(getContext(), UrlActivity.class);
                        intent.putExtra("urlStr","https://www.pku.edu.cn/");
                        startActivity(intent);
                        break;
                    case 1:
                        Intent intent1 = new Intent(getContext(),UrlActivity.class);
                        intent1.putExtra("urlStr","https://www.tsinghua.edu.cn/publish/thu2018/index.html");
                        startActivity(intent1);
                        break;
                    case 2:
                        Intent intent2 = new Intent(getContext(),UrlActivity.class);
                        intent2.putExtra("urlStr","http://www.ox.ac.uk/");
                        startActivity(intent2);
                        break;
                    case 3:
                        Intent intent3 = new Intent(getContext(),UrlActivity.class);
                        intent3.putExtra("urlStr","https://www.cam.ac.uk/");
                        startActivity(intent3);
                        break;
                    case 4:
                        Intent intent4 = new Intent(getContext(),UrlActivity.class);
                        intent4.putExtra("urlStr","http://www.zju.edu.cn/");
                        startActivity(intent4);
                        break;
                }
                //Toast.makeText(getContext(),"click page:"+(position+1),Toast.LENGTH_SHORT).show();
            }
        });
        mMZBanner.addPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Log.e(TAG,"----->addPageChangeLisnter:"+position + "positionOffset:"+positionOffset+ "positionOffsetPixels:"+positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                Log.e(TAG,"addPageChangeLisnter:"+position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        List<Integer> bannerList = new ArrayList<>();
        for(int i=0;i<BANNER.length;i++){
            bannerList.add(BANNER[i]);
        }
        mMZBanner.setIndicatorVisible(true);
        mMZBanner.setPages(bannerList, new MZHolderCreator<BannerViewHolder>() {
            @Override
            public BannerViewHolder createViewHolder() {
                return new BannerViewHolder();
            }
        });

    }

    public static class BannerViewHolder implements MZViewHolder<Integer> {
        private ImageView mImageView;
        @Override
        public View createView(Context context) {
            // 返回页面布局文件
            View view = LayoutInflater.from(context).inflate(R.layout.banner_item,null);
            mImageView = (ImageView) view.findViewById(R.id.banner_image);
            return view;
        }

        @Override
        public void onBind(Context context, int position, Integer data) {
            // 数据绑定
            mImageView.setImageResource(data);
        }
    }

    public static class BannerPaddingViewHolder implements MZViewHolder<Integer> {
        private ImageView mImageView;
        @Override
        public View createView(Context context) {
            // 返回页面布局文件
            View view = LayoutInflater.from(context).inflate(R.layout.banner_item_padding,null);
            mImageView = (ImageView) view.findViewById(R.id.banner_image);
            return view;
        }

        @Override
        public void onBind(Context context, int position, Integer data) {
            // 数据绑定
            mImageView.setImageResource(data);
        }
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.mz_mode_not_cover,null);
        initView(view);
        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        mMZBanner.pause();
    }

    @Override
    public void onResume() {
        super.onResume();
        mMZBanner.start();
    }
}
