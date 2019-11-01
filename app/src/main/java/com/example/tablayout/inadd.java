package com.example.tablayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class inadd extends AppCompatActivity {
    DatePickerDialog picker;
    private Context context;
    private EditText edate,edesc,eloc,equant;
    Button Send;
    private EditText productId;
    private EditText ename;

    private String user_id;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        user_id = FirebaseAuth.getInstance().getUid();


        setContentView(R.layout.activity_inadd);
        ImageButton imageButton = findViewById(R.id.barcode);
        productId = findViewById(R.id.add_productId);
        edesc = findViewById(R.id.Desc);
        eloc = findViewById(R.id.loc);
        equant = findViewById(R.id.quant);
        ename = findViewById(R.id.Item_name);
        Send = findViewById(R.id.btnsave);
        edate=findViewById(R.id.date);
        edate.setInputType(InputType.TYPE_NULL);




        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(inadd.this,Scanner2.class);
                startActivity(intent);
            }
        });
        edate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                final int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);

                // date picker dialog
                picker = new DatePickerDialog(inadd.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                String myear,mmonthOfYear,day;
                                myear = String.valueOf(year);
                                mmonthOfYear = String.valueOf(monthOfYear+1);
                                day = String.valueOf(dayOfMonth);
                                if(monthOfYear+1 < 10){

                                    mmonthOfYear = "0" + mmonthOfYear;
                                }
                                if(dayOfMonth < 10){

                                    day  = "0" + day ;
                                }



                                String datenumber = myear+mmonthOfYear+day;
                                Log.d("date", datenumber);
                                edate.setText(datenumber);
                            }
                        }, year, month, day);
                picker.show();
            }
        });
        Send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveIn(view);
            }
        });

    }

    public void saveIn(View v){
        final String Name = ename.getText().toString();
        final int Date = Integer.valueOf(edate.getText().toString());
        final int Quant = Integer.valueOf(equant.getText().toString());
        final String loc = eloc.getText().toString();
        final String Desc = edesc.getText().toString();
        final String id = productId.getText().toString();
        final int[] realcount = {0};


        db.collection("Inventory").document(user_id).collection("Items")
                .whereEqualTo("id", id).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            QuerySnapshot documentSnapshot = task.getResult();
                            if (documentSnapshot.isEmpty()) {
                                db.collection("Inventory").document(user_id).collection("Items")
                                        .add(new Items(Name, Desc, Quant, id));
                                db.collection("Inventory").document(user_id).collection("Incoming")
                                        .add(new Initem(Name,Desc,Quant,loc,id,Date));
                                db.collection("Inventory").document(user_id).collection("TotalIncoming")
                                        .add(new Initem(Name,Desc,Quant,loc,id,Date));
                                Toast.makeText(inadd.this, "Added to database", Toast.LENGTH_SHORT).show();
                                finish();

                            } else {
                                db.collection("Inventory").document(user_id).collection("Items")
                                        .whereEqualTo("id",id).get()
                                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                for (QueryDocumentSnapshot document : task.getResult()) {
                                                    ename.setText(document.get("name").toString());
                                                    edesc.setText(document.get("desc").toString());
                                                    Items item = document.toObject(Items.class);
                                                    String itemname = item.getName();
                                                    int Count = item.getCount()+Quant;
                                                    String Id = item.getId();

                                                    String idesc = item.getDesc();


                                                    realcount[0] = Integer.valueOf(document.get("count").toString());
                                                    Log.d("item count inside", String.valueOf(realcount[0]));
                                                    Log.d("Icoming inside", String.valueOf(Quant));
                                                    Log.d("update", String.valueOf(realcount[0]+Quant));
                                                    Map<Object, Integer> map = new HashMap<>();
                                                    map.put("count",realcount[0]+Quant );
                                                    db.collection("Inventory").document(user_id)
                                                            .collection("Items")
                                                    .document(document.getId()).set(map, SetOptions.merge());
                                                    db.collection("Inventory").document(user_id).collection("Incoming")
                                                            .add(new Initem(itemname,idesc,Quant,loc,Id,Date));
                                                    db.collection("Inventory").document(user_id).collection("TotalIncoming")
                                                            .add(new Initem(itemname,idesc,Quant,loc,id,Date));
                                                    Toast.makeText(inadd.this, "Added to database", Toast.LENGTH_SHORT).show();

                                                }
                                            }

                                        });


                            }

                        }

                    }

                });



        Log.d("Incoming", String.valueOf(Quant));
        Log.d("item count", String.valueOf(realcount[0]));
        //finish();






    }
    @Override
    protected void onResume() {
        super.onResume();
        Intent data = getIntent();
        String Id = data.getStringExtra("prodId");
        if(Id!=null)
            productId.setText(Id);

    }
}
