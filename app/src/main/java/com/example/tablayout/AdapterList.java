package com.example.tablayout;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterList extends RecyclerView.Adapter<AdapterList.MyHolder> {
    Context context;
    ArrayList<Items> Items;


    public AdapterList(Context context, ArrayList<Items> Items) {
        this.context = context;
        this.Items = Items;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_layout, viewGroup, false);

        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder myholder, int i) {
        final int f = i;
        myholder.Name.setText(Items.get(i).getName());
        myholder.Count.setText(Integer.toString(Items.get(i).getCount()));
        myholder.Desc.setText(Items.get(i).getDesc());


    }

    @Override
    public int getItemCount() {

        return Items.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {

        TextView Name, Count,Desc;
        RelativeLayout relativeLayout;
        private MyHolder(@NonNull View itemView) {
            super(itemView);
            Name = itemView.findViewById(R.id.i_name);
            Count = itemView.findViewById(R.id.count);
            relativeLayout = itemView.findViewById(R.id.itemlist);
            Desc = itemView.findViewById(R.id.item_desc);

        }
    }
}
