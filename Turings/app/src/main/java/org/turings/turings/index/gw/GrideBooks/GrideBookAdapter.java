package org.turings.turings.index.gw.GrideBooks;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.turings.turings.R;

import java.util.List;

public class GrideBookAdapter extends RecyclerView.Adapter<GrideBookAdapter.BeautyViewHolder> {
    /**
     * 上下文
     */
    private Context mContext;
    /**
     * 数据集合
     */
    private List<GrideBook> data;

    public GrideBookAdapter(List<GrideBook> data, Context context) {
        this.data = data;
        this.mContext = context;
    }

    @Override
    public BeautyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //加载item 布局文件
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.gw_gride_book_item, parent, false);
        final  BeautyViewHolder holder = new BeautyViewHolder(view);
        holder.beautyImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("https://book.douban.com/subject/1873231/");    //设置跳转的网站
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                mContext.startActivity(intent);
            }
        });
        return new BeautyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BeautyViewHolder holder, int position) {
        //将数据设置到item上
        final GrideBook beauty = data.get(position);
        Glide.with(mContext).load(beauty.getImageId()).into(holder.beautyImage);
        holder.nameTv.setText(beauty.getName());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    static class BeautyViewHolder extends RecyclerView.ViewHolder {
        ImageView beautyImage;
        TextView nameTv;

        public BeautyViewHolder(View itemView) {
            super(itemView);
            beautyImage = itemView.findViewById(R.id.image_item);
            nameTv = itemView.findViewById(R.id.name_item);
        }
    }
}