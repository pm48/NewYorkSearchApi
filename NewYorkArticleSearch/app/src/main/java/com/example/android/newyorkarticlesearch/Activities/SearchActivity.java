package com.example.android.newyorkarticlesearch.Activities;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.android.newyorkarticlesearch.R;
import com.example.android.newyorkarticlesearch.adapters.ArticleAdapter;
import com.example.android.newyorkarticlesearch.models.Article;
import com.example.android.newyorkarticlesearch.utils.EndlessRecyclerViewScrollListener;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;


public class SearchActivity extends AppCompatActivity {
    EditText etQuery;
    Button btnSearch;
    // Store a member variable for the listener
    private EndlessRecyclerViewScrollListener scrollListener;
    ArrayList<Article> articles;
    ArticleAdapter adapter;
    private String date = new String();
    private ArrayList<String> news = new ArrayList<>();
    private String order = new String();
    private  SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setupViews();
    }

    private void setupViews() {
        etQuery = (EditText) findViewById(R.id.etQuery);
        btnSearch = (Button) findViewById(R.id.btnSearch);
        // Configure the RecyclerView
        RecyclerView rvItems = (RecyclerView) findViewById(R.id.rvResults);
        articles = new ArrayList<>();

        adapter = new ArticleAdapter(this,articles);
        rvItems.setAdapter(adapter);
        StaggeredGridLayoutManager gridLayoutManager = new StaggeredGridLayoutManager(4, LinearLayoutManager.VERTICAL);
        rvItems.setLayoutManager(gridLayoutManager);
        scrollListener = new EndlessRecyclerViewScrollListener(gridLayoutManager) {
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                loadArticles(view,page);
            }
        };
        // Adds the scroll listener to RecyclerView
        rvItems.addOnScrollListener(scrollListener);


    }

    private Boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }

    public boolean isOnline() {
        Runtime runtime = Runtime.getRuntime();
        try {
            Process ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8");
            int     exitValue = ipProcess.waitFor();
            return (exitValue == 0);
        } catch (IOException e)          { e.printStackTrace(); }
        catch (InterruptedException e) { e.printStackTrace(); }
        return false;
    }

    private void loadArticles(View view,int page) {
        if(isNetworkAvailable() && isOnline()) {
            String query = searchView.getQuery().toString();
            AsyncHttpClient client = new AsyncHttpClient();
            String url = "https://api.nytimes.com/svc/search/v2/articlesearch.json";
            RequestParams params = new RequestParams();
            params.put("api-key", "d1f515eb20094309a5f0343a026bfcc5");
            params.put("page", page);

            params.put("q", query);
            if (!date.isEmpty()) {
                params.put("begin_date", date);
            }
            if (!order.isEmpty()) {
                params.put("sort", order);
            }
            if (!news.isEmpty()) {
                String value = "news_desk:(";
                for (String element : news) {
                    value = value + "\"" + element + "\"" + " ";
                }
                value = value.trim() + ")";
                params.put("fq", value);
            }
            client.get(url, params, new JsonHttpResponseHandler() {

                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    JSONArray articlesJSONResults = null;
                    try {
                        articlesJSONResults = response.getJSONObject("response").getJSONArray("docs");
                        articles.addAll(Article.fromJSONArray(articlesJSONResults));
                        adapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    super.onFailure(statusCode, headers, responseString, throwable);
                }
            });
        }
        else{
            Toast.makeText(this,"Please check your network settings.",Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);

        searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                onArticleSearch(searchView);
                searchView.clearFocus();

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.action_filter:
                Intent i = new Intent(getApplicationContext(),
                        FilteredActivity.class);
                if(!date.isEmpty()) {
                    i.putExtra("date", date);
                }
                if(!order.isEmpty()){
                    i.putExtra("order",order);
                }
                if(!news.isEmpty()){
                    i.putStringArrayListExtra("news",news);
                }
                startActivityForResult(i, 5);
                Toast.makeText(this, "FilterPage ", Toast.LENGTH_LONG).show();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }

    public void onArticleSearch(View view) {
        articles.clear();
        // 2. Notify the adapter of the update
        adapter.notifyDataSetChanged(); // or notifyItemRangeRemoved
        // 3. Reset endless scroll listener when performing a new search
        scrollListener.resetState();
        loadArticles(view,0);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 5 && resultCode == RESULT_OK) {
            date = data.getExtras().getString("date");
            news = new ArrayList<>();
            news = data.getExtras().getStringArrayList("news");
            order = data.getExtras().getString("sort");
            onArticleSearch(btnSearch);
        }
    }


}
