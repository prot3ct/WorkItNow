package prot3ct.workit.views.completed_tasks;


import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;


import org.w3c.dom.Text;

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
import prot3ct.workit.view_models.CompletedTasksListViewModel;
import prot3ct.workit.views.assigned_tasks.AssignedTasksAdapter;
import prot3ct.workit.views.completed_tasks.base.CompletedTasksContract;
import prot3ct.workit.views.task_details.TaskDetailsActivity;

public class CompletedTasksAdapter extends RecyclerView.Adapter<CompletedTasksAdapter.TaskViewHolder> {
    private List<CompletedTasksListViewModel> tasks;
    private List<CompletedTasksListViewModel> allTasks = new ArrayList<CompletedTasksListViewModel>();
    private Context context;
    private CompletedTasksContract.View view;
    private int loggedInUserId;

    CompletedTasksAdapter(List<CompletedTasksListViewModel> tasks, Context context, CompletedTasksContract.View view){
        this.tasks = tasks;
        this.allTasks.addAll(this.tasks);
        this.context = context;
        this.view = view;
        this.loggedInUserId = view.getLoggedInUserId();
    }

    @Override
    public CompletedTasksAdapter.TaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_completed_task, parent, false);
        CompletedTasksAdapter.TaskViewHolder pvh = new CompletedTasksAdapter.TaskViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(final CompletedTasksAdapter.TaskViewHolder holder, int position) {
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
        }
        else {
            holder.startTime.setText(getOrdinal(calendar.get(Calendar.DAY_OF_MONTH)) + " " + getMonthForInt(calendar.get(Calendar.MONTH)));
        }
        holder.taskTitle.setText(tasks.get(position).getTitle());
        holder.taskCreator.setText("Created by: " + tasks.get(position).getSupervisorFullName());
        holder.taskTasker.setText("Assigned to: " + tasks.get(position).getTaskerFullName());

        if(tasks.get(position).getTaskerId() == loggedInUserId && tasks.get(position).getHasTaskerGivenrating()) {
            holder.rateButton.setVisibility(View.GONE);
            holder.ratingResult.setText("You have already rated " + tasks.get(position).getSupervisorFullName());
        }

        else if(tasks.get(position).getSupervisorId() == loggedInUserId && tasks.get(position).getHasSupervisorGivenRating()) {
            holder.rateButton.setVisibility(View.GONE);
            holder.ratingResult.setText("You have already rated " + tasks.get(position).getTaskerFullName());
        }
        else {
            holder.rateButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    view.updateSelectedInfo(tasks.get(holder.getAbsoluteAdapterPosition()).getTaskId(), tasks.get(holder.getLayoutPosition()).getSupervisorId(), tasks.get(position).getTaskerId());
                    view.showDialog();
                    holder.rateButton.setVisibility(View.GONE);
                    if(tasks.get(holder.getAbsoluteAdapterPosition()).getTaskerId() == loggedInUserId) {
                        holder.ratingResult.setText("You have already rated " + tasks.get(holder.getAbsoluteAdapterPosition()).getSupervisorFullName());
                    }
                    else {
                        holder.ratingResult.setText("You have already rated " + tasks.get(holder.getAbsoluteAdapterPosition()).getTaskerFullName());
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public void hideButton(int start) {

    }

    public static class TaskViewHolder extends RecyclerView.ViewHolder {
        TextView startTime;
        TextView taskTitle;
        TextView taskCreator;
        TextView taskTasker;
        Button rateButton;
        TextView ratingResult;

        TaskViewHolder(View itemView) {
            super(itemView);
            startTime = itemView.findViewById(R.id.id_task_start_time);
            taskTitle = itemView.findViewById(R.id.id_task_title);
            taskCreator = itemView.findViewById(R.id.id_task_supervisor);
            taskTasker = itemView.findViewById(R.id.id_task_tasker);
            rateButton = itemView.findViewById(R.id.id_rate_button);
            ratingResult = itemView.findViewById(R.id.id_rating_result_text_view);
        }
    }

    public void filter(String text) {
        tasks.clear();
        if(text.isEmpty()) {
            tasks.addAll(allTasks);
        }
        else {
            text = text.toLowerCase();
            for (CompletedTasksListViewModel task : allTasks) {
                if (task.getTitle().toLowerCase().contains(text)) {
                    tasks.add(task);
                }
            }
        }
        notifyDataSetChanged();
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