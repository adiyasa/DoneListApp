package models;

import com.google.firebase.database.Exclude;

import java.io.Serializable;

public class DoneTaskModel implements Serializable {
    String task;
    @Exclude
    String Uid;

    public void setUid(String uid) {
        Uid = uid;
    }
    public String getUid() {
        return Uid;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public DoneTaskModel(String task) {
        this.task = task;
    }

    public DoneTaskModel() {
    }
}
