package com.elhefny.askdoctor.LastNews;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.elhefny.askdoctor.R;
import com.google.android.material.textview.MaterialTextView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class LastNewsAdapter extends RecyclerView.Adapter<LastNewsAdapter.newsViewHolder> {
    Context context;
    ArrayList<Article> news;

    public LastNewsAdapter(Context context, ArrayList<Article> news) {
        this.context = context;
        this.news = news;
    }

    @NonNull
    @Override
    public newsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new newsViewHolder(LayoutInflater.from(context).inflate(R.layout.recycler_new_news_design, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull newsViewHolder holder, int position) {
        holder.itemView.setAnimation(AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left));
        Article article = news.get(position);
        holder.title.setText(article.getTitle());
        holder.description.setText(article.getDescription());
        Picasso.get()
                .load(article.getUrlToImage())
                .placeholder(R.drawable.ic_news)
                .fit()
                .centerCrop()
                .into(holder.iv);
    }

    @Override
    public int getItemCount() {
        return news.size() == 0 ? 0 : news.size();
    }

    public class newsViewHolder extends RecyclerView.ViewHolder {
        MaterialTextView title, description;
        ImageView iv;

        public newsViewHolder(@NonNull View itemView) {
            super(itemView);
            iv = itemView.findViewById(R.id.recycler_new_news_design_iv);
            title = itemView.findViewById(R.id.recycler_new_news_design_tv);
            description = itemView.findViewById(R.id.recycler_new_news_design_tv2);
        }
    }
}
