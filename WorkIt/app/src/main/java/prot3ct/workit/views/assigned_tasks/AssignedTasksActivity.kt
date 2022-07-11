package prot3ct.workit.views.assigned_tasks

import androidx.appcompat.app.AppCompatActivity
import prot3ct.workit.views.assigned_tasks.base.AssignedTasksContract
import prot3ct.workit.views.assigned_tasks.AssignedTasksFragment
import android.os.Bundle
import android.view.Menu
import prot3ct.workit.R
import prot3ct.workit.views.navigation.DrawerUtil
import prot3ct.workit.views.assigned_tasks.AssignedTasksPresenter
import android.view.MenuInflater
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import prot3ct.workit.views.chat.ChatFragment

class AssignedTasksActivity : AppCompatActivity() {

    private lateinit var presenter: AssignedTasksContract.Presenter
    private var assignedTasksFragment: AssignedTasksFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_assigned_tasks)
        val toolbar = findViewById<Toolbar>(R.id.id_drawer_toolbar)
        setSupportActionBar(toolbar)
        title = "Assigned Tasks"
        val drawer = DrawerUtil(this, toolbar)
        drawer.getDrawer()
        assignedTasksFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_container) as AssignedTasksFragment?

        if (assignedTasksFragment == null) {
            assignedTasksFragment = AssignedTasksFragment.newInstance()
            supportFragmentManager
                .beginTransaction()
                .add(R.id.fragment_container, assignedTasksFragment!!)
                .commit()
        }

        presenter = AssignedTasksPresenter(assignedTasksFragment!!, this)
        assignedTasksFragment!!.setPresenter(presenter)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_options, menu)
        val ourSearchItem = menu.findItem(R.id.menu_search)
        val sv = ourSearchItem.actionView as SearchView
        sv.queryHint = "Search by title"
        sv.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                assignedTasksFragment!!.filterTask(newText)
                return true
            }
        })
        return true
    }
}