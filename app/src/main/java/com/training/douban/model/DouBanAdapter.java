package com.training.douban.model;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.training.R;
import com.training.network.model.RpDBM250;

import java.util.List;

/**
 * Created by chenqiuyi on 17/1/6.
 */

public class DouBanAdapter extends RecyclerView.Adapter<DouBanAdapter.DBHolder> {
    public interface DoWhat {
        void onMovieItemClick(View v, int position);
    }

    private Context context;
    private List<RpDBM250.Subject> rpList;
    private DoWhat doWhat;

    public DouBanAdapter(Context context, List<RpDBM250.Subject> rpList, DoWhat doWhat) {
        this.context = context;
        this.rpList = rpList;
        this.doWhat = doWhat;
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
                doWhat.onMovieItemClick(v, holder.getAdapterPosition());
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(DBHolder holder, int position) {
        RpDBM250.Subject subject = rpList.get(position);
        holder.textView.setText(subject.getTitle());
        Picasso.with(context)
                .load(subject.getImages().getLarge())
                .into(holder.imageView);
        holder.origin_title.setText(subject.getOriginal_title());
        holder.year.setText(subject.getYear());
        holder.rb.setRating((float) (subject.getRating().getAverage() / 2));

        StringBuffer buffer = new StringBuffer("");
        for (String genre : subject.getGenres()) {
            buffer.append(genre);
            buffer.append(",");
        }
        buffer.deleteCharAt(buffer.length() - 1);
        holder.genre.setText(buffer.toString());
    }

    @Override
    public int getItemCount() {
        return rpList.size();
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
            imageView = (ImageView) itemView.findViewById(R.id.img_movie);
            textView = (TextView) itemView.findViewById(R.id.tv_title);
            origin_title = (TextView) itemView.findViewById(R.id.tv_origin_title);
            genre = (TextView) itemView.findViewById(R.id.tv_genre);
            year = (TextView) itemView.findViewById(R.id.tv_year);
            rb = (RatingBar) itemView.findViewById(R.id.rb);
        }
    }
}
