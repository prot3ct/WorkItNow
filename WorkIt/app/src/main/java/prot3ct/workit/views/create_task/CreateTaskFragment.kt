package prot3ct.workit.views.create_task


import prot3ct.workit.utils.WorkItProgressDialog
import android.view.LayoutInflater
import android.view.ViewGroup
import android.os.Bundle
import prot3ct.workit.R
import prot3ct.workit.views.navigation.DrawerUtil
import android.content.Intent
import prot3ct.workit.views.list_tasks.ListTasksActivity
import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.app.TimePickerDialog
import android.content.Context
import android.view.View
import android.widget.*
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.google.android.material.textfield.TextInputLayout
import prot3ct.workit.views.create_task.base.CreateTaskContract
import java.lang.Exception
import java.text.DateFormatSymbols
import java.text.SimpleDateFormat
import java.util.*

class CreateTaskFragment : Fragment(), CreateTaskContract.View {
    private lateinit var presenter: CreateTaskContract.Presenter
    private lateinit var  titleLayout: TextInputLayout
    private lateinit var  lengthLayout: TextInputLayout
    private lateinit var  descriptionLayout: TextInputLayout
    private lateinit var  cityLayout: TextInputLayout
    private lateinit var  addressLayout: TextInputLayout
    private lateinit var  rewardLayout: TextInputLayout
    private lateinit var  titleTextView: TextView
    private lateinit var  startDateTextView: TextView
    private lateinit var  lengthEditText: EditText
    private lateinit var  descriptionTextView: TextView
    private lateinit var  cityTextView: TextView
    private lateinit var  addressTextView: TextView
    private lateinit var  rewardTextView: TextView
    private lateinit var  toolbar: Toolbar
    private lateinit var  saveTaskButton: Button
    private lateinit var startDateString: String
    private lateinit var  date: Calendar
    private lateinit var dialog: WorkItProgressDialog

    override fun setPresenter(presenter: CreateTaskContract.Presenter) {
        this.presenter = presenter
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        dialog = WorkItProgressDialog(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_create_task, container, false)
        toolbar = view.findViewById(R.id.id_drawer_toolbar)
        titleLayout = view.findViewById(R.id.input_layout_title)
        lengthLayout = view.findViewById(R.id.input_layout_length)
        descriptionLayout = view.findViewById(R.id.input_layout_description)
        cityLayout = view.findViewById(R.id.input_layout_city)
        addressLayout = view.findViewById(R.id.input_layout_address)
        rewardLayout = view.findViewById(R.id.input_layout_reward)
        titleTextView = view.findViewById<View>(R.id.id_title_edit_text) as TextView
        startDateTextView = view.findViewById<View>(R.id.id_choose_start_date_text_view) as TextView
        lengthEditText = view.findViewById<View>(R.id.id_length_edit_text) as EditText
        descriptionTextView = view.findViewById<View>(R.id.id_description_edit_text) as TextView
        cityTextView = view.findViewById<View>(R.id.id_city_edit_text) as TextView
        addressTextView = view.findViewById<View>(R.id.id_address_edit_text) as TextView
        rewardTextView = view.findViewById<View>(R.id.id_reward_edit_text) as TextView
        saveTaskButton = view.findViewById<View>(R.id.id_create_task_btn) as Button
        val drawer = DrawerUtil(requireActivity(), toolbar)
        drawer.getDrawer()
        startDateTextView.setOnClickListener { showDateTimePicker() }
        saveTaskButton.setOnClickListener {
            titleLayout.error = null
            addressLayout.error = null
            lengthLayout.error = null
            cityLayout.error = null
            rewardLayout.error = null
            validateTask(
                titleTextView.text.toString(),
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

    override fun notifyError(errorMessage: String) {
        Toast.makeText(getContext(), errorMessage, Toast.LENGTH_LONG).show()
    }

    override fun createTask() {
        presenter.createTask(
            titleTextView.text.toString(),
            startDateString,
            lengthEditText.text.toString(),
            descriptionTextView.text.toString(),
            cityTextView.text.toString(),
            addressTextView.text.toString(),
            rewardTextView.text.toString()
        )
    }

    override fun notifyInvalidLocation() {
        cityLayout.error = "Invalid location"
        addressLayout.error = "Invalid location"
    }

    override fun notifySuccessful() {
        Toast.makeText(context, "Task created successfully", Toast.LENGTH_SHORT).show()
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
        val pickerDialog = DatePickerDialog(requireContext(), object : OnDateSetListener {
            var first = true
            override fun onDateSet(view: DatePicker, year: Int, monthOfYear: Int, dayOfMonth: Int) {
                if (first) {
                    date.set(year, monthOfYear, dayOfMonth)
                    TimePickerDialog(context, { view, hourOfDay, minute ->
                        date.set(Calendar.HOUR_OF_DAY, hourOfDay)
                        date.set(Calendar.MINUTE, minute)
                        val dateFormat = SimpleDateFormat("yyyy-MM-dd,HH:mm")
                        startDateTextView!!.text =
                            getOrdinal(date.get(Calendar.DAY_OF_MONTH)) + " " +
                                    getMonthForInt(date.get(Calendar.MONTH)) + " at " + String.format(
                                "%02d:%02d", date.get(
                                    Calendar.HOUR_OF_DAY
                                ), date.get(Calendar.MINUTE)
                            )
                        startDateString = dateFormat.format(date.getTime())
                        first = false
                    }, currentDate[Calendar.HOUR_OF_DAY], currentDate[Calendar.MINUTE], true).show()
                    first = false
                }
            }
        }, currentDate[Calendar.YEAR], currentDate[Calendar.MONTH], currentDate[Calendar.DATE])
        val minDate = Calendar.getInstance()
        minDate.add(Calendar.DAY_OF_MONTH, 1)
        val maxDate = Calendar.getInstance()
        maxDate.add(Calendar.DAY_OF_MONTH, 8)
        pickerDialog.datePicker.minDate = minDate.time.time
        pickerDialog.datePicker.maxDate = maxDate.time.time
        pickerDialog.show()
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

    private fun validateTask(
        title: String, length: String, description: String,
        city: String, address: String, reward: String
    ) {
        if (title.length < 5 || title.length > 30) {
            titleLayout.error = "Title must be bettwen 5 and 30 symbols long"
            return
        }
        if (!isInteger(length)) {
            lengthLayout.error = "Invalid length number"
            return
        }
        val lengthInt = length.toInt()
        if (!(lengthInt == 1 || lengthInt == 2 || lengthInt == 3 || lengthInt == 4)) {
            lengthLayout.error = "Invalid length number"
            return
        }
        if (!isInteger(reward)) {
            rewardLayout.error = "Invalid reward number"
            return
        }
        presenter.checkIfLocationExists("$city, $address")
    }

    private fun isInteger(input: String): Boolean {
        return try {
            input.toInt()
            true
        } catch (e: Exception) {
            false
        }
    }

    companion object {
        fun newInstance(): CreateTaskFragment {
            return CreateTaskFragment()
        }
    }
}