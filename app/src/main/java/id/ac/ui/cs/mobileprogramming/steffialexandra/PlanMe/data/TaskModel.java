package id.ac.ui.cs.mobileprogramming.steffialexandra.PlanMe.data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity
public class TaskModel {

    @PrimaryKey
    public int taskid;

    @ColumnInfo(name = "tasktitle")
    public String title;

    @ColumnInfo(name = "taskdate")
    public String taskdate;

    @ColumnInfo(name = "desc")
    public String desc;

    @ColumnInfo(name = "priority")
    public String priority;

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

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
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
