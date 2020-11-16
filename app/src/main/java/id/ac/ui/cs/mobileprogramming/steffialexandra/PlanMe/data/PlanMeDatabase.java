package id.ac.ui.cs.mobileprogramming.steffialexandra.PlanMe.data;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {TaskModel.class, UserModel.class, HistoryModel.class}, version = 1, exportSchema = false)
public abstract  class PlanMeDatabase extends RoomDatabase {
    private static PlanMeDatabase database;
    private static String DATABASE_NAME = "database";
    public synchronized static PlanMeDatabase getInstance(Context context){
        if(database == null){
            database = Room.databaseBuilder(context.getApplicationContext()
            , PlanMeDatabase.class, DATABASE_NAME)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return database;
    }

    public abstract DaoAccess daoAccess();
}
