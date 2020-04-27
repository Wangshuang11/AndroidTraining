package org.turings.turings.near.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.turings.turings.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AdepterDemo extends BaseAdapter {
    private List<Map<String,String>> list = new ArrayList<>();
    private Context context;
    private int itemLayoutId;

    public AdepterDemo(Context context,List<Map<String,String>> list, int itemLayoutId) {
        this.list = list;
        this.itemLayoutId = itemLayoutId;
        this.context = context;
    }
    //无参的构造方法
    public AdepterDemo(){

    }

    @Override
    public int getCount() {//得到数据的条数
        return list.size();
    }

    @Override
    public Object getItem(int position) {//每一项要显示的数据：循环调用
        if(null != list){
            return list.get(position);
        }else{
            return null;
        }
    }

    @Override
    public long getItemId(int position) {//导致了id和position一样：循环调用
        return position;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //加载进item的布局文件，是最重要的方法:循环调用
        //手动加载item 对应的布局文件
        LayoutInflater inflater = LayoutInflater.from(context);
        convertView = inflater.inflate(itemLayoutId,null);   //第二个参数是父布局,不能传parent,

        //获取每一个item中各种视图控件的对象
        TextView title = convertView.findViewById(R.id.tvTitle);
        TextView content = convertView.findViewById(R.id.tvContent);
        LinearLayout llBack = convertView.findViewById(R.id.llBack);
        llBack.setBackgroundColor(Color.argb(20,255,255,255));
        title.setText(list.get(position).get("title"));
        content.setText(list.get(position).get("content"));

        return convertView;
    }
}
