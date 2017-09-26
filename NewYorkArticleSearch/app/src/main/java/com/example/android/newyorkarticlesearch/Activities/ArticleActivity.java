package com.example.android.newyorkarticlesearch.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.android.newyorkarticlesearch.R;
import com.example.android.newyorkarticlesearch.models.Article;

import static com.example.android.newyorkarticlesearch.R.id.wvArticle;
import static com.example.android.newyorkarticlesearch.adapters.ArticleAdapter.EXTRA_PARAMS;

public class ArticleActivity extends AppCompatActivity {
    private ShareActionProvider miShareAction;
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);
        Article article = getIntent().getParcelableExtra(EXTRA_PARAMS);
        webView = (WebView) findViewById(wvArticle);
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean  shouldOverrideUrlLoading(WebView view,String url){
                view.loadUrl(url);
                return true;
            }
        }
        );
        webView.loadUrl(article.getWebUrl());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_article, menu);
        MenuItem item = menu.findItem(R.id.menu_item_share);
        miShareAction = (ShareActionProvider) MenuItemCompat.getActionProvider(item);
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, webView.getUrl());
        miShareAction.setShareIntent(shareIntent);
        return super.onCreateOptionsMenu(menu);


    }
}
