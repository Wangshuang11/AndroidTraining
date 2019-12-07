package org.turings.turings.index.gw.HotCourses;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.turings.turings.R;

import java.util.List;

public class HotCourseAdapter extends BaseAdapter {
    private Context mContext;
    private List<HotCourse> mCourse;
    public HotCourseAdapter(Context context,List<HotCourse> mCourse){
        this.mContext = context;
        this.mCourse = mCourse;
    }
    @Override
    public int getCount() {
        return mCourse.size();
    }

    @Override
    public Object getItem(int i) {
        return mCourse.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(convertView == null){
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.gw_hotcourse_item,null);
            viewHolder.courseImage = convertView.findViewById(R.id.courseImage);
            viewHolder.courseData = convertView.findViewById(R.id.courseData);
            viewHolder.coursePerson = convertView.findViewById(R.id.coursePerson);
            viewHolder.courseTitle = convertView.findViewById(R.id.courseTitle);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        //加载图片作为视频的显示图片
        Glide.with(mContext)
                .load(mCourse.get(position).getCourseImage())
                .into(viewHolder.courseImage);
//        viewHolder.courseImage.setImageResource(R.drawable.course2);
        viewHolder.courseTitle.setText(mCourse.get(position).getCourseTitle());
        viewHolder.coursePerson.setText(mCourse.get(position).getCoursePerson());
        viewHolder.courseData.setText(mCourse.get(position).getCourserData());
        return convertView;
    }

    class ViewHolder{
        ImageView courseImage;
        TextView courseTitle;
        TextView courseData;
        TextView coursePerson;
    }
}