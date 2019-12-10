package org.turings.turings.myself.tools;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import org.turings.turings.myself.entity.Fan;

import java.util.ArrayList;
import java.util.List;

public class FanAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private int img;
    private int name;
    private int motto;
    private List<Fan> urls = new ArrayList<>();

    public FanAdapter(Context context, List<Fan> urls, int layout,int img,int name,int motto) {
        this.context = context;
        this.urls = urls;
        this.layout=layout;
        this.img=img;
        this.name=name;
        this.motto=motto;
    }

    public void setUrls(List<Fan> urls) {
        this.urls = urls;
    }

    @Override
    public int getCount() {
        return urls.size();
    }

    @Override
    public Fan getItem(int i) {
        return urls.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder vh = null;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(layout, null);
            vh = new ViewHolder();
            vh.imageView =view.findViewById(img);
            vh.title=view.findViewById(name);
            vh.flag=view.findViewById(motto);
            view.setTag(vh);
        }
        vh = (ViewHolder) view.getTag();
        if (urls != null && urls.size() > 0) {
            RequestOptions requestOptions=new RequestOptions().circleCrop();
            Glide.with(context).load(urls.get(i).getAvatar()).apply(requestOptions).into(vh.imageView);
            vh.title.setText(urls.get(i).getName());
            vh.flag.setText(urls.get(i).getMotto());
        }
        return view;
    }

    class ViewHolder {
        ImageView imageView;
        TextView title;
        TextView flag;
    }
}
