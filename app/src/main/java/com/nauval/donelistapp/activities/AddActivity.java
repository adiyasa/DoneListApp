package com.nauval.donelistapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.nauval.donelistapp.R;

import java.util.HashMap;

import DAO.DAODoneTask;
import models.DoneTaskModel;

public class AddActivity extends AppCompatActivity {
    FirebaseUser user;
    TextView tvName;
    String task;
    EditText etTask;
    DoneTaskModel modelEdit;
    Button butAddTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        user = FirebaseAuth.getInstance().getCurrentUser();
        tvName = findViewById(R.id.tv_name);
        tvName.setText(user.getDisplayName());
        etTask = findViewById(R.id.et_done_task);
        modelEdit = (DoneTaskModel) getIntent().getSerializableExtra("EDIT");
        if(modelEdit != null){
            etTask.setText(modelEdit.getTask());
            butAddTask = findViewById(R.id.but_add_task);
            butAddTask.setText(R.string.update);
        }
    }

    public void addDoneTask(View view) {
        task = etTask.getText().toString();
        if(task.isEmpty()){
            Toast.makeText(AddActivity.this, "Please insert Task", Toast.LENGTH_SHORT).show();
        }
        else {
            DoneTaskModel taskModel = new DoneTaskModel(task);
            DAODoneTask dao = new DAODoneTask();
            if (modelEdit == null) {
                dao.add(taskModel).addOnSuccessListener(unused -> {
                    Toast.makeText(AddActivity.this, "Add task success", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(AddActivity.this, DoneListActivity.class));
                    finish();
                }).addOnFailureListener(e -> Toast.makeText(AddActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show());
            }else {
                HashMap<String, Object> map = new HashMap<>();
                map.put("task",task);
                dao.update(modelEdit.getUid(),map).addOnSuccessListener(unused -> {
                    Toast.makeText(AddActivity.this, "Update task success", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(AddActivity.this, DoneListActivity.class));
                    finish();
                }).addOnFailureListener(e -> Toast.makeText(AddActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show());

            }
        }
    }
}