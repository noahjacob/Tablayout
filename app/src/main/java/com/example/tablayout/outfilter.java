package com.example.tablayout;


import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class outfilter extends AppCompatActivity {
    private int To,From;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private filterAdapter adapter;
    private String user_id;
    Query qu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outfilter);

        Bundle extras = getIntent().getExtras();

        setContentView(R.layout.activity_outfilter);
        user_id = FirebaseAuth.getInstance().getUid();
        To = extras.getInt("to");
        From = extras.getInt("from");
        setUpRecyclerview(To,From);
    }

    private void setUpRecyclerview(int to, int from){
        qu = db.collection("Inventory").document(user_id)
                .collection("Outgoing")
                .whereGreaterThanOrEqualTo("date",from)
                .whereLessThanOrEqualTo("date",to);
        Log.d("TO", String.valueOf(to));
        FirestoreRecyclerOptions<Initem> options = new FirestoreRecyclerOptions.Builder<Initem>()
                .setQuery(qu,Initem.class)
                .build();

        adapter = new filterAdapter(options);
        RecyclerView recyclerView = findViewById(R.id.outfilter_recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);








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
