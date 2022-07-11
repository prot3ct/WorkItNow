package prot3ct.workit.views.list_task_requests

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.os.Bundle
import prot3ct.workit.R
import androidx.recyclerview.widget.LinearLayoutManager
import prot3ct.workit.views.navigation.DrawerUtil
import android.widget.Toast
import android.content.Intent
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import prot3ct.workit.views.my_tasks.MyTasksActivity
import prot3ct.workit.view_models.TaskRequestListViewModel
import prot3ct.workit.views.list_task_requests.base.ListTaskRequestContract
import kotlin.properties.Delegates

class ListTaskRequestsFragment : Fragment(), ListTaskRequestContract.View {
    private lateinit var presenter: ListTaskRequestContract.Presenter
    private lateinit var recyclerTaskRequestView: RecyclerView
    private lateinit var toolbar: Toolbar
    private var taskId: Int by Delegates.notNull()

    override fun setPresenter(presenter: ListTaskRequestContract.Presenter) {
        this.presenter = presenter
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_list_task_requests, container, false)
        toolbar = view.findViewById(R.id.id_drawer_toolbar)
        taskId = requireActivity().intent.getIntExtra("taskId", 0)
        recyclerTaskRequestView = view.findViewById(R.id.id_list_task_requests_list_view)
        val llm = LinearLayoutManager(context)
        recyclerTaskRequestView.layoutManager = llm
        val drawer = DrawerUtil(requireActivity(), toolbar)
        drawer.getDrawer()
        presenter.getTaskRequests(taskId)
        return view
    }

    override fun notifySuccessful(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    override fun showMyTasksActivty() {
        val intent = Intent(context, MyTasksActivity::class.java)
        startActivity(intent)
    }

    override fun notifyError(errorMessage: String) {
        Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
    }

    override fun setupTaskRequestsAdapter(users: List<TaskRequestListViewModel>) {
        val adapter = ListTaskRequestAdapter(users.toMutableList(), requireContext(), presenter)
        recyclerTaskRequestView.adapter = adapter
    }

    companion object {
        fun newInstance(): ListTaskRequestsFragment {
            return ListTaskRequestsFragment()
        }
    }
}