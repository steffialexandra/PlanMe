package id.ac.ui.cs.mobileprogramming.steffialexandra.PlanMe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import id.ac.ui.cs.mobileprogramming.steffialexandra.PlanMe.data.PlanMeDatabase;
import id.ac.ui.cs.mobileprogramming.steffialexandra.PlanMe.data.TaskModel;
import id.ac.ui.cs.mobileprogramming.steffialexandra.PlanMe.data.UserModel;

public class TaskActivity extends AppCompatActivity {

    Button btnAddNew, logoutBtn;
    ArrayList<TaskModel> taskList;
    RecyclerView tasksRecycler;
    TaskAdapter adapter;
    TextView uname;
    LinearLayoutManager linearLayoutManager;
    PlanMeDatabase database;
    UserModel currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task_activity);
        database = PlanMeDatabase.getInstance(this);
        currentUser = (UserModel) getIntent().getSerializableExtra("User");
        uname = findViewById(R.id.username);
        taskList = (ArrayList<TaskModel>) database.daoAccess().loadAllByUsername(currentUser.getUsername());
        uname.setText("Welcome, " + currentUser.getUsername());
        /*database.daoAccess().deleteAll();
        taskList.clear();*/

        if(taskList != null){
            tasksRecycler = findViewById(R.id.tasklist);
            linearLayoutManager = new LinearLayoutManager(this);
            tasksRecycler.setLayoutManager(linearLayoutManager);
            adapter = new TaskAdapter(TaskActivity.this, taskList, currentUser);
            tasksRecycler.setAdapter(adapter);
        }
        btnAddNew = findViewById(R.id.btnAddNew);
        btnAddNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(TaskActivity.this, NewTask.class);
                i.putExtra("User", currentUser);
                startActivity(i);
            }
        });

        logoutBtn = findViewById(R.id.logout);
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(TaskActivity.this, MainActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
                TaskActivity.this.finish();
            }
        });

    }

}