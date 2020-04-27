package org.turings.turings.index.gw.MainSubjects;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import org.turings.turings.R;

import java.util.List;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

public class HotVideoAdapter extends BaseAdapter{
    private Context mContext;
    private List<String> listUrl;
    private List<String> listTitle;
    //private List<String> listThum;
    public HotVideoAdapter(Context context,List<String> listUrl,List<String> listTitle){
        this.mContext = context;
        this.listUrl = listUrl;
        this.listTitle = listTitle;
        //this.listThum = listThum;
    }
    @Override
    public int getCount() {
        return listUrl.size();
    }

    @Override
    public Object getItem(int i) {
        return listUrl.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(convertView == null){
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.gw_hot_subject_video_item,null);
            viewHolder.mJCVideoPlayerStandard = (JCVideoPlayerStandard) convertView.findViewById(R.id.jc_player);
            viewHolder.shareVideo = convertView.findViewById(R.id.shareVideo);
            viewHolder.storeVideo = convertView.findViewById(R.id.storeVideo);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.mJCVideoPlayerStandard.setUp(listUrl.get(position),viewHolder.mJCVideoPlayerStandard.SCREEN_LAYOUT_NORMAL,listTitle.get(position));
        //加载图片作为视频的显示图片
        Glide.with(mContext)
                .setDefaultRequestOptions(
                        new RequestOptions()
                                .frame(3000000).centerCrop()
                )
                .load(listUrl.get(position))
                .into(viewHolder.mJCVideoPlayerStandard.thumbImageView);
        final ViewHolder finalViewHolder = viewHolder;
        viewHolder.shareVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent textIntent = new Intent(Intent.ACTION_SEND);
                textIntent.setType("text/plain");
                textIntent.putExtra(Intent.EXTRA_TEXT, "我在turings上发现了一个好看的视频："+listUrl.get(position));
                mContext.startActivity(Intent.createChooser(textIntent, "分享给你"));
            }
        });
        final ViewHolder finalViewHolder1 = viewHolder;
        viewHolder.storeVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder=new AlertDialog.Builder(mContext);
                builder.setIcon(R.drawable.yellow_star);//设置图标
                builder.setTitle("您是否想要收藏该视频");//设置对话框的标题
                builder.setMessage("你确定要收藏吗？");//设置对话框的内容
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {  //这个是设置确定按钮

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        Toast.makeText(mContext, "收藏成功", Toast.LENGTH_SHORT).show();
                        finalViewHolder1.storeVideo.setImageResource(R.drawable.yellow_star);
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {  //取消按钮

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        Toast.makeText(mContext, "取消收藏", Toast.LENGTH_SHORT).show();

                    }
                });
                AlertDialog b=builder.create();
                b.show();

            }
        });
        return convertView;
    }

    class ViewHolder{
        JCVideoPlayerStandard mJCVideoPlayerStandard;
        ImageView shareVideo;
        ImageView storeVideo;
    }
}
