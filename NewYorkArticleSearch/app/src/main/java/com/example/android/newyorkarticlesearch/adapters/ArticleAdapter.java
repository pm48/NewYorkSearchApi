package com.example.android.newyorkarticlesearch.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.android.newyorkarticlesearch.Activities.ArticleActivity;
import com.example.android.newyorkarticlesearch.R;
import com.example.android.newyorkarticlesearch.models.Article;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by prernamanaktala on 9/22/17.
 */

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ViewHolder> {

    public static final String EXTRA_PARAMS = "URL";
    // Store a member variable for the articles
    private  List<Article> mArticles ;
    private Context mContext;

    public ArticleAdapter(Context context,ArrayList<Article> articles) {
        mContext = context;
        mArticles = articles;
    }



    @Override
    public ArticleAdapter.ViewHolder onCreateViewHolder (ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View articleView = inflater.inflate(R.layout.item_article_result, parent, false);

        ViewHolder viewHolder = new ViewHolder(articleView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ArticleAdapter.ViewHolder holder, int position) {
        Article article = mArticles.get(position);

        TextView textView = holder.tvTitle;
        ImageView imgView = holder.imageView;
        textView.setText(article.getHeadline());
        String thumbnail = article.getThumbNail();
        if(!thumbnail.isEmpty()) {
            Glide.with(mContext).load(thumbnail).into(imgView);
        }

    }

    @Override
    public int getItemCount() {
        return mArticles.size();
    }

    // Easy access to the context object in the recyclerview
    private Context getContext() {
        return mContext;
    }
    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public  class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView tvTitle;
        public ImageView imageView;
        //private Context context;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            imageView = (ImageView) itemView.findViewById(R.id.ivImage);
            imageView.setImageResource(0);
            // Store the context
            //this.context = context;
            // Attach a click listener to the entire row view
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition(); // gets item position
                Article article = mArticles.get(position);
                Intent intent = new Intent(getContext(), ArticleActivity.class);
                intent.putExtra(EXTRA_PARAMS,  article);
                getContext().startActivity(intent);


        }
    }
}
