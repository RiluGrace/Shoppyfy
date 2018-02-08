package com.example.rilu_pc.shoppyfy;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.EventLogTags;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

public class ArticleDetailActivity extends AppCompatActivity
{
     int id;
    TextView h,d;
    String responseText;
    StringBuffer response;
    URL url;
    ListView listView;
    String tag="Article list";
    private Context mContext;

    final ArrayList<Article> articlelist = new ArrayList<>();
    private ProgressDialog progressDialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_detail);
        h = (TextView) findViewById(R.id.head);
        d = (TextView) findViewById(R.id.desc);

        id = getIntent().getExtras().getInt("id");

        Toolbar mToolbar = (Toolbar) findViewById(R.id.mToolbar);
        mToolbar.setTitle("Article Details");
        mToolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_keyboard_arrow_left);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ArticleDetailActivity.this, ArticleListActivity.class));
            }
        });
    }

    protected void onStart()
    {
        super.onStart();
        new GetServerData().execute();
    }

    class GetServerData extends AsyncTask<Article, Void, Article>
    {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
//            progressDialog = new ProgressDialog(ArticleDetailActivity.this);
//            progressDialog.setMessage("Articles");
//            progressDialog.setCancelable(false);
//            progressDialog.show();

        }

        @Override
        protected Article doInBackground(Article[] articles) {
            return getWebServiceResponseData();

        }
        @Override
        protected void onPostExecute(Article article)
        {
            super.onPostExecute(article);
            String header=article.getHeader();
            String description=article.getDescription();
            h.setText(header);
            d.setText(description);
        }
    }

    public Article getWebServiceResponseData()
    {
        try {
            String path="http://rilu-article.herokuapp.com/articles/" +id+ ".json";
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
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
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
        Article article=new Article();
        try {
           // JSONArray jsonarray = new JSONArray(responseText);

                JSONObject jsonobject = new JSONObject(responseText);
                int id = jsonobject.getInt("id");

                 String header = jsonobject.getString("title");
                 article.setHeader(header);
              //  h.setText(header);
            //String head=h.getText().toString();
                String description=jsonobject.getString("message");
                article.setDescription(description);


              //  d.setText(description);
                Log.d("", "id:" + id);
                Log.d("", "header:" + header);
                Log.d("", "description:" + description);
              //  Article article=new Article(id,header,description);

               // articlelist.add(article);

            }

        catch (JSONException e) {
            e.printStackTrace();
        }
        return  article;
    }


}
