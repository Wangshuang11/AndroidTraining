package org.turings.turings.index.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import org.turings.turings.R;
import org.turings.turings.UrlActivity;
import org.turings.turings.index.entity.School;

import java.util.List;


public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private Context context;
    private int itemlayout;
    private List<School> schools;

    public RecyclerAdapter() {
    }

    public RecyclerAdapter(Context context, int itemlayout, List<School> schools) {
        this.context = context;
        this.itemlayout = itemlayout;
        this.schools = schools;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = null;
        RecyclerView.ViewHolder holder = null;
        v = LayoutInflater.from(context).inflate(itemlayout, viewGroup, false);
        holder = new ItemHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {
        ItemHolder holder = (ItemHolder) viewHolder;
        RequestOptions ro=new RequestOptions().placeholder(R.drawable.loading);
        Glide.with(context).load(schools.get(i).getSrc()).apply(ro).into(holder.img_grid);
        holder.text_grid.setText(schools.get(i).getName());
        holder.img_grid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, UrlActivity.class);
                intent.putExtra("urlStr",schools.get(i).getUrl());
                intent.setAction("url");
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(null!=schools){
            return schools.size();
        }
        return 0;
    }

    private class ItemHolder extends RecyclerView.ViewHolder {
        private ImageView img_grid;
        private TextView text_grid;
		public ItemHolder(View v) {
		    super(v);
		    img_grid=v.findViewById(R.id.img_grid);
		    text_grid=v.findViewById(R.id.text_grid);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
