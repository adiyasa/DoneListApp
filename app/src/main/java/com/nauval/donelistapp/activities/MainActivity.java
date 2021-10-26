package com.nauval.donelistapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.nauval.donelistapp.R;

public class MainActivity extends AppCompatActivity {
    FirebaseUser user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }
    @Override
    protected void onStart() {
        super.onStart();
        user = FirebaseAuth.getInstance().getCurrentUser();
        if(user != null){
            startActivity(new Intent(MainActivity.this,DoneListActivity.class));
            finish();
        }
    }

    public void toRegister(View view) {
        startActivity(new Intent(MainActivity.this,RegisterActivity.class));
    }

    public void toLogin(View view) {
        startActivity(new Intent(MainActivity.this,LoginActivity.class));
    }
}