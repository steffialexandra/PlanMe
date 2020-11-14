package id.ac.ui.cs.mobileprogramming.steffialexandra.PlanMe.data;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {TaskModel.class}, version = 1, exportSchema = false)
public abstract  class TaskDatabase extends RoomDatabase {
    private static TaskDatabase database;
    private static String DATABASE_NAME = "database";
    public synchronized static TaskDatabase getInstance(Context context){
        if(database == null){
            database = Room.databaseBuilder(context.getApplicationContext()
            ,TaskDatabase.class, DATABASE_NAME)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return database;
    }

    public abstract DaoAccess daoAccess();
}
