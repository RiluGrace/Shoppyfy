package com.example.rilu_pc.shoppyfy;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import static android.support.v4.widget.SearchViewCompat.getQuery;

public class ArticleActivity extends AppCompatActivity {
    //    private Toolbar mToolbar;
    String responseText;
    StringBuffer response;
    URL url;
    EditText et_title, et_body;
    Button save;


    private RecyclerView.Adapter mAdapter;
   // final ArrayList<Article> articlelist = new ArrayList<>();
    private ProgressDialog progressDialog;
    List<NameValuePair> params = new ArrayList<NameValuePair>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);

        et_title = findViewById(R.id.et_title);
        et_body = findViewById(R.id.et_body);
        save = findViewById(R.id.save);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.mToolbar0);
        mToolbar.setTitle(" Create Article ");
        mToolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_keyboard_arrow_left);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                startActivity(new Intent(ArticleActivity.this, HomeActivity.class));
            }
        });
    }

    public void save(View v)

    {
        new GetServerData().execute();
//        startActivity(new Intent(ArticleActivity.this, ArticleListActivity.class));
    }

    class GetServerData extends AsyncTask
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
        protected Object doInBackground(Object[] objects)
        {

            return getWebServiceResponseData();
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);


            try {
                mAdapter.notifyDataSetChanged();

                // Dismiss the progress dialog
                if (progressDialog.isShowing())
                    progressDialog.dismiss();
                // For populating list data
            } catch (Exception e) {
                e.printStackTrace();
            }

        }


    }

    public Object getWebServiceResponseData()
    {
        try {
            String path = "http://rilu-article.herokuapp.com/articles.json";
            url = new URL(path);
            Log.d("", "ServerData: " + path);
            DataOutputStream printout;
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setReadTimeout(15000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setUseCaches (false);
            conn.setRequestProperty("Content-Type","application/json");
            conn.setRequestProperty("api-key","api-key");
            conn.setRequestProperty("api-secret","api-secret");
            conn.connect();
//            conn.setDoOutput(true);

            //*********************
           //to get the title n body
            String title=et_title.getText().toString();
            String body=et_body.getText().toString();
            //Whereas postParameters is the query string, such as “param1=value1&param2=value2”
           // String parameter="title="+title+"&message="+body;



           JSONObject articles = new JSONObject();
           JSONObject data=new JSONObject();
           articles.put("title",title);
           articles.put("message",body);
           data.put("article",articles);

//            OutputStreamWriter wr= new OutputStreamWriter(conn.getOutputStream());
//            wr.write(data.toString());

            // Send POST output.
            printout = new DataOutputStream(conn.getOutputStream ());
//            StringEntity se=new StringEntity(data.toString());
            printout.writeBytes(data.toString());
            printout.flush ();
            printout.close ();


//            conn.setFixedLengthStreamingMode(data.toString().length());
//            PrintWriter out = new PrintWriter(conn.getOutputStream());
//            out.print(data);
//            out.close();

//            params.add(new BasicNameValuePair("title","What is IT" ));
//            params.add(new BasicNameValuePair("message","Information Technology"));
//            OutputStream os = conn.getOutputStream();
//            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
//           writer.write(getQuery(params));
//            writer.flush();
//            writer.close();
//            os.close();



            int responseCode = conn.getResponseCode();

            response = new StringBuffer();
            Log.d("", "Response code: " + responseCode);
            if (responseCode == 201) {
                // Reading response from input Stream
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String output;

                while ((output = in.readLine()) != null) {
                    response.append(output);
                }
                in.close();
            }
            else
            {
                runOnUiThread(new Runnable() {

                    public void run() {

                        Toast.makeText(getApplicationContext(), "API call failed", Toast.LENGTH_SHORT).show();

                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (response.toString()!=null)
        {
            responseText = response.toString();
        }
        //Call ServerData() method to call webservice and store result in response
        //  response = service.ServerData(path, postDataParams);
        Log.d("", "data:" + responseText);
        try {
            // JSONArray jsonarray = new JSONArray(responseText);

            JSONObject jsonobject = new JSONObject(responseText);
            int id = jsonobject.getInt("id");
            String header = jsonobject.getString("title");
            et_title.setText(header);
            String description = jsonobject.getString("message");
            et_body.setText(description);
            Log.d("", "id:" + id);
            Log.d("", "header:" + header);
            Log.d("", "description:" + description);
            //  Article article=new Article(id,header,description);

            // articlelist.add(article);

        } catch (JSONException e)
        {
            e.printStackTrace();
        }
        return null;


    }
}