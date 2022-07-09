package prot3ct.workit.views.task_details

import com.google.android.gms.maps.OnMapReadyCallback
import prot3ct.workit.view_models.TaskDetailViewModel
import com.google.android.gms.maps.GoogleMap
import android.widget.TextView
import prot3ct.workit.utils.WorkItProgressDialog
import android.view.LayoutInflater
import android.view.ViewGroup
import android.os.Bundle
import prot3ct.workit.R
import com.google.android.gms.maps.SupportMapFragment
import prot3ct.workit.views.navigation.DrawerUtil
import android.content.Intent
import android.view.View
import android.widget.Button
import prot3ct.workit.views.profile.ProfileActivity
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.CameraUpdateFactory
import prot3ct.workit.view_models.IsUserAssignableToTaskViewModel
import prot3ct.workit.views.task_details.base.TaskDetailsContract
import java.text.DateFormat
import java.text.DateFormatSymbols
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.properties.Delegates

class TaskDetailsFragment : Fragment(), TaskDetailsContract.View, OnMapReadyCallback {

    private lateinit var presenter: TaskDetailsContract.Presenter
    private lateinit var taskDetails: TaskDetailViewModel
    private var flag by Delegates.notNull<Int>()
    private lateinit var mMap: GoogleMap
    private lateinit var taskTitle: TextView
    private lateinit var taskDescription: TextView
    private lateinit var taskStartDate: TextView
    private lateinit var reward: TextView
    private lateinit var city: TextView
    private lateinit var supervisorName: TextView
    private lateinit var supervisorRating: TextView
    private lateinit var toolbar: Toolbar
    private lateinit var applyForTask: Button
    private val dialog: WorkItProgressDialog = WorkItProgressDialog(context)

    override fun setPresenter(presenter: TaskDetailsContract.Presenter) {
        this.presenter = presenter
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_task_details, container, false)
        val mapFragment = childFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment!!.getMapAsync(this)
        applyForTask = view.findViewById(R.id.id_apply_for_task_button)
        toolbar = view.findViewById(R.id.id_drawer_toolbar)
        taskTitle = view.findViewById(R.id.id_title_details_edit_text)
        reward = view.findViewById(R.id.id_reward_details_edit_text)
        taskDescription = view.findViewById(R.id.id_description_details_edit_text)
        taskStartDate = view.findViewById(R.id.id_date_details_text_view)
        city = view.findViewById(R.id.id_city_details_text_view)
        supervisorName = view.findViewById(R.id.id_supervisor_text_view)
        supervisorRating = view.findViewById(R.id.id_supervisor_rating_text_view)
        val drawer = DrawerUtil(this.requireActivity(), toolbar)
        drawer.getDrawer()
        return view
    }

    override fun updateTask(taskDetails: TaskDetailViewModel) {
        this.taskDetails = taskDetails
        taskTitle.text = this.taskDetails.title
        taskDescription.text = this.taskDetails.description
        val format: DateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm", Locale.ENGLISH)
        var date: Date? = null
        try {
            date = format.parse(this.taskDetails.startDate)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        val currentDate = Calendar.getInstance().time
        val calendar = Calendar.getInstance()
        calendar.time = date
        taskStartDate.text = getOrdinal(calendar[Calendar.DAY_OF_MONTH]) + " " +
                getMonthForInt(calendar[Calendar.MONTH]) + " at " + String.format(
            "%02d:%02d",
            calendar[Calendar.HOUR_OF_DAY],
            calendar[Calendar.MINUTE]
        ) +
                " for " + this.taskDetails!!.length + " hours"
        reward.text = "BGN " + this.taskDetails!!.reward + "/hr"
        city.text = this.taskDetails!!.city + ", " + this.taskDetails!!.address // for
        supervisorName.text = "For " + taskDetails.supervisorName
        supervisorRating.text = taskDetails.supervisorRating.toString() + ""
        supervisorName.setOnClickListener {
            val intent = Intent(context, ProfileActivity::class.java)
            intent.putExtra("userId", taskDetails.supervisorId)
            requireContext().startActivity(intent)
        }
        presenter.getLatLng(this.taskDetails.city + ", " + this.taskDetails.address)
    }

    override fun notifySuccessful(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    override fun notifyError(errorMessage: String) {
        Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
    }

    override fun showDialogforLoading() {
        dialog.showProgress("Logging in...")
    }

    override fun dismissDialog() {
        dialog.dismissProgress()
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

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        val taskId = requireActivity().intent.getIntExtra("taskId", 0)
        presenter.getTaskDetails(taskId)
        presenter.getCanAssignToTask(taskId)
    }

    override fun updateMap(lat: Double, lng: Double) {
        val location = LatLng(lat, lng)
        mMap.addMarker(MarkerOptions().position(location))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 17.0f))
    }

    override fun updateButton(canAssignToTask: IsUserAssignableToTaskViewModel) {
        applyForTask.setOnClickListener {
            when (flag) {
                0 -> {
                    presenter.declineTaskRequest(canAssignToTask.pendingRequestId, 2)
                    applyForTask.text = "I'll do it"
                    applyForTask.setTextColor(resources.getColor(R.color.md_black_1000))
                    applyForTask.setBackgroundColor(resources.getColor(R.color.bg_login))
                    flag = 2
                }
                1 -> {
                    presenter.removeAssignedUser(taskDetails.taskId)
                    requireActivity().finish()
                }
                else -> {
                    presenter.createTaskRequest(taskDetails.taskId)
                    applyForTask.text = "Cancel pending request"
                    applyForTask.setTextColor(resources.getColor(R.color.md_white_1000))
                    applyForTask.setBackgroundColor(resources.getColor(R.color.md_red_400))
                    flag = 0
                }
            }
        }
        when (canAssignToTask.isUserAssignableToTaskMessage) {
            "Cancel pending request" -> {
                applyForTask.text = canAssignToTask.isUserAssignableToTaskMessage
                applyForTask.setTextColor(resources.getColor(R.color.md_white_1000))
                applyForTask.setBackgroundColor(resources.getColor(R.color.md_red_400))
                flag = 0
            }
            "I can't do this task anymore" -> {
                applyForTask.text = canAssignToTask.isUserAssignableToTaskMessage
                applyForTask.setTextColor(resources.getColor(R.color.md_white_1000))
                applyForTask.setBackgroundColor(resources.getColor(R.color.md_red_900))
                flag = 1
            }
            else -> {
                flag = 2
            }
        }
    }

    companion object {
        fun newInstance(): TaskDetailsFragment {
            return TaskDetailsFragment()
        }
    }
}