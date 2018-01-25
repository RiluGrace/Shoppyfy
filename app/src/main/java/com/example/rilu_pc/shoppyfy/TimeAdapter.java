package com.example.rilu_pc.shoppyfy;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by babur on 23-01-2018.
 */

public class TimeAdapter extends RecyclerView.Adapter<TimeAdapter.ViewHolder>
{
    private ArrayList<Time> Data;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        // each data item is just a string in this case
        public View mView;
        TextView time;

        public ViewHolder(View v)
        {
            super(v);
            mView = v;
            time=mView.findViewById(R.id.time);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public TimeAdapter(ArrayList<Time> myDataset)
    {
        Data = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public TimeAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        // create a new view
        View v =  LayoutInflater.from(parent.getContext()).inflate(R.layout.item_timelist, parent, false);
        // set the view's size, margins, paddings and layout parameters


        TimeAdapter.ViewHolder vh = new TimeAdapter.ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(TimeAdapter.ViewHolder holder, int position)
    {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        String time=Data.get(position).get_time();
        Log.d("gettime",time);
        holder.time.setText(time);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount()
    {
        return Data.size();

    }
}

