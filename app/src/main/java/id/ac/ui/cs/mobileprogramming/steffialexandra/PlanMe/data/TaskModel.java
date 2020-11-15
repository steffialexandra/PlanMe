package id.ac.ui.cs.mobileprogramming.steffialexandra.PlanMe.data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.Date;

@Entity(tableName = "task_model")
public class TaskModel implements Serializable {

    @PrimaryKey(autoGenerate = true)
    public int taskid;

    @ColumnInfo(name = "tasktitle")
    public String title;

    @ColumnInfo(name = "taskdate")
    public String taskdate;

    @ColumnInfo(name = "desc")
    public String desc;

    @ColumnInfo(name = "priority")
    public boolean priority;

    public int getTaskid() {
        return taskid;
    }

    public void setTaskid(int taskid) {
        this.taskid = taskid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTaskdate() {
        return taskdate;
    }

    public void setTaskdate(String taskdate) {
        this.taskdate = taskdate;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public boolean getPriority() {
        return priority;
    }

    public void setPriority(boolean priority) {
        this.priority = priority;
    }


   /* String title;
    Date datetask;
    String desc;
    boolean priority;

    public TaskModel() {
    }

    public TaskModel(String title, Date datetask, String desc, boolean priority) {
        this.title = title;
        this.datetask = datetask;
        this.desc = desc;
        this.priority = priority;
    }*/
}
