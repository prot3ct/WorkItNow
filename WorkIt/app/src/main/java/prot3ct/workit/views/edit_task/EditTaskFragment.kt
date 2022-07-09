package prot3ct.workit.views.edit_task

import prot3ct.workit.utils.WorkItProgressDialog
import android.view.LayoutInflater
import android.view.ViewGroup
import android.os.Bundle
import prot3ct.workit.R
import prot3ct.workit.views.navigation.DrawerUtil
import android.content.Intent
import prot3ct.workit.views.list_tasks.ListTasksActivity
import prot3ct.workit.view_models.TaskDetailViewModel
import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.app.TimePickerDialog
import android.view.View
import android.widget.*
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import prot3ct.workit.views.edit_task.base.EditTaskContract
import java.text.SimpleDateFormat
import java.util.*

class EditTaskFragment : Fragment(), EditTaskContract.View {
    private lateinit var presenter: EditTaskContract.Presenter
    private var taskId = 0
    private lateinit var titleTextView: TextView
    private lateinit var startDateTextView: TextView
    private lateinit var lengthEditText: EditText
    private lateinit var descriptionTextView: TextView
    private lateinit var cityTextView: TextView
    private lateinit var addressTextView: TextView
    private lateinit var rewardTextView: TextView
    private lateinit var toolbar: Toolbar
    private lateinit var saveTaskButton: Button
    private lateinit var date: Calendar
    private var dialog: WorkItProgressDialog = WorkItProgressDialog(context)

    override fun setPresenter(presenter: EditTaskContract.Presenter) {
        this.presenter = presenter
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_edit_task, container, false)
        toolbar = view.findViewById(R.id.id_drawer_toolbar)
        dialog = WorkItProgressDialog(context)
        titleTextView = view.findViewById<View>(R.id.id_title_edit_text) as TextView
        startDateTextView = view.findViewById<View>(R.id.id_choose_start_date_text_view) as TextView
        lengthEditText = view.findViewById<View>(R.id.id_length_edit_text) as EditText
        descriptionTextView = view.findViewById<View>(R.id.id_description_edit_text) as TextView
        cityTextView = view.findViewById<View>(R.id.id_city_edit_text) as TextView
        addressTextView = view.findViewById<View>(R.id.id_address_edit_text) as TextView
        rewardTextView = view.findViewById<View>(R.id.id_reward_edit_text) as TextView
        saveTaskButton = view.findViewById<View>(R.id.id_create_task_btn) as Button
        val drawer = DrawerUtil(requireActivity().parent, toolbar)
        drawer.getDrawer()
        taskId = requireActivity().intent.getIntExtra("taskId", 0)
        presenter.getTaskDetails(taskId)
        startDateTextView.setOnClickListener { showDateTimePicker() }
        saveTaskButton.setOnClickListener {
            presenter.updateTask(
                taskId,
                titleTextView.text.toString(),
                startDateTextView.text.toString(),
                lengthEditText.text.toString(),
                descriptionTextView.text.toString(),
                cityTextView.text.toString(),
                addressTextView.text.toString(),
                rewardTextView.text.toString()
            )
        }
        return view
    }

    override fun showListJobsActivity() {
        val intent = Intent(context, ListTasksActivity::class.java)
        startActivity(intent)
    }

    override fun updateTask(task: TaskDetailViewModel) {
        titleTextView.text = task.title
        startDateTextView.text = task.startDate
        lengthEditText.setText(task.length.toString())
        descriptionTextView.text = task.description
        cityTextView.text = task.city
        addressTextView.text = task.address
        rewardTextView.text = task.reward.toString()
    }

    override fun notifyError(errorMessage: String) {
        Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
    }

    override fun notifySuccessful() {
        Toast.makeText(context, "Task updated successfully", Toast.LENGTH_SHORT).show()
    }

    override fun showDialogforLoading() {
        dialog.showProgress("Creating task...")
    }

    override fun dismissDialog() {
        dialog.dismissProgress()
    }

    private fun showDateTimePicker() {
        val currentDate = Calendar.getInstance()
        date = Calendar.getInstance()
        DatePickerDialog(
            requireContext(),
            object : OnDateSetListener {
                var first = true
                override fun onDateSet(
                    view: DatePicker,
                    year: Int,
                    monthOfYear: Int,
                    dayOfMonth: Int
                ) {
                    if (first) {
                        date.set(year, monthOfYear, dayOfMonth)
                        TimePickerDialog(
                            context,
                            { _, hourOfDay, minute ->
                                date.set(Calendar.HOUR_OF_DAY, hourOfDay)
                                date.set(Calendar.MINUTE, minute)
                                val dateFormat = SimpleDateFormat("yyyy-MM-dd,HH:mm")
                                startDateTextView.text = dateFormat.format(date.getTime())
                                first = false
                            },
                            currentDate[Calendar.HOUR_OF_DAY],
                            currentDate[Calendar.MINUTE],
                            true
                        ).show()
                        first = false
                    }
                }
            },
            currentDate[Calendar.YEAR],
            currentDate[Calendar.MONTH],
            currentDate[Calendar.DATE]
        ).show()
    }

    companion object {
        fun newInstance(): EditTaskFragment {
            return EditTaskFragment()
        }
    }
}