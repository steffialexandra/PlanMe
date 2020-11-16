package id.ac.ui.cs.mobileprogramming.steffialexandra.PlanMe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

import id.ac.ui.cs.mobileprogramming.steffialexandra.PlanMe.data.PlanMeDatabase;
import id.ac.ui.cs.mobileprogramming.steffialexandra.PlanMe.data.TaskModel;

public class MainActivity extends AppCompatActivity {

    Button btnAddNew;
    ArrayList<TaskModel> taskList;
    RecyclerView tasksRecycler;
    TaskAdapter adapter;
    LinearLayoutManager linearLayoutManager;
    PlanMeDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        database = PlanMeDatabase.getInstance(this);
        taskList = (ArrayList<TaskModel>) database.daoAccess().getAllTasks();
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