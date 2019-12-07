package org.turings.turings.index.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import org.turings.turings.index.entity.Comment2;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class RecyclerCommentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private int itemlayout;
    private List<Comment2> list;
    private EditText editText;
    private Button btn_send;

    public RecyclerCommentAdapter(Context context, int itemlayout, List<Comment2> list, EditText editText, Button btn_send) {
        this.context = context;
        this.itemlayout = itemlayout;
        this.list = list;
        this.editText = editText;
        this.btn_send = btn_send;
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
        int id=context.getResources().getIdentifier(list.get(position).getImg(),"drawable",context.getPackageName());
        holdert.img.setImageResource(id);
        holdert.tv_name.setText(list.get(position).getName());
        holdert.tv_content.setText(list.get(position).getContent());
        holdert.tv_time.setText(list.get(position).getDatetv());
        holdert.tv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                list.remove(position);
                notifyDataSetChanged();
            }
        });
        holdert.tv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(list.get(position).getName().startsWith("hello回复")||list.get(position).getName().equals("hello")){
                    Toast.makeText(context,"不能自己回复自己",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(context,"回复"+list.get(position).getName(),Toast.LENGTH_SHORT).show();
                    editText.setText("");
                    editText.setHint("回复"+list.get(position).getName());
                    btn_send.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Comment2 comment2=new Comment2();
                            comment2.setImg("frog");
                            comment2.setName("hello回复"+list.get(position).getName());
                            comment2.setContent(editText.getText().toString());
                            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                            String strDate=sdf.format(new Date());
                            comment2.setDatetv(strDate);
                            list.add(comment2);
                            notifyDataSetChanged();
                        }
                    });
                }
            }
        });
    }
    private class ItemHolder extends RecyclerView.ViewHolder {
        private ImageView img;
        private TextView tv_name;
        private TextView tv_delete;
        private TextView tv_content;
        private TextView tv_time;
        private TextView tv_back;
        public ItemHolder(View v) {
            super(v);
            img=v.findViewById(R.id.img);
            tv_name=v.findViewById(R.id.tv_name);
            tv_delete=v.findViewById(R.id.tv_delete);
            tv_content=v.findViewById(R.id.tv_content);
            tv_time=v.findViewById(R.id.tv_time);
            tv_back=v.findViewById(R.id.tv_back);
        }
    }
    @Override
    public int getItemCount() {
        if(null!=list){
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
