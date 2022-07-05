package prot3ct.workit.views.list_tasks;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Period;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import prot3ct.workit.R;
import prot3ct.workit.view_models.AvailableTasksListViewModel;
import prot3ct.workit.views.task_details.TaskDetailsActivity;

public class ListTasksAdapter extends RecyclerView.Adapter<ListTasksAdapter.TaskViewHolder> {
    List<AvailableTasksListViewModel> tasks;
    List<AvailableTasksListViewModel> allTasks = new ArrayList<AvailableTasksListViewModel>();
    private Context context;

    ListTasksAdapter(List<AvailableTasksListViewModel> tasks, Context context){
        this.tasks = tasks;
        this.allTasks.addAll(tasks);
        this.context = context;
    }

    @Override
    public TaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_task_list_tasks, parent, false);
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

        long diffInMillies = Math.abs(currentDate.getTime() - date.getTime());
        long diffHours = TimeUnit.HOURS.convert(diffInMillies, TimeUnit.MILLISECONDS);
        long diffMinutes = TimeUnit.MINUTES.convert(diffInMillies, TimeUnit.MILLISECONDS);

        if (diffHours <= 24) {
            holder.startTime.setText("Today");
            if (diffHours != 0) {
                holder.timeLeft.setText(diffHours + " hours left to respond");
            }
            else {
                holder.timeLeft.setText(diffMinutes + " minutes left to respond");
            }
        }
        else {
            holder.startTime.setText(getOrdinal(calendar.get(Calendar.DAY_OF_MONTH)) + " " + getMonthForInt(calendar.get(Calendar.MONTH)));
        }
        holder.taskTitle.setText(tasks.get(position).getTitle());
        holder.taskCreator.setText("for " + tasks.get(position).getFullName());
        holder.supervisorRating.setText(tasks.get(position).getSupervisorRating()+"");
        holder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, TaskDetailsActivity.class);
                intent.putExtra("taskId", tasks.get(position).getTaskId());
                context.startActivity(intent);
            }
        });
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
        TextView supervisorRating;

        TaskViewHolder(View itemView) {
            super(itemView);
            cv = itemView.findViewById(R.id.id_single_task_from_list_tasks);
            startTime = itemView.findViewById(R.id.id_task_start_time);
            timeLeft = itemView.findViewById(R.id.id_task_time_left);
            taskTitle = itemView.findViewById(R.id.id_task_title);
            taskCreator = itemView.findViewById(R.id.id_task_creator);
            supervisorRating = itemView.findViewById(R.id.id_task_supervisor_rating_text_view);
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
