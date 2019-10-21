package com.example.tablayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class ItemAdapter extends FirestoreRecyclerAdapter<Items,ItemAdapter.itemholder> {
    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public ItemAdapter(@NonNull FirestoreRecyclerOptions<Items> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ItemAdapter.itemholder itemholder, int i, @NonNull Items items) {
        itemholder.textname.setText(items.getName());
        itemholder.textcount.setText(String.valueOf(items.getCount()));
        itemholder.textdesc.setText(items.getDesc());
        itemholder.textid.setText(items.getId());



    }

    @NonNull
    @Override
    public ItemAdapter.itemholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout,parent,false);
        return new ItemAdapter.itemholder(v);
    }

    class itemholder extends RecyclerView.ViewHolder{
        //Item view layout components
        TextView textname,textdesc,textcount,textid;

        public itemholder(@NonNull View itemView) {
            super(itemView);
            textname = itemView.findViewById(R.id.text_name);
            textdesc = itemView.findViewById(R.id.Desc);
            textcount = itemView.findViewById(R.id.text_count);

            textid = itemView.findViewById(R.id.text_id);



        }
    }
}

