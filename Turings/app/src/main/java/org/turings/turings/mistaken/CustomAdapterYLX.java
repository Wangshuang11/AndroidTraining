package org.turings.turings.mistaken;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.turings.turings.R;

import java.text.SimpleDateFormat;
import java.util.List;
import org.turings.turings.mistaken.SubjectMsg;

public class CustomAdapterYLX extends BaseAdapter {
    private List<SubjectMsg> list;//数据源
    private Context context;//上下文环境
    private int itemLayout;//布局文件

    public CustomAdapterYLX(List<SubjectMsg> list, Context context, int itemLayout) {
        this.list = list;
        this.context = context;
        this.itemLayout = itemLayout;
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
        ViewHolder holder = null;
        if(null == convertView){
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(itemLayout,null);
            holder.time = convertView.findViewById(R.id.time_ylx);
            holder.type = convertView.findViewById(R.id.tag_ylx);
            holder.imageView = convertView.findViewById(R.id.subject_ylx);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();

        }
        //添加时间
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
        String str = sdf.format(list.get(position).getTime());
        holder.time.setText(str);
        //添加题型
        holder.type.setText(list.get(position).getType().toString());
        //获取图片id,从data的files目录下取出来
        String dataFileStr = context.getFilesDir().getAbsolutePath()+"/"+list.get(position).getTitleImg();
        Bitmap bitmap = BitmapFactory.decodeFile(dataFileStr);
        //添加图片
        holder.imageView.setImageBitmap(bitmap);
        return convertView;
    }

    final static class ViewHolder{
        private TextView time;
        private TextView type;
        private ImageView imageView;
    }

}
