package com.example.tablayout;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class Home extends Fragment implements ExampleDialog2.ExampleDialogListener {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private ItemAdapter adapter;
    private String user_id,Location;
    private int To,From;
    private String ID=null;



    public Home() {
        // Required empty public constructor
        setHasOptionsMenu(true);
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        user_id = FirebaseAuth.getInstance().getUid();


        View view = inflater.inflate(R.layout.fragment_home, container, false);

        setUpRecyclerview(view);

        FloatingActionButton fab = view.findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(getContext(),AddingItem.class);
                startActivity(intent);
            }
        });
        return view;
    }
    private void setUpRecyclerview(View v){
        Query query = db.collection("Inventory").document(user_id)
                .collection("Items");
        FirestoreRecyclerOptions<Items> options = new FirestoreRecyclerOptions.Builder<Items>()
                .setQuery(query,Items.class)
                .build();

        adapter = new ItemAdapter(options);
        RecyclerView recyclerView = v.findViewById(R.id.item_recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new ItemAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
                Items item = documentSnapshot.toObject(Items.class);
                Toast.makeText(getContext(), "ID:"+item.getId(), Toast.LENGTH_SHORT).show();
                ExampleDialog2 dialog = new ExampleDialog2();
                ID = item.getId();
                dialog.setTargetFragment(Home.this, 0);
                dialog.show(requireActivity().getSupportFragmentManager(), null);
            }
        });



    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        menu.clear();
        inflater.inflate(R.menu.main_menu,menu);
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
    public void applyTexts(int to, int from,String location) {
        To=to;
        Log.d("IN func","Started");
        From = from;
        Location = location;
        Log.d("Test","Is this working?");
        Log.d("Dates", String.valueOf(To)+From);
        Bundle bundle = new Bundle();
        if(ID!=null){
            bundle.putString("id",ID);
        }
        bundle.putInt("to",To);
        bundle.putInt("from",From);
        bundle.putString("loc",Location);
        Intent i = new Intent(getContext(),totalinfilter.class);
        i.putExtras(bundle);
        startActivity(i);



    }
    @Override
    public void applyTexts2(int to, int from,String location) {
        To=to;
        Log.d("Out func","Started");
        From = from;
        Location = location;
        Log.d("Test","Is this working?");
        Log.d("Dates", String.valueOf(To)+From);
        Bundle bundle = new Bundle();
        if(ID!=null){
            bundle.putString("id",ID);
        }
        bundle.putInt("to",To);
        bundle.putInt("from",From);
        bundle.putString("loc",Location);
        Intent i = new Intent(getContext(),totaloutfilter.class);
        i.putExtras(bundle);
        startActivity(i);



    }



}
