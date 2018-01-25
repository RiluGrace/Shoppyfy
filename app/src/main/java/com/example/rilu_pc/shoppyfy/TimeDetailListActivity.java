package com.example.rilu_pc.shoppyfy;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;

public class TimeDetailListActivity extends AppCompatActivity
{
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    DatabaseHandler db = new DatabaseHandler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_detail_list);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);


          ArrayList<Time> timelist=new ArrayList<>();
          timelist=(ArrayList) db.getAllTime();
        for (int i = 0; i < timelist.size(); i++)
        {
           Time t= timelist.get(i);
            Log.d("time..",t.get_time());
        }

        // specify an adapter (see also next example)
        mAdapter = new TimeAdapter(timelist);
        mRecyclerView.setAdapter(mAdapter);
    }
}
