package org.turings.turings.index.gw.MainSubjects;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.turings.turings.R;

import java.util.List;

public class SubjectAdapter extends RecyclerView.Adapter<SubjectAdapter.ViewHolder> {

    private List<Subject> mSubjectList;

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView subjectImage;
        TextView subjectName;

        public ViewHolder(View view) {
            super(view);
            subjectImage = (ImageView) view.findViewById(R.id.subjectImage);
            subjectName = (TextView) view.findViewById(R.id.subjectName);
        }

    }

    public SubjectAdapter(List<Subject> subjects) {
        mSubjectList = subjects;
    }

    @Override

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.gw_subject_item, parent, false);
        final  ViewHolder holder = new ViewHolder(view);
        holder.subjectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //点击跳转
                Intent intent = new Intent(view.getContext(), HotSubjectVideo.class);
                view.getContext().startActivity(intent);
            }
        });


        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Subject subject = mSubjectList.get(position);
        holder.subjectImage.setImageResource(subject.getImageId());
        holder.subjectName.setText(subject.getName());
    }

    @Override
    public int getItemCount() {
        return mSubjectList.size();
    }
}