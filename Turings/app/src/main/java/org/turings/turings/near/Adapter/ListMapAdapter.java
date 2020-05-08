package org.turings.turings.near.Adapter;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.load.engine.Resource;

import org.turings.turings.R;
import org.turings.turings.near.entity.RoundImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ListMapAdapter extends BaseAdapter {

    private List<Map<String,String>> list = new ArrayList<>();
    private Context context;
    private int itemLayoutId;
    private Resources resources;

    public ListMapAdapter(Context context,List<Map<String,String>> list, int itemLayoutId,Resources resources) {
        this.list = list;
        this.itemLayoutId = itemLayoutId;
        this.context = context;
        this.resources = resources;
    }
    //无参的构造方法
    public ListMapAdapter(){

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
        RoundImageView title = convertView.findViewById(R.id.ivPortrait_lyh);
        TextView content = convertView.findViewById(R.id.tvFollowName_lyh);
        int id = resources.getIdentifier(list.get(position).get("portrait"), "mipmap", context.getPackageName());
//        title.setImageResource(list.get(position).get("portrait"));
        title.setImageResource(id);
        content.setText(list.get(position).get("name"));

        return convertView;
    }
}
