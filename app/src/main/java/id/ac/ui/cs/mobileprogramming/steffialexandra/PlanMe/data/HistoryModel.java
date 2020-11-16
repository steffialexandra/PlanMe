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
    public String user;

    @ColumnInfo(name = "taskModel")
    public String task;

    @ColumnInfo(name = "date")
    public String date;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @ColumnInfo(name = "type")
    public int type;

    public int getHistoryid() {
        return historyid;
    }

    public void setHistoryid(int historyid) {
        this.historyid = historyid;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
