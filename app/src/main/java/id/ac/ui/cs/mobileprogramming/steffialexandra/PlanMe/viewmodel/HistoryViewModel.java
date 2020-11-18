package id.ac.ui.cs.mobileprogramming.steffialexandra.PlanMe.viewmodel;

import android.view.View;

import androidx.lifecycle.ViewModel;

import id.ac.ui.cs.mobileprogramming.steffialexandra.PlanMe.data.DaoAccess;
import id.ac.ui.cs.mobileprogramming.steffialexandra.PlanMe.data.HistoryModel;
import id.ac.ui.cs.mobileprogramming.steffialexandra.PlanMe.data.PlanMeDatabase;

public class HistoryViewModel extends ViewModel {
    private PlanMeDatabase database;

    private DaoAccess daoAccess ;

    public HistoryViewModel(PlanMeDatabase database){
        this.database = database;
        this.daoAccess = database.daoAccess();
    }

    public void makeNewHistory(String username, String task, String date, int i) {
        HistoryModel newHistory = new HistoryModel();
        newHistory.setType(i);
        newHistory.setUser(username);
        newHistory.setTask(task);
        newHistory.setDate(date);
        daoAccess.addHistory(newHistory);
    }
}
