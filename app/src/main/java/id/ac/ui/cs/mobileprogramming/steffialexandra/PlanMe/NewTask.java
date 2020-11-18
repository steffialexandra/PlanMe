package id.ac.ui.cs.mobileprogramming.steffialexandra.PlanMe;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

import id.ac.ui.cs.mobileprogramming.steffialexandra.PlanMe.data.PlanMeDatabase;
import id.ac.ui.cs.mobileprogramming.steffialexandra.PlanMe.data.TaskModel;
import id.ac.ui.cs.mobileprogramming.steffialexandra.PlanMe.data.UserModel;
import id.ac.ui.cs.mobileprogramming.steffialexandra.PlanMe.service.NotificationService;
import id.ac.ui.cs.mobileprogramming.steffialexandra.PlanMe.viewmodel.HistoryViewModel;
import id.ac.ui.cs.mobileprogramming.steffialexandra.PlanMe.viewmodel.TaskViewModel;

public class NewTask extends AppCompatActivity {


        TextView tasktitle, desc, taskdate, priority;
        EditText newtitle, newdesc;
        DatePicker newdate;
        CheckBox newpriority;
        Button saveButton, cancelButton;
        PlanMeDatabase database;
        TaskAdapter adapter;
        Integer number = new Random().nextInt();
        String taskid = Integer.toString(number);
        TaskViewModel taskViewModel;
        HistoryViewModel historyViewModel;
        ArrayList<TaskModel> taskList;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.new_task_layout);
            database = PlanMeDatabase.getInstance(this);
            taskViewModel = new TaskViewModel(database);
            historyViewModel = new HistoryViewModel(database);
            taskList = new ArrayList<TaskModel>();

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
            cancelButton = (Button) findViewById(R.id.cancelButton);

            saveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if( TextUtils.isEmpty(newtitle.getText()) || TextUtils.isEmpty(newdesc.getText())){
                        Toast.makeText(getApplicationContext(),"Please fill all of the fields!",Toast.LENGTH_SHORT).show(); }
                    else{
                        //creating plan
                        String date = getDateFromDatePicker(newdate);
                        UserModel currentUser = (UserModel) getIntent().getSerializableExtra("User");
                        taskViewModel.makeNewTask(newtitle.getText().toString(), newdesc.getText().toString(), date, newpriority.isChecked(), currentUser.getUsername());
                        historyViewModel.makeNewHistory(currentUser.getUsername(), newtitle.getText().toString(), Calendar.getInstance().getTime().toString(), 0);

                        adapter = new TaskAdapter(NewTask.this, taskList, currentUser);
                        taskList.clear();
                        taskList.addAll(taskViewModel.getAllTasks());
                        adapter.notifyDataSetChanged();

                        Intent newi = new Intent(NewTask.this, TaskActivity.class);
                        newi.putExtra("User", currentUser);
                        startActivity(newi);

                        // memanggil service AlarmManager
                        ContextCompat.startForegroundService(NewTask.this,new Intent(NewTask.this, NotificationService.class).putExtra("date", date).putExtra("taskid", taskid));
                        Toast.makeText(getApplicationContext(),"Plan Created!",Toast.LENGTH_SHORT).show();
                    }
                }
            });

            cancelButton.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                    startActivity(new Intent(NewTask.this, TaskActivity.class));
                }
            });

    }

    public static String getDateFromDatePicker(DatePicker datePicker){
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth();
        int year =  datePicker.getYear();

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        Format f = new SimpleDateFormat("dd-MM-yyyy");
        return f.format(calendar.getTime());
    }

}
