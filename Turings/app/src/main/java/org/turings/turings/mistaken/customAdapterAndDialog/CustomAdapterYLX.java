package org.turings.turings.mistaken.customAdapterAndDialog;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import org.turings.turings.R;
import org.turings.turings.mistaken.entity.SubjectMsg;

import java.text.SimpleDateFormat;
import java.util.List;

public class CustomAdapterYLX extends BaseAdapter {
    private List<SubjectMsg> list;//数据源
    private Context context;//上下文环境
    private int itemLayout;//布局文件
    public ViewHolder holder;
    private boolean isShowCheckBox = false;//表示当前是否是多选状态。
    private SparseBooleanArray stateCheckedMap = new SparseBooleanArray();//用来存放CheckBox的选中状态，true为选中,false为没有选中

    public CustomAdapterYLX(List<SubjectMsg> list, Context context, int itemLayout, SparseBooleanArray stateCheckedMap) {
        this.list = list;
        this.context = context;
        this.itemLayout = itemLayout;
        this.stateCheckedMap = stateCheckedMap;
    }

    @Override
    public int getCount() {
        if(list != null){
            return list.size();
        }else {
            return 0;
        }
    }

    @Override
    public Object getItem(int position) {
        if(list != null){
            return list.get(position);
        }else {
            return null;
        }
    }

    @Override
    public long getItemId(int position) {
        if(list != null){
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
            holder.time = convertView.findViewById(R.id.time_ylx);
            holder.type = convertView.findViewById(R.id.tag_ylx);
            holder.tvImg = convertView.findViewById(R.id.subject_img_ylx);
            holder.tvSub=convertView.findViewById(R.id.subject_tv_ylx);
            holder.check = convertView.findViewById(R.id.chb_select_way_point);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();

        }
        showAndHideCheckBox();//控制CheckBox的那个的框显示与隐藏
        //添加时间
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
        String str = sdf.format(list.get(position).getTime());
        holder.time.setText(str);
        //添加题型
        holder.type.setText(list.get(position).getType().toString());
        //获取图片id,从data的files目录下取出来
//        if(list.get(position).getTitleImg()!=null){//题目用图片
//            String dataFileStr = list.get(position).getTitleImg();
//            Bitmap bitmap = BitmapFactory.decodeFile(dataFileStr);
//            //添加图片
////            holder.tvImg.setImageResource(R.mipmap.aylx);
//            holder.tvImg.setImageBitmap(bitmap);
//            holder.tvImg.setVisibility(View.VISIBLE);
//            holder.tvSub.setVisibility(View.GONE);
//        }else {//题目用文字的题目
        if(list.get(position).getType().equals("选择题")){
            holder.tvSub.setText(list.get(position).getContent()+"\n"+list.get(position).getOptionA()
                    +"\n"+list.get(position).getOptionB()+"\n"
                    +list.get(position).getOptionC()+"\n"
                    +list.get(position).getOptionD());
        }else {
            holder.tvSub.setText(list.get(position).getContent());
        }
        holder.tvImg.setVisibility(View.GONE);
        holder.tvSub.setVisibility(View.VISIBLE);
//        }
        //判断是否被选中
        holder.check.setChecked(stateCheckedMap.get(position));//设置CheckBox是否选中
        return convertView;
    }

    public final static class ViewHolder{
        public TextView time;
        public TextView type;
        public ImageView tvImg;
        public TextView tvSub;
        public CheckBox check;
    }
    private void showAndHideCheckBox() {
        if (isShowCheckBox) {
            holder.check.setVisibility(View.VISIBLE);
        } else {
            holder.check.setVisibility(View.GONE);
        }
    }


    public boolean isShowCheckBox() {
        return isShowCheckBox;
    }

    public void setShowCheckBox(boolean showCheckBox) {
        isShowCheckBox = showCheckBox;
    }
}
