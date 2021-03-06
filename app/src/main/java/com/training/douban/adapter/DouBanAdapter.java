package com.training.douban.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.training.R;
import com.training.main.model.DoubanMovieBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenqiuyi on 17/1/6.
 */

public class DouBanAdapter extends RecyclerView.Adapter<DouBanAdapter.DBHolder> {
    public interface OnItemClickListener {
        void onMovieItemClick(View v, int position);
    }

    private Context context;
    private List<DoubanMovieBean.Subject> mRpList = new ArrayList<DoubanMovieBean.Subject>();
    private OnItemClickListener mItemClickListener;

    public DouBanAdapter(Context context) {
        this.context = context;
    }

    public List<DoubanMovieBean.Subject> getRpList() {
        return mRpList;
    }

    public void setRpList(List<DoubanMovieBean.Subject> rpList) {
        if (rpList != null) {
            mRpList.clear();
            mRpList.addAll(rpList);
        }
        notifyDataSetChanged();
    }

    public void addRpList(List<DoubanMovieBean.Subject> rpList) {
        int oldLength = mRpList.size();
        if (rpList != null) {
            mRpList.addAll(rpList);
        }
        notifyItemInserted(oldLength);
    }

    public OnItemClickListener getItemClickListener() {
        return mItemClickListener;
    }

    public void setItemClickListener(OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    @Override
    public DBHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(context)
                .inflate(R.layout.activity_douban_item, parent, false);
        final DouBanAdapter.DBHolder holder = new DouBanAdapter.DBHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mItemClickListener != null) {
                    mItemClickListener.onMovieItemClick(v, holder.getAdapterPosition());
                }
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(DBHolder holder, int position) {
        DoubanMovieBean.Subject subject = mRpList.get(position);
        holder.textView.setText(subject.getTitle());
        Glide.with(context)
                .load(subject.getImages().getLarge())
                .into(holder.imageView);
        holder.origin_title.setText(subject.getOriginal_title());
        holder.year.setText(subject.getYear());
        holder.rb.setRating((float) (subject.getRating().getAverage() / 2));

        StringBuilder buffer = new StringBuilder("");
        for (String genre : subject.getGenres()) {
            buffer.append(genre);
            buffer.append(",");
        }
        buffer.deleteCharAt(buffer.length() - 1);
        holder.genre.setText(buffer.toString());
    }

    @Override
    public int getItemCount() {
        return mRpList.size();
    }

    class DBHolder extends RecyclerView.ViewHolder {
        View itemView;
        TextView textView;
        ImageView imageView;
        TextView origin_title;
        TextView genre;
        TextView year;
        RatingBar rb;

        DBHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            imageView = itemView.findViewById(R.id.img_movie);
            textView = itemView.findViewById(R.id.tv_title);
            origin_title = itemView.findViewById(R.id.tv_origin_title);
            genre = itemView.findViewById(R.id.tv_genre);
            year = itemView.findViewById(R.id.tv_year);
            rb = itemView.findViewById(R.id.rb);
        }
    }

    public int getStart() {
        return mRpList.size();
    }
}
