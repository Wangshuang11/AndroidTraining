package org.turings.turings.index.adapter;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.zhuang.likeviewlibrary.LikeView;

import org.turings.turings.R;
import org.turings.turings.index.entity.Comment2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;


public class RecyclerCommentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private int itemlayout;
    private List<Comment2> list;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 100:
                    String string = (String) msg.obj;
                    if (string.startsWith("lph_one")) {
                        Toast.makeText(context, "评论收藏成功", Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }
    };

    public RecyclerCommentAdapter(Context context, int itemlayout, List<Comment2> list) {
        this.context = context;
        this.itemlayout = itemlayout;
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = null;
        RecyclerView.ViewHolder holder = null;
        v = LayoutInflater.from(context).inflate(itemlayout, parent, false);
        holder = new ItemHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        ItemHolder holdert = (ItemHolder) holder;
//        int icon=context.getResources().getIdentifier(list.get(position).getIcon(),"drawable",context.getPackageName());
////        holdert.img.setImageResource(icon);
        RequestOptions ro = new RequestOptions().placeholder(R.drawable.loading);
        Glide.with(context).load(list.get(position).getIcon()).apply(ro).into(holdert.img);
        holdert.tv_name.setText(list.get(position).getName());
        int iv_dengji = context.getResources().getIdentifier(list.get(position).getDjIcon(), "drawable", context.getPackageName());
        holdert.iv_dengji.setImageResource(iv_dengji);
        holdert.tv_age.setText(list.get(position).getDjName());
        holdert.btn_good.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int num = Integer.valueOf(list.get(position).getNum());
                num++;
                list.get(position).setNum(num + "");
                holdert.tv_goodnum.setText(num+"");
                sendToServer(num + "", list.get(position).getId());
            }
        });
        holdert.tv_content.setText(list.get(position).getContent());
        holdert.tv_time.setText(list.get(position).getTime());
        holdert.tv_goodnum.setText(list.get(position).getNum());
    }

    private void sendToServer(final String starnum, final int id) {
        new Thread() {
            @Override
            public void run() {
                try {
                    String string = "http://" + context.getResources().getString(R.string.lphipConfig) + ":8080/Turings/lph/updateComment?starnum=" + starnum + "&id=" + id ;
                    URL url = new URL(string);
                    URLConnection conn = url.openConnection();
                    InputStream in = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in, "utf-8"));
                    String info = reader.readLine();
                    Message msg = Message.obtain();
                    msg.obj = info;
                    msg.what = 100;
                    handler.sendMessage(msg);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    private class ItemHolder extends RecyclerView.ViewHolder {
        private ImageView img;
        private TextView tv_name;
        private ImageView iv_dengji;
        private TextView tv_age;
        private Button btn_good;
        private TextView tv_content;
        private TextView tv_time;
        private TextView tv_goodnum;
        public ItemHolder(View v) {
            super(v);
            img = v.findViewById(R.id.img);
            tv_name = v.findViewById(R.id.tv_name);
            iv_dengji = v.findViewById(R.id.iv_dengji);
            tv_age = v.findViewById(R.id.tv_age);
            btn_good = v.findViewById(R.id.btn_good);
            tv_content = v.findViewById(R.id.tv_content);
            tv_time = v.findViewById(R.id.tv_time);
            tv_goodnum=v.findViewById(R.id.tv_goodnum);
        }
    }

    @Override
    public int getItemCount() {
        if (null != list) {
            return list.size();
        }
        return 0;
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
