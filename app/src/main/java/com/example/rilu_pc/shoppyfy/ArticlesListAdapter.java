package com.example.rilu_pc.shoppyfy;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by babur on 17-01-2018.
 */

public class ArticlesListAdapter extends RecyclerView.Adapter<ArticlesListAdapter.ViewHolder>
{
    private ArrayList<Article> mDataset;
    private final OnItemClickListener listener;




    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
       // public View mTextView;
        public View mView;



        public ViewHolder(View v) {
            super(v);
            mView = v;


        }

        public void bind(final Article item, final OnItemClickListener listener) {
            mView.setOnClickListener(new View.OnClickListener() {

                @Override public void onClick(View v) {

                    listener.onItemClick(item);

                }

            });

        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public ArticlesListAdapter(ArrayList<Article> myDataset, OnItemClickListener listener)
    {
        mDataset = myDataset;
        this.listener = listener;

    }

    // Create new views (invoked by the layout manager)
    @Override
    public ArticlesListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = (View) LayoutInflater.from(parent.getContext()).inflate(R.layout.item_article_list, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override

    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        //String header=mDataset.get(position).getHeader();

        TextView header,description;

        header=holder.mView.findViewById(R.id.header);
        description=holder.mView.findViewById(R.id.description);

        Article a  = mDataset.get(position);
       String h = a.getHeader();
        header.setText(h);

        Article d  = mDataset.get(position);
        String d1 = d.getDescription();
        description.setText(d1);

        holder.bind(mDataset.get(position), listener);

    }





    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
