package prot3ct.workit.views.my_tasks;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import prot3ct.workit.R;
import prot3ct.workit.view_models.AvailableTasksListViewModel;
import prot3ct.workit.view_models.MyTasksListViewModel;
import prot3ct.workit.views.edit_task.EditTaskActivity;
import prot3ct.workit.views.list_task_requests.ListTaskRequestsActivity;
import prot3ct.workit.views.my_tasks.base.MyTasksContract;

public class MyTasksAdapter extends RecyclerView.Adapter<MyTasksAdapter.TaskViewHolder> {
    private MyTasksContract.Presenter presenter;
    private List<MyTasksListViewModel> tasks;
    private List<MyTasksListViewModel> allTasks = new ArrayList<MyTasksListViewModel>();
    private Context context;

    MyTasksAdapter(List<MyTasksListViewModel> tasks, Context context, MyTasksContract.Presenter presenter) {
        this.tasks = tasks;
        this.allTasks.addAll(tasks);
        this.context = context;
        this.presenter = presenter;
    }

    @Override
    public TaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_task_my_tasks, parent, false);
        TaskViewHolder pvh = new TaskViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(final TaskViewHolder holder, final int position) {
        if (!tasks.get(position).hasPendingRequest()) {
            holder.taskRequestersButton.setVisibility(View.GONE);
        }

        DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm", Locale.ENGLISH);
        Date date = null;
        try {
            date = format.parse(tasks.get(position).getStartDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Date currentDate = Calendar.getInstance().getTime();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        Calendar currentCalendar = Calendar.getInstance();
        currentCalendar.setTime(currentDate);

        if (currentDate.getDay() == date.getDay()) {
            holder.startTime.setText("Today");
            if (date.getHours() != currentDate.getHours()) {
                if (calendar.get(Calendar.HOUR_OF_DAY) - currentCalendar.get(Calendar.HOUR_OF_DAY) <= 0) {
                    holder.timeLeft.setText("Expired");
                }
                else {
                    holder.timeLeft.setText(calendar.get(Calendar.HOUR_OF_DAY) - currentCalendar.get(Calendar.HOUR_OF_DAY) + " hours left until start");
                }
            }
            else {
                holder.timeLeft.setText(calendar.get(Calendar.MINUTE) - currentCalendar.get(Calendar.MINUTE) + " minutes left until start");
            }
        }
        else {
            holder.startTime.setText(getOrdinal(calendar.get(Calendar.DAY_OF_MONTH)) + " " + getMonthForInt(calendar.get(Calendar.MONTH)));
        }
        holder.taskTitle.setText(tasks.get(position).getTitle());
        holder.taskCreator.setText("for me");

        holder.editTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, EditTaskActivity.class);
                intent.putExtra("taskId", tasks.get(position).getTaskId());
                context.startActivity(intent);
            }
        });

        holder.taskRequestersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ListTaskRequestsActivity.class);
                intent.putExtra("taskId", tasks.get(position).getTaskId());
                context.startActivity(intent);
            }
        });

        holder.deleteTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                presenter.deleteTask(tasks.get(position).getTaskId());
                                tasks.remove(position);
                                notifyDataSetChanged();
                                break;
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Are you sure you want to delete this task?").setPositiveButton("Yes", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    private String getMonthForInt(int num) {
        String month = "wrong";
        DateFormatSymbols dfs = new DateFormatSymbols();
        String[] months = dfs.getMonths();
        if (num >= 0 && num <= 11 ) {
            month = months[num];
        }
        return month;
    }

    private String getOrdinal(int i) {
        String[] sufixes = new String[] { "th", "st", "nd", "rd", "th", "th", "th", "th", "th", "th" };
        switch (i % 100) {
            case 11:
            case 12:
            case 13:
                return i + "th";
            default:
                return i + sufixes[i % 10];
        }
    }

    public void filter(String text) {
        tasks.clear();
        if(text.isEmpty()) {
            tasks.addAll(allTasks);
        }
        else {
            text = text.toLowerCase();
            for (MyTasksListViewModel task : allTasks) {
                if (task.getTitle().toLowerCase().contains(text)) {
                    tasks.add(task);
                }
            }
        }
        notifyDataSetChanged();
    }


    public static class TaskViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView startTime;
        TextView timeLeft;
        TextView taskTitle;
        TextView taskCreator;
        Button editTaskButton;
        Button taskRequestersButton;
        Button deleteTaskButton;

        TaskViewHolder(View itemView) {
            super(itemView);
            cv = itemView.findViewById(R.id.id_single_task_from_my_tasks);
            startTime = itemView.findViewById(R.id.id_task_start_time);
            timeLeft = itemView.findViewById(R.id.id_task_time_left);
            taskTitle = itemView.findViewById(R.id.id_task_title);
            taskCreator = itemView.findViewById(R.id.id_task_creator);
            editTaskButton = itemView.findViewById(R.id.id_edit_task_button);
            taskRequestersButton = itemView.findViewById(R.id.id_task_requests_button);
            deleteTaskButton = itemView.findViewById(R.id.id_delete_task_button);
        }
    }
}
