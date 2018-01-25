package com.example.rilu_pc.shoppyfy;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>
{
    private ArrayList<Merchant> mDataset;
    Context mContext;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        // each data item is just a string in this case
        public View mView;
        TextView tv_name,tv_loc,tv_type;
        ImageView iv_image;

        public ViewHolder(View v)
        {
            super(v);
            mView = v;
            iv_image=mView.findViewById(R.id.iv_image);
            tv_name=mView.findViewById(R.id.tv_name);
            tv_loc=mView.findViewById(R.id.tv_loc);
            tv_type=mView.findViewById(R.id.tv_type);
        }


    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapter(ArrayList<Merchant> myDataset, Context mContext)
    {
        mDataset = myDataset;
        mContext = mContext;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        // create a new view
        View v =  LayoutInflater.from(parent.getContext()).inflate(R.layout.item_merchantlist, parent, false);
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
        String image=mDataset.get(position).getImageurl();
        String name=mDataset.get(position).getName();
        String location=mDataset.get(position).get_location();
        String businesstype=mDataset.get(position).get_business_type();

                Picasso.with(mContext)
                .load(image)
                .placeholder(R.drawable.placeholder)   // optional
                .error(R.drawable.error)      // optional
                .resize(400,400)                        // optional
                .into(holder.iv_image);


        holder.tv_name.setText(name);
        holder.tv_loc.setText(location);
        holder.tv_type.setText(businesstype);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount()
    {
        return mDataset.size();

    }
}