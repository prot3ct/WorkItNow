package prot3ct.workit.views.list_task_requests

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import prot3ct.workit.R
import prot3ct.workit.views.list_task_requests.ListTaskRequestsFragment
import prot3ct.workit.views.list_task_requests.ListTaskRequestsPresenter
import prot3ct.workit.views.list_task_requests.base.ListTaskRequestContract

class ListTaskRequestsActivity : AppCompatActivity() {

    private lateinit var presenter: ListTaskRequestContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_task_requests)
        var listTaskRequestsFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_container) as ListTaskRequestsFragment?
        if (listTaskRequestsFragment == null) {
            listTaskRequestsFragment = ListTaskRequestsFragment.newInstance()
            supportFragmentManager
                .beginTransaction()
                .add(R.id.fragment_container, listTaskRequestsFragment)
                .commit()
        }
        presenter = ListTaskRequestsPresenter(listTaskRequestsFragment, this)
        listTaskRequestsFragment.setPresenter(presenter)
    }
}