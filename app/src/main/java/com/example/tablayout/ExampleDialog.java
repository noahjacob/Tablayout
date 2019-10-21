package com.example.tablayout;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.Fragment;

import com.google.firebase.firestore.Query;

public class ExampleDialog extends AppCompatDialogFragment {
    private EditText TO,FROM;
    private ExampleDialogListener listener;

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

