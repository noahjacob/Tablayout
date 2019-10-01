package com.example.tablayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterList extends RecyclerView.Adapter<AdapterList.ViewHolder> {
    private ArrayList<Items> mList;
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mName;
        public TextView mDesc;
        public TextView mCount;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mName = itemView.findViewById(R.id.i_name);
            mDesc = itemView.findViewById(R.id.item_desc);
            mCount = itemView.findViewById(R.id.count);
        }
    }
    public AdapterList(ArrayList<Items> List){
        mList = List;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout,parent,false);
        ViewHolder vh = new ViewHolder(v);
        return vh;

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Items currentItem =  mList.get(position);
        holder.mName.setText(currentItem.getName());
        holder.mDesc.setText(currentItem.getDesc());
        holder.mCount.setText(currentItem.getCount());


    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}
