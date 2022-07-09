package prot3ct.workit.views.my_tasks

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.os.Bundle
import prot3ct.workit.R
import androidx.recyclerview.widget.LinearLayoutManager
import android.content.Intent
import android.view.View
import prot3ct.workit.views.create_task.CreateTaskActivity
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import prot3ct.workit.view_models.MyTasksListViewModel
import prot3ct.workit.views.my_tasks.base.MyTasksContract

class MyTasksFragment : Fragment(), MyTasksContract.View {

    private lateinit var presenter: MyTasksContract.Presenter
    private lateinit var adapter: MyTasksAdapter
    private lateinit var createTaskButton: FloatingActionButton
    private lateinit var recyclerTaskView: RecyclerView

    override fun setPresenter(presenter: MyTasksContract.Presenter) {
        this.presenter = presenter
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_my_tasks, container, false)
        createTaskButton = view.findViewById(R.id.id_create_task_button)
        recyclerTaskView = view.findViewById(R.id.id_my_tasks_list_view)
        val llm = LinearLayoutManager(context)
        recyclerTaskView.layoutManager = llm
        createTaskButton.setOnClickListener(View.OnClickListener { showCreateJobActivity() })
        presenter.myTasks
        return view
    }

    override fun showCreateJobActivity() {
        val intent = Intent(context, CreateTaskActivity::class.java)
        startActivity(intent)
    }

    override fun notifySuccessful(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    override fun filterTask(query: String) {
        adapter.filter(query)
    }

    override fun notifyError(errorMessage: String) {
        Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
    }

    override fun setupTasksAdapter(tasks: List<MyTasksListViewModel>) {
        adapter = MyTasksAdapter(tasks.toMutableList(), requireContext(), presenter!!)
        recyclerTaskView.adapter = adapter
    }

    companion object {
        fun newInstance(): MyTasksFragment {
            return MyTasksFragment()
        }
    }
}