package id.ac.ui.cs.mobileprogramming.steffialexandra.PlanMe.contentprovider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.Room;

import id.ac.ui.cs.mobileprogramming.steffialexandra.PlanMe.data.DaoAccess;
import id.ac.ui.cs.mobileprogramming.steffialexandra.PlanMe.data.DaoAccess_Impl;
import id.ac.ui.cs.mobileprogramming.steffialexandra.PlanMe.data.TaskDatabase;

public class MyContentProvider extends ContentProvider {

    // Defines a handle to the Room database
    private TaskDatabase taskDatabase;

    // Defines a Data Access Object to perform the database operations
    private DaoAccess daoAccess;

    // Defines the database name
    private static final String DBNAME = "task_model";

    public boolean onCreate() {
        taskDatabase = Room.databaseBuilder(getContext(), TaskDatabase.class, DBNAME).build();
        daoAccess = taskDatabase.daoAccess();
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        return null;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
