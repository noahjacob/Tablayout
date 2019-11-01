package com.example.tablayout;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.Fragment;

import com.google.firebase.firestore.Query;

import java.util.Calendar;

public class ExampleDialog extends AppCompatDialogFragment {
    private EditText TO,FROM;
    private ExampleDialogListener listener;
    DatePickerDialog picker;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_dialog, null);

        builder.setView(view)
                .setTitle("Date Range")
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })

                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        int to = Integer.valueOf(TO.getText().toString());
                        int from = Integer.valueOf(FROM.getText().toString());
                        listener = (ExampleDialogListener) getTargetFragment();
                        listener.applyTexts(to, from);
                    }
                });

        FROM= view.findViewById(R.id.from);
        TO = view.findViewById(R.id.to);

        FROM.setInputType(InputType.TYPE_NULL);
        FROM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                final int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);

                // date picker dialog
                picker = new DatePickerDialog(getContext(),
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
                                FROM.setText(datenumber);
                            }
                        }, year, month, day);
                picker.show();
            }

        });


        TO.setInputType(InputType.TYPE_NULL);
        TO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                final int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);

                // date picker dialog
                picker = new DatePickerDialog(getContext(),
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
                                TO.setText(datenumber);
                            }
                        }, year, month, day);
                picker.show();
            }

        });

        return builder.create();
    }

    @Override
    public void onAttachFragment(@NonNull Fragment childFragment) {
        super.onAttachFragment(childFragment);
        try {
            listener = (ExampleDialogListener) childFragment;
        } catch (ClassCastException e) {
            throw new ClassCastException(childFragment.toString() +
                    "must implement ExampleDialogListener");
        }

    }

    public interface ExampleDialogListener {
         void applyTexts(int to, int from);
    }
}

