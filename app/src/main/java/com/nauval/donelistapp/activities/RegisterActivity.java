package com.nauval.donelistapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.nauval.donelistapp.R;

public class RegisterActivity extends AppCompatActivity {
    EditText etEmail,etPassword,etName;
    String email, password, name;
    FirebaseAuth mAuth;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        etEmail = findViewById(R.id.editTextTextEmailAddress);
        etPassword = findViewById(R.id.editTextTextPassword);
        etName = findViewById(R.id.editTextTextPersonName);
        mAuth = FirebaseAuth.getInstance();
    }

    public void register(View view) {
        email = etEmail.getText().toString();
        password = etPassword.getText().toString();
        name = etName.getText().toString();
        if(email.isEmpty()||password.isEmpty()||name.isEmpty()){
            Toast.makeText(this, "All field must be filled", Toast.LENGTH_SHORT).show();
        }
        else{
            mAuth.createUserWithEmailAndPassword(email, password).addOnSuccessListener(authResult -> {
                user = FirebaseAuth.getInstance().getCurrentUser();
                UserProfileChangeRequest profileUpdate = new UserProfileChangeRequest.Builder()
                        .setDisplayName(name).build();
                user.updateProfile(profileUpdate)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                Log.d("Update user name", "User profile updated.");
                                startActivity(new Intent(RegisterActivity.this, DoneListActivity.class));
                                finish();
                            }
                        });
            }).addOnFailureListener(e ->
                    Toast.makeText(RegisterActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show());
        }
    }
}