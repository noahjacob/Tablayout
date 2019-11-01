package com.example.tablayout;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
public class OutgoingFragment extends Fragment implements ExampleDialog.ExampleDialogListener {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private OutAdapter adapter;
    private String user_id;
    private int To,From;

    public OutgoingFragment() {
        // Required empty public constructor
        setHasOptionsMenu(true);
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
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        menu.clear();
        inflater.inflate(R.menu.incoming,menu);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.signout) {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(getContext(), Login.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);


            return true;
        }
        else{
            ExampleDialog dialog = new ExampleDialog();
            dialog.setTargetFragment(OutgoingFragment.this, 0);
            dialog.show(requireActivity().getSupportFragmentManager(), null);

        }

        return super.onOptionsItemSelected(item);
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
        Log.d("Test","Is this working?");
        Log.d("Dates", String.valueOf(To)+From);
        Bundle bundle = new Bundle();
        bundle.putInt("to",To);
        bundle.putInt("from",From);
        Intent i = new Intent(getContext(),outfilter.class);
        i.putExtras(bundle);
        startActivity(i);



    }
}


