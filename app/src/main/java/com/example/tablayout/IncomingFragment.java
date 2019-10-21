package com.example.tablayout;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.local.QueryEngine;


/**
 * A simple {@link Fragment} subclass.
 */
public class IncomingFragment extends Fragment implements ExampleDialog.ExampleDialogListener {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private InAdapter adapter;
    private String user_id;
    Query qu;
    private int To,From;




    public IncomingFragment() {
        // Required empty public constructor
        setHasOptionsMenu(true);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        user_id = FirebaseAuth.getInstance().getUid();
        View view = inflater.inflate(R.layout.fragment_incoming, container, false);
        FloatingActionButton fab = view.findViewById(R.id.fab);
        FloatingActionButton d = view.findViewById(R.id.dialog);




        d.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ExampleDialog dialog = new ExampleDialog();
                dialog.setTargetFragment(IncomingFragment.this, 0);
                dialog.show(requireActivity().getSupportFragmentManager(), null);


            }
        });
        setUpRecyclerview(view);

        fab.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(getContext(),inadd.class);
                startActivity(intent);
            }
        });

        return view;
    }
    private void setUpRecyclerview(View v){
         qu = db.collection("Inventory").document(user_id)
                .collection("Incoming");

        FirestoreRecyclerOptions<Initem> options = new FirestoreRecyclerOptions.Builder<Initem>()
                .setQuery(qu,Initem.class)
                .build();

        adapter = new InAdapter(options);
        RecyclerView recyclerView = v.findViewById(R.id.in_recycler);
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
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        menu.clear();
        inflater.inflate(R.menu.incoming,menu);
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


    @Override
    public void applyTexts(int to, int from) {
        To=to;
        From = from;
        Log.d("Dates", String.valueOf(To)+From);
        Bundle bundle = new Bundle();
        bundle.putInt("to",To);
        bundle.putInt("from",From);
        Intent i = new Intent(getContext(),incoming_filter.class);
        i.putExtras(bundle);
        startActivity(i);



    }
}

