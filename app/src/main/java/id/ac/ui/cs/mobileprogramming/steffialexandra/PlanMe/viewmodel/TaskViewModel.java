package id.ac.ui.cs.mobileprogramming.steffialexandra.PlanMe.viewmodel;

import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.Collection;

import id.ac.ui.cs.mobileprogramming.steffialexandra.PlanMe.data.DaoAccess;
import id.ac.ui.cs.mobileprogramming.steffialexandra.PlanMe.data.PlanMeDatabase;
import id.ac.ui.cs.mobileprogramming.steffialexandra.PlanMe.data.TaskModel;

public class TaskViewModel extends ViewModel {
    private PlanMeDatabase database;

    private DaoAccess daoAccess ;

    public TaskViewModel(PlanMeDatabase database){
        this.database = database;
        this.daoAccess = database.daoAccess();
    }

    public void makeNewTask(String title, String desc, String date, boolean checked, String username) {
        TaskModel newTask = new TaskModel();
        newTask.setTitle(title);
        newTask.setDesc(desc);
        newTask.setTaskdate(date);
        newTask.setPriority(checked);
        newTask.setCreator(username);
        daoAccess.insertTask(newTask);
    }

    public Collection<? extends TaskModel> getAllTasks() {
        return daoAccess.getAllTasks();
    }

    public ArrayList<TaskModel> getAllTasksByUsername(String username) {
        return (ArrayList<TaskModel>) daoAccess.loadAllByUsername(username);
    }

    public void deleteTask(int taskid) {
        daoAccess.deleteItem(taskid);
    }
}
