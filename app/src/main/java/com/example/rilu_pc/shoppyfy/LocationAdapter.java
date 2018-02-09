package com.example.rilu_pc.shoppyfy;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by babur on 05-02-2018.
 */

public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.ViewHolder>
{

    private ArrayList<Location> Data;


    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder



    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        // each data item is just a string in this case
        public View mView;
        TextView time,lon,lat;

        public ViewHolder(View v)
        {
            super(v);
            mView = v;
            time=mView.findViewById(R.id.time);
            lon=mView.findViewById(R.id.lon);
            lat=mView.findViewById(R.id.lat);
        }



    }
    public LocationAdapter(ArrayList<Location> myDataset)
    {
        Data = myDataset;


    }


    // Create new views (invoked by the layout manager)
    @Override
    public LocationAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        // create a new view
        View v =  LayoutInflater.from(parent.getContext()).inflate(R.layout.item_locationlist, parent, false);
        // set the view's size, margins, paddings and layout parameters


       ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position)
    {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        String time=Data.get(position).get_time();
       // Log.d("gettime",time);
        holder.time.setText(time);
        String lon=Data.get(position).get_lon();
        holder.lon.setText(lon);
        String lat=Data.get(position).get_lat();
        holder.lat.setText(lat);

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount()
    {
        return Data.size();

    }
}


