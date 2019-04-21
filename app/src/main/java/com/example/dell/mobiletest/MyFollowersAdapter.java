package com.example.dell.mobiletest;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;


public class MyFollowersAdapter extends RecyclerView.Adapter<MyFollowersAdapter.ViewHolder> {
    Context context;


    private List<Followers> mDataset;
    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView name;
        public ImageView avatar;


        public ViewHolder(View v) {
            super(v);
            name = (TextView) v.findViewById(R.id.nameofFollower);
            avatar = (ImageView) v.findViewById(R.id.AvatarofFollower);


        }
    }
    public void add(int position, Followers item) {
        mDataset.add(position, item);
        notifyItemInserted(position);
    }
    public void remove(Followers item) {
        int position = mDataset.indexOf(item);
        mDataset.remove(position);
        notifyItemRemoved(position);
    }
    public MyFollowersAdapter(List<Followers> myDataset) {
        this.mDataset = myDataset;
    }
    @Override
    public MyFollowersAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.followers_list, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.name.setText(mDataset.get(position).getName());
        Glide.with(holder.avatar.getContext()).load(mDataset.get(position).getUrl()).into(holder.avatar);



    }
    @Override
    public int getItemCount() {
        return mDataset.size();
    }



}