package prot3ct.workit.views.list_tasks

import android.content.Context
import prot3ct.workit.view_models.AvailableTasksListViewModel
import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import android.view.LayoutInflater
import prot3ct.workit.R
import android.content.Intent
import android.view.View
import prot3ct.workit.views.task_details.TaskDetailsActivity
import androidx.cardview.widget.CardView
import android.widget.TextView
import java.text.DateFormat
import java.text.DateFormatSymbols
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class ListTasksAdapter internal constructor(
    var tasks: List<AvailableTasksListViewModel>,
    val context: Context
) : RecyclerView.Adapter<ListTasksAdapter.TaskViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TaskViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.single_task_list_tasks, parent, false)
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
        val diffInMillies = Math.abs(currentDate.time - date!!.time)
        val diffHours = TimeUnit.HOURS.convert(diffInMillies, TimeUnit.MILLISECONDS)
        val diffMinutes = TimeUnit.MINUTES.convert(diffInMillies, TimeUnit.MILLISECONDS)
        if (diffHours <= 24) {
            holder.startTime.text = "Today"
            if (diffHours != 0L) {
                holder.timeLeft.text = "$diffHours hours left to respond"
            } else {
                holder.timeLeft.text = "$diffMinutes minutes left to respond"
            }
        } else {
            holder.startTime.text =
                getOrdinal(calendar[Calendar.DAY_OF_MONTH]) + " " + getMonthForInt(
                    calendar[Calendar.MONTH]
                )
        }
        holder.taskTitle.text = tasks[position].title
        holder.taskCreator.text = "for " + tasks[position].fullName
        holder.supervisorRating.text = tasks[position].supervisorRating + ""
        holder.cv.setOnClickListener {
            val intent = Intent(context, TaskDetailsActivity::class.java)
            intent.putExtra("taskId", tasks[holder.absoluteAdapterPosition].taskId)
            context.startActivity(intent)
        }
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
        var supervisorRating: TextView

        init {
            cv = itemView.findViewById(R.id.id_single_task_from_list_tasks)
            startTime = itemView.findViewById(R.id.id_task_start_time)
            timeLeft = itemView.findViewById(R.id.id_task_time_left)
            taskTitle = itemView.findViewById(R.id.id_task_title)
            taskCreator = itemView.findViewById(R.id.id_task_creator)
            supervisorRating = itemView.findViewById(R.id.id_task_supervisor_rating_text_view)
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
}