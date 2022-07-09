package prot3ct.workit.views.my_tasks

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import prot3ct.workit.R
import prot3ct.workit.views.navigation.DrawerUtil
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import prot3ct.workit.views.my_tasks.base.MyTasksContract

class MyTasksActivity : AppCompatActivity() {
    private lateinit  var presenter: MyTasksContract.Presenter
    private var myTasksFragment: MyTasksFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_tasks)
        val toolbar = findViewById<Toolbar>(R.id.id_drawer_toolbar)
        setSupportActionBar(toolbar)
        title = "My Tasks"
        val drawer = DrawerUtil(this, toolbar)
        drawer.getDrawer()
        myTasksFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_container) as MyTasksFragment?
        if (myTasksFragment == null) {
            myTasksFragment = MyTasksFragment.newInstance()
            supportFragmentManager
                .beginTransaction()
                .add(R.id.fragment_container, myTasksFragment!!)
                .commit()
        }
        presenter = MyTasksPresenter(myTasksFragment!!, this)
        myTasksFragment!!.setPresenter(presenter)
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
                myTasksFragment!!.filterTask(newText)
                return true
            }
        })
        return true
    }
}