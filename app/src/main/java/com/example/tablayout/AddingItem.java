package com.example.tablayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class AddingItem extends AppCompatActivity{
    private Context context;
    private EditText productId,ename,edesc,ecount;
    Button send;

    private String user_id;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adding_item);
        user_id = FirebaseAuth.getInstance().getUid();

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED)
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.CAMERA}, 100);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        productId = findViewById(R.id.add_productId);
        ImageButton imageButton = findViewById(R.id.barcode);
        edesc = findViewById(R.id.Desc);
        ename = findViewById(R.id.Item_name);
        ecount = findViewById(R.id.quant);

        send = findViewById(R.id.btnsave);
        context = this;
        ename.setText("Name");
        edesc.setText("Desc");
        ecount.setText("1");

        toolbar.setNavigationOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){
                Intent intent = new Intent(context,MainActivity.class);
                startActivity(intent);

            }
        });

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(context,Scanner.class);
                startActivity(intent);
            }
        });
//        s.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(context,MainActivity.class);
//                startActivity(intent);
//            }
//        });
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveIn(view);

            }
        });

    }
    public void saveIn(View v) {
        final String Name = ename.getText().toString();

        final int Quant = Integer.valueOf(ecount.getText().toString());

        final String Desc = edesc.getText().toString();

        final String id = productId.getText().toString();


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
                                Toast.makeText(AddingItem.this, "Added to database", Toast.LENGTH_SHORT).show();
                                Intent i =new Intent(AddingItem.this,Home.class);
                                startActivity(i);



                            } else {
                                db.collection("Inventory").document(user_id).collection("Items")
                                        .whereEqualTo("id",id).get()
                                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                for (QueryDocumentSnapshot document : task.getResult()) {
                                                    ename.setText(document.get("name").toString());
                                                    edesc.setText(document.get("desc").toString());
                                                    ecount.setText(document.get("count").toString());

                                                }
                                            }
                                        });
                                Toast.makeText(AddingItem.this, "Item Already Exist!", Toast.LENGTH_SHORT).show();
                            }

                        }

                    }

                });
    }





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_menu,menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.additem){
            Intent i = new Intent(context,MainActivity.class);
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
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
