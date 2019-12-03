package org.turings.turings.index.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import org.turings.turings.R;
import org.turings.turings.index.entity.Pre;

import java.util.ArrayList;
import java.util.List;

public class RecyclerListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private Context context;
    private List<Pre> pres=new ArrayList<>();
    private int itemlayout;
    private View view;

    public RecyclerListAdapter() {
    }

    public RecyclerListAdapter(Context context, List<Pre> pres, int itemlayout, View view) {
        this.context = context;
        this.pres = pres;
        this.itemlayout = itemlayout;
        this.view = view;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = null;
        RecyclerView.ViewHolder holder = null;
        v = LayoutInflater.from(context).inflate(itemlayout,parent, false);
        holder = new ItemHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        ItemHolder holdert = (ItemHolder) holder;
        RequestOptions ro=new RequestOptions().placeholder(R.drawable.loading);
        Glide.with(context).load(pres.get(position).getSrc()).apply(ro).into(holdert.img);
        holdert.title.setText(pres.get(position).getTitle());
        holdert.text.setText(pres.get(position).getContent().substring(0,18)+"....");
        holdert.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final PopupWindow popupWindow=new PopupWindow(context);
                LayoutInflater layoutInflater=LayoutInflater.from(context);
                View view_pop=layoutInflater.inflate(R.layout.popupwindow_layout,null);
                TextView text_pop= view_pop.findViewById(R.id.text_pop);
                text_pop.setText(pres.get(position).getContent());
                Button btn_ok=view_pop.findViewById(R.id.btn_ok);
                btn_ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                    }
                });
                popupWindow.setContentView(view_pop);
                popupWindow.showAtLocation(view, Gravity.FILL_HORIZONTAL,0,0);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(null!=pres){
            return pres.size();
        }
        return 0;
    }
    private class ItemHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private TextView text;
        private ImageView img;
        private Button btn;
        public ItemHolder(View v) {
            super(v);
            title=v.findViewById(R.id.title);
            text=v.findViewById(R.id.text);
            img=v.findViewById(R.id.img);
            btn=v.findViewById(R.id.btn);
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
