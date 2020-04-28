package org.turings.turings.mistaken.customAdapterAndDialog;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.turings.turings.R;
import org.turings.turings.mistaken.entity.SubjectMsg;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class CustomAdapterToAddCartYLX extends BaseAdapter {
    private List<SubjectMsg> subjectMsgs;//数据源
    private Context context;//上下文环境
    private int itemLayout;//布局文件
    public ViewHolder holder;
    public static int COUNT_IN_CART = 0;//数量
    private List<Integer> selectedSub = new ArrayList<>();//已选题目样式
    public CustomAdapterToAddCartYLX(List<SubjectMsg> subjectMsgs, Context context, int itemLayout) {
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
            holder.timeSub = convertView.findViewById(R.id.time_ylx);
            holder.typeSub = convertView.findViewById(R.id.tag_ylx);
            holder.subjectSub = convertView.findViewById(R.id.subject_ylx);
            holder.subjectImgSub=convertView.findViewById(R.id.subject_img_ylx);
            holder.cart = convertView.findViewById(R.id.cart_ylx);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();

        }
        if (selectedSub.contains(position)){
            holder.cart.setBackgroundColor(Color.parseColor("#e5e5e5"));
        }else{
            holder.cart.setBackgroundColor(Color.parseColor("#1AA8D7"));
        }
        //添加时间
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
        String str = sdf.format(subjectMsgs.get(position).getTime());
        holder.timeSub.setText(str);
        //添加题型
        holder.typeSub.setText(subjectMsgs.get(position).getType().toString());
        //获取图片id,从data的files目录下取出来
        //添加图片
        Log.i("www", "getView: "+subjectMsgs.get(position).getContent());
//        if(subjectMsgs.get(position).getTitleImg()==null) {
        if(subjectMsgs.get(position).getType().equals("选择题")){
            holder.subjectSub.setText(subjectMsgs.get(position).getContent()+"\n"+subjectMsgs.get(position).getOptionA()
                    +"\n"+subjectMsgs.get(position).getOptionB()+"\n"
                    +subjectMsgs.get(position).getOptionC()+"\n"
                    +subjectMsgs.get(position).getOptionD());
        }else {
            holder.subjectSub.setText(subjectMsgs.get(position).getContent());
        }
        holder.subjectImgSub.setVisibility(View.GONE);
        holder.subjectSub.setVisibility(View.VISIBLE);
//        }else {
//            String dataFileStr = context.getFilesDir().getAbsolutePath()+"/"+subjectMsgs.get(position).getContent();
//            Bitmap bitmap = BitmapFactory.decodeFile(dataFileStr);
//            //添加图片
////            holder.tvImg.setImageResource(R.mipmap.aylx);
//            holder.subjectImgSub.setImageBitmap(bitmap);
//            holder.subjectImgSub.setVisibility(View.VISIBLE);
//            holder.subjectSub.setVisibility(View.GONE);
//        }
//        holder.subjectSub.setText("题目"+position);
//        holder.imageView.setImageBitmap(bitmap);
        holder.cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {//点击加入错题篮
                Log.i("www", "onClick:点击加车子 ");
                //改变样式
                if(!selectedSub.contains(listPosition)){
                    COUNT_IN_CART = COUNT_IN_CART+1;
                    mOnItemAddListener.onAddClick(COUNT_IN_CART,listPosition);
                    selectedSub.add(listPosition);
                }else {
                    mOnItemAddListener.onAddClick(COUNT_IN_CART,listPosition);
                }
                Log.i("www", "onClick:加入篮子的应该变画背景的位置 "+selectedSub.toString());
                notifyDataSetChanged();
            }
        });
        return convertView;
    }
    public final static class ViewHolder{
        public TextView timeSub;//时间
        public TextView typeSub;//类型
        public TextView subjectSub;//题目
        public ImageView subjectImgSub;//题目图片
        public RelativeLayout cart;//添加到错题篮
    }
    /**
     * 添加按钮的监听接口
     */
    public interface onItemAddListener {
        void onAddClick(int number, int position);
    }

    private onItemAddListener mOnItemAddListener;

    public void setOnItemAddClickListener(onItemAddListener mOnItemAddListener) {
        this.mOnItemAddListener  = mOnItemAddListener;
    }

}
