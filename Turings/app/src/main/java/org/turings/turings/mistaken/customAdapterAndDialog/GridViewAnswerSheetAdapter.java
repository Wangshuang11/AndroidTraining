package org.turings.turings.mistaken.customAdapterAndDialog;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import org.turings.turings.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GridViewAnswerSheetAdapter extends BaseAdapter {
    private Context context;//上下文
    private List<Integer> list;//数据源
    private int itemLayout;//布局文件
    private Map<Integer,Integer> choosesStated = new HashMap<>();//答题状况
    public GridViewAnswerSheetAdapter(Context context, List<Integer> list, int itemLayout, Map<Integer,Integer> map) {
        this.context = context;
        this.list = list;
        this.itemLayout = itemLayout;
        this.choosesStated = map;
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
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        final int listPosition = i;
        if(null == view){
            holder = new ViewHolder();
            view = LayoutInflater.from(context).inflate(itemLayout,null);
            holder.button = view.findViewById(R.id.btn_num);
            view.setTag(holder);
        }else {
            holder = (ViewHolder)view.getTag();

        }
        holder.button.setText(list.get(i)+1+"");
        if(choosesStated.get(list.get(i)) == 0){//没有做的题目
            holder.button.setBackgroundResource(R.drawable.ylx_answer_circle_background2);
            holder.button.setTextColor(Color.BLACK);
        }else {//做过的题目
            holder.button.setBackgroundResource(R.drawable.ylx_answer_circle_background1);
            holder.button.setTextColor(Color.WHITE);
        }
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnItemClickListener.onNumClick(listPosition);
            }
        });
        return view;
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
