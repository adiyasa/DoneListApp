package com.nauval.donelistapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.nauval.donelistapp.R;

import java.util.ArrayList;

import DAO.DAODoneTask;
import Dialog.LoadingDialog;
import adapters.DoneListAdapter;
import models.DoneTaskModel;

public class DoneListActivity extends AppCompatActivity {
    private FirebaseUser user;
    private TextView tvName;
    private DoneListAdapter adapter;
    private DAODoneTask dao;
    private LoadingDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dialog = new LoadingDialog(this);
        setContentView(R.layout.activity_done_list);
        user = FirebaseAuth.getInstance().getCurrentUser();
        tvName = findViewById(R.id.tv_name);
        RecyclerView recyclerView = findViewById(R.id.rv_done_list);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new DoneListAdapter(this);
        recyclerView.setAdapter(adapter);
        dao = new DAODoneTask();
        loadData();
    }

    @Override
    protected void onStart() {
        super.onStart();
        tvName.setText(String.format("Hi %s", user.getDisplayName()));
    }

    private void loadData(){
        dialog.startLoadingDialog();
        dao.get().addListenerForSingleValueEvent(new ValueEventListener() {
            final ArrayList<DoneTaskModel> models = new ArrayList<>();
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    DoneTaskModel model = dataSnapshot.getValue(DoneTaskModel.class);
                    model.setUid(dataSnapshot.getKey());
                    models.add(model);
                }
                adapter.setItem(models);
                adapter.notifyDataSetChanged();
                dialog.dismissDialog();
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(DoneListActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                dialog.dismissDialog();
            }
        });

    }
    public void addActivity(View view) {
        startActivity(new Intent(DoneListActivity.this,AddActivity.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        if (item.getItemId() == R.id.menu_logout) {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(DoneListActivity.this, MainActivity.class));
            finish();
            return false;
        }
        return super.onOptionsItemSelected(item);
    }


}