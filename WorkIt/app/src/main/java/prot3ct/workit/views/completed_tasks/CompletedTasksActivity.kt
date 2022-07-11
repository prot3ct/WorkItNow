package prot3ct.workit.views.completed_tasks

import androidx.appcompat.app.AppCompatActivity
import com.stepstone.apprating.listener.RatingDialogListener
import prot3ct.workit.views.completed_tasks.CompletedTasksFragment
import android.os.Bundle
import android.view.Menu
import prot3ct.workit.R
import prot3ct.workit.views.navigation.DrawerUtil
import prot3ct.workit.views.completed_tasks.CompletedTasksPresenter
import android.view.MenuInflater
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import prot3ct.workit.views.completed_tasks.base.CompletedTasksContract
import prot3ct.workit.views.create_task.CreateTaskFragment

class CompletedTasksActivity : AppCompatActivity(), RatingDialogListener {

    private lateinit var presenter: CompletedTasksContract.Presenter

    private var completedTasksFragment: CompletedTasksFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_completed_tasks)
        val toolbar = findViewById<Toolbar>(R.id.id_drawer_toolbar)
        setSupportActionBar(toolbar)
        title = "Completed Tasks"
        val drawer = DrawerUtil(this, toolbar)
        drawer.getDrawer()
        completedTasksFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_container) as CompletedTasksFragment?

        if (completedTasksFragment == null) {
            completedTasksFragment = CompletedTasksFragment.newInstance()
            supportFragmentManager
                .beginTransaction()
                .add(R.id.fragment_container, completedTasksFragment!!)
                .commit()
        }

        presenter = CompletedTasksPresenter(completedTasksFragment!!, this)
        completedTasksFragment!!.setPresenter(presenter)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_options, menu)
        val ourSearchItem = menu.findItem(R.id.menu_search)
        val sv = ourSearchItem.actionView as SearchView?
        sv!!.queryHint = "Search by title"
        sv.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                completedTasksFragment!!.filterTask(newText)
                return true
            }
        })
        return true
    }

    override fun onPositiveButtonClicked(value: Int, description: String) {
        completedTasksFragment!!.postRaiting(value, description)
    }

    override fun onNegativeButtonClicked() {}
    override fun onNeutralButtonClicked() {}
}