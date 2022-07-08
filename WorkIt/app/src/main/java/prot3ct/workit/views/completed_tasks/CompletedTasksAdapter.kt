package prot3ct.workit.views.completed_tasks

import android.content.Context
import prot3ct.workit.view_models.CompletedTasksListViewModel
import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import prot3ct.workit.R
import android.widget.TextView
import prot3ct.workit.views.completed_tasks.base.CompletedTasksContract
import java.text.DateFormat
import java.text.DateFormatSymbols
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class CompletedTasksAdapter internal constructor(
    private val tasks: MutableList<CompletedTasksListViewModel>,
    context: Context,
    view: CompletedTasksContract.View
) : RecyclerView.Adapter<CompletedTasksAdapter.TaskViewHolder>() {
    private val allTasks: MutableList<CompletedTasksListViewModel> = ArrayList()
    private val context: Context
    private val view: CompletedTasksContract.View
    private val loggedInUserId: Int
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TaskViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.single_completed_task, parent, false)
        return TaskViewHolder(v)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val format: DateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm", Locale.ENGLISH)
        var date: Date? = null
        try {
            date = format.parse(tasks[position].startDate)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        val currentDate = Calendar.getInstance().time
        val calendar = Calendar.getInstance()
        calendar.time = date
        val currentCalendar = Calendar.getInstance()
        currentCalendar.time = currentDate
        if (currentCalendar[Calendar.DAY_OF_MONTH] == calendar[Calendar.DAY_OF_MONTH]) {
            holder.startTime.text = "Today"
        } else {
            holder.startTime.text =
                getOrdinal(calendar[Calendar.DAY_OF_MONTH]) + " " + getMonthForInt(
                    calendar[Calendar.MONTH]
                )
        }
        holder.taskTitle.text = tasks[position].title
        holder.taskCreator.text = "Created by: " + tasks[position].supervisorFullName
        holder.taskTasker.text = "Assigned to: " + tasks[position].taskerFullName
        if (tasks[position].taskerId == loggedInUserId && tasks[position].hasTaskerGivenRating) {
            holder.rateButton.visibility = View.GONE
            holder.ratingResult.text =
                "You have already rated " + tasks[position].supervisorFullName
        } else if (tasks[position].supervisorId == loggedInUserId && tasks[position].hasSupervisorGivenRating) {
            holder.rateButton.visibility = View.GONE
            holder.ratingResult.text = "You have already rated " + tasks[position].taskerFullName
        } else {
            holder.rateButton.setOnClickListener {
                view.updateSelectedInfo(
                    tasks[holder.absoluteAdapterPosition].taskId,
                    tasks[holder.layoutPosition].supervisorId,
                    tasks[position].taskerId
                )
                view.showDialog()
                holder.rateButton.visibility = View.GONE
                if (tasks[holder.absoluteAdapterPosition].taskerId == loggedInUserId) {
                    holder.ratingResult.text =
                        "You have already rated " + tasks[holder.absoluteAdapterPosition].supervisorFullName
                } else {
                    holder.ratingResult.text =
                        "You have already rated " + tasks[holder.absoluteAdapterPosition].taskerFullName
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return tasks.size
    }

    class TaskViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var startTime: TextView
        var taskTitle: TextView
        var taskCreator: TextView
        var taskTasker: TextView
        var rateButton: Button
        var ratingResult: TextView

        init {
            startTime = itemView.findViewById(R.id.id_task_start_time)
            taskTitle = itemView.findViewById(R.id.id_task_title)
            taskCreator = itemView.findViewById(R.id.id_task_supervisor)
            taskTasker = itemView.findViewById(R.id.id_task_tasker)
            rateButton = itemView.findViewById(R.id.id_rate_button)
            ratingResult = itemView.findViewById(R.id.id_rating_result_text_view)
        }
    }

    fun filter(text: String) {
        var text = text
        tasks.clear()
        if (text.isEmpty()) {
            tasks.addAll(allTasks)
        } else {
            text = text.lowercase(Locale.getDefault())
            for (task in allTasks) {
                if (task.title.lowercase(Locale.getDefault()).contains(text)) {
                    tasks.add(task)
                }
            }
        }
        notifyDataSetChanged()
    }

    private fun getMonthForInt(num: Int): String {
        var month = "wrong"
        val dfs = DateFormatSymbols()
        val months = dfs.months
        if (num >= 0 && num <= 11) {
            month = months[num]
        }
        return month
    }

    private fun getOrdinal(i: Int): String {
        val sufixes = arrayOf("th", "st", "nd", "rd", "th", "th", "th", "th", "th", "th")
        return when (i % 100) {
            11, 12, 13 -> i.toString() + "th"
            else -> i.toString() + sufixes[i % 10]
        }
    }

    init {
        allTasks.addAll(tasks)
        this.context = context
        this.view = view
        loggedInUserId = view.loggedInUserId
    }
}