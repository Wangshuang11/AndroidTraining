package org.turings.turings.mistaken.customAdapterAndDialog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.turings.turings.R;
import org.turings.turings.mistaken.SubjectMsg;

import java.util.List;

public class CustomAdapterInPagerYLX extends BaseAdapter {
    private List<SubjectMsg> subjectMsgs;//数据源
    private Context context;//上下文环境
    private int itemLayout;//布局文件
    public ViewHolder holder;
    public CustomAdapterInPagerYLX(List<SubjectMsg> subjectMsgs, Context context, int itemLayout) {
        this.subjectMsgs = subjectMsgs;
        this.context = context;
        this.itemLayout = itemLayout;
    }

    @Override
    public int getCount() {
        if(subjectMsgs != null){
            return subjectMsgs.size();
        }else {
            return 0;
        }
    }

    @Override
    public Object getItem(int position) {
        if(subjectMsgs != null){
            return subjectMsgs.get(position);
        }else {
            return null;
        }
    }

    @Override
    public long getItemId(int position) {
        if(subjectMsgs != null){
            return position;
        }else {
            return 0;
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //Item的位置
        final int listPosition = position;
        if(null == convertView){
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(itemLayout,null);
            holder.subNum = convertView.findViewById(R.id.subNum);
            holder.subjectSub = convertView.findViewById(R.id.subject_ylx);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();

        }
        //获取图片id,从data的files目录下取出来
//        String dataFileStr = context.getFilesDir().getAbsolutePath()+"/"+list.get(position).getTitleImg();
//        Bitmap bitmap = BitmapFactory.decodeFile(dataFileStr);
        //添加图片
        holder.subNum.setText(position+1+".");
        holder.subjectSub.setText("题目"+subjectMsgs.get(position).getId());
//        holder.imageView.setImageBitmap(bitmap);
        return convertView;
    }
    public final static class ViewHolder{
        public TextView subjectSub;//题目
        public TextView subNum;//题目标号
    }

}
