package org.turings.turings.mistaken.customAdapterAndDialog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.turings.turings.R;
import org.turings.turings.mistaken.entity.SubjectMsg;

import java.util.HashMap;
import java.util.Map;

public class CustomAdapterTestContentYLX extends BaseAdapter {
    private SubjectMsg subjectMsg;//数据源
    private Context context;//上下文环境
    private int itemLayout;//布局文件
    private static Map<Integer,Integer> selectedPosition = new HashMap<>();//被选中位置
    public CustomAdapterTestContentYLX(SubjectMsg subjectMsgs, Context context, int itemLayout) {
        this.subjectMsg = subjectMsgs;
        this.context = context;
        this.itemLayout = itemLayout;
    }

    @Override
    public int getCount() {
        if(subjectMsg != null){
            return 4;
        }else {
            return 0;
        }
    }

    @Override
    public Object getItem(int position) {
        if(subjectMsg != null){
            if(position == 0){
                return subjectMsg.getOptionA();
            }else if(position == 1){
                return subjectMsg.getOptionB();
            }else if(position == 2){
                return subjectMsg.getOptionC();
            }else {
                return subjectMsg.getOptionD();
            }
        }else {
            return null;
        }
    }

    @Override
    public long getItemId(int position) {
        if(subjectMsg != null){
            return position;
        }else {
            return 0;
        }
    }
    //更换配选中的位置
    public void clearSelection(Integer id,Integer position) {
        if(selectedPosition.containsKey(id)){
            selectedPosition.remove(id);
            this.selectedPosition.put(id,position);
        }else {
            this.selectedPosition.put(id,position);
        }
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        //Item的位置
        final int listPosition = i;
        ViewHolder holder = null;
        if(null == view){
            holder = new ViewHolder();
            view = LayoutInflater.from(context).inflate(itemLayout,null);
            holder.ivAnswer=view.findViewById(R.id.iv_answer);
            holder.tvAnswer=view.findViewById(R.id.tv_answer);
            view.setTag(holder);
        }else {
            holder = (ViewHolder) view.getTag();
        }

        if(selectedPosition.containsKey(subjectMsg.getId())){
            switch (i){
                case 0:
                    if(selectedPosition.get(subjectMsg.getId()) == 1){
                        holder.ivAnswer.setImageResource(R.mipmap.xuanxiangylxa2);
                        holder.tvAnswer.setText(subjectMsg.getOptionA());
                    }else {
                        holder.ivAnswer.setImageResource(R.mipmap.xuanxiangylxa1);
                        holder.tvAnswer.setText(subjectMsg.getOptionA());
                    }
                    break;
                case 1:
                    if(selectedPosition.get(subjectMsg.getId()) == 2){
                        holder.ivAnswer.setImageResource(R.mipmap.xuanxiangylxb2);
                        holder.tvAnswer.setText(subjectMsg.getOptionB());
                    }else {
                        holder.ivAnswer.setImageResource(R.mipmap.xuanxiangylxb1);
                        holder.tvAnswer.setText(subjectMsg.getOptionB());
                    }
                    break;
                case 2:
                    if(selectedPosition.get(subjectMsg.getId()) == 3){
                        holder.ivAnswer.setImageResource(R.mipmap.xuanxiangylxc2);
                        holder.tvAnswer.setText(subjectMsg.getOptionC());
                    }else {
                        holder.ivAnswer.setImageResource(R.mipmap.xuanxiangylxc1);
                        holder.tvAnswer.setText(subjectMsg.getOptionC());
                    }
                    break;
                case 3:
                    if(selectedPosition.get(subjectMsg.getId()) == 4){
                        holder.ivAnswer.setImageResource(R.mipmap.xuanxiangylxd2);
                        holder.tvAnswer.setText(subjectMsg.getOptionD());
                    }else {
                        holder.ivAnswer.setImageResource(R.mipmap.xuanxiangylxd1);
                        holder.tvAnswer.setText(subjectMsg.getOptionD());
                    }
                    break;
            }
        }else {
            switch (i) {
                case 0:
                    holder.ivAnswer.setImageResource(R.mipmap.xuanxiangylxa1);
                    holder.tvAnswer.setText(subjectMsg.getOptionA());
                    break;
                case 1:
                    holder.ivAnswer.setImageResource(R.mipmap.xuanxiangylxb1);
                    holder.tvAnswer.setText(subjectMsg.getOptionB());
                    break;
                case 2:
                    holder.ivAnswer.setImageResource(R.mipmap.xuanxiangylxc1);
                    holder.tvAnswer.setText(subjectMsg.getOptionC());
                    break;
                case 3:
                    holder.ivAnswer.setImageResource(R.mipmap.xuanxiangylxd1);
                    holder.tvAnswer.setText(subjectMsg.getOptionD());
                    break;
            }
        }
        return view;
    }
    static class ViewHolder{
        public TextView tvAnswer;//选项
        public ImageView ivAnswer;//选项图片
    }
}
