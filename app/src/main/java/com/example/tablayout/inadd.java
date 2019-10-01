package com.example.tablayout;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;

public class inadd extends AppCompatActivity {
    DatePickerDialog picker;
    EditText eText;
    private TextInputEditText productId;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inadd);
        ImageButton imageButton = findViewById(R.id.barcode);
        productId = findViewById(R.id.add_productId);

        eText=findViewById(R.id.date);
        eText.setInputType(InputType.TYPE_NULL);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(inadd.this,Scanner.class);
                startActivity(intent);
            }
        });
        eText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(inadd.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                eText.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, year, month, day);
                picker.show();
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
