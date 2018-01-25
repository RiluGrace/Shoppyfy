package com.example.rilu_pc.shoppyfy;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

public class ArticleListActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;


 //   private Button btnSubmit;
    String responseText;
    StringBuffer response;
    URL url;

  final ArrayList<Article> articlelist = new ArrayList<>();
    private ProgressDialog progressDialog;
    ListView listView;
    String tag="Article list";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_list);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.mToolbar1);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_keyboard_arrow_left);




//        Article article = new Article();
        //article.setHeader("What is Computer?");
        //article.setDescription("A computer is a device that can be instructed to carry out arbitrary sequences of arithmetic or logical operations automatically.");

        // Adding articles to list
//        articlelist.add(new Article("What is Computer?", "A computer is a device that can be instructed to carry out arbitrary sequences of arithmetic or logical operations automatically."));
//        articlelist.add(new Article("What is Computer?", "A computer is a device that can be instructed to carry out arbitrary sequences of arithmetic or logical operations automatically."));
//        articlelist.add(new Article("What is Computer?", "A computer is a device that can be instructed to carry out arbitrary sequences of arithmetic or logical operations automatically."));
//        articlelist.add(new Article("What is Computer?", "A computer is a device that can be instructed to carry out arbitrary sequences of arithmetic or logical operations automatically."));
//        articlelist.add(new Article("What is Computer?", "A computer is a device that can be instructed to carry out arbitrary sequences of arithmetic or logical operations automatically."));
//        articlelist.add(new Article("What is Computer?", "A computer is a device that can be instructed to carry out arbitrary sequences of arithmetic or logical operations automatically."));
//        articlelist.add(new Article("What is Computer?", "A computer is a device that can be instructed to carry out arbitrary sequences of arithmetic or logical operations automatically."));
//        articlelist.add(new Article("What is Computer?", "A computer is a device that can be instructed to carry out arbitrary sequences of arithmetic or logical operations automatically."));
//        articlelist.add(new Article("What is Computer?", "A computer is a device that can be instructed to carry out arbitrary sequences of arithmetic or logical operations automatically."));
//        articlelist.add(new Article("What is Computer?", "A computer is a device that can be instructed to carry out arbitrary sequences of arithmetic or logical operations automatically."));

        // specify an adapter (see also next example)


        mAdapter = new ArticlesListAdapter(articlelist, new OnItemClickListener() {
            @Override
            public void onItemClick(Article item) {
                Toast.makeText(ArticleListActivity.this, "article", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(ArticleListActivity.this, ArticleDetailActivity.class);
                i.putExtra("id", item.getId());
                startActivity(i);
            }
        });
        mRecyclerView.setAdapter(mAdapter);

        //back to home page
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ArticleListActivity.this,HomeActivity.class));
            }
        });
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        articlelist.clear();
        new GetServerData().execute();
    }

    class GetServerData extends AsyncTask
    {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            progressDialog = new ProgressDialog(ArticleListActivity.this);
            progressDialog.setMessage("Articles");
            progressDialog.setCancelable(false);
            progressDialog.show();

        }

        @Override
        protected Object doInBackground(Object[] objects) {
            return getWebServiceResponseData();
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);

            mAdapter.notifyDataSetChanged();

            // Dismiss the progress dialog
            if (progressDialog.isShowing())
                progressDialog.dismiss();
            // For populating list data

    }




    }

    public Object getWebServiceResponseData()
    {
        try {
             String path="http://rilu-article.herokuapp.com/articles.json";
            url = new URL(path);

            Log.d(tag, "ServerData: " + path);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(15000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("GET");

            int responseCode = conn.getResponseCode();

            Log.d(tag, "Response code: " + responseCode);
            if (responseCode == HttpsURLConnection.HTTP_OK) {
                // Reading response from input Stream
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(conn.getInputStream()));
                String output;
                response = new StringBuffer();

                while ((output = in.readLine()) != null) {
                    response.append(output);
                }
                in.close();
            }}
        catch(Exception e){
            e.printStackTrace();
        }

        responseText = response.toString();
        //Call ServerData() method to call webservice and store result in response
        //  response = service.ServerData(path, postDataParams);
        Log.d(tag, "data:" + responseText);
        try {
            JSONArray jsonarray = new JSONArray(responseText);
            for (int i = 0; i < jsonarray.length(); i++)
            {
                JSONObject jsonobject = jsonarray.getJSONObject(i);
                int id = jsonobject.getInt("id");
                String header = jsonobject.getString("title");
                String description=jsonobject.getString("message");
                Log.d("", "id:" + id);
                Log.d("", "header:" + header);
                Log.d("", "description:" + description);
                Article article=new Article(id,header,description);

                articlelist.add(article);

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return  null;
    }
}