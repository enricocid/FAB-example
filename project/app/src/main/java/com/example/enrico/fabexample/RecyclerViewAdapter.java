package com.example.enrico.fabexample;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Enrico on 07/06/2017.
 */

class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.SimpleViewHolder> {

    private String[] numbers;
    private Activity activity;

    //simple recycler view adapter with activity and string array as arguments
    RecyclerViewAdapter(Activity activity, String[] numbers) {
        this.numbers = numbers;
        this.activity = activity;
    }

    @Override
    public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        // inflate recycler view items layout
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item, parent, false);
        return new SimpleViewHolder(activity, itemView);
    }

    @Override
    public void onBindViewHolder(SimpleViewHolder holder, int position) {

        //set text content according to position
        holder.textView.setText(numbers[position]);
    }

    @Override
    public int getItemCount() {

        //get array length
        return numbers.length;
    }

    //simple view holder implementing click and long click listeners and with activity and itemView as arguments
    static class SimpleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        private TextView textView;

        private Activity activity;

        SimpleViewHolder(Activity activity, View itemView) {
            super(itemView);

            //get activity
            this.activity = activity;

            //get the views
            textView = (TextView) itemView.findViewById(R.id.text);

            //enable click and on long click
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        //add click and long lick
        @Override
        public void onClick(View v) {

            //show me the clicked position
            Toast.makeText(activity, "Clicked position: " + String.valueOf(getAdapterPosition()), Toast.LENGTH_SHORT)
                    .show();
        }

        @Override
        public boolean onLongClick(View v) {

            //show me the long clicked position
            Toast.makeText(activity, "Long clicked position: " + String.valueOf(getAdapterPosition()), Toast.LENGTH_SHORT)
                    .show();

            return false;
        }
    }
}