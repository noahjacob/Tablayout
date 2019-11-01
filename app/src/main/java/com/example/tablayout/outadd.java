package com.example.tablayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
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
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class outadd extends AppCompatActivity {
    DatePickerDialog picker;
    EditText edate, edesc, eloc, equant, ename;
    Button Send;
    private TextInputEditText productId;
    private String user_id;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        user_id = FirebaseAuth.getInstance().getUid();
        setContentView(R.layout.activity_outadd);
        ImageButton imageButton = findViewById(R.id.barcode);
        productId = findViewById(R.id.add_productId);
        edesc = findViewById(R.id.Desc);
        eloc = findViewById(R.id.loc);
        equant = findViewById(R.id.quant);
        ename = findViewById(R.id.Item_name);
        Send = findViewById(R.id.btnsave);
        edate = findViewById(R.id.date);


        edate.setInputType(InputType.TYPE_NULL);
        Send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveIn(view);
            }
        });
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(outadd.this, Scanner3.class);
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
                picker = new DatePickerDialog(outadd.this,
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
    }

    public void saveIn(View v) {
        final String Name = ename.getText().toString();
        final int Date = Integer.valueOf(edate.getText().toString());
        final int Quant = Integer.valueOf(equant.getText().toString());
        final String loc = eloc.getText().toString();
        final String Desc = edesc.getText().toString();
        final String id = productId.getText().toString();

        final CollectionReference Itemref =  db.collection("Inventory").document(user_id).collection("Items");
        Itemref.whereEqualTo("id",id).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                String name;
                String desc;
                for(QueryDocumentSnapshot document :task.getResult()){
                    int snapcount = Integer.valueOf(document.get("count").toString());
                    Items out = document.toObject(Items.class);




                    if (Quant>out.getCount()||Quant-out.getCount()==0){
                        Toast.makeText(outadd.this, "Insufficient stock", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Map<Object, Integer> map = new HashMap<>();
                        map.put("count",snapcount-Quant );

                        name=out.getName();
                        desc = out.getDesc();
                        Log.d("Name", String.valueOf(document.get("name")));
                        Itemref.document(document.getId()).set(map,SetOptions.merge());
                        db.collection("Inventory").document(user_id).collection("Outgoing")
                                .add(new Initem(name,desc,Quant,loc,id,Date));
                        db.collection("Inventory").document(user_id).collection("TotalOut")
                                .add(new Initem(Name,Desc,Quant,loc,id,Date));
                        Toast.makeText(outadd.this, "Added to database", Toast.LENGTH_SHORT).show();

                    }
                }

            }
        });


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
