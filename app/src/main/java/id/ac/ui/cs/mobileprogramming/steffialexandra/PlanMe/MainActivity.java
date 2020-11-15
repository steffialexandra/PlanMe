package id.ac.ui.cs.mobileprogramming.steffialexandra.PlanMe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import id.ac.ui.cs.mobileprogramming.steffialexandra.PlanMe.data.TaskDatabase;
import id.ac.ui.cs.mobileprogramming.steffialexandra.PlanMe.data.TaskModel;

public class MainActivity extends AppCompatActivity {

    Button btnAddNew;
    ArrayList<TaskModel> taskList;
    RecyclerView tasksRecycler;
    TaskAdapter adapter;
    LinearLayoutManager linearLayoutManager;
    TaskDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        database = TaskDatabase.getInstance(this);
        taskList = (ArrayList<TaskModel>) database.daoAccess().getAll();
        /*database.daoAccess().deleteAll();
        taskList.clear();*/

        if(taskList != null){
            Log.v("msg", "null gan");
            tasksRecycler = findViewById(R.id.tasklist);
            linearLayoutManager = new LinearLayoutManager(this);
            tasksRecycler.setLayoutManager(linearLayoutManager);
            adapter = new TaskAdapter(MainActivity.this, taskList);
            tasksRecycler.setAdapter(adapter);
        }
        btnAddNew = findViewById(R.id.btnAddNew);
        btnAddNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, NewTask.class));
            }
        });
    }


}