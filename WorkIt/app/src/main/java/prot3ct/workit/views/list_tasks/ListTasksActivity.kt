package prot3ct.workit.views.list_tasks

import androidx.appcompat.app.AppCompatActivity
import prot3ct.workit.views.list_tasks.ListTasksFragment
import android.os.Bundle
import android.view.Menu
import prot3ct.workit.R
import prot3ct.workit.views.navigation.DrawerUtil
import prot3ct.workit.views.list_tasks.ListTasksPresenter
import android.view.MenuInflater
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import prot3ct.workit.views.list_tasks.base.ListTasksContract

class ListTasksActivity : AppCompatActivity() {

    private lateinit var presenter: ListTasksContract.Presenter
    private var listJobsFragment: ListTasksFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_tasks)
        val toolbar = findViewById<Toolbar>(R.id.id_drawer_toolbar)
        setSupportActionBar(toolbar)
        title = "Browse Tasks"
        val drawer = DrawerUtil(this, toolbar)
        drawer.getDrawer()
        listJobsFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_container) as ListTasksFragment?
        if (listJobsFragment == null) {
            listJobsFragment = ListTasksFragment.newInstance()
            supportFragmentManager
                .beginTransaction()
                .add(R.id.fragment_container, listJobsFragment!!)
                .commit()
        }
        presenter = ListTasksPresenter(listJobsFragment!!, this)
        listJobsFragment!!.setPresenter(presenter)
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
                listJobsFragment!!.filterTask(newText)
                return true
            }
        })
        return true
    }
}