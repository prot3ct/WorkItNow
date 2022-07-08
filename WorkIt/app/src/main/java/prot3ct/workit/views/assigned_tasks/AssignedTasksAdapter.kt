package prot3ct.workit.views.assigned_tasks

import android.content.Context
import prot3ct.workit.view_models.AssignedTasksListViewModel
import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import android.view.LayoutInflater
import prot3ct.workit.R
import android.content.Intent
import android.view.View
import android.widget.Button
import prot3ct.workit.views.task_details.TaskDetailsActivity
import androidx.cardview.widget.CardView
import android.widget.TextView
import prot3ct.workit.views.assigned_tasks.base.AssignedTasksContract
import java.text.DateFormat
import java.text.DateFormatSymbols
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class AssignedTasksAdapter internal constructor(
    val tasks: MutableList<AssignedTasksListViewModel>,
    context: Context,
    presenter: AssignedTasksContract.Presenter
) : RecyclerView.Adapter<AssignedTasksAdapter.TaskViewHolder>() {
    private val presenter: AssignedTasksContract.Presenter
    private lateinit var allTasks: List<AssignedTasksListViewModel> = listOf()
    private val context: Context
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TaskViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.single_assigned_task, parent, false)
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
            if (date!!.hours != currentDate.hours) {
                if (calendar[Calendar.HOUR_OF_DAY] - currentCalendar[Calendar.HOUR_OF_DAY] <= 0) {
                    holder.timeLeft.text = "Expired"
                } else {
                    holder.timeLeft.setText((calendar[Calendar.HOUR_OF_DAY] - currentCalendar[Calendar.HOUR_OF_DAY]).toString() + " hours left until start")
                }
            } else {
                holder.timeLeft.setText((calendar[Calendar.MINUTE] - currentCalendar[Calendar.MINUTE]).toString() + " minutes left until start")
            }
        } else {
            holder.startTime.text =
                getOrdinal(calendar[Calendar.DAY_OF_MONTH]) + " " + getMonthForInt(
                    calendar[Calendar.MONTH]
                )
        }
        holder.taskTitle.text = tasks[position].title
        holder.taskCreator.text = "for " + tasks[position].fullName
        holder.cv.setOnClickListener {
            val intent = Intent(context, TaskDetailsActivity::class.java)
            intent.putExtra("taskId", tasks[position].taskId)
            context.startActivity(intent)
        }
        holder.cancelButton.setOnClickListener {
            presenter.removeAssignedUser(tasks[position].taskId)
            tasks.removeAt(position)
            notifyDataSetChanged()
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

    override fun getItemCount(): Int {
        return tasks.size
    }

    class TaskViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var cv: CardView
        var startTime: TextView
        var timeLeft: TextView
        var taskTitle: TextView
        var taskCreator: TextView
        var cancelButton: Button

        init {
            cv = itemView.findViewById(R.id.id_single_task_from_list_tasks)
            startTime = itemView.findViewById(R.id.id_task_start_time)
            taskTitle = itemView.findViewById(R.id.id_task_title)
            taskCreator = itemView.findViewById(R.id.id_task_creator)
            timeLeft = itemView.findViewById(R.id.id_task_time_left)
            cancelButton = itemView.findViewById(R.id.id_cancel_task_assignment_button)
        }
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
        allTasks = muta(tasks)
        allTasks.addAll(tasks)
        this.context = context
        this.presenter = presenter
    }
}