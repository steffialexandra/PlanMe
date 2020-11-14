package id.ac.ui.cs.mobileprogramming.steffialexandra.PlanMe;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import id.ac.ui.cs.mobileprogramming.steffialexandra.PlanMe.data.TaskModel;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.MyViewHolder> {

    String tasktitle, desc, taskdate, priority;

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tasktitle, desc, taskdate, priority;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tasktitle = (TextView) itemView.findViewById(R.id.tasktitle);
            desc = (TextView) itemView.findViewById(R.id.desc);
            taskdate = (TextView) itemView.findViewById(R.id.taskdate);
            priority = (TextView) itemView.findViewById(R.id.priority);

        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.tasks, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int position) {
        myViewHolder.tasktitle.setText(taskModel.get(position).getTitle());
        myViewHolder.taskdate.setText(taskModel.get(position).getTaskdate());
        myViewHolder.desc.setText(taskModel.get(position).getDesc());
        myViewHolder.priority.setText(taskModel.get(position).getPriority());
    }

    @Override
    public int getItemCount() {
        return taskModel.size();
    }


    Context context;
    ArrayList<TaskModel> taskModel;

    public TaskAdapter(Context c, ArrayList<TaskModel> p) {
        context = c;
        taskModel = p;
    }
}
