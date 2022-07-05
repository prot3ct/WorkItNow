package prot3ct.workit.views.assigned_tasks;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
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
import prot3ct.workit.view_models.AssignedTasksListViewModel;
import prot3ct.workit.view_models.AvailableTasksListViewModel;
import prot3ct.workit.views.assigned_tasks.base.AssignedTasksContract;
import prot3ct.workit.views.task_details.TaskDetailsActivity;

public class AssignedTasksAdapter extends RecyclerView.Adapter<AssignedTasksAdapter.TaskViewHolder> {
    private AssignedTasksContract.Presenter presenter;
    List<AssignedTasksListViewModel> tasks;
    List<AssignedTasksListViewModel> allTasks = new ArrayList<AssignedTasksListViewModel>();
    private Context context;

    AssignedTasksAdapter(List<AssignedTasksListViewModel> tasks, Context context, AssignedTasksContract.Presenter presenter){
        this.tasks = tasks;
        allTasks.addAll(tasks);
        this.context = context;
        this.presenter = presenter;
    }

    @Override
    public TaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_assigned_task, parent, false);
        TaskViewHolder pvh = new TaskViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(TaskViewHolder holder, final int position) {
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

        if (currentCalendar.get(Calendar.DAY_OF_MONTH) == calendar.get(Calendar.DAY_OF_MONTH)) {
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
        holder.taskCreator.setText("for " + tasks.get(position).getFullName());
        holder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, TaskDetailsActivity.class);
                intent.putExtra("taskId", tasks.get(position).getTaskId());
                context.startActivity(intent);
            }
        });

        holder.cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.removeAssignedUser(tasks.get(position).getTaskId());
                tasks.remove(position);
                notifyDataSetChanged();
            }
        });
    }

    public void filter(String text) {
        tasks.clear();
        if(text.isEmpty()) {
            tasks.addAll(allTasks);
        }
        else {
            text = text.toLowerCase();
            for (AssignedTasksListViewModel task : allTasks) {
                if (task.getTitle().toLowerCase().contains(text)) {
                    tasks.add(task);
                }
            }
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public static class TaskViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView startTime;
        TextView timeLeft;
        TextView taskTitle;
        TextView taskCreator;
        Button cancelButton;

        TaskViewHolder(View itemView) {
            super(itemView);
            cv = itemView.findViewById(R.id.id_single_task_from_list_tasks);
            startTime = itemView.findViewById(R.id.id_task_start_time);
            taskTitle = itemView.findViewById(R.id.id_task_title);
            taskCreator = itemView.findViewById(R.id.id_task_creator);
            timeLeft = itemView.findViewById(R.id.id_task_time_left);
            cancelButton = itemView.findViewById(R.id.id_cancel_task_assignment_button);
        }
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
}
