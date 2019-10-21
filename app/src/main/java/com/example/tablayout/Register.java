package com.example.tablayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;



public class Register extends AppCompatActivity {

    private FirebaseAuth mAuth;

    private EditText EmailField;
    private EditText PasswordField;

    Button reg;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();

        EmailField = findViewById(R.id.email);
        PasswordField = findViewById(R.id.pass);


        reg = findViewById(R.id.regbtn);

        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createAccount(EmailField.getText().toString(), PasswordField.getText().toString());

            }
        });
    }

    private void createAccount(String email, String password) {

        if (!validateForm()) {

            Toast.makeText(Register.this, "email and password is required..",
                    Toast.LENGTH_SHORT).show();
            return;
        }


        // [START create_user_with_email]
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(Register.this, "Authentication successful.",
                                    Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(getApplicationContext() ,Login.class);
                            startActivity(i);

                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(Register.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(getApplicationContext() ,Register.class);
                            startActivity(i);

                        }

                        // [START_EXCLUDE]

                        // [END_EXCLUDE]
                    }
                });
        // [END create_user_with_email]
    }

    private boolean validateForm() {
        boolean valid = true;

        String email = EmailField.getText().toString();
        if (TextUtils.isEmpty(email)) {
            EmailField.setError("Required.");
            valid = false;
        } else {
            EmailField.setError(null);
        }

        String password = PasswordField.getText().toString();
        if (TextUtils.isEmpty(password)) {
            PasswordField.setError("Required.");
            valid = false;
        } else {
            PasswordField.setError(null);
        }

        return valid;
    }



}
