package prot3ct.workit.views.my_tasks

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import prot3ct.workit.view_models.MyTasksListViewModel
import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import android.view.LayoutInflater
import prot3ct.workit.R
import android.content.Intent
import android.view.View
import android.widget.Button
import prot3ct.workit.views.edit_task.EditTaskActivity
import prot3ct.workit.views.list_task_requests.ListTaskRequestsActivity
import androidx.cardview.widget.CardView
import android.widget.TextView
import prot3ct.workit.views.my_tasks.base.MyTasksContract
import java.text.DateFormat
import java.text.DateFormatSymbols
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class MyTasksAdapter internal constructor(
    private val tasks: MutableList<MyTasksListViewModel>,
    val context: Context,
    private val myTasksPresenter: MyTasksContract.Presenter
) : RecyclerView.Adapter<MyTasksAdapter.TaskViewHolder>() {

    private val presenter: MyTasksContract.Presenter = myTasksPresenter
    private val allTasks: MutableList<MyTasksListViewModel> = tasks.toMutableList()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TaskViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.single_task_my_tasks, parent, false)
        return TaskViewHolder(v)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        if (!tasks[position].hasPendingRequests) {
            holder.taskRequestersButton.visibility = View.GONE
        }
        val format: DateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm", Locale.ENGLISH)
        lateinit var date: Date
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
        if (currentDate.day == date.day) {
            holder.startTime.text = "Today"
            if (date.hours != currentDate.hours) {
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
        holder.taskCreator.text = "for me"
        holder.editTaskButton.setOnClickListener {
            val intent = Intent(context, EditTaskActivity::class.java)
            intent.putExtra("taskId", tasks[holder.absoluteAdapterPosition].taskId)
            context.startActivity(intent)
        }
        holder.taskRequestersButton.setOnClickListener {
            val intent = Intent(context, ListTaskRequestsActivity::class.java)
            intent.putExtra("taskId", tasks[holder.absoluteAdapterPosition].taskId)
            context.startActivity(intent)
        }
        holder.deleteTaskButton.setOnClickListener {
            val dialogClickListener = DialogInterface.OnClickListener { dialog, which ->
                when (which) {
                    DialogInterface.BUTTON_POSITIVE -> {
                        presenter.deleteTask(tasks[holder.absoluteAdapterPosition].taskId)
                        tasks.removeAt(holder.absoluteAdapterPosition)
                        notifyDataSetChanged()
                    }
                }
            }
            val builder = AlertDialog.Builder(context)
            builder.setMessage("Are you sure you want to delete this task?")
                .setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show()
        }
    }

    override fun getItemCount(): Int {
        return tasks.size
    }

    private fun getMonthForInt(num: Int): String {
        var month = "wrong"
        val dfs = DateFormatSymbols()
        val months = dfs.months
        if (num in 0..11) {
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

    class TaskViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var cv: CardView
        var startTime: TextView
        var timeLeft: TextView
        var taskTitle: TextView
        var taskCreator: TextView
        var editTaskButton: Button
        var taskRequestersButton: Button
        var deleteTaskButton: Button

        init {
            cv = itemView.findViewById(R.id.id_single_task_from_my_tasks)
            startTime = itemView.findViewById(R.id.id_task_start_time)
            timeLeft = itemView.findViewById(R.id.id_task_time_left)
            taskTitle = itemView.findViewById(R.id.id_task_title)
            taskCreator = itemView.findViewById(R.id.id_task_creator)
            editTaskButton = itemView.findViewById(R.id.id_edit_task_button)
            taskRequestersButton = itemView.findViewById(R.id.id_task_requests_button)
            deleteTaskButton = itemView.findViewById(R.id.id_delete_task_button)
        }
    }
}