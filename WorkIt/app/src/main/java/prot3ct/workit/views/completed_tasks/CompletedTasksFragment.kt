package prot3ct.workit.views.completed_tasks

import android.content.Context

import prot3ct.workit.utils.WorkItProgressDialog
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.os.Bundle
import android.view.View
import prot3ct.workit.R
import androidx.recyclerview.widget.LinearLayoutManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.stepstone.apprating.AppRatingDialog
import prot3ct.workit.view_models.CompletedTasksListViewModel
import prot3ct.workit.views.completed_tasks.base.CompletedTasksContract
import kotlin.properties.Delegates

class CompletedTasksFragment : Fragment(), CompletedTasksContract.View {
    private lateinit var presenter: CompletedTasksContract.Presenter
    private lateinit var adapter: CompletedTasksAdapter
    override var loggedInUserId by Delegates.notNull<Int>()
        private set
    private var selectedTaskId by Delegates.notNull<Int>()
    private var userToBeRatedId by Delegates.notNull<Int>()
    private var receiverUserRoleId by Delegates.notNull<Int>()
    private val dialog: WorkItProgressDialog = WorkItProgressDialog(context)
    private lateinit var recyclerTaskView: RecyclerView

    override fun setPresenter(presenter: CompletedTasksContract.Presenter) {
        this.presenter = presenter
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_completed_tasks, container, false)
        recyclerTaskView = view.findViewById(R.id.id_list_tasks_list_view)
        val llm = LinearLayoutManager(context)
        recyclerTaskView.layoutManager = llm
        presenter.completedTasks
        loggedInUserId = presenter!!.loggedInUserId
        return view
    }

    override fun filterTask(query: String) {
        adapter.filter(query)
    }

    override fun updateSelectedInfo(taskId: Int, supervisorId: Int, taskerId: Int) {
        selectedTaskId = taskId
        if (loggedInUserId == supervisorId) {
            userToBeRatedId = taskerId
            receiverUserRoleId = 3
        } else {
            userToBeRatedId = supervisorId
            receiverUserRoleId = 4
        }
    }

    override fun postRaiting(value: Int, description: String) {
        presenter.createRating(
            value,
            description,
            userToBeRatedId,
            selectedTaskId,
            receiverUserRoleId
        )
    }

    override fun notifyError(msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
    }

    override fun notifySuccessful(msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
    }

    override fun showDialogForLoading() {
        dialog.showProgress("Loading...")
    }

    override fun dismissDialog() {
        dialog.dismissProgress()
    }

    override fun setupTasksAdapter(tasks: List<CompletedTasksListViewModel>) {
        adapter = CompletedTasksAdapter(tasks.toMutableList(), requireContext(), this)
        recyclerTaskView.adapter = adapter
    }

    override fun showDialog() {
        AppRatingDialog.Builder()
            .setPositiveButtonText("Submit")
            .setDefaultRating(3)
            .setTitle("Rate user performance")
            .setStarColor(R.color.bg_login)
            .setTitleTextColor(R.color.md_black_1000)
            .setNumberOfStars(5)
            .setCommentBackgroundColor(R.color.md_blue_grey_50)
            .setHint("Please write short review")
            .setHintTextColor(R.color.md_black_1000) //.setWindowAnimation(R.style.MyDialogFadeAnimation)
            .create(requireActivity())
            .show()
    }

    companion object {
        fun newInstance(): CompletedTasksFragment {
            return CompletedTasksFragment()
        }
    }
}