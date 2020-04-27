package org.turings.turings.mistaken.customAdapterAndDialog;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import org.turings.turings.R;

import java.util.ArrayList;
import java.util.List;

public class GridViewTagAdapter extends BaseAdapter {
    Context context;//上下文
    List<String> list;//数据源
    private int itemLayout;//布局文件
    private List<Integer> selectedPosition = new ArrayList<>();
    public GridViewTagAdapter(Context _context, List<String> _list,int itemLayout ) {
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
    public void clearSelection(List<Integer> selectedPosition) {
        this.selectedPosition.clear();
        for(Integer id:selectedPosition){
            this.selectedPosition.add(id);
            Log.i("www", "clearSelection: "+id);
        }
    }
    @SuppressLint("ResourceAsColor")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if(null == convertView){
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(itemLayout,null);
            holder.button= convertView.findViewById(R.id.btn_math_ylx);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();

        }
        if(selectedPosition.size() != 0){
            if(selectedPosition.contains(0)){//选择了全部
                if (position == 0){//全部
                    holder.button.setBackground(convertView.getResources().getDrawable(R.drawable.ylx_check_ifinfo_ok_layput));
                    holder.button.setTextColor(Color.WHITE);
                }else {//其他
                    holder.button.setBackground(convertView.getResources().getDrawable(R.drawable.ylx_check_ifinfo_no_layput));
                    holder.button.setTextColor(Color.BLACK);
                }
            }else{//没有选择全部
                if(position == 0){
                    holder.button.setBackground(convertView.getResources().getDrawable(R.drawable.ylx_check_ifinfo_no_layput));
                    holder.button.setTextColor(Color.BLACK);
                }
                if(position != 0 && selectedPosition.contains(position)){
                    holder.button.setBackground(convertView.getResources().getDrawable(R.drawable.ylx_check_ifinfo_ok_layput));
                    holder.button.setTextColor(Color.WHITE);
                }else {
                    holder.button.setBackground(convertView.getResources().getDrawable(R.drawable.ylx_check_ifinfo_no_layput));
                    holder.button.setTextColor(Color.BLACK);
                }
            }
        }else{
            //默认值
            if(position==0){
                holder.button.setBackground(convertView.getResources().getDrawable(R.drawable.ylx_check_ifinfo_ok_layput));
                holder.button.setTextColor(Color.WHITE);
            }else {
                holder.button.setBackground(convertView.getResources().getDrawable(R.drawable.ylx_check_ifinfo_no_layput));
                holder.button.setTextColor(Color.BLACK);
            }
        }
        holder.button.setText(list.get(position));
        return convertView;
    }
    final static class ViewHolder{
        private Button button;
    }
}
