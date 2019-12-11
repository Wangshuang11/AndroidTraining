package org.turings.myself.tools;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.turings.myself.R;
import org.turings.myself.entity.Course;

import java.util.ArrayList;
import java.util.List;

public class CourseAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private List<Course> urls = new ArrayList<>();

    public CourseAdapter(Context context, List<Course> urls, int layout) {
        this.context = context;
        this.urls = urls;
        this.layout=layout;
    }

    public void setUrls(List<Course> urls) {
        this.urls = urls;
    }

    @Override
    public int getCount() {
        return urls.size();
    }

    @Override
    public Course getItem(int i) {
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
            vh.imageView =view.findViewById(R.id.sxn_course_img);
            vh.title=view.findViewById(R.id.sxn_course_title);
            vh.flag=view.findViewById(R.id.sxn_course_info);
            view.setTag(vh);
        }
        vh = (ViewHolder) view.getTag();
        if (urls != null && urls.size() > 0) {
            Glide.with(context).load(urls.get(i).getImage()).centerCrop().into(vh.imageView);
            vh.title.setText(urls.get(i).getTitle());
            vh.flag.setText(urls.get(i).getData());
        }
        return view;
    }

    class ViewHolder {
        ImageView imageView;
        TextView title;
        TextView flag;
    }
}