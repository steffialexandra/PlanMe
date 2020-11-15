package id.ac.ui.cs.mobileprogramming.steffialexandra.PlanMe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import id.ac.ui.cs.mobileprogramming.steffialexandra.PlanMe.data.TaskDatabase;
import id.ac.ui.cs.mobileprogramming.steffialexandra.PlanMe.data.TaskModel;

public class MainActivity extends AppCompatActivity {

    TextView titlepage;
    Button btnAddNew;
    TaskAdapter taskAdapter;
    ArrayList<TaskModel> taskList;
    RecyclerView tasks;
    TaskAdapter adapter;
    LinearLayoutManager linearLayoutManager;
    TaskDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        database = TaskDatabase.getInstance(this);
        taskList = (ArrayList<TaskModel>) database.daoAccess().getAll();
        btnAddNew = findViewById(R.id.btnAddNew);
        btnAddNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(MainActivity.this,NewTask.class);
                startActivity(a);
            }
        });




    }
}