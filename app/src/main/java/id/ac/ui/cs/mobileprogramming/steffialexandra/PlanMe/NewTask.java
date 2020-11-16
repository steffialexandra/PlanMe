package id.ac.ui.cs.mobileprogramming.steffialexandra.PlanMe;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
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

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

import id.ac.ui.cs.mobileprogramming.steffialexandra.PlanMe.data.HistoryModel;
import id.ac.ui.cs.mobileprogramming.steffialexandra.PlanMe.data.PlanMeDatabase;
import id.ac.ui.cs.mobileprogramming.steffialexandra.PlanMe.data.TaskModel;
import id.ac.ui.cs.mobileprogramming.steffialexandra.PlanMe.data.UserModel;
import id.ac.ui.cs.mobileprogramming.steffialexandra.PlanMe.service.CalendarService;
import id.ac.ui.cs.mobileprogramming.steffialexandra.PlanMe.service.NotificationService;

public class NewTask extends AppCompatActivity {


        TextView tasktitle, desc, taskdate, priority;
        EditText newtitle, newdesc;
        DatePicker newdate;
        CheckBox newpriority;
        Button saveButton, cancelButton;
        PlanMeDatabase database;
        TaskAdapter adapter;
        ArrayList<TaskModel> taskList;
        Integer number = new Random().nextInt();
        String taskid = Integer.toString(number);

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.new_task_layout);

            database = PlanMeDatabase.getInstance(this);
            taskList = new ArrayList<TaskModel>();

            //create notification
            createNotification();

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
                        TaskModel newTask = new TaskModel();
                        HistoryModel newHistory = new HistoryModel();
                        newTask.setDesc(newdesc.getText().toString());
                        newTask.setTitle(newtitle.getText().toString());
                        String date = getDateFromDatePicker(newdate);
                        newTask.setTaskdate(date);
                        newTask.setPriority(newpriority.isChecked());
                        UserModel currentUser = (UserModel) getIntent().getSerializableExtra("User");
                        newHistory.setUser(currentUser.getUsername());
                        newTask.setCreator(currentUser.getUsername());
                        newHistory.setTask(newTask.getTitle());
                        newHistory.setType(0);
                        database.daoAccess().insertTask(newTask);
                        adapter = new TaskAdapter(NewTask.this, taskList, currentUser);
                        taskList.clear();
                        taskList.addAll(database.daoAccess().getAllTasks());
                        adapter.notifyDataSetChanged();
                        Intent newi = new Intent(NewTask.this, TaskActivity.class);
                        newi.putExtra("User", currentUser);
                        startActivity(newi);
                        Toast.makeText(getApplicationContext(),"Plan Created!",Toast.LENGTH_SHORT).show();
                        // memanggil service untuk CalendarProvider dan AlarmManager
                        startService(new Intent(NewTask.this, NotificationService.class).putExtra("date", date).putExtra("taskid", taskid));
                        startService(new Intent(NewTask.this, CalendarService.class).putExtra("date", date).putExtra("newtitle", newtitle.getText().toString()).putExtra("newdesc", newdesc.getText().toString()));
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

    private void createNotification(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            CharSequence name = "Plan Me";
            String description = "Reminder for PlanMe";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("PlanMe", name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
