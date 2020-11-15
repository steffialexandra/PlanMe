package id.ac.ui.cs.mobileprogramming.steffialexandra.PlanMe;

import android.os.Bundle;
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

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Random;

import id.ac.ui.cs.mobileprogramming.steffialexandra.PlanMe.data.TaskDatabase;
import id.ac.ui.cs.mobileprogramming.steffialexandra.PlanMe.data.TaskModel;

public class NewTask extends AppCompatActivity {

        EditText newtitle, newdesc;
        CheckBox newpriority;
        LinearLayoutManager linearLayoutManager;
        DatePicker newdate;
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
            newpriority = findViewById(R.id.newpriority);
            saveButton = findViewById(R.id.saveButton);
            cancelButton = findViewById(R.id.cancelButton);
            taskList = (ArrayList<TaskModel>) database.daoAccess().getAll();
            tasks = findViewById(R.id.tasklist);
            linearLayoutManager = new LinearLayoutManager(this);
            tasks.setLayoutManager(linearLayoutManager);
            adapter = new TaskAdapter(NewTask.this, taskList);
            tasks.setAdapter(adapter);
            String date = newdate.toString();

            saveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   TaskModel newTask = new TaskModel();
                   newTask.setDesc(newdesc.getText().toString());
                   newTask.setTitle(newtitle.getText().toString());
                   newTask.setTaskdate(newdate.toString());
                   newTask.setPriority(newpriority.isChecked());
                   database.daoAccess().insertTask(newTask);
                   taskList.clear();
                   taskList.addAll(database.daoAccess().getAll());
                   adapter.notifyDataSetChanged();
                }
            });
    }
}
