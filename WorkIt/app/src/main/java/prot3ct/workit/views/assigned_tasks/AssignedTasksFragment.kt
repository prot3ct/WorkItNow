package prot3ct.workit.views.assigned_tasks

import android.content.Context
import prot3ct.workit.views.assigned_tasks.base.AssignedTasksContract.Presenter.assignedTasks
import prot3ct.workit.views.assigned_tasks.AssignedTasksAdapter.filter
import prot3ct.workit.views.assigned_tasks.AssignedTasksAdapter
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.os.Bundle
import android.view.View
import prot3ct.workit.R
import androidx.recyclerview.widget.LinearLayoutManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import prot3ct.workit.view_models.AssignedTasksListViewModel
import prot3ct.workit.views.assigned_tasks.AssignedTasksFragment
import prot3ct.workit.views.assigned_tasks.base.AssignedTasksContract

class AssignedTasksFragment : Fragment(), AssignedTasksContract.View {
    private lateinit var presenter: AssignedTasksContract.Presenter
    private lateinit var adapter: AssignedTasksAdapter
    private lateinit var recyclerTaskView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_assigned_tasks, container, false)
        recyclerTaskView = view.findViewById(R.id.id_list_tasks_list_view)
        val llm = LinearLayoutManager(context)
        recyclerTaskView.layoutManager = llm
        presenter.getAssignedTasks()
        return view
    }

    override fun filterTask(query: String) {
        adapter.filter(query)
    }

    override fun notifySuccessful(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    override fun notifyError(errorMessage: String) {
        Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
    }

    override fun setupTasksAdapter(tasks: List<AssignedTasksListViewModel>) {
        adapter = AssignedTasksAdapter(tasks, requireContext(), presenter)
        recyclerTaskView.adapter = adapter
    }

    override fun setPresenter(presenter: AssignedTasksContract.Presenter) {
        this.presenter = presenter
    }

    companion object {
        fun newInstance(): AssignedTasksFragment {
            return AssignedTasksFragment()
        }
    }
}