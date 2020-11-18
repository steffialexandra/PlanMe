package id.ac.ui.cs.mobileprogramming.steffialexandra.PlanMe.viewmodel;

import android.content.Context;

import androidx.lifecycle.ViewModel;

import id.ac.ui.cs.mobileprogramming.steffialexandra.PlanMe.data.DaoAccess;
import id.ac.ui.cs.mobileprogramming.steffialexandra.PlanMe.data.PlanMeDatabase;
import id.ac.ui.cs.mobileprogramming.steffialexandra.PlanMe.data.UserModel;

public class UserViewModel extends ViewModel {
    private PlanMeDatabase database;

    private DaoAccess daoAccess ;

    public UserViewModel(PlanMeDatabase database){
        this.database = database;
        this.daoAccess = database.daoAccess();
    }

    public void addUser(String username, String password) {
        UserModel user = new UserModel(username, password);
        daoAccess.addUser(user);
    }

    public boolean checkAvailability(String username){
        if(daoAccess.getUserByUsername(username) != null){
            return false;
        }else{
            return true;
        }
    }

    public UserModel getUserFromDb(String uname, String pass) {
        return daoAccess.getUser(uname, pass);
    }


    public boolean getUnameAvailability(String toString) {
        if(daoAccess.getUserByUsername(toString) == null){
            return true;
        }else{
            return false;
        }
    }
}
