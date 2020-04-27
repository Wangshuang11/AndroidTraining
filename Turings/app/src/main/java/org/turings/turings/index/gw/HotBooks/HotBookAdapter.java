package org.turings.turings.index.gw.HotBooks;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.turings.turings.R;

import java.util.List;

public class HotBookAdapter  extends RecyclerView.Adapter<HotBookAdapter.ViewHolder> {

        private List<HotBook> mBookList;

        static class ViewHolder extends RecyclerView.ViewHolder {
            ImageView bookImage;
            TextView bookName;

            public ViewHolder(View view) {
                super(view);
                bookImage = (ImageView) view.findViewById(R.id.bookImage);
                bookName = (TextView) view.findViewById(R.id.bookName);
            }

        }

        public HotBookAdapter(List<HotBook> bookList) {
            mBookList = bookList;
        }

        @Override

        public HotBookAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.gw_hotbook_item, parent, false);
            final HotBookAdapter.ViewHolder holder = new HotBookAdapter.ViewHolder(view);
            holder.bookImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = holder.getAdapterPosition();
                    HotBook book = mBookList.get(position);
                    Toast.makeText(view.getContext(), "you clicked image" + book.getName(), Toast.LENGTH_SHORT).show();
                }
            });


            return holder;
        }

        @Override
        public void onBindViewHolder(HotBookAdapter.ViewHolder holder, int position) {

            HotBook fruit = mBookList.get(position);
            holder.bookImage.setImageResource(fruit.getImageId());
            holder.bookName.setText(fruit.getName());
        }

        @Override
        public int getItemCount() {
            return mBookList.size();
        }
    }