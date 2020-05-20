package org.turings.turings.mistaken;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import org.turings.turings.R;

import java.util.List;

public class CustomAdapterGridViewForColor extends BaseAdapter {
    Context context;//上下文
    List<Integer> list;//数据源
    private int itemLayout;//布局文件

    public CustomAdapterGridViewForColor(Context context, List<Integer> list, int itemLayout) {
        this.context = context;
        this.list = list;
        this.itemLayout = itemLayout;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if(null == convertView){
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(itemLayout,null);
            holder.button= convertView.findViewById(R.id.btn_color);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();

        }
        holder.button.setBackgroundColor(list.get(position));
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //返回位置
                mOnItemClickListener.onNumClick(position);
            }
        });
        return convertView;
    }
    final static class ViewHolder{
        private Button button;
    }
    /**
     * 点击题目按钮的监听接口
     */
    public interface onItemClickListener {
        void onNumClick(int position);
    }

    private onItemClickListener mOnItemClickListener;

    public void setmOnItemClickListener(onItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener  = mOnItemClickListener;
    }
}
