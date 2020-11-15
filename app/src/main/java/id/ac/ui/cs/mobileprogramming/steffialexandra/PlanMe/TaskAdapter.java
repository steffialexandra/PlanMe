package id.ac.ui.cs.mobileprogramming.steffialexandra.PlanMe;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import id.ac.ui.cs.mobileprogramming.steffialexandra.PlanMe.data.TaskDatabase;
import id.ac.ui.cs.mobileprogramming.steffialexandra.PlanMe.data.TaskModel;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.MyViewHolder> {

    String tasktitle, desc, taskdate;
    CheckBox priority;
    TaskDatabase database;

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tasktitle, desc, taskdate;
        CheckBox priority;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tasktitle = (TextView) itemView.findViewById(R.id.tasktitle);
            desc = (TextView) itemView.findViewById(R.id.desc);
            taskdate = (TextView) itemView.findViewById(R.id.taskdate);
            priority = itemView.findViewById(R.id.priority);


        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.tasks, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, int position) {
        database = TaskDatabase.getInstance(context);
        myViewHolder.tasktitle.setText(taskList.get(position).getTitle());
        myViewHolder.taskdate.setText(taskList.get(position).getTaskdate());
        myViewHolder.desc.setText(taskList.get(position).getDesc());
        myViewHolder.priority.setChecked(taskList.get(position).getPriority());
        myViewHolder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                TaskModel d = taskList.get(myViewHolder.getAdapterPosition());
                final int taskId = d.getTaskid();
                Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.viewtask);
                int width = WindowManager.LayoutParams.MATCH_PARENT;
                int height = WindowManager.LayoutParams.MATCH_PARENT;
                dialog.getWindow().setLayout(width,height);
                dialog.show();

                Button deleteBtn = dialog.findViewById(R.id.deleteButton);
                deleteBtn.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v){
                        AlertDialog.Builder alert = new AlertDialog.Builder(context);
                        alert.setTitle("Delete");
                        alert.setMessage("Are you sure you want to delete?");
                        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                TaskModel d = taskList.get(myViewHolder.getAdapterPosition());
                                database.daoAccess().deleteItem(d.taskid);
                                int position = myViewHolder.getAdapterPosition();
                                taskList.remove(position);
                                notifyItemRemoved(position);
                                notifyItemRangeChanged(position, taskList.size());
                                dialog.dismiss();
                                dialog.dismiss();
                            }
                        });

                        alert.setNegativeButton("No", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                dialog.dismiss();
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

    public TaskAdapter(Context c, ArrayList<TaskModel> p) {
        context = c;
        taskList = p;
    }
}
