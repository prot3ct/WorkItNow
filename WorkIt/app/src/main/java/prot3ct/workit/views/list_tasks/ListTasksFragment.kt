package prot3ct.workit.views.list_tasks

import androidx.recyclerview.widget.RecyclerView
import prot3ct.workit.utils.EndlessRecyclerViewScrollListener
import prot3ct.workit.view_models.AvailableTasksListViewModel
import android.view.LayoutInflater
import android.view.ViewGroup
import android.os.Bundle
import prot3ct.workit.R
import androidx.recyclerview.widget.LinearLayoutManager
import android.widget.Toast
import android.content.Intent
import android.view.View
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import prot3ct.workit.views.create_task.CreateTaskActivity
import prot3ct.workit.views.login.LoginActivity
import prot3ct.workit.views.list_tasks.base.ListTasksContract
import java.util.ArrayList

class ListTasksFragment : Fragment(), ListTasksContract.View {
    private lateinit var presenter: ListTasksContract.Presenter
    private lateinit var adapter: ListTasksAdapter
    private lateinit var createTaskButton: FloatingActionButton
    private lateinit var recyclerTaskView: RecyclerView
    private lateinit var endProgressBar: ProgressBar
    private var searchMode = false
    private var searchQuery: String? = null
    private lateinit var scrollListener: EndlessRecyclerViewScrollListener
    private val allTasks: MutableList<AvailableTasksListViewModel> = ArrayList()

    override fun setPresenter(presenter: ListTasksContract.Presenter) {
        this.presenter = presenter
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_list_tasks, container, false)
        endProgressBar = view.findViewById(R.id.list_tasks_end_progress_bar)
        createTaskButton = view.findViewById(R.id.id_create_task_button)
        recyclerTaskView = view.findViewById(R.id.id_list_tasks_list_view)
        val llm = LinearLayoutManager(context)
        recyclerTaskView.layoutManager = llm
        adapter = ListTasksAdapter(allTasks, requireContext())
        recyclerTaskView.adapter = adapter
        scrollListener = object : EndlessRecyclerViewScrollListener(llm) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
                showEndProgressBar()
                if (searchMode) {
                    presenter.getSearchedAvailableTasks(page, searchQuery!!)
                } else {
                    presenter.getAllTasks(page)
                }
            }
        }
        recyclerTaskView.addOnScrollListener(scrollListener)
        createTaskButton.setOnClickListener { showCreateJobActivity() }
        presenter.getAllTasks(1)
        return view
    }

    override fun showEndProgressBar() {
        endProgressBar.visibility = View.VISIBLE
    }

    override fun hideEndProgressBar() {
        endProgressBar.visibility = View.GONE
    }

    override fun notifyError(msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
    }

    override fun showCreateJobActivity() {
        val intent = Intent(context, CreateTaskActivity::class.java)
        startActivity(intent)
    }

    override fun showLoginActivity() {
        val intent = Intent(context, LoginActivity::class.java)
        startActivity(intent)
    }

    override fun notifySuccessful(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    override fun updateTasks(tasks: List<AvailableTasksListViewModel>) {
        allTasks.addAll(tasks)
        adapter.notifyDataSetChanged()
        hideEndProgressBar()
    }

    override fun filterTask(query: String) {
        searchQuery = query
        if (!searchMode && query.isNotEmpty()) {
            searchMode = true
            showEndProgressBar()
            allTasks.clear()
            adapter.notifyDataSetChanged()
            scrollListener.resetState()
            presenter.getSearchedAvailableTasks(1, query)
        }
        if (query.isEmpty()) {
            searchMode = false
            showEndProgressBar()
            allTasks.clear()
            adapter.notifyDataSetChanged()
            scrollListener.resetState()
            presenter.getAllTasks(1)
        }
    }

    companion object {
        fun newInstance(): ListTasksFragment {
            return ListTasksFragment()
        }
    }
}