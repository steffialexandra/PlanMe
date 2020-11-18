package id.ac.ui.cs.mobileprogramming.steffialexandra.PlanMe;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Calendar;

import id.ac.ui.cs.mobileprogramming.steffialexandra.PlanMe.data.PlanMeDatabase;
import id.ac.ui.cs.mobileprogramming.steffialexandra.PlanMe.data.TaskModel;
import id.ac.ui.cs.mobileprogramming.steffialexandra.PlanMe.data.UserModel;
import id.ac.ui.cs.mobileprogramming.steffialexandra.PlanMe.service.NotificationService;
import id.ac.ui.cs.mobileprogramming.steffialexandra.PlanMe.viewmodel.HistoryViewModel;
import id.ac.ui.cs.mobileprogramming.steffialexandra.PlanMe.viewmodel.TaskViewModel;

import static android.content.Context.ALARM_SERVICE;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.MyViewHolder> {

    PlanMeDatabase database;
    TaskViewModel taskViewModel;
    HistoryViewModel historyViewModel;

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tasktitle, desc, taskdate;
        CheckBox priority;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tasktitle = (TextView) itemView.findViewById(R.id.tasktitle);
            desc = (TextView) itemView.findViewById(R.id.desc);
            taskdate = (TextView) itemView.findViewById(R.id.taskdate);
            priority = (CheckBox)itemView.findViewById(R.id.priority);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.tasks, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, final int position) {

        PlanMeDatabase database = PlanMeDatabase.getInstance(context);
        taskViewModel = new TaskViewModel(database);
        historyViewModel = new HistoryViewModel(database);

        myViewHolder.tasktitle.setText(taskList.get(position).getTitle());
        myViewHolder.taskdate.setText(taskList.get(position).getTaskdate());
        myViewHolder.priority.setChecked(taskList.get(position).getPriority());
        myViewHolder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                TaskModel d = taskList.get(myViewHolder.getAdapterPosition());
                final int taskId = d.getTaskid();
                final Dialog dialog = new Dialog(context,android.R.style.Theme_Black_NoTitleBar_Fullscreen);
                dialog.setContentView(R.layout.viewtask);
                int width = WindowManager.LayoutParams.MATCH_PARENT;
                int height = WindowManager.LayoutParams.MATCH_PARENT;
                dialog.getWindow().setLayout(width,height);
                dialog.show();
                final TextView tasktitle = dialog.findViewById(R.id.newtitle);
                tasktitle.setText(taskList.get(position).getTitle());
                TextView desc = dialog.findViewById(R.id.newdesc);
                desc.setText(taskList.get(position).getDesc());
                TextView date = dialog.findViewById(R.id.newdate);
                date.setText(d.getTaskdate());
                CheckBox prior = dialog.findViewById(R.id.newpriority);
                prior.setChecked(taskList.get(position).getPriority());
                prior.setEnabled(false);
                Button deleteBtn = dialog.findViewById(R.id.deleteButton);
                deleteBtn.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v){
                        AlertDialog.Builder alert = new AlertDialog.Builder(context);
                        alert.setTitle(R.string.deletedialogtitle);
                        alert.setMessage(R.string.deletedialogmsg);
                        alert.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialogs, int which) {
                                TaskModel d = taskList.get(myViewHolder.getAdapterPosition());
                                taskViewModel.deleteTask(d.taskid);
                                int position = myViewHolder.getAdapterPosition();
                                historyViewModel.makeNewHistory(userModel.getUsername(), tasktitle.toString(), Calendar.getInstance().getTime().toString(),1);

                                taskList.remove(position);
                                notifyItemRemoved(position);
                                notifyItemRangeChanged(position, taskList.size());
                                dialogs.dismiss();
                                dialog.dismiss();

                                //membatalkan alarmmanager untuk objek yang ingin dihapus
                                Intent intent = new Intent(context, NotificationService.class);
                                PendingIntent pi = PendingIntent.getBroadcast(context, Integer.valueOf(taskId), intent, 0);
                                AlarmManager am = (AlarmManager) context.getSystemService(ALARM_SERVICE);
                                am.cancel(pi);
                            }
                        });

                        alert.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialogs, int which) {
                                dialogs.dismiss();
                            }
                        });

                        alert.show();
                    }
                });

            }
        });
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    Context context;
    ArrayList<TaskModel> taskList;
    UserModel userModel;

    public TaskAdapter(Context c, ArrayList<TaskModel> p, UserModel u) {
        context = c;
        taskList = p;
        userModel = u;
    }
}
