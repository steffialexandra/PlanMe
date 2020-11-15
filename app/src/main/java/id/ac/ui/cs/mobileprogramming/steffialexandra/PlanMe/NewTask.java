package id.ac.ui.cs.mobileprogramming.steffialexandra.PlanMe;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Random;

import id.ac.ui.cs.mobileprogramming.steffialexandra.PlanMe.data.TaskDatabase;
import id.ac.ui.cs.mobileprogramming.steffialexandra.PlanMe.data.TaskModel;

public class NewTask extends AppCompatActivity {

        EditText newtitle, newdesc;
        DatePicker newdate;
        CheckBox newpriority;
        LinearLayoutManager linearLayoutManager;
        TextView tasktitle, desc, taskdate, priority;
        Button saveButton, cancelButton;
        TaskDatabase database;
        RecyclerView tasks;
        TaskAdapter adapter;
        ArrayList<TaskModel> taskList;
        Integer number = new Random().nextInt();
        String taskid = Integer.toString(number);

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.new_task_layout);
            tasktitle = findViewById(R.id.tasktitle);
            desc = findViewById(R.id.desc);
            taskdate = findViewById(R.id.taskdate);
            priority = findViewById(R.id.priority);
            newtitle = findViewById(R.id.newtitle);
            newdesc = findViewById(R.id.newdesc);
            newdate = findViewById(R.id.newdate);
            newdate.setMinDate(System.currentTimeMillis() - 1000);
            newpriority = findViewById(R.id.newpriority);
            saveButton = (Button) findViewById(R.id.saveButton);
            database = TaskDatabase.getInstance(this);
            cancelButton = (Button) findViewById(R.id.cancelButton);
            taskList = new ArrayList<TaskModel>();
            String date = newdate.toString();

            saveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   TaskModel newTask = new TaskModel();
                   newTask.setDesc(newdesc.getText().toString());
                   newTask.setTitle(newtitle.getText().toString());
                   newTask.setTaskdate(newdate.toString()); //problem 

                   newTask.setPriority(newpriority.isChecked());
                   database.daoAccess().insertTask(newTask);
                   adapter = new TaskAdapter(NewTask.this, taskList);
                   taskList.clear();
                   taskList.addAll(database.daoAccess().getAll());
                   adapter.notifyDataSetChanged();
                   Log.v("msg","clicked!");
                }
            });

            cancelButton.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                    startActivity(new Intent(NewTask.this, MainActivity.class));
                }
            });

    }
}
