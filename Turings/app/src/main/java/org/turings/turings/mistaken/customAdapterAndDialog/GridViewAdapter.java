package org.turings.turings.mistaken.customAdapterAndDialog;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.turings.turings.R;

import java.util.List;

public class GridViewAdapter extends BaseAdapter {
    Context context;//上下文
    List<String> list;//数据源
    private int itemLayout;//布局文件
    private int selectedPosition = 0;
    public GridViewAdapter(Context _context, List<String> _list,int itemLayout ) {
        this.list = _list;
        this.context = _context;
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
    //更换配选中的位置
    public void clearSelection(int position) {
        this.selectedPosition = position;
    }
    @SuppressLint("ResourceAsColor")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if(null == convertView){
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(itemLayout,null);
            holder.tvTag = convertView.findViewById(R.id.left_one_text_ylx);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();

        }
        holder.tvTag.setText(list.get(position));
        if(selectedPosition == position){//被选中标签背景色改为蓝色
            holder.tvTag.setBackgroundResource(R.drawable.subject_btn2_background);
            holder.tvTag.setTextColor(Color.parseColor("#1AA8D7"));
        }else {
            holder.tvTag.setBackgroundResource(R.drawable.subject_btn_background);
            holder.tvTag.setTextColor(Color.parseColor("#A9A9A9"));
        }
        return convertView;
    }
    final static class ViewHolder{
        private TextView tvTag;
    }
}
