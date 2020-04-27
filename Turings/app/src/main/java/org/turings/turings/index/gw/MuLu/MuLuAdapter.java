package org.turings.turings.index.gw.MuLu;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.turings.turings.R;
import org.turings.turings.index.gw.EndVideo.EndCourse;

import java.util.List;

public class MuLuAdapter  extends RecyclerView.Adapter<MuLuAdapter.ViewHolder> {

    private List<Mulu> mBookList;

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView bookImage;
        TextView bookName;

        public ViewHolder(View view) {
            super(view);
            bookImage = (ImageView) view.findViewById(R.id.muluimage);
            bookName = (TextView) view.findViewById(R.id.muluname);
        }

    }

    public MuLuAdapter(List<Mulu> bookList) {
        mBookList = bookList;
    }

    @Override

    public MuLuAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.gw_muluvideo_item, parent, false);
        final MuLuAdapter.ViewHolder holder = new MuLuAdapter.ViewHolder(view);
        holder.bookImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                Mulu book = mBookList.get(position);
                Toast.makeText(view.getContext(), "正在播放" + book.getName(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(view.getContext(), EndCourse.class);
                view.getContext().startActivity(intent);

            }
        });


        return holder;
    }

    @Override
    public void onBindViewHolder(MuLuAdapter.ViewHolder holder, int position) {

        Mulu fruit = mBookList.get(position);
        holder.bookImage.setImageResource(R.drawable.videoshow);
        holder.bookName.setText(fruit.getName());
    }

    @Override
    public int getItemCount() {
        return mBookList.size();
    }
}