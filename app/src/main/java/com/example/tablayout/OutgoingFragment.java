package com.example.tablayout;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;


/**
 * A simple {@link Fragment} subclass.
 */
public class OutgoingFragment extends Fragment {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private OutAdapter adapter;
    private String user_id;

    public OutgoingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        user_id = FirebaseAuth.getInstance().getUid();
        View view= inflater.inflate(R.layout.fragment_outgoing, container, false);
        FloatingActionButton fab = view.findViewById(R.id.fab);
        setUpRecyclerview(view);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), outadd.class);
                startActivity(intent);
            }
        });
        return view;
    }
    private void setUpRecyclerview(View v){
        Query query = db.collection("Inventory").document(user_id)
                .collection("Outgoing");
        FirestoreRecyclerOptions<Initem> options = new FirestoreRecyclerOptions.Builder<Initem>()
                .setQuery(query,Initem.class)
                .build();

        adapter = new OutAdapter(options);
        RecyclerView recyclerView = v.findViewById(R.id.out_recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                adapter.deleteItem(viewHolder.getAdapterPosition());
            }
        }).attachToRecyclerView(recyclerView);




    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}


