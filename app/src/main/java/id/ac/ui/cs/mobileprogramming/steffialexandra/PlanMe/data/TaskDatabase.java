package id.ac.ui.cs.mobileprogramming.steffialexandra.PlanMe.data;


import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {TaskModel.class}, version = 1)
public abstract  class TaskDatabase extends RoomDatabase {
    public abstract DaoAccess daoAccess();
}
