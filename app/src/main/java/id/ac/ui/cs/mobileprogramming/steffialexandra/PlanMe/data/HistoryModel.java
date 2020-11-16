package id.ac.ui.cs.mobileprogramming.steffialexandra.PlanMe.data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.Date;

@Entity(tableName = "history_model")
public class HistoryModel implements Serializable {

    @PrimaryKey(autoGenerate = true)
    public int historyid;

    @ColumnInfo(name = "userModel")
    public UserModel user;

    @ColumnInfo(name = "taskModel")
    public TaskModel task;

    @ColumnInfo(name = "date")
    public Date date;

    public int getHistoryid() {
        return historyid;
    }

    public void setHistoryid(int historyid) {
        this.historyid = historyid;
    }

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }

    public TaskModel getTask() {
        return task;
    }

    public void setTask(TaskModel task) {
        this.task = task;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
