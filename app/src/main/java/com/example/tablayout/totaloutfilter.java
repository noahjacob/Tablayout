package com.example.tablayout;


import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class totaloutfilter extends AppCompatActivity {
    TextView name,value,loc;
    private String user_id,id,Loc;
    private int To,From;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        user_id = FirebaseAuth.getInstance().getUid();
        Bundle extras = getIntent().getExtras();
        To = extras.getInt("to");
        From = extras.getInt("from");
        id = extras.getString("id");
        Loc = extras.getString("loc");
        name = findViewById(R.id.name);
        value = findViewById(R.id.totoutv);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_totaloutfilter);
        name = findViewById(R.id.name);
        value = findViewById(R.id.totoutv);
        loc = findViewById(R.id.loc);
        db.collection("Inventory").document(user_id).collection("Outgoing")
                .whereEqualTo("id",id)
                .whereEqualTo("location",Loc)
                .whereGreaterThanOrEqualTo("date",From)
                .whereLessThanOrEqualTo("date",To)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        int count = 0;
                        String Name = null;
                        for (QueryDocumentSnapshot document : task.getResult()) {


                            Initem in = document.toObject(Initem.class);
                            count+=in.getCount();
                            Name = in.getItemname();
                            Log.d("Countin", String.valueOf(count));

                        }
                        name.setText(Name);
                        loc.setText(Loc);

                        value.setText(String.valueOf(count));



                    }

                });

    }
}
