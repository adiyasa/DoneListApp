package DAO;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.HashMap;
import java.util.Objects;

import models.DoneTaskModel;

public class DAODoneTask {
    private final DatabaseReference mDatabase;

    public DAODoneTask(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        mDatabase = db.getReference("Task").child(Objects.requireNonNull(user).getUid());
    }
    public Task<Void> add(DoneTaskModel taskModel){
        return mDatabase.push().setValue(taskModel);
    }
    public Task<Void> update(String key, HashMap<String, Object> map){
        return mDatabase.child(key).updateChildren(map);
    }
    public Task<Void> remove(String key){
        return mDatabase.child(key).removeValue();
    }
    public Query get(){
        return mDatabase.orderByKey();
    }
}
