package org.turings.turings.mistaken.customAdapterAndDialog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.turings.turings.R;
import org.turings.turings.mistaken.SubjectMsg;

import java.util.List;

public class CustomAdapterInCartYLX extends BaseAdapter {
    private List<SubjectMsg> subjectMsgs;//数据源
    private Context context;//上下文环境
    private int itemLayout;//布局文件
    public ViewHolder holder;
    public CustomAdapterInCartYLX(List<SubjectMsg> subjectMsgs, Context context, int itemLayout) {
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
            holder.topSub = convertView.findViewById(R.id.top_ylx);
            holder.downSub = convertView.findViewById(R.id.down_ylx);
            holder.subjectSub = convertView.findViewById(R.id.subject_ylx);
            holder.cart = convertView.findViewById(R.id.cart_ylx);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();

        }
        //获取图片id,从data的files目录下取出来
//        String dataFileStr = context.getFilesDir().getAbsolutePath()+"/"+list.get(position).getTitleImg();
//        Bitmap bitmap = BitmapFactory.decodeFile(dataFileStr);
        //添加图片
        holder.subjectSub.setText("题目"+subjectMsgs.get(position).getId());
//        holder.imageView.setImageBitmap(bitmap);
        holder.cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {//删除
                mOnItemDeleteListener.onDeleteClick(listPosition);
            }
        });
        //上移
        holder.topSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnItemTopMoveListener.onTopMoveClick(listPosition);
            }
        });
        //下移
        holder.downSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnItemDownMoveListener.onDownMoveClick(listPosition);
            }
        });
        return convertView;
    }
    public final static class ViewHolder{
        public LinearLayout topSub;//上移
        public LinearLayout downSub;//下移
        public TextView subjectSub;//题目
        public LinearLayout cart;//删除
    }
    /**
     * 删除按钮的监听接口
     */
    public interface onItemDeleteListener {
        void onDeleteClick(int position);
    }

    private onItemDeleteListener mOnItemDeleteListener;

    public void setmOnItemDeleteClickListener(onItemDeleteListener mOnItemDeleteListener) {
        this.mOnItemDeleteListener  = mOnItemDeleteListener;
    }
    /**
     * 上移按钮的监听接口
     */
    public interface onItemTopMoveListener {
        void onTopMoveClick(int position);
    }

    private onItemTopMoveListener mOnItemTopMoveListener;

    public void setmOnItemTopMoveClickListener(onItemTopMoveListener mOnItemTopMoveListener) {
        this.mOnItemTopMoveListener  = mOnItemTopMoveListener;
    }
    /**
     * 下移按钮的监听接口
     */
    public interface onItemDownMoveListener {
        void onDownMoveClick(int position);
    }

    private onItemDownMoveListener mOnItemDownMoveListener;

    public void setmOnItemDownMoveClickListener(onItemDownMoveListener mOnItemDownMoveListener) {
        this.mOnItemDownMoveListener  = mOnItemDownMoveListener;
    }

}
