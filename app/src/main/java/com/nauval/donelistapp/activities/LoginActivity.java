package com.nauval.donelistapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.nauval.donelistapp.R;

import Dialog.LoadingDialog;

public class LoginActivity extends AppCompatActivity {
    FirebaseAuth mAuth;
    EditText etEmail,etPassword;
    String email, password;
    LoadingDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        etEmail = findViewById(R.id.et_email_login);
        etPassword = findViewById(R.id.et_password_login);
        dialog = new LoadingDialog(this);
    }

    public void login(View view) {
        dialog.startLoadingDialog();
        email = etEmail.getText().toString();
        password = etPassword.getText().toString();
        if(email.isEmpty()||password.isEmpty()){
            Toast.makeText(LoginActivity.this, "All field must be filled", Toast.LENGTH_SHORT).show();
        }
        else{
            mAuth.signInWithEmailAndPassword(email,password).addOnSuccessListener(authResult -> {
                startActivity(new Intent(LoginActivity.this,DoneListActivity.class));
                dialog.dismissDialog();
                finish();
            }).addOnFailureListener(e ->
                    Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show());
                    dialog.dismissDialog();
        }

    }
}